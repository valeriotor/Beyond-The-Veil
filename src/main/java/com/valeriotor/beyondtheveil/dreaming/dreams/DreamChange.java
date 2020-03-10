package com.valeriotor.beyondtheveil.dreaming.dreams;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DreamChange extends Dream{

	public DreamChange(String name, int priority) {
		super(name, priority);
	}
	
	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		return this.activatePlayer(p, p, w);
	}
	
	@Override
	public boolean activatePlayer(EntityPlayer caster, EntityPlayer target, World w) {
		int lvl = DreamHandler.getDreamLevel(caster);
		Collection<PotionEffect> effects = target.getActivePotionEffects();
		List<PotionEffect> negativeEffects = Lists.newArrayList();
		effects.forEach(effect -> {
			if(effect.getPotion().isBadEffect()) negativeEffects.add(effect);
		});
		negativeEffects.forEach(effect ->{
			Potion newPot = getPositiveCounterpart(effect.getPotion());
			if(newPot != null) target.addPotionEffect(new PotionEffect(getPositiveCounterpart(effect.getPotion()), effect.getDuration()+300*lvl, effect.getAmplifier()+lvl/4));
			target.removePotionEffect(effect.getPotion());
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
