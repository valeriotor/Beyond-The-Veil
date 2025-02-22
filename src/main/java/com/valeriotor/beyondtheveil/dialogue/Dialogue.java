package com.valeriotor.beyondtheveil.dialogue;

import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class Dialogue {

    private final DialogueTemplate template;
    private DialogueBranch currentBranch;
    private int indexInBranch = 0;
    private boolean finished;
    private boolean openTrade;

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
            if (indexInBranch < currentBranch.getLength() - 1 || (currentBranch.endsDialogue() && indexInBranch < currentBranch.getLength())) {
                indexInBranch++;
            } else {
                currentBranch = template.getNodeByID(currentBranch.getEndingNodeID()).getDialogueOptions(data).get(index);
                indexInBranch = 0;
                currentBranch.getUnlockedData().forEach(s -> unlockDataFromDialogue(player, s));
            }
            if (currentBranch.endsDialogue() && indexInBranch >= currentBranch.getLength()) {
                finished = true;
                if (currentBranch.getEndingNodeID().equals("trade")) {
                    openTrade = true;
                }
                DialogueData capability = DialogueData.for_(player);
                if(!currentBranch.stopsDialogueUnlock()) {
                    for (String dialogueUnlock : template.getDialogueUnlocks()) {
                        String[] split = dialogueUnlock.split(":");
                        capability.setDialogue(split[0], split[1]);
                    }
                }
                for (String dataUnlock : template.getDataUnlocks()) {
                    DataUtil.setBooleanOnServerAndSync(player, dataUnlock, true, false);
                }
            }
        });
    }

    private static void unlockDataFromDialogue(ServerPlayer player, String s) {
        DataUtil.setBooleanOnServerAndSync(player, s, true, false);
        if ("spoke_keeper".equals(s) && DataUtil.getBoolean(player, "reminisced_darkness")) {
            DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.UNLOCKED_HAMLET, true, false);
        }
        if (Objects.equals(s, PlayerDataLib.RATIONALIZED)) {
            DialogueData.for_(player).setDialogue(DialogueType.BLACK_MIRROR, DialogueRegistry.getTemplate(DialogueType.BLACK_MIRROR, "rationalize3"));
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isOpenTrade() {
        return openTrade;
    }

    public DialogueBranch getCurrentBranch() {
        return currentBranch;
    }

    public int getIndexInBranch() {
        return indexInBranch;
    }
}
