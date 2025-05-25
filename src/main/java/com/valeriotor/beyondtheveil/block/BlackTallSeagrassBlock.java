package com.valeriotor.beyondtheveil.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlackTallSeagrassBlock extends TallSeagrassBlock {
    public BlackTallSeagrassBlock(Properties p_154745_) {
        super(p_154745_);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter p_154749_, BlockPos p_154750_, BlockState p_154751_) {
        return new ItemStack(Blocks.SEAGRASS);
    }
}
