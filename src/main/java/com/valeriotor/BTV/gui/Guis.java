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
@SideOnly(Side.CLIENT)
public class Guis {
	
	public static String GuiSleepingChamber = "GSC";
	
	public static String GuiDialogueDweller = "GDD";
	
	private static HashMap<String, Class<? extends GuiScreen>> guis = new HashMap<>();
	
	public static GuiScreen getGui(String id) {
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
		
		
	}
	
}
