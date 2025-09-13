package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class FleboBE extends BlockEntity {

    private ColorTriplet poolTriplet = new ColorTriplet(null, null, null);
    private UUID owner;

    public FleboBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.FLEBO_BE.get(), pPos, pBlockState);
    }

    public void setColor(DyeColor first, DyeColor second, DyeColor third) {
        poolTriplet = new ColorTriplet(first, second, third);
    }

    public ColorTriplet getColor() {
        return poolTriplet;
    }

    public UUID getOwner() {
        return owner;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        poolTriplet.saveToTag(pTag);
        if (owner != null) {
            pTag.putUUID("owner", owner);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        poolTriplet = ColorTriplet.fromTag(pTag);
        owner = pTag.contains("owner") ? pTag.getUUID("owner") : null;
    }
}
