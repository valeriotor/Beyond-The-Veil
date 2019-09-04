package com.valeriotor.BTV.shoggoth;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.shoggoth.buildings.BuildingLargeTower;
import com.valeriotor.BTV.shoggoth.buildings.BuildingTower;

import net.minecraft.util.ResourceLocation;

public class BuildingRegistry {
	
	public static BuildingTemplate tower;
	public static BuildingTemplate largeTower;
	
	public static final BuildingTemplate[] templates = new BuildingTemplate[64];
	
	
	public static final ResourceLocation GuiIcons = new ResourceLocation(References.MODID, "textures/gui/city_buildings.png");
	
	public static void registerBuildings() {
		tower = new BuildingTower(0);
		largeTower = new BuildingLargeTower(1);
	}
	
}
