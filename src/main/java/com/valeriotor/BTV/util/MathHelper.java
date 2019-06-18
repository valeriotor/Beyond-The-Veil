package com.valeriotor.BTV.util;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class MathHelper {
	
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
	
	/** Returns the closest entity the player is looking at
	 * 
	 * @param p The player
	 * @param dis The maximum distance the entity can be at
	 */
	public static EntityLiving getClosestLookedAtEntity(EntityPlayer p, double dis, Predicate<EntityLiving> predIn) {
		List<EntityLiving> ents = getLookedAtEntities(p, dis, predIn);
		if(ents.isEmpty()) return null;
		double minDist = ents.get(0).getDistanceSq(p);
		int selectedEntity = 0;
		for(int i = 1; i < ents.size(); i++) {
			EntityLiving e = ents.get(i);
			double newDist = e.getDistanceSq(p);
			if(newDist < minDist) {
				minDist = newDist;
				selectedEntity = i;
			}
		}
		return ents.get(selectedEntity);
	}
	
	/** Returns a List of entities the player is looking at
	 * 
	 * @param p The player
	 * @param dis The maximum distance the entities can be at
	 * @return
	 */
	public static List<EntityLiving> getLookedAtEntities(EntityPlayer p, double dis, Predicate<EntityLiving> predIn){
		List<EntityLiving> list = Lists.newArrayList();
		Vec3d lookVec = p.getLook(1.0F);
		BlockPos pos = p.getPosition();
		Predicate<EntityLiving> pred = e -> e.getDistanceSq(p) < dis*dis && predIn.apply(e);
		List<EntityLiving> ents = p.world.getEntities(EntityLiving.class, pred);
		
		for(Entity e : ents) {
			if(getIntersectionLineBoundingBox(pos, lookVec, e.getEntityBoundingBox().grow(1.0D)) != null) {
				list.add((EntityLiving)e);
			}
		}
		return list;
		
	}
	
	/** Finds the intersection between a one-way line (defined by a starting point and a directional vector) and a
	 *  bounding box
	 * 
	 * @param startPos The point from which the line starts
	 * @param vec The directional vector of the line
	 * @param bbox The bounding vox
	 * @return A DoubleBlockPos (a BlockPos with double parameters for coords) representing one of the points in 
	 * which the line intersects the bbox
	 */
	public static DoubleBlockPos getIntersectionLineBoundingBox(BlockPos startPos, Vec3d vec, AxisAlignedBB bbox) {
		double x1 = bbox.minX, x2 = bbox.maxX;
		double y1 = bbox.minY, y2 = bbox.maxY;
		double z1 = bbox.minZ, z2 = bbox.maxZ;
		DoubleBlockPos pos = getIntersectionLineXPlane(startPos, vec, x1);
		if(pos != null && isBetween(pos.getY(), y1, y2) && isBetween(pos.getZ(), z1, z2)) return pos;
		
		pos = getIntersectionLineXPlane(startPos, vec, x2);
		if(pos != null && isBetween(pos.getY(), y1, y2) && isBetween(pos.getZ(), z1, z2)) return pos;
		
		pos = getIntersectionLineYPlane(startPos, vec, y1);
		if(pos != null && isBetween(pos.getX(), x1, x2) && isBetween(pos.getZ(), z1, z2)) return pos;
		
		pos = getIntersectionLineYPlane(startPos, vec, y2);
		if(pos != null && isBetween(pos.getX(), x1, x2) && isBetween(pos.getZ(), z1, z2)) return pos;
		
		pos = getIntersectionLineZPlane(startPos, vec, z1);
		if(pos != null && isBetween(pos.getY(), y1, y2) && isBetween(pos.getX(), x1, x2)) return pos;
		
		pos = getIntersectionLineZPlane(startPos, vec, z2);
		if(pos != null && isBetween(pos.getY(), y1, y2) && isBetween(pos.getX(), x1, x2)) return pos;
		
		return null;
	}
	
	/** The intersection of a one way line with a plane defined by an x value. See getIntersectionLineBoundingBox 
	 *  for more info
	 */
	public static DoubleBlockPos getIntersectionLineXPlane(BlockPos startPos, Vec3d vec, double x) {
		DoubleBlockPos pos = null;
		double t = (x - startPos.getX())/vec.x;
		if(t < 0) return null;
		double y = startPos.getY() + vec.y * t;
		double z = startPos.getZ() + vec.z * t;
		pos = new DoubleBlockPos(x, y, z);
		return pos;
	}
	
	/** The intersection of a one way line with a plane defined by a y value. See getIntersectionLineBoundingBox 
	 *  for more info
	 */
	public static DoubleBlockPos getIntersectionLineYPlane(BlockPos startPos, Vec3d vec, double y) {
		DoubleBlockPos pos = null;
		double t = (y - startPos.getY())/vec.y;
		if(t < 0) return null;
		double x = startPos.getX() + vec.x * t;
		double z = startPos.getZ() + vec.z * t;
		pos = new DoubleBlockPos(x, y, z);
		return pos;
	}
	
	/** The intersection of a one way line with a plane defined by a z value. See getIntersectionLineBoundingBox 
	 *  for more info
	 */
	public static DoubleBlockPos getIntersectionLineZPlane(BlockPos startPos, Vec3d vec, double z) {
		DoubleBlockPos pos = null;
		double t = (z - startPos.getZ())/vec.z;
		if(t < 0) return null;
		double y = startPos.getY() + vec.y * t;
		double x = startPos.getX() + vec.x * t;
		pos = new DoubleBlockPos(x, y, z);
		return pos;
	}
	
	private static class DoubleBlockPos {
		private final double x, y, z;
		public DoubleBlockPos(double x, double y, double z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
		public double getX() {return this.x;}
		public double getY() {return this.y;}
		public double getZ() {return this.z;}
	}
	
	/** Checks if par 1 is between par2 and par3, just for quick use
	 */
	public static boolean isBetween(double par1, double par2, double par3) {
		return par1 > par2 && par1 < par3;
	}
	
}
