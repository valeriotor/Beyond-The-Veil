package com.valeriotor.beyondtheveil.dreaming.dreams;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class ReminiscenceWaypoint extends Reminiscence {

    private ResourceKey<Level> dimension;
    private BlockPos pos;
    private int color;

    public ReminiscenceWaypoint() {
        this.pos = BlockPos.ZERO;
        this.color = 0;
        this.dimension = Level.OVERWORLD;
    }

    public ReminiscenceWaypoint(BlockPos pos, int color, ResourceKey<Level> dimension) {
        this.pos = pos;
        this.color = color;
        this.dimension = dimension;
    }

    @Override
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("pos", pos.asLong());
        tag.putInt("color", color);
        tag.putString("dimension", dimension.location().toString());
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        this.pos = BlockPos.of(tag.getLong("pos"));
        this.color = tag.getInt("color");
        this.dimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("dimension")));
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getColor() {
        return color;
    }

    public ResourceKey<Level> getDimension() {
        return dimension;
    }
}
