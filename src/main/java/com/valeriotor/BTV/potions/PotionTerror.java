package com.valeriotor.BTV.potions;

import java.util.List;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.BTVEntityRegistry;
import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.MathHelper;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class PotionTerror extends Potion{

	protected PotionTerror(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(References.MODID + ":terror");
	}
	
	
	@Override
	public void performEffect(EntityLivingBase e, int amplifier) {
		if(e instanceof EntityPlayer) {
			ItemStack stack = BaublesApi.getBaublesHandler((EntityPlayer)e).getStackInSlot(4);
			if(stack.getItem() == ItemRegistry.bone_tiara && 
			  ((EntityPlayer)e).getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, 4), 1, false) == 1	) return;
			
			EntityLivingBase entity = MathHelper.getClosestLookedAtEntity((EntityPlayer)e, 7, ent -> ent != e);
			if(entity != null && BTVEntityRegistry.isScaryEntity(entity)) {
				if(e.world.rand.nextBoolean()) {
					e.rotationYaw += e.world.rand.nextBoolean() ? 60 : -60;
				}
				else {
					double xDist = entity.posX - e.posX;
					double zDist = entity.posZ - e.posZ;
					double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
					e.motionZ = - zDist / dist;
					e.motionX = - xDist / dist;
				}
			}
		}else if(!BTVEntityRegistry.isFearlessEntity(e)){
			List<EntityLivingBase> ents = e.world.getEntities(EntityLivingBase.class, ent -> ent.getDistance(e) < 7 && BTVEntityRegistry.isScaryEntity(ent));
			for(EntityLivingBase entity : ents) {
				double xDist = entity.posX - e.posX;
				double zDist = entity.posZ - e.posZ;
				double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
				e.motionZ = - zDist / dist;
				e.motionX = - xDist / dist;
			}
		}
		super.performEffect(e, amplifier);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		if(duration % (15/ (amplifier+1) + 1) == 0) return true;
		return false;
	}

}
