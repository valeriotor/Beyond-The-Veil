package com.valeriotor.beyondtheveil.entities.ictya;

public enum IctyaSize {
	PREY,
	TINY,
	SMALL,
	MEDIUM,
	LARGE,
	HUGE,
	COLOSSAL;
	
	public int getSizeInt() {
		return ordinal() - 1;
	}
}
