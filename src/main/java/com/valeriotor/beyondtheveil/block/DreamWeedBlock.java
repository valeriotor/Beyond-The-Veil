package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class DreamWeedBlock extends CropBlock {
    public DreamWeedBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        if (this == Registration.GHOST_WEED.get()) {
            return Registration.GHOST_WEED_SEEDS.get();
        } else if (this == Registration.REDSTONE_WEED.get()) {
            return Registration.REDSTONE_WEED_SEEDS.get();
        } else {
            return Registration.GRASS_WEED_SEEDS.get();
        }
    }
}
