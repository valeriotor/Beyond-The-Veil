package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.util.ClientTalkable;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.entity.Talkable;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class DoubleDialogueMenu extends AbstractContainerMenu {
    private final Talkable npc;
    private boolean bowing = false;

    public DoubleDialogueMenu(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, new ClientTalkable(player));
    }

    public DoubleDialogueMenu(int pContainerId, Talkable talkable) {
        super(Registration.DOUBLE_DIALOGUE_MENU.get(), pContainerId);
        this.npc = talkable;

    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return npc.getTalkingPlayer() == pPlayer && npc instanceof LivingEntity e && e.isAlive() && !bowing;
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.npc.setTalkingPlayer((Player) null);
    }

    public void chooseOptionOnServer(ServerPlayer player, int optionIndex) {
        // TODO eh I guess all handled client side?
    }

    public void killKeeper(boolean killCultist) {
        if (npc instanceof BloodCultistEntity cultist) {
            if (cultist.getKillingEntity() instanceof ShoremanEntity shoreman) {
                shoreman.sendAnimation(AnimationRegistry.shoreman_keeper_death_cultist, 0);
                shoreman.aboutToDie();
            }
            if (killCultist) {
                cultist.sendAnimation(AnimationRegistry.blood_cultist_kill_keeper, 0);
                cultist.finalCutscene();
            } else {
                cultist.sendAnimation(AnimationRegistry.blood_cultist_kill_keeper_spare_cultist, 0);
            }
            cultist.level().playSound(null, cultist.blockPosition(), BTVSounds.KILL_KEEPER.get(), SoundSource.NEUTRAL, 1, 1);
        }
    }

    public void spareCultist() {
        if (npc instanceof BloodCultistEntity cultist) {
            cultist.bowAndLeave();
            bowing = true;
        }
    }
}
