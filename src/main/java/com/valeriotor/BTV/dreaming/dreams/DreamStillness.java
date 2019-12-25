package com.valeriotor.BTV.dreaming.dreams;

import java.util.Collection;

import com.valeriotor.BTV.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DreamStillness extends AbstractDream{

	public DreamStillness(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activate(EntityPlayer p, World w) {
		if(!DreamHandler.youDontKnowDream(p, "metallum")) return false;
		int lvl = DreamHandler.getDreamLevel(p);
		Collection<PotionEffect> effects = p.getActivePotionEffects();
		effects.forEach(effect -> p.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()+3000+500*lvl)));
		
		return true;
	}

}
