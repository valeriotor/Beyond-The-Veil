package com.valeriotor.beyondtheveil.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogue;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;

public class GuiBlackMirror extends GuiScreen{
	private final MirrorDialogue dialogue;
	
	/** Dialogue options split by width in sublists*/
	private List<List<String>> splitDialogueOptions;
	private List<DialogueLine> dialogueLines = new ArrayList<>();
	private int counter = 30;
	private DialogueLine nextLine;
	
	public GuiBlackMirror(MirrorDialogue dialogue) {
		this.dialogue = dialogue;
		addDialogueLine();
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		updateDialogueOptions(); // resizes current string lists
	}
	
	@Override
	public void updateScreen() {
		if(counter < 30) {
			counter++;
			if(counter == 30) {
				updateDialogueOptions();
				addDialogueLine();
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawRect(0, 0, width, height, 0xFF000000);
		drawDialogueOptions(mouseX, mouseY);
		drawDialogueLines(partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void drawDialogueOptions(int mouseX, int mouseY) {
		if(!splitDialogueOptions.isEmpty()) {
			int yOffset = getDialogueOptionsYOffset();
			int x = (width - getLineWidth()) / 2;
			int selectedOption = getHoveredDialogueOption(mouseX, mouseY);
			for(int i = 0; i < splitDialogueOptions.size(); i++) {
				List<String> option = splitDialogueOptions.get(i);
				int xOffset = selectedOption == i ? x+20 : x;
				for(String optionLine : option) {
					drawString(mc.fontRenderer, optionLine, xOffset, yOffset, 0xFFAA2200);
					yOffset += getTextHeight();
				}
			}
		}
	}
	
	private void drawDialogueLines(float partialTicks) {
		int yOffset = getDialogueOptionsYOffset() - 20;
		if(counter > 15 && counter < 30) {
			int nextLineSize = nextLine == null ? 1 : nextLine.size();
			yOffset -= (int)(nextLine.size() * getTextHeight() * Math.pow((counter + partialTicks - 15) / 15, 2));
		}
		int sideSpace = (width - getLineWidth()) / 2;
		for(int i = dialogueLines.size()-1; i >= 0; i--) {
			DialogueLine line = dialogueLines.get(i);
			yOffset -= line.size() * getTextHeight();
			int drawY = yOffset;
			for(int j = 0; j < line.size(); j++) {
				drawString(mc.fontRenderer, line.getLine(j), sideSpace, drawY, line.color());
				drawY += getTextHeight();
			}
			if(yOffset < 0)
				break;
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int selectedOption = getHoveredDialogueOption(mouseX, mouseY);
		if(selectedOption >= 0) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			if(dialogue.isAtNode()) {
				List<String> option = splitDialogueOptions.get(selectedOption);
				option.set(0, "> " + option.get(0));
				dialogueLines.add(new DialogueLine(option));
			}
			counter = 0;
			dialogue.chooseDialogueOption(selectedOption);
			splitDialogueOptions.clear();
			nextLine = getNextLine();
		}
	}
	
	private int getHoveredDialogueOption(int mouseX, int mouseY) {
		if(!splitDialogueOptions.isEmpty()) {
			int sideSpace = (width - getLineWidth()) / 2;
			// The following two ifs are to get in the "options' region"
			if(mouseX > sideSpace && mouseX < width - sideSpace) {
				if(mouseY > getDialogueOptionsYOffset()) {
					int yOffset = 0;
					int mouseYFromOptionsTop = mouseY - getDialogueOptionsYOffset();
					for(int i = 0; i < splitDialogueOptions.size(); i++) {
						int size = splitDialogueOptions.get(i).size() * getTextHeight();
						if(mouseYFromOptionsTop < yOffset + size) {
							return i;
						}
						else
							yOffset += size;
					}
				}
			}
		}
		return -1;
	}
	
	private int getTextHeight() {
		return 15;
	}
	
	private void updateDialogueOptions() {
		splitDialogueOptions = dialogue.getUnlocalizedDialogueOptions().stream()
										.map(I18n::format)
										.map(option -> mc.fontRenderer.listFormattedStringToWidth(option, getLineWidth()))
										.collect(Collectors.toCollection(ArrayList::new));
	}
	
	private int getLineWidth() {
		return width * 3 / 5;
	}
	
	private int getDialogueOptionsYOffset() {
		return height * 85 / 100;
	}
	
	private DialogueLine getNextLine() {
		String unlocalizedNewLine = dialogue.getUnlocalizedDialogueLine();
		if(unlocalizedNewLine.length() > 0) {
			String localizedNewLine = I18n.format(dialogue.getUnlocalizedDialogueLine());
			DialogueLine line = new DialogueLine(localizedNewLine, text -> mc.fontRenderer.listFormattedStringToWidth(text, getLineWidth()));
			return line;
		}
		return null;
	}
	
	private void addDialogueLine() {
		if(nextLine != null) {
			dialogueLines.add(nextLine);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private static class DialogueLine {
		private List<String> splitLocalizedLines = new ArrayList<String>();
		private int color = 0xFFFFFFFF;
		
		private DialogueLine(String localizedLine, Function<String, List<String>> widthSplitter) {
			if(localizedLine.isEmpty())
				throw new IllegalArgumentException();
			if(localizedLine.charAt(0) == '[') {
				color = getColorFromChar(localizedLine.charAt(1));
				localizedLine = localizedLine.substring(3);
			}
			splitLocalizedLines = widthSplitter.apply(localizedLine);
		}
		
		private DialogueLine(List<String> splitlocalizedLine) {
			this.splitLocalizedLines = splitlocalizedLine;
		}
		
		private int size() {
			return splitLocalizedLines.size();
		}
		
		private List<String> getLines() {
			return splitLocalizedLines;
		}
		
		private String getLine(int index) {
			return getLines().get(index);
		}
		
		private int color() {
			return color;
		}
		
		private static int getColorFromChar(char letter) {
			switch(letter) {
			default:
			case 'W': return 0xFFFFFFFF;
			case 'Y': return Color.YELLOW.getRGB();
			}
		}
		
	}
	
}
