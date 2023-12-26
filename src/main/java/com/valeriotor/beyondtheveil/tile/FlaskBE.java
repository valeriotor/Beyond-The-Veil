package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
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

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        tank.readFromNBT(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tank.writeToNBT(tag);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == ForgeCapabilities.FLUID_HANDLER)
            return holder.cast();
        return super.getCapability(capability, facing);
    }


    /** Currently same as FluidTank. Later, it might combine FluidTank and ItemHandler so that it can check if there's
     *  already a liquid when trying to add an item and viceversa, and the Flask will be able to contain both
     *
     */
    public static class FlaskTank extends FluidTank{

        public FlaskTank(int capacity) {
            super(capacity);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return super.fill(resource, action);
        }
    }


}
