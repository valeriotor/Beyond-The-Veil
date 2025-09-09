package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FleboBE extends BlockEntity {

    private boolean pool = false;
    private ColorTriplet poolTriplet = new ColorTriplet(null, null, null);

    public FleboBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.FLEBO_BE.get(), pPos, pBlockState);
    }

    public void setColor(DyeColor first, DyeColor second, DyeColor third) {
        poolTriplet = new ColorTriplet(first, second, third);
    }

    public ColorTriplet getColor() {
        return poolTriplet;
    }

    public boolean isPool() {
        return pool;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("pool", pool);
        poolTriplet.saveToTag(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        pool = pTag.getBoolean("pool");
        poolTriplet = ColorTriplet.fromTag(pTag);
    }
}
