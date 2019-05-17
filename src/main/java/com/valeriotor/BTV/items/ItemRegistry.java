package com.valeriotor.BTV.items;

import java.util.Random;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.lib.BlockNames;
import com.valeriotor.BTV.proxy.ClientProxy;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;


@ObjectHolder("beyondtheveil")
public class ItemRegistry {
	
	
	public static final TestItem testItem = new TestItem();
	public static final ItemOniricIncense oniricIncense = new ItemOniricIncense();
	public static final ItemSlugCatcher slugCatcher = new ItemSlugCatcher();
	public static final ItemSlug slug = new ItemSlug();
	public static final ItemCanoe canoe = new ItemCanoe();
	public static final ItemDrink rum = new ItemDrink("drink_rum");
	public static final ItemDrink wine = new ItemDrink("drink_wine");
	public static final ItemDrink ale = new ItemDrink("drink_ale");
	public static final ItemDrink vodka = new ItemDrink("drink_vodka");
	public static final ItemDrink mead = new ItemDrink("drink_mead");
	public static final ItemDrink cup = new ItemDrink("drink_empty");
	public static final ItemFlute flute = new ItemFlute();
	public static final ItemSawCleaver saw_cleaver = new ItemSawCleaver(ToolMaterial.IRON);
	public static final ItemWolfMedallion wolf_medallion = new ItemWolfMedallion();
	public static final ItemTablet tablet = new ItemTablet("tablet");
	public static final ItemBronzeSphere bronze_sphere= new ItemBronzeSphere();
	public static final Item redstone_weed_seeds = new ItemSpecialGrassSeeds(BlockRegistry.PlantRedstoneWeed, BlockRegistry.BlockRedstoneGrass).setRegistryName(BlockNames.REDSTONEWEED).setUnlocalizedName(BlockNames.REDSTONEWEED);
	public static final Item ghost_weed_seeds = new ItemSpecialGrassSeeds(BlockRegistry.PlantGhostWeed, BlockRegistry.BlockGhostGrass).setRegistryName(BlockNames.GHOSTWEED).setUnlocalizedName(BlockNames.GHOSTWEED);
	public static final Item vanilla_weed_seeds = new ItemSpecialGrassSeeds(BlockRegistry.PlantVanillaWeed, Blocks.GRASS).setRegistryName(BlockNames.VANILLAWEED).setUnlocalizedName(BlockNames.VANILLAWEED);
	public static final ItemBlackjack blackjack = new ItemBlackjack();
	
	
	
	
	//public static final ItemTablet old_map = new ItemTablet("old_map");
	//public static final ItemTablet scroll = new ItemTablet("scroll");
	//public static final ItemTablet depiction = new ItemTablet("depiction");

	
	
