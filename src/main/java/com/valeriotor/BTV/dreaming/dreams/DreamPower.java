package com.valeriotor.BTV.dreaming.dreams;

import java.util.Collection;

import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DreamPower extends Dream{

	public DreamPower(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		if(!DreamHandler.youDontKnowDream(p, "metallum")) return false;
		int lvl = DreamHandler.getDreamLevel(p);
		int increase = 1 + (lvl+2)/3;
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		effects.forEach(effect -> p.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getPotion() == MobEffects.RESISTANCE ? Math.max(3, effect.getAmplifier() + increase) : effect.getAmplifier()+increase)));
		
		return true;
	}

}
