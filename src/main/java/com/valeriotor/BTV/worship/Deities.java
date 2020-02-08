package com.valeriotor.BTV.worship;

public enum Deities {
	NONE(""),
	GREATDREAMER("greatdreamer"); // This is gonna stay like this for a loooooooooooooong time
	
	private String key;
	
	private Deities(String key) {
		this.key = key;
	}
	
	public String getKey() {return this.key;}
		
}
