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

public class DialogueDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<DialogueData> DIALOGUE_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private DialogueData dialogueData = null;
    private final LazyOptional<DialogueData> opt = LazyOptional.of(this::getDialogueData);

    private DialogueData getDialogueData() {
        if (dialogueData == null) {
            dialogueData = new DialogueData();
        }
        return dialogueData;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == DIALOGUE_DATA) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        getDialogueData().saveToNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getDialogueData().loadFromNBT(nbt);
    }
}
