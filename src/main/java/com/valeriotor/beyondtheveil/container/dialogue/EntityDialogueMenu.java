package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.util.ClientTalkable;
import com.valeriotor.beyondtheveil.dialogue.*;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.entity.Talkable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EntityDialogueMenu extends AbstractContainerMenu {

    private final Talkable npc;
    private final DialogueTemplate template;
    private final DataSlot branch;
    private final DataSlot indexInBranch;
    private final Dialogue dialogue;
    private final List<DialogueBranch> allBranches;

    public EntityDialogueMenu(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, playerInventory, player, new ClientTalkable(player), DialogueRegistry.getTemplate(DialogueType.valueOf(byteBuf.readUtf()), byteBuf.readUtf()));
    }

    public EntityDialogueMenu(int pContainerId, Inventory playerInventory, Player player, Talkable talkable, DialogueTemplate template) {
        super(Registration.SHOREMAN_DIALOGUE_MENU.get(), pContainerId);
        this.npc = talkable;
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
        } else {
            if (dialogue.isOpenTrade()) {
                if (npc instanceof ShoremanEntity sh) {
                    sh.setTradingPlayer(player);
                    sh.openTradingScreen(player, Component.translatable("gui.%s.display_name".formatted(sh.getProfession().name().toLowerCase())), 5);

                }
            }
            additionalEndEffects();
        }
    }

    private void additionalEndEffects() {
        if (this.npc instanceof BloodCultistEntity bc && template == DialogueRegistry.getTemplate(DialogueType.BLOOD_CULTIST, "immortal")) {
            bc.bowAndLeave();
        } else if (this.npc instanceof ShoremanEntity e) {
            ShoremanEntity.ShoremanProfession profession = e.getProfession();
            if (profession == ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER && template == DialogueRegistry.getTemplate(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "death")) {
                BloodCultistEntity.startKeeperKill(e);
            }
        }
    }

    public DialogueTemplate getTemplate() {
        return template;
    }

    public String getNpcLine() {
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
        return npc.getTalkingPlayer() == pPlayer && !dialogue.isFinished();
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.npc.setTalkingPlayer((Player) null);
    }



}
