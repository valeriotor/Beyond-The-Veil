package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.items.baubles.IActiveBauble;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncIntDataToServer;
import com.valeriotor.BTV.proxy.ClientProxy;
import com.valeriotor.BTV.worship.ActivePowers.IActivePower;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiActiveBauble extends GuiOptionWheel{
	
	
		
	private Item[] baubles = {null,null,null,null};
	private ResourceLocation[] textures = {null,null,null,null};
	
	public GuiActiveBauble() {
		if(Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED)) return;
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler(Minecraft.getMinecraft().player);
		for(int i = 0; i < 4; i++) {
			ItemStack stack = handler.getStackInSlot(i);
			if(stack != null && stack.getItem() != Items.AIR) {
				baubles[i] = stack.getItem();
				System.out.println(i + ": " + stack.getDisplayName());
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
				String[] start = model.getParticleTexture().getIconName().split(":");
				String built = new StringBuilder(start[0]).append(":textures/").append(start[1]).append(".png").toString();
				textures[i] = new ResourceLocation(built);
			}
		}
	}
	
	@Override protected boolean isNorthOptionAvailable() {return baubles[0] != null;}
	@Override protected boolean isWestOptionAvailable() {return baubles[1] != null;}
	@Override protected boolean isSouthOptionAvailable() {return baubles[3] != null;}
	@Override protected boolean isEastOptionAvailable() {return baubles[2] != null;}
	
	@Override protected boolean isNorthOptionGreyedOut() {return !(baubles[0] instanceof IActiveBauble);}
	@Override protected boolean isWestOptionGreyedOut() {return !(baubles[1] instanceof IActiveBauble);}
	@Override protected boolean isSouthOptionGreyedOut() {return !(baubles[3] instanceof IActiveBauble);}
	@Override protected boolean isEastOptionGreyedOut() {return !(baubles[2] instanceof IActiveBauble);}
	
	@Override public ResourceLocation getNorthOptionTexture() {return textures[0];}
	@Override public ResourceLocation getWestOptionTexture() {return textures[1];}
	@Override public ResourceLocation getSouthOptionTexture() {return textures[3];}
	@Override public ResourceLocation getEastOptionTexture() {return textures[2];}
	
	@Override public void doNorthAction() {this.writeData(0);}
	@Override public void doWestAction() {this.writeData(1);}
	@Override public void doSouthAction() {this.writeData(3);}
	@Override public void doEastAction() {this.writeData(2);}
	
	private void writeData(int option) {
		this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.SELECTED_BAUBLE, option, false);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncIntDataToServer(PlayerDataLib.SELECTED_BAUBLE, option));
	}
	
	@Override
	public String getGuiLangName() {
		return "guiBauble";
	}
		
	
}
