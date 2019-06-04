package com.valeriotor.BTV.gui;

import java.io.IOException;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiOptionWheel extends GuiScreen{
	
	protected int counter = 1;
	private static final ResourceLocation optionTexture = new ResourceLocation(References.MODID + ":textures/gui/option.png");
	protected int xPos[] = new int[4];
	protected int yPos[] = new int[4];	
	
	@Override
	public void initGui() {
		for(int i = 0; i < 4; i++) {
			xPos[i] = this.getXOffset(i);
			yPos[i] = this.getYOffset(i);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(optionTexture);
		int selectedOption = this.getHoveredOption(mouseX, mouseY);
		float scale = ((float)this.counter)/(this.bootTime());
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate((this.width/2)/scale, (this.height/2)/scale, 0);
		for(int i = 0; i < 4; i++) {
			GlStateManager.pushMatrix();
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			if(selectedOption == i || this.isOptionGreyedOut(i))
				GlStateManager.color(1, 1, 1, (float) 0.2);
			else
				GlStateManager.color(1, 1, 1, (float) 0.6);
			drawModalRectWithCustomSizedTexture(xPos[i], yPos[i], (i/2)*96, (i%2)*96, 96, 96, 192, 192);
			GlStateManager.popMatrix();
		}
		GlStateManager.color(1, 1, 1, 1);
		if(this.counter == this.bootTime()) {
			for(int i = 0; i < 4; i++) {
				if(!this.isOptionAvailable(i)) continue;
				if(this.getOptionTexture(i) == null) continue;
				if(selectedOption == i && !this.isOptionGreyedOut(i)) {
					GlStateManager.pushMatrix();
					GlStateManager.scale(1.6, 1.6, 1.6);
				}
				this.mc.renderEngine.bindTexture(this.getOptionTexture(i));
				drawModalRectWithCustomSizedTexture(xPos[i]+18+16*(i-2)*(i%2), yPos[i]+18+16*(i-1)*((i+1)%2), 0, 0, 60, 60, 60, 60);
				if(selectedOption == i && !this.isOptionGreyedOut(i)) {
					GlStateManager.popMatrix();
				}
					
			}
		}
		if(selectedOption != -1 && isOptionAvailable(selectedOption)) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.6, 1.6, 1.6);
			// TODO: Get config option to make it appear below the selected option and not on the bottom part of the screen 
			// x = xPos[selectedOption], y = yPos[selectedOption+70]
			drawCenteredString(mc.fontRenderer, I18n.format(String.format("gui.optionwheel.%s.%d", this.getGuiLangName(), selectedOption)), 0, this.mc.gameSettings.guiScale == 0 ? 0 : 80, 0xFFFF00);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		if(this.counter < this.bootTime()) this.counter++;
		super.updateScreen();
	}
	
	
	protected int bootTime() {
		return 5;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int option = this.getHoveredOption(mouseX, mouseY);
		if(option != -1)
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		if(this.isOptionAvailable(option) && !this.isOptionGreyedOut(option)) {
			this.doAction(option);
			Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 18)
			this.mc.displayGuiScreen((GuiScreen)null);
		super.keyTyped(typedChar, keyCode);
	}
	
	private int getXOffset(int i) {
		switch(i) {
		case 0:
		case 2:
			return -45;
		case 1:
			return -90;
		case 3:
		default:	
			return 0;
		}
	}
	
	private int getYOffset(int i) {
		switch(i) {
		case 1:
		case 3:
			return -45;
		case 0:
			return -90;
		case 2:
		default:	
			return 0;
		}
	}
	
	private int getHoveredOption(int mouseX, int mouseY) {
		int distX = mouseX-(this.width/2);
		int distY = (this.height/2)-mouseY;
		float distance = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		if(distance < 15 || distance > 95) 
			return -1;
		double a = Math.asin(distY/distance);
		if(a < -Math.PI/4) return 2;
		if(a >  Math.PI/4) return 0;
		if(distX < 0) return 1;
		return 3;
	}
	
	protected final boolean isOptionAvailable(int option) {
		switch(option) {
		case 0: return this.isNorthOptionAvailable();
		case 1: return this.isWestOptionAvailable();
		case 2: return this.isSouthOptionAvailable();
		case 3: return this.isEastOptionAvailable();
		default: return false;
		}
	}
	
	protected boolean isNorthOptionAvailable() {return false;}
	protected boolean isWestOptionAvailable() {return false;}
	protected boolean isSouthOptionAvailable() {return false;}
	protected boolean isEastOptionAvailable() {return false;}
	

	public final boolean isOptionGreyedOut(int option) {
		switch(option) {
		case 0: return this.isNorthOptionGreyedOut();
		case 1: return this.isWestOptionGreyedOut();
		case 2: return this.isSouthOptionGreyedOut();
		case 3: return this.isEastOptionGreyedOut();
		default: return false;
		}
	}
	
	protected boolean isNorthOptionGreyedOut() {return false;}
	protected boolean isWestOptionGreyedOut() {return false;}
	protected boolean isSouthOptionGreyedOut() {return false;}
	protected boolean isEastOptionGreyedOut() {return false;}
	
	
	public final ResourceLocation getOptionTexture(int option) {
		switch(option) {
		case 0: return this.getNorthOptionTexture();
		case 1: return this.getWestOptionTexture();
		case 2: return this.getSouthOptionTexture();
		case 3: return this.getEastOptionTexture();
		default: return null;
		}
	}
	
	public ResourceLocation getNorthOptionTexture() {return null;}
	public ResourceLocation getWestOptionTexture() {return null;}
	public ResourceLocation getSouthOptionTexture() {return null;}
	public ResourceLocation getEastOptionTexture() {return null;}
	
	
	public final void doAction(int option) {
		switch(option) {
		case 0: this.doNorthAction(); 
		break;
		case 1: this.doWestAction(); 
		break;
		case 2: this.doSouthAction(); 
		break;
		case 3: this.doEastAction(); 
		break;
		}
	}
	
	public void doNorthAction() {}
	public void doWestAction() {}
	public void doSouthAction() {}
	public void doEastAction() {}
	
	public abstract String getGuiLangName();
	
}
