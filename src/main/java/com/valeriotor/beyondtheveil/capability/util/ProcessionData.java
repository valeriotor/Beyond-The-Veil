package com.valeriotor.beyondtheveil.capability.util;

import com.valeriotor.beyondtheveil.tile.BloodWellBE;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

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

    public void every20Ticks(ServerLevel sl) {
        if (destination != null) {
            BlockEntity blockEntity = sl.getBlockEntity(destination);
            if (!(blockEntity instanceof HeartBE) && !(blockEntity instanceof BloodWellBE)) {
                destination = null;
            }
        }
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
