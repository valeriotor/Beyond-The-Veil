package com.valeriotor.beyondtheveil;

import com.valeriotor.beyondtheveil.block.*;
import com.valeriotor.beyondtheveil.container.GearBenchContainer;
import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.item.SlugCatcherItem;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.GearBenchBE;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import com.valeriotor.beyondtheveil.tile.SlugBaitBE;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, References.MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.MODID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
        ENTITIES.register(bus);
    }

    public static final RegistryObject<EntityType<DeepOneEntity>> DEEP_ONE = ENTITIES.register("deep_one", () -> EntityType.Builder.of(DeepOneEntity::new, MobCategory.MONSTER).sized(0.6F, 2.1F).clientTrackingRange(32).build("deep_one"));

    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(References.ITEM_GROUP);
    public static final BlockBehaviour.Properties DAMP_WOOD_PROPERTIES = BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD);
    public static final BlockBehaviour.Properties DAMP_CANOPY_PROPERTIES = BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD).noOcclusion();
    public static final BlockBehaviour.Properties DARK_SAND_PROPERTIES = BlockBehaviour.Properties.of(Material.SAND).strength(3f).sound(SoundType.SAND);
    public static final BlockBehaviour.Properties BRICK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 7.0F);
    public static final BlockBehaviour.Properties ELDER_BRICK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 7.0F);

    public static final RegistryObject<Block> DAMP_WOOD = BLOCKS.register("damp_wood", () -> new Block(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> DARK_SAND = BLOCKS.register("dark_sand", () -> new Block(DARK_SAND_PROPERTIES));
    public static final RegistryObject<RotatedPillarBlock> DAMP_LOG = BLOCKS.register("damp_log", () -> new RotatedPillarBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<StairBlock> DAMP_WOOD_STAIRS = BLOCKS.register("damp_wood_stairs", () -> new StairBlock(() -> DAMP_WOOD.get().defaultBlockState(), DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> DAMP_CANOPY = BLOCKS.register("damp_canopy", () -> new DampCanopyBlock(DAMP_CANOPY_PROPERTIES));
    public static final RegistryObject<Block> DAMP_FILLED_CANOPY = BLOCKS.register("damp_filled_canopy", () -> new Block(DAMP_CANOPY_PROPERTIES));
    public static final RegistryObject<Block> WORN_BRICKS = BLOCKS.register("worn_bricks", () -> new Block(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> IDOL = BLOCKS.register("idol", () -> new IdolBlock(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noDrops()));
    public static final RegistryObject<Block> FISH_BARREL = BLOCKS.register("fish_barrel", () -> new FishBarrelBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> SLUG_BAIT = BLOCKS.register("slug_bait", () -> new SlugBaitBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> LAMP = BLOCKS.register("lamp", () -> new LampBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f).lightLevel(state -> 12).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> BLUE_BRICKS = BLOCKS.register("blue_bricks", () -> new Block(BRICK_PROPERTIES));
    public static final RegistryObject<StairBlock> WORN_BRICK_STAIRS = BLOCKS.register("worn_brick_stairs", () -> new StairBlock(() -> WORN_BRICKS.get().defaultBlockState(), DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<FenceBlock> DAMP_WOOD_FENCE = BLOCKS.register("damp_wood_fence", () -> new FenceBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> FUME_SPREADER = BLOCKS.register("fume_spreader", () -> new FumeSpreaderBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(3f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> SLEEP_CHAMBER = BLOCKS.register("sleep_chamber", () -> new SleepChamberBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(4f, 7f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GEAR_BENCH = BLOCKS.register("gear_bench", () -> new GearBenchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(4f, 7f).sound(SoundType.WOOD)));

    //public static final RegistryObject<Block> WATERY_CRADLE = BLOCKS.register("watery_cradle", () -> new Block(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    //public static final RegistryObject<Block> LACRYMATORY = BLOCKS.register("lacrymatory", () -> new Block(BRICK_PROPERTIES)); // new BlockLacrymatory(BlockNames.LACRYMATORY);
    //public static final RegistryObject<Block> CITY_MAPPER = BLOCKS.register("city_mapper", () -> new Block(BRICK_PROPERTIES)); // new BlockCityMapper(BlockNames.CITYMAPPER);
    public static final RegistryObject<Block> ELDER_STONE_BRICK = BLOCKS.register("elder_stone_brick", () -> new Block(ELDER_BRICK_PROPERTIES)); // new ModBlock(Material.ROCK, BlockNames.ELDER_STONE_BRICK).setHardness(10).setResistance(3000);
    public static final RegistryObject<Block> ELDER_STONE_BRICK_CHISELED = BLOCKS.register("elder_stone_brick_chiseled", () -> new Block(ELDER_BRICK_PROPERTIES)); // new ModBlock(Material.ROCK, BlockNames.ELDER_STONE_BRICK_CHISEL).setHardness(10).setResistance(3000);
    public static final RegistryObject<SlabBlock> ELDER_STONE_BRICK_SLAB = BLOCKS.register("elder_stone_brick_slab", () -> new SlabBlock(ELDER_BRICK_PROPERTIES)); // new ModBlock(Material.ROCK, BlockNames.ELDER_STONE_BRICK_CHISEL).setHardness(10).setResistance(3000);
    public static final RegistryObject<StairBlock> ELDER_STONE_BRICK_STAIRS = BLOCKS.register("elder_stone_brick_stairs", () -> new StairBlock(() -> ELDER_STONE_BRICK.get().defaultBlockState(), ELDER_BRICK_PROPERTIES)); // new ModStairs(BlockStoneElderBrick.getDefaultState(), BlockNames.ELDER_STONE_BRICK_STAIRS);
    public static final RegistryObject<Block> ELDER_BRICK = BLOCKS.register("elder_brick", () -> new Block(ELDER_BRICK_PROPERTIES)); // new ModBlock(Material.ROCK, BlockNames.ELDER_BRICK).setHardness(10).setResistance(3000);
    public static final RegistryObject<SlabBlock> ELDER_BRICK_SLAB = BLOCKS.register("elder_brick_slab", () -> new SlabBlock(ELDER_BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.ELDER_SLAB, Material.ROCK, false).setHardness(10).setResistance(3000);
    public static final RegistryObject<StairBlock> ELDER_BRICK_STAIRS = BLOCKS.register("elder_brick_stairs", () -> new StairBlock(() -> ELDER_BRICK.get().defaultBlockState(), ELDER_BRICK_PROPERTIES)); // new ModStairs(BlockStoneElderBrick.getDefaultState(), BlockNames.ELDER_STONE_BRICK_STAIRS);
    public static final RegistryObject<Block> ELDER_SMOOTH_STONE = BLOCKS.register("elder_smooth_stone", () -> new Block(ELDER_BRICK_PROPERTIES)); // new ModStairs(BlockStoneElderBrick.getDefaultState(), BlockNames.ELDER_STONE_BRICK_STAIRS);
    public static final RegistryObject<SlabBlock> ELDER_SMOOTH_STONE_SLAB = BLOCKS.register("elder_smooth_stone_slab", () -> new SlabBlock(ELDER_BRICK_PROPERTIES)); // new ModStairs(BlockStoneElderBrick.getDefaultState(), BlockNames.ELDER_STONE_BRICK_STAIRS);
    public static final RegistryObject<Block> BLOOD_BRICK = BLOCKS.register("blood_brick", () -> new Block(BRICK_PROPERTIES)); // new ModBlock(Material.ROCK, BlockNames.BLOODBRICKS);
    //public static final RegistryObject<Block> SACRIFICE_ALTAR = BLOCKS.register("sacrifice_altar", () -> new Block(BRICK_PROPERTIES)); // new BlockSacrificeAltarCore(Material.IRON, BlockNames.SACRIFICE_ALTAR);
    public static final RegistryObject<SlabBlock> BLOOD_BRICK_SLAB = BLOCKS.register("blood_brick_slab", () -> new SlabBlock(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB, Material.ROCK, false).setHardness(10).setResistance(3000);
    public static final RegistryObject<StairBlock> BLOOD_BRICK_STAIRS = BLOCKS.register("blood_brick_stairs", () -> new StairBlock(() -> BLOOD_BRICK.get().defaultBlockState(), BRICK_PROPERTIES)); // new ModStairs(BlockBloodBrick.getDefaultState(), BlockNames.BLOOD_BRICKS_STAIRS);
    public static final RegistryObject<Block> BLOOD_SMOOTH_STONE = BLOCKS.register("blood_smooth_stone", () -> new Block(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
    public static final RegistryObject<SlabBlock> BLOOD_SMOOTH_STONE_SLAB = BLOCKS.register("blood_smooth_stone_slab", () -> new SlabBlock(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
    public static final RegistryObject<Block> HEART = BLOCKS.register("heart", () -> new HeartBlock(BlockBehaviour.Properties.of(Material.SPONGE).strength(0.5F))); // new BlockHeart(Material.SPONGE, BlockNames.HEART);
    //public static final RegistryObject<Block> BLOOD_WELL = BLOCKS.register("blood_well", () -> new Block(BRICK_PROPERTIES)); // new BlockBloodWell(Material.PORTAL, BlockNames.BLOOD_WELL);
    //public static final RegistryObject<Block> STATUE = BLOCKS.register("statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.STATUE, WorshipType.DEFAULT);
    //public static final RegistryObject<Block> SACRIFICE_STATUE = BLOCKS.register("sacrifice_statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.SACRIFICE_STATUE, WorshipType.SACRIFICE);
    //public static final RegistryObject<Block> PENITENCE_STATUE = BLOCKS.register("penitence_statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.PENITENCE_STATUE, WorshipType.PENITENCE);
    //public static final RegistryObject<Block> MEMORY_SIEVE = BLOCKS.register("memory_sieve", () -> new Block(BRICK_PROPERTIES)); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    //public static final RegistryObject<Block> DREAM_FOCUS = BLOCKS.register("dream_focus", () -> new Block(BRICK_PROPERTIES)); // new BlockDreamFocus(BlockNames.DREAMFOCUS);
    //public static final RegistryObject<Block> DREAM_FOCUS_FLUIDS = BLOCKS.register("dream_focus_fluids", () -> new Block(BRICK_PROPERTIES)); // new BlockDreamFocusFluids(BlockNames.DREAMFOCUSFLUIDS);
    //public static final RegistryObject<Block> DREAM_FOCUS_VILLAGERS = BLOCKS.register("dream_focus_villagers", () -> new Block(BRICK_PROPERTIES)); // new BlockDreamFocusVillagers(BlockNames.DREAMFOCUSVILLAGERS);
    //public static final RegistryObject<Block> CURTAIN = BLOCKS.register("curtain", () -> new Block(BRICK_PROPERTIES)); // new BlockCurtain(BlockNames.CURTAIN);
    //public static final RegistryObject<Block> MEGYDREA = BLOCKS.register("megydrea", () -> new Block(BRICK_PROPERTIES)); // new BlockMegydrea(BlockNames.MEGYDREA);
    //public static final RegistryObject<Block> THICK_ALGAE = BLOCKS.register("thick_algae", () -> new Block(BRICK_PROPERTIES)); // new BlockThickAlgae(BlockNames.THICK_ALGAE);
    //public static final RegistryObject<Block> ARCHE_PORTAL = BLOCKS.register("arche_portal", () -> new Block(BRICK_PROPERTIES)); // new BlockArchePortal(BlockNames.ARCHE_PORTAL);
    //public static final RegistryObject<Block> DARK_GLASS = BLOCKS.register("dark_glass", () -> new Block(BRICK_PROPERTIES)); // new BlockDarkGlass(BlockNames.DARK_GLASS);
    //public static final RegistryObject<Block> DEEP_PRISMARINE = BLOCKS.register("deep_prismarine", () -> new Block(BRICK_PROPERTIES)); // new BlockDeepPrismarine(BlockNames.DEEP_PRISMARINE);
    //public static final RegistryObject<Block> ARENA = BLOCKS.register("arena", () -> new Block(BRICK_PROPERTIES)); // new BlockArena(BlockNames.ARENA);
    //public static final RegistryObject<Block> DEEP_CHEST = BLOCKS.register("deep_chest", () -> new Block(BRICK_PROPERTIES)); // new BlockDeepChest(BlockNames.DEEP_CHEST);



    public static final RegistryObject<Item> DAMP_WOOD_ITEM = fromBlock(DAMP_WOOD);
    public static final RegistryObject<Item> DARK_SAND_ITEM = fromBlock(DARK_SAND);
    public static final RegistryObject<Item> DAMP_LOG_ITEM = fromBlock(DAMP_LOG);
    public static final RegistryObject<Item> DAMP_WOOD_STAIRS_ITEM = fromBlock(DAMP_WOOD_STAIRS);
    public static final RegistryObject<Item> DAMP_CANOPY_ITEM = fromBlock(DAMP_CANOPY);
    public static final RegistryObject<Item> DAMP_FILLED_CANOPY_ITEM = fromBlock(DAMP_FILLED_CANOPY);
    public static final RegistryObject<Item> WORN_BRICKS_ITEM = fromBlock(WORN_BRICKS);
    public static final RegistryObject<Item> IDOL_ITEM = fromBlock(IDOL);
    public static final RegistryObject<Item> FISH_BARREL_ITEM = fromBlock(FISH_BARREL);
    public static final RegistryObject<Item> SLUG_BAIT_ITEM = fromBlock(SLUG_BAIT);
    public static final RegistryObject<Item> LAMP_ITEM = fromBlock(LAMP);
    public static final RegistryObject<Item> BLUE_BRICKS_ITEM = fromBlock(BLUE_BRICKS);
    public static final RegistryObject<Item> WORN_BRICK_STAIRS_ITEM = fromBlock(WORN_BRICK_STAIRS);
    public static final RegistryObject<Item> DAMP_WOOD_FENCE_ITEM = fromBlock(DAMP_WOOD_FENCE);
    public static final RegistryObject<Item> FUME_SPREADER_ITEM = fromBlock(FUME_SPREADER);
    public static final RegistryObject<Item> SLEEP_CHAMBER_ITEM = fromBlock(SLEEP_CHAMBER);
    public static final RegistryObject<Item> GEAR_BENCH_ITEM = fromBlock(GEAR_BENCH);
    //public static final RegistryObject<Item> WATERY_CRADLE_ITEM = fromBlock(WATERY_CRADLE);
    //public static final RegistryObject<Item> LACRYMATORY_ITEM = fromBlock(LACRYMATORY);
    //public static final RegistryObject<Item> CITY_MAPPER_ITEM = fromBlock(CITY_MAPPER);
    public static final RegistryObject<Item> ELDER_STONE_BRICK_ITEM = fromBlock(ELDER_STONE_BRICK);
    public static final RegistryObject<Item> ELDER_STONE_BRICK_SLAB_ITEM = fromBlock(ELDER_STONE_BRICK_SLAB);
    public static final RegistryObject<Item> ELDER_STONE_BRICK_STAIRS_ITEM = fromBlock(ELDER_STONE_BRICK_STAIRS);
    public static final RegistryObject<Item> ELDER_STONE_BRICK_CHISELED_ITEM = fromBlock(ELDER_STONE_BRICK_CHISELED);
    public static final RegistryObject<Item> ELDER_BRICK_ITEM = fromBlock(ELDER_BRICK);
    public static final RegistryObject<Item> ELDER_BRICK_SLAB_ITEM = fromBlock(ELDER_BRICK_SLAB);
    public static final RegistryObject<Item> ELDER_BRICK_STAIRS_ITEM = fromBlock(ELDER_BRICK_STAIRS);
    public static final RegistryObject<Item> ELDER_SMOOTH_STONE_ITEM = fromBlock(ELDER_SMOOTH_STONE);
    public static final RegistryObject<Item> ELDER_SMOOTH_STONE_SLAB_ITEM = fromBlock(ELDER_SMOOTH_STONE_SLAB);
    //public static final RegistryObject<Item> HEART_ITEM = fromBlock(HEART);
    //public static final RegistryObject<Item> SACRIFICE_ALTAR_ITEM = fromBlock(SACRIFICE_ALTAR);
    public static final RegistryObject<Item> BLOOD_BRICK_ITEM = fromBlock(BLOOD_BRICK);
    public static final RegistryObject<Item> BLOOD_BRICK_SLAB_ITEM = fromBlock(BLOOD_BRICK_SLAB);
    public static final RegistryObject<Item> BLOOD_BRICK_STAIRS_ITEM = fromBlock(BLOOD_BRICK_STAIRS);
    public static final RegistryObject<Item> BLOOD_SMOOTH_STONE_ITEM = fromBlock(BLOOD_SMOOTH_STONE);
    public static final RegistryObject<Item> BLOOD_SMOOTH_STONE_SLAB_ITEM = fromBlock(BLOOD_SMOOTH_STONE_SLAB);
    public static final RegistryObject<Item> HEART_ITEM = fromBlock(HEART);

    //public static final RegistryObject<Item> BLOOD_WELL_ITEM = fromBlock(BLOOD_WELL);
    //public static final RegistryObject<Item> STATUE_ITEM = fromBlock(STATUE);
    //public static final RegistryObject<Item> SACRIFICE_STATUE_ITEM = fromBlock(SACRIFICE_STATUE);
    //public static final RegistryObject<Item> PENITENCE_STATUE_ITEM = fromBlock(PENITENCE_STATUE);
    //public static final RegistryObject<Item> MEMORY_SIEVE_ITEM = fromBlock(MEMORY_SIEVE);
    //public static final RegistryObject<Item> DREAM_FOCUS_ITEM = fromBlock(DREAM_FOCUS);
    //public static final RegistryObject<Item> DREAM_FOCUS_FLUIDS_ITEM = fromBlock(DREAM_FOCUS_FLUIDS);
    //public static final RegistryObject<Item> DREAM_FOCUS_VILLAGERS_ITEM = fromBlock(DREAM_FOCUS_VILLAGERS);
    //public static final RegistryObject<Item> CURTAIN_ITEM = fromBlock(CURTAIN);
    //public static final RegistryObject<Item> MEGYDREA_ITEM = fromBlock(MEGYDREA);
    //public static final RegistryObject<Item> THICK_ALGAE_ITEM = fromBlock(THICK_ALGAE);
    //public static final RegistryObject<Item> ARCHE_PORTAL_ITEM = fromBlock(ARCHE_PORTAL);
    //public static final RegistryObject<Item> DARK_GLASS_ITEM = fromBlock(DARK_GLASS);
    //public static final RegistryObject<Item> DEEP_PRISMARINE_ITEM = fromBlock(DEEP_PRISMARINE);
    //public static final RegistryObject<Item> ARENA_ITEM = fromBlock(ARENA);
    //public static final RegistryObject<Item> DEEP_CHEST_ITEM = fromBlock(DEEP_CHEST);

    public static final RegistryObject<Item> ONIRIC_INCENSE = ITEMS.register("oniric_incense", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> SLUG_CATCHER = ITEMS.register("slug_catcher", SlugCatcherItem::new);
    public static final RegistryObject<Item> SLUG = ITEMS.register("slug", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> CANOE = ITEMS.register("canoe", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> RUM = ITEMS.register("drink_rum", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> WINE = ITEMS.register("drink_wine", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> ALE = ITEMS.register("drink_ale", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> VODKA = ITEMS.register("drink_vodka", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> MEAD = ITEMS.register("drink_mead", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> CUP = ITEMS.register("drink_empty", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> FLUTE = ITEMS.register("flute", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> WOLF_MEDALLION = ITEMS.register("wolf_medallion", () -> new Item(ITEM_PROPERTIES)); // new ItemWolfMedallion("wolf_medallion");
    public static final RegistryObject<Item> TABLET = ITEMS.register("tablet", () -> new Item(ITEM_PROPERTIES)); // new ItemTablet("tablet");
    public static final RegistryObject<Item> BRONZE_SPHERE= ITEMS.register("bronze_sphere", () -> new Item(ITEM_PROPERTIES)); // new ItemBronzeSphere("bronze_sphere");
    public static final RegistryObject<Item> REDSTONE_WEED_SEEDS = ITEMS.register("redstone_weed_seeds", () -> new Item(ITEM_PROPERTIES)); // new ItemSpecialGrassSeeds(BlockRegistry.PlantRedstoneWeed, BlockRegistry.BlockRedstoneGrass, BlockNames.REDSTONEWEED);
    public static final RegistryObject<Item> GHOST_WEED_SEEDS = ITEMS.register("ghost_weed_seeds", () -> new Item(ITEM_PROPERTIES)); // new ItemSpecialGrassSeeds(BlockRegistry.PlantGhostWeed, BlockRegistry.BlockGhostGrass, BlockNames.GHOSTWEED);
    public static final RegistryObject<Item> VANILLA_WEED_SEEDS = ITEMS.register("vanilla_weed_seeds", () -> new Item(ITEM_PROPERTIES)); // new ItemSpecialGrassSeeds(BlockRegistry.PlantVanillaWeed, Blocks.GRASS, BlockNames.VANILLAWEED);
    public static final RegistryObject<Item> BLACKJACK = ITEMS.register("blackjack", () -> new Item(ITEM_PROPERTIES)); // new ItemBlackjack("blackjack");
    public static final RegistryObject<Item> SPINE = ITEMS.register("spine", () -> new Item(ITEM_PROPERTIES)); // new ModItem("spine");
    public static final RegistryObject<Item> HELD_VILLAGER = ITEMS.register("held_villager", () -> new Item(ITEM_PROPERTIES)); // new ItemHeldVillager("held_villager");
    public static final RegistryObject<Item> HELD_WEEPER = ITEMS.register("held_weeper", () -> new Item(ITEM_PROPERTIES)); // new ItemHeldWeeper("held_weeper");
    public static final RegistryObject<Item> HELD_FLETUM = ITEMS.register("held_fletum", () -> new Item(ITEM_PROPERTIES)); // new ItemHeldFletum("held_fletum");
    public static final RegistryObject<Item> HELD_SHOGGOTH = ITEMS.register("held_shoggoth", () -> new Item(ITEM_PROPERTIES)); // new ItemHeldShoggoth("held_shoggoth");
    public static final RegistryObject<Item> SURGERY_TOOLS = ITEMS.register("surgery_tools", () -> new Item(ITEM_PROPERTIES)); // new ModItem("surgery_tools");
    public static final RegistryObject<Item> BONE_TIARA = ITEMS.register("bone_tiara", () -> new Item(ITEM_PROPERTIES)); // new ItemBoneTiara("bone_tiara");
    public static final RegistryObject<Item> BLEEDING_BELT = ITEMS.register("bleeding_belt", () -> new Item(ITEM_PROPERTIES)); // new ItemBleedingBelt("bleeding_belt");
    public static final RegistryObject<Item> DREAM_BOTTLE = ITEMS.register("dream_bottle", () -> new Item(ITEM_PROPERTIES)); // new ItemDreamBottle("dream_bottle");
    public static final RegistryObject<Item> SHOGGOTH_MAP = ITEMS.register("shoggoth_map", () -> new Item(ITEM_PROPERTIES)); // new ModItem("shoggoth_map").setMaxStackSize(1);
    public static final RegistryObject<Item> BLOOD_COVENANT = ITEMS.register("blood_covenant", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodCovenant("blood_covenant").setMaxStackSize(1);
    public static final RegistryObject<Item> REVELATION_RING = ITEMS.register("revelation_ring", () -> new Item(ITEM_PROPERTIES)); // new ItemRevelationRing("revelation_ring").setMaxStackSize(1);
    public static final RegistryObject<Item> AZACNO_CHARM = ITEMS.register("azacno_charm", () -> new Item(ITEM_PROPERTIES)); // new ItemAzacnoCharm("azacno_charm").setMaxStackSize(1);
    public static final RegistryObject<Item> BLOOD_CROWN = ITEMS.register("blood_crown", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodCrown("blood_crown").setMaxStackSize(1);
    public static final RegistryObject<Item> CORAL_STAFF = ITEMS.register("coral_staff", () -> new Item(ITEM_PROPERTIES)); // new ItemCoralStaff("coral_staff");
    public static final RegistryObject<Item> SIGIL_ZOMBIE = ITEMS.register("sigil_zombie", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilUndead("sigil_zombie", BloodMobs.BLOOD_ZOMBIE);
    public static final RegistryObject<Item> SIGIL_SKELLIE = ITEMS.register("sigil_skellie", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilUndead("sigil_skellie", BloodMobs.BLOOD_SKELLIE);
    public static final RegistryObject<Item> SIGIL_PLAYER = ITEMS.register("sigil_player", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilPlayer("sigil_player");
    public static final RegistryObject<Item> SIGIL_PATHWAY = ITEMS.register("sigil_pathway", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilPathway("sigil_pathway");
    public static final RegistryObject<Item> SACRIFICIAL_KNIFE = ITEMS.register("sacrificial_knife", () -> new Item(ITEM_PROPERTIES)); // new ItemSacrificialKnife("sacrificial_knife");
    public static final RegistryObject<Item> MEMORY_PHIAL = ITEMS.register("memory_phial", () -> new Item(ITEM_PROPERTIES)); // new ItemMemoryPhial("memory_phial");
    public static final RegistryObject<Item> NECRONOMICON = ITEMS.register("necronomicon", () -> new Item(ITEM_PROPERTIES)); // new ItemNecronomicon("necronomicon");
    public static final RegistryObject<Item> GEAR = ITEMS.register("gear", () -> new Item(ITEM_PROPERTIES)); // new ModItem("gear");
    public static final RegistryObject<Item> SURGEON_SUMMONS = ITEMS.register("surgeon_summons", () -> new Item(ITEM_PROPERTIES)); // new ItemSurgeonSummoner("surgeon_summons");
    public static final RegistryObject<Item> CRUCIBLE = ITEMS.register("crucible", () -> new Item(ITEM_PROPERTIES)); // new ItemCrucible("crucible");
    public static final RegistryObject<Item> BLACK_MIRROR = ITEMS.register("black_mirror", () -> new Item(ITEM_PROPERTIES)); // new ItemBlackMirror("black_mirror");
    public static final RegistryObject<Item> FLESH_CARBON_TOKEN = ITEMS.register("flesh_carbon_token", () -> new Item(ITEM_PROPERTIES)); // new ModItem("fleshcarbontoken");


    public static final RegistryObject<Item> DEEP_ONE_EGG = ITEMS.register("deep_one", () -> new ForgeSpawnEggItem(DEEP_ONE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));


    public static final RegistryObject<BlockEntityType<GearBenchBE>> GEAR_BENCH_BE = BLOCK_ENTITIES.register(GEAR_BENCH.getId().getPath(), () -> BlockEntityType.Builder.of(GearBenchBE::new, GEAR_BENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<SlugBaitBE>> SLUG_BAIT_BE = BLOCK_ENTITIES.register(SLUG_BAIT.getId().getPath(), () -> BlockEntityType.Builder.of(SlugBaitBE::new, SLUG_BAIT.get()).build(null));
    public static final RegistryObject<BlockEntityType<HeartBE>> HEART_BE = BLOCK_ENTITIES.register(HEART.getId().getPath(), () -> BlockEntityType.Builder.of(HeartBE::new, HEART.get()).build(null));

    public static final RegistryObject<MenuType<GearBenchContainer>> GEAR_BENCH_CONTAINER = CONTAINERS.register(GEAR_BENCH.getId().getPath(), () -> IForgeMenuType.create((windowId, inv, data) -> new GearBenchContainer(windowId, data.readBlockPos(), inv, inv.player)));




    private static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> blockObject) {
        return ITEMS.register(blockObject.getId().getPath(), () -> new BlockItem(blockObject.get(), ITEM_PROPERTIES));
    }


}
