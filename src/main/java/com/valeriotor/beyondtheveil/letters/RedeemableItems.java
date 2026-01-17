package com.valeriotor.beyondtheveil.letters;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

public enum RedeemableItems {
    SLUGS(List.of(new ItemStack(Registration.SLUG.get()))),
    THESIS(List.of(new ItemStack(Registration.BLOOD_THESIS.get()))),
    LARVA(List.of(new ItemStack(Registration.SURGEON_LARVA.get())));

    private final Function<Letter, List<ItemStack>> stacks;

    RedeemableItems(List<ItemStack> stacks) {
        this(c -> stacks);
    }

    RedeemableItems(Function<Letter, List<ItemStack>> stacks) {
        this.stacks = stacks;
    }

    public List<ItemStack> getStacks(Letter previous) {
        return stacks.apply(previous).stream().map(ItemStack::copy).toList();
    }
}
