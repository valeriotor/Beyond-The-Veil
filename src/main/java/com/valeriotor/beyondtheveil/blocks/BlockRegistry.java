package com.valeriotor.beyondtheveil.blocks;

import com.valeriotor.beyondtheveil.blocks.flora.BlockGhostGrass;
import com.valeriotor.beyondtheveil.blocks.flora.BlockMegydrea;
import com.valeriotor.beyondtheveil.blocks.flora.BlockMutator;
import com.valeriotor.beyondtheveil.blocks.flora.BlockRedstoneGrass;
import com.valeriotor.beyondtheveil.blocks.flora.PlantArborealGenerator;
import com.valeriotor.beyondtheveil.blocks.flora.PlantBeanStalk;
import com.valeriotor.beyondtheveil.blocks.flora.PlantBreaker;
import com.valeriotor.beyondtheveil.blocks.flora.PlantSaplingShrub;
import com.valeriotor.beyondtheveil.blocks.flora.PlantVijhiss;
import com.valeriotor.beyondtheveil.blocks.flora.PlantWeed;
import com.valeriotor.beyondtheveil.blocks.fluid.BlockFluidTears;
import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.worship.CrawlerWorship.WorshipType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

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
	public static final PlantBreaker PlantBreaker = new PlantBreaker(BlockNames.PLANTBREAKER);
	public static final PlantVijhiss PlantVijhiss = new PlantVijhiss(BlockNames.PLANTVIJHISS);
	public static final BlockMutator BlockMutator = new BlockMutator(BlockNames.MUTATOR);
	public static final BlockWateryCradle BlockWateryCradle = new BlockWateryCradle(BlockNames.WATERYCRADLE);
	public static final BlockLacrymatory BlockLacrymatory = new BlockLacrymatory(BlockNames.LACRYMATORY);
	public static final BlockCityMapper BlockCityMapper = new BlockCityMapper(BlockNames.CITYMAPPER);
	public static final Block BlockStoneElderBrick = new ModBlock(Material.ROCK, BlockNames.ELDER_STONE_BRICK).setHardness(10).setResistance(3000);
	public static final Block BlockStoneElderBrickChiseled = new ModBlock(Material.ROCK, BlockNames.ELDER_STONE_BRICK_CHISEL).setHardness(10).setResistance(3000);
	public static final Block BlockElderBrick = new ModBlock(Material.ROCK, BlockNames.ELDER_BRICK).setHardness(10).setResistance(3000);
	public static final ModSlab SlabElderHalf = (ModSlab) new ModSlab(BlockNames.ELDER_SLAB, Material.ROCK, false).setHardness(10).setResistance(3000);
	public static final ModSlab SlabElderDouble = (ModSlab) new ModSlab(BlockNames.ELDER_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
	public static final ModStairs BlockStoneElderBrickStairs = new ModStairs(BlockStoneElderBrick.getDefaultState(), BlockNames.ELDER_STONE_BRICK_STAIRS);
	public static final Block BlockHeart = new BlockHeart(Material.SPONGE, BlockNames.HEART);
	public static final Block BlockSacrificeAltar = new BlockSacrificeAltarCore(Material.IRON, BlockNames.SACRIFICE_ALTAR);
	public static final Block BlockBloodBrick = new ModBlock(Material.ROCK, BlockNames.BLOODBRICKS);
	public static final ModSlab SlabBloodHalf = (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB, Material.ROCK, false).setHardness(10).setResistance(3000);
	public static final ModSlab SlabBloodDouble = (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
	public static final ModStairs BlockBloodBrickStairs = new ModStairs(BlockBloodBrick.getDefaultState(), BlockNames.BLOOD_BRICKS_STAIRS);
	public static final BlockBloodWell BlockBloodWell = new BlockBloodWell(Material.PORTAL, BlockNames.BLOOD_WELL);
	public static final BlockStatue BlockStatue = new BlockStatue(Material.ROCK, BlockNames.STATUE, WorshipType.DEFAULT);
	public static final BlockStatue BlockSacrificeStatue = new BlockStatue(Material.ROCK, BlockNames.SACRIFICE_STATUE, WorshipType.SACRIFICE);
	public static final BlockStatue BlockPenitenceStatue = new BlockStatue(Material.ROCK, BlockNames.PENITENCE_STATUE, WorshipType.PENITENCE);
	public static final BlockMemorySieve BlockMemorySieve = new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_SIEVE);
	public static final BlockGearBench BlockGearBench = new BlockGearBench(Material.IRON, BlockNames.GEAR_BENCH);
	public static final BlockDreamFocus BlockDreamFocus = new BlockDreamFocus(BlockNames.DREAMFOCUS);
	public static final BlockDreamFocusFluids BlockDreamFocusFluids = new BlockDreamFocusFluids(BlockNames.DREAMFOCUSFLUIDS);
	public static final BlockDreamFocusVillagers BlockDreamFocusVillagers = new BlockDreamFocusVillagers(BlockNames.DREAMFOCUSVILLAGERS);
	public static final BlockCurtain BlockCurtain = new BlockCurtain(BlockNames.CURTAIN);
	public static final BlockMegydrea BlockMegydrea = new BlockMegydrea(BlockNames.MEGYDREA);
	
	// Fluid Blocks. Registered in ModFluids to make sure it happens after the Fluids themselves.
	public static BlockFluidTears BlockFluidTears;
	
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		registry.register(DampWood);;
		registry.register(FumeSpreader);
		registry.register(DarkSand);
		registry.register(DampStone);
		registry.register(DampLog);
	   	registry.register(DampWoodStairs);
	   	registry.register(DampCanopy);
	   	registry.register(DampCanopyWood); 
	   	registry.register(WornBricks);
	   	registry.register(BlockIdol);
	   	registry.register(BlockBarrel);
	   	registry.register(BlockSlugBait);
	   	registry.register(BlockLamp);
	   	registry.register(BricksBlue);
	   	registry.register(WornBrickStairs);
	   	registry.register(DampWoodFence);
	   	registry.register(SleepChamber);
	   	registry.register(SleepChamberAdvanced);
	   	registry.register(PlantBeanStalk);
	   	registry.register(PlantRedstoneWeed);
	   	registry.register(BlockRedstoneGrass);
	   	registry.register(PlantGhostWeed);
	   	registry.register(BlockGhostGrass);
	   	registry.register(PlantVanillaWeed);
	   	registry.register(PlantArborealGenerator);
	   	registry.register(PlantSaplingShrub);
	   	//registry.register(PlantBreaker);
	   	registry.register(PlantVijhiss);
	   	//registry.register(BlockMutator);
	   	registry.register(BlockWateryCradle);
	   	registry.register(BlockLacrymatory);
	   	registry.register(BlockCityMapper);
	   	registry.register(BlockStoneElderBrick);
	   	registry.register(BlockStoneElderBrickChiseled);
	   	registry.register(BlockElderBrick);
	   	registry.register(BlockStoneElderBrickStairs);
	   	registry.register(BlockHeart);
	   	registry.register(BlockSacrificeAltar);
	   	registry.register(BlockBloodBrick);
	   	registry.register(BlockBloodBrickStairs);
	   	registry.register(BlockBloodWell);
	   	registry.register(BlockStatue);
	   	registry.register(BlockSacrificeStatue);
	   	registry.register(BlockPenitenceStatue);
	   	registry.register(BlockMemorySieve);
	   	registry.register(BlockGearBench);
	   	registry.register(BlockDreamFocus);
	   	registry.register(BlockDreamFocusFluids);
	   	registry.register(BlockDreamFocusVillagers);
	   	registry.register(BlockCurtain);
	   	registry.register(BlockMegydrea);
	   	
	   	ModSlab.registerSlab(SlabElderHalf, SlabElderDouble, registry);
	   	ModSlab.registerSlab(SlabBloodHalf, SlabBloodDouble, registry);
	   	
	   	registry.register(BlockFluidTears);
	}
}
