package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.dialogue.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AloneDialogueMenu extends AbstractContainerMenu {
    private final DialogueTemplate template;
    private final DataSlot branch;
    private final DataSlot indexInBranch;
    private final Dialogue dialogue;
    private final List<DialogueBranch> allBranches;

    public AloneDialogueMenu(MenuType<?> type, int pContainerId, Inventory playerInventory, Player player, DialogueTemplate template) {
        super(type, pContainerId);
        this.template = template;
        this.branch = DataSlot.standalone(); // TODO consider making it an array of two ints? In case they don't get sent together otherwise
        this.indexInBranch = DataSlot.standalone();
        addDataSlot(this.branch);
        addDataSlot(this.indexInBranch);
        if (!player.level().isClientSide) {
            dialogue = new Dialogue(template);
        } else {
            dialogue = null;
        }
        this.allBranches = template.getAllBranches();
        this.branch.set(allBranches.indexOf(template.getStartingBranch()));
        this.indexInBranch.set(0);
    }

    public void chooseOptionOnServer(ServerPlayer player, int option) {
        player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> dialogue.chooseOption(player, option));
        if (!dialogue.isFinished()) {
            this.branch.set(allBranches.indexOf(dialogue.getCurrentBranch()));
            this.indexInBranch.set(dialogue.getIndexInBranch());
            broadcastChanges();
        }
    }

    public DialogueTemplate getTemplate() {
        return template;
    }

    public String getLine() {
        return allBranches.get(branch.get()).getNpcLine(template, indexInBranch.get());
    }

    public List<DialogueBranch.DialogueOption> getDialogueOptions(PlayerData data) {
        return allBranches.get(branch.get()).getDialogueOptions(data, template, indexInBranch.get());
    }

    public int getBranch() {
        return branch.get();
    }

    public int getIndexInBranch() {
        return indexInBranch.get();
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !dialogue.isFinished();
    }
}
