package com.valeriotor.beyondtheveil.blackmirror;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.generic.GenericMessageKey;
import com.valeriotor.beyondtheveil.network.generic.MessageGenericToServer;
import net.minecraft.entity.player.EntityPlayer;


public class MirrorDialogue {
    private final MirrorDialogueTemplate template;
    private final List<String> continueOption;
    private final List<String> endOption;
    private int branchCounter = 0;
    private MirrorDialogueBranch currentBranch;
    private final EntityPlayer player;
    private MirrorDialogueNode currentNode;
    private List<MirrorDialogueBranch> availableOptions = new ArrayList<>();

    public static MirrorDialogue getDialogue(EntityPlayer p) {
        return new MirrorDialogue(MirrorUtil.getCurrentDialogue(p), p);
    }

    private MirrorDialogue(MirrorDialogueTemplate template, EntityPlayer p) {
        this.template = template;
        currentBranch = template.getStartingBranch();
        this.player = p;
        if (currentBranch.getLength() <= 1) {
            updateCurrentNode();
        }
        continueOption = ImmutableList.of("mirror.continuebutton");
        endOption = ImmutableList.of("mirror.endbutton");
    }

    public boolean isAtNode() {
        return currentNode != null;
    }

    public String getUnlocalizedDialogueLine() {
        if (currentBranch.getLength() > 0) {
            return formatDialogueLine();
        } else {
            return "";
        }
    }

    public List<String> getUnlocalizedDialogueOptions() {
        if (currentNode == null) {
            if (shouldShowEndButton())
                return endOption;
            else
                return continueOption;
        }
        List<String> optionNames = new ArrayList<>();
        for (MirrorDialogueBranch option : availableOptions) {
            String optionName = formatDialogueOption(option);
            optionNames.add(optionName);
        }
        return optionNames;
    }

    private String formatDialogueLine() {
        return new StringBuilder()
                .append("mirror.")
                .append(template.getID())
                .append(".")
                .append(currentBranch.getBranchID())
                .append(".")
                .append(String.valueOf(branchCounter))
                .toString();
    }

    private String formatDialogueOption(MirrorDialogueBranch option) {
        return new StringBuilder()
                .append("mirror.")
                .append(template.getID())
                .append(".")
                .append(currentNode.getNodeID())
                .append(".")
                .append(option.getBranchID())
                .toString();
    }

    public void chooseDialogueOption(int index) {
        if (currentNode == null) {
            progressDialogueBranch();
        } else {
            currentBranch = availableOptions.get(index);
            branchCounter = 0;
            if (currentBranch.getLength() <= 1)
                updateCurrentNode();
            else
                currentNode = null;
        }
    }

    private void progressDialogueBranch() {
        branchCounter++;
        if (branchCounter >= currentBranch.getLength() - 1) {
            updateCurrentNode();
        }
    }

    private void unlockData() {
        for (String unlockedData : currentBranch.getUnlockedData()) {
            BTVPacketHandler.INSTANCE.sendToServer(new MessageGenericToServer(GenericMessageKey.MIRROR_UNLOCK_DATA, unlockedData));
        }
    }

    private void updateCurrentNode() {
        unlockData();
        currentNode = template.getNodeByID(currentBranch.getEndingNodeID());
        availableOptions.clear();
        if (currentNode != null) {
            IPlayerData data = PlayerDataLib.getCap(player);
            for (MirrorDialogueBranch branch : currentNode.getDialogueOptions()) {
                boolean flag = true;
                for (String s : branch.getMustHaveData()) {
                    if (!data.getString(s)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (String s : branch.getMustNotHaveData()) {
                        if (data.getString(s)) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    availableOptions.add(branch);
                }
            }
        }
    }

    private boolean shouldShowEndButton() {
        return currentBranch.endsDialogue() && branchCounter >= currentBranch.getLength() - 1;
    }

    public boolean shouldEndNow() {
        return currentBranch.endsDialogue() && branchCounter >= currentBranch.getLength();
    }
}
