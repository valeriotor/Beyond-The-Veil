package com.valeriotor.BTV.blocks;

import com.valeriotor.BTV.blocks.flora.BlockGhostGrass;
import com.valeriotor.BTV.blocks.flora.BlockRedstoneGrass;
import com.valeriotor.BTV.blocks.flora.PlantArborealGenerator;
import com.valeriotor.BTV.blocks.flora.PlantBeanStalk;
import com.valeriotor.BTV.blocks.flora.PlantSaplingShrub;
import com.valeriotor.BTV.blocks.flora.PlantWeed;
import com.valeriotor.BTV.lib.BlockNames;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BlockRegistry {
	
	
	public static final DampWood DampWood = new DampWood();	
	public static final BlockFumeSpreader FumeSpreader = new BlockFumeSpreader();
	public static final BlockDarkSand DarkSand = new BlockDarkSand();
	public static final DampStone DampStone = new DampStone();
	public static final DampLog DampLog = new DampLog();
	public static final DampWoodStairs DampWoodStairs = new DampWoodStairs(DampWood.getDefaultState());
	public static final DampCanopy DampCanopy = new DampCanopy();
	public static final DampCanopyWood DampCanopyWood = new DampCanopyWood();
	public static final BlockWornBricks WornBricks = new BlockWornBricks();
	public static final BlockIdol BlockIdol = new BlockIdol();
	public static final BlockBarrel BlockBarrel = new BlockBarrel();
	public static final BlockSlugBait BlockSlugBait = new BlockSlugBait();
	public static final BlockLamp BlockLamp = new BlockLamp();
	public static final BlockBricksBlue BricksBlue = new BlockBricksBlue();
	public static final BlockWornBrickStairs WornBrickStairs = new BlockWornBrickStairs(WornBricks.getDefaultState());
	public static final DampWoodFence DampWoodFence = new DampWoodFence();
	public static final BlockSleepChamber SleepChamber = new BlockSleepChamber(BlockNames.SLEEPCHAMBER);
	public static final BlockSleepChamber SleepChamberAdvanced = new BlockSleepChamber(BlockNames.SLEEPCHAMBERADVANCED);
	public static final PlantBeanStalk PlantBeanStalk = new PlantBeanStalk(BlockNames.BEANSTALK);
	public static final PlantWeed PlantRedstoneWeed = new PlantWeed(BlockNames.REDSTONEWEED);
	public static final BlockRedstoneGrass BlockRedstoneGrass = new BlockRedstoneGrass(BlockNames.REDSTONEGRASS);
	public static final PlantWeed PlantGhostWeed = new PlantWeed(BlockNames.GHOSTWEED);
	public static final BlockGhostGrass BlockGhostGrass = new BlockGhostGrass(BlockNames.GHOSTGRASS);
	public static final PlantWeed PlantVanillaWeed = new PlantWeed(BlockNames.VANILLAWEED);
	public static final PlantArborealGenerator PlantArborealGenerator = new PlantArborealGenerator(BlockNames.ARBOREALGENERATOR);
	public static final PlantSaplingShrub PlantSaplingShrub = new PlantSaplingShrub(BlockNames.SAPLINGSHRUB);
	
	
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
	   	event.getRegistry().register(SleepChamber);
	   	event.getRegistry().register(SleepChamberAdvanced);
	   	event.getRegistry().register(PlantBeanStalk);
	   	event.getRegistry().register(PlantRedstoneWeed);
	   	event.getRegistry().register(BlockRedstoneGrass);
	   	event.getRegistry().register(PlantGhostWeed);
	   	event.getRegistry().register(BlockGhostGrass);
	   	event.getRegistry().register(PlantVanillaWeed);
	   	event.getRegistry().register(PlantArborealGenerator);
	   	event.getRegistry().register(PlantSaplingShrub);
	}
}