    public static void initModels() {
        ClientProxy.registerItemRenderer(testItem, 0, "inventory");
        ClientProxy.registerItemRenderer(oniricIncense, 0, "inventory");
        ClientProxy.registerItemRenderer(slugCatcher, 0, "inventory");
        ClientProxy.registerItemRenderer(slug, 0, "inventory");
        ClientProxy.registerItemRenderer(canoe, 0, "inventory");
        ClientProxy.registerItemRenderer(rum, 0, "inventory");
        ClientProxy.registerItemRenderer(wine, 0, "inventory");
        ClientProxy.registerItemRenderer(ale, 0, "inventory");
        ClientProxy.registerItemRenderer(vodka, 0, "inventory");
        ClientProxy.registerItemRenderer(mead, 0, "inventory");
        ClientProxy.registerItemRenderer(cup, 0, "inventory");
        ClientProxy.registerItemRenderer(flute, 0, "inventory");
        ClientProxy.registerItemRenderer(saw_cleaver, 0, "inventory");
        ClientProxy.registerItemRenderer(wolf_medallion, 0, "inventory");
        ClientProxy.registerItemRenderer(tablet, 0, "unfinished");
        ClientProxy.registerItemRenderer(tablet, 1, "finished");
        ClientProxy.registerItemRenderer(bronze_sphere, 0, "inventory");
        ClientProxy.registerItemRenderer(redstone_weed_seeds, 0, "inventory");
        ClientProxy.registerItemRenderer(ghost_weed_seeds, 0, "inventory");
        ClientProxy.registerItemRenderer(vanilla_weed_seeds, 0, "inventory");
        ClientProxy.registerItemRenderer(blackjack, 0, "inventory");
        
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DampWood), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.FumeSpreader), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DarkSand), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DampStone), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DampLog), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DampWoodStairs), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DampCanopy), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DampCanopyWood), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.WornBricks), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockIdol), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockBarrel), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockSlugBait), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockLamp), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BricksBlue), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.WornBrickStairs), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.DampWoodFence), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.SleepChamber), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.SleepChamberAdvanced), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantBeanStalk), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockRedstoneGrass), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantArborealGenerator), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantSaplingShrub), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantTerra), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantOrdo), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockMutator), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockWateryCradle), 0, "inventory");
        
        
    }
    
    public static void registerItems(RegistryEvent.Register<Item> event) {
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DampWood).setRegistryName(BlockRegistry.DampWood.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.FumeSpreader).setRegistryName(BlockRegistry.FumeSpreader.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DarkSand).setRegistryName(BlockRegistry.DarkSand.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DampStone).setRegistryName(BlockRegistry.DampStone.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DampLog).setRegistryName(BlockRegistry.DampLog.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DampWoodStairs).setRegistryName(BlockRegistry.DampWoodStairs.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DampCanopy).setRegistryName(BlockRegistry.DampCanopy.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DampCanopyWood).setRegistryName(BlockRegistry.DampCanopyWood.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.WornBricks).setRegistryName(BlockRegistry.WornBricks.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BlockIdol).setRegistryName(BlockRegistry.BlockIdol.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BlockBarrel).setRegistryName(BlockRegistry.BlockBarrel.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BlockSlugBait).setRegistryName(BlockRegistry.BlockSlugBait.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BlockLamp).setRegistryName(BlockRegistry.BlockLamp.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BricksBlue).setRegistryName(BlockRegistry.BricksBlue.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.WornBrickStairs).setRegistryName(BlockRegistry.WornBrickStairs.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.DampWoodFence).setRegistryName(BlockRegistry.DampWoodFence.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.SleepChamber).setRegistryName(BlockRegistry.SleepChamber.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.SleepChamberAdvanced).setRegistryName(BlockRegistry.SleepChamberAdvanced.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.PlantBeanStalk).setRegistryName(BlockRegistry.PlantBeanStalk.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BlockRedstoneGrass).setRegistryName(BlockRegistry.BlockRedstoneGrass.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.PlantArborealGenerator).setRegistryName(BlockRegistry.PlantArborealGenerator.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.PlantSaplingShrub).setRegistryName(BlockRegistry.PlantSaplingShrub.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.PlantTerra).setRegistryName(BlockRegistry.PlantTerra.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.PlantOrdo).setRegistryName(BlockRegistry.PlantOrdo.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BlockMutator).setRegistryName(BlockRegistry.BlockMutator.getRegistryName()));
    	event.getRegistry().register(new ItemBlock(BlockRegistry.BlockWateryCradle).setRegistryName(BlockRegistry.BlockWateryCradle.getRegistryName()));
    	event.getRegistry().register(ItemRegistry.testItem);
    	event.getRegistry().register(ItemRegistry.oniricIncense);
    	event.getRegistry().register(ItemRegistry.slugCatcher);
    	event.getRegistry().register(ItemRegistry.slug);
    	event.getRegistry().register(ItemRegistry.canoe);
    	event.getRegistry().register(ItemRegistry.rum);
    	event.getRegistry().register(ItemRegistry.wine);
    	event.getRegistry().register(ItemRegistry.ale);
    	event.getRegistry().register(ItemRegistry.vodka);
    	event.getRegistry().register(ItemRegistry.mead);
    	event.getRegistry().register(ItemRegistry.cup);
    	event.getRegistry().register(ItemRegistry.flute);
    	event.getRegistry().register(ItemRegistry.saw_cleaver);
    	event.getRegistry().register(ItemRegistry.wolf_medallion);
    	event.getRegistry().register(ItemRegistry.tablet);
    	event.getRegistry().register(ItemRegistry.bronze_sphere);
    	event.getRegistry().register(ItemRegistry.blackjack);
    	
    	
    	event.getRegistry().register(ItemRegistry.redstone_weed_seeds);
    	BlockRegistry.PlantRedstoneWeed.setSeed(redstone_weed_seeds);
    	event.getRegistry().register(ItemRegistry.ghost_weed_seeds);
    	BlockRegistry.PlantGhostWeed.setSeed(ghost_weed_seeds);
    	event.getRegistry().register(ItemRegistry.vanilla_weed_seeds);
    	BlockRegistry.PlantVanillaWeed.setSeed(vanilla_weed_seeds);
    }
    
    
    
    public static Item getRandomArtifact(Random r) {
    	int a = r.nextInt(4);
    	switch(a) {
    	case 0: return saw_cleaver;
    	case 1: return flute;
    	case 2: return wolf_medallion;
    	case 3: return bronze_sphere;
    	default: return null;
    	}
    }
    
    public static ItemStack getRandomSeed(Random r, int chance) {
    	int a = r.nextInt(3*chance);
    	switch(a) {
    	case 0: return new ItemStack(redstone_weed_seeds);
    	case 1: return new ItemStack(ghost_weed_seeds);
    	case 2: return new ItemStack(vanilla_weed_seeds);
    	default: return ItemStack.EMPTY;
    	}
    }
}
