package com.valeriotor.beyondtheveil.dreaming;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FumeSpreaderBlock;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.dreaming.dreams.Dream;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamRegistry;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.event.MemoryUnlockEvents;
import com.valeriotor.beyondtheveil.item.MemoryPhialItem;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.tile.FumeSpreaderBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PersistentPlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.*;

public class DreamHandler {

    public static void dream(ServerPlayer p) {
        dream(p, true);
    }

    public static void dream(ServerPlayer p, boolean bed) {
        if (!bed) {
            if (p.level().getBlockState(p.getOnPos()).getBlock() != Registration.SLEEP_CHAMBER.get()) {
                return;
            }
            if (!p.getCapability(PlayerDataProvider.PLAYER_DATA).isPresent() || p.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().getOrSetInteger(PlayerDataLib.TIMES_DREAMT.apply("sleep_chamber"), 0, false) >= 2) {
                return;
            }
        }
        DataUtil.clearReminiscences(p);
        List<FumeSpreaderBE> spreaders = findFumeSpreader(p, p.level(), p.getOnPos(), 1);
        //spreaders.sort(Comparator.comparingInt(be -> Dream.REGISTRY.get(be.getStoredMemory()).getPriority()));
        //TODO reimplement sorting. First void, then find highest (lowest) priority among all voided versions (if void was used) of other dreams, then sort as normal
        List<FumeSpreaderBE> successes = new ArrayList<>();
        for (FumeSpreaderBE be : spreaders) {
            Memory memory = be.getStoredMemory();
            boolean hasVoid = hasVoid(p);
            if (DreamRegistry.getDreamFromMemory(memory, hasVoid(p)).activate(p, p.level())) {
                successes.add(be);
                DataUtil.getMemoryStatus(p, memory).increaseTo(1, Memory.Target.BASE, !hasVoid(p) && hasVoid);
            }
        }

        for (FumeSpreaderBE spreader : successes) {
            DataUtil.setBooleanOnServerAndSync(p, spreader.getStoredMemory().name() + "Dream", true, false);
            spreader.setStoredMemory(null);
            BlockPos pos = spreader.getBlockPos();
            p.level().setBlock(pos, p.level().getBlockState(pos).setValue(FumeSpreaderBlock.FULL, false), 3);
        }
        if (successes.isEmpty()) {
            boolean emptyReminiscence = false;
            if (DataUtil.getBoolean(p, PlayerDataLib.drankmemory.name())) {
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.drankmemory.name(), false, false);
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.drank_dream.name(), true, false);
                emptyReminiscence = true;
            }
            ItemStack mainHandItem = p.getMainHandItem();
            if (mainHandItem.getItem() == Registration.MEMORY_PHIAL.get()) {
                CompoundTag tag = mainHandItem.getOrCreateTag();
                Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));
                if (m != null) {
                    DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.held_dream.name(), true, false);
                    emptyReminiscence = true;
                }
            }
            if (emptyReminiscence) {
                DataUtil.addReminiscence(p, "none", new Reminiscence.EmptyReminiscence());
            }
        } else {
            if (!bed) {
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.slept_in_chamber.name(), true, false);
                DataUtil.incrementOrSetInteger(p, PlayerDataLib.TIMES_DREAMT.apply("sleep_chamber"), 1, 1, false);
                markTimesDreamt(p, "sleep_chamber");
            }
            MemoryUnlockEvents.dreamEvent(p);
        }
        DataUtil.syncReminiscences(p);
        DataUtil.syncMemories(p);
    }

    public static void dreamBottle(ServerPlayer p, ItemStack stack) {
        if (!stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            return;
        }
        IFluidHandlerItem fluid = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().get();
        boolean isRunningOnFluid = fluid.getFluidInTank(0).getAmount() < 100;
        if (!p.getCapability(PlayerDataProvider.PLAYER_DATA).isPresent() ||
                (p.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().getOrSetInteger(PlayerDataLib.TIMES_DREAMT.apply("dream_bottle"), 0, false) >= 1 && isRunningOnFluid)) {
            return;
        }
        UUID target = null;
        BlockPos targetPos0 = null;
        ResourceKey<Level> targetDimension = null;
        ItemStack mainHandItem = p.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandItem = p.getItemInHand(InteractionHand.OFF_HAND);
        InteractionHand toRemove;
        if (mainHandItem.getItem() == Registration.SIGIL_PLAYER.get() && mainHandItem.getTag() != null && mainHandItem.getTag().contains("player")) { // TODO needs testing
            target = mainHandItem.getTag().getUUID("player");
            toRemove = InteractionHand.MAIN_HAND;
        } else if (offHandItem.getItem() == Registration.SIGIL_PLAYER.get() && offHandItem.getTag() != null && offHandItem.getTag().contains("player")) {
            target = offHandItem.getTag().getUUID("player");
            toRemove = InteractionHand.OFF_HAND;
        } else if (mainHandItem.getItem() == Registration.SIGIL_PATHWAY.get() && mainHandItem.getTag() != null && mainHandItem.getTag().contains("area")) { // TODO needs testing
            targetPos0 = BlockPos.of(mainHandItem.getTag().getLong("area"));
            targetDimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(mainHandItem.getTag().getString("dimension")));
            toRemove = InteractionHand.MAIN_HAND;
        } else if (offHandItem.getItem() == Registration.SIGIL_PATHWAY.get() && offHandItem.getTag() != null && offHandItem.getTag().contains("area")) {
            targetPos0 = BlockPos.of(offHandItem.getTag().getLong("area"));
            targetDimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(offHandItem.getTag().getString("dimension")));
            toRemove = InteractionHand.OFF_HAND;
        } else {
            toRemove = null;
        }
        ServerPlayer targetPlayer = target != null && p.getServer() != null ? p.getServer().getPlayerList().getPlayer(target) : null;
        if (targetPlayer == null && target != null) {
            p.sendSystemMessage(Component.translatable("message.dream_bottle.player_not_found"));
            return;
        }
        if (targetDimension != null && !targetDimension.equals(p.level().dimension())) {
            p.sendSystemMessage(Component.translatable("message.dream_bottle.other_dimension"));
            return;
        }
        DataUtil.clearReminiscences(p);
        BlockPos targetPos = targetPos0;
        stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(c -> {
            List<Tuple<Memory, Integer>> memories = new ArrayList<>();
            for (int i = 0; i < c.getSlots(); i++) {
                ItemStack stackInSlot = c.getStackInSlot(i);
                Memory memory = MemoryPhialItem.fromStack(stackInSlot);
                if (memory != null) {
                    memories.add(new Tuple<>(memory, i));
                }
            }
            List<Integer> successes = new ArrayList<>();
            // TODO sort? actually no
            for (Tuple<Memory, Integer> memory : memories) {
                Dream dream = DreamRegistry.getDreamFromMemory(memory.getA(), hasVoid(p));
                if (isRunningOnFluid && fluid.getFluidInTank(0).getAmount() < 100) {
                    break;
                }
                boolean hasVoid = hasVoid(p);
                boolean success = targetPlayer != null ? dream.activatePlayer(p, targetPlayer, p.level()) : (targetPos != null ? dream.activatePos(p, p.level(), targetPos) : dream.activate(p, p.level()));
                if (success) {
                    if (toRemove != null) {
                        p.getItemInHand(toRemove).shrink(1);
                    }
                    successes.add(memory.getB());
                    DataUtil.setBooleanOnServerAndSync(p, memory.getA().name() + "Dream", true, false);
                    DataUtil.getMemoryStatus(p, memory.getA()).increaseTo(1, targetPlayer != null ? Memory.Target.PLAYER : (targetPos != null ? Memory.Target.PATH : Memory.Target.BASE), !hasVoid(p) && hasVoid);
                    fluid.drain(100, IFluidHandler.FluidAction.EXECUTE);
                }
            }
            if (!successes.isEmpty()) {
                for (Integer success : successes) {
                    c.extractItem(success, 1, false);
                }
                if (!isRunningOnFluid) {
                    DataUtil.incrementOrSetInteger(p, PlayerDataLib.TIMES_DREAMT.apply("dream_bottle"), 1, 1, false);
                    markTimesDreamt(p, "dream_bottle");
                }
                
                DataUtil.setBooleanOnServerAndSyncIfDifferent(p, PlayerDataLib.used_bottle.name(), true, false);
            }
            DataUtil.syncReminiscences(p);
            DataUtil.syncMemories(p);
        });
    }

    private static void markTimesDreamt(Player player, String type) {
        player.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> {
            if (!c.hasTimer(type)) {
                PlayerTimer timer = new PlayerTimer(24000, type, PersistentPlayerTimer.DREAMT, Map.of("time", String.valueOf(player.level().getDayTime())));
                c.addTimer(timer);
            }
        });
    }

    private static final int[][] MULTIPLIERS = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

    private static List<FumeSpreaderBE> findFumeSpreader(Player p, Level level, BlockPos playerPos, int max) {
        List<FumeSpreaderBE> spreaders = new ArrayList<>();
        Set<Memory> memories = new HashSet<>();
        if (max == 0) return spreaders;
        checkColumnForSpreader(p, 0, 0, level, playerPos, spreaders, max, memories);
        for (int i = 1; i < 4 && spreaders.size() < max; i++) {
            for (int j = 0; j <= i && spreaders.size() < max; j++) {
                int multipliersToCheck = j == 0 ? 2 : 4;
                for (int k = 0; k < multipliersToCheck && spreaders.size() < max; k++) {
                    int[] multiplier = MULTIPLIERS[k];
                    checkColumnForSpreader(p, i * multiplier[0], j * multiplier[1], level, playerPos, spreaders, max, memories);
                    if (i != j) {
                        checkColumnForSpreader(p, j * multiplier[0], i * multiplier[1], level, playerPos, spreaders, max, memories);
                    }
                }
            }
        }
        return spreaders;
    }

    private static void checkColumnForSpreader(Player p, int xOffset, int zOffset, Level level, BlockPos startPos, List<FumeSpreaderBE> spreaders, int max, Set<Memory> memories) {
        if (spreaders.size() >= max) return;
        for (int yOffset = -1; yOffset < 3; yOffset++) {
            BlockEntity entity = level.getBlockEntity(startPos.offset(xOffset, yOffset, zOffset));
            if (entity instanceof FumeSpreaderBE fumeSpreaderBE) {
                Memory storedMemory = fumeSpreaderBE.getStoredMemory();
                if (storedMemory != null /*&& storedMemory.isUnlocked(p) */ && !memories.contains(storedMemory)) {
                    spreaders.add(fumeSpreaderBE);
                    memories.add(storedMemory);
                    if (spreaders.size() >= max) {
                        return;
                    }
                }
            }
        }
    }

    public static boolean hasVoid(Player player) {
        return DataUtil.getBoolean(player, PlayerDataLib.void_.name());
    }

    public static boolean consumeVoid(Player player) {
        if (DataUtil.getBoolean(player, PlayerDataLib.void_.name())) {
            DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.void_.name(), false, false);
            DataUtil.removeReminiscence(player, Memory.VOID.getDataName(false));
            return true;
        }
        return false;
    }

}
