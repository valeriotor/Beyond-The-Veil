package com.valeriotor.beyondtheveil.blocks;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public enum EnumHalf implements IStringSerializable{
	TOP("top"),
	BOTTOM("bottom");

	private final String name;

    private EnumHalf(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

    public String getName()
    {
        return this.name;
    }
    
    public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.<EnumHalf>create("half", EnumHalf.class);
	
}
