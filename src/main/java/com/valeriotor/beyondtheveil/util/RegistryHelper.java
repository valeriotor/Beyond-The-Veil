package com.valeriotor.beyondtheveil.util;

import javax.annotation.Nullable;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tileEntities.*;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHelper {
	
	

	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    	ItemRegistry.registerItems(event);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		BlockRegistry.registerBlocks(event);
	}
    
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ItemRegistry.initModels();
	}
	
	public static void registerTileEntities() {
		registerTileEntity(TileFumeSpreader.class, "tilefumespreader");
		registerTileEntity(TileBarrel.class, "tilebarrel");
		registerTileEntity(TileSlugBait.class, "tileslug_bait");
		//registerTileEntity(TileMutator.class, "tilemutator");
		registerTileEntity(TileArborealGeneratorBottom.class, "tilearboreal_generator");
		//registerTileEntity(TilePlantTerra.class, "tileplantterra");
		registerTileEntity(TileWateryCradle.class, "tilewaterycradle");
		registerTileEntity(TileLacrymatory.class, "tilelacrymatory");
		registerTileEntity(TileCityMapper.class, "tilecitymapper");
		registerTileEntity(TileHeart.class, "tileheart");
		registerTileEntity(TileBloodWell.class, "tilebloodwell");
		registerTileEntity(TileStatue.class, "tilestatue");
		registerTileEntity(TileMemorySieve.class, "tilememorysieve");
		registerTileEntity(TileGearBench.class, "tilegearbench");
		registerTileEntity(TileDreamFocus.class, "tiledreamfocus");
		registerTileEntity(TileMegydrea.class, "tilemegydrea");
		registerTileEntity(TileDeepChest.class, "tiledeepchest");

	}
	
	private static void registerTileEntity(Class<? extends TileEntity> teClass, String name) {
		GameRegistry.registerTileEntity(teClass, new ResourceLocation(References.MODID + ":" + name));
	}
	
	
	@SideOnly(Side.CLIENT)
	public static void registerColorHandlers() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                return worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
            }
        }, new  Block[] {BlockRegistry.BlockRedstoneGrass, BlockRegistry.BlockGhostGrass});
	}
	
	
	/*@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		event.getRegistry().register(new BiomeInnsmouth(new BiomeProperties("Voided")));
	}*/
	
}
