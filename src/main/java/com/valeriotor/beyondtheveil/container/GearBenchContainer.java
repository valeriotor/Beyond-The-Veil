package com.valeriotor.beyondtheveil.container;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import com.valeriotor.beyondtheveil.tile.GearBenchBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GearBenchContainer extends AbstractContainerMenu {

    private GearBenchBE blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;

    public GearBenchContainer(int pContainerId, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.GEAR_BENCH_CONTAINER.get(), pContainerId);
        this.playerEntity = player;
        BlockEntity blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);
        //this.addSlot(new SlotGearBenchOutput(gb.output, gb, 0, 138, 24));

        if (blockEntity instanceof GearBenchBE gearBenchBE) {
            this.blockEntity = gearBenchBE;
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                for (int i = 0; i < h.getSlots(); i++) {
                    addSlot(new SlotItemHandler(h, i, 8 + 18 * (i % 4), -2 + 18 * (i / 4)));
                }
            });
        }
        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(h ->
                addSlot(new GearBenchOutputSlot(h, 0, 138, 24, blockEntity)));

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 89;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 147;
        final int HOTBAR_SLOT_COUNT = 9;
        final int PLAYER_INVENTORY_ROW_COUNT = 3;
        final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;

        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            addSlot(new SlotItemHandler(this.playerInventory, x, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new SlotItemHandler(this.playerInventory, slotNumber,  xpos, ypos));
            }
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, Registration.GEAR_BENCH.get());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();
            if (pIndex == 16) {
                do {
                    if (this.moveItemStackTo(stack, 17, 52, false)) {
                        if (stack.getCount() > 0) {
                            this.setCarried(new ItemStack(stack.getItem(), 0));
                            do {
                                this.getCarried().setCount(this.getCarried().getCount()+stack.getCount());
                                slot.onTake(pPlayer, stack);
                                stack = slot.getItem();
                            } while (!stack.isEmpty() && stack.getCount() < getCarried().getMaxStackSize() - getCarried().getCount());
                            return ItemStack.EMPTY;
                        }
                    } else {
                        this.setCarried(new ItemStack(stack.getItem(), 0));
                        do {
                            this.getCarried().setCount(this.getCarried().getCount()+stack.getCount());
                            slot.onTake(pPlayer, stack);
                            stack = slot.getItem();
                        } while (!stack.isEmpty() && stack.getCount() < getCarried().getMaxStackSize() - getCarried().getCount());
                        return ItemStack.EMPTY;
                    }
                    slot.onTake(pPlayer, stack);
                    stack = slot.getItem();
                }while(!stack.isEmpty());
                return ItemStack.EMPTY;
            }
            if (pIndex < 16) {
                if (!this.moveItemStackTo(stack, 17, 52, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, stack);
        }
        return itemStack;
    }

    public ItemStack getCraftItem(int slot) {
        return blockEntity.getCraftItem(slot);
    }

    private static class GearBenchOutputSlot extends SlotItemHandler {
        private final BlockEntity benchBE;

        public GearBenchOutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, BlockEntity benchBE) {
            super(itemHandler, index, xPosition, yPosition);
            this.benchBE = benchBE;
        }

        @Override
        public void onTake(Player pPlayer, ItemStack pStack) {
            LazyOptional<IItemHandler> lazyCap = benchBE.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP);
            if (lazyCap.isPresent()) {
                IItemHandler cap = lazyCap.orElse(null);
                for (int i = 0; i < 16; i++) {
                    Item item = cap.extractItem(i, 1, false).getItem();
                    if (item instanceof BucketItem) {
                        cap.insertItem(i, new ItemStack(Items.BUCKET), false);
                    }
                }
                //TODO research event
            }
            super.onTake(pPlayer, pStack);
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            this.getItem();
            if(!this.getItem().isEmpty()) {
                //GearBenchRecipe gbr = GearBenchRecipeRegistry.recipesFromKeys.get(this.getStack().getItem().getRegistryName().toString());
                //if(gbr != null)
                //    return gbr.isKnown(playerIn) && super.canTakeStack(playerIn);
            }
            return super.mayPickup(playerIn);
        }
    }

}
