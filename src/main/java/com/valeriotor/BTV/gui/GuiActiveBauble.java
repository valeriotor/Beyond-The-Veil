package com.valeriotor.BTV.gui;

import java.io.IOException;
import java.util.List;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncIntDataToServer;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiActiveBauble extends GuiScreen{
	
	private final int bootTime = 5;
	private TextureAtlasSprite[] bTextures = new TextureAtlasSprite[7];
	private BaubleStatus[] status = new BaubleStatus[8];
	protected int counter = 1;
	private static final String[] helpText = I18n.format("gui.activebauble.help").split("/"); 
	private static final ResourceLocation optionTexture = new ResourceLocation(References.MODID + ":textures/gui/bauble_option.png");
	private static final ResourceLocation questionMark = new ResourceLocation(References.MODID + ":textures/gui/question_mark.png");
	private static final ResourceLocation emptyBackground = new ResourceLocation(References.MODID + ":textures/gui/empty_background.png");
	
	public GuiActiveBauble() {
		this.updateBaubles();
		
	}
	
	private void updateBaubles() {
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler(Minecraft.getMinecraft().player);
		for(int i = 0; i < 7; i++) {
			ItemStack stack = handler.getStackInSlot(i);
			if(stack != null && stack.getItem() != Items.AIR) {
				bTextures[i] = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player).getParticleTexture();
			} else {
				bTextures[i] = null;
			}
			status[i] = Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, i), 1, false) == 0 ? BaubleStatus.NONE : BaubleStatus.PASSIVE;
		}
		int active = Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SELECTED_BAUBLE, -1, false);
		if(active != -1) status[active] = status[active] == BaubleStatus.NONE ? BaubleStatus.ACTIVE : BaubleStatus.ACTIVEPASSIVE;
		status[7] = BaubleStatus.NONE;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(optionTexture);
		float scale = ((float)this.counter)/(this.bootTime);
		int selectedOp = this.getSelectedOption(mouseX, mouseY);
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate((this.width/2)/scale, (this.height/2)/scale, 0);
		for(int i = 0; i < 8; i++) {
			GlStateManager.pushMatrix();
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			this.color(i, selectedOp == i);
			drawModalRectWithCustomSizedTexture(this.getXOffset(i)*2, this.getYOffset(i)*2, (i%3)*76, (i/3)*76, 76, 76, 256, 256);
			GlStateManager.popMatrix();
		}
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if(this.counter == this.bootTime) {
			for(int i = 0; i < 7; i++) {
				if(this.bTextures[i] == null) continue;
				int xCoord = (int) (Math.cos(i*Math.PI/4 + 5*Math.PI/8)*100 - 24);
				int yCoord = (int) (Math.sin(i*Math.PI/4 + 5*Math.PI/8)*100 + 24);
				drawTexturedModalRect(xCoord, -yCoord, this.bTextures[i], 48, 48);
			}
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(questionMark);
		int xCoord = (int) (Math.cos(7*Math.PI/4 + 5*Math.PI/8)*100 - 24);
		int yCoord = (int) (Math.sin(7*Math.PI/4 + 5*Math.PI/8)*100 + 24);
		drawModalRectWithCustomSizedTexture(xCoord, -yCoord, 0, 0, 48, 48, 48, 48);
		if(selectedOp == 7) {
			Minecraft.getMinecraft().renderEngine.bindTexture(emptyBackground);
			drawModalRectWithCustomSizedTexture(-165, -84, 0, 0, 192, 192, 192, 192);
			int j = 0;
			for(String s : helpText) {
				List<String> ss = GuiHelper.splitStringsByWidth(s, 175, mc.fontRenderer);
				for(String sss : ss) {
					drawString(mc.fontRenderer, sss, -158, -76+(j++)*15, 0xFFFF00);
				}
			}
		}else if(selectedOp != -1){
			drawCenteredString(mc.fontRenderer, this.getLocalizedBaubleName(selectedOp), 0, 0, 0xFFFF00);
			String s1 = status[selectedOp] == BaubleStatus.ACTIVE || status[selectedOp] == BaubleStatus.ACTIVEPASSIVE ? I18n.format("gui.activebauble.active") : I18n.format("gui.activebauble.inactive");
			String s2 = status[selectedOp] == BaubleStatus.PASSIVE || status[selectedOp] == BaubleStatus.ACTIVEPASSIVE ? I18n.format("gui.activebauble.passiveon") : I18n.format("gui.activebauble.passiveoff");
			drawCenteredString(mc.fontRenderer, s1, 0, 15, 0xFFFF00);
			drawCenteredString(mc.fontRenderer, s2, 0, 30, 0xFFFF00);
		}
		
		GlStateManager.popMatrix();
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 18)
			this.mc.displayGuiScreen((GuiScreen)null);
		super.keyTyped(typedChar, keyCode);
	}
	
	
	private int getXOffset(int index) {
		switch(index) {
		case 0: return -39;
		case 1: return -64;
		case 2: return -63;
		case 3: return -39;
		case 4: return 2;
		case 5: return 32;
		case 6: return 32;
		case 7: return 2;
		}
		return 0;
	}
	
	private int getYOffset(int index) {
		switch(index) {
		case 0: return -64;
		case 1: return -39;
		case 2: return 2;
		case 3: return 32;
		case 4: return 32;
		case 5: return 2;
		case 6: return -39;
		case 7: return -56;
		}
		return 0;
	}
	
	private String getLocalizedBaubleName(int selected) {
		if(selected == -1 || selected == 7) return "";
		return I18n.format(String.format("gui.activebauble.bauble%d", selected));
	}
	
	@Override
	public void updateScreen() {
		if(this.counter < this.bootTime) this.counter++;
		super.updateScreen();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private int getSelectedOption(int mx, int my) {
		int distX = mx-(this.width/2);
		int distY = (this.height/2)-my;
		float distance = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		if(distance > 80 && distance < 116) {
			double a = distY / distance;
			if(distX > 0) {
				if(a < -Math.sqrt(2)/2) return 4;
				else if(a < 0) return 5;
				else if(a < Math.sqrt(2)/2) return 6;
				else return 7;
			}else {
				if(a < -Math.sqrt(2)/2) return 3;
				else if(a < 0) return 2;
				else if(a < Math.sqrt(2)/2) return 1;
				else return 0;
			}
		}
		return -1;
	}
	
	private void color(int option, boolean selected) {
		float transp = selected ? 0.8F : 0.6F;
		if(this.status[option] == null) {
			GlStateManager.color(1, 0, 0, transp);
			return;
		}
		switch(this.status[option]) {
		case ACTIVE: GlStateManager.color(1, 1, 0, transp);
			break;
		case ACTIVEPASSIVE: GlStateManager.color(0, 1, 0, transp);
			break;
		case NONE: GlStateManager.color(1, 0, 0, transp);
			break;
		case PASSIVE: GlStateManager.color(1, 0, 1, transp);
			break;
		
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int op = this.getSelectedOption(mouseX, mouseY);
		if(op != -1 && op != 7) {
			if(mouseButton == 0) {
				Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.SELECTED_BAUBLE, op, false);
				BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncIntDataToServer(PlayerDataLib.SELECTED_BAUBLE, op));
			}else if(mouseButton == 1) {
				int active = mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, op), 1, false);
				Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, op), (active+1)%2, false);
				BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncIntDataToServer(String.format(PlayerDataLib.PASSIVE_BAUBLE, op), (active+1)%2));
			}
		}
		this.updateBaubles();
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
	}
	
	private enum BaubleStatus{
		ACTIVE, ACTIVEPASSIVE, PASSIVE, NONE;
	}
	
	
	public String getGuiLangName() {
		return "guiBauble";
	}
		
	
}
