package com.valeriotor.BTV.lib;

import net.minecraft.creativetab.CreativeTabs;

public class References {
	public static final String MODID = "beyondtheveil";
    public static final String NAME = "Beyond The Veil";
    public static final String VERSION = "0.0.0.1";
    public static final String DEPENDENCIES = "required-after:baubles";
    
    public static final String PURPLE = "§5§o";
    
    public static CreativeTabs BTV_TAB = new CreativeTabBTV(CreativeTabBTV.getNextID(), MODID);
}
