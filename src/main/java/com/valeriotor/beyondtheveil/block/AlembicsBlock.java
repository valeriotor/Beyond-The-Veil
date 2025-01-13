package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock3by1;
import com.valeriotor.beyondtheveil.tile.AlembicsBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AlembicsBlock extends ThinMultiBlock3by1 implements EntityBlock {


    private static final VoxelShape BASE_SHAPE = Shapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.25D, 0.8125D);
    private static final double a = 0.0625;
    private static final VoxelShape[][] SHAPES = new VoxelShape[3][4];

    static {
        VoxelShape[] ALEMBIC1_1 = new VoxelShape[4];
        VoxelShape[] ALEMBIC1_2 = new VoxelShape[4];
        VoxelShape[] ALEMBIC1_3 = new VoxelShape[4];
        VoxelShape[] SOLID_1 = new VoxelShape[4];
        VoxelShape[] SOLID_2 = new VoxelShape[4];
        VoxelShape[] SOLID_3 = new VoxelShape[4];
        VoxelShape[] ALEMBIC2_1 = new VoxelShape[4];
        VoxelShape[] ALEMBIC2_2 = new VoxelShape[4];
        VoxelShape[] ALEMBIC2_3 = new VoxelShape[4];

        ALEMBIC1_1[0] = Shapes.box(0.5 - 3 * a, 0, 1 - 8 * a, 0.5 + 3 * a, 1 - a, 1 - 2 * a);
        ALEMBIC1_1[1] = Shapes.box(2 * a, 0, 0.5 - 3 * a, 8 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC1_1[2] = Shapes.box(0.5 - 3 * a, 0, 2 * a, 0.5 + 3 * a, 1 - a, 8 * a);
        ALEMBIC1_1[3] = Shapes.box(1 - 8 * a, 0, 0.5 - 3 * a, 1 - 2 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC1_2[0] = Shapes.box(0.5 - 3 * a, 0, 2 - 8 * a, 0.5 + 3 * a, 1 - a, 2 - 2 * a);
        ALEMBIC1_2[1] = Shapes.box(-1 + 2 * a, 0, 0.5 - 3 * a, -1 + 8 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC1_2[2] = Shapes.box(0.5 - 3 * a, 0, -1 + 2 * a, 0.5 + 3 * a, 1 - a, -1 + 8 * a);
        ALEMBIC1_2[3] = Shapes.box(2 - 8 * a, 0, 0.5 - 3 * a, 2 - 2 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC1_3[0] = Shapes.box(0.5 - 3 * a, 0, 3 - 8 * a, 0.5 + 3 * a, 1 - a, 3 - 2 * a);
        ALEMBIC1_3[1] = Shapes.box(-2 + 2 * a, 0, 0.5 - 3 * a, -2 + 8 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC1_3[2] = Shapes.box(0.5 - 3 * a, 0, -2 + 2 * a, 0.5 + 3 * a, 1 - a, -2 + 8 * a);
        ALEMBIC1_3[3] = Shapes.box(3 - 8 * a, 0, 0.5 - 3 * a, 3 - 2 * a, 1 - a, 0.5 + 3 * a);

        SOLID_1[0] = Shapes.box(0.5 - 3 * a, 0, -5 * a, 0.5 + 3 * a, 10 * a, 1 * a);
        SOLID_1[1] = Shapes.box(1 - 1 * a, 0, 0.5 - 3 * a, 1 + 5 * a, 10 * a, 0.5 + 3 * a);
        SOLID_1[2] = Shapes.box(0.5 - 3 * a, 0, 1 - 1 * a, 0.5 + 3 * a, 10 * a, 1 + 5 * a);
        SOLID_1[3] = Shapes.box(-5 * a, 0, 0.5 - 3 * a, 1 * a, 10 * a, 0.5 + 3 * a);
        SOLID_2[0] = Shapes.box(0.5 - 3 * a, 0, 1 - 5 * a, 0.5 + 3 * a, 10 * a, 1 + 1 * a);
        SOLID_2[1] = Shapes.box(-1 * a, 0, 0.5 - 3 * a, 5 * a, 10 * a, 0.5 + 3 * a);
        SOLID_2[2] = Shapes.box(0.5 - 3 * a, 0, -1 * a, 0.5 + 3 * a, 10 * a, 5 * a);
        SOLID_2[3] = Shapes.box(1 - 5 * a, 0, 0.5 - 3 * a, 1 + 1 * a, 10 * a, 0.5 + 3 * a);
        SOLID_3[0] = Shapes.box(0.5 - 3 * a, 0, 2 - 5 * a, 0.5 + 3 * a, 10 * a, 2 + 1 * a);
        SOLID_3[1] = Shapes.box(-1 - 1 * a, 0, 0.5 - 3 * a, -1 + 5 * a, 10 * a, 0.5 + 3 * a);
        SOLID_3[2] = Shapes.box(0.5 - 3 * a, 0, -1 - 1 * a, 0.5 + 3 * a, 10 * a, -1 + 5 * a);
        SOLID_3[3] = Shapes.box(2 - 5 * a, 0, 0.5 - 3 * a, 2 + 1 * a, 10 * a, 0.5 + 3 * a);

        ALEMBIC2_1[0] = Shapes.box(0.5 - 3 * a, 0, -1 - 2 * a, 0.5 + 3 * a, 1 - a, -1 + 4 * a);
        ALEMBIC2_1[1] = Shapes.box(2 - 4 * a, 0, 0.5 - 3 * a, 2 + 2 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC2_1[2] = Shapes.box(0.5 - 3 * a, 0, 2 - 4 * a, 0.5 + 3 * a, 1 - a, 2 + 2 * a);
        ALEMBIC2_1[3] = Shapes.box(-1 - 2 * a, 0, 0.5 - 3 * a, -14 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC2_2[0] = Shapes.box(0.5 - 3 * a, 0, -2 * a, 0.5 + 3 * a, 1 - a, 4 * a);
        ALEMBIC2_2[1] = Shapes.box(1 - 4 * a, 0, 0.5 - 3 * a, 1 + 2 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC2_2[2] = Shapes.box(0.5 - 3 * a, 0, 1 - 4 * a, 0.5 + 3 * a, 1 - a, 1 + 2 * a);
        ALEMBIC2_2[3] = Shapes.box(-2 * a, 0, 0.5 - 3 * a, 4 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC2_3[0] = Shapes.box(0.5 - 3 * a, 0, 1 - 2 * a, 0.5 + 3 * a, 1 - a, 1 + 4 * a);
        ALEMBIC2_3[1] = Shapes.box(-4 * a, 0, 0.5 - 3 * a, 2 * a, 1 - a, 0.5 + 3 * a);
        ALEMBIC2_3[2] = Shapes.box(0.5 - 3 * a, 0, -4 * a, 0.5 + 3 * a, 1 - a, 2 * a);
        ALEMBIC2_3[3] = Shapes.box(1 - 2 * a, 0, 0.5 - 3 * a, 1 + 4 * a, 1 - a, 0.5 + 3 * a);

        SHAPES[0][0] = Shapes.or(ALEMBIC1_1[0], SOLID_1[0], ALEMBIC2_1[0]);
        SHAPES[0][1] = Shapes.or(ALEMBIC1_1[1], SOLID_1[1], ALEMBIC2_1[1]);
        SHAPES[0][2] = Shapes.or(ALEMBIC1_1[2], SOLID_1[2], ALEMBIC2_1[2]);
        SHAPES[0][3] = Shapes.or(ALEMBIC1_1[3], SOLID_1[3], ALEMBIC2_1[3]);

        SHAPES[1][0] = Shapes.or(ALEMBIC1_2[0], SOLID_2[0], ALEMBIC2_2[0]);
        SHAPES[1][1] = Shapes.or(ALEMBIC1_2[1], SOLID_2[1], ALEMBIC2_2[1]);
        SHAPES[1][2] = Shapes.or(ALEMBIC1_2[2], SOLID_2[2], ALEMBIC2_2[2]);
        SHAPES[1][3] = Shapes.or(ALEMBIC1_2[3], SOLID_2[3], ALEMBIC2_2[3]);

        SHAPES[2][0] = Shapes.or(ALEMBIC1_3[0], SOLID_3[0], ALEMBIC2_3[0]);
        SHAPES[2][1] = Shapes.or(ALEMBIC1_3[1], SOLID_3[1], ALEMBIC2_3[1]);
        SHAPES[2][2] = Shapes.or(ALEMBIC1_3[2], SOLID_3[2], ALEMBIC2_3[2]);
        SHAPES[2][3] = Shapes.or(ALEMBIC1_3[3], SOLID_3[3], ALEMBIC2_3[3]);

    }

    public AlembicsBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return getOcclusionShape(state, p_60556_, p_60557_);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter p_60579_, BlockPos p_60580_) {
        return SHAPES[state.getValue(getSideProperty())][(state.getValue(FACING).get2DDataValue() + 1) & 3];
    }

    @Override
    public InteractionResult use(BlockState state, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        BlockPos centerPos = findCenter(pPos, state);
        Direction facing = state.getValue(FACING);
        int hit = getHitAlembic(pHit, centerPos, facing);
        if (pLevel.getBlockEntity(centerPos) instanceof AlembicsBE be) {
            return be.interactServer(pPlayer, pHand, hit) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        return super.use(state, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public static int getHitAlembic(BlockHitResult pHit, BlockPos centerPos, Direction facing) {
        double distanceFromStart = switch (facing) {
            case NORTH -> -(pHit.getLocation().x - (centerPos.getX() + 1)) + 1;
            case SOUTH -> pHit.getLocation().x - centerPos.getX() + 1;
            case WEST -> pHit.getLocation().z - centerPos.getZ() + 1;
            case EAST -> -(pHit.getLocation().z - (centerPos.getZ() + 1)) + 1;
            default -> throw new IllegalStateException("Unexpected value: " + facing);
        };
        int hit;
        if (distanceFromStart < 9 * a) {
            hit = 0;
        } else if (distanceFromStart < 1 + 6 * a) {
            hit = 1;
        } else if (distanceFromStart < 2 + 3 * a) {
            hit = 2;
        } else {
            hit = 3;
        }
        return hit;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AlembicsBE(pPos, pState);
    }
}
