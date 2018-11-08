package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.proxy.ClientProxy;
import com.valeriotor.BTV.lib.References;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod.EventBusSubscriber
public class BlockRegistry {
	
	
	public static final DampWood DampWood = new DampWood();	
	public static final FumeSpreader FumeSpreader = new FumeSpreader();
	public static final DarkSand DarkSand = new DarkSand();
	public static final DampStone DampStone = new DampStone();
	public static final DampLog DampLog = new DampLog();
	public static final DampWoodStairs DampWoodStairs = new DampWoodStairs(DampWood.getDefaultState());
	public static final DampCanopy DampCanopy = new DampCanopy();
	public static final DampCanopyWood DampCanopyWood = new DampCanopyWood();
	public static final WornBricks WornBricks = new WornBricks();
	public static final BlockIdol BlockIdol = new BlockIdol();
	public static final BlockBarrel BlockBarrel = new BlockBarrel();
	public static final BlockSlugBait BlockSlugBait = new BlockSlugBait();
	public static final BlockLamp BlockLamp = new BlockLamp();
	public static final BricksBlue BricksBlue = new BricksBlue();
	public static final WornBrickStairs WornBrickStairs = new WornBrickStairs(WornBricks.getDefaultState());
	public static final DampWoodFence DampWoodFence = new DampWoodFence();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
	   	event.getRegistry().register(DampWood);;
	   	event.getRegistry().register(FumeSpreader);
	   	event.getRegistry().register(DarkSand);
	   	event.getRegistry().register(DampStone);
	   	event.getRegistry().register(DampLog);
	   	event.getRegistry().register(DampWoodStairs);
	   	event.getRegistry().register(DampCanopy);
	   	event.getRegistry().register(DampCanopyWood); 
	   	event.getRegistry().register(WornBricks);
	   	event.getRegistry().register(BlockIdol);
	   	event.getRegistry().register(BlockBarrel);
	   	event.getRegistry().register(BlockSlugBait);
	   	event.getRegistry().register(BlockLamp);
	   	event.getRegistry().register(BricksBlue);
	   	event.getRegistry().register(WornBrickStairs);
	   	event.getRegistry().register(DampWoodFence);
	}
}
