package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FleboBlock extends ThinMultiBlock1by2 {
    protected static final VoxelShape SHAPE_STICK_DOWN;
    protected static final VoxelShape SHAPE_UP_X;
    protected static final VoxelShape SHAPE_UP_Z;

    static {
        double a = 0.0625;
        VoxelShape s1 = Shapes.box(7.5 * a, a, 7.5 * a, 8.5 * a, 1, 8.5 * a);
        VoxelShape s2 = Shapes.box(7.5 * a, 0, 7.5 * a, 8.5 * a, 1, 8.5 * a);

        VoxelShape s3 = Shapes.box(1.75 * a, 2.5 * a, 6.5 * a, 4.75 * a, 7.5 * a, 9.5 * a);
        s3 = Shapes.or(s3, Shapes.box(11.25 * a, 2.5 * a, 6.5 * a, 14.25 * a, 7.5 * a, 9.5 * a));


        VoxelShape s4 = Shapes.box(6.5 * a, 2.5 * a, 1.75 * a, 9.5 * a, 7.5 * a, 4.75 * a);
        s4 = Shapes.or(s4, Shapes.box(6.5 * a, 2.5 * a, 11.25 * a, 9.5 * a, 7.5 * a, 14.25 * a));

        SHAPE_STICK_DOWN = s1;
        SHAPE_UP_X = Shapes.or(s2, s3);
        SHAPE_UP_Z = Shapes.or(s2, s4);
    }


    public FleboBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(getLevelProperty()) == 0)
            return SHAPE_STICK_DOWN;
        return pState.getValue(FACING).getAxis() == Direction.Axis.Z ? SHAPE_UP_X : SHAPE_UP_Z;
    }
}
