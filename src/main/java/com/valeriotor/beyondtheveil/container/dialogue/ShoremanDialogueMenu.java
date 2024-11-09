package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.util.ClientTalkable;
import com.valeriotor.beyondtheveil.dialogue.Dialogue;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.Talkable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;

public class ShoremanDialogueMenu extends AbstractContainerMenu {

    private final Talkable npc;
    private final DialogueTemplate template;
    private final DataSlot branch;
    private final DataSlot indexInBranch;
    private final Dialogue dialogue;

    public ShoremanDialogueMenu(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, playerInventory, player, new ClientTalkable(player), DialogueRegistry.getTemplate(DialogueType.valueOf(byteBuf.readUtf()), byteBuf.readUtf()));
    }

    public ShoremanDialogueMenu(int pContainerId, Inventory playerInventory, Player player, Talkable talkable, DialogueTemplate template) {
        super(Registration.SHOREMAN_DIALOGUE_MENU.get(), pContainerId);
        this.npc = talkable;
        this.template = template;
        this.branch = DataSlot.standalone(); // TODO consider making it an array of two ints? In case they don't get sent together otherwise
        this.indexInBranch = DataSlot.standalone();
        this.branch.set(-1);
        this.indexInBranch.set(-1);
        addDataSlot(this.branch);
        addDataSlot(this.indexInBranch);
        if (!player.level().isClientSide) {
            dialogue = new Dialogue(template);
        } else {
            dialogue = null;
        }
    }

    public void chooseOptionOnServer(ServerPlayer player, int option) {
        player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> dialogue.chooseOption(player, option));
        if (!dialogue.isFinished()) {
            this.branch.set(template.getAllBranches().indexOf(dialogue.getCurrentBranch()));
            this.indexInBranch.set(dialogue.getIndexInBranch());
        }
    }

    public DialogueTemplate getTemplate() {
        return template;
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
