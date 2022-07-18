package com.valeriotor.beyondtheveil.capability;

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

public class PlayerDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private PlayerData playerData = null;
    private final LazyOptional<PlayerData> opt = LazyOptional.of(this::getPlayerData);

    private PlayerData getPlayerData() {
        if (playerData == null) {
            playerData = new PlayerData();
        }
        return playerData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getPlayerData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getPlayerData().loadFromNBT(nbt);
    }
}
