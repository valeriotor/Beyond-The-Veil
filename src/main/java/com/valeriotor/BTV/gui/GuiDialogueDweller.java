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
	private int talkCount = -1;
	private int letterCount = 1;
	private int intervalCount = 0;
	private int interval = 2;
	private int dialogueLength = 0;
	private long timeStart;
	private boolean ignoreDot = false;
	private String dialogue = "";
	
	
	@Override
	public void initGui() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 200, this.height - 185, I18n.format("gui.dialogue.talk")));
        this.buttonList.add(new GuiButton(2, this.width / 2, this.height - 185, I18n.format("gui.dialogue.trade")));
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawModalRectWithCustomSizedTexture(this.width/4 - 10, this.height - 164, 0, 0, 512, 164, 512, 512);
		if(this.talkCount >= 0) {
			if(this.mc.world.getTotalWorldTime() - this.timeStart > 0) {
				this.intervalCount++;
				this.timeStart = this.mc.world.getTotalWorldTime();
			}
			
			if(this.letterCount < this.dialogueLength) {
			if(this.intervalCount >= this.interval) {
				this.intervalCount = 0;
				this.letterCount++;
			}/*else {
				this.intervalCount++;  This commented section was used to increment the intervalCount by fps and not by tick. Maybe will be back as a config option
			}*/
			}
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
	protected void actionPerformed(GuiButton button) throws IOException {
		switch(button.id) {
			case 1:
				this.talkCount = (this.talkCount+1)%this.getTalkingEntityTalkCount();
				this.letterCount = 1;
				this.dialogue = I18n.format(String.format("dweller.%s.talk%d", this.getTalkingEntity(), this.talkCount));
				this.dialogueLength = this.dialogue.length();
				this.timeStart = this.mc.world.getTotalWorldTime();
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
		if(keyCode == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
			return;
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
	
	private String getTalkingEntity() {
		switch(this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).getDialogueType()) {
			case 1:
				return "bartender";
			default:
				return "";
		}
	}
	
	private int getTalkingEntityTalkCount() {
		switch(this.mc.player.getCapability(FlagProvider.FLAG_CAP, null).getDialogueType()) {
		case 1:
			return 2;
		default:
			return 0;
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
			this.interval = 3;
			break;
		case '}':
		case ']':
			this.interval = 1;
			break;
		default:
			deleteChar = false;
		}
		if(this.letterCount > 1) {
			if(this.dialogue.charAt(this.letterCount - 2) == '.') {
				if(!this.ignoreDot) this.intervalCount-=4;
				this.ignoreDot = true;
			}else {
				this.ignoreDot = false;
			}
		}
		if(deleteChar) {
			StringBuilder sb = new StringBuilder(this.dialogue);
			this.dialogue = sb.deleteCharAt(this.letterCount - 1).toString();
			this.dialogueLength = this.dialogue.length();
		}
		
		
	}
	
}
