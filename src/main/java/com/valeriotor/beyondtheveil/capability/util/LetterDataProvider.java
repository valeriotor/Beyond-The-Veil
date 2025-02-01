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

public class LetterDataProvider  implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<LetterData> LETTER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private LetterData letterData = null;
    private final LazyOptional<LetterData> opt = LazyOptional.of(this::getLetterData);

    private LetterData getLetterData() {
        if (letterData == null) {
            letterData = new LetterData();
        }
        return letterData;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == LETTER_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getLetterData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getLetterData().loadFromNBT(nbt);
    }
}
