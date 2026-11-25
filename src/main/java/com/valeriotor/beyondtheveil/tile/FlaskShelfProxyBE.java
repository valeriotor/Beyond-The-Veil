package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlaskShelfProxyBE extends BlockEntity {

    public FlaskShelfProxyBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.FLASK_SHELF_PROXY_BE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        BlockState blockState = getBlockState();
        if (blockState.hasProperty(Registration.FLASK_SHELF.get().getSideProperty())) {
            BlockPos center = Registration.FLASK_SHELF.get().findBEPos(worldPosition, blockState);
            if (level != null && level.getBlockEntity(center) instanceof FlaskShelfBE be) {
                return be.getCapability(cap, side);
            }

        }
        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        BlockState blockState = getBlockState();
        if (blockState.hasProperty(Registration.FLASK_SHELF.get().getSideProperty())) {
            BlockPos center = Registration.FLASK_SHELF.get().findBEPos(worldPosition, blockState);
            if (level != null && level.getBlockEntity(center) instanceof FlaskShelfBE be) {
                return be.getCapability(cap);
            }
        }
        return super.getCapability(cap);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.remove("ForgeCaps"); // TODO test this out
    }
}
