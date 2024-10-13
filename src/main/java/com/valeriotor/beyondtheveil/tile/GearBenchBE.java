package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GearBenchBE extends BlockEntity implements CraftingContainer {

    private final ItemStackHandler inputItemHandler = createInputHandler();
    private final LazyOptional<IItemHandler> lazyInputItemHandler = LazyOptional.of(() -> inputItemHandler);
    private final ItemStackHandler outputItemHandler = createOutputHandler();

    private final LazyOptional<IItemHandler> lazyOutputItemHandler = LazyOptional.of(() -> outputItemHandler);


    public GearBenchBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.GEAR_BENCH_BE.get(), pWorldPosition, pBlockState);
    }

    public ItemStack getCraftItem(int slot) {
        if (lazyInputItemHandler.isPresent()) {
            return lazyInputItemHandler.resolve().get().getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
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

        if (!level.isClientSide) {
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<GearBenchRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(Registration.GEAR_BENCH_RECIPE_TYPE.get(), this, level);
            if (optional.isPresent()) {
                GearBenchRecipe craftingrecipe = optional.get();
                ItemStack itemstack1 = craftingrecipe.assemble(this, level.registryAccess());
                if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                    itemstack = itemstack1;
                }
            }

            outputItemHandler.setStackInSlot(0, itemstack);
        }
        //// TODO check for gear bench recipes
        //if (inputItemHandler.getStackInSlot(0).getItem() == Item.byBlock(Blocks.DIRT)) {
        //    outputItemHandler.setStackInSlot(0, new ItemStack(Registration.IDOL_ITEM.get(), 3));
        //} else {
        //    outputItemHandler.setStackInSlot(0, ItemStack.EMPTY);
        //}
        //setChanged();
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
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.DOWN) {
                return lazyOutputItemHandler.cast();
            }
            return lazyInputItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public int getContainerSize() {
        return 16;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < 16; i++) {
            if (!inputItemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void setOutput(ItemStack stack) {
        outputItemHandler.setStackInSlot(0, stack);
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return inputItemHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return inputItemHandler.extractItem(pSlot, pAmount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return inputItemHandler.extractItem(pSlot, 1, false);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        inputItemHandler.setStackInSlot(pSlot, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {

    }

    @Override
    public int getWidth() {
        return 4;
    }

    @Override
    public int getHeight() {
        return 4;
    }

    @Override
    public List<ItemStack> getItems() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            stacks.add(inputItemHandler.getStackInSlot(i));
        }
        return stacks;
    }

    @Override
    public void fillStackedContents(StackedContents pContents) {
        for (int i = 0; i < 16; i++) {
            pContents.accountSimpleStack(inputItemHandler.getStackInSlot(i));
        }
    }
}
