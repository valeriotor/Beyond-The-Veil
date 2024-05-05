package com.valeriotor.beyondtheveil.capability.arsenal;

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

public class TriggerDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<TriggerData> TRIGGER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private TriggerData triggerData = null;
    private final LazyOptional<TriggerData> opt = LazyOptional.of(this::getTriggerData);

    private TriggerData getTriggerData() {
        if (triggerData == null) {
            triggerData = new TriggerData();
        }
        return triggerData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TRIGGER_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getTriggerData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getTriggerData().loadFromNBT(nbt);
    }

}
