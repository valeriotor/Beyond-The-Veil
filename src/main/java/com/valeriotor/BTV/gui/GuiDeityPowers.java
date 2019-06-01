package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageActivatePower;
import com.valeriotor.BTV.network.MessageSyncIntDataToServer;
import com.valeriotor.BTV.proxy.ClientProxy;
import com.valeriotor.BTV.worship.Deities;
import com.valeriotor.BTV.worship.Worship;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDeityPowers{
	
	private int timePressed = 0;
	private final Minecraft mc;
	private static final ResourceLocation GD_NORTH_TEXTURE = new ResourceLocation(References.MODID + ":textures/gui/powers/summon_deep_ones.png");
	private static final ResourceLocation GD_WEST_TEXTURE = new ResourceLocation(References.MODID + ":textures/items/blackjack.png");
	
	public GuiDeityPowers(Minecraft mc) {
		this.mc = mc;
	}
	
	@SubscribeEvent
	public void inputRead(ClientTickEvent event) {
		if(event.phase.equals(Phase.END)) {
			EntityPlayerSP p = Minecraft.getMinecraft().player;
			if(!this.mc.isGamePaused() && p != null && this.mc.currentScreen == null) {
				if(ClientProxy.handler.power.isKeyDown()) {
					this.timePressed++;
					if(this.timePressed >= 15) {
						this.timePressed = 0;
						this.mc.displayGuiScreen(new GuiGDPowers());
					}
				} else {
					if(this.timePressed >= 1) {
						this.timePressed = 0;
						//System.out.println("Selected power: " + p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger("currentPower", -1, false));
						BTVPacketHandler.INSTANCE.sendToServer(new MessageActivatePower());
					}
				}
				//System.out.println("Current power: " + p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger("currentPower", -1, false));
			}
				
		}
	}
	
	
	public static class GuiGDPowers extends GuiOptionWheel {

		private final int level;
		private boolean[] availableOptions = {false,false,false,false};
		
		public GuiGDPowers() {
			this.level = Worship.getSelectedDeityLevel(Minecraft.getMinecraft().player);
			for(int i = 0; i < 4; i++) 
				this.availableOptions[i] = Worship.getSpecificPower(Minecraft.getMinecraft().player, i).hasRequirement(Minecraft.getMinecraft().player);
		}
		
		@Override
		protected boolean isNorthOptionAvailable() {
			return this.availableOptions[0];
		}
		
		@Override
		protected boolean isWestOptionAvailable() {
			return this.availableOptions[1];
		}
		
		@Override
		public void doNorthAction() {
			this.writeData(0);
		}
		
		@Override
		public void doWestAction() {
			this.writeData(1);
		}
		
		@Override
		public ResourceLocation getNorthOptionTexture() {
			return GD_NORTH_TEXTURE;
		}
		
		@Override
		public ResourceLocation getWestOptionTexture() {
			return GD_WEST_TEXTURE;
		}
		
		private void writeData(int option) {
			this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger("currentPower", option, false);
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncIntDataToServer("currentPower", option));
			this.mc.displayGuiScreen((GuiScreen)null);
		}
		
		@Override
		public String getGuiLangName() {
			return "guiGDPower";
		}
		
	}

}
