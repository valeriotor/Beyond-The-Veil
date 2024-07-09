package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlaskBE extends BlockEntity {

    private FluidTank tank;
    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);


    public FlaskBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.FLASK_BE.get(), pWorldPosition, pBlockState);
        tank = getTankByFlaskType(((FlaskBlock) pBlockState.getBlock()).size);
    }

    public static FluidTank getTankByFlaskType(FlaskBlock.FlaskSize size) {
        return new FluidTank(size.getCapacity());
    }

    public FluidTank getTank() {
        return tank;
    }

    /**
     * Server-Side only
     */
    public InteractionResult tryFillFromItem(Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            if (itemStack.getItem() != Registration.SYRINGE.get()) {
                if (!pLevel.isClientSide) {
                    FluidUtil.interactWithFluidHandler(pPlayer, pHand, pLevel, pPos, pHit.getDirection());
                    setChanged();
                    if (level != null) {
                        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                    }
                }
                return InteractionResult.SUCCESS;
            } else {
                if (!pLevel.isClientSide) {

                    IFluidHandlerItem syringe = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
                    if (!pPlayer.isShiftKeyDown()) {
                        if (syringe.fill(tank.drain(1, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE) == 1) {
                            syringe.fill(tank.drain(1, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                            setChanged();
                            if (level != null) {
                                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                            }
                        }
                    } else {
                        if (true || tank.fill(syringe.drain(1, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE) == 1) {
                            //tank.fill(syringe.drain(1, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                            tank.fill(new FluidStack(Registration.SOURCE_FLUID_LIQUID_BLAZE_POWDER.get(), 5), IFluidHandler.FluidAction.EXECUTE);
                            setChanged();
                            if (level != null) {
                                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                            }
                        }
                    }

                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag != null) {
            super.load(tag);
        }
        loadCommonData(tag);
    }

    private void loadCommonData(CompoundTag tag) {
        tank.readFromNBT(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        saveCommonData(tag);
    }

    private void saveCommonData(CompoundTag tag) {
        tank.writeToNBT(tag);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.FLUID_HANDLER)
            return holder.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loadCommonData(tag); // default behaviour, here just to remind me of that
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveCommonData(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }


    /**
     * Currently same as FluidTank. Later, it might combine FluidTank and ItemHandler so that it can check if there's
     * already a liquid when trying to add an item and viceversa, and the Flask will be able to contain both
     */
    public static class FlaskTank extends FluidTank {

        public FlaskTank(int capacity) {
            super(capacity);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return super.fill(resource, action);
        }
    }


}
