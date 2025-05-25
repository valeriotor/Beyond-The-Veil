package com.valeriotor.beyondtheveil.capability.util;

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

public class ProcessionDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ProcessionData> PROCESSION_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private ProcessionData processionData = null;
    private final LazyOptional<ProcessionData> opt = LazyOptional.of(this::getProcessionData);

    private ProcessionData getProcessionData() {
        if (processionData == null) {
            processionData = new ProcessionData();
        }
        return processionData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PROCESSION_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getProcessionData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getProcessionData().loadFromNBT(nbt);
    }
}
