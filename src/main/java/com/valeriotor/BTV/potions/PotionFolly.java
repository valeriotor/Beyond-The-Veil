package com.valeriotor.BTV.potions;

import com.valeriotor.BTV.lib.References;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionFolly extends Potion{

	protected PotionFolly(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(References.MODID + ":folly");
	}
	
	@Override
	public void performEffect(EntityLivingBase e, int amplifier) {
		if(e instanceof EntityMob) {
			
			((EntityMob) e).setAttackTarget(null);
			e.setRotationYawHead(e.world.rand.nextInt(360));
		}
		if(e instanceof EntityPlayer) e.rotationYaw += e.world.rand.nextInt(20 + 10*amplifier)- 10 - 5*amplifier;
		e.rotationPitch += e.world.rand.nextInt(20 + 10*amplifier)- 10 - 5*amplifier + (e.rotationPitch > 100 ? -10 : (e.rotationPitch < -100 ? +10 : 0));
		super.performEffect(e, amplifier);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

}
