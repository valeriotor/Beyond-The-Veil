package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.util.multiblocks.MultiblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BloodWellBE extends BlockEntity {

    private int counter;

    public BloodWellBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.BLOOD_WELL_BE.get(), pWorldPosition, pBlockState);
    }

    public void tickServer() {
        counter++;
        if ((counter & 15) == 0) {
            if (!MultiblockRegistry.BLOOD_WELL_COMPLETE.checksOutBottomCenter(level, worldPosition.below())) {
                level.setBlock(worldPosition, Registration.BLOOD_BRICK.get().defaultBlockState(), 3);
                return;
            }
        }
    }

}
