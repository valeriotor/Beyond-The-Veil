package com.valeriotor.beyondtheveil.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SaplingShrubBlock extends Block {
    private static final double a = 0.0625;
    private static final VoxelShape SHAPE = Shapes.box(a * 4, 0, a * 4, a * 12, a * 7, a * 12);

    public SaplingShrubBlock(Properties pProperties) {
        super(pProperties);
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return 3;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
