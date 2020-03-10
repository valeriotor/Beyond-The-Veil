package com.valeriotor.beyondtheveil.shoggoth;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.shoggoth.buildings.BuildingClinic;
import com.valeriotor.beyondtheveil.shoggoth.buildings.BuildingLargeTower;
import com.valeriotor.beyondtheveil.shoggoth.buildings.BuildingPyramid;
import com.valeriotor.beyondtheveil.shoggoth.buildings.BuildingRoad;
import com.valeriotor.beyondtheveil.shoggoth.buildings.BuildingTallTower;
import com.valeriotor.beyondtheveil.shoggoth.buildings.BuildingTower;
import com.valeriotor.beyondtheveil.shoggoth.buildings.BuildingWall;

import net.minecraft.util.ResourceLocation;

public class BuildingRegistry {
	

	public static BuildingTemplate tallTower;
	public static BuildingTemplate tower;
	public static BuildingTemplate largeTower;
	public static BuildingTemplate pyramid;
	public static BuildingTemplate wall;
	public static BuildingTemplate road;
	public static BuildingTemplate clinic;
	
	public static final BuildingTemplate[] templates = new BuildingTemplate[64];
	
	
	public static final ResourceLocation GuiIcons = new ResourceLocation(References.MODID, "textures/gui/city_buildings.png");
	
	public static void registerBuildings() {
		int count = 0;
		tower = new BuildingTower("tower", count++);
		largeTower = new BuildingLargeTower("largetower", count++);
		tallTower = new BuildingTallTower("talltower", count++);
		pyramid = new BuildingPyramid("pyramid", count++);
		clinic = new BuildingClinic("clinic", count++);
		wall = new BuildingWall("wall", count++);
		road = new BuildingRoad("road", count++);
	}
	
}
