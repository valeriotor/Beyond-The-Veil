package com.valeriotor.beyondtheveil.dialogue;

import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class Dialogue {

    private final DialogueTemplate template;
    private DialogueBranch currentBranch;
    private int indexInBranch = 0;
    private boolean finished;

    public Dialogue(DialogueTemplate template) {
        this.template = template;
        currentBranch = template.getStartingBranch();
    }

    public void chooseOption(ServerPlayer player, int index) {
        player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> {
            int numberOfDialogueOptions = currentBranch.getNumberOfDialogueOptions(data, template, indexInBranch);
            if (index < 0 || index >= numberOfDialogueOptions) {
                return;
            }
            if (indexInBranch < currentBranch.getLength() - 1) {
                indexInBranch++;
            } else {
                currentBranch = template.getNodeByID(currentBranch.getEndingNodeID()).getDialogueOptions(data).get(index);
                indexInBranch = 0;
                currentBranch.getUnlockedData().forEach(s -> DataUtil.setBooleanOnServerAndSync(player, s, true, false));
            }
            if (currentBranch.endsDialogue() && indexInBranch >= currentBranch.getLength() - 1) {
                finished = true;
                DialogueData capability = DialogueData.for_(player);
                for (String dialogueUnlock : template.getDialogueUnlocks()) {
                    String[] split = dialogueUnlock.split(":");
                    capability.setDialogue(split[0], split[1]);
                }
            }
        });
    }

    public boolean isFinished() {
        return finished;
    }

    public DialogueBranch getCurrentBranch() {
        return currentBranch;
    }

    public int getIndexInBranch() {
        return indexInBranch;
    }
}
