package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.tile.DeepChestBE;
import com.valeriotor.beyondtheveil.tile.FishBarrelBE;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FishBarrelBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Shapes.box(0.125,0.0,0.125,0.875,0.875,0.875);

    public FishBarrelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return getOcclusionShape(state, p_60556_, p_60557_);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter p_60579_, BlockPos p_60580_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (pLevel.getBlockEntity(pPos) instanceof FishBarrelBE be) {
            if (be.interact(pPlayer, pHand)) {
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FishBarrelBE(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof FishBarrelBE be) {
                if (pLevel instanceof ServerLevel) {
                    double pX = pPos.getX() + 0.5;
                    double pY = pPos.getY() + 0.5;
                    double pZ = pPos.getZ() + 0.5;
                    double d0 = (double) EntityType.ITEM.getWidth();
                    double d1 = 1.0D - d0;
                    double d2 = d0 / 2.0D;
                    double d3 = Math.floor(pX) + pLevel.random.nextDouble() * d1 + d2;
                    double d4 = Math.floor(pY) + pLevel.random.nextDouble() * d1;
                    double d5 = Math.floor(pZ) + pLevel.random.nextDouble() * d1 + d2;
                    ItemEntity itementity = new ItemEntity(pLevel, d3, d4, d5, be.getStack());
                    float f = 0.05F;
                    itementity.setDeltaMovement(pLevel.random.triangle(0.0D, 0.11485000171139836D), pLevel.random.triangle(0.2D, 0.11485000171139836D), pLevel.random.triangle(0.0D, 0.11485000171139836D));
                    pLevel.addFreshEntity(itementity);
                }

                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }
}
