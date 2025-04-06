package com.valeriotor.beyondtheveil.container;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.util.ClientTalkable;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class DrownedContainer extends AbstractContainerMenu {

    private final BlockPos pos;
    private final Player player;
    private final int phase;

    public DrownedContainer(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, player, byteBuf.readInt());
    }

    public DrownedContainer(int pContainerId, Player player, int phase) {
        super(Registration.DROWNED_CONTAINER.get(), pContainerId);
        this.pos = player.getOnPos();
        this.player = player;
        this.phase = phase;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return pPlayer.distanceToSqr(pos.getCenter()) < 25 && pPlayer.isAlive();
    }

    public int getPhase() {
        return phase;
    }
}
