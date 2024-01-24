package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.tile.WateryCradleBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SurgeryBedBlock  extends ThinMultiBlock3by2 implements EntityBlock {
    protected static final VoxelShape UPPER_LEVEL = Shapes.box(0, 0, 0, 1, 0.0625, 1);

    public SurgeryBedBlock(Properties pProperties) {
        super(pProperties.noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(LEVEL) == 1 ? UPPER_LEVEL : Shapes.block();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return isCenter(pState) ? new SurgeryBedBE(pPos, pState) : null;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.getItem() == Registration.SYRINGE.get()) { // Handled by SyringeItem
            return InteractionResult.SUCCESS;
        }
        BlockPos centerPos = findCenter(pPos, pState);
        BlockEntity entity = pLevel.getBlockEntity(centerPos);
        if (entity instanceof SurgeryBedBE surgicalBE) {
            boolean success = surgicalBE.interact(pPlayer, stack, pHand);
            return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}
