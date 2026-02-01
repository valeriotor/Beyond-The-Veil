package com.valeriotor.beyondtheveil.rituals.bindings;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class BindingData {


    private final Binding binding;
    private int energy;
    private BlockPos overworldPos1;
    private BlockPos overworldPos2;
    private boolean blockBreakingMode;
    private boolean instantKill;
    private ArcheDamageType selectedType;

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
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
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


}
