package com.valeriotor.beyondtheveil.capability.surgery;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
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

public class ConvalescentDataProvider  implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ConvalescentData> CONVALESCENT_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private ConvalescentData convalescentData = null;
    private final LazyOptional<ConvalescentData> opt = LazyOptional.of(this::getConvalescentData);

    private ConvalescentData getConvalescentData() {
        if (convalescentData == null) {
            convalescentData = new ConvalescentData();
        }
        return convalescentData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CONVALESCENT_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getConvalescentData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getConvalescentData().loadFromNBT(nbt);
    }
}
