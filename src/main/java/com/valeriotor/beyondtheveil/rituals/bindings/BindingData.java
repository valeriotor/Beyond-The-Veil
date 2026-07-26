package com.valeriotor.beyondtheveil.rituals.bindings;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

public class BindingData {


    private final Binding binding;
    private int energy;
    private BlockPos overworldPos1;
    private BlockPos overworldPos2;
    private boolean blockBreakingMode;
    private boolean instantKill;
    private ArcheDamageType selectedType;
    private Map<PowerToggles, Boolean> enabledPowers = new EnumMap<>(PowerToggles.class);

    public BindingData(@NotNull Binding binding) {
        this.binding = binding;
        energy = 10000;
        instantKill = binding == Binding.NETHER;
    }

    public BindingData(CompoundTag nbt) {
        binding = Binding.valueOf(nbt.getString("binding"));
        energy = nbt.getInt("energy");
        if (nbt.contains("overworldPos1")) {
            overworldPos1 = BlockPos.of(nbt.getLong("overworldPos1"));
        }
        if (nbt.contains("overworldPos2")) {
            overworldPos2 = BlockPos.of(nbt.getLong("overworldPos2"));
        }
        instantKill = nbt.getBoolean("instantKill");
        if (nbt.contains("selectedType")) {
            selectedType = ArcheDamageType.valueOf(nbt.getString("selectedType"));
        }
        blockBreakingMode = nbt.getBoolean("blockBreakingMode");
        CompoundTag enabledPowersTag = nbt.getCompound("enabledPowers");
        for (String key : enabledPowersTag.getAllKeys()) {
            enabledPowers.put(PowerToggles.valueOf(key), enabledPowersTag.getBoolean(key));
        }
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void fillEnergy(int amount) {
        energy = Math.min(BindingCosts.MAX_ENERGY, energy + amount);
    }

    public boolean drainEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }

    public Binding getBinding() {
        return binding;
    }

    public BlockPos getOverworldPos1() {
        return overworldPos1;
    }

    public void setOverworldPos1(BlockPos overworldPos1) {
        this.overworldPos1 = overworldPos1;
    }

    public BlockPos getOverworldPos2() {
        return overworldPos2;
    }

    public void setOverworldPos2(BlockPos overworldPos2) {
        this.overworldPos2 = overworldPos2;
    }

    public boolean isBlockBreakingMode() {
        return blockBreakingMode;
    }

    public void setBlockBreakingMode(boolean blockBreakingMode) {
        this.blockBreakingMode = blockBreakingMode;
    }

    public boolean isInstantKill() {
        return instantKill;
    }

    public void setInstantKill(boolean instantKill) {
        if (binding == Binding.NETHER) {
            this.instantKill = instantKill;
        }
    }

    public int checkArcheResistance(DamageSource source) {
        if (selectedType == null) {
            return 0;
        }
        if (selectedType.test(source)) {
            return 1;
        }
        for (ArcheDamageType value : ArcheDamageType.values()) {
            if (value.test(source)) {
                return -1;
            }
        }
        return 0;
    }

    public void setSelectedType(ArcheDamageType selectedType) {
        this.selectedType = selectedType;
    }

    public ArcheDamageType getSelectedType() {
        return selectedType;
    }

    public boolean isPowerEnabled(PowerToggles power) {
        return enabledPowers.getOrDefault(power, true);
    }

    public void setPowerEnabled(PowerToggles power, boolean enabled) {
        enabledPowers.put(power, enabled);
        if (!enabled) {
            if (power == PowerToggles.OVERWORLD_BREAK) {
                setBlockBreakingMode(false);
            } else if (power == PowerToggles.OVERWORLD_BUILD) {
                overworldPos1 = null;
                overworldPos2 = null;
            }
        }
    }

    public CompoundTag saveToNBT(CompoundTag tag) {
        tag.putString("binding", binding.name());
        tag.putInt("energy", energy);
        if (overworldPos1 != null) {
            tag.putLong("overworldPos1", overworldPos1.asLong());
        }
        if (overworldPos2 != null) {
            tag.putLong("overworldPos2", overworldPos2.asLong());
        }
        tag.putBoolean("instantKill", instantKill);
        if (selectedType != null) {
            tag.putString("selectedType", selectedType.name());
        }
        tag.putBoolean("blockBreakingMode", blockBreakingMode);
        CompoundTag enabledPowersTag = new CompoundTag();
        this.enabledPowers.forEach((powerToggles, enabled) -> enabledPowersTag.putBoolean(powerToggles.name(), enabled));
        tag.put("enabledPowers", enabledPowersTag);
        return tag;
    }

    public enum ArcheDamageType {
        FIRE(s -> s.is(DamageTypeTags.IS_FIRE)),
        EXPLOSION(s -> s.is(DamageTypeTags.IS_EXPLOSION)),
        FALL(s -> s.is(DamageTypeTags.IS_FALL)),
        DROWNING(s -> s.is(DamageTypeTags.IS_DROWNING)),
        FREEZING(s -> s.is(DamageTypeTags.IS_FREEZING)),
        MAGIC(s -> s.is(DamageTypes.MAGIC)),
        WITHER(s -> s.is(DamageTypes.WITHER));

        private final Predicate<DamageSource> test;

        ArcheDamageType(Predicate<DamageSource> test) {
            this.test = test;
        }

        public boolean test(DamageSource source) {
            return test.test(source);
        }
    }

    public enum PowerToggles {
        OVERWORLD_BUILD(Binding.OVERWORLD),
        OVERWORLD_BREAK(Binding.OVERWORLD),
        OVERWORLD_HEAL(Binding.OVERWORLD),
        OVERWORLD_REPAIR(Binding.OVERWORLD),
        NETHER_ATTACK(Binding.NETHER),
        NETHER_HEAL(Binding.NETHER),
        NETHER_FEED(Binding.NETHER),
        NETHER_CREATE_FIRE(Binding.NETHER),
        NETHER_TARGET(Binding.NETHER),
        END_FLY(Binding.END),
        END_ATTACK(Binding.END),
        END_VERTICAL_TP(Binding.END),
        END_SURVIVE(Binding.END),
        ARCHE_ATTACK(Binding.ARCHE),
        ARCHE_MOVE(Binding.ARCHE),
        ARCHE_NODE(Binding.ARCHE);

        private final Binding binding;

        PowerToggles(Binding binding) {
            this.binding = binding;
        }

        public Binding getBinding() {
            return binding;
        }
    }

}
