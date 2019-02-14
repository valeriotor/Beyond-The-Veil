package com.valeriotor.BTV.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

// Just some methods to help me out with drawing strings. There's probably methods I haven't looked in that would help me the same way, if not better. BUT EH!
public class GuiHelper {
	
	public static String[] splitStrings(String string) {
		return string.split(":");
	}
	
	public static int getPreviousStringsLength(int index, String string) {
		String[] strings = splitStrings(string);
		int total = 0;
		if(index < 0 || index > strings.length -1) {
			return 0;
		}
		for(int i = 0; i < index; i++) {
			total+= strings[i].length();
		}
		return total;
	}
	
	public static List<String> splitStringsByWidth(String string, int width, FontRenderer f) {
		return f.listFormattedStringToWidth(string, width);
	}
	
	public static int getPreviousStringsLengthByWidth(int index, String string, int width, FontRenderer f) {
		List<String> strings = splitStringsByWidth(string, width, f);
		int total = 0;
		if(index < 0 || index > strings.size() -1) {
			return 0;
		}
		for(int i = 0; i < index; i++) {
			total+= strings.get(i).length();
		}
		return total;
	}
	
	public static double getScaleMultiplier() {
		return ((double)(5 + 1.25*((Minecraft.getMinecraft().gameSettings.guiScale - 1) & 3))) / 6.25;
	}

}
