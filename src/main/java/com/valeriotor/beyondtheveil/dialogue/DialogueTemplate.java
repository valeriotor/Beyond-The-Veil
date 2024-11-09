package com.valeriotor.beyondtheveil.dialogue;


import java.util.*;

public class DialogueTemplate {
    private String id;
    private DialogueBranch startingBranch;
    private Map<String, DialogueNode> dialogueNodes = new HashMap<>();
    private String unlocksAtEnd;
    private String type;

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
            if (type != null && dialogueType.name().toLowerCase().equals(type.toLowerCase())) {
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
        branches.sort(Comparator.comparing(DialogueBranch::getBranchID));
        return branches;
    }

}
