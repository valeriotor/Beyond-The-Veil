package com.valeriotor.BTV.blocks;

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
}
