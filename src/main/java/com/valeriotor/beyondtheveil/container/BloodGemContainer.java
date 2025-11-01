package com.valeriotor.beyondtheveil.container;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class BloodGemContainer extends AbstractContainerMenu {


    private Player playerEntity;
    private IItemHandler playerInventory;
    private IItemHandler gemInventory;

    public BloodGemContainer(int pContainerId, Inventory playerInventory, Player player) {
        super(Registration.BLOOD_GEM_CONTAINER.get(), pContainerId);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        ItemStack gem = player.getItemInHand(InteractionHand.MAIN_HAND);

        final int GEM_INVENTORY_X = 17;
        final int GEM_INVENTORY_Y = 5;
        final int GEM_SLOT_X_SPACING = 32;
        final int SLOT_Y_SPACING = 18;

        gem.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(c -> {
            gemInventory = c;
            for (int i = 0; i < c.getSlots(); i++) {
                addSlot(new BloodGemSlot(this.gemInventory, i,  GEM_INVENTORY_X + GEM_SLOT_X_SPACING * (i / 3) - (i > 2 ? 1 : 0), GEM_INVENTORY_Y + SLOT_Y_SPACING * (i % 3)));
            }
        });

        final int PLAYER_INVENTORY_XPOS = 8;
        final int PLAYER_INVENTORY_YPOS = 89;
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 147;
        final int HOTBAR_SLOT_COUNT = 9;
        final int PLAYER_INVENTORY_ROW_COUNT = 3;
        final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
        final int SLOT_X_SPACING = 18;

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
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.BLOOD_GEM.get();
    }

    private static class BloodGemSlot extends SlotItemHandler {

        public BloodGemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public int getMaxStackSize(@NotNull ItemStack stack) {
            return 1;
        }
    }

}
