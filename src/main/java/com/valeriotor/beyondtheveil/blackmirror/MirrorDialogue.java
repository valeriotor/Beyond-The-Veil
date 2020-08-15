package com.valeriotor.beyondtheveil.blackmirror;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class MirrorDialogue {
	private final MirrorDialogueTemplate template;
	private int branchCounter = 0;
	private MirrorDialogueBranch currentBranch;
	private MirrorDialogueNode currentNode;
	
	public static MirrorDialogue getDialogue(String id) {
		return new MirrorDialogue(MirrorDialogueRegistry.getDialogueTemplate(id));
	}
	
	private MirrorDialogue(MirrorDialogueTemplate template) {
		this.template = template;
		currentBranch = template.getStartingBranch();
		if(currentBranch.getLength() <= 1) {
			updateCurrentNode();
		}
	}
	
	public String getUnlocalizedDialogueLine() {
		if(currentBranch.getLength() > 0) {
			return formatDialogueLine();
		} else {
			return "";
		}		
	}

	public List<String> getUnlocalizedDialogueOptions() {
		if(currentNode == null)
			return Collections.EMPTY_LIST;
		List<String> optionNames = new ArrayList<>();
		for(MirrorDialogueBranch option : currentNode.getDialogueOptions()) {
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
		if(currentNode == null) {
			progressDialogueBranch();
		} else {
			currentBranch = currentNode.getDialogueOptions().get(index);
			branchCounter = 0;
			if(currentBranch.getLength() <= 1)
				updateCurrentNode();
			else
				currentNode = null;
		}
	}
	
	public void progressDialogueBranch() {
		branchCounter++;
		if(branchCounter >= currentBranch.getLength() - 1) {
			updateCurrentNode();
		}
	}
	
	private void updateCurrentNode() {
		/*String newNodeId = currentBranch.getEndingNodeID();
		if("end".equals(newNodeId))*/
		currentNode = template.getNodeByID(currentBranch.getEndingNodeID());
	}
}
