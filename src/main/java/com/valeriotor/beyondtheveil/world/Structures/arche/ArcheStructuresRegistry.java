package com.valeriotor.beyondtheveil.world.Structures.arche;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.valeriotor.beyondtheveil.world.Structures.arche.deepcity.DeepCityStructure;
import com.valeriotor.beyondtheveil.world.Structures.arche.deepcity.DeepCityStructureTemplate;

import net.minecraft.util.math.BlockPos;

public class ArcheStructuresRegistry {
	public static final BloodHome HOME = new BloodHome();
	public static final DeepCityStructureTemplate HOME1 = new DeepCityStructureTemplate("deep_home1", 6);
	public static final DeepCityStructureTemplate BEACON = new DeepCityStructureTemplate("deep_beacon", 9);
	
	public static final Map<String, DeepCityStructureTemplate> DEEP_CITY_BUILDINGS = new HashMap<>();
	
	public static void registerArcheStructures() {
		HOME.registerBlocks();
		HOME1.registerBlocks();
		BEACON.registerBlocks();
		
		DEEP_CITY_BUILDINGS.put(HOME1.getName(), HOME1);
		DEEP_CITY_BUILDINGS.put(BEACON.getName(), BEACON);
	}
	
	public static DeepCityStructure getRandomDeepCityStructure(Random r, BlockPos pos) {
		return r.nextInt(10) == 0 ? new DeepCityStructure(BEACON, pos) : new DeepCityStructure(HOME1, pos);
	}
	
	public static DeepCityStructureTemplate getStructure(String name) {
		return DEEP_CITY_BUILDINGS.get(name);
	}
	
}
