package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record ColorTriplet(DyeColor first, DyeColor second, DyeColor third) implements Comparable<ColorTriplet> {

    public static ColorTriplet fromTag(CompoundTag tag) {
        DyeColor first = tag.contains("first") ? DyeColor.valueOf(tag.getString("first")) : null;
        DyeColor second = tag.contains("second") ? DyeColor.valueOf(tag.getString("second")) : null;
        DyeColor third = tag.contains("third") ? DyeColor.valueOf(tag.getString("third")) : null;
        return new ColorTriplet(first, second, third);
    }

    public static ColorTriplet fromItems(Item item0, Item item1, Item item2) {
        DyeColor dyeColor0 = null;
        DyeColor dyeColor1 = null;
        DyeColor dyeColor2 = null;
        if (item0 instanceof DyeItem dye) {
            dyeColor0 = dye.getDyeColor();
        }
        if (item1 instanceof DyeItem dye) {
            dyeColor1 = dye.getDyeColor();
        }
        if (item2 instanceof DyeItem dye) {
            dyeColor2 = dye.getDyeColor();
        }
        return new ColorTriplet(dyeColor0, dyeColor1, dyeColor2);
    }

    public DyeColor index(int i) {
        if (i == 0) {
            return first;
        } else if (i == 1) {
            return second;
        } else {
            return third;
        }
    }

    public ColorTriplet addToRight(Item newItem) {
        DyeColor newColor = null;
        if (newItem instanceof DyeItem dye) {
            newColor = dye.getDyeColor();
        }
        return new ColorTriplet(second, third, newColor);
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
