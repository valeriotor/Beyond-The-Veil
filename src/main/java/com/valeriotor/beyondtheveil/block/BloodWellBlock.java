package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.tile.BloodWellBE;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BloodWellBlock  extends Block implements EntityBlock {

    public BloodWellBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BloodWellBE(pPos, pState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide()) {
            return (pLevel1, pPos, pState1, pBlockEntity) -> {
                if (pBlockEntity instanceof BloodWellBE be) be.tickServer();
            };
        } else {
            return (pLevel1, pPos, pState1, pBlockEntity) -> {
                if (pBlockEntity instanceof BloodWellBE be) be.tickClient();
            };
        }
    }
}
