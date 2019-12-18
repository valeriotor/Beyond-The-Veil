package com.valeriotor.BTV.worship;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.valeriotor.BTV.entities.IPlayerGuardian;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CrawlerWorship {
	
	private int timer = 200;
	private int strength = 0;
	private WorshipType wType = WorshipType.DEFAULT;
	
	public boolean update() {
		this.timer--;
		return this.isDone();
	}
	
	public boolean isDone() {
		return this.timer <= 0;
	}
	
	public void resetTimer() {
		this.timer = 200;
	}
	
	public CrawlerWorship setWorshipType(WorshipType type) {
		this.wType = type;
		return this;
	}
	
	public WorshipType getWorshipType() {
		return this.wType;
	}
	
	public CrawlerWorship setStrength(int strength) {
		this.strength = strength;
		return this;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getBaubleCooldown(int defaultVal) {
		return Math.max(defaultVal / 2, defaultVal * (10 - 1 - this.strength) / 10);
	}
	
	public int getPowerCooldown(int defaultVal) {
		return Math.max(defaultVal / 2, defaultVal * (10 - 1 - this.strength) / 10);
	}
	
	public int getDreamBonusStrength() {
		return this.strength;
	}
	
	public int getDeepOneBonus() {
		return (this.strength +  1) / 2;
	}
	
	public void empowerMinions(EntityPlayer p) {
		if(this.wType != WorshipType.SACRIFICE) return;
		List<EntityLiving> minions = p.world.getEntities(EntityLiving.class, e -> (e instanceof IPlayerGuardian) && e.getDistance(p) < 35);
		boolean crit = criticalHeal(p.getRNG());
		for(EntityLiving e : minions) {
			int maxStrength = Math.min(5, this.strength);
			e.heal((6 + 2 * maxStrength) * (crit ? 2 : 1));
			if(this.strength > 0) {
				e.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100, maxStrength));
				e.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, maxStrength / 2));
			}
		}
	}
	
	public boolean criticalHeal(Random r) {
		return this.wType == WorshipType.SACRIFICE && r.nextInt(10) < Math.min(this.strength, 5);
	}
	
	public boolean sacrificeAltar() {
		return this.wType == WorshipType.SACRIFICE;
	}
	
	public boolean monsterSpawnEgg() {
		return this.wType == WorshipType.SACRIFICE && this.strength > 0;
	}
	
	public void getSelfHarmBonuses(EntityPlayer p) {
		if(this.wType != WorshipType.PENITENCE) return;
		Item i = p.getHeldItemMainhand().getItem();
		if(i instanceof ItemSword) {
			p.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 3));
		} else if(i instanceof ItemAxe || i instanceof ItemSpade || i instanceof ItemPickaxe) {
			p.addPotionEffect(new PotionEffect(MobEffects.HASTE, 300, 3));
		} else if(i instanceof ItemHoe) {
			World w = p.world;
			for(int x = -4; x <= 4; x++) {
				for(int z = -4; z <= 4; z++) {
					for(int y = -1; y <= 1; y++) {
						IBlockState state = w.getBlockState(p.getPosition().add(x, y, z));
						if(state.getBlock() instanceof IGrowable) {
							((IGrowable)state.getBlock()).grow(w, w.rand, p.getPosition().add(x, y, z), state);
						}
					}
				}
			}
		}
	}
	
	public boolean improvesCrownOfThorns() {
		return this.wType == WorshipType.PENITENCE && this.strength > 0;
	}
	
	
	
	public static enum WorshipType {
		DEFAULT,
		SACRIFICE,
		PENITENCE;
	}
}
