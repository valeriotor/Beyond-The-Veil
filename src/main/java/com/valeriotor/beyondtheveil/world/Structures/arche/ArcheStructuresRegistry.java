package com.valeriotor.beyondtheveil.world.Structures.arche;

public class ArcheStructuresRegistry {
	public static final BloodHome home = new BloodHome();
	
	public static void registerArcheStructures() {
		home.registerBlocks();
	}
	
}
