package com.valeriotor.BTV.gui;

import java.io.IOException;

import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncAntiqueNBT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.research.ResearchCategories;

public class GuiTablet extends GuiScreen{
	
	/* TO-DO LIST:
	 * Add more inscriptions
	 * Make theorycraft card that accepts completed Tablets
	 * Make Tablets appear as loot in Vanilla and BTV chests
	 * 
	 */
	
	
	private static final int inscriptions = 5;
	private static final ResourceLocation texture = new ResourceLocation(References.MODID + ":textures/gui/tablet.png");
	private static final ResourceLocation up = new ResourceLocation(References.MODID + ":textures/gui/uparrow.png");
	private static final ResourceLocation down = new ResourceLocation(References.MODID + ":textures/gui/downarrow.png");
	private String inscription;
	private String drawnInscription;
	private int odd[] = new int[200];
	private int even[] = new int[200];
	private int oddDiff = 0;
	private int evenDiff = 0;
	private double scaleMultiplier;
	private int xSize = 320;
	private int ySize = 320;
	private EnumHand hand = EnumHand.MAIN_HAND;
	
	@Override
	public void initGui() {
		
		this.scaleMultiplier = ((double)(5 + 2.5*((mc.gameSettings.guiScale - 1) & 3))) / 6.25;
		this.xSize = (int) Math.round(this.width / 3 * scaleMultiplier);
		this.ySize = (int) Math.round(this.height * 0.628 * scaleMultiplier);
		
		
		if(inscription == null) {
			for(int i = 0; i < odd.length; i++) {
				odd[i] = -1;
				even[i] = -1;
			}
			
			
			EntityPlayerSP p = Minecraft.getMinecraft().player;
			int stringNumber = -1;
		
			for(EnumHand h : EnumHand.values()) {
				if(p.getHeldItem(h).getItem() == ItemRegistry.tablet) {
					if(p.getHeldItem(h).getTagCompound().hasKey("inscription")) {
						stringNumber = p.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getInteger("inscription");
						break;
					}else {
						int c = this.getUndiscoveredInscription();
						if(c == -1) c = p.world.rand.nextInt(inscriptions);
						BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncAntiqueNBT("inscription", c));
						stringNumber = c;
					}
					this.hand = h;
				}
			}
			if(stringNumber == -1) {
				this.mc.displayGuiScreen((GuiScreen)null);
				return;
			}	
		
			this.inscription = I18n.format(String.format("gui.tablet.inscription%d", stringNumber));
			this.drawnInscription = this.inscription;
			char characters[] = this.inscription.toCharArray();
			int count = 0;
			for(int i = 0; i < characters.length; i++) {
				char c = characters[i];
				if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
					if((count & 1) == 0) {
						even[count/2] = i;
					}else if((count & 1) == 1) {
						odd[count/2] = i;
					}
					count++;
				}
			}
			
			if(!p.getHeldItem(hand).getTagCompound().hasKey("oddDiff") || !p.getHeldItem(hand).getTagCompound().hasKey("evenDiff")) {
				while(oddDiff == 0) oddDiff = p.world.rand.nextInt(26);
				while(evenDiff == 0) evenDiff = p.world.rand.nextInt(26);
			}else {
				this.oddDiff = p.getHeldItem(hand).getTagCompound().getInteger("oddDiff");
				this.evenDiff = p.getHeldItem(hand).getTagCompound().getInteger("evenDiff");
			}
			
			
			
