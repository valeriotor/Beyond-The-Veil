package com.valeriotor.beyondtheveil.capability.crossync;

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

public class CrossSyncDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<CrossSyncData> CROSS_SYNC_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private CrossSyncData crossSyncData = null;
    private final LazyOptional<CrossSyncData> opt = LazyOptional.of(this::getCrossSyncData);

    private CrossSyncData getCrossSyncData() {
        if (crossSyncData == null) {
            crossSyncData = new CrossSyncData();
        }
        return crossSyncData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CROSS_SYNC_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getCrossSyncData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getCrossSyncData().loadFromNBT(nbt);
    }

}
