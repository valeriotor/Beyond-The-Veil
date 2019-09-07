package com.valeriotor.BTV.shoggoth;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.shoggoth.buildings.BuildingLargeTower;
import com.valeriotor.BTV.shoggoth.buildings.BuildingPyramid;
import com.valeriotor.BTV.shoggoth.buildings.BuildingTower;
import com.valeriotor.BTV.shoggoth.buildings.BuildingWall;

import net.minecraft.util.ResourceLocation;

public class BuildingRegistry {
	
	public static BuildingTemplate tower;
	public static BuildingTemplate largeTower;
	public static BuildingTemplate pyramid;
	public static BuildingTemplate wall;
	
	public static final BuildingTemplate[] templates = new BuildingTemplate[64];
	
	
	public static final ResourceLocation GuiIcons = new ResourceLocation(References.MODID, "textures/gui/city_buildings.png");
	
	public static void registerBuildings() {
		tower = new BuildingTower("tower", 0);
		largeTower = new BuildingLargeTower("largetower", 1);
		pyramid = new BuildingPyramid("pyramid", 2);
		wall = new BuildingWall("wall", 3);
	}
	
}
