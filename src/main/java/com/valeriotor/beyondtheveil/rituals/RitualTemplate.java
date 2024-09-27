package com.valeriotor.beyondtheveil.rituals;

import com.google.common.collect.Lists;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class RitualTemplate {

    private final String name;
    private final int startingPrimaryInstability;
    private final int primaryInstabilityRate;
    private final int secondaryInstabilityRate;
    private final List<Item> ingredients;
    private final BiFunction<List<ItemStack>, Player, List<ItemStack>> outputs;
    private final Consumer<Player> otherEffects;

    private RitualTemplate(RitualTemplateBuilder builder) {
        this.name = builder.name;
        this.startingPrimaryInstability = builder.startingPrimaryInstability;
        this.primaryInstabilityRate = builder.primaryInstabilityRate;
        this.secondaryInstabilityRate = builder.secondaryInstabilityRate;
        this.ingredients = builder.ingredients;
        this.outputs = builder.outputs;
        this.otherEffects = builder.otherEffects;
    }

    public String getName() {
        return name;
    }

    public static class RitualTemplateBuilder {

        private final String name;
        private final int startingPrimaryInstability;
        private final int primaryInstabilityRate;
        private final int secondaryInstabilityRate;
        private final List<Item> ingredients = new ArrayList<>();
        private BiFunction<List<ItemStack>, Player, List<ItemStack>> outputs = (a, b) -> new ArrayList<>();
        private Consumer<Player> otherEffects;

        public RitualTemplateBuilder(String name, int startingPrimaryInstability, int primaryInstabilityRate, int secondaryInstabilityRate) {
            this.name = name;
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

        public RitualTemplate toTemplate(Map<Item, Map<List<Item>, RitualTemplate>> dictionary, Map<String, RitualTemplate> by_name) {
            RitualTemplate template = new RitualTemplate(this);
            Map<List<Item>, RitualTemplate> templatesByInverseIngredients = dictionary.computeIfAbsent(template.ingredients.get(template.ingredients.size() - 1), i -> new HashMap<>());
            templatesByInverseIngredients.put(Lists.reverse(template.ingredients), template);
            by_name.put(name, template);
            return template;
        }

    }



}
