package com.valeriotor.beyondtheveil.capability.research;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResearchProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ResearchData> RESEARCH = CapabilityManager.get(new CapabilityToken<>() {
    });

    private ResearchData researchData = null;
    private final LazyOptional<ResearchData> opt = LazyOptional.of(this::getResearchData);

    private ResearchData getResearchData() {
        if (researchData == null) {
            researchData = new ResearchData();
        }
        return researchData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == RESEARCH) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getResearchData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getResearchData().loadFromNBT(nbt);
    }
}
