package com.valeriotor.beyondtheveil.dreaming;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FumeSpreaderBlock;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.dreaming.dreams.DreamRegistry;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.item.MemoryPhialItem;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.tile.FumeSpreaderBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PersistentPlayerTimer;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.*;

public class DreamHandler {

    public static void dream(Player p) {
        dream(p, true);
    }

    public static void dream(Player p, boolean bed) {
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
            if (DreamRegistry.getDreamFromMemory(be.getStoredMemory(), hasVoid(p)).activate(p, p.level())) {
                successes.add(be);
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
            if (DataUtil.getBoolean(p, PlayerDataLib.DRANK_ANY_MEMORY)) {
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.DRANK_ANY_MEMORY, false, false);
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.DRANK_MEMORY_DREAM, true, false);
                emptyReminiscence = true;
            }
            ItemStack mainHandItem = p.getMainHandItem();
            if (mainHandItem.getItem() == Registration.MEMORY_PHIAL.get()) {
                CompoundTag tag = mainHandItem.getOrCreateTag();
                Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));
                if (m != null) {
                    DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.HELD_MEMORY_DREAM, true, false);
                    emptyReminiscence = true;
                }
            }
            if (emptyReminiscence) {
                DataUtil.addReminiscence(p, "none", new Reminiscence.EmptyReminiscence());
            }
        } else {
            if (!bed) {
                DataUtil.setBooleanOnServerAndSync(p, PlayerDataLib.SLEPT_IN_CHAMBER, true, false);
                DataUtil.incrementOrSetInteger(p, PlayerDataLib.TIMES_DREAMT.apply("sleep_chamber"), 1, 1, false);
                markTimesDreamt(p, "sleep_chamber");
            }
        }
        DataUtil.syncReminiscences(p);
    }

    public static void dreamBottle(Player p, ItemStack stack) {
        if (!p.getCapability(PlayerDataProvider.PLAYER_DATA).isPresent() || p.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().getOrSetInteger(PlayerDataLib.TIMES_DREAMT.apply("dream_bottle"), 0, false) >= 1) {
            return;
        }
        DataUtil.clearReminiscences(p);
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
                if (DreamRegistry.getDreamFromMemory(memory.getA(), hasVoid(p)).activate(p, p.level())) {
                    successes.add(memory.getB());
                    DataUtil.setBooleanOnServerAndSync(p, memory.getA().name() + "Dream", true, false);
                }
            }
            if (!successes.isEmpty()) {
                for (Integer success : successes) {
                    c.extractItem(success, 1, false);
                }
                DataUtil.incrementOrSetInteger(p, PlayerDataLib.TIMES_DREAMT.apply("dream_bottle"), 1, 1, false);
                markTimesDreamt(p, "dream_bottle");
                DataUtil.setBooleanOnServerAndSyncIfDifferent(p, PlayerDataLib.USED_BOTTLE, true, false);
            }
            DataUtil.syncReminiscences(p);

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
        return DataUtil.getBoolean(player, PlayerDataLib.VOID);
    }

    public static boolean consumeVoid(Player player) {
        if (DataUtil.getBoolean(player, PlayerDataLib.VOID)) {
            DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.VOID, false, false);
            return true;
        }
        return false;
    }

}
