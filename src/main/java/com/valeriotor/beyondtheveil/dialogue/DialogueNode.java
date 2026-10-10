package com.valeriotor.beyondtheveil.dialogue;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.client.util.CrossSyncHolder;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogueNode {
    private String id;
    private List<DialogueBranch> dialogueOptions;

    public String getNodeID() {
        return this.id;
    }

    public List<DialogueBranch> getDialogueOptions(Player player, PlayerData data, DialogueTemplate template) {
        List<DialogueBranch> availableOptions = new ArrayList<>();
        for (DialogueBranch dialogueOption : dialogueOptions) {
            if (dialogueOption.isUnlocked(data) && hardcodedCheck(player, dialogueOption, template)) {
                availableOptions.add(dialogueOption);
            }
        }
        return availableOptions;
    }

    public List<DialogueBranch> getAllDialogueOptions() {
        return this.dialogueOptions;
    }

    private boolean hardcodedCheck(Player player, DialogueBranch dialogueOption, DialogueTemplate template) {
        if (template.getType() == DialogueType.BLOOD_CULTIST && Objects.equals(template.getID(), "gift") && Objects.equals(id, "thanks")) {
            boolean b = hasSomethingOnShoulder(player);
            if (dialogueOption.getBranchID().equals("on_")) {
                return !b;
            } else if (dialogueOption.getBranchID().equals("on__")) {
                return b;
            }

        }
        return true;
    }

    private boolean hasSomethingOnShoulder(Player player) {
        CrossSync crossSync;
        if (player.level().isClientSide) {
            crossSync = CrossSyncHolder.getCrossSync(player); // I don't like calling beyondtheveil.client code from a common server/client class. Likely unimportant, but proxy would be clean
        } else {
            crossSync = player.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).resolve().map(CrossSyncData::getCrossSync).orElse(null);
        }
        if (crossSync != null) {
            return crossSync.getHeldPatientEntity(player.level()) != null || !crossSync.canHoldPatient();
        }
        return false;
    }
}
