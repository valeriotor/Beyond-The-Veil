package com.valeriotor.beyondtheveil.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
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

    public static void dropItem(LivingEntity entity) {
        ItemStack mainHandItem = entity.getMainHandItem();
        if (!mainHandItem.isEmpty()) {
            entity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            double d0 = entity.getEyeY() - (double)0.3F;
            ItemEntity itementity = new ItemEntity(entity.level(), entity.getX(), d0, entity.getZ(), mainHandItem);
            itementity.setPickUpDelay(40);
            itementity.setThrower(entity.getUUID());
            float f7 = 0.3F;
            float f8 = Mth.sin(entity.getXRot() * ((float)Math.PI / 180F));
            float f2 = Mth.cos(entity.getXRot() * ((float)Math.PI / 180F));
            float f3 = Mth.sin(entity.getYRot() * ((float)Math.PI / 180F));
            float f4 = Mth.cos(entity.getYRot() * ((float)Math.PI / 180F));
            float f5 = entity.getRandom().nextFloat() * ((float)Math.PI * 2F);
            float f6 = 0.02F * entity.getRandom().nextFloat();
            itementity.setDeltaMovement((double)(-f3 * f2 * 0.3F) + Math.cos((double)f5) * (double)f6, (double)(-f8 * 0.3F + 0.1F + (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F), (double)(f4 * f2 * 0.3F) + Math.sin((double)f5) * (double)f6);
            entity.level().addFreshEntity(itementity);
        }
    }
}
