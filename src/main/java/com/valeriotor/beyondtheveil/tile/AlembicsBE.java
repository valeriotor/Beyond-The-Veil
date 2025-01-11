package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlembicsBE extends BlockEntity {

    private FluidTank inputTank1;
    private FluidTank inputTank2;
    private FluidTank outputTank;

    private final LazyOptional<IFluidHandler> inputTank1Holder = LazyOptional.of(() -> inputTank1);
    private final LazyOptional<IFluidHandler> inputTank2Holder = LazyOptional.of(() -> inputTank2);
    private final LazyOptional<IFluidHandler> outputTankHolder = LazyOptional.of(() -> outputTank);


    public AlembicsBE(BlockPos pPos, BlockState pBlockState) {
        super(Registration.ALEMBICS_BE.get(), pPos, pBlockState);
        inputTank1 = new FluidTank(2000);
        inputTank2 = new FluidTank(1000);
        outputTank = new FluidTank(1000);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag != null) {
            super.load(tag);
        }
        loadCommonData(tag);
    }

    private void loadCommonData(CompoundTag tag) {
        if (tag.contains("inputTank1")) {
            inputTank1.readFromNBT(tag.getCompound("inputTank1"));
            inputTank2.readFromNBT(tag.getCompound("inputTank2"));
            outputTank.readFromNBT(tag.getCompound("outputTank"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        saveCommonData(tag);
    }

    private void saveCommonData(CompoundTag tag) {
        CompoundTag inputTank1Tag = new CompoundTag();
        inputTank1.writeToNBT(inputTank1Tag);
        tag.put("inputTank1", inputTank1Tag);
        CompoundTag inputTank2Tag = new CompoundTag();
        inputTank2.writeToNBT(inputTank2Tag);
        tag.put("inputTank2", inputTank2Tag);
        CompoundTag outputTankTag = new CompoundTag();
        outputTank.writeToNBT(outputTankTag);
        tag.put("outputTank", outputTankTag);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {

            //TODO if facing == .... return holder.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        loadCommonData(tag);
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

}
