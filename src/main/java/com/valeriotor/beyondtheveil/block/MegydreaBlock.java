package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MegydreaBlock extends ThinMultiBlock1by2 {

    protected static final VoxelShape SHAPE_LOWER;
    protected static final VoxelShape SHAPE_UPPER;

    static {
        double a = 0.0625;
        SHAPE_LOWER = Shapes.box(3*a, 5*a, 3*a, 13*a, 1, 13*a);
        SHAPE_UPPER = Shapes.box(3*a, 0, 3*a, 13*a, 11*a, 13*a);
    }

    public MegydreaBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getLevelValue(pState) == 0 ? SHAPE_LOWER : SHAPE_UPPER;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        Direction opposite = context.getHorizontalDirection().getOpposite();
        if (!level.getBlockState(blockpos).canBeReplaced(context) || !level.getBlockState(blockpos.below()).canBeReplaced(context)) {
            return null;
        }
        BlockState blockState = defaultBlockState().setValue(FACING, opposite);
        blockState = blockState.setValue(getLevelProperty(), 1);

        return blockState;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockState blockState = pState.setValue(getLevelProperty(), 0);
        pLevel.setBlock(pPos.below(), blockState, 3);

    }
}
