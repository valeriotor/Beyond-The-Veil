package com.valeriotor.BTV.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

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
		List<String> strings = f.listFormattedStringToWidth(string, width);
		for(int i = 0; i < strings.size() - 1; i++) {
			strings.set(i, strings.get(i).concat(" "));
		}
		return strings;
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
	
	public static int getPreviousStringsLength(List<String> strings, int index) {
		int len = 0;
		for(int i = 0; i < strings.size() && i < index; i++) {
			len += strings.get(i).length();
		}
		return len;
	}
	
	public static void drawCenteredParagraph(GuiScreen screen, FontRenderer f, String s, int width, int topLeftX, int topY) {
		List<String> ss = splitStringsByWidth(s, width, f);
		int i = 0;
		for(String s1 : ss) {
			screen.drawCenteredString(f, s1, topLeftX + width/2, topY + 15*(i++), 0xFFFFFFFF);
		}
	}
	
	public static void drawItemStack(IItemRenderGui gui, ItemStack stack, int x, int y) {
        gui.getItemRender().renderItemAndEffectIntoGUI(stack, x, y);
    }
	

}
