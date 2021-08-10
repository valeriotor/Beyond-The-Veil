package com.valeriotor.beyondtheveil.world.Structures.loot;

import com.google.common.collect.ImmutableMap;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.HashMap;
import java.util.Map;

public class LootTables {

    public static final ResourceLocation hamletStoreHouse1 = LootTableList.register(new ResourceLocation(References.MODID, "storehouse1"));
    public static final ResourceLocation hamletLightHouse = LootTableList.register(new ResourceLocation(References.MODID, "light_house"));
    public static final ResourceLocation hamletTownHall = LootTableList.register(new ResourceLocation(References.MODID, "town_hall"));
    public static final ResourceLocation tablet = LootTableList.register(new ResourceLocation(References.MODID, "inject/tablet"));
    public static final ResourceLocation deep_city_corridor = LootTableList.register(new ResourceLocation(References.MODID, "deep_city_corridor"));
    public static final Map<String, ResourceLocation> lootTables;

    static {
        Map<String, ResourceLocation> tempTables = new HashMap<>();
        tempTables.put(hamletStoreHouse1.getResourcePath(), hamletStoreHouse1);
        tempTables.put(hamletLightHouse.getResourcePath(), hamletLightHouse);
        tempTables.put(hamletTownHall.getResourcePath(), hamletTownHall);
        tempTables.put(tablet.getResourcePath(), tablet);
        tempTables.put(deep_city_corridor.getResourcePath(), deep_city_corridor);
        lootTables = ImmutableMap.copyOf(tempTables);
    }

}
