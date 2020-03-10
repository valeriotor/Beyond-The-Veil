package com.valeriotor.beyondtheveil.dreaming.dreams;

import java.util.Collection;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DreamStillness extends Dream{

	public DreamStillness(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		return this.activatePlayer(p, p, w);
	}
	
	@Override
	public boolean activatePlayer(EntityPlayer p, EntityPlayer target, World w) {
		int lvl = DreamHandler.getDreamLevel(p);
		Collection<PotionEffect> effects = target.getActivePotionEffects();
		effects.forEach(effect -> target.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration()+3000+500*lvl)));
		
		return true;
	}

}
