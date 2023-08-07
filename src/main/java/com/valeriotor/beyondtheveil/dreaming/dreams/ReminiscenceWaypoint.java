package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class ReminiscenceWaypoint extends Reminiscence {

    private BlockPos pos;
    private int color;

    public ReminiscenceWaypoint() {
        this.pos = BlockPos.ZERO;
        this.color = 0;
    }

    public ReminiscenceWaypoint(BlockPos pos, int color) {
        this.pos = pos;
        this.color = color;
    }

    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("pos", pos.asLong());
        tag.putInt("color", color);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        this.pos = BlockPos.of(tag.getLong("pos"));
        this.color = tag.getInt("color");
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getColor() {
        return color;
    }
}
