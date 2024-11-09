package com.valeriotor.beyondtheveil.dialogue;

import com.google.common.collect.ImmutableList;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DialogueBranch {
    private String id;
    private int branchLength;
    private String endingNodeID;
    private String translationKey; // can be null, then we just branch ID. Not used for player dialogue option, but for npc lines
    private List<String> unlockedData = new ArrayList<>();
    private List<String> mustHaveData = new ArrayList<>();
    private List<String> mustNotHaveData = new ArrayList<>();
    private boolean endsDialogue = false;

    public String getBranchID() {
        return this.id;
    }

    public String getTranslationKey() {
        return translationKey == null ? getBranchID() : translationKey;
    }

    public List<Component> getDialogueOptions(PlayerData data, DialogueTemplate template, int indexInBranch) {
        if (indexInBranch >= branchLength) {
            return template.getNodeByID(endingNodeID).getDialogueOptions(data).stream().map(b -> Component.translatable("dialogue.%s.%s.option".formatted(template.getType().name().toLowerCase(), b.getBranchID()))).collect(Collectors.toList());
        }
        return List.of(Component.translatable("dialogue.continue"));
    }

    public int getNumberOfDialogueOptions(PlayerData data, DialogueTemplate template, int indexInBranch) {
        if (indexInBranch >= branchLength) {
            return template.getNodeByID(endingNodeID).getDialogueOptions(data).size();
        }
        return 1;
    }

    public int getLength() {
        return this.branchLength;
    }

    public String getEndingNodeID() {
        return this.endingNodeID;
    }

    public boolean endsDialogue() {
        return this.endsDialogue;
    }

    public boolean isUnlocked(PlayerData data) {
        for (String mustHaveDatum : mustHaveData) {
            if (!data.getBoolean(mustHaveDatum)) {
                return false;
            }
        }
        for (String mustNotHaveDatum : mustNotHaveData) {
            if (data.getBoolean(mustNotHaveDatum)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getUnlockedData() {
        return ImmutableList.copyOf(unlockedData);
    }

    public List<String> getMustHaveData() {
        return ImmutableList.copyOf(mustHaveData);
    }

    public List<String> getMustNotHaveData() {
        return ImmutableList.copyOf(mustNotHaveData);
    }
}
