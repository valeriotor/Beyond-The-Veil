package com.valeriotor.beyondtheveil.blackmirror;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.resources.I18n;


public class MirrorDialogue {
	private final MirrorDialogueTemplate template;
	private final List<String> continueOption;
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
		continueOption = ImmutableList.of("mirror.continuebutton");
	}
	
	public boolean isAtNode() {
		return currentNode != null;
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
			return continueOption;
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
	
	private void progressDialogueBranch() {
		branchCounter++;
		if(branchCounter >= currentBranch.getLength() - 1) {
			updateCurrentNode();
		}
	}
	
	private void updateCurrentNode() {
		currentNode = template.getNodeByID(currentBranch.getEndingNodeID());
	}
}
