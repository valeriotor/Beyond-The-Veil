package com.valeriotor.beyondtheveil.dialogue;

import com.google.common.collect.ImmutableList;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DialogueBranch {
    private String id;
    private int branchLength;
    private String endingNodeID;
    private String translationKey; // can be null, then we just ending node ID. Not used for player dialogue option, but for npc lines
    private List<String> unlockedData = new ArrayList<>();
    private List<String> mustHaveData = new ArrayList<>();
    private List<String> mustNotHaveData = new ArrayList<>();
    private boolean endsDialogue = false;

    public String getBranchID() {
        return this.id;
    }

    public String getNpcLine(DialogueTemplate template, int indexInBranch) {
        String key = translationKey == null ? getEndingNodeID() : translationKey;
        return I18n.get("dialogue.%s.%s.%s.%d".formatted(template.getType().name().toLowerCase(), template.getID(), key, indexInBranch));
    }

    public List<String> getDialogueOptions(PlayerData data, DialogueTemplate template, int indexInBranch) {
        return getDialogueOptionKeys(data, template, indexInBranch).stream().map(I18n::get).toList();
    }

    private List<String> getDialogueOptionKeys(PlayerData data, DialogueTemplate template, int indexInBranch) {
        if (indexInBranch >= branchLength - 1) {
            if (endsDialogue) {
                return List.of("dialogue.end");
            }
            return template.getNodeByID(endingNodeID).getDialogueOptions(data).stream().map(b -> "dialogue.%s.%s.%s.option".formatted(template.getType().name().toLowerCase(), template.getID(), b.getBranchID())).collect(Collectors.toList());
        }
        return List.of("dialogue.continue");
    }

    public int getNumberOfDialogueOptions(PlayerData data, DialogueTemplate template, int indexInBranch) {
        if (indexInBranch >= branchLength - 1) {
            if (endsDialogue) {
                return 1;
            }
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
