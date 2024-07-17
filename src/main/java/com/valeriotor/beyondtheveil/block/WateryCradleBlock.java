package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock3by1;
import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock3by2;
import com.valeriotor.beyondtheveil.tile.SlugBaitBE;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.tile.WateryCradleBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WateryCradleBlock extends ThinMultiBlock3by1 implements EntityBlock {
    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1, 0.0625 * 15, 1);
    private static final VoxelShape NORTH = Shapes.box(0, 0, 0, 1, 0.0625 * 15, 0.0625);
    private static final VoxelShape SOUTH = Shapes.box(0, 0, 0.0625 * 15, 1, 0.0625 * 15, 1);
    private static final VoxelShape EAST = Shapes.box(0, 0, 0, 0.0625, 0.0625 * 15, 1);
    private static final VoxelShape WEST = Shapes.box(0.0625 * 15, 0, 0, 1, 0.0625 * 15, 1);
    private static final VoxelShape UP = Shapes.box(0, 0.0625 * 15, 0, 1, 1, 1);
    private static final VoxelShape DOWN = Shapes.box(0, 0, 0, 1, 0.0625, 1);
    private static final VoxelShape SHAPE_MID_X = Shapes.or(UP, DOWN, NORTH, SOUTH);
    private static final VoxelShape SHAPE_MID_Z = Shapes.or(UP, DOWN, EAST, WEST);
    private static final VoxelShape SHAPE_CLOSED_WEST = Shapes.or(UP, DOWN, NORTH, SOUTH, WEST);
    private static final VoxelShape SHAPE_CLOSED_NORTH = Shapes.or(UP, DOWN, EAST, WEST, NORTH);
    private static final VoxelShape SHAPE_CLOSED_EAST = Shapes.or(UP, DOWN, NORTH, SOUTH, EAST);
    private static final VoxelShape SHAPE_CLOSED_SOUTH = Shapes.or(UP, DOWN, EAST, WEST, SOUTH);

    public WateryCradleBlock(Properties properties) {
        super(BlockBehaviour.Properties.of().strength(5.0F, 7.0F).noOcclusion());
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        Direction facing = pState.getValue(FACING);
        if (isCenter(pState)) {
            if (facing.getAxis() == Direction.Axis.X) {
                return SHAPE_MID_Z;
            }
            return SHAPE_MID_X;
        }
        int side = pState.getValue(getSideProperty());
        if (side == 0) {
            return switch (facing) {
                case NORTH -> SHAPE_CLOSED_WEST;
                case SOUTH -> SHAPE_CLOSED_EAST;
                case WEST -> SHAPE_CLOSED_NORTH;
                case EAST -> SHAPE_CLOSED_SOUTH;
                default -> throw new IllegalStateException("Unexpected value: " + facing);

            };
        } else {
            return switch (facing) {
                case NORTH -> SHAPE_CLOSED_EAST;
                case SOUTH -> SHAPE_CLOSED_WEST;
                case WEST -> SHAPE_CLOSED_SOUTH;
                case EAST -> SHAPE_CLOSED_NORTH;
                default -> throw new IllegalStateException("Unexpected value: " + facing);
            };
        }
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.getItem() == Registration.SYRINGE.get()) {
            return InteractionResult.SUCCESS;
        }
        BlockPos centerPos = findCenter(pPos, pState);
        BlockEntity entity = pLevel.getBlockEntity(centerPos);
        if (entity instanceof SurgicalBE surgicalBE) {
            boolean success = surgicalBE.interact(pPlayer, stack, pHand);
            return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return isCenter(pState) ? new WateryCradleBE(pPos, pState) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide()) {
            return (pLevel1, pPos, pState1, pBlockEntity) -> {
                if(pBlockEntity instanceof WateryCradleBE) ((WateryCradleBE) pBlockEntity).tickServer();
            };
        }
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if(pBlockEntity instanceof WateryCradleBE) ((WateryCradleBE) pBlockEntity).tickClient();
        };
    }
}
