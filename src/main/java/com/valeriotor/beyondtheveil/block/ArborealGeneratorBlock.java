package com.valeriotor.beyondtheveil.block;

import com.google.common.collect.ImmutableMap;
import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import com.valeriotor.beyondtheveil.tile.AlembicsBE;
import com.valeriotor.beyondtheveil.tile.ArborealGeneratorBE;
import com.valeriotor.beyondtheveil.tile.FleboBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ArborealGeneratorBlock extends ThinMultiBlock1by2 implements EntityBlock {
    private static final double a = 0.0625;
    private static final VoxelShape TRUNK_AABB = Shapes.box(a * 4, 0, a * 5, a * 10, 1.0, a * 11);
    private static final VoxelShape TRUNKTOP_AABB = Shapes.box(a * 4, 0, a * 5, a * 10, a * 7, a * 11);

    public ArborealGeneratorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(getLevelProperty()) == 0 ? new ArborealGeneratorBE(pPos, pState) : null;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide()) {
            return (pLevel1, pPos, pState1, pBlockEntity) -> {
                if(pBlockEntity instanceof ArborealGeneratorBE be) be.tickServer();
            };
        }
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(getLevelProperty()) == 0 ? TRUNK_AABB : TRUNKTOP_AABB;
    }
}
