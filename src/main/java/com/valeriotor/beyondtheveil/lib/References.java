package com.valeriotor.beyondtheveil.lib;

import net.minecraft.creativetab.CreativeTabs;

public class References {
	public static final String MODID = "beyondtheveil";
    public static final String NAME = "Beyond The Veil";
    public static final String VERSION = "1.12.2-0.0.4";
    public static final String DEPENDENCIES = "required-after:baubles";
    
    public static final String PURPLE = "§5§o";
    
    public static CreativeTabs BTV_TAB = new CreativeTabBTV(CreativeTabBTV.getNextID(), MODID);
}
