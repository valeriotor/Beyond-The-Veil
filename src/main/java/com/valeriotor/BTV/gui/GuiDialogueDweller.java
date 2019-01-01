package com.valeriotor.BTV.gui;

import java.io.IOException;

import com.valeriotor.BTV.capabilities.FlagProvider;
import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageLocalizedMessage;
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
		if(this.talkCount >= 0) {
			this.setDialogueSpeed();
			
			String[] strings = this.splitStrings();
			for(int i = 0; i < strings.length; i++) {
				if(this.letterCount > this.getPreviousStringsLength(i)) {
					String toWrite = strings[i].substring(0, Math.min(strings[i].length(), Math.max(0, this.letterCount - 1 - this.getPreviousStringsLength(i))));
					drawString(mc.fontRenderer, toWrite, this.width/4 + 12, this.height + (i * 15) - 140, 0xFFFFFF);
				}
			}
			
		}
		
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		if(this.talkCount >= 0) {
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
		}
		
		
		super.updateScreen();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
			case 1:
				this.talkCount = (this.talkCount+1)%this.getTalkingEntityTalkCount();
				this.letterCount = 1;
				this.interval = 1;
				this.dialogue = I18n.format(String.format("dweller.%s.talk%d", this.getTalkingEntityName(), this.talkCount));
				this.dialogueLength = this.dialogue.length();
				this.setDialogueSpeed();
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
		}else if(keyCode == 42 || keyCode == 28) {
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
			this.letterCount = this.dialogueLength - 1;
		}
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
	
	private String[] splitStrings() {
		return this.dialogue.split(":");
	}
	
	private int getPreviousStringsLength(int index) {
		String[] strings = this.splitStrings();
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
			this.interval = 2;
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
	
}
