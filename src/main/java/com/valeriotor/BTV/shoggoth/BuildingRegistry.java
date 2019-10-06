package com.valeriotor.BTV.shoggoth;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.shoggoth.buildings.BuildingLargeTower;
import com.valeriotor.BTV.shoggoth.buildings.BuildingPyramid;
import com.valeriotor.BTV.shoggoth.buildings.BuildingTallTower;
import com.valeriotor.BTV.shoggoth.buildings.BuildingTower;
import com.valeriotor.BTV.shoggoth.buildings.BuildingWall;

import net.minecraft.util.ResourceLocation;

public class BuildingRegistry {
	

	public static BuildingTemplate tallTower;
	public static BuildingTemplate tower;
	public static BuildingTemplate largeTower;
	public static BuildingTemplate pyramid;
	public static BuildingTemplate wall;
	
	public static final BuildingTemplate[] templates = new BuildingTemplate[64];
	
	
	public static final ResourceLocation GuiIcons = new ResourceLocation(References.MODID, "textures/gui/city_buildings.png");
	
	public static void registerBuildings() {
		int count = 0;
		tower = new BuildingTower("tower", count++);
		largeTower = new BuildingLargeTower("largetower", count++);
		tallTower = new BuildingTallTower("talltower", count++);
		pyramid = new BuildingPyramid("pyramid", count++);
		wall = new BuildingWall("wall", count++);
	}
	
}
