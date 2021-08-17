package com.valeriotor.beyondtheveil.dreaming.dreams;

import java.util.List;

import com.valeriotor.beyondtheveil.potions.PotionRegistry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DreamHeartbreak extends Dream{

	public DreamHeartbreak(String name, int priority) {
		super(name, priority);
	}
	
	@Override
	public boolean activate(EntityPlayer p, World w) {
		p.addPotionEffect(new PotionEffect(PotionRegistry.heartbreak, 60*20));
		this.activatePos(p, w, p.getPosition());
		return true;
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		List<EntityLiving> ents = w.getEntities(EntityLiving.class, e -> e.getDistanceSq(pos) < 900);
		if(!ents.isEmpty()) {
			ents.forEach(e -> e.addPotionEffect(new PotionEffect(PotionRegistry.heartbreak, 60*20)));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean activatePlayer(EntityPlayer caster, EntityPlayer target, World w) {
		if(target != null) {
			target.addPotionEffect(new PotionEffect(PotionRegistry.heartbreak, 60*20));
			return true;
		}
		return false;
	}

}
