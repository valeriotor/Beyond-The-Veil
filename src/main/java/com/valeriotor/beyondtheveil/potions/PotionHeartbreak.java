package com.valeriotor.beyondtheveil.potions;

import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;

public class PotionHeartbreak extends Potion{

	protected PotionHeartbreak(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(References.MODID + ":heartbreak");
	}
	
	@Override
	public void performEffect(EntityLivingBase e, int amplifier) {
		float hp = getMaxHp(e, amplifier);
		if(e.getHealth() > hp) e.setHealth(hp);
		super.performEffect(e, amplifier);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return (duration & 7) == 0;
	}
	
	public static float getMaxHp(EntityLivingBase e, int amplifier) {
		return Math.max(4, e.getMaxHealth() - (amplifier+1)*4);
	}
	
	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase e,
			AbstractAttributeMap attributeMapIn, int amplifier) {
		super.removeAttributesModifiersFromEntity(e, attributeMapIn, amplifier);
		e.heal(e.getMaxHealth()-getMaxHp(e,	amplifier));
	}

}
