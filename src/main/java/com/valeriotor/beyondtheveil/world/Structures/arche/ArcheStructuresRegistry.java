package com.valeriotor.beyondtheveil.world.Structures.arche;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.valeriotor.beyondtheveil.world.Structures.arche.deepcity.DeepCityStructure;
import com.valeriotor.beyondtheveil.world.Structures.arche.deepcity.DeepCityStructureTemplate;

import net.minecraft.util.math.BlockPos;

public class ArcheStructuresRegistry {
	public static final BloodHome HOME = new BloodHome();
	public static final DeepCityStructureTemplate TEST = new DeepCityStructureTemplate("blood_home", 15);
	
	public static final Map<String, DeepCityStructureTemplate> DEEP_CITY_BUILDINGS = new HashMap<>();
	
	public static void registerArcheStructures() {
		HOME.registerBlocks();
		TEST.registerBlocks();
		
		DEEP_CITY_BUILDINGS.put(TEST.getName(), TEST);
	}
	
	public static DeepCityStructure getRandomDeepCityStructure(Random r, BlockPos pos) {
		return new DeepCityStructure(TEST, pos);
	}
	
	public static DeepCityStructureTemplate getStructure(String name) {
		return DEEP_CITY_BUILDINGS.get(name);
	}
	
}
