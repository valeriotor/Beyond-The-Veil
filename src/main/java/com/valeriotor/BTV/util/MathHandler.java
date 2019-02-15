package com.valeriotor.BTV.util;

import net.minecraft.entity.player.EntityPlayer;

public class MathHandler {
	
	/** Removes experience from a player (since the vanilla method addExperience won't do that).
	 * 
	 * @param p The player
	 * @param amount The absolute value of the xp to remove.
	 */
	public static void removeExperience(EntityPlayer p, int amount) {
		amount = Math.abs(amount);
		int level = p.experienceLevel;
		int exp = (int)(p.experience * getExperienceToLevel(level));
		int expTot = p.experienceTotal;
		
		if(amount >= expTot) {
			p.experienceLevel = 0;
			p.experience = 0;
			p.experienceTotal = 0;
		}else if(exp > amount) {
			p.addExperience(-amount);
		}else {
			int sum = exp;
			while(sum < amount) {
				sum += getExperienceToLevel(--level);
			}
			p.experienceLevel = level;
			p.experienceTotal = expTot - amount;
			p.experience = (float)(p.experienceTotal - getTotalExperience(level))/getExperienceToLevel(level);
		}
		
	}
	
	/** Calculates the amount of experience required to reach the next XP level,
	 *  based on the formulas in the Minecraft wiki.
	 * 
	 * @param fromLevel The current level
	 * @return The experience needed to reach the next level
	 */
	public static int getExperienceToLevel(int fromLevel) {
		if(fromLevel < 16) {
			return 2 * fromLevel + 7;
		}else if(fromLevel < 31){
			return 5 * fromLevel - 38;
		}else {
			return 9 * fromLevel - 158;
		}
	}
	
	/** Calculates the total experience required to reach a certain level, starting from zero,
	 *  based on the formulas in the Minecraft wiki.
	 * 
	 * @param level The exact level that needs to be unlocked, with no extra experience  
	 */
	public static int getTotalExperience(int level) {
		if(level < 16) {
			return level * level + 6 * level;
		}else if(level < 31){
			return (5 * level * level - 81 * level) / 2 + 360;
		}else {
			return (9 * level * level - 325 * level) / 2 + 2220;
		}
	}
	
}
