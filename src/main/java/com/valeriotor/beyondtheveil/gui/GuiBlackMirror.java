package com.valeriotor.beyondtheveil.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogue;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.mirror.MessageMirrorToServer;

import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Mouse;

public class GuiBlackMirror extends GuiScreen{
	private final MirrorDialogue dialogue;
	
	/** Dialogue options split by width in sublists*/
	private List<List<String>> splitDialogueOptions;
	private List<DialogueLine> dialogueLines = new ArrayList<>();
	private int counter = 30;
	private int yOptionOffset = 0;
	private int yFullOptionSize = 0;
	private DialogueLine nextLine;
	
	public GuiBlackMirror(MirrorDialogue dialogue) {
		this.dialogue = dialogue;
	}
	
	@Override
	public void initGui() {
		if (dialogueLines.isEmpty()) {
			nextLine = getNextLine();
			addDialogueLine();
		}
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
			int yAreaOffset = getOptionAreaYOffset();
			int yOffset = getDialogueOptionsYOffset();
			int x = (width - getLineWidth()) / 2;
			int selectedOption = getHoveredDialogueOption(mouseX, mouseY);
			for(int i = 0; i < splitDialogueOptions.size(); i++) {
				List<String> option = splitDialogueOptions.get(i);
				int xOffset = selectedOption == i ? x+25 : x + 10;
				if(yOffset > yAreaOffset - getTextHeight())
					drawString(mc.fontRenderer, "> ", x, yOffset, 0xFFAA2200);
				for(String optionLine : option) {
					if(yOffset > yAreaOffset - getTextHeight())
						drawString(mc.fontRenderer, optionLine, xOffset, yOffset, 0xFFAA2200);
					yOffset += getTextHeight();
					if (yOffset > height) {
						break;
					}
				}
			}
			drawRect(x, yAreaOffset - getTextHeight(), x+getLineWidth(), yAreaOffset - getTextHeight()/4,  0xFF000000);
			int optionAreaToOptionsDifference = getOptionAreaToOptionsDifference();
			if (optionAreaToOptionsDifference > 0) {
				x += getLineWidth()*21/20;
				int optionAreaHeight = height - yAreaOffset;
				int scrollSectionHeight = optionAreaHeight *9/10;
				drawRect(x, yAreaOffset, x + 8, yAreaOffset+scrollSectionHeight, 0xFF333333);
				int barHeight = scrollSectionHeight * optionAreaHeight / yFullOptionSize;
				int barY = yAreaOffset + (scrollSectionHeight - barHeight) * yOptionOffset / optionAreaToOptionsDifference;
				drawRect(x, barY, x+8, barY+barHeight, 0xFFBBBBBB);
				if (barHeight > 8) {
					drawRect(x+1, barY+1, x+7, barY+barHeight-1, 0xFF777777);
					drawRect(x+1, barY+barHeight/2-3, x+7, barY+barHeight/2-2, 0xFF333333);
				}
				drawRect(x+1, barY+barHeight/2+2, x+7, barY+barHeight/2+3, 0xFF333333);
			}
			GlStateManager.color(1, 1, 1);
		}
	}
	
	private void drawDialogueLines(float partialTicks) {
		int yOffset = getOptionAreaYOffset() - 20;
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
			if(dialogue.shouldEndNow()) 
				finishDialogue();
		}
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int optionAreaToOptionsDifference = getOptionAreaToOptionsDifference();
		yOptionOffset = MathHelperBTV.clamp(0, Math.max(0, optionAreaToOptionsDifference), yOptionOffset - 4*(int) Math.signum(Mouse.getDWheel()));
	}

	private int getOptionAreaToOptionsDifference() {
		return yFullOptionSize - (height - getOptionAreaYOffset());
	}

	private void finishDialogue() {
		mc.displayGuiScreen(null);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageMirrorToServer());
	}
	
	private int getHoveredDialogueOption(int mouseX, int mouseY) {
		if(!splitDialogueOptions.isEmpty() && mouseY > getOptionAreaYOffset()) {
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
		yFullOptionSize = 0;
		for (List<String> optionLines : splitDialogueOptions) {
			yFullOptionSize += optionLines.size() * getTextHeight();
		}
	}
	
	private int getLineWidth() {
		return width * 3 / 5;
	}
	
	private int getDialogueOptionsYOffset() {
		return height * 85 / 100 - yOptionOffset;
	}

	private int getOptionAreaYOffset() {
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
				if (true) {
					localizedLine = "- " + localizedLine;
				}
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
