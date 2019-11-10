package com.valeriotor.BTV.worship.ActivePowers;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncTransformedPlayer;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;

public class TransformDeepOne implements IActivePower{
	
	private TransformDeepOne() {}
	private static final TransformDeepOne INSTANCE = new TransformDeepOne();
	public static IActivePower getInstance() {return INSTANCE;}
	
	@Override
	public boolean activatePower(EntityPlayer p) {
		if(!DGWorshipHelper.canTransform(p)) return false;
		boolean transformed = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED);
		if(transformed) p.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString(PlayerDataLib.TRANSFORMED);
		else p.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(PlayerDataLib.TRANSFORMED, false);
		((WorldServer)p.world).getEntityTracker().sendToTrackingAndSelf(p, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncTransformedPlayer(p.getPersistentID(), !transformed)));
		return true;
	}

	@Override
	public int getCooldownTicks() {
		return 300;
	}

	@Override
	public Deities getDeity() {
		return Deities.GREATDREAMER;
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public boolean hasRequirement(EntityPlayer p) {
		return DGWorshipHelper.canTransform(p);
	}
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID + ":textures/gui/powers/transform_deep_one.png");
	
	@Override
	public ResourceLocation getGuiTexture() {
		return TEXTURE;
	}
	

}
