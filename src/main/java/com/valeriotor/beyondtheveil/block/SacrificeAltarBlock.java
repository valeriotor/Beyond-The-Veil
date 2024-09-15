package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.multiblock.FullMultiBlock;
import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.tile.SacrificeAltarBE;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SacrificeAltarBlock extends FullMultiBlock implements EntityBlock {

    public static final IntegerProperty SIDE = IntegerProperty.create("side", 0, 2);
    public static final IntegerProperty DEPTH = IntegerProperty.create("depth", 0, 1);

    public SacrificeAltarBlock(Properties properties) {
        super(properties, 1, 0, 1, 2);
    }

    @Override
    public IntegerProperty getSideProperty() {
        return SIDE;
    }

    @Override
    public IntegerProperty getLevelProperty() {
        return null;
    }

    @Override
    public IntegerProperty getDepthProperty() {
        return DEPTH;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return isCenter(pState) ? new SacrificeAltarBE(pPos, pState) : null;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        BlockPos centerPos = findCenter(pPos, pState);
        BlockEntity entity = pLevel.getBlockEntity(centerPos);
        if (entity instanceof SacrificeAltarBE sacrificeAltarBE) {
            boolean success = sacrificeAltarBE.interact(pPlayer, stack, pHand);
            return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}
