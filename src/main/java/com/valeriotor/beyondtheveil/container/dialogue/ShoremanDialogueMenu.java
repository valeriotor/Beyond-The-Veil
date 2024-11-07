package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.util.ClientTalkable;
import com.valeriotor.beyondtheveil.entity.Talkable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ShoremanDialogueMenu extends AbstractContainerMenu {

    private final Talkable npc;
    private final DataSlot node;

    public ShoremanDialogueMenu(int pContainerId, Inventory playerInventory, Player player) {
        this(pContainerId, playerInventory, player, new ClientTalkable(player));
    }

    public ShoremanDialogueMenu(int pContainerId, Inventory playerInventory, Player player, Talkable talkable) {
        super(Registration.SHOREMAN_DIALOGUE_MENU.get(), pContainerId);
        this.npc = talkable;
        this.node = DataSlot.standalone();
        this.node.set(-1);
        addDataSlot(this.node);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return npc.getTalkingPlayer() == pPlayer;
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.npc.setTalkingPlayer((Player)null);
    }
}
