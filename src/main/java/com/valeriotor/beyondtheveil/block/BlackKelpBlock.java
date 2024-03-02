package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;

public class BlackKelpBlock extends KelpBlock {
    public BlackKelpBlock(Properties p_54300_) {
        super(p_54300_);
    }

    @Override
    protected Block getBodyBlock() {
        return Registration.BLACK_KELP_PLANT.get();
    }
}
