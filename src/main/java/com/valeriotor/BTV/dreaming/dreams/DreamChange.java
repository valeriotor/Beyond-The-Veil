package com.valeriotor.BTV.dreaming.dreams;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DreamChange extends AbstractDream{

	public DreamChange(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		if(!DreamHandler.youDontKnowDream(p, "metallum")) return false;
		int lvl = DreamHandler.getDreamLevel(p);
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		List<PotionEffect> negativeEffects = Lists.newArrayList();
		effects.forEach(effect -> {
			if(effect.getPotion().isBadEffect()) negativeEffects.add(effect);
		});
		negativeEffects.forEach(effect ->{
			Potion newPot = getPositiveCounterpart(effect.getPotion());
			if(newPot != null) p.addPotionEffect(new PotionEffect(getPositiveCounterpart(effect.getPotion()), effect.getDuration()+300*lvl, effect.getAmplifier()+lvl/4));
			p.removePotionEffect(effect.getPotion());
		});
		
		return true;
	}
	
	private Potion getPositiveCounterpart(Potion p) {
		if(p == MobEffects.BLINDNESS) return MobEffects.NIGHT_VISION;
		else if(p == MobEffects.HUNGER) return MobEffects.SATURATION;
		else if(p == MobEffects.POISON) return MobEffects.REGENERATION;
		else if(p == MobEffects.MINING_FATIGUE) return MobEffects.HASTE;
		else if(p == MobEffects.WITHER) return MobEffects.REGENERATION;
		else if(p == MobEffects.SLOWNESS) return MobEffects.SPEED;
		else if(p == MobEffects.WEAKNESS) return MobEffects.STRENGTH;
		else if(p == MobEffects.UNLUCK) return MobEffects.LUCK;
		return null;
	}

}
