package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlembicsProxyBE extends BlockEntity {

    public AlembicsProxyBE(BlockPos pPos, BlockState pBlockState) {
        super(BTVBlockEntities.ALEMBICS_PROXY_BE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        BlockState blockState = getBlockState();
        if (blockState.hasProperty(Registration.ALEMBICS.get().getSideProperty())) {
            BlockPos center = Registration.ALEMBICS.get().findCenter(worldPosition, blockState);
            int sideValue = blockState.getValue(Registration.ALEMBICS.get().getSideProperty());
            if (level != null && level.getBlockEntity(center) instanceof AlembicsBE be) {
                if (cap == ForgeCapabilities.ITEM_HANDLER) {
                    return be.getCapability(cap, side);
                }
                if (cap == ForgeCapabilities.FLUID_HANDLER) {
                    if (sideValue == 0) {
                        return be.getInputTank1Holder().cast();
                    } else if (sideValue == 2) {
                        return be.getOutputTankHolder().cast();
                    }
                }
            }

        }
        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        BlockState blockState = getBlockState();
        if (blockState.hasProperty(Registration.ALEMBICS.get().getSideProperty())) {
            BlockPos center = Registration.ALEMBICS.get().findCenter(worldPosition, blockState);
            int side = blockState.getValue(Registration.ALEMBICS.get().getSideProperty());
            if (level != null && level.getBlockEntity(center) instanceof AlembicsBE be) {
                if (cap == ForgeCapabilities.ITEM_HANDLER) {
                    return be.getCapability(cap);
                }
                if (cap == ForgeCapabilities.FLUID_HANDLER) {
                    if (side == 0) {
                        return be.getInputTank1Holder().cast();
                    } else if (side == 2) {
                        return be.getOutputTankHolder().cast();
                    }
                }
            }

        }
        return super.getCapability(cap);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        BlockState blockState = getBlockState();
        if (blockState.hasProperty(Registration.ALEMBICS.get().getSideProperty())) {
            BlockPos center = Registration.ALEMBICS.get().findCenter(worldPosition, blockState);
            if (level != null && level.getBlockEntity(center) instanceof AlembicsBE be) {
                be.setChanged();
                BlockState state = level.getBlockState(center);
                level.sendBlockUpdated(center, state, state, 2);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.remove("ForgeCaps"); // TODO test this out
    }

}
