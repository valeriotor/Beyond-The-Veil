package com.valeriotor.beyondtheveil.dialogue;


import java.util.*;

public class DialogueTemplate {
    private String id;
    private DialogueBranch startingBranch;
    private Map<String, DialogueNode> dialogueNodes = new HashMap<>();
    private String unlocksAtEnd;
    private String type;
    private List<String> dialogueUnlocks = new ArrayList<>();
    private List<String> dataUnlocks = new ArrayList<>();

    public String getID() {
        return this.id;
    }

    public DialogueBranch getStartingBranch() {
        return this.startingBranch;
    }

    public DialogueNode getNodeByID(String id) {
        return dialogueNodes.get(id);
    }

    public String getDefaultDialogueUnlockedWhenFinished() {
        return unlocksAtEnd;
    }

    //public Set<String> getUnlockableData() {
    //    Set<String> unlockableData = new HashSet<>();
    //    if (dialogueNodes != null) {
    //        for (DialogueNode node : dialogueNodes.values()) {
    //            for (DialogueBranch branch : node.getDialogueOptions()) {
    //                unlockableData.addAll(branch.getUnlockedData());
    //            }
    //        }
    //    }
    //    return unlockableData;
    //}

    public DialogueType getType() {
        for (DialogueType dialogueType : DialogueType.values()) {
            if (dialogueType.name().equalsIgnoreCase(type)) {
                return dialogueType;
            }
        }
        return null;
    }

    public List<DialogueBranch> getAllBranches() {
        List<DialogueBranch> branches = new ArrayList<>();
        for (DialogueNode value : dialogueNodes.values()) {
            branches.addAll(value.getAllDialogueOptions());
        }
        branches.add(startingBranch);
        branches.sort(Comparator.comparing(DialogueBranch::getBranchID));
        return branches;
    }

    public List<String> getDialogueUnlocks() {
        return dialogueUnlocks;
    }

    public List<String> getDataUnlocks() {
        return dataUnlocks;
    }
}
