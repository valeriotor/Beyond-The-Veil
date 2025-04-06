package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.dialogue.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MirrorDialogueMenu extends AloneDialogueMenu {

    public MirrorDialogueMenu(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, playerInventory, player, DialogueRegistry.getTemplate(DialogueType.BLACK_MIRROR, byteBuf.readUtf()));
    }

    public MirrorDialogueMenu(int pContainerId, Inventory playerInventory, Player player, DialogueTemplate template) {
        super(Registration.MIRROR_DIALOGUE_MENU.get(), pContainerId, playerInventory, player, template);
    }

}
