package com.valeriotor.beyondtheveil.entities.ictya;

public enum IctyaSize {
	TINY,
	SMALL,
	MEDIUM,
	LARGE,
	HUGE,
	COLOSSAL;
	
	public int getSizeInt() {
		return ordinal();
	}
}
