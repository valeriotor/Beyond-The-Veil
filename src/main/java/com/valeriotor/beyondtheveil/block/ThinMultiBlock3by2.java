package com.valeriotor.beyondtheveil.block;

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

/** Can be as tall and as wide as you wish, but only 1 block "deep" (i.e. in the direction where the player is facing
 *  when placing it)
 *
 */
public abstract class ThinMultiBlock3by2 extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 2-1);
    public static final int levels = 2;
    public static final IntegerProperty SIDE = IntegerProperty.create("side", 0, 1*2);
    public static final int centerY = (2-1) / 2;
    public static final int horizontalRadius = 1;

    public ThinMultiBlock3by2(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SIDE, 1).setValue(LEVEL, centerY));
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
        return defaultBlockState().setValue(FACING, opposite).setValue(SIDE, horizontalRadius).setValue(LEVEL, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(SIDE).add(LEVEL);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockPos center = findCenter(pPos, pState);
        for (int i = -horizontalRadius; i <= horizontalRadius; i++) {
            for (int y = -centerY; y <= levels-1-centerY; y++) {
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
        int i = -pState.getValue(SIDE)+horizontalRadius;
        int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
        int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
        return pPos.offset(x, - pState.getValue(LEVEL), z);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        for (int i = -horizontalRadius; i <= horizontalRadius; i++) {
            for (int y = 0; y < levels; y++) {
                if(i == 0 && y == 0) continue;
                Direction facing = pState.getValue(FACING);
                int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
                int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
                pLevel.setBlock(pPos.offset(x, y, z), pState.setValue(SIDE, i+horizontalRadius).setValue(LEVEL, y), 3);
            }
        }
    }

    public boolean isCenter(BlockState state) {
        return state.getValue(SIDE) == horizontalRadius && state.getValue(LEVEL) == centerY;
    }

    public IntegerProperty getSideProperty() {
        return SIDE;
    }

    public IntegerProperty getLevelProperty() {
        return LEVEL;
    }

    public int getHorizontalRadius() {
        return horizontalRadius;
    }

    public int getCenterY() {
        return centerY;
    }

}
