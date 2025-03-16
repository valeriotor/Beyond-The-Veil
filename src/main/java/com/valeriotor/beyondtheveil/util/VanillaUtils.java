package com.valeriotor.beyondtheveil.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import javax.swing.text.html.parser.Entity;

public class VanillaUtils {
    public static void awardAdvancement(ServerPlayer p, ResourceLocation id) {
        if (p.getServer() != null) {
            Advancement advancement = p.getServer().getAdvancements().getAdvancement(id);
            if (advancement != null) {
                AdvancementProgress progress = p.getAdvancements().getOrStartProgress(advancement);
                if (!progress.isDone()) {
                    for (String remainingCriterion : progress.getRemainingCriteria()) {
                        p.getAdvancements().award(advancement, remainingCriterion);
                    }
                }
            }
        }
    }

    public static boolean canTeleportTo(LivingEntity entity, BlockPos pPos, Level level, boolean canFly) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(level, pPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = level.getBlockState(pPos.below());
            if (!canFly && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = pPos.subtract(entity.blockPosition());
                return level.noCollision(entity, entity.getBoundingBox().move(blockpos));
            }
        }
    }
}
