package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.tile.BloodBasinBE;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BloodBasinBlock extends Block implements EntityBlock {


    private static final double a = 0.0625;
    private static final VoxelShape BBOX = Shapes.box(a * 5, 0, a * 5, a * 11, a * 4, a * 11);
    private static final VoxelShape BBOX2 = Shapes.box(a * 6, a * 4, a * 6, a * 10, a * 10, a * 10);
    private static final VoxelShape BBOX3 = Shapes.box(a * 1, a*12, a * 1, a * 15, a * 14, a * 15);
    private static final VoxelShape BBOX4 = Shapes.box(0, a*14, 0, 1, a * 15, 1);

    public BloodBasinBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        ItemStack stack = pPlayer.getItemInHand(pHand);
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if (entity instanceof BloodBasinBE sieve) {
            sieve.interact(pPlayer, pHand, stack);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.or(BBOX, BBOX2, BBOX3, BBOX4);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return Shapes.or(BBOX, BBOX2, BBOX3, BBOX4);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BloodBasinBE(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide()) {
            return null;
        }
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if(pBlockEntity instanceof BloodBasinBE be) be.tickClient();
        };
    }
}
