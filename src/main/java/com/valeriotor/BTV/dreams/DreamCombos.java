package com.valeriotor.BTV.dreams;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class DreamCombos {
	
	public static void multipleDreams(EntityPlayer p, List<String> aspects, List<BlockPos> spreaders) {
		
	}
	
	public static void sortLists(List<String> aspects, List<BlockPos> spreaders) {
		moveSingleObjects(aspects, spreaders, "Vacuos", false);
		moveSingleObjects(aspects, spreaders, "Vinculum", true);
		moveSingleObjects(aspects, spreaders, "Potentia", true);
		moveSingleObjects(aspects, spreaders, "Permutatio", true);
		
	}
	
	/** Moves both an aspect and its corresponding spreader BlockPos to either first or last position
	 * 
	 * @param aspects The aspect list
	 * @param spreaders The list of BlockPos indicating the spreaders' positions
	 * @param aspect The aspect you want to move
	 * @param lastNotFirst If true the objects are moved to last position, otherwise to first.
	 */
	public static void moveSingleObjects(List<String> aspects, List<BlockPos> spreaders, String aspect, boolean lastNotFirst) {
		if(aspects.contains(aspect)) {
			int i = aspects.indexOf(aspect);
			aspects.remove(i);
			BlockPos pos = spreaders.get(i);
			spreaders.remove(i);
			if(lastNotFirst) {
				aspects.add(aspect);
				spreaders.add(pos);
			} else {
				aspects.add(0, aspect);
				spreaders.add(0, pos);
			}
		}
	}
	
}
