package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageActivatePower;
import com.valeriotor.BTV.network.MessageSyncIntDataToServer;
import com.valeriotor.BTV.proxy.ClientProxy;
import com.valeriotor.BTV.worship.Deities;
import com.valeriotor.BTV.worship.Worship;
import com.valeriotor.BTV.worship.ActivePowers.IActivePower;

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
public class GuiDeityPowers extends GuiOptionWheel{
	
	private boolean[] availableOptions = {false,false,false,false};
	private ResourceLocation[] textures = new ResourceLocation[4];
	private final String guiName;
	
	public GuiDeityPowers() {
		Deities deity = Worship.getSelectedDeity(Minecraft.getMinecraft().player);
		this.guiName = "power." + deity.name().toLowerCase();
		for(int i = 0; i < 4; i++) {
			IActivePower pow = Worship.getSpecificPower(Minecraft.getMinecraft().player, i);
			this.availableOptions[i] = pow.hasRequirement(Minecraft.getMinecraft().player);
			this.textures[i] = pow.getGuiTexture();
		}
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
	protected boolean isEastOptionAvailable() {
		return this.availableOptions[3];
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
	public void doEastAction() {
		this.writeData(3);
	}
	
	@Override
	public ResourceLocation getNorthOptionTexture() {
		return this.textures[0];
	}
	
	@Override
	public ResourceLocation getWestOptionTexture() {
		return this.textures[1];
	}
	
	@Override
	public ResourceLocation getEastOptionTexture() {
		return this.textures[3];
	}
	
	
	private void writeData(int option) {
		this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.SELECTED_POWER, option, false);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncIntDataToServer(PlayerDataLib.SELECTED_POWER, option));
	}
	
	@Override
	public String getGuiLangName() {
		return this.guiName;
	}
	


}