			this.moveLetters();
			
		}
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawModalRectWithCustomSizedTexture((int) (this.width/2 - xSize/2), (int) Math.round(this.height/2 - ySize / 2), 0, 0, xSize, ySize, xSize, ySize);
		
		String[] strings = GuiHelper.splitStrings(this.drawnInscription);
		for(int i = 0; i < strings.length; i++)
		drawCenteredString(Minecraft.getMinecraft().fontRenderer, strings[i], this.width/2, this.height / 2 + 25 * i - ySize/3, 0xCDC6C6);
		int sB = this.getButtonFromMouseCoords(mouseX, mouseY);
		Minecraft.getMinecraft().renderEngine.bindTexture(up);
		drawModalRectWithCustomSizedTexture((int) (this.width/2 - (int)Math.round((double)11*xSize/48)), (int) Math.round(this.height/2 + ySize / 5), 0, sB-- == 0 ? 24 : 0, xSize/8, ySize/16, xSize/8, ySize/8);
		drawModalRectWithCustomSizedTexture((int) (this.width/2 + (int)Math.round((double)5*xSize/48)), (int) Math.round(this.height/2 + ySize / 5), 0, sB-- == 0 ? 24 : 0, xSize/8, ySize/16, xSize/8, ySize/8);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(down);
		drawModalRectWithCustomSizedTexture((int) (this.width/2 - (int)Math.round((double)11*xSize/48)), (int) Math.round(this.height/2 + 11*ySize / 40), 0, sB-- == 0 ? 24 : 0, xSize/8, ySize/16, xSize/8, ySize/8);
		drawModalRectWithCustomSizedTexture((int) (this.width/2 + (int)Math.round((double)5*xSize/48)), (int) Math.round(this.height/2 + 11*ySize / 40), 0, sB-- == 0 ? 24 : 0, xSize/8, ySize/16, xSize/8, ySize/8);
		
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void moveLetters() {
		char characters[] = this.inscription.toCharArray();
		char drawnCharacters[] = this.drawnInscription.toCharArray();
		for(int i = 0; this.even[i] != -1; i++) {
			char c = characters[this.even[i]];
			if(c >= 'A' && c <= 'Z') {
				drawnCharacters[this.even[i]] = (char) (((int)c - 'A' + this.evenDiff)%26 + 'A');
			}else if(c >= 'a' && c <= 'z') {
				drawnCharacters[this.even[i]] = (char) (((int)c - 'a' + this.evenDiff)%26 + 'a');
			}
		}
		
		for(int i = 0; this.odd[i] != -1; i++) {
			char c = characters[this.odd[i]];
			if(c >= 'A' && c <= 'Z') {
				drawnCharacters[this.odd[i]] = (char) ((c - 'A' + this.oddDiff)%26 + 'A');
			}else if(c >= 'a' && c <= 'z') {
				drawnCharacters[this.odd[i]] = (char) ((c - 'a' + this.oddDiff)%26 + 'a');
			}
		}
		this.drawnInscription = String.copyValueOf(drawnCharacters);
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		
		if(this.evenDiff == 0 && this.oddDiff == 0 && !p.getHeldItem(hand).getTagCompound().getBoolean("finished")) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncAntiqueNBT("evenDiff", this.evenDiff));
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncAntiqueNBT("oddDiff", this.oddDiff));
			Minecraft.getMinecraft().player.playSound(BTVSounds.flute, 1, 1);
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncAntiqueNBT("finished", 1));
			
		}
		
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 1) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncAntiqueNBT("evenDiff", this.evenDiff));
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncAntiqueNBT("oddDiff", this.oddDiff));
			this.mc.displayGuiScreen((GuiScreen)null);
			return;
		}else if(this.oddDiff == 0 && this.evenDiff == 0) {
			return;
		}else if(keyCode == 203 || keyCode == 205) {
			this.evenDiff = (this.evenDiff + (keyCode - 204)) % 26;
		}else if(keyCode == 200 || keyCode == 208) {
			this.oddDiff = (this.oddDiff - (keyCode - 204)/4) % 26;
		}
		this.moveLetters();
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int sB = this.getButtonFromMouseCoords(mouseX, mouseY);
		if(sB > -1 && (this.oddDiff != 0 || this.evenDiff != 0)) {
			switch(sB) {
			case 0: this.evenDiff++; break;
			case 1: this.oddDiff++; break;
			case 2: this.evenDiff--; break;
			case 3: this.oddDiff--; break;
			}
			this.oddDiff = this.oddDiff % 26;
			this.evenDiff = this.evenDiff % 26;
			this.moveLetters();
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private int getButtonFromMouseCoords(int mouseX, int mouseY) {
		
		if(mouseX >= (this.width/2 - (int)Math.round((double)11*xSize/48)) && mouseX <= (this.width/2 - (int)Math.round((double)5*xSize/48)) &&
		   mouseY >= Math.round(this.height/2 + 1 * ySize / 5) && mouseY <= Math.round(this.height/2 + 21*ySize / 80)){
			return 0;
		}
		if(mouseX >= (this.width/2 + (int)Math.round((double)5*xSize/48)) && mouseX <= (this.width/2 + (int)Math.round((double)11*xSize/48)) &&
		   mouseY >= Math.round(this.height/2 + 1 * ySize / 5) && mouseY <= Math.round(this.height/2 + 21*ySize / 80)){
			return 1;
		}
		if(mouseX >= (this.width/2 - (int)Math.round((double)11*xSize/48)) && mouseX <= (this.width/2 - (int)Math.round((double)5*xSize/48)) &&
		   mouseY >= Math.round(this.height/2 + 11 * ySize / 40) && mouseY <= Math.round(this.height/2 + 27*ySize / 40)){
			return 2;
		}
		if(mouseX >= (this.width/2 + (int)Math.round((double)5*xSize/48)) && mouseX <= (this.width/2 + (int)Math.round((double)11*xSize/48)) &&
		   mouseY >= Math.round(this.height/2 + 11 * ySize / 40) && mouseY <= Math.round(this.height/2 + 27*ySize / 40)){
			return 3;
		}
		
		return -1;
	}
	
	private int getUndiscoveredInscription() {
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		int discovered = 0;
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
		for(int i = 0; i < inscriptions; i++) {
			if(k.isResearchKnown(String.format("inscription%d", i))) discovered++;
		}
		int undiscovered = inscriptions - discovered;
		int count = 0;
		if(undiscovered == 0) return -1; 
		int theOne = p.world.rand.nextInt(undiscovered);
		for(int i = 0; i < inscriptions; i++) {
			if(!k.isResearchKnown(String.format("inscription%d", i))) {
				if(theOne == count) {
					return i;
				}else count++;
			}
		}
		return -1;
	}
	
}
