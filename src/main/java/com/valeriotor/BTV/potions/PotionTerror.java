package com.valeriotor.BTV.potions;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.MathHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionTerror extends Potion{

	protected PotionTerror(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(References.MODID + ":terror");
	}
	
	
	@Override
	public void performEffect(EntityLivingBase e, int amplifier) {
		if(e instanceof EntityPlayer) {
			if(MathHelper.getClosestLookedAtEntity((EntityPlayer)e, 7, ent -> ent != e) != null) {
				if(e.world.rand.nextBoolean()) {
					e.rotationYaw += e.world.rand.nextBoolean() ? 60 : -60;
				}
				else {
					e.motionZ = -Math.cos(e.rotationYawHead);
					e.motionX = Math.sin(e.rotationYawHead);
				}
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
