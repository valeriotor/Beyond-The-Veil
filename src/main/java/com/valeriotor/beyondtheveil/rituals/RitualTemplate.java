package com.valeriotor.beyondtheveil.rituals;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class RitualTemplate {

    private final int startingPrimaryInstability;
    private final int primaryInstabilityRate;
    private final int secondaryInstabilityRate;
    private final List<Item> ingredients;
    private final BiFunction<List<ItemStack>, Player, List<ItemStack>> outputs;
    private final Consumer<Player> otherEffects;

    private RitualTemplate(RitualTemplateBuilder builder) {
        this.startingPrimaryInstability = builder.startingPrimaryInstability;
        this.primaryInstabilityRate = builder.primaryInstabilityRate;
        this.secondaryInstabilityRate = builder.secondaryInstabilityRate;
        this.ingredients = builder.ingredients;
        this.outputs = builder.outputs;
        this.otherEffects = builder.otherEffects;
    }

    public class RitualTemplateBuilder {

        private final int startingPrimaryInstability;
        private final int primaryInstabilityRate;
        private final int secondaryInstabilityRate;
        private final List<Item> ingredients = new ArrayList<>();
        private BiFunction<List<ItemStack>, Player, List<ItemStack>> outputs = (a, b) -> new ArrayList<>();
        private Consumer<Player> otherEffects;

        public RitualTemplateBuilder(int startingPrimaryInstability, int primaryInstabilityRate, int secondaryInstabilityRate) {
            this.startingPrimaryInstability = startingPrimaryInstability;
            this.primaryInstabilityRate = primaryInstabilityRate;
            this.secondaryInstabilityRate = secondaryInstabilityRate;
        }

        public RitualTemplateBuilder setIngredients(List<Item> ingredients) {
            this.ingredients.clear();
            this.ingredients.addAll(ingredients);
            return this;
        }

        public RitualTemplateBuilder setOutputs(List<ItemStack> outputs) {
            this.outputs = (a, b) -> outputs;
            return this;
        }

        public RitualTemplateBuilder setOutputs(BiFunction<List<ItemStack>, Player, List<ItemStack>> outputs) {
            this.outputs = outputs;
            return this;
        }

        public RitualTemplateBuilder setOtherEffects(Consumer<Player> otherEffects) {
            this.otherEffects = otherEffects;
            return this;
        }

        public RitualTemplate toTemplate() {
            RitualTemplate template = new RitualTemplate(this);
            return template;
        }

    }



}
