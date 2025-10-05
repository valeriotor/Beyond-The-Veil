package com.valeriotor.beyondtheveil.rituals;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.util.ItemSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RitualTemplate {

    private final String name;
    private final int startingPrimaryInstability;
    private final int primaryInstabilityRate;
    private final int secondaryInstabilityRate;
    private final BiFunction<List<ItemStack>, Player, List<ItemStack>> outputs;
    private final AdditionalRitualEffect otherEffects;
    private final Predicate<List<Item>> match;

    private RitualTemplate(RitualTemplateBuilder builder) {
        this.name = builder.name;
        this.startingPrimaryInstability = builder.startingPrimaryInstability;
        this.primaryInstabilityRate = builder.primaryInstabilityRate;
        this.secondaryInstabilityRate = builder.secondaryInstabilityRate;
        this.outputs = builder.outputs;
        this.otherEffects = builder.otherEffects;
        match = builder.match;
    }

    public String getName() {
        return name;
    }

    public BiFunction<List<ItemStack>, Player, List<ItemStack>> getOutputs() {
        return outputs;
    }

    public AdditionalRitualEffect getOtherEffects() {
        return otherEffects;
    }

    public int getStartingPrimaryInstability() {
        return startingPrimaryInstability;
    }

    public int getPrimaryInstabilityRate() {
        return primaryInstabilityRate;
    }

    public int getSecondaryInstabilityRate() {
        return secondaryInstabilityRate;
    }

    public boolean matches(List<Item> input) {
        return match.test(input);
    }

    public static class RitualTemplateBuilder {

        private final String name;
        private final int startingPrimaryInstability;
        private final int primaryInstabilityRate;
        private final int secondaryInstabilityRate;
        private BiFunction<List<ItemStack>, Player, List<ItemStack>> outputs = (a, b) -> new ArrayList<>();
        private AdditionalRitualEffect otherEffects = (player, level, vec3) -> {};
        private Predicate<List<Item>> match;

        public RitualTemplateBuilder(String name, int startingPrimaryInstability, int primaryInstabilityRate, int secondaryInstabilityRate) {
            this.name = name;
            this.startingPrimaryInstability = startingPrimaryInstability;
            this.primaryInstabilityRate = primaryInstabilityRate;
            this.secondaryInstabilityRate = secondaryInstabilityRate;
        }

        public RitualTemplateBuilder setMatch(Predicate<List<Item>> input) {
            match = input;
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

        public RitualTemplateBuilder setOtherEffects(AdditionalRitualEffect otherEffects) {
            this.otherEffects = otherEffects;
            return this;
        }

        public RitualTemplate toTemplate(List<RitualTemplate> templates, Map<String, RitualTemplate> by_name) {
            RitualTemplate template = new RitualTemplate(this);
            templates.add(template);
            by_name.put(name, template);
            return template;
        }

    }

    @FunctionalInterface
    interface AdditionalRitualEffect {
        void apply(Player player, ServerLevel level, Vec3 altarPos);
    }



}
