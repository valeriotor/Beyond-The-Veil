package com.valeriotor.beyondtheveil.worship;

import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentTranslation;

public enum Deities {
	NONE(""),
	GREATDREAMER("greatdreamer"); // This is gonna stay like this for a loooooooooooooong time
	
	private String key;
	
	private Deities(String key) {
		this.key = key;
	}
	
	public String getKey() {return this.key;}
	public String getName() {
		return new TextComponentTranslation("deity." + this.name().toLowerCase()).getFormattedText();
	}
}
