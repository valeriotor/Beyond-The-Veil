package com.valeriotor.beyondtheveil.capability.util;

import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class ProcessionData {

    private BlockPos destination;
    private boolean overriding = false;

    public void setDestination(BlockPos destination, BlockPos requester) {
        setDestination(destination, requester, false);
    }

    public void setDestination(BlockPos destination, BlockPos requester, boolean overriding) {
        if (this.destination == null || this.destination.equals(requester) || (!this.overriding && overriding)) {
            this.destination = destination;
            if (overriding) {
                this.overriding = true;
            }
        }
    }

    public BlockPos getDestination() {
        return destination;
    }

    public void tick() {

    }

    public void saveToNBT(CompoundTag compoundTag) {
        if (destination != null) {
            compoundTag.putLong("dest", destination.asLong());
            compoundTag.putBoolean("overriding", overriding);
        }
    }

    public void loadFromNBT(CompoundTag compoundTag) {
        if (compoundTag.contains("dest")) {
            destination = BlockPos.of(compoundTag.getLong("dest"));
            overriding = compoundTag.getBoolean("overriding");
        }
    }

}
