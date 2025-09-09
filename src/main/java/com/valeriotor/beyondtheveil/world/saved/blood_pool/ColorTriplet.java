package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;

public record ColorTriplet(DyeColor first, DyeColor second, DyeColor third) {

    public CompoundTag saveToTag(CompoundTag tag) {
        if (first != null) {
            tag.putString("first", first.name());
        }
        if (second != null) {
            tag.putString("second", second.name());
        }
        if (third != null) {
            tag.putString("third", third.name());
        }
        return tag;
    }

    public static ColorTriplet fromTag(CompoundTag tag) {
        DyeColor first = tag.contains("first") ? DyeColor.valueOf(tag.getString("first")) : null;
        DyeColor second = tag.contains("second") ? DyeColor.valueOf(tag.getString("second")) : null;
        DyeColor third = tag.contains("third") ? DyeColor.valueOf(tag.getString("third")) : null;
        return new ColorTriplet(first, second, third);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first == null ? "null|" : (first.name() + "|"));
        sb.append(second == null ? "null|" : (second.name() + "|"));
        sb.append(third == null ? "null" : (third.name()));
        return sb.toString();
    }
}
