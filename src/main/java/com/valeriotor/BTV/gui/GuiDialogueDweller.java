package com.valeriotor.BTV.gui;

import java.io.IOException;

import com.valeriotor.BTV.capabilities.FlagProvider;
import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageOpenTradeGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
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
	
	
	@Override
	public void initGui() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 200, this.height - 185, I18n.format("gui.dialogue.talk")));
        this.buttonList.add(new GuiButton(2, this.width / 2, this.height - 185, I18n.format("gui.dialogue.trade")));
        if(this.getTalkingEntityId() == 3 || this.getTalkingEntityId() == 4) {
        	this.buttonList.get(1).enabled = false;
        }
        this.dialogue = I18n.format(String.format("dweller.%s.talk%d", this.getTalkingEntityName(), this.talkCount));
		this.dialogueLength = this.dialogue.length();
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawModalRectWithCustomSizedTexture(this.width/4 - 10, this.height - 164, 0, 0, 512, 164, 512, 512);
			this.setDialogueSpeed();
			
			String[] strings = this.splitStrings(this.dialogue);
			for(int i = 0; i < strings.length; i++) {
				if(this.letterCount > this.getPreviousStringsLength(i, this.dialogue)) {
					String toWrite = strings[i].substring(0, Math.min(strings[i].length(), Math.max(0, this.letterCount - 1 - this.getPreviousStringsLength(i, this.dialogue))));
					drawString(mc.fontRenderer, toWrite, this.width/4 + 12, this.height + (i * 15) - 140, 0xFFFFFF);
				}
			}
			String option0 = this.getDialogueOption(0);
			String option1 = this.getDialogueOption(1);
			if(option0 != null && option1 != null && this.letterCount == this.dialogueLength) {
				String[] option0split = this.splitStrings(option0);
				for(int i = 0; i < option0split.length; i++) {
						String toWrite = option0split[i].substring(0, Math.min(option0split[i].length(), Math.max(0, option0.length() - 1 - this.getPreviousStringsLength(i, option0))));
						drawString(mc.fontRenderer, toWrite, this.width/4 + 62, this.height + (i * 15) - 60, (this.selectedOption == 1 ? 0xFFFFFF : 0xFFFF00));
					
				}
				
				String[] option1split = this.splitStrings(option1);
				for(int i = 0; i < option1split.length; i++) {
						String toWrite = option1split[i].substring(0, Math.min(option1split[i].length(), Math.max(0, option1.length() - 1 - this.getPreviousStringsLength(i, option1))));
						drawString(mc.fontRenderer, toWrite, this.width/4 + 362, this.height + (i * 15) - 60, (this.selectedOption == 0 ? 0xFFFFFF : 0xFFFF00));
					
				}
			
		}
		
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
			this.intervalCount++;
			
			if(this.letterCount < this.dialogueLength) {
				if(this.intervalCount >= this.interval) {
					this.intervalCount = 0;
					this.letterCount++;
					if(this.dialogue.charAt(this.letterCount - 2) == '.') this.intervalCount-=4;
					else if(this.dialogue.charAt(this.letterCount - 2) == ',') this.intervalCount--;
				}
			}

			this.setDialogueSpeed();
		
		
		super.updateScreen();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
			case 1:
				if(this.selectedOption != -1) break;
				proceedDialogue(false);
				break;
			case 2:
				this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).setDialogueType(0);
				BTVPacketHandler.INSTANCE.sendToServer(new MessageOpenTradeGui(true));
				break;
		}
		super.actionPerformed(button);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 1 || keyCode == 18) {
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
		if(this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).getDialogueType() != 0) {
			this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).setDialogueType(0);
			BTVPacketHandler.INSTANCE.sendToServer(new MessageOpenTradeGui(false));
		}
		super.onGuiClosed();
	}
	
	private String getTalkingEntityName() {
		switch(this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).getDialogueType()) {
			case 1:
				return "bartender";
			case 2:
				return "carpenter";
			case 3:
				return "lhkeeper";
			case 4:
				return "scholar";
			default:
				return "";
		}
	}
	
	private int getTalkingEntityId() {
		return this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).getDialogueType();
	}
	
	private int getTalkingEntityTalkCount() {
		switch(this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).getDialogueType()) {
		case 1:
		case 2:
		case 3:
			return 2;
		case 4:
			return 2;
		default:
			return 1;
		}
	}
	
	private String[] splitStrings(String string) {
		return string.split(":");
	}
	
	private int getPreviousStringsLength(int index, String string) {
		String[] strings = this.splitStrings(string);
		int total = 0;
		if(index < 0 || index > strings.length -1) {
			return 0;
		}
		for(int i = 0; i < index; i++) {
			total+= strings[i].length();
		}
		return total;
	}
	
	private void setDialogueSpeed() {
		boolean deleteChar = true;
		
		switch(this.dialogue.charAt(this.letterCount - 1)) {
		case '{':
			this.interval += 1;
			break;
		case '[':
			this.interval = 5;
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
			this.dialogue = sb.deleteCharAt(this.letterCount - 1).toString();
			this.dialogueLength = this.dialogue.length();
		}
		
		
	}
	
	private String getDialogueOption(int id) {
		String string = I18n.format(String.format("dweller.%s.talk%d.option%d", this.getTalkingEntityName(), this.talkCount, id));
		if(string.substring(0, 7).equals("dweller")) string = null;
		return string;
	}
	
	private void proceedDialogue(boolean option) {
		int tmp = this.talkCount;
		this.talkCount = option ? -1 : (this.talkCount+1)%this.getTalkingEntityTalkCount();
		this.letterCount = 1;
		this.interval = 1;
		String newDialogue = String.format("dweller.%s.talk%d", this.getTalkingEntityName(), option ? tmp : this.talkCount);
		if(option) newDialogue = newDialogue.concat(String.format(".reply%d" , this.selectedOption));
		this.dialogue = I18n.format(newDialogue);
		this.dialogueLength = this.dialogue.length();
		this.setDialogueSpeed();
		if(this.getDialogueOption(0) != null) this.selectedOption = 0;
		else this.selectedOption = -1;
	}
}
