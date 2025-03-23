package com.valeriotor.beyondtheveil.container;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.tile.GearBenchBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class DreamBottleContainer extends AbstractContainerMenu {
    private Player playerEntity;
    private IItemHandler playerInventory;
    private IItemHandler bottleInventory;
    private IFluidHandlerItem fluidHandler;

    public DreamBottleContainer(int pContainerId, Inventory playerInventory, Player player) {
        super(Registration.DREAM_BOTTLE_CONTAINER.get(), pContainerId);
        this.playerEntity = player;
        ItemStack bottle = player.getItemInHand(InteractionHand.MAIN_HAND);

        final int BOTTLE_INVENTORY_X = 72;
        final int BOTTLE_INVENTORY_Y = 26;
        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;

        bottle.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(c -> {
            bottleInventory = c;
            for (int i = 0; i < c.getSlots(); i++) {
                addSlot(new SlotItemHandler(this.bottleInventory, i,  BOTTLE_INVENTORY_X + SLOT_X_SPACING * (i % 2), BOTTLE_INVENTORY_Y + SLOT_Y_SPACING * (i / 2)));
            }
        });
        bottle.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(c -> {
            fluidHandler = c;
        });
        this.playerInventory = new InvWrapper(playerInventory);

        // y coords shifted by -20 here because of stuff in gui...
        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 89;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 147;
        final int HOTBAR_SLOT_COUNT = 9;
        final int PLAYER_INVENTORY_ROW_COUNT = 3;
        final int PLAYER_INVENTORY_COLUMN_COUNT = 9;

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
    public ItemStack quickMoveStack(Player pPlayer, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();
            if (index < 4) {
                if (!this.moveItemStackTo(stack, 4, slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stack.getItem() == Registration.MEMORY_PHIAL.get() && !this.moveItemStackTo(stack, 0, 4, false)) {
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

    @Override
    public boolean stillValid(Player pPlayer) {
        return bottleInventory != null;
    }

    public IFluidHandlerItem getFluidHandler() {
        return fluidHandler;
    }
}
