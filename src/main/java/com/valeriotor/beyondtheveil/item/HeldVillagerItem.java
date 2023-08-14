package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.world.item.Item;

public class HeldVillagerItem extends Item {

    public HeldVillagerItem() {
        super(new Item.Properties().tab(References.ITEM_GROUP).stacksTo(1));
    }


}
