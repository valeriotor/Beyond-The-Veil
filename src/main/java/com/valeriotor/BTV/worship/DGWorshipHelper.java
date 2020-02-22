package com.valeriotor.BTV.worship;

import static com.valeriotor.BTV.lib.PlayerDataLib.DAGONQUEST;
import static com.valeriotor.BTV.lib.PlayerDataLib.DAGONQUEST2;
import static com.valeriotor.BTV.lib.PlayerDataLib.ENDBATH;
import static com.valeriotor.BTV.lib.PlayerDataLib.FISHQUEST;
import static com.valeriotor.BTV.lib.PlayerDataLib.RITUALQUEST;
import static com.valeriotor.BTV.lib.PlayerDataLib.SLUGS;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

public class DGWorshipHelper {
	
	public static final HashMap<String, DGResearch> researches = new HashMap<>(); 
	private static final HashMap<UUID, Double> defenseMultipliers = new HashMap<>();
	private static final HashMap<UUID, Double> attackMultipliers = new HashMap<>();
	private static final HashMap<UUID, Integer> dreamPower = new HashMap<>();
	
	public static void loadDreamerResearch() {
		researches.put(SLUGS, new DGResearch("SLUGS", 0, 0, 30, true, 0));
		researches.put(FISHQUEST, new DGResearch("CANOE", 0.5, -0.05, 30, false, 1));
		researches.put(RITUALQUEST, new DGResearch("BAPTISM", 0.1, -0.05, 0, true, 1));
		researches.put(DAGONQUEST, new DGResearch("ALLIANCE", 0.25, -0.1, 30, false, 2));
		researches.put(DAGONQUEST2, new DGResearch("METAMORPHOSIS", 0.25, -0.05, 35, false, 1));
		researches.put(ENDBATH, new DGResearch("WATERWALKING", 0.1, -0.05, 25, false, 1));
	}
	
	public static void levelUp(EntityPlayer p) {
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		int slugs = data.getOrSetInteger(PlayerDataLib.SLUGS, 0, false);
		boolean atLeastOne = false, notEnoughSlugs = false;
		for(Entry<String, DGResearch> entry : researches.entrySet()) {
			DGResearch res = entry.getValue();
			if((data.getString(entry.getKey()) || entry.getKey().equals(SLUGS)) && res.canBeUnlocked(p) && !res.isRequirementUnlocked(p)) {
				if(slugs >= res.getRequiredSlugs()) {
					res.unlock(p);
					slugs -= res.getRequiredSlugs();
					//data.removeString(entry.getKey()); Do I need this?
					atLeastOne = true;
					if(entry.getKey().equals(DAGONQUEST)) p.sendMessage(new TextComponentTranslation("interact.idol.power"));
					if(entry.getKey().equals(DAGONQUEST2)) {
						p.sendMessage(new TextComponentTranslation("interact.idol.power"));
						Worship.getSpecificPower(p, 1).activatePower(p);
					}
					if(entry.getKey().equals(ENDBATH)) p.sendMessage(new TextComponentTranslation("interact.idol.power"));
				} else {
					notEnoughSlugs = true;
				}
			}
		}
		if(notEnoughSlugs && !atLeastOne) p.sendMessage(new TextComponentTranslation("interact.idol.moreslugs"));
		SyncUtil.addIntDataOnServer(p, false, SLUGS, slugs);
		calculateModifier(p);
		
	}
	
	public static int getRequiredSlugs(int lvl) {
		return (int) Math.ceil(90*(1 - Math.pow(1.5, -lvl)));
	}
	
	public static boolean hasRequiredQuest(EntityPlayer p, int lvl) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(getRequiredQuest(lvl));
	}
	
	public static String getRequiredQuest(int lvl) {
		switch(lvl) {
			case 2: return FISHQUEST;
			case 3: return RITUALQUEST;
			default: return null;
		}
	}
	
	public static boolean hasWaterPowers(EntityPlayer p) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(PlayerDataLib.RITUALQUEST);
	}
	
	public static boolean canSummon(EntityPlayer p) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("!ALLIANCE");
	}
	
	public static boolean canTransform(EntityPlayer p) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("!METAMORPHOSIS");
	}
	
	public static boolean canTeleport(EntityPlayer p) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("!WATERWALKING");
	}
	
	public static void removeModifiers(EntityPlayer p) {
		attackMultipliers.remove(p.getPersistentID());
		defenseMultipliers.remove(p.getPersistentID());
		dreamPower.remove(p.getPersistentID());
	}
	
	public static void calculateModifier(EntityPlayer p) {
		double attack = 1;
		double defense = 1;
		int dream = 0;
		for(Entry<String, DGResearch> entry : researches.entrySet()) {
			if(entry.getValue().isUnlocked(p)) {
				attack += entry.getValue().attackIncrement;
				defense += entry.getValue().defenseIncrement;
				if(entry.getValue().improvesDreams) dream++;
			}
		}
		attackMultipliers.put(p.getPersistentID(), attack);
		defenseMultipliers.put(p.getPersistentID(), defense);			
		dreamPower.put(p.getPersistentID(), dream);
	}
	
	public static double getAttackModifier(EntityPlayer p) {
		if(!attackMultipliers.containsKey(p.getPersistentID()))calculateModifier(p);
		return attackMultipliers.get(p.getPersistentID());
	}
	
	public static double getDefenseModifier(EntityPlayer p) {
		if(!defenseMultipliers.containsKey(p.getPersistentID()))calculateModifier(p);
		return defenseMultipliers.get(p.getPersistentID());
	}
	
	public static int getDreamPower(EntityPlayer p) {
		if(!dreamPower.containsKey(p.getPersistentID())) calculateModifier(p);
		return dreamPower.get(p.getPersistentID());
	}
	
	public static boolean areDeepOnesFriendly(EntityPlayer p) {
		return researches.get(DAGONQUEST).isUnlocked(p);
	}
	
	public static class DGResearch {
		private final String key;
		public final double attackIncrement;
		public final double defenseIncrement;
		private final int requiredSlugs;
		public final boolean improvesDreams;
		private final int stage;
		
		public DGResearch(String key, double attackIncrement, double defenseIncrement, int requiredSlugs, boolean improvesDreams, int stage) {
			this.key = key;
			this.attackIncrement = attackIncrement;
			this.defenseIncrement = defenseIncrement;
			this.requiredSlugs = requiredSlugs;
			this.improvesDreams = improvesDreams;
			this.stage = stage;
		}
		
		public boolean canBeUnlocked(EntityPlayer p) {
			return ResearchUtil.getResearchStage(p, key) == stage;
		}
		
		public boolean isUnlocked(EntityPlayer p) {
			return ResearchUtil.getResearchStage(p, key) > stage;
		}
		
		public boolean isRequirementUnlocked(EntityPlayer p) {
			return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("!".concat(key));
		}
		
		public int getRequiredSlugs() {
			return this.requiredSlugs;
		}
		
		public void unlock(EntityPlayer p) {
			SyncUtil.addStringDataOnServer(p, false, "!".concat(key));
		}
		
	}
	
	// Remove old keys from pData
}
