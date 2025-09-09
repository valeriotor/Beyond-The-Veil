package com.valeriotor.beyondtheveil.util;

import net.minecraft.world.item.Item;

import java.util.*;

public class ItemSet {

    public static ItemSet of(Item... items) {
        return new ItemSet(Arrays.asList(items));
    }

    private final Set<Item> items;

    public ItemSet(Collection<Item> possibilities) {
        items = Set.copyOf(possibilities);
    }

    public ItemSet(Item item) {
        items = Set.of(item);
    }

    public boolean match(Item test) {
        return items.contains(test);
    }

}
