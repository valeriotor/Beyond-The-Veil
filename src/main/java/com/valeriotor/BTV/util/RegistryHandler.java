package com.valeriotor.BTV.util;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.blocks.DampLog;
import com.valeriotor.BTV.blocks.DampStone;
import com.valeriotor.BTV.blocks.DarkSand;
import com.valeriotor.BTV.blocks.FumeSpreader;
import com.valeriotor.BTV.blocks.dampWood;
import com.valeriotor.BTV.entities.EntityDeepOne;
import com.valeriotor.BTV.entities.RegisterEntities;
import com.valeriotor.BTV.entities.render.RegisterRenders;
import com.valeriotor.BTV.entities.render.RenderDeepOne;
import com.valeriotor.BTV.items.ItemOniricIncense;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.items.TestItem;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.tileEntities.TileBarrel;
import com.valeriotor.BTV.tileEntities.TileFumeSpreader;
import com.valeriotor.BTV.tileEntities.TileSlugBait;
import com.valeriotor.BTV.world.BiomeInnsmouth;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class RegistryHandler {
	
	

	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    	ItemRegistry.registerItems(event);
	}
    
	public static void registerEntities() {
		
		
		
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileFumeSpreader.class, References.MODID + ":tilefumespreader");
		GameRegistry.registerTileEntity(TileBarrel.class, References.MODID + ":tilebarrel");
		GameRegistry.registerTileEntity(TileSlugBait.class, References.MODID + ":tileslug_bait");
	}
	
	/*@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		event.getRegistry().register(new BiomeInnsmouth(new BiomeProperties("Voided")));
	}*/
	
}
