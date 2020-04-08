package com.valeriotor.beyondtheveil.worship.ActivePowers;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlaySound;
import com.valeriotor.beyondtheveil.network.MessageStepAssist;
import com.valeriotor.beyondtheveil.network.MessageSyncPlayerRender;
import com.valeriotor.beyondtheveil.util.SyncUtil;
import com.valeriotor.beyondtheveil.worship.DGWorshipHelper;
import com.valeriotor.beyondtheveil.worship.Deities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;

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
			((WorldServer)p.world).getEntityTracker().sendToTrackingAndSelf(p, BTVPacketHandler.INSTANCE.getPacketFrom(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.deep_one_transform), p.getPosition().toLong())));
		}
		((WorldServer)p.world).getEntityTracker().sendToTrackingAndSelf(p, BTVPacketHandler.INSTANCE.getPacketFrom(new MessageSyncPlayerRender(p.getPersistentID(), !transformed, MessageSyncPlayerRender.Type.DEEPONE)));
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
	
	public static void applyAttributes(EntityPlayer p) {
		Multimap<String, AttributeModifier> map = getModifiers(p);
		p.getAttributeMap().applyAttributeModifiers(map);
		BTVPacketHandler.INSTANCE.sendTo(new MessageStepAssist(true), (EntityPlayerMP)p);
		p.heal(1);
	}
	
	public static void removeAttributes(EntityPlayer p) {
		Multimap<String, AttributeModifier> map = getModifiers(p);
		p.getAttributeMap().removeAttributeModifiers(map);
		BTVPacketHandler.INSTANCE.sendTo(new MessageStepAssist(false), (EntityPlayerMP)p);
	}
	
	private static Multimap<String, AttributeModifier> getModifiers(EntityPlayer p) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		if(!data.hasKeyedString("transformuuid")) {
			data.addKeyedString("transformuuid", MathHelper.getRandomUUID().toString());
		}
		UUID u = UUID.fromString(data.getKeyedString("transformuuid"));
		Multimap<String, AttributeModifier> map = HashMultimap.create();
		map.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(u, "transformspeed", 1.2, 1));
		map.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(u, "transformhealth", 20, 0));
		map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(u, "transformattack", 6, 0));
		return map;
	}

}
