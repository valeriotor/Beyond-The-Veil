package com.valeriotor.BTV.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.valeriotor.BTV.entities.EntityHamletDweller;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Just a class that contains and returns Gui ids
 */
public class Guis {
	
	public static String GuiEmpty = "GE";
	public static String GuiSleepingChamber = "GSC";
	public static String GuiDialogueDweller = "GDD";
	public static String GuiTablet = "GT";
	public static String GuiAlienisDream = "GAD";
	public static String GuiWateryCradle = "GWC";
	
	private static HashMap<String, Class<? extends GuiScreen>> guis = new HashMap<>();
	
	public static GuiScreen getGui(String id) {
		if(id.equals(GuiEmpty)) return (GuiScreen)null;
		GuiScreen gui = null;
		try {
			gui = guis.get(id).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gui;
	}
	
	public static void registerGuis() {
		guis.put(GuiSleepingChamber, GuiSleepingChamber.class);
		guis.put(GuiDialogueDweller, GuiDialogueDweller.class);
		guis.put(GuiTablet, GuiTablet.class);
		guis.put(GuiAlienisDream, GuiAlienisDream.class);
		guis.put(GuiWateryCradle, GuiWateryCradle.class);
		
		
	}
	
}
