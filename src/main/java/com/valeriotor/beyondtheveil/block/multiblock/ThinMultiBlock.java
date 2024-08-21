package com.valeriotor.beyondtheveil.block.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public abstract class ThinMultiBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    public final int levels;
    public final int centerY;
    public final int horizontalRadius;

    public ThinMultiBlock(Properties pProperties, int levels, int centerY, int horizontalRadius) {
        super(pProperties);
        this.levels = levels;
        this.centerY = centerY;
        this.horizontalRadius = horizontalRadius;

        BlockState blockState = this.stateDefinition.any().setValue(FACING, Direction.NORTH);
        if (getLevelProperty() != null) {
            blockState = blockState.setValue(getLevelProperty(), centerY);
        }
        if (getSideProperty() != null) {
            blockState = blockState.setValue(getSideProperty(), horizontalRadius);
        }

        this.registerDefaultState(blockState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        Direction opposite = context.getHorizontalDirection().getOpposite();
        for (int i = -horizontalRadius; i <= horizontalRadius; i++) {
            for (int y = 0; y < levels; y++) {
                int x = opposite.getAxis() == Direction.Axis.X ? 0 : i;
                int z = opposite.getAxis() == Direction.Axis.Z ? 0 : i;
                if (!level.getBlockState(blockpos.offset(x, y, z)).canBeReplaced(context)) {
                    return null;
                }
            }
        }
        BlockState blockState = defaultBlockState().setValue(FACING, opposite);
        if (getLevelProperty() != null) {
            blockState = blockState.setValue(getLevelProperty(), 0);
        }
        if (getSideProperty() != null) {
            blockState = blockState.setValue(getSideProperty(), horizontalRadius);
        }
        return blockState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        if (getLevelProperty() != null) {
            builder.add(getLevelProperty());
        }
        if (getSideProperty() != null) {
            builder.add(getSideProperty());
        }
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockPos center = findCenter(pPos, pState);
        for (int i = -horizontalRadius; i <= horizontalRadius; i++) {
            for (int y = -centerY; y <= levels - 1 - centerY; y++) {
                int x = pState.getValue(FACING).getAxis() == Direction.Axis.X ? 0 : i;
                int z = pState.getValue(FACING).getAxis() == Direction.Axis.Z ? 0 : i;
                BlockPos neighbourPos = center.offset(x, y, z);
                BlockState neighbour = pLevel.getBlockState(neighbourPos);
                if (neighbour.is(this) && neighbour.getValue(FACING) == pState.getValue(FACING)) {
                    pLevel.setBlock(neighbourPos, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, neighbourPos, Block.getId(neighbour));
                }
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public BlockPos findCenter(BlockPos pPos, BlockState pState) {
        Direction facing = pState.getValue(FACING);
        int i = getSideProperty() != null ? -pState.getValue(getSideProperty()) + horizontalRadius : 0;
        int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
        int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
        return pPos.offset(x, getLevelProperty() != null ? -pState.getValue(getLevelProperty()) : 0, z);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for (int i = -horizontalRadius; i <= horizontalRadius; i++) {
            for (int y = 0; y < levels; y++) {
                if (i == 0 && y == 0) continue;
                Direction facing = pState.getValue(FACING);
                int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
                int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
                BlockState blockState = pState;
                if (getLevelProperty() != null) {
                    blockState = blockState.setValue(getLevelProperty(), y);
                }
                if (getSideProperty() != null) {
                    blockState = blockState.setValue(getSideProperty(), i + horizontalRadius);
                }
                pLevel.setBlock(pPos.offset(x, y, z), blockState, 3);
            }
        }
    }

    public boolean isCenter(BlockState state) {
        return (getSideProperty() == null || state.getValue(getSideProperty()) == horizontalRadius) && (getLevelProperty() == null || state.getValue(getLevelProperty()) == centerY);
    }

    public int getHorizontalRadius() {
        return horizontalRadius;
    }

    public int getCenterY() {
        return centerY;
    }

    public abstract IntegerProperty getSideProperty();

    public abstract IntegerProperty getLevelProperty();
}
