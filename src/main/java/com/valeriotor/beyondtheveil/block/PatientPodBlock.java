package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import com.valeriotor.beyondtheveil.tile.PatientPodBE;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PatientPodBlock extends ThinMultiBlock1by2 implements EntityBlock {
    protected static final VoxelShape SHAPE;

    static {
        double a = 0.0625;
        SHAPE = Shapes.box(a, 0, a, 1-a, 1, 1-a);
    }

    public PatientPodBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(getLevelProperty()) == 0 ? new PatientPodBE(pPos, pState) : null;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (itemInHand.isEmpty()) {
            BlockPos centerPos = findCenter(pPos, pState);
            if (pLevel.getBlockEntity(centerPos) instanceof PatientPodBE be) {
                return be.interact(pPlayer, itemInHand, pHand) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (pLevel instanceof ServerLevel sl) {
            LifeEconomyData.getInstance(sl).addEmptyPod(pPos);
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        if (pLevel instanceof ServerLevel sl && pState.getValue(getLevelProperty()) == 0) {
            LifeEconomyData.getInstance(sl).removePod(pPos);
        }
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }
}
