package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.util.ClientTalkable;
import com.valeriotor.beyondtheveil.dialogue.*;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.entity.Talkable;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Objects;

public class EntityDialogueMenu extends AbstractContainerMenu {

    private final Talkable npc;
    private final DialogueTemplate template;
    private final int entityId;
    private final DataSlot branch;
    private final DataSlot indexInBranch;
    private final Dialogue dialogue;
    private final DataSlot hide;
    private final List<DialogueBranch> allBranches;
    private boolean stayValid;

    public EntityDialogueMenu(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, playerInventory, player, new ClientTalkable(player), DialogueRegistry.getTemplate(DialogueType.valueOf(byteBuf.readUtf()), byteBuf.readUtf()), byteBuf.readInt());
    }

    public EntityDialogueMenu(int pContainerId, Inventory playerInventory, Player player, Talkable talkable, DialogueTemplate template, int entityId) {
        super(Registration.SHOREMAN_DIALOGUE_MENU.get(), pContainerId);
        this.npc = talkable;
        this.template = template;
        this.entityId = entityId;
        this.branch = DataSlot.standalone(); // TODO consider making it an array of two ints? In case they don't get sent together otherwise
        this.indexInBranch = DataSlot.standalone();
        this.hide = DataSlot.standalone();
        addDataSlot(this.branch);
        addDataSlot(this.indexInBranch);
        addDataSlot(this.hide);
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
        if (this.npc instanceof BloodCultistEntity bc && (template == DialogueRegistry.getTemplate(DialogueType.BLOOD_CULTIST, "immortal") || template == DialogueRegistry.getTemplate(DialogueType.BLOOD_CULTIST, "immortal2"))) {
            if (Objects.equals(dialogue.getCurrentBranch().getBranchID(), "§omadman_§r")) {
                DialogueData.for_(bc.getTalkingPlayer()).setDialogue(DialogueType.BLOOD_CULTIST, DialogueRegistry.getTemplate(DialogueType.BLOOD_CULTIST, "immortal2"));
                bc.sendAnimation(AnimationRegistry.blood_cultist_killed_by_player, 0);
                bc.killCutscene(false);
                bc.setKillingEntity(null);
                stayValid = true;
                setHide(true);
            } else {
                bc.bowAndLeave();
                //DialogueData.for_(bc.getTalkingPlayer()).setDialogue(DialogueType.BLOOD_CULTIST, DialogueRegistry.getTemplate(DialogueType.BLOOD_CULTIST, "immortal"));
            }
        } else if (this.npc instanceof ShoremanEntity e) {
            ShoremanEntity.ShoremanProfession profession = e.getProfession();
            if (profession == ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER && template == DialogueRegistry.getTemplate(DialogueType.SHOREMAN_LIGHTHOUSE_KEEPER, "death")) {
                if (DataUtil.getBoolean(e.getTalkingPlayer(), PlayerDataLib.bound_cult.name())) {
                    BloodCultistEntity.startKeeperKill(e);
                } else {
                    e.startKeeperDeath();
                    stayValid = true;
                    setHide(true);
                }
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

    public DialogueBranch getBranchTemplate() {
        return allBranches.get(branch.get());
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
        if (npc instanceof LivingEntity e) {
            if (e.isDeadOrDying()) {
                return false;
            }
        }
        return npc.getTalkingPlayer() == pPlayer && (stayValid || !dialogue.isFinished());
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.npc.setTalkingPlayer((Player) null);
    }

    public boolean isHide() {
        return hide.get() == 1;
    }

    private void setHide(boolean hide) {
        this.hide.set(hide ? 1 : 0);
    }

    public int getEntityId() {
        return entityId;
    }
}
