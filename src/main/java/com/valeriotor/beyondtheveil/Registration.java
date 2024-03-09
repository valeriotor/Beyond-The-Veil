package com.valeriotor.beyondtheveil;

import com.valeriotor.beyondtheveil.block.*;
import com.valeriotor.beyondtheveil.container.GearBenchContainer;
import com.valeriotor.beyondtheveil.entity.*;
import com.valeriotor.beyondtheveil.fluid.SurgicalFluidType;
import com.valeriotor.beyondtheveil.item.*;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, References.MODID);
    private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, References.MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, References.MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, References.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.MODID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        FLUID_TYPES.register(bus);
        FLUIDS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
        ENTITIES.register(bus);
        CREATIVE_TAB.register(bus);
    }

    public static final RegistryObject<EntityType<DeepOneEntity>> DEEP_ONE = ENTITIES.register("deep_one", () -> EntityType.Builder.of(DeepOneEntity::new, MobCategory.MONSTER).sized(0.6F, 2.1F).clientTrackingRange(32).build("deep_one"));
    public static final RegistryObject<EntityType<BloodSkeletonEntity>> BLOOD_SKELETON = ENTITIES.register("blood_skeleton", () -> EntityType.Builder.of(BloodSkeletonEntity::new, MobCategory.MONSTER).sized(0.7F, 5F).clientTrackingRange(32).build("blood_skeleton"));
    public static final RegistryObject<EntityType<BloodZombieEntity>> BLOOD_ZOMBIE = ENTITIES.register("blood_zombie", () -> EntityType.Builder.of(BloodZombieEntity::new, MobCategory.MONSTER).sized(0.7F, 5F).clientTrackingRange(32).build("blood_zombie"));
    public static final RegistryObject<EntityType<BloodWraithEntity>> BLOOD_WRAITH = ENTITIES.register("blood_wraith", () -> EntityType.Builder.of(BloodWraithEntity::new, MobCategory.MONSTER).sized(0.7F, 5F).clientTrackingRange(32).build("blood_wraith"));
    public static final RegistryObject<EntityType<NautilusEntity>> NAUTILUS = ENTITIES.register("nautilus", () -> EntityType.Builder.of(NautilusEntity::new, MobCategory.MISC).sized(5.6F, 5F).clientTrackingRange(32).build("nautilus"));
    public static final RegistryObject<EntityType<CrawlerEntity>> CRAWLER = ENTITIES.register("crawler", () -> EntityType.Builder.of(CrawlerEntity::new, MobCategory.CREATURE).sized(1.2F, 0.7F).clientTrackingRange(32).build("crawler"));
    public static final RegistryObject<EntityType<WeeperEntity>> WEEPER = ENTITIES.register("weeper", () -> EntityType.Builder.of(WeeperEntity::new, MobCategory.CREATURE).sized(1.8F, 0.7F).clientTrackingRange(32).build("weeper"));

    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties();
    public static final BlockBehaviour.Properties DAMP_WOOD_PROPERTIES = BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD);
    public static final BlockBehaviour.Properties DAMP_CANOPY_PROPERTIES = BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).noOcclusion();
    public static final BlockBehaviour.Properties DARK_SAND_PROPERTIES = BlockBehaviour.Properties.of().strength(3f).sound(SoundType.SAND);
    public static final BlockBehaviour.Properties BRICK_PROPERTIES = BlockBehaviour.Properties.of().strength(3.0F, 7.0F);
    public static final BlockBehaviour.Properties ELDER_BRICK_PROPERTIES = BlockBehaviour.Properties.of().strength(3.0F, 7.0F);

    public static final RegistryObject<Block> DAMP_WOOD = BLOCKS.register("damp_wood", () -> new Block(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> DARK_SAND = BLOCKS.register("dark_sand", () -> new Block(DARK_SAND_PROPERTIES));
    public static final RegistryObject<RotatedPillarBlock> DAMP_LOG = BLOCKS.register("damp_log", () -> new RotatedPillarBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<StairBlock> DAMP_WOOD_STAIRS = BLOCKS.register("damp_wood_stairs", () -> new StairBlock(() -> DAMP_WOOD.get().defaultBlockState(), DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> DAMP_CANOPY = BLOCKS.register("damp_canopy", () -> new DampCanopyBlock(DAMP_CANOPY_PROPERTIES));
    public static final RegistryObject<Block> DAMP_FILLED_CANOPY = BLOCKS.register("damp_filled_canopy", () -> new DampFilledCanopyBlock(DAMP_CANOPY_PROPERTIES));
    public static final RegistryObject<Block> WORN_BRICKS = BLOCKS.register("worn_bricks", () -> new Block(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> IDOL = BLOCKS.register("idol", () -> new IdolBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F))); // TODO NO DROPS
    public static final RegistryObject<Block> FISH_BARREL = BLOCKS.register("fish_barrel", () -> new FishBarrelBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> SLUG_BAIT = BLOCKS.register("slug_bait", () -> new SlugBaitBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> LAMP = BLOCKS.register("lamp", () -> new LampBlock(BlockBehaviour.Properties.of().strength(2f).lightLevel(state -> 12).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> BLUE_BRICKS = BLOCKS.register("blue_bricks", () -> new Block(BRICK_PROPERTIES));
    public static final RegistryObject<StairBlock> WORN_BRICK_STAIRS = BLOCKS.register("worn_brick_stairs", () -> new StairBlock(() -> WORN_BRICKS.get().defaultBlockState(), DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<FenceBlock> DAMP_WOOD_FENCE = BLOCKS.register("damp_wood_fence", () -> new FenceBlock(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> FUME_SPREADER = BLOCKS.register("fume_spreader", () -> new FumeSpreaderBlock(BlockBehaviour.Properties.of().strength(3f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> SLEEP_CHAMBER = BLOCKS.register("sleep_chamber", () -> new SleepChamberBlock(BlockBehaviour.Properties.of().strength(4f, 7f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GEAR_BENCH = BLOCKS.register("gear_bench", () -> new GearBenchBlock(BlockBehaviour.Properties.of().strength(4f, 7f).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> WATERY_CRADLE = BLOCKS.register("watery_cradle", () -> new WateryCradleBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<FlaskShelfBlock> FLASK_SHELF = BLOCKS.register("flask_shelf", () -> new FlaskShelfBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<SurgeryBedBlock> SURGERY_BED = BLOCKS.register("surgery_bed", () -> new SurgeryBedBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
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
    public static final RegistryObject<Block> SACRIFICE_ALTAR = BLOCKS.register("sacrifice_altar", () -> new SacrificeAltarBlock(BRICK_PROPERTIES)); // new BlockSacrificeAltarCore(Material.IRON, BlockNames.SACRIFICE_ALTAR);
    public static final RegistryObject<SlabBlock> BLOOD_BRICK_SLAB = BLOCKS.register("blood_brick_slab", () -> new SlabBlock(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB, Material.ROCK, false).setHardness(10).setResistance(3000);
    public static final RegistryObject<StairBlock> BLOOD_BRICK_STAIRS = BLOCKS.register("blood_brick_stairs", () -> new StairBlock(() -> BLOOD_BRICK.get().defaultBlockState(), BRICK_PROPERTIES)); // new ModStairs(BlockBloodBrick.getDefaultState(), BlockNames.BLOOD_BRICKS_STAIRS);
    public static final RegistryObject<Block> BLOOD_SMOOTH_STONE = BLOCKS.register("blood_smooth_stone", () -> new Block(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
    public static final RegistryObject<SlabBlock> BLOOD_SMOOTH_STONE_SLAB = BLOCKS.register("blood_smooth_stone_slab", () -> new SlabBlock(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
    public static final RegistryObject<Block> HEART = BLOCKS.register("heart", () -> new HeartBlock(BlockBehaviour.Properties.of().strength(0.5F))); // new BlockHeart(Material.SPONGE, BlockNames.HEART);
    //public static final RegistryObject<Block> BLOOD_WELL = BLOCKS.register("blood_well", () -> new Block(BRICK_PROPERTIES)); // new BlockBloodWell(Material.PORTAL, BlockNames.BLOOD_WELL);
    //public static final RegistryObject<Block> STATUE = BLOCKS.register("statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.STATUE, WorshipType.DEFAULT);
    //public static final RegistryObject<Block> SACRIFICE_STATUE = BLOCKS.register("sacrifice_statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.SACRIFICE_STATUE, WorshipType.SACRIFICE);
    //public static final RegistryObject<Block> PENITENCE_STATUE = BLOCKS.register("penitence_statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.PENITENCE_STATUE, WorshipType.PENITENCE);
    public static final RegistryObject<Block> MEMORY_SIEVE = BLOCKS.register("memory_sieve", () -> new MemorySieveBlock(BRICK_PROPERTIES)); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
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
    public static final RegistryObject<GrowingPlantHeadBlock> BLACK_KELP = BLOCKS.register("black_kelp", () -> new BlackKelpBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY))); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<GrowingPlantBodyBlock> BLACK_KELP_PLANT = BLOCKS.register("black_kelp_plant", () -> new BlackKelpPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY))); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S

    public static final RegistryObject<Block> FLASK_LARGE = BLOCKS.register("flask_large", () -> new FlaskBlock(BRICK_PROPERTIES, FlaskBlock.FlaskSize.LARGE)); // TODO GLASS PROPERTIES
    public static final RegistryObject<Block> FLASK_MEDIUM = BLOCKS.register("flask_medium", () -> new FlaskBlock(BRICK_PROPERTIES, FlaskBlock.FlaskSize.MEDIUM)); // TODO GLASS PROPERTIES
    public static final RegistryObject<Block> FLASK_SMALL = BLOCKS.register("flask_small", () -> new FlaskBlock(BRICK_PROPERTIES, FlaskBlock.FlaskSize.SMALL)); // TODO GLASS PROPERTIES

    public static final RegistryObject<LiquidBlock> SEDATIVE_BLOCK = BLOCKS.register("sedative_block", () -> new LiquidBlock(Registration.SOURCE_FLUID_SEDATIVE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> SOFTENER_BLOCK = BLOCKS.register("softener_block", () -> new LiquidBlock(Registration.SOURCE_FLUID_SOFTENER, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> COAGULANT_BLOCK = BLOCKS.register("coagulant_block", () -> new LiquidBlock(Registration.SOURCE_FLUID_COAGULANT, BlockBehaviour.Properties.copy(Blocks.WATER)));

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
    public static final RegistryObject<Item> WATERY_CRADLE_ITEM = fromBlock(WATERY_CRADLE);

    public static final RegistryObject<Item> FLASK_SHELF_ITEM = fromBlock(FLASK_SHELF);
    public static final RegistryObject<Item> SURGERY_BED_ITEM = fromBlock(SURGERY_BED);
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
    public static final RegistryObject<Item> SACRIFICE_ALTAR_ITEM = fromBlock(SACRIFICE_ALTAR);
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
    public static final RegistryObject<Item> MEMORY_SIEVE_ITEM = fromBlock(MEMORY_SIEVE);
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
    public static final RegistryObject<Item> FLASK_LARGE_ITEM = fromBlock(FLASK_LARGE);
    public static final RegistryObject<Item> FLASK_MEDIUM_ITEM = fromBlock(FLASK_MEDIUM);
    public static final RegistryObject<Item> FLASK_SMALL_ITEM = fromBlock(FLASK_SMALL);
    public static final RegistryObject<Item> BLACK_KELP_ITEM = fromBlock(BLACK_KELP);

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
    public static final RegistryObject<Item> BLACKJACK = ITEMS.register("blackjack", BlackjackItem::new); // new ItemBlackjack("blackjack");
    public static final RegistryObject<Item> SPINE = ITEMS.register("spine", () -> new Item(ITEM_PROPERTIES)); // new ModItem("spine");
    public static final RegistryObject<HeldVillagerItem> HELD_VILLAGER = ITEMS.register("held_villager", HeldVillagerItem::new); // new ItemHeldVillager("held_villager");
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
    public static final RegistryObject<Item> CORAL_STAFF = ITEMS.register("coral_staff", CoralStaffItem::new); // new ItemCoralStaff("coral_staff");
    public static final RegistryObject<Item> SIGIL_ZOMBIE = ITEMS.register("sigil_zombie", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilUndead("sigil_zombie", BloodMobs.BLOOD_ZOMBIE);
    public static final RegistryObject<Item> SIGIL_SKELLIE = ITEMS.register("sigil_skellie", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilUndead("sigil_skellie", BloodMobs.BLOOD_SKELLIE);
    public static final RegistryObject<Item> SIGIL_PLAYER = ITEMS.register("sigil_player", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilPlayer("sigil_player");
    public static final RegistryObject<Item> SIGIL_PATHWAY = ITEMS.register("sigil_pathway", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilPathway("sigil_pathway");
    public static final RegistryObject<Item> SACRIFICIAL_KNIFE = ITEMS.register("sacrificial_knife", () -> new Item(ITEM_PROPERTIES)); // new ItemSacrificialKnife("sacrificial_knife");
    public static final RegistryObject<Item> MEMORY_PHIAL = ITEMS.register("memory_phial", MemoryPhialItem::new); // new ItemMemoryPhial("memory_phial");
    public static final RegistryObject<Item> NECRONOMICON = ITEMS.register("necronomicon", NecronomiconItem::new); // new ItemNecronomicon("necronomicon");
    public static final RegistryObject<Item> GEAR = ITEMS.register("gear", () -> new Item(ITEM_PROPERTIES)); // new ModItem("gear");
    public static final RegistryObject<Item> SURGEON_SUMMONS = ITEMS.register("surgeon_summons", () -> new Item(ITEM_PROPERTIES)); // new ItemSurgeonSummoner("surgeon_summons");
    public static final RegistryObject<Item> CRUCIBLE = ITEMS.register("crucible", () -> new Item(ITEM_PROPERTIES)); // new ItemCrucible("crucible");
    public static final RegistryObject<Item> BLACK_MIRROR = ITEMS.register("black_mirror", () -> new Item(ITEM_PROPERTIES)); // new ItemBlackMirror("black_mirror");
    public static final RegistryObject<Item> FLESH_CARBON_TOKEN = ITEMS.register("flesh_carbon_token", () -> new Item(ITEM_PROPERTIES)); // new ModItem("fleshcarbontoken");
    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", SyringeItem::new);
    public static final RegistryObject<Item> SCALPEL = ITEMS.register("scalpel", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> FORCEPS = ITEMS.register("forceps", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> TONGS = ITEMS.register("tongs", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> SEWING_NEEDLE = ITEMS.register("sewing_needle", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> SEDATIVE_BUCKET = ITEMS.register("sedative_bucket", () -> new BucketItem(Registration.SOURCE_FLUID_SEDATIVE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> SOFTENER_BUCKET = ITEMS.register("softener_bucket", () -> new BucketItem(Registration.SOURCE_FLUID_SOFTENER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> COAGULANT_BUCKET = ITEMS.register("coagulant_bucket", () -> new BucketItem(Registration.SOURCE_FLUID_COAGULANT, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> DEEP_ONE_EGG = ITEMS.register("deep_one", () -> new ForgeSpawnEggItem(DEEP_ONE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BLOOD_SKELETON_EGG = ITEMS.register("blood_skeleton", () -> new ForgeSpawnEggItem(BLOOD_SKELETON, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BLOOD_ZOMBIE_EGG = ITEMS.register("blood_zombie", () -> new ForgeSpawnEggItem(BLOOD_ZOMBIE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BLOOD_WRAITH_EGG = ITEMS.register("blood_wraith", () -> new ForgeSpawnEggItem(BLOOD_WRAITH, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));


    public static final RegistryObject<BlockEntityType<GearBenchBE>> GEAR_BENCH_BE = BLOCK_ENTITIES.register(GEAR_BENCH.getId().getPath(), () -> BlockEntityType.Builder.of(GearBenchBE::new, GEAR_BENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<SlugBaitBE>> SLUG_BAIT_BE = BLOCK_ENTITIES.register(SLUG_BAIT.getId().getPath(), () -> BlockEntityType.Builder.of(SlugBaitBE::new, SLUG_BAIT.get()).build(null));
    public static final RegistryObject<BlockEntityType<HeartBE>> HEART_BE = BLOCK_ENTITIES.register(HEART.getId().getPath(), () -> BlockEntityType.Builder.of(HeartBE::new, HEART.get()).build(null));
    public static final RegistryObject<BlockEntityType<FumeSpreaderBE>> FUME_SPREADER_BE = BLOCK_ENTITIES.register(FUME_SPREADER.getId().getPath(), () -> BlockEntityType.Builder.of(FumeSpreaderBE::new, FUME_SPREADER.get()).build(null));
    public static final RegistryObject<BlockEntityType<FlaskShelfBE>> FLASK_SHELF_BE = BLOCK_ENTITIES.register(FLASK_SHELF.getId().getPath(), () -> BlockEntityType.Builder.of(FlaskShelfBE::new, FLASK_SHELF.get()).build(null));
    public static final RegistryObject<BlockEntityType<SurgeryBedBE>> SURGERY_BED_BE = BLOCK_ENTITIES.register(SURGERY_BED.getId().getPath(), () -> BlockEntityType.Builder.of(SurgeryBedBE::new, SURGERY_BED.get()).build(null));
    public static final RegistryObject<BlockEntityType<FlaskBE>> FLASK_BE = BLOCK_ENTITIES.register(new ResourceLocation(References.MODID, "flask").getPath(), () -> BlockEntityType.Builder.of(FlaskBE::new, FLASK_LARGE.get(), FLASK_MEDIUM.get(), FLASK_SMALL.get()).build(null));
    public static final RegistryObject<BlockEntityType<MemorySieveBE>> MEMORY_SIEVE_BE = BLOCK_ENTITIES.register(MEMORY_SIEVE.getId().getPath(), () -> BlockEntityType.Builder.of(MemorySieveBE::new, MEMORY_SIEVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<WateryCradleBE>> WATERY_CRADLE_BE = BLOCK_ENTITIES.register(WATERY_CRADLE.getId().getPath(), () -> BlockEntityType.Builder.of(WateryCradleBE::new, WATERY_CRADLE.get()).build(null));

    public static final RegistryObject<MenuType<GearBenchContainer>> GEAR_BENCH_CONTAINER = CONTAINERS.register(GEAR_BENCH.getId().getPath(), () -> IForgeMenuType.create((windowId, inv, data) -> new GearBenchContainer(windowId, data.readBlockPos(), inv, inv.player)));

    public static final RegistryObject<FluidType> SEDATIVE_FLUID_TYPE = FLUID_TYPES.register("sedative_fluid", () -> new SurgicalFluidType(FluidType.Properties.create().lightLevel(2).density(15).viscosity(5), new ResourceLocation(References.MODID, "block/fluids/sedative_still"), new ResourceLocation(References.MODID, "block/fluids/sedative_flow"), new ResourceLocation(References.MODID, "block/fluids/sedative_overlay"), 0xFFC13719));
    public static final RegistryObject<FluidType> SOFTENER_FLUID_TYPE = FLUID_TYPES.register("softener_fluid", () -> new SurgicalFluidType(FluidType.Properties.create().lightLevel(2).density(15).viscosity(5), new ResourceLocation(References.MODID, "block/fluids/softener_still"), new ResourceLocation(References.MODID, "block/fluids/softener_flow"), new ResourceLocation(References.MODID, "block/fluids/softener_overlay"), 0xFFF4C42F));
    public static final RegistryObject<FluidType> COAGULANT_FLUID_TYPE = FLUID_TYPES.register("coagulant_fluid", () -> new SurgicalFluidType(FluidType.Properties.create().lightLevel(2).density(15).viscosity(5), new ResourceLocation(References.MODID, "block/fluids/coagulant_still"), new ResourceLocation(References.MODID, "block/fluids/coagulant_flow"), new ResourceLocation(References.MODID, "block/fluids/coagulant_overlay"), 0xFFBA10CD));

    public static final RegistryObject<FlowingFluid> SOURCE_FLUID_SEDATIVE = FLUIDS.register("sedative_fluid_source", () -> new ForgeFlowingFluid.Source(Registration.SEDATIVE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_FLUID_SEDATIVE = FLUIDS.register("sedative_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(Registration.SEDATIVE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_FLUID_SOFTENER = FLUIDS.register("softener_fluid_source", () -> new ForgeFlowingFluid.Source(Registration.SOFTENER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_FLUID_SOFTENER = FLUIDS.register("softener_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(Registration.SOFTENER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_FLUID_COAGULANT = FLUIDS.register("coagulant_fluid_source", () -> new ForgeFlowingFluid.Source(Registration.COAGULANT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_FLUID_COAGULANT = FLUIDS.register("coagulant_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(Registration.COAGULANT_PROPERTIES));

    public static final ForgeFlowingFluid.Properties SEDATIVE_PROPERTIES = new ForgeFlowingFluid.Properties(Registration.SEDATIVE_FLUID_TYPE, SOURCE_FLUID_SEDATIVE, FLOWING_FLUID_SEDATIVE).slopeFindDistance(2).levelDecreasePerBlock(2).block(Registration.SEDATIVE_BLOCK).bucket(Registration.SEDATIVE_BUCKET);
    public static final ForgeFlowingFluid.Properties SOFTENER_PROPERTIES = new ForgeFlowingFluid.Properties(Registration.SOFTENER_FLUID_TYPE, SOURCE_FLUID_SOFTENER, FLOWING_FLUID_SOFTENER).slopeFindDistance(2).levelDecreasePerBlock(2).block(Registration.SOFTENER_BLOCK).bucket(Registration.SOFTENER_BUCKET);
    public static final ForgeFlowingFluid.Properties COAGULANT_PROPERTIES = new ForgeFlowingFluid.Properties(Registration.COAGULANT_FLUID_TYPE, SOURCE_FLUID_COAGULANT, FLOWING_FLUID_COAGULANT).slopeFindDistance(2).levelDecreasePerBlock(2).block(Registration.COAGULANT_BLOCK).bucket(Registration.COAGULANT_BUCKET);


    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TAB.register("items", () -> CreativeModeTab.builder().icon(() -> new ItemStack(BLACK_MIRROR.get())).title(Component.translatable("creative_tab.beyondtheveil"))
            .displayItems((features, output) -> {

                output.accept(MEMORY_SIEVE.get());
                output.accept(FLASK_LARGE.get());
                output.accept(FLASK_MEDIUM.get());
                output.accept(FLASK_SMALL.get());

                output.accept(DAMP_WOOD.get());
                output.accept(DARK_SAND.get());
                output.accept(DAMP_LOG.get());
                output.accept(DAMP_WOOD_STAIRS.get());
                output.accept(DAMP_CANOPY.get());
                output.accept(DAMP_FILLED_CANOPY.get());
                output.accept(WORN_BRICKS.get());
                output.accept(IDOL.get());
                output.accept(FISH_BARREL.get());
                output.accept(SLUG_BAIT.get());
                output.accept(LAMP.get());
                output.accept(BLUE_BRICKS.get());
                output.accept(WORN_BRICK_STAIRS.get());
                output.accept(DAMP_WOOD_FENCE.get());
                output.accept(FUME_SPREADER.get());
                output.accept(SLEEP_CHAMBER.get());
                output.accept(GEAR_BENCH.get());
                output.accept(WATERY_CRADLE.get());
                output.accept(FLASK_SHELF.get());
                output.accept(SURGERY_BED.get());
                output.accept(ELDER_STONE_BRICK.get());
                output.accept(ELDER_STONE_BRICK_CHISELED.get());
                output.accept(ELDER_STONE_BRICK_SLAB.get());
                output.accept(ELDER_STONE_BRICK_STAIRS.get());
                output.accept(ELDER_BRICK.get());
                output.accept(ELDER_BRICK_SLAB.get());
                output.accept(ELDER_BRICK_STAIRS.get());
                output.accept(ELDER_SMOOTH_STONE.get());
                output.accept(ELDER_SMOOTH_STONE_SLAB.get());
                output.accept(BLOOD_BRICK.get());
                output.accept(SACRIFICE_ALTAR.get());
                output.accept(BLOOD_BRICK_SLAB.get());
                output.accept(BLOOD_BRICK_STAIRS.get());
                output.accept(BLOOD_SMOOTH_STONE.get());
                output.accept(BLOOD_SMOOTH_STONE_SLAB.get());
                output.accept(HEART.get());
                output.accept(BLACK_KELP.get());

                output.accept(ONIRIC_INCENSE.get());
                output.accept(SLUG_CATCHER.get());
                output.accept(SLUG.get());
                output.accept(CANOE.get());
                output.accept(RUM.get());
                output.accept(WINE.get());
                output.accept(ALE.get());
                output.accept(VODKA.get());
                output.accept(MEAD.get());
                output.accept(CUP.get());
                output.accept(FLUTE.get());

                output.accept(WOLF_MEDALLION.get());
                output.accept(TABLET.get());
                output.accept(BRONZE_SPHERE.get());
                output.accept(REDSTONE_WEED_SEEDS.get());
                output.accept(GHOST_WEED_SEEDS.get());
                output.accept(VANILLA_WEED_SEEDS.get());
                output.accept(BLACKJACK.get());
                output.accept(SPINE.get());
                output.accept(HELD_VILLAGER.get());
                output.accept(HELD_WEEPER.get());
                output.accept(HELD_FLETUM.get());
                output.accept(HELD_SHOGGOTH.get());
                output.accept(SURGERY_TOOLS.get());
                output.accept(BONE_TIARA.get());
                output.accept(BLEEDING_BELT.get());
                output.accept(DREAM_BOTTLE.get());
                output.accept(SHOGGOTH_MAP.get());
                output.accept(BLOOD_COVENANT.get());
                output.accept(REVELATION_RING.get());
                output.accept(AZACNO_CHARM.get());
                output.accept(BLOOD_CROWN.get());
                output.accept(CORAL_STAFF.get());
                output.accept(SIGIL_ZOMBIE.get());
                output.accept(SIGIL_SKELLIE.get());
                output.accept(SIGIL_PLAYER.get());
                output.accept(SIGIL_PATHWAY.get());
                output.accept(SACRIFICIAL_KNIFE.get());
                output.accept(MEMORY_PHIAL.get());
                output.accept(NECRONOMICON.get());
                output.accept(GEAR.get());
                output.accept(SURGEON_SUMMONS.get());
                output.accept(CRUCIBLE.get());
                output.accept(BLACK_MIRROR.get());
                output.accept(FLESH_CARBON_TOKEN.get());
                output.accept(SYRINGE.get());
                output.accept(SCALPEL.get());
                output.accept(FORCEPS.get());
                output.accept(TONGS.get());
                output.accept(SEWING_NEEDLE.get());

                output.accept(SEDATIVE_BUCKET.get());
                output.accept(SOFTENER_BUCKET.get());
                output.accept(COAGULANT_BUCKET.get());

                output.accept(DEEP_ONE_EGG.get());
                output.accept(BLOOD_SKELETON_EGG.get());
                output.accept(BLOOD_ZOMBIE_EGG.get());
                output.accept(BLOOD_WRAITH_EGG.get());

    }).build());

    private static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> blockObject) {
        return ITEMS.register(blockObject.getId().getPath(), () -> new BlockItem(blockObject.get(), ITEM_PROPERTIES));
    }


}
