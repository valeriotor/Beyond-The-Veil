package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;

public class BlackKelpPlantBlock extends KelpPlantBlock {

    public BlackKelpPlantBlock(Properties p_54323_) {
        super(p_54323_);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return Registration.BLACK_KELP.get();
    }
}
