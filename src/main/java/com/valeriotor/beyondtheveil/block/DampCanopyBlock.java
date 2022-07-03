package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DampCanopyBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty FLAT = BooleanProperty.create("flat");
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    private static final VoxelShape[] shapes = {Shapes.empty(), Shapes.empty(), Shapes.empty(), Shapes.empty(), Shapes.box(0, 0, 0, 1, 0.125D, 1)};
    private static final VoxelShape[] shapes_bent = {Shapes.empty(), Shapes.empty(), Shapes.empty(), Shapes.empty(), Shapes.empty(), Shapes.empty(), Shapes.empty(), Shapes.empty()};

    static {
        for (int i = 0; i < 8; i++) {
            shapes[0] = Shapes.or(shapes[0], Shapes.box(0, i * 0.125D, (7 - i) * 0.125D, 1.0D, (i + 1) * 0.125D, (8 - i) * 0.125D));
        }
        for (int i = 0; i < 8; i++) {
            shapes[1] = Shapes.or(shapes[1], Shapes.box(0, i * 0.125D, i * 0.125D, 1.0D, (i + 1) * 0.125D, (i + 1) * 0.125D));
        }
        for (int i = 0; i < 8; i++) {
            shapes[2] = Shapes.or(shapes[2], Shapes.box((7 - i) * 0.125D, i * 0.125D, 0, (8 - i) * 0.125D, (i + 1) * 0.125D, 1.0D));
        }
        for (int i = 0; i < 8; i++) {
            shapes[3] = Shapes.or(shapes[3], Shapes.box(i * 0.125D, i * 0.125D, 0, (i + 1) * 0.125D, (i + 1) * 0.125D, 1.0D));
        }

        for (int i = 0; i < 8; i++) {
            shapes_bent[0] = Shapes.or(shapes_bent[0], Shapes.box((7 - i) * 0.125D, i * 0.125D, (7 - i) * 0.125D, (8 - i) * 0.125D, (i + 1) * 0.125D, 1.0D));
            if (i > 0) {
                shapes_bent[0] = Shapes.or(shapes_bent[0], Shapes.box((7 - i) * 0.125D, i * 0.125D, (7 - i) * 0.125D, 1.0D, (i + 1) * 0.125D, (8 - i) * 0.125D));
            }
        }
        for (int i = 0; i < 8; i++) {
            shapes_bent[1] = Shapes.or(shapes_bent[1], Shapes.box(0, i * 0.125D, i * 0.125D, (i + 1) * 0.125D, (i + 1) * 0.125D, (i + 1) * 0.125D));
            if (i > 0) {
                shapes_bent[1] = Shapes.or(shapes_bent[1], Shapes.box(i * 0.125D, i * 0.125D, 0, (i + 1) * 0.125D, (i + 1) * 0.125D, i * 0.125D));
            }
        }
        for (int i = 0; i < 8; i++) {
            shapes_bent[2] = Shapes.or(shapes_bent[2], Shapes.box((7 - i) * 0.125D, i * 0.125D, 0, (8 - i) * 0.125D, (i + 1) * 0.125D, (i + 1) * 0.125D));
            if (i > 0) {
                shapes_bent[2] = Shapes.or(shapes_bent[2], Shapes.box((8 - i) * 0.125D, i * 0.125D, i * 0.125D, 1.0D, (i + 1) * 0.125D, (i + 1) * 0.125D));
            }
        }
        for (int i = 0; i < 8; i++) {
            shapes_bent[3] = Shapes.or(shapes_bent[3], Shapes.box(0, i * 0.125D, (7 - i) * 0.125D, (i + 1) * 0.125D, (i + 1) * 0.125D, (8 - i) * 0.125D));
            if (i > 0) {
                shapes_bent[3] = Shapes.or(shapes_bent[3], Shapes.box(i * 0.125D, i * 0.125D, (8 - i) * 0.125D, (i + 1) * 0.125D, (i + 1) * 0.125D, 1.0D));
            }
        }

        for (int i = 0; i < 8; i++) {
            shapes_bent[4] = Shapes.or(shapes_bent[4], Shapes.box((7 - i) * 0.125D, (7 - i) * 0.125D, (7 - i) * 0.125D, (8 - i) * 0.125D, (8 - i) * 0.125D, 1.0D));
            if (i > 0) {
                shapes_bent[4] = Shapes.or(shapes_bent[4], Shapes.box((7 - i) * 0.125D, (7 - i) * 0.125D, (7 - i) * 0.125D, 1.0D, (8 - i) * 0.125D, (8 - i) * 0.125D));
            }
        }
        for (int i = 0; i < 8; i++) {
            shapes_bent[5] = Shapes.or(shapes_bent[5], Shapes.box(0, (7 - i) * 0.125D, i * 0.125D, (i + 1) * 0.125D, (8 - i) * 0.125D, (i + 1) * 0.125D));
            if (i > 0) {
                shapes_bent[5] = Shapes.or(shapes_bent[5], Shapes.box(i * 0.125D, (7 - i) * 0.125D, 0, (i + 1) * 0.125D, (8 - i) * 0.125D, i * 0.125D));
            }
        }
        for (int i = 0; i < 8; i++) {
            shapes_bent[6] = Shapes.or(shapes_bent[6], Shapes.box((7 - i) * 0.125D, (7 - i) * 0.125D, 0, (8 - i) * 0.125D, (8 - i) * 0.125D, (i + 1) * 0.125D));
            if (i > 0) {
                shapes_bent[6] = Shapes.or(shapes_bent[6], Shapes.box((8 - i) * 0.125D, (7 - i) * 0.125D, i * 0.125D, 1.0D, (8 - i) * 0.125D, (i + 1) * 0.125D));
            }
        }
        for (int i = 0; i < 8; i++) {
            shapes_bent[7] = Shapes.or(shapes_bent[7], Shapes.box(0, (7 - i) * 0.125D, (7 - i) * 0.125D, (i + 1) * 0.125D, (8 - i) * 0.125D, (8 - i) * 0.125D));
            if (i > 0) {
                shapes_bent[7] = Shapes.or(shapes_bent[7], Shapes.box(i * 0.125D, (7 - i) * 0.125D, (8 - i) * 0.125D, (i + 1) * 0.125D, (8 - i) * 0.125D, 1.0D));
            }
        }

    }

    public DampCanopyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FLAT, false).setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return getOcclusionShape(state, p_60556_, p_60557_);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter p_60579_, BlockPos p_60580_) {
        if (state.getValue(FLAT)) return shapes[4];
        int permutation = (9 - state.getValue(FACING).ordinal()) & 3;
        if (permutation <= 1) permutation = 1 - permutation;
        return switch (state.getValue(SHAPE)) {
            case STRAIGHT -> shapes[state.getValue(FACING).ordinal() - 2];
            case INNER_LEFT -> shapes_bent[state.getValue(FACING).ordinal() - 2];
            case INNER_RIGHT -> shapes_bent[permutation];
            case OUTER_LEFT -> shapes_bent[(state.getValue(FACING).getOpposite().ordinal() - 2) + 4];
            case OUTER_RIGHT -> shapes_bent[(permutation ^ 1) + 4];
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FLAT, SHAPE, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean sneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        BlockState state = this.defaultBlockState();
        if (clickedFace == Direction.UP) {
            state = state.setValue(FLAT, !sneaking);
        } else {
            state = state.setValue(FLAT, sneaking);
        }
        state = state.setValue(FACING, context.getHorizontalDirection());
        return state.setValue(SHAPE, getStairsShape(state, context.getLevel(), context.getClickedPos())).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState p_60543_, LevelAccessor level, BlockPos pos, BlockPos p_60546_) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return state.setValue(SHAPE, getStairsShape(state, level, pos));
    }

    private StairsShape getStairsShape(BlockState blockState, BlockGetter level, BlockPos blockPos) {
        Direction direction = blockState.getValue(FACING);
        BlockState blockstate = level.getBlockState(blockPos.relative(direction));
        if (isCanopy(blockstate)) {
            Direction direction1 = blockstate.getValue(FACING);
            if (direction1.getAxis() != blockState.getValue(FACING).getAxis() && canTakeShape(blockState, level, blockPos, direction1.getOpposite())) {
                if (direction1 == direction.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT;
                }

                return StairsShape.OUTER_RIGHT;
            }
        }

        BlockState blockstate1 = level.getBlockState(blockPos.relative(direction.getOpposite()));
        if (isCanopy(blockstate1)) {
            Direction direction2 = blockstate1.getValue(FACING);
            if (direction2.getAxis() != blockState.getValue(FACING).getAxis() && canTakeShape(blockState, level, blockPos, direction2)) {
                if (direction2 == direction.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT;
                }

                return StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private boolean canTakeShape(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction direction) {
        BlockState blockstate = level.getBlockState(blockPos.relative(direction));
        return !isCanopy(blockstate) || blockstate.getValue(FACING) != blockState.getValue(FACING);
    }

    private boolean isCanopy(BlockState blockstate) {
        return blockstate.getBlock() == this || blockstate.getBlock() == Registration.DAMP_FILLED_CANOPY.get();
    }

    public FluidState getFluidState(BlockState p_56969_) {
        return p_56969_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56969_);
    }

}
