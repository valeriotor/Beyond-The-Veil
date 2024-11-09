package com.valeriotor.beyondtheveil.dialogue;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DialogueNode {
    private String id;
    private List<DialogueBranch> dialogueOptions;

    public String getNodeID() {
        return this.id;
    }

    public List<DialogueBranch> getDialogueOptions(PlayerData data) {
        List<DialogueBranch> availableOptions = new ArrayList<>();
        for (DialogueBranch dialogueOption : dialogueOptions) {
            if (dialogueOption.isUnlocked(data)) {
                availableOptions.add(dialogueOption);
            }
        }
        return availableOptions;
    }

    public List<DialogueBranch> getAllDialogueOptions() {
        return this.dialogueOptions;
    }
}
