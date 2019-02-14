package com.valeriotor.BTV.gui;

import java.io.IOException;
import java.util.List;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageOpenTradeGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDialogueDweller extends GuiScreen {
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID + ":textures/gui/dialogue_box.png");
	private int talkCount = 0;
	private int letterCount = 1;
	private int intervalCount = 0;
	private int interval = 2;
	private int dialogueLength = 0;
	private int selectedOption = -1;
	private boolean ignoreDot = false;
	private String dialogue = "";
	private String branch = "";
	private String profession = "";
	private String option0 = "";
	private String option1 = "";
	private double scaleMultiplier = 1;
	private int xSize = 512;
	private int ySize = 164;
	
	
	
	@Override
	public void initGui() {
        this.scaleMultiplier = this.getScaleMultiplier();
        this.xSize = (int) (512 * this.scaleMultiplier);
        this.ySize = (int) (164 * this.scaleMultiplier);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 200, this.height - this.ySize - 20, I18n.format("gui.dialogue.talk")));
        this.buttonList.add(new GuiButton(2, this.width / 2, this.height - this.ySize - 20, I18n.format("gui.dialogue.trade")));
        
        this.profession = DialogueHandler.getProfession(Minecraft.getMinecraft().player);
        
        if(!this.profession.equals("bartender") && !this.profession.equals("carpenter")) this.buttonList.get(1).enabled = false;
        
        if(this.dialogue.equals("")) {
        	this.dialogue = DialogueHandler.getLocalizedDialogue(this.profession, this.talkCount, this.branch);
        //this.dialogue = I18n.format(String.format("dweller.%s.talk%d", this.getTalkingEntityName(), this.talkCount));
        	this.dialogueLength = this.dialogue.length();
        }
        this.option0 = DialogueHandler.getLocalizedDialogueOption(this.profession, this.talkCount, 0, this.branch);
		this.option1 = DialogueHandler.getLocalizedDialogueOption(this.profession, this.talkCount, 1, this.branch);
		
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawModalRectWithCustomSizedTexture(this.width/2 - this.xSize/2, this.height - this.ySize, 0F, 0F, xSize, ySize, xSize, xSize);
			this.setDialogueSpeed();
			
			String[] strings = GuiHelper.splitStrings(this.dialogue);
			int totalOffset = 0;
			for(int i = 0; i < strings.length; i++) {
				if(this.letterCount > GuiHelper.getPreviousStringsLength(i, this.dialogue)) {
					List<String> stringettes = GuiHelper.splitStringsByWidth(strings[i], (int)(this.xSize * 0.9), mc.fontRenderer);
					for(int j = 0; j < stringettes.size(); j++) {
						if(this.letterCount > GuiHelper.getPreviousStringsLength(i, this.dialogue) + GuiHelper.getPreviousStringsLengthByWidth(j, strings[i], (int)(this.xSize * 0.9), mc.fontRenderer)) {
							String toWrite = stringettes.get(j).substring(0, Math.min(stringettes.get(j).length(), Math.max(0, this.letterCount - 1 - GuiHelper.getPreviousStringsLength(i, this.dialogue) - GuiHelper.getPreviousStringsLengthByWidth(j, strings[i], (int)(this.xSize * 0.9), mc.fontRenderer))));
							drawString(mc.fontRenderer, toWrite, this.width/2 - this.xSize / 2 + (int)(24 * this.scaleMultiplier), this.height + (int)(18*this.scaleMultiplier) + totalOffset - this.ySize, 0xFFFFFF);
							totalOffset += 15;
						}
					}
				}	
			}
			if(this.option0 != null && this.option1 != null && this.letterCount == this.dialogueLength) {
				String[] option0split = GuiHelper.splitStrings(this.option0);
				for(int i = 0; i < option0split.length; i++) {
						String toWrite = option0split[i].substring(0, Math.min(option0split[i].length(), Math.max(0, this.option0.length() - 1 - GuiHelper.getPreviousStringsLength(i, this.option0))));
						drawCenteredString(mc.fontRenderer, toWrite, this.width/2 - this.xSize / 4, this.height + (i * 15) - (int)(this.ySize*0.367), (this.selectedOption == 1 ? 0xFFFFFF : 0xFFFF00));
					
				}
				
				String[] option1split = GuiHelper.splitStrings(this.option1);
				for(int i = 0; i < option1split.length; i++) {
						String toWrite = option1split[i].substring(0, Math.min(option1split[i].length(), Math.max(0, this.option1.length() - 1 - GuiHelper.getPreviousStringsLength(i, this.option1))));
						drawCenteredString(mc.fontRenderer, toWrite, this.width/2 + this.xSize / 4, this.height + (i * 15) - (int)(this.ySize*0.367), (this.selectedOption == 0 ? 0xFFFFFF : 0xFFFF00));
					
				}
			
			}	
		
			this.setDialogueSpeed();
		
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
			this.intervalCount++;
			
			if(this.letterCount < this.dialogueLength) {
				if(this.intervalCount >= this.interval) {
					this.intervalCount = 0;
					this.letterCount++;
					char c = this.dialogue.charAt(this.letterCount - 2);
					if(c == '.' || c == '?' || c == '!') this.intervalCount-=4;
					else if(this.dialogue.charAt(this.letterCount - 2) == ',') this.intervalCount--;
					this.setDialogueSpeed();
				}
			}

		
		
		super.updateScreen();
	}
	
	private double getScaleMultiplier() {
		switch(this.mc.gameSettings.guiScale) {
		case 0: return 0.85;
		case 1: return 1.5;
		case 2: return 1;
		case 3: return 0.875;
		default: return 1;	
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
			case 1:
				if(this.selectedOption != -1) break;
				proceedDialogue(false);
				break;
			case 2:
				//this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setDialogueType(0);
				//DialogueHandler.removeDialogue(Minecraft.getMinecraft().player);
				BTVPacketHandler.INSTANCE.sendToServer(new MessageOpenTradeGui(true));
				break;
		}
		super.actionPerformed(button);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 1 || keyCode == 18) {
			DialogueHandler.removeDialogue(Minecraft.getMinecraft().player);
			this.mc.displayGuiScreen((GuiScreen)null);
			return;
		}else if(keyCode == 42 || keyCode == 54) {
			StringBuilder sb = new StringBuilder(this.dialogue);
			for(int i = 0; i < sb.length(); i++) {
				char c = sb.charAt(i);
				if(c == '{' || c == '[' || c == '}' || c == ']' || c == '|') {
					sb.deleteCharAt(i);
					i--;
				}
			}
			this.dialogue = sb.toString();
			this.dialogueLength = this.dialogue.length();
			this.letterCount = this.dialogueLength;
		}else if(keyCode == 28) {
			if(this.letterCount != this.dialogueLength) return;
			if(this.selectedOption != -1) {
				proceedDialogue(true);
			}else {
				proceedDialogue(false);
			}
		}else if(keyCode == 203 && this.selectedOption == 1) this.selectedOption = 0;
		else if(keyCode == 205 && this.selectedOption == 0) this.selectedOption = 1; 
		super.keyTyped(typedChar, keyCode);
	}
	
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void onGuiClosed() {
		DialogueHandler.removeDialogue(Minecraft.getMinecraft().player);
		//if(this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).getDialogueType() != 0) {
			//this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setDialogueType(0);
			BTVPacketHandler.INSTANCE.sendToServer(new MessageOpenTradeGui(false));
		//}
		super.onGuiClosed();
	}
	
	
	private void setDialogueSpeed() {
		boolean deleteChar = true;
		int offset = 0;
		if(this.letterCount >= this.dialogueLength - 1) return;
		if(this.letterCount == 1) offset = -1;
		switch(this.dialogue.charAt(this.letterCount + offset)) {
		case '{':
			this.interval += 1;
			break;
		case '[':
			this.interval = 3;
			break;
		case '|':
			this.intervalCount -= 8;
			break;
		case '}':
		case ']':
			this.interval = 1;
			break;
		default:
			deleteChar = false;
		}
		if(deleteChar) {
			StringBuilder sb = new StringBuilder(this.dialogue);
			this.dialogue = sb.deleteCharAt(this.letterCount + offset).toString();
			this.dialogueLength = this.dialogue.length();
		}
		
		
	}
	
	
	private void proceedDialogue(boolean option) {
		int tmp = this.talkCount;
		this.talkCount = option ? 0 : this.talkCount+1;
		if(DialogueHandler.updateDialogueData(this.profession, this.branch, this.selectedOption, this.talkCount)) {
			this.talkCount = 0;
			this.branch = "";
			this.selectedOption = -1;
			option = false;
		}
		if(option) this.branch = DialogueHandler.getBranch(this.profession, this.branch, selectedOption);
		if(!this.branch.equals("") && this.talkCount >= DialogueHandler.getBranchTalkCount(profession, branch)) {
			this.talkCount = 0;
			this.branch = "";
		}
		this.talkCount %= DialogueHandler.getTalkCount(this.branch);
		this.letterCount = 1;
		this.interval = 1;
		this.dialogue = DialogueHandler.getLocalizedDialogue(this.profession, this.talkCount, this.branch);
		this.dialogueLength = this.dialogue.length();
		this.setDialogueSpeed();
		if(DialogueHandler.getLocalizedDialogueOption(this.profession, this.talkCount, 0, this.branch) != null) this.selectedOption = 0;
		else this.selectedOption = -1;
		this.option0 = DialogueHandler.getLocalizedDialogueOption(this.profession, this.talkCount, 0, this.branch);
		this.option1 = DialogueHandler.getLocalizedDialogueOption(this.profession, this.talkCount, 1, this.branch);
		
	}
}
