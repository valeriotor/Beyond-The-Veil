package com.valeriotor.beyondtheveil.rituals;

import com.valeriotor.beyondtheveil.surgery.PatientType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class RitualTemplate {

    private final String name;
    private final int startingPrimaryInstability;
    private final double primaryInstabilityRateTemplate;
    private final double secondaryInstabilityRateTemplate;
    private final double secondarySeverityRateTemplate;
    private final int frequencyRate = 0;
    private final BiFunction<List<ItemStack>, UUID, List<ItemStack>> outputs;
    private final AdditionalRitualEffect otherEffects;
    private final Predicate<List<ItemStack>> match;
    private final PatientType patientType;
    private final double startingSecondarySeverity;
    private final Instability thesisInstability;


    private RitualTemplate(RitualTemplateBuilder builder) {
        this.name = builder.name;
        this.startingPrimaryInstability = builder.startingPrimaryInstability;
        this.primaryInstabilityRateTemplate = builder.primaryInstabilityRateTemplate;
        this.secondaryInstabilityRateTemplate = builder.secondaryInstabilityRateTemplate;
        this.secondarySeverityRateTemplate = builder.secondarySeverityRateTemplate;
        this.startingSecondarySeverity = builder.startingSecondarySeverity;
        this.outputs = builder.outputs;
        this.otherEffects = builder.otherEffects;
        match = builder.match;
        this.patientType = builder.patientType;
        this.thesisInstability = builder.thesisInstability;
    }

    public String getName() {
        return name;
    }

    public BiFunction<List<ItemStack>, UUID, List<ItemStack>> getOutputs() {
        return outputs;
    }

    public AdditionalRitualEffect getOtherEffects() {
        return otherEffects;
    }

    public int getStartingPrimaryInstability() {
        return startingPrimaryInstability;
    }

    public double getPrimaryInstabilityRateTemplate() {
        return primaryInstabilityRateTemplate;
    }

    public double getSecondaryInstabilityRateTemplate() {
        return secondaryInstabilityRateTemplate;
    }

    public double getStartingSecondarySeverity() {
        return startingSecondarySeverity;
    }

    public double getSecondarySeverityRateTemplate() {
        return secondarySeverityRateTemplate;
    }

    public Instability getThesisInstability() {
        return thesisInstability;
    }

    public boolean matches(List<ItemStack> input, PatientType patientType) {
        return patientType == this.patientType && match.test(input);
    }

    public static class RitualTemplateBuilder {

        private final String name;
        private final int startingPrimaryInstability;
        private final double primaryInstabilityRateTemplate;
        private final double secondaryInstabilityRateTemplate;
        private final double secondarySeverityRateTemplate;
        private final double startingSecondarySeverity;
        public final Instability thesisInstability;
        private BiFunction<List<ItemStack>, UUID, List<ItemStack>> outputs = (a, b) -> new ArrayList<>();
        private AdditionalRitualEffect otherEffects = (player, level, vec3, dimension) -> {};
        private Predicate<List<ItemStack>> match;
        private PatientType patientType = PatientType.VILLAGER;

        public RitualTemplateBuilder(String name, int startingPrimaryInstability, double primaryInstabilityRateTemplate, double secondaryInstabilityRateTemplate, double secondarySeverityRateTemplate, double startingSecondarySeverity, Instability thesisInstability) {
            this.name = name;
            this.startingPrimaryInstability = startingPrimaryInstability;
            this.primaryInstabilityRateTemplate = primaryInstabilityRateTemplate;
            this.secondaryInstabilityRateTemplate = secondaryInstabilityRateTemplate;
            this.secondarySeverityRateTemplate = secondarySeverityRateTemplate;
            this.startingSecondarySeverity = startingSecondarySeverity;
            this.thesisInstability = thesisInstability;
        }

        public RitualTemplateBuilder setMatch(Predicate<List<ItemStack>> input) {
            match = input;
            return this;
        }

        public RitualTemplateBuilder setOutputs(List<ItemStack> outputs) {
            this.outputs = (a, b) -> outputs;
            return this;
        }

        public RitualTemplateBuilder setOutputs(BiFunction<List<ItemStack>, UUID, List<ItemStack>> outputs) {
            this.outputs = outputs;
            return this;
        }

        public RitualTemplateBuilder setOtherEffects(AdditionalRitualEffect otherEffects) {
            this.otherEffects = otherEffects;
            return this;
        }

        public RitualTemplateBuilder weeper() {
            this.patientType = PatientType.WEEPER;
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
        void apply(UUID player, ServerLevel level, Vec3 targetPos, ResourceKey<Level> dimension); // target pos is either altar or whatever is targeted by modifier sigils
    }

    public enum Instability {
        NEGLIGIBLE, LOW, MEDIUM, HIGH
    }

}
