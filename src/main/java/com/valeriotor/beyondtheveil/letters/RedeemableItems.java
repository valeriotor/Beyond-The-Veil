package com.valeriotor.beyondtheveil.letters;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public enum RedeemableItems {
    SLUGS(List.of(new ItemStack(Registration.SLUG.get())));

    private final List<ItemStack> stacks;

    RedeemableItems(List<ItemStack> stacks) {

        this.stacks = stacks;
    }

    public List<ItemStack> getStacks() {
        return stacks.stream().map(ItemStack::copy).toList();
    }
}
