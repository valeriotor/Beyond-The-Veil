package com.valeriotor.BTV.worship.ActivePowers;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncTransformedPlayer;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TransformDeepOne implements IActivePower{
	
	private TransformDeepOne() {}
	private static final TransformDeepOne INSTANCE = new TransformDeepOne();
	public static IActivePower getInstance() {return INSTANCE;}
	
	@Override
	public boolean activatePower(EntityPlayer p) {
		if(!DGWorshipHelper.canTransform(p)) return false;
		boolean transformed = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.TRANSFORMED);
		if(transformed) {
			SyncUtil.removeStringDataOnServer(p, PlayerDataLib.TRANSFORMED);
			removeAttributes(p);
			p.capabilities.isFlying = false;
		}
		else {
			SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.TRANSFORMED);
			applyAttributes(p);
		}
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
	

	public static final AttributeModifier SPEED_MOD = new AttributeModifier("speedmod", 1.2, 1);
	public static final AttributeModifier HEALTH_MOD = new AttributeModifier("healthmod", 20, 0);
	public static final AttributeModifier ATTACK_MOD = new AttributeModifier("attackmod", 9, 0);
	
	public static void applyAttributes(EntityPlayer p) {
		if(!p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MOD)) {
			p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(SPEED_MOD);
			p.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(HEALTH_MOD);
			p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(ATTACK_MOD);
		}
	}
	
	public static void removeAttributes(EntityPlayer p) {
		if(p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(SPEED_MOD)) {
			p.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED_MOD);
			p.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(HEALTH_MOD);
			p.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(ATTACK_MOD);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean stepAssist = false;

}
