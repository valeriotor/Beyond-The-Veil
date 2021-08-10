package com.valeriotor.beyondtheveil.items;

import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.items.baubles.ItemAzacnoCharm;
import com.valeriotor.beyondtheveil.items.baubles.ItemBleedingBelt;
import com.valeriotor.beyondtheveil.items.baubles.ItemBloodCovenant;
import com.valeriotor.beyondtheveil.items.baubles.ItemBloodCrown;
import com.valeriotor.beyondtheveil.items.baubles.ItemBoneTiara;
import com.valeriotor.beyondtheveil.items.baubles.ItemRevelationRing;
import com.valeriotor.beyondtheveil.items.baubles.ItemWolfMedallion;
import com.valeriotor.beyondtheveil.lib.BlockNames;
import com.valeriotor.beyondtheveil.proxy.ClientProxy;
import com.valeriotor.beyondtheveil.tileEntities.TileBloodWell.BloodMobs;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;


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
	public static final ModItem spine = new ModItem("spine");
	public static final ItemHeldVillager held_villager = new ItemHeldVillager("held_villager");
	public static final ItemHeldWeeper held_weeper = new ItemHeldWeeper("held_weeper");
	public static final ItemHeldFletum held_fletum = new ItemHeldFletum("held_fletum");
	public static final ItemHeldShoggoth held_shoggoth = new ItemHeldShoggoth("held_shoggoth");
	public static final ModItem surgery_tools = new ModItem("surgery_tools");
	public static final ItemBoneTiara bone_tiara = new ItemBoneTiara();
	public static final ItemBleedingBelt bleeding_belt = new ItemBleedingBelt("bleeding_belt");
	public static final ItemDreamBottle dream_bottle = new ItemDreamBottle("dream_bottle");
	public static final Item shoggoth_map = new ModItem("shoggoth_map").setMaxStackSize(1);
	public static final Item blood_covenant = new ItemBloodCovenant("blood_covenant").setMaxStackSize(1);
	public static final Item revelation_ring = new ItemRevelationRing("revelation_ring").setMaxStackSize(1);
	public static final Item azacno_charm = new ItemAzacnoCharm("azacno_charm").setMaxStackSize(1);
	public static final Item blood_crown = new ItemBloodCrown("blood_crown").setMaxStackSize(1);
	public static final ItemCoralStaff coral_staff = new ItemCoralStaff("coral_staff");
	public static final ItemBloodSigilUndead sigil_zombie = new ItemBloodSigilUndead("sigil_zombie", BloodMobs.BLOOD_ZOMBIE);
	public static final ItemBloodSigilUndead sigil_skellie = new ItemBloodSigilUndead("sigil_skellie", BloodMobs.BLOOD_SKELLIE);
	public static final ItemBloodSigilPlayer sigil_player = new ItemBloodSigilPlayer("sigil_player");
	public static final ItemBloodSigilPathway sigil_pathway = new ItemBloodSigilPathway("sigil_pathway");
	public static final ItemSacrificialKnife sacrificial_knife = new ItemSacrificialKnife("sacrificial_knife");
	public static final ItemMemoryPhial memory_phial = new ItemMemoryPhial("memory_phial");
	public static final ItemNecronomicon necronomicon = new ItemNecronomicon("necronomicon");
	public static final ModItem gear = new ModItem("gear");
	public static final ModItem surgeon_summons = new ItemSurgeonSummoner("surgeon_summons");
	public static final ItemCrucible crucible = new ItemCrucible("crucible");
	public static final ItemBlackMirror black_mirror = new ItemBlackMirror("black_mirror");
	public static final ModItem fleshcarbontoken = new ModItem("fleshcarbontoken");
	public static ItemInkMask ink_mask; // name: ink_mask

	
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
        ClientProxy.registerItemRenderer(spine, 0, "inventory");
        ClientProxy.registerItemRenderer(held_villager, 0, "inventory");
        ClientProxy.registerItemRenderer(held_weeper, 0, "inventory");
        ClientProxy.registerItemRenderer(held_fletum, 0, "inventory");
        ClientProxy.registerItemRenderer(surgery_tools, 0, "inventory");
        ClientProxy.registerItemRenderer(bone_tiara, 0, "inventory");
        ClientProxy.registerItemRenderer(bleeding_belt, 0, "inventory");
        ClientProxy.registerItemRenderer(dream_bottle, 0, "inventory");
        ClientProxy.registerItemRenderer(shoggoth_map, 0, "inventory");
        ClientProxy.registerItemRenderer(held_shoggoth, 0, "inventory");
        ClientProxy.registerItemRenderer(blood_covenant, 0, "inventory");
        ClientProxy.registerItemRenderer(revelation_ring, 0, "inventory");
        ClientProxy.registerItemRenderer(azacno_charm, 0, "inventory");
        ClientProxy.registerItemRenderer(blood_crown, 0, "inventory");
        ClientProxy.registerItemRenderer(coral_staff, 0, "inventory");
        ClientProxy.registerItemRenderer(sigil_zombie, 0, "inventory");
        ClientProxy.registerItemRenderer(sigil_skellie, 0, "inventory");
        ClientProxy.registerItemRenderer(sigil_player, 0, "inventory");
        ClientProxy.registerItemRenderer(sigil_pathway, 0, "inventory");
        ClientProxy.registerItemRenderer(sacrificial_knife, 0, "inventory");
        ClientProxy.registerItemRenderer(memory_phial, 0, "inventory");
        ClientProxy.registerItemRenderer(necronomicon, 0, "inventory");
        ClientProxy.registerItemRenderer(gear, 0, "inventory");
        ClientProxy.registerItemRenderer(surgeon_summons, 0, "inventory");
        ClientProxy.registerItemRenderer(crucible, 0, "inventory");
        ClientProxy.registerItemRenderer(black_mirror, 0, "inventory");
        ClientProxy.registerItemRenderer(fleshcarbontoken, 0, "inventory");
        ClientProxy.registerItemRenderer(ink_mask, 0, "inventory");

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
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockGhostGrass), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantArborealGenerator), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantSaplingShrub), 0, "inventory");
        //ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantBreaker), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.PlantVijhiss), 0, "inventory");
        //ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockMutator), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockWateryCradle), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockLacrymatory), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockCityMapper), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockStoneElderBrick), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockStoneElderBrickChiseled), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockElderBrick), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockStoneElderBrickStairs), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.SlabElderHalf), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockHeart), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockSacrificeAltar), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockBloodBrick), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockBloodBrickStairs), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.SlabBloodHalf), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockBloodWell), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockStatue), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockSacrificeStatue), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockPenitenceStatue), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockMemorySieve), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockGearBench), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockDreamFocus), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockDreamFocusFluids), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockDreamFocusVillagers), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockCurtain), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockMegydrea), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockThickAlgae), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockArchePortal), 0, "inventory");
        ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockDarkGlass), 0, "inventory");
		ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockArena), 0, "inventory");
		ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockDeepPrismarine), 0, "inventory");
		ClientProxy.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.BlockDeepChest), 0, "inventory");

	}
    
    public static void registerItems(RegistryEvent.Register<Item> event) {
		ItemArmor.ArmorMaterial inkMaskMaterial = EnumHelper.addArmorMaterial("ink_mask", "beyondtheveil:ink_mask", 1000, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
		ink_mask = new ItemInkMask("ink_mask", inkMaskMaterial);

		IForgeRegistry<Item> registry = event.getRegistry();
    	registry.register(new ItemBlock(BlockRegistry.DampWood).setRegistryName(BlockRegistry.DampWood.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.FumeSpreader).setRegistryName(BlockRegistry.FumeSpreader.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.DarkSand).setRegistryName(BlockRegistry.DarkSand.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.DampStone).setRegistryName(BlockRegistry.DampStone.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.DampLog).setRegistryName(BlockRegistry.DampLog.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.DampWoodStairs).setRegistryName(BlockRegistry.DampWoodStairs.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.DampCanopy).setRegistryName(BlockRegistry.DampCanopy.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.DampCanopyWood).setRegistryName(BlockRegistry.DampCanopyWood.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.WornBricks).setRegistryName(BlockRegistry.WornBricks.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockIdol).setRegistryName(BlockRegistry.BlockIdol.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockBarrel).setRegistryName(BlockRegistry.BlockBarrel.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockSlugBait).setRegistryName(BlockRegistry.BlockSlugBait.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockLamp).setRegistryName(BlockRegistry.BlockLamp.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BricksBlue).setRegistryName(BlockRegistry.BricksBlue.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.WornBrickStairs).setRegistryName(BlockRegistry.WornBrickStairs.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.DampWoodFence).setRegistryName(BlockRegistry.DampWoodFence.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.SleepChamber).setRegistryName(BlockRegistry.SleepChamber.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.SleepChamberAdvanced).setRegistryName(BlockRegistry.SleepChamberAdvanced.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.PlantBeanStalk).setRegistryName(BlockRegistry.PlantBeanStalk.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockRedstoneGrass).setRegistryName(BlockRegistry.BlockRedstoneGrass.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockGhostGrass).setRegistryName(BlockRegistry.BlockGhostGrass.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.PlantArborealGenerator).setRegistryName(BlockRegistry.PlantArborealGenerator.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.PlantSaplingShrub).setRegistryName(BlockRegistry.PlantSaplingShrub.getRegistryName()));
    	//registry.register(new ItemBlock(BlockRegistry.PlantBreaker).setRegistryName(BlockRegistry.PlantBreaker.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.PlantVijhiss).setRegistryName(BlockRegistry.PlantVijhiss.getRegistryName()));
    	//registry.register(new ItemBlock(BlockRegistry.BlockMutator).setRegistryName(BlockRegistry.BlockMutator.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockWateryCradle).setRegistryName(BlockRegistry.BlockWateryCradle.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockLacrymatory).setRegistryName(BlockRegistry.BlockLacrymatory.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockCityMapper).setRegistryName(BlockRegistry.BlockCityMapper.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockStoneElderBrick).setRegistryName(BlockRegistry.BlockStoneElderBrick.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockStoneElderBrickChiseled).setRegistryName(BlockRegistry.BlockStoneElderBrickChiseled.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockElderBrick).setRegistryName(BlockRegistry.BlockElderBrick.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockStoneElderBrickStairs).setRegistryName(BlockRegistry.BlockStoneElderBrickStairs.getRegistryName()));
    	registry.register(new ItemSlab(BlockRegistry.SlabElderHalf, BlockRegistry.SlabElderHalf, BlockRegistry.SlabElderDouble).setRegistryName(BlockRegistry.SlabElderHalf.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockHeart).setRegistryName(BlockRegistry.BlockHeart.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockSacrificeAltar).setRegistryName(BlockRegistry.BlockSacrificeAltar.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockBloodBrick).setRegistryName(BlockRegistry.BlockBloodBrick.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockBloodBrickStairs).setRegistryName(BlockRegistry.BlockBloodBrickStairs.getRegistryName()));
    	registry.register(new ItemSlab(BlockRegistry.SlabBloodHalf, BlockRegistry.SlabBloodHalf, BlockRegistry.SlabBloodDouble).setRegistryName(BlockRegistry.SlabBloodHalf.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockBloodWell).setRegistryName(BlockRegistry.BlockBloodWell.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockStatue).setRegistryName(BlockRegistry.BlockStatue.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockSacrificeStatue).setRegistryName(BlockRegistry.BlockSacrificeStatue.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockPenitenceStatue).setRegistryName(BlockRegistry.BlockPenitenceStatue.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockMemorySieve).setRegistryName(BlockRegistry.BlockMemorySieve.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockGearBench).setRegistryName(BlockRegistry.BlockGearBench.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockDreamFocus).setRegistryName(BlockRegistry.BlockDreamFocus.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockDreamFocusFluids).setRegistryName(BlockRegistry.BlockDreamFocusFluids.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockDreamFocusVillagers).setRegistryName(BlockRegistry.BlockDreamFocusVillagers.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockCurtain).setRegistryName(BlockRegistry.BlockCurtain.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockMegydrea).setRegistryName(BlockRegistry.BlockMegydrea.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockThickAlgae).setRegistryName(BlockRegistry.BlockThickAlgae.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockArchePortal).setRegistryName(BlockRegistry.BlockArchePortal.getRegistryName()));
    	registry.register(new ItemBlock(BlockRegistry.BlockDarkGlass).setRegistryName(BlockRegistry.BlockDarkGlass.getRegistryName()));
		registry.register(new ItemBlock(BlockRegistry.BlockDeepPrismarine).setRegistryName(BlockRegistry.BlockDeepPrismarine.getRegistryName()));
		registry.register(new ItemBlock(BlockRegistry.BlockArena).setRegistryName(BlockRegistry.BlockArena.getRegistryName()));
		registry.register(new ItemBlock(BlockRegistry.BlockDeepChest).setRegistryName(BlockRegistry.BlockDeepChest.getRegistryName()));
    	registry.register(ItemRegistry.testItem);
    	registry.register(ItemRegistry.oniricIncense);
    	registry.register(ItemRegistry.slugCatcher);
    	registry.register(ItemRegistry.slug);
    	registry.register(ItemRegistry.canoe);
    	registry.register(ItemRegistry.rum);
    	registry.register(ItemRegistry.wine);
    	registry.register(ItemRegistry.ale);
    	registry.register(ItemRegistry.vodka);
    	registry.register(ItemRegistry.mead);
    	registry.register(ItemRegistry.cup);
    	registry.register(ItemRegistry.flute);
    	registry.register(ItemRegistry.saw_cleaver);
    	registry.register(ItemRegistry.wolf_medallion);
    	registry.register(ItemRegistry.tablet);
    	registry.register(ItemRegistry.bronze_sphere);
    	registry.register(ItemRegistry.blackjack);
    	registry.register(ItemRegistry.spine);
    	registry.register(ItemRegistry.held_villager);
    	registry.register(ItemRegistry.held_weeper);
    	registry.register(ItemRegistry.held_fletum);
    	registry.register(ItemRegistry.surgery_tools);
    	registry.register(ItemRegistry.bone_tiara);
    	registry.register(ItemRegistry.bleeding_belt);
    	registry.register(ItemRegistry.dream_bottle);
    	registry.register(ItemRegistry.shoggoth_map);
    	registry.register(ItemRegistry.held_shoggoth);
    	registry.register(ItemRegistry.blood_covenant);
    	registry.register(ItemRegistry.revelation_ring);
    	registry.register(ItemRegistry.azacno_charm);
    	registry.register(ItemRegistry.blood_crown);
    	registry.register(ItemRegistry.coral_staff);
    	registry.register(ItemRegistry.sigil_zombie);
    	registry.register(ItemRegistry.sigil_skellie);
    	registry.register(ItemRegistry.sigil_player);
    	registry.register(ItemRegistry.sigil_pathway);
    	registry.register(ItemRegistry.sacrificial_knife);
    	registry.register(ItemRegistry.memory_phial);
    	registry.register(ItemRegistry.necronomicon);
    	registry.register(ItemRegistry.gear);
    	registry.register(ItemRegistry.surgeon_summons);
    	registry.register(ItemRegistry.crucible);
    	registry.register(ItemRegistry.black_mirror);
    	registry.register(ItemRegistry.fleshcarbontoken);
    	registry.register(ItemRegistry.ink_mask);

    	
    	registry.register(ItemRegistry.redstone_weed_seeds);
    	BlockRegistry.PlantRedstoneWeed.setSeed(redstone_weed_seeds);
    	registry.register(ItemRegistry.ghost_weed_seeds);
    	BlockRegistry.PlantGhostWeed.setSeed(ghost_weed_seeds);
    	registry.register(ItemRegistry.vanilla_weed_seeds);
    	BlockRegistry.PlantVanillaWeed.setSeed(vanilla_weed_seeds);
    }
    
    
    
    public static Item getRandomArtifact(Random r) {
    	int a = r.nextInt(5);
    	switch(a) {
    	case 0: return saw_cleaver;
    	case 1: return flute;
    	case 2: return wolf_medallion;
    	case 3: return bronze_sphere;
    	case 4: return crucible;
    	default: return null;
    	}
    }
    
    public static ItemStack getRandomSeed(Random r, int chance) {
    	int a = r.nextInt(9*chance);
    	switch(a) {
    	case 0:
    	case 1: 
    	case 2: return new ItemStack(redstone_weed_seeds);
    	case 3:
    	case 4: 
    	case 5: return new ItemStack(ghost_weed_seeds);
    	case 6:
    	case 7:
    	case 8: return new ItemStack(vanilla_weed_seeds);
    	case 9: return new ItemStack(BlockRegistry.PlantBeanStalk);
    	default: return ItemStack.EMPTY;
    	}
    }
}
