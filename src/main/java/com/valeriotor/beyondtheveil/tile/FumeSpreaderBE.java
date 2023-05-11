package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FumeSpreaderBE extends BlockEntity {

    private Memory storedMemory;
    public FumeSpreaderBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.FUME_SPREADER_BE.get(), pWorldPosition, pBlockState);
    }




}
