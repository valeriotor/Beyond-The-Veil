package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HeartBE extends BlockEntity {

    private int counter = 0;

    public HeartBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.HEART_BE.get(), pWorldPosition, pBlockState);
    }

    public void tickServer() {
        counter++;
        if(counter > Integer.MAX_VALUE/2) counter = 0;

    }


    public void tickClient() {
        counter++;
        if (counter >= 30) {
            counter = 0;
        }

    }

    public int getCounter() {
        return counter;
    }
}
