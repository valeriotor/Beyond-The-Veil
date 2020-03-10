package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.world.Structures.loot.LootTables;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LootEvents {
	
	// This was made with the help of Vazkii's Botania's Loothandler, on github.
	
	@SubscribeEvent
	public static void lootTableEvent(LootTableLoadEvent event) {
		String name = event.getName().toString();
		String prefix = "minecraft:chests/";
		if(name.startsWith(prefix)) {
			name = name.substring(name.indexOf(prefix) + prefix.length());
		}
		switch(name) {
			case "abandoned_mineshaft":
			case "stronghold_corridor":
			case "simple_dungeon":
				event.getTable().addPool(getPoolTablet());
				break;
		}
	}
	
	private static LootPool getPoolTablet() {
		LootEntryTable entry = new LootEntryTable(new ResourceLocation(References.MODID, "inject/tablet"), 1, 0, new LootCondition[0], "beyondtheveil_inject_entry");
		return new LootPool(new LootEntry[] {entry}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "beyondtheveil_inject_pool");
	}
	
}
