package com.valeriotor.beyondtheveil.event;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MemoryUnlockEvents {

    public static final Set<Block> CRYSTAL_BLOCKS = Set.of(Blocks.AMETHYST_BLOCK, Blocks.AMETHYST_CLUSTER, Blocks.BUDDING_AMETHYST, Blocks.LARGE_AMETHYST_BUD, Blocks.MEDIUM_AMETHYST_BUD, Blocks.SMALL_AMETHYST_BUD, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.LAPIS_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_LAPIS_ORE);

    @SubscribeEvent
    public static void babyEntitySpawnEvent(BabyEntitySpawnEvent event) {
        Player player = event.getCausedByPlayer();
        if (player instanceof ServerPlayer sp) {
            Memory.ANIMAL.unlock(sp);
        }
    }


    @SubscribeEvent
    public static void breakBlockEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer sp && CRYSTAL_BLOCKS.contains(event.getState().getBlock()) && ResearchUtil.getResearchStage(sp, "FIRSTDREAMS") >= 2) {
            Memory.CRYSTAL.unlock(sp);
        }
    }

    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp && communed(sp)) {
            Memory.DEATH.unlock(sp);
        }
        if (event.getSource().getEntity() instanceof ServerPlayer sp && communed(sp)) {
            if (event.getEntity().getMaxHealth() >= 200) {
                Memory.POWER.unlock(sp);
            }
        }
    }

    @SubscribeEvent
    public static void livingDropsEvent(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer sp && communed(sp) && !Memory.BEHEADING.isUnlocked(sp)) {
            for (ItemEntity drop : event.getDrops()) {
                if (drop.getItem().getItem() == Items.WITHER_SKELETON_SKULL) {
                    Memory.BEHEADING.unlock(sp);
                }
            }
        }
    }

    public static void archeCurrentEvent(ServerPlayer player, long ticks) {
        if (ticks > 600 && ticks < ArcheCycleData.CURRENT_PEAK && !Memory.CHANGE.isUnlocked(player)) {
            DataUtil.incrementOrSetInteger(player, PlayerDataLib.change_memory_progress.name(), 1, 1, false);
            if (DataUtil.getInt(player, PlayerDataLib.change_memory_progress.name()) > 5000) {
                Memory.CHANGE.unlock(player);
            }
        }
    }

    @SubscribeEvent
    public static void anvilRepairEvent(AnvilRepairEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp && communed(sp) && !Memory.REPAIR.isUnlocked(sp)) {
            DataUtil.incrementOrSetInteger(sp, PlayerDataLib.repair_memory_progress.name(), 1, 0, false);
            if (DataUtil.getInt(sp, PlayerDataLib.repair_memory_progress.name()) >= 5) {
                Memory.REPAIR.unlock(sp);
            }
        }
    }

    public static void dreamEvent(ServerPlayer sp) {
        if (communed(sp) && !Memory.STILLNESS.isUnlocked(sp)) {
            DataUtil.incrementOrSetInteger(sp, PlayerDataLib.stillness_memory_progress.name(), 1, 0, false);
            if (DataUtil.getInt(sp, PlayerDataLib.stillness_memory_progress.name()) >= 25) {
                Memory.STILLNESS.unlock(sp);
            }
        }
    }

    private static boolean communed(ServerPlayer sp) {
        return ResearchUtil.isResearchComplete(sp, "COMMUNION");
    }



}
