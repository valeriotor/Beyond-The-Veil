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

public class PlayerTimerDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerTimerData> PLAYER_TIMER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private PlayerTimerData playerTimerData = null;
    private final LazyOptional<PlayerTimerData> opt = LazyOptional.of(this::getPlayerTimerData);

    private PlayerTimerData getPlayerTimerData() {
        if (playerTimerData == null) {
            playerTimerData = new PlayerTimerData();
        }
        return playerTimerData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_TIMER_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getPlayerTimerData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getPlayerTimerData().loadFromNBT(nbt);
    }
}
