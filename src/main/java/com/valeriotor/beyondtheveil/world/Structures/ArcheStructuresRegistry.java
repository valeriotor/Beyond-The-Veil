package com.valeriotor.beyondtheveil.world.Structures;

public class ArcheStructuresRegistry {
	public static final BloodHome home = new BloodHome();
	
	public static void registerArcheStructures() {
		home.registerBlocks();
	}
	
}
