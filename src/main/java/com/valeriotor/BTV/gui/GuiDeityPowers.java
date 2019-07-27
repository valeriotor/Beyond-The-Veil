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
	
	private static final ResourceLocation GD_NORTH_TEXTURE = new ResourceLocation(References.MODID + ":textures/gui/powers/summon_deep_ones.png");
	private static final ResourceLocation GD_EAST_TEXTURE = new ResourceLocation(References.MODID + ":textures/gui/powers/water_teleport.png");
	private static final ResourceLocation GD_WEST_TEXTURE = new ResourceLocation(References.MODID + ":textures/gui/powers/transform_deep_one.png");
	
	private final int level;
	private final Deities deity;
	private boolean[] availableOptions = {false,false,false,false};
	private final String guiName;
	
	public GuiDeityPowers() {
		this.level = Worship.getSelectedDeityLevel(Minecraft.getMinecraft().player);
		this.deity = Worship.getSelectedDeity(Minecraft.getMinecraft().player);
		this.guiName = "guiPower" + this.deity.name();
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
		switch(deity) {
		case GREATDREAMER: return GD_NORTH_TEXTURE;
		default: return null;
		}
	}
	
	@Override
	public ResourceLocation getWestOptionTexture() {
		switch(deity) {
		case GREATDREAMER: return GD_WEST_TEXTURE;
		default: return null;
		}
	}
	
	@Override
	public ResourceLocation getEastOptionTexture() {
		switch(deity) {
		case GREATDREAMER: return GD_EAST_TEXTURE;
		default: return null;
		}
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
