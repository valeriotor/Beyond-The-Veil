package com.valeriotor.BTV.gui;

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
	
	public static GuiScreen getGui(String id, Object... args) {
		switch(id) {
		case GuiEmpty: return null;
		case GuiSleepingChamber: return new GuiSleepingChamber();
		case GuiDialogueDweller: return new GuiDialogueDweller();
		case GuiTablet: return new GuiTablet();
		case GuiAlienisDream: return new GuiAlienisDream();	
		}
		
		return null;
	}
	
}
