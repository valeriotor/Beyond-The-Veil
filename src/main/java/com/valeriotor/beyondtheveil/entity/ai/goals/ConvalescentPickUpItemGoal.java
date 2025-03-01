package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Comparator;
import java.util.List;

public class ConvalescentPickUpItemGoal<T extends Mob & SurgeryPatient> extends Goal {

    private final T entity;

    public ConvalescentPickUpItemGoal(T entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        LazyOptional<ConvalescentData> cap = entity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA);
        if (cap.isPresent()) {
            ConvalescentData data = cap.resolve().get();
            if (data.getFlags().getOrDefault("obedience_hormones", 0) > 0 && data.getChestPos() != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (entity.tickCount % 10 < 5 || entity.level().isClientSide) {
            return;
        }
        entity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
            BlockPos chestPos = c.getChestPos();
            if (c.getHeldStack().isEmpty()) {
                int moveRange = 24;
                List<ItemEntity> items = entity.level().getEntities(EntityTypeTest.forClass(ItemEntity.class), AABB.ofSize(entity.position(), moveRange, 5, moveRange), entity::hasLineOfSight);
                items.sort(Comparator.comparing(i -> i.distanceToSqr(entity)));
                if (!items.isEmpty()) {
                    ItemEntity nearest = items.get(0);
                    entity.getNavigation().moveTo(nearest, 1);
                }
                int pickUpRange = 3;
                List<ItemEntity> closeItems = entity.level().getEntities(EntityTypeTest.forClass(ItemEntity.class), AABB.ofSize(entity.position(), pickUpRange, 2, pickUpRange), i -> true);
                closeItems.sort(Comparator.comparing(i -> i.distanceToSqr(entity)));
                if (!closeItems.isEmpty()) {
                    ItemEntity nearest = closeItems.get(0);
                    c.setHeldStack(nearest.getItem());
                    nearest.discard();
                }
                if (items.isEmpty() && closeItems.isEmpty()) {
                    entity.getNavigation().moveTo(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1);
                }
            } else {
                if (chestPos != null) {
                    BlockEntity be = entity.level().getBlockEntity(chestPos);
                    if (be != null && be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                        entity.getNavigation().moveTo(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1);
                        if (entity.distanceToSqr(chestPos.getCenter()) < 4) {
                            ItemStack stack = ItemHandlerHelper.insertItemStacked(be.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get(), c.getHeldStack(), false);
                            c.setHeldStack(stack);
                        }
                    }
                }
            }
        });
    }
}
