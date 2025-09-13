package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

public record ColorTriplet(DyeColor first, DyeColor second, DyeColor third) implements Comparable<ColorTriplet> {

    public DyeColor index(int i) {
        if (i == 0) {
            return first;
        } else if (i == 1) {
            return second;
        } else {
            return third;
        }
    }

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

    @Override
    public int compareTo(@NotNull ColorTriplet o) {
        for (int i = 0; i < 3; i++) {
            if (index(i) == null && o.index(i) != null) {
                return -1;
            } else if (index(i) != null && o.index(i) == null) {
                return 1;
            } else if (index(i) != null && o.index(i) != null) {
                int compareDye = index(i).compareTo(o.index(i));
                if (compareDye != 0) {
                    return compareDye;
                }
            }
        }
        return 0;
    }
}
