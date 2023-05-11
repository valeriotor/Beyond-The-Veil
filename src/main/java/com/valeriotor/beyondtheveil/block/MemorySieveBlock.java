package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.tile.MemorySieveBE;
import com.valeriotor.beyondtheveil.tile.SlugBaitBE;
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

public class MemorySieveBlock extends Block implements EntityBlock {

    private static final double a = 0.0625;
    private static final VoxelShape BBOX = Shapes.box(a * 7, 0, a * 7, a * 9, a * 12, a * 9);;
    private static final VoxelShape BBOX2 = Shapes.box(a * 2, a * 13, a * 2, a * 14, a * 14, a * 14);;
    private static final VoxelShape BBOX3 = Shapes.box(a * 2, 0, a * 2, a * 14, a * 14, a * 14);


    public MemorySieveBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if (entity instanceof MemorySieveBE sieve) {
            boolean success = sieve.getOrAddItem(pPlayer, stack);
            return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.or(BBOX, BBOX2);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_60578_, BlockGetter p_60579_, BlockPos p_60580_) {
        return Shapes.or(BBOX, BBOX2);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MemorySieveBE(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide()) {
            return null;
        }
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if(pBlockEntity instanceof MemorySieveBE be) be.tickClient();
        };
    }

}
