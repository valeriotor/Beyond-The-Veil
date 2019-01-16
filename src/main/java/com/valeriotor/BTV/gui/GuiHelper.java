package com.valeriotor.BTV.gui;


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

}
