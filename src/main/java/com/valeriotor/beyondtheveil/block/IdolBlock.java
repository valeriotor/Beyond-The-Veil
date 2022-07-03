package com.valeriotor.beyondtheveil.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IdolBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape BASE_SHAPE = Shapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.25D, 0.8125D);
    private static final VoxelShape[] DIRECTIONAL_SHAPES = new VoxelShape[4];

    static {
        DIRECTIONAL_SHAPES[0] = Shapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.875D, 0.6875D);
        DIRECTIONAL_SHAPES[1] = Shapes.box(0.1875D, 0.0D, 0.3125D, 0.8125D, 0.875D, 0.8125D);
        DIRECTIONAL_SHAPES[2] = Shapes.box(0.1875D, 0.0D, 0.1875D, 0.6875D, 0.875D, 0.8125D);
        DIRECTIONAL_SHAPES[3] = Shapes.box(0.3125D, 0.0D, 0.1875D, 0.8125D, 0.875D, 0.8125D);

    }

    public IdolBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return getOcclusionShape(state, p_60556_, p_60557_);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter p_60579_, BlockPos p_60580_) {
        return Shapes.or(BASE_SHAPE, DIRECTIONAL_SHAPES[state.getValue(FACING).getOpposite().ordinal()-2]);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

}
