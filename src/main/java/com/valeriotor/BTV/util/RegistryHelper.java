package com.valeriotor.BTV.util;

import javax.annotation.Nullable;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.tileEntities.TileArborealGeneratorBottom;
import com.valeriotor.BTV.tileEntities.TileBarrel;
import com.valeriotor.BTV.tileEntities.TileFumeSpreader;
import com.valeriotor.BTV.tileEntities.TileMutator;
import com.valeriotor.BTV.tileEntities.TilePlantTerra;
import com.valeriotor.BTV.tileEntities.TileSlugBait;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.Item;
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
    
	public static void registerEntities() {
		
		
		
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileFumeSpreader.class, References.MODID + ":tilefumespreader");
		GameRegistry.registerTileEntity(TileBarrel.class, References.MODID + ":tilebarrel");
		GameRegistry.registerTileEntity(TileSlugBait.class, References.MODID + ":tileslug_bait");
		GameRegistry.registerTileEntity(TileMutator.class, References.MODID + ":tilemutator");
		GameRegistry.registerTileEntity(TileArborealGeneratorBottom.class, References.MODID + ":tilearboreal_generator");
		GameRegistry.registerTileEntity(TilePlantTerra.class, References.MODID + ":tileplantterra");
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
