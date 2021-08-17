package com.valeriotor.beyondtheveil.gui;

import net.minecraft.client.gui.GuiScreen;

/*
 * Just a class that contains and returns Gui ids
 */
public class Guis {
	
	public static final String GuiEmpty = "GE";
	public static final String GuiSleepingChamber = "GSC";
	public static final String GuiDialogueDweller = "GDD";
	public static final String GuiTablet = "GT";
	public static final String GuiAlienisDream = "GAD";
	public static final String GuiWateryCradle = "GWC";
	public static final String GuiDagon = "GD";
	public static final String GuiBaptism = "GBT";
	public static final String GuiSurgeon = "GSG";
	
	public static GuiScreen getGui(String id, Object... args) {
		switch(id) {
		case GuiEmpty: return null;
		case GuiSleepingChamber: return new GuiSleepingChamber();
		case GuiDialogueDweller: return new GuiDialogueDweller();
		case GuiTablet: return new GuiTablet();
		case GuiAlienisDream: return new GuiAlienisDream();	
		case GuiDagon: return new GuiDagon();
		case GuiBaptism: return new GuiDrowned((byte)args[0], (boolean)args[1], (boolean)args[2]);
		case GuiSurgeon: return new GuiSurgeon((int)args[0]);
		}
		
		return null;
	}
	
}
