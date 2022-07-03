package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class GearBenchBE extends BlockEntity {

    private final ItemStackHandler inputItemHandler = createInputHandler();
    private final LazyOptional<IItemHandler> lazyInputItemHandler = LazyOptional.of(() -> inputItemHandler);
    private final ItemStackHandler outputItemHandler = createOutputHandler();

    private final LazyOptional<IItemHandler> lazyOutputItemHandler = LazyOptional.of(() -> outputItemHandler);


    public GearBenchBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.GEAR_BENCH_BE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        lazyInputItemHandler.invalidate();
        lazyOutputItemHandler.invalidate();
    }

    private ItemStackHandler createInputHandler() {
        return new ItemStackHandler(16){
            @Override
            protected void onContentsChanged(int slot) {
                changeOutput();
                setChanged();
            }

        };
    }

    private ItemStackHandler createOutputHandler() {
        return new ItemStackHandler(1){
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return false;
            }
        };
    }

    private void changeOutput(){
        // TODO check for gear bench recipes
        if (inputItemHandler.getStackInSlot(0).getItem() == Item.byBlock(Blocks.DIRT)) {
            outputItemHandler.setStackInSlot(0, new ItemStack(Registration.IDOL_ITEM.get(), 3));
        } else {
            outputItemHandler.setStackInSlot(0, ItemStack.EMPTY);
        }
        setChanged();
    }

    @Override
    public void load(CompoundTag pTag) {
        if (pTag.contains("Inventory")) {
            inputItemHandler.deserializeNBT(pTag.getCompound("Inventory"));
            changeOutput();
        }
        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("Inventory", inputItemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                return lazyOutputItemHandler.cast();
            }
            return lazyInputItemHandler.cast();
        }
        return super.getCapability(cap);
    }
}
