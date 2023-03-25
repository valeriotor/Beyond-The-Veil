package com.valeriotor.beyondtheveil.client;

import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class MiscModels {

    private static MiscModels INSTANCE;

    public static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MiscModels();
        }
    }

    public static MiscModels getINSTANCE() {
        return INSTANCE;
    }

    public final Material heart = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/heart"));


}
