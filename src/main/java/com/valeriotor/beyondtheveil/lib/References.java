package com.valeriotor.beyondtheveil.lib;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class References {
    public static final String MODID = "beyondtheveil";
    public static final String NAME = "Beyond The Veil";

    public static final String TAB_NAME = MODID;
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };



}
