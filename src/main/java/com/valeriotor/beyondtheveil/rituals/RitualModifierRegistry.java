package com.valeriotor.beyondtheveil.rituals;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RitualModifierRegistry {

    private static final Map<Item, Consumer<RitualStatus>> MODIFIERS = new HashMap<>();

    static {

    }

    public static boolean isModifier(Item item) {
        return MODIFIERS.containsKey(item);
    }


}
