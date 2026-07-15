package com.valeriotor.beyondtheveil;

import com.valeriotor.beyondtheveil.block.*;
import com.valeriotor.beyondtheveil.block.multiblock.ThinMultiBlock1by2;
import com.valeriotor.beyondtheveil.container.*;
import com.valeriotor.beyondtheveil.container.dialogue.DoubleDialogueMenu;
import com.valeriotor.beyondtheveil.container.dialogue.DrownedDialogueMenu;
import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.container.dialogue.MirrorDialogueMenu;
import com.valeriotor.beyondtheveil.item.*;
import com.valeriotor.beyondtheveil.lib.*;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import com.valeriotor.beyondtheveil.world.feature.arche.BlackKelpFeature;
import com.valeriotor.beyondtheveil.world.feature.arche.BlackSeagrassFeature;
import com.valeriotor.beyondtheveil.world.processor.HamletBuildingsProcessor;
import com.valeriotor.beyondtheveil.world.structures.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, References.MODID);
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, References.MODID);
    private static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, References.MODID);
    private static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, References.MODID);
    private static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, References.MODID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, References.MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, References.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.MODID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        MENUS.register(bus);
        FEATURES.register(bus);
        STRUCTURE_TYPES.register(bus);
        STRUCTURE_PIECE_TYPES.register(bus);
        STRUCTURE_PROCESSORS.register(bus);
        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        CREATIVE_TAB.register(bus);

        BTVFluids.init(bus);
        BTVParticles.init(bus);
        BTVEffects.init(bus);
        BTVSounds.init(bus);
        BTVEntities.init(bus);
        BTVBlockEntities.init(bus);
    }

    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties();
    public static final BlockBehaviour.Properties DAMP_WOOD_PROPERTIES = BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops();
    public static final BlockBehaviour.Properties DAMP_CANOPY_PROPERTIES = BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).noOcclusion().requiresCorrectToolForDrops();
    public static final BlockBehaviour.Properties DARK_SAND_PROPERTIES = BlockBehaviour.Properties.of().strength(3f).sound(SoundType.SAND);
    public static final BlockBehaviour.Properties BRICK_PROPERTIES = BlockBehaviour.Properties.of().strength(3.0F, 7.0F).requiresCorrectToolForDrops();
    public static final BlockBehaviour.Properties ELDER_BRICK_PROPERTIES = BlockBehaviour.Properties.of().strength(3.0F, 7.0F).requiresCorrectToolForDrops();

    public static final RegistryObject<Block> DAMP_STONE = BLOCKS.register("damp_stone", () -> new Block(BlockBehaviour.Properties.of().strength(5f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DAMP_MOSSY_STONE = BLOCKS.register("damp_mossy_stone", () -> new Block(BlockBehaviour.Properties.of().strength(5f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DAMP_WOOD = BLOCKS.register("damp_wood", () -> new Block(DAMP_WOOD_PROPERTIES));
    public static final RegistryObject<Block> DARK_SAND = BLOCKS.register("dark_sand", () -> new Block(DARK_SAND_PROPERTIES));
    public static final RegistryObject<Block> ALGAE_BLOCK = BLOCKS.register("algae_block", () -> new Block(BlockBehaviour.Properties.of().strength(3f).sound(SoundType.MOSS)));
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

    public static final RegistryObject<WateryCradleBlock> WATERY_CRADLE = BLOCKS.register("watery_cradle", () -> new WateryCradleBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<PatientPodBlock> PATIENT_POD = BLOCKS.register("patient_pod", () -> new PatientPodBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<FlaskShelfBlock> FLASK_SHELF = BLOCKS.register("flask_shelf", () -> new FlaskShelfBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<FleboBlock> FLEBO = BLOCKS.register("flebo", () -> new FleboBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<SurgeryBedBlock> SURGERY_BED = BLOCKS.register("surgery_bed", () -> new SurgeryBedBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<AlembicsBlock> ALEMBICS = BLOCKS.register("alembics", () -> new AlembicsBlock(BRICK_PROPERTIES)); // new BlockWateryCradle(BlockNames.WATERYCRADLE);
    public static final RegistryObject<Block> LACRYMATORY = BLOCKS.register("lacrymatory", () -> new LacrymatoryBlock(BRICK_PROPERTIES)); // new BlockLacrymatory(BlockNames.LACRYMATORY);
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
    public static final RegistryObject<Block> VEIN_STONE = BLOCKS.register("vein_stone", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 7.0F).sound(SoundType.MUD))); // new ModBlock(Material.ROCK, BlockNames.BLOODBRICKS);
    public static final RegistryObject<Block> VEIN_STONE_VESSEL = BLOCKS.register("vein_stone_vessel", () -> new Block(BlockBehaviour.Properties.of().strength(3.0F, 7.0F).sound(SoundType.MUD))); // new ModBlock(Material.ROCK, BlockNames.BLOODBRICKS);
    public static final RegistryObject<SacrificeAltarBlock> SACRIFICE_ALTAR = BLOCKS.register("sacrifice_altar", () -> new SacrificeAltarBlock(BRICK_PROPERTIES)); // new BlockSacrificeAltarCore(Material.IRON, BlockNames.SACRIFICE_ALTAR);
    public static final RegistryObject<BloodBasinBlock> BLOOD_BASIN = BLOCKS.register("blood_basin", () -> new BloodBasinBlock(BRICK_PROPERTIES)); // new BlockSacrificeAltarCore(Material.IRON, BlockNames.SACRIFICE_ALTAR);
    public static final RegistryObject<SlabBlock> BLOOD_BRICK_SLAB = BLOCKS.register("blood_brick_slab", () -> new SlabBlock(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB, Material.ROCK, false).setHardness(10).setResistance(3000);
    public static final RegistryObject<StairBlock> BLOOD_BRICK_STAIRS = BLOCKS.register("blood_brick_stairs", () -> new StairBlock(() -> BLOOD_BRICK.get().defaultBlockState(), BRICK_PROPERTIES)); // new ModStairs(BlockBloodBrick.getDefaultState(), BlockNames.BLOOD_BRICKS_STAIRS);
    public static final RegistryObject<Block> BLOOD_SMOOTH_STONE = BLOCKS.register("blood_smooth_stone", () -> new Block(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
    public static final RegistryObject<SlabBlock> BLOOD_SMOOTH_STONE_SLAB = BLOCKS.register("blood_smooth_stone_slab", () -> new SlabBlock(BRICK_PROPERTIES)); // (ModSlab) new ModSlab(BlockNames.BLOOD_BRICKS_SLAB_DOUBLE, Material.ROCK, true).setHardness(10).setResistance(3000);
    public static final RegistryObject<Block> HEART = BLOCKS.register("heart", () -> new HeartBlock(BlockBehaviour.Properties.of().strength(0.5F))); // new BlockHeart(Material.SPONGE, BlockNames.HEART);
    public static final RegistryObject<Block> BLOOD_WELL = BLOCKS.register("blood_well", () -> new BloodWellBlock(BRICK_PROPERTIES)); // new BlockBloodWell(Material.PORTAL, BlockNames.BLOOD_WELL);
    //public static final RegistryObject<Block> STATUE = BLOCKS.register("statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.STATUE, WorshipType.DEFAULT);
    //public static final RegistryObject<Block> SACRIFICE_STATUE = BLOCKS.register("sacrifice_statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.SACRIFICE_STATUE, WorshipType.SACRIFICE);
    //public static final RegistryObject<Block> PENITENCE_STATUE = BLOCKS.register("penitence_statue", () -> new Block(BRICK_PROPERTIES)); // new BlockStatue(Material.ROCK, BlockNames.PENITENCE_STATUE, WorshipType.PENITENCE);
    public static final RegistryObject<Block> MEMORY_SIEVE = BLOCKS.register("memory_sieve", () -> new MemorySieveBlock(BRICK_PROPERTIES)); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<ThinMultiBlock1by2> LETTER_BOX = BLOCKS.register("letter_box", () -> new LetterBoxBlock(BRICK_PROPERTIES)); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<PillarBlock> DEMAND_PILLAR = BLOCKS.register("demand_pillar", () -> new PillarBlock(BRICK_PROPERTIES, false)); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<PillarBlock> OFFER_PILLAR = BLOCKS.register("offer_pillar", () -> new PillarBlock(BRICK_PROPERTIES, true)); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<Block> DREAM_FOCUS = BLOCKS.register("dream_focus", () -> new DreamFocusBlock.DreamFocusItemBlock(BRICK_PROPERTIES)); // new BlockDreamFocus(BlockNames.DREAMFOCUS);
    public static final RegistryObject<Block> DREAM_FOCUS_FLUIDS = BLOCKS.register("dream_focus_fluids", () -> new DreamFocusBlock.DreamFocusFluidBlock(BRICK_PROPERTIES)); // new BlockDreamFocus(BlockNames.DREAMFOCUS);
    //public static final RegistryObject<Block> DREAM_FOCUS_FLUIDS = BLOCKS.register("dream_focus_fluids", () -> new Block(BRICK_PROPERTIES)); // new BlockDreamFocusFluids(BlockNames.DREAMFOCUSFLUIDS);
    //public static final RegistryObject<Block> DREAM_FOCUS_VILLAGERS = BLOCKS.register("dream_focus_villagers", () -> new Block(BRICK_PROPERTIES)); // new BlockDreamFocusVillagers(BlockNames.DREAMFOCUSVILLAGERS);
    public static final RegistryObject<Block> CURTAIN = BLOCKS.register("curtain", () -> new CurtainBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().pushReaction(PushReaction.DESTROY))); // new BlockCurtain(BlockNames.CURTAIN);
    //public static final RegistryObject<Block> MEGYDREA = BLOCKS.register("megydrea", () -> new Block(BRICK_PROPERTIES)); // new BlockMegydrea(BlockNames.MEGYDREA);
    //public static final RegistryObject<Block> THICK_ALGAE = BLOCKS.register("thick_algae", () -> new Block(BRICK_PROPERTIES)); // new BlockThickAlgae(BlockNames.THICK_ALGAE);
    //public static final RegistryObject<Block> ARCHE_PORTAL = BLOCKS.register("arche_portal", () -> new Block(BRICK_PROPERTIES)); // new BlockArchePortal(BlockNames.ARCHE_PORTAL);
    public static final RegistryObject<Block> DARK_GLASS = BLOCKS.register("dark_glass", () -> new Block(BRICK_PROPERTIES)); // new BlockDarkGlass(BlockNames.DARK_GLASS);
    //public static final RegistryObject<Block> DEEP_PRISMARINE = BLOCKS.register("deep_prismarine", () -> new Block(BRICK_PROPERTIES)); // new BlockDeepPrismarine(BlockNames.DEEP_PRISMARINE);
    public static final RegistryObject<Block> ARENA = BLOCKS.register("arena", () -> new Block(BRICK_PROPERTIES)); // new BlockArena(BlockNames.ARENA);
    public static final RegistryObject<Block> DEEP_CHEST = BLOCKS.register("deep_chest", () -> new DeepChestBlock(BlockBehaviour.Properties.of().strength(3.0F, 7.0F).sound(SoundType.ANCIENT_DEBRIS))); // new BlockDeepChest(BlockNames.DEEP_CHEST);
    public static final RegistryObject<GrowingPlantHeadBlock> BLACK_KELP = BLOCKS.register("black_kelp", () -> new BlackKelpBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY))); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<GrowingPlantBodyBlock> BLACK_KELP_PLANT = BLOCKS.register("black_kelp_plant", () -> new BlackKelpPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).noCollission().randomTicks().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY))); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<BlackSeagrassBlock> BLACK_SEAGRASS = BLOCKS.register("black_seagrass", () -> new BlackSeagrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY))); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S
    public static final RegistryObject<BlackTallSeagrassBlock> BLACK_TALL_SEAGRASS = BLOCKS.register("black_tall_seagrass", () -> new BlackTallSeagrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollission().instabreak().sound(SoundType.WET_GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY))); // new BlockMemorySieve(Material.ROCK, BlockNames.MEMORY_S

    public static final RegistryObject<Block> FLASK_LARGE = BLOCKS.register("flask_large", () -> new FlaskBlock(BRICK_PROPERTIES, FlaskBlock.FlaskSize.LARGE)); // TODO GLASS PROPERTIES
    public static final RegistryObject<Block> FLASK_MEDIUM = BLOCKS.register("flask_medium", () -> new FlaskBlock(BRICK_PROPERTIES, FlaskBlock.FlaskSize.MEDIUM)); // TODO GLASS PROPERTIES
    public static final RegistryObject<Block> FLASK_SMALL = BLOCKS.register("flask_small", () -> new FlaskBlock(BRICK_PROPERTIES, FlaskBlock.FlaskSize.SMALL)); // TODO GLASS PROPERTIES
    public static final RegistryObject<Block> FLASK_ITEM = BLOCKS.register("flask_item", () -> new FlaskBlock(BRICK_PROPERTIES, FlaskBlock.FlaskSize.ITEM)); // TODO GLASS PROPERTIES

    public static final RegistryObject<LiquidBlock> SEDATIVE_BLOCK = BLOCKS.register("sedative_block", () -> new LiquidBlock(BTVFluids.SOURCE_FLUID_SEDATIVE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> SOFTENER_BLOCK = BLOCKS.register("softener_block", () -> new LiquidBlock(BTVFluids.SOURCE_FLUID_SOFTENER, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> COAGULANT_BLOCK = BLOCKS.register("coagulant_block", () -> new LiquidBlock(BTVFluids.SOURCE_FLUID_COAGULANT, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> TEARS_BLOCK = BLOCKS.register("tears_block", () -> new LiquidBlock(BTVFluids.FLUID_TEARS.getA(), BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryObject<Item> DAMP_STONE_ITEM = fromBlock(DAMP_STONE);
    public static final RegistryObject<Item> DAMP_MOSSY_STONE_ITEM = fromBlock(DAMP_MOSSY_STONE);
    public static final RegistryObject<Item> DAMP_WOOD_ITEM = fromBlock(DAMP_WOOD);
    public static final RegistryObject<Item> DARK_SAND_ITEM = fromBlock(DARK_SAND);
    public static final RegistryObject<Item> ALGAE_BLOCK_ITEM = fromBlock(ALGAE_BLOCK);
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
    public static final RegistryObject<Item> PATIENT_POD_ITEM = fromBlock(PATIENT_POD);

    public static final RegistryObject<Item> FLASK_SHELF_ITEM = fromBlock(FLASK_SHELF);
    public static final RegistryObject<Item> FLEBO_ITEM = fromBlock(FLEBO);
    public static final RegistryObject<Item> SURGERY_BED_ITEM = fromBlock(SURGERY_BED);
    public static final RegistryObject<Item> ALEMBICS_ITEM = fromBlock(ALEMBICS);
    public static final RegistryObject<Item> LACRYMATORY_ITEM = fromBlock(LACRYMATORY);
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
    public static final RegistryObject<Item> BLOOD_BASIN_ITEM = fromBlock(BLOOD_BASIN);
    public static final RegistryObject<Item> BLOOD_BRICK_ITEM = fromBlock(BLOOD_BRICK);
    public static final RegistryObject<Item> VEIN_STONE_ITEM = fromBlock(VEIN_STONE);
    public static final RegistryObject<Item> VEIN_STONE_VESSEL_ITEM = fromBlock(VEIN_STONE_VESSEL);
    public static final RegistryObject<Item> BLOOD_BRICK_SLAB_ITEM = fromBlock(BLOOD_BRICK_SLAB);
    public static final RegistryObject<Item> BLOOD_BRICK_STAIRS_ITEM = fromBlock(BLOOD_BRICK_STAIRS);
    public static final RegistryObject<Item> BLOOD_SMOOTH_STONE_ITEM = fromBlock(BLOOD_SMOOTH_STONE);
    public static final RegistryObject<Item> BLOOD_SMOOTH_STONE_SLAB_ITEM = fromBlock(BLOOD_SMOOTH_STONE_SLAB);
    public static final RegistryObject<Item> HEART_ITEM = fromBlock(HEART);
    public static final RegistryObject<Item> BLOOD_WELL_ITEM = fromBlock(BLOOD_WELL);

    //public static final RegistryObject<Item> BLOOD_WELL_ITEM = fromBlock(BLOOD_WELL);
    //public static final RegistryObject<Item> STATUE_ITEM = fromBlock(STATUE);
    //public static final RegistryObject<Item> SACRIFICE_STATUE_ITEM = fromBlock(SACRIFICE_STATUE);
    //public static final RegistryObject<Item> PENITENCE_STATUE_ITEM = fromBlock(PENITENCE_STATUE);
    public static final RegistryObject<Item> MEMORY_SIEVE_ITEM = fromBlock(MEMORY_SIEVE);
    public static final RegistryObject<Item> LETTER_BOX_ITEM = fromBlock(LETTER_BOX);
    public static final RegistryObject<Item> DEMAND_PILLAR_ITEM = fromBlock(DEMAND_PILLAR, new Item.Properties().stacksTo(1));
    public static final RegistryObject<Item> OFFER_PILLAR_ITEM = fromBlock(OFFER_PILLAR, new Item.Properties().stacksTo(1));
    public static final RegistryObject<Item> DREAM_FOCUS_ITEM = fromBlock(DREAM_FOCUS);
    public static final RegistryObject<Item> CURTAIN_ITEM = fromBlock(CURTAIN);
    public static final RegistryObject<Item> DREAM_FOCUS_FLUIDS_ITEM = fromBlock(DREAM_FOCUS_FLUIDS);
    //public static final RegistryObject<Item> DREAM_FOCUS_ITEM = fromBlock(DREAM_FOCUS);
    //public static final RegistryObject<Item> DREAM_FOCUS_FLUIDS_ITEM = fromBlock(DREAM_FOCUS_FLUIDS);
    //public static final RegistryObject<Item> DREAM_FOCUS_VILLAGERS_ITEM = fromBlock(DREAM_FOCUS_VILLAGERS);
    //public static final RegistryObject<Item> CURTAIN_ITEM = fromBlock(CURTAIN);
    //public static final RegistryObject<Item> MEGYDREA_ITEM = fromBlock(MEGYDREA);
    //public static final RegistryObject<Item> THICK_ALGAE_ITEM = fromBlock(THICK_ALGAE);
    //public static final RegistryObject<Item> ARCHE_PORTAL_ITEM = fromBlock(ARCHE_PORTAL);
    public static final RegistryObject<Item> DARK_GLASS_ITEM = fromBlock(DARK_GLASS);
    //public static final RegistryObject<Item> DEEP_PRISMARINE_ITEM = fromBlock(DEEP_PRISMARINE);
    public static final RegistryObject<Item> ARENA_ITEM = fromBlock(ARENA);
    public static final RegistryObject<Item> DEEP_CHEST_ITEM = fromBlock(DEEP_CHEST);
    public static final RegistryObject<Item> FLASK_LARGE_ITEM = fromBlock(FLASK_LARGE);
    public static final RegistryObject<Item> FLASK_MEDIUM_ITEM = fromBlock(FLASK_MEDIUM);
    public static final RegistryObject<Item> FLASK_SMALL_ITEM = fromBlock(FLASK_SMALL);
    public static final RegistryObject<Item> FLASK_ITEM_ITEM = fromBlock(FLASK_ITEM);
    public static final RegistryObject<Item> BLACK_KELP_ITEM = fromBlock(BLACK_KELP);
    public static final RegistryObject<Item> BLACK_SEAGRASS_ITEM = fromBlock(BLACK_SEAGRASS);

    public static final RegistryObject<Item> ONIRIC_INCENSE = ITEMS.register("oniric_incense", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> SLUG_CATCHER = ITEMS.register("slug_catcher", SlugCatcherItem::new);
    public static final RegistryObject<Item> SLUG = ITEMS.register("slug", () -> new SlugItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).build())));
    public static final RegistryObject<Item> SEAWEED_STEW = ITEMS.register("seaweed_stew", () -> new DelicacyItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(3).saturationMod(1.4F).build())));
    public static final RegistryObject<Item> KELP_COD_BUNDLE = ITEMS.register("kelp_cod_bundle", () -> new DelicacyItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(5).saturationMod(1.5F).build())));
    public static final RegistryObject<Item> TROPICAL_DELIGHT = ITEMS.register("tropical_delight", () -> new DelicacyItem(new Item.Properties().food((new FoodProperties.Builder()).nutrition(10).saturationMod(1F).build())));
    public static final RegistryObject<Item> CANOE = ITEMS.register("canoe", () -> new CanoeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RUM = ITEMS.register("drink_rum", DrinkItem::new);
    public static final RegistryObject<Item> WINE = ITEMS.register("drink_wine", DrinkItem::new);
    public static final RegistryObject<Item> ALE = ITEMS.register("drink_ale", DrinkItem::new);
    public static final RegistryObject<Item> VODKA = ITEMS.register("drink_vodka", DrinkItem::new);
    public static final RegistryObject<Item> MEAD = ITEMS.register("drink_mead", DrinkItem::new);
    public static final RegistryObject<Item> CUP = ITEMS.register("drink_empty", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> FLUTE = ITEMS.register("flute", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> WOLF_MEDALLION = ITEMS.register("wolf_medallion", () -> new Item(ITEM_PROPERTIES)); // new ItemWolfMedallion("wolf_medallion");
    public static final RegistryObject<Item> TABLET = ITEMS.register("tablet", () -> new Item(ITEM_PROPERTIES)); // new ItemTablet("tablet");
    public static final RegistryObject<Item> BRONZE_SPHERE = ITEMS.register("bronze_sphere", () -> new Item(ITEM_PROPERTIES)); // new ItemBronzeSphere("bronze_sphere");
    public static final RegistryObject<Item> REDSTONE_WEED_SEEDS = ITEMS.register("redstone_weed_seeds", () -> new Item(ITEM_PROPERTIES)); // new ItemSpecialGrassSeeds(BlockRegistry.PlantRedstoneWeed, BlockRegistry.BlockRedstoneGrass, BlockNames.REDSTONEWEED);
    public static final RegistryObject<Item> GHOST_WEED_SEEDS = ITEMS.register("ghost_weed_seeds", () -> new Item(ITEM_PROPERTIES)); // new ItemSpecialGrassSeeds(BlockRegistry.PlantGhostWeed, BlockRegistry.BlockGhostGrass, BlockNames.GHOSTWEED);
    public static final RegistryObject<Item> VANILLA_WEED_SEEDS = ITEMS.register("vanilla_weed_seeds", () -> new Item(ITEM_PROPERTIES)); // new ItemSpecialGrassSeeds(BlockRegistry.PlantVanillaWeed, Blocks.GRASS, BlockNames.VANILLAWEED);
    public static final RegistryObject<Item> BLACKJACK = ITEMS.register("blackjack", BlackjackItem::new); // new ItemBlackjack("blackjack");
    public static final RegistryObject<Item> SPINE = ITEMS.register("spine", () -> new Item(ITEM_PROPERTIES)); // new ModItem("spine");
    public static final RegistryObject<BloodShardItem> BLOOD_SHARD = ITEMS.register("blood_shard", BloodShardItem::new); // new ItemHeldVillager("held_villager");
    public static final RegistryObject<HeldVillagerItem> HELD_VILLAGER = ITEMS.register("held_villager", HeldVillagerItem::new); // new ItemHeldVillager("held_villager");
    public static final RegistryObject<Item> HELD_WEEPER = ITEMS.register("held_weeper", () -> new Item(ITEM_PROPERTIES)); // new ItemHeldWeeper("held_weeper");
    public static final RegistryObject<Item> HELD_FLETUM = ITEMS.register("held_fletum", FletumItem::new); // new ItemHeldFletum("held_fletum");
    public static final RegistryObject<Item> HELD_SHOGGOTH = ITEMS.register("held_shoggoth", () -> new Item(ITEM_PROPERTIES)); // new ItemHeldShoggoth("held_shoggoth");
    public static final RegistryObject<Item> SURGERY_TOOLS = ITEMS.register("surgery_tools", () -> new Item(ITEM_PROPERTIES)); // new ModItem("surgery_tools");
    public static final RegistryObject<BoneTiaraItem> BONE_TIARA = ITEMS.register("bone_tiara", () -> new BoneTiaraItem(ITEM_PROPERTIES)); // new ItemBoneTiara("bone_tiara");
    public static final RegistryObject<Item> BLEEDING_BELT = ITEMS.register("bleeding_belt", BleedingBeltItem::new); // new ItemBleedingBelt("bleeding_belt");
    public static final RegistryObject<Item> DREAM_BOTTLE = ITEMS.register("dream_bottle", DreamBottleItem::new); // new ItemDreamBottle("dream_bottle");
    public static final RegistryObject<Item> BLOOD_GEM = ITEMS.register("blood_gem", BloodGemItem::new); // new ItemDreamBottle("dream_bottle");
    public static final RegistryObject<Item> BLOOD_THESIS = ITEMS.register("blood_thesis", BloodThesisItem::new); // new ItemDreamBottle("dream_bottle");
    public static final RegistryObject<Item> SHOGGOTH_MAP = ITEMS.register("shoggoth_map", () -> new Item(ITEM_PROPERTIES)); // new ModItem("shoggoth_map").setMaxStackSize(1);
    public static final RegistryObject<Item> BLOOD_COVENANT = ITEMS.register("blood_covenant", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodCovenant("blood_covenant").setMaxStackSize(1);
    public static final RegistryObject<Item> REVELATION_RING = ITEMS.register("revelation_ring", () -> new Item(ITEM_PROPERTIES)); // new ItemRevelationRing("revelation_ring").setMaxStackSize(1);
    public static final RegistryObject<Item> AZACNO_CHARM = ITEMS.register("azacno_charm", () -> new Item(ITEM_PROPERTIES)); // new ItemAzacnoCharm("azacno_charm").setMaxStackSize(1);
    public static final RegistryObject<Item> BLOOD_CROWN = ITEMS.register("blood_crown", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodCrown("blood_crown").setMaxStackSize(1);
    public static final RegistryObject<Item> CORAL_STAFF = ITEMS.register("coral_staff", CoralStaffItem::new); // new ItemCoralStaff("coral_staff");
    public static final RegistryObject<Item> SIGIL_ZOMBIE = ITEMS.register("sigil_zombie", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilUndead("sigil_zombie", BloodMobs.BLOOD_ZOMBIE);
    public static final RegistryObject<Item> SIGIL_SKELLIE = ITEMS.register("sigil_skellie", () -> new Item(ITEM_PROPERTIES)); // new ItemBloodSigilUndead("sigil_skellie", BloodMobs.BLOOD_SKELLIE);
    public static final RegistryObject<Item> SIGIL_PLAYER = ITEMS.register("sigil_player", BloodSigilPlayer::new); // new ItemBloodSigilPlayer("sigil_player");
    public static final RegistryObject<Item> SIGIL_PATHWAY = ITEMS.register("sigil_pathway", BloodSigilPathway::new); // new ItemBloodSigilPathway("sigil_pathway");
    public static final RegistryObject<Item> SACRIFICIAL_KNIFE = ITEMS.register("sacrificial_knife", SacrificialKnifeItem::new); // new ItemSacrificialKnife("sacrificial_knife");
    public static final RegistryObject<Item> MEMORY_PHIAL = ITEMS.register("memory_phial", MemoryPhialItem::new); // new ItemMemoryPhial("memory_phial");
    public static final RegistryObject<Item> SAMPLE_TUBE = ITEMS.register("sample_tube", SampleTubeItem::new); // new ItemMemoryPhial("memory_phial");
    public static final RegistryObject<Item> NECRONOMICON = ITEMS.register("necronomicon", NecronomiconItem::new); // new ItemNecronomicon("necronomicon");
    public static final RegistryObject<Item> JOURNAL = ITEMS.register("journal", JournalItem::new); // new ItemNecronomicon("necronomicon");
    public static final RegistryObject<Item> BLOOD_ORB = ITEMS.register("blood_orb", BloodOrbItem::new); // new ItemNecronomicon("necronomicon");
    public static final RegistryObject<Item> GREAT_HEART = ITEMS.register("great_heart", GreatHeartItem::new); // new BlockHeart(Material.SPONGE, BlockNames.HEART);
    public static final RegistryObject<Item> GEAR = ITEMS.register("gear", () -> new Item(ITEM_PROPERTIES)); // new ModItem("gear");
    public static final RegistryObject<Item> SURGEON_SUMMONS = ITEMS.register("surgeon_summons", () -> new Item(ITEM_PROPERTIES)); // new ItemSurgeonSummoner("surgeon_summons");
    public static final RegistryObject<Item> CRUCIBLE = ITEMS.register("crucible", () -> new Item(ITEM_PROPERTIES)); // new ItemCrucible("crucible");
    public static final RegistryObject<Item> BLACK_MIRROR = ITEMS.register("black_mirror", BlackMirrorItem::new); // new ItemBlackMirror("black_mirror");
    public static final RegistryObject<Item> FLESH_CARBON_TOKEN = ITEMS.register("flesh_carbon_token", () -> new Item(ITEM_PROPERTIES)); // new ModItem("fleshcarbontoken");
    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", SyringeItem::new);
    public static final RegistryObject<Item> SCALPEL = ITEMS.register("scalpel", () -> new ScalpelItem(SurgeryItem.SurgeryItemType.SCALPEL));
    public static final RegistryObject<Item> FORCEPS = ITEMS.register("forceps", ForcepsItem::new);
    public static final RegistryObject<Item> TONGS = ITEMS.register("tongs", () -> new SurgeryItem(SurgeryItem.SurgeryItemType.TONGS));
    public static final RegistryObject<Item> SEWING_NEEDLE = ITEMS.register("sewing_needle", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> ANTIDOTE_CAPSULE = ITEMS.register("antidote_capsule", () -> new AntidoteCapsuleItem(ITEM_PROPERTIES));
    public static final RegistryObject<Item> VEIN_MINER = ITEMS.register("vein_miner", () -> new VeinMinerItem(1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> REPAIR_HAMMER = ITEMS.register("repair_hammer", () -> new RepairHammerItem(1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> NAUTILUS = ITEMS.register("nautilus", () -> new NautilusItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCHE_DIAL = ITEMS.register("arche_dial", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VESSEL_STONE = ITEMS.register("vessel_stone", () -> new VesselStoneItem(new Item.Properties().stacksTo(1).durability(3)));
    public static final RegistryObject<Item> SURGERY_REPORT = ITEMS.register("surgery_report", ReportSheetItem::new);
    public static final RegistryObject<Item> SURGEON_BELL = ITEMS.register("surgeon_bell", SurgeonBellItem::new);
    public static final RegistryObject<Item> SURGEON_LARVA = ITEMS.register("surgeon_larva", SurgeonLarvaItem::new);
    public static final RegistryObject<Item> BLOOD_FIST = ITEMS.register("blood_fist", BloodFistItem::new);
    public static final RegistryObject<Item> PLUCKED_EYE = ITEMS.register("plucked_eye", SurgeryIngredient::new);
    public static final RegistryObject<Item> SHELL = ITEMS.register("shell", SurgeryIngredient::new);
    public static final RegistryObject<Item> TINY_SKULL = ITEMS.register("tiny_skull", SurgeryIngredient::new);
    public static final RegistryObject<Item> ACID_GLAND = ITEMS.register("acid_gland", SurgeryIngredient::new);
    public static final RegistryObject<Item> FERTILIZER_GLAND = ITEMS.register("fertilizer_gland", SurgeryIngredient::new);
    public static final RegistryObject<Item> MARROW_GLAND = ITEMS.register("marrow_gland", SurgeryIngredient::new);
    public static final RegistryObject<Item> SILK_GLAND = ITEMS.register("silk_gland", SurgeryIngredient::new);
    public static final RegistryObject<Item> GUNPOWDER_BLADDER = ITEMS.register("gunpowder_bladder", SurgeryIngredient::new);
    public static final RegistryObject<Item> LIVING_IRON = ITEMS.register("living_iron", SurgeryIngredient::new);
    public static final RegistryObject<Item> EMPTY_BLADDER = ITEMS.register("empty_bladder", SurgeryIngredient::new);
    public static final RegistryObject<Item> EMERALD_GEM = ITEMS.register("emerald_gem", SurgeryIngredient::new);

    public static final RegistryObject<Item> SEDATIVE_BUCKET = ITEMS.register("sedative_bucket", () -> new BucketItem(BTVFluids.SOURCE_FLUID_SEDATIVE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> SOFTENER_BUCKET = ITEMS.register("softener_bucket", () -> new BucketItem(BTVFluids.SOURCE_FLUID_SOFTENER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> COAGULANT_BUCKET = ITEMS.register("coagulant_bucket", () -> new BucketItem(BTVFluids.SOURCE_FLUID_COAGULANT, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MOVEMENT_SPEED_SERUM_BUCKET = ITEMS.register("movement_speed_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_MOVEMENT_SPEED_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MOVEMENT_SLOWDOWN_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("movement_slowdown_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_MOVEMENT_SLOWDOWN_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> DIG_SPEED_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("dig_speed_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_DIG_SPEED_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> DIG_SLOWDOWN_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("dig_slowdown_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_DIG_SLOWDOWN_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> DAMAGE_BOOST_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("damage_boost_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_DAMAGE_BOOST_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> HEAL_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("heal_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_HEAL_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> HARM_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("harm_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_HARM_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> JUMP_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("jump_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_JUMP_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> CONFUSION_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("confusion_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_CONFUSION_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> REGENERATION_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("regeneration_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_REGENERATION_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> DAMAGE_RESISTANCE_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("damage_resistance_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_DAMAGE_RESISTANCE_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> FIRE_RESISTANCE_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("fire_resistance_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_FIRE_RESISTANCE_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> WATER_BREATHING_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("water_breathing_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_WATER_BREATHING_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> INVISIBILITY_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("invisibility_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_INVISIBILITY_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> BLINDNESS_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("blindness_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_BLINDNESS_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> NIGHT_VISION_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("night_vision_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_NIGHT_VISION_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> HUNGER_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("hunger_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_HUNGER_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> WEAKNESS_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("weakness_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_WEAKNESS_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> POISON_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("poison_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_POISON_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> WITHER_SERUM_FLUID_SERUM_BUCKET = ITEMS.register("wither_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_WITHER_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MEMORY_HORMONES_FLUID_BUCKET = ITEMS.register("memory_hormones_bucket", () -> new BucketItem(BTVFluids.FLUID_MEMORY_HORMONES.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> OBEDIENCE_HORMONES_FLUID_BUCKET = ITEMS.register("obedience_hormones_bucket", () -> new BucketItem(BTVFluids.FLUID_OBEDIENCE_HORMONES.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> PARENTAL_HORMONES_FLUID_BUCKET = ITEMS.register("parental_hormones_bucket", () -> new BucketItem(BTVFluids.FLUID_PARENTAL_HORMONES.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> GROWTH_STIMULANT_FLUID_BUCKET = ITEMS.register("growth_stimulant_bucket", () -> new BucketItem(BTVFluids.FLUID_GROWTH_STIMULANT.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> TEARS_FLUID_BUCKET = ITEMS.register("tears_bucket", () -> new BucketItem(BTVFluids.FLUID_TEARS.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_POWDER_FLUID_BUCKET = ITEMS.register("diamond_powder_bucket", () -> new BucketItem(BTVFluids.FLUID_DIAMOND_POWDER.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> GS121_SERUM_FLUID_BUCKET = ITEMS.register("gs121_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_GS121_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> LIQUID_GLOWSTONE_FLUID_BUCKET = ITEMS.register("liquid_glowstone_bucket", () -> new BucketItem(BTVFluids.FLUID_LIQUID_GLOWSTONE.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> LIQUID_GOLD_FLUID_BUCKET = ITEMS.register("liquid_gold_bucket", () -> new BucketItem(BTVFluids.FLUID_LIQUID_GOLD.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> ORGANOCHLORIDE_FLUID_BUCKET = ITEMS.register("organochloride_bucket", () -> new BucketItem(BTVFluids.FLUID_ORGANOCHLORIDE.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> PHEROMONES_FLUID_BUCKET = ITEMS.register("pheromones_bucket", () -> new BucketItem(BTVFluids.FLUID_PHEROMONES.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> SA245_SERUM_FLUID_BUCKET = ITEMS.register("sa245_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_SA245_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> VASOCONSTRICTOR_FLUID_BUCKET = ITEMS.register("vasoconstrictor_bucket", () -> new BucketItem(BTVFluids.FLUID_VASOCONSTRICTOR.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> WART_SERUM_FLUID_BUCKET = ITEMS.register("wart_serum_bucket", () -> new BucketItem(BTVFluids.FLUID_WART_SERUM.getA(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> DEEP_ONE_EGG = ITEMS.register("deep_one", () -> new ForgeSpawnEggItem(BTVEntities.DEEP_ONE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BLOOD_SKELETON_EGG = ITEMS.register("blood_skeleton", () -> new ForgeSpawnEggItem(BTVEntities.BLOOD_SKELETON, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BLOOD_ZOMBIE_EGG = ITEMS.register("blood_zombie", () -> new ForgeSpawnEggItem(BTVEntities.BLOOD_ZOMBIE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BLOOD_WRAITH_EGG = ITEMS.register("blood_wraith", () -> new ForgeSpawnEggItem(BTVEntities.BLOOD_WRAITH, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BLOOD_CULTIST_EGG = ITEMS.register("blood_cultist", () -> new ForgeSpawnEggItem(BTVEntities.BLOOD_CULTIST, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> SURGEON_EGG = ITEMS.register("surgeon", () -> new ForgeSpawnEggItem(BTVEntities.SURGEON, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> WEEPER_EGG = ITEMS.register("weeper", () -> new ForgeSpawnEggItem(BTVEntities.WEEPER, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> FLETUM_EGG = ITEMS.register("fletum", () -> new ForgeSpawnEggItem(BTVEntities.FLETUM, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> SHOREMAN_EGG = ITEMS.register("shoreman", () -> new ForgeSpawnEggItem(BTVEntities.SHOREMAN, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> CEPHALOPODIAN_EGG = ITEMS.register("cephalopodian", () -> new ForgeSpawnEggItem(BTVEntities.CEPHALOPODIAN, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> ANGLER_EGG = ITEMS.register("angler", () -> new ForgeSpawnEggItem(BTVEntities.ANGLER, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> SEA_SNAKE_EGG = ITEMS.register("sea_snake", () -> new ForgeSpawnEggItem(BTVEntities.SEA_SNAKE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));

    public static final RegistryObject<Item> SEPIID_EGG = ITEMS.register("sepiid", () -> new ForgeSpawnEggItem(BTVEntities.SEPIID, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> ADELINE_EGG = ITEMS.register("adeline", () -> new ForgeSpawnEggItem(BTVEntities.ADELINE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> BONECAGE_EGG = ITEMS.register("bonecage", () -> new ForgeSpawnEggItem(BTVEntities.BONECAGE, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> MAN_O_WAR_EGG = ITEMS.register("man_o_war", () -> new ForgeSpawnEggItem(BTVEntities.MAN_O_WAR, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> OCTID_EGG = ITEMS.register("octid", () -> new ForgeSpawnEggItem(BTVEntities.OCTID, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> UMANCALA_EGG = ITEMS.register("umancala", () -> new ForgeSpawnEggItem(BTVEntities.UMANCALA, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));
    public static final RegistryObject<Item> SANDFLATTER_EGG = ITEMS.register("sandflatter", () -> new ForgeSpawnEggItem(BTVEntities.SANDFLATTER, 0xF52A37, 0x589BCD, ITEM_PROPERTIES));

    public static final RegistryObject<MenuType<GearBenchContainer>> GEAR_BENCH_CONTAINER = MENUS.register(GEAR_BENCH.getId().getPath(), () -> IForgeMenuType.create((windowId, inv, data) -> new GearBenchContainer(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<BloodGemContainer>> BLOOD_GEM_CONTAINER = MENUS.register(BLOOD_GEM.getId().getPath(), () -> IForgeMenuType.create((windowId, inv, data) -> new BloodGemContainer(windowId, inv, inv.player)));
    public static final RegistryObject<MenuType<LetterBoxContainer>> LETTER_BOX_CONTAINER = MENUS.register(LETTER_BOX.getId().getPath(), () -> IForgeMenuType.create((windowId, inv, data) -> new LetterBoxContainer(windowId, data.readBlockPos(), inv.player)));
    public static final RegistryObject<MenuType<DreamBottleContainer>> DREAM_BOTTLE_CONTAINER = MENUS.register(DREAM_BOTTLE.getId().getPath(), () -> IForgeMenuType.create((windowId, inv, data) -> new DreamBottleContainer(windowId, inv, inv.player)));
    public static final RegistryObject<MenuType<DrownedContainer>> DROWNED_CONTAINER = MENUS.register("drowned", () -> IForgeMenuType.create((windowId, inv, data) -> new DrownedContainer(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<EntityDialogueMenu>> SHOREMAN_DIALOGUE_MENU = MENUS.register("shoreman_dialogue", () -> IForgeMenuType.create((windowId, inv, data) -> new EntityDialogueMenu(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<DoubleDialogueMenu>> DOUBLE_DIALOGUE_MENU = MENUS.register("double_dialogue", () -> IForgeMenuType.create((windowId, inv, data) -> new DoubleDialogueMenu(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<MirrorDialogueMenu>> MIRROR_DIALOGUE_MENU = MENUS.register("mirror_dialogue", () -> IForgeMenuType.create((windowId, inv, data) -> new MirrorDialogueMenu(windowId, inv, inv.player, data)));
    public static final RegistryObject<MenuType<DrownedDialogueMenu>> DROWNED_DIALOGUE_MENU = MENUS.register("drowned_dialogue_menu", () -> IForgeMenuType.create((windowId, inv, data) -> new DrownedDialogueMenu(windowId, inv, inv.player, data)));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BLACK_KELP_FEATURE = FEATURES.register("black_kelp", () -> new BlackKelpFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> BLACK_SEAGRASS_FEATURE = FEATURES.register("black_seagrass", () -> new BlackSeagrassFeature(ProbabilityFeatureConfiguration.CODEC));

    public static final RegistryObject<StructureType<DeepCityStructure>> DEEP_CITY = STRUCTURE_TYPES.register("deep_city", () -> () -> DeepCityStructure.CODEC);
    public static final RegistryObject<StructureType<HamletStructure>> HAMLET = STRUCTURE_TYPES.register("hamlet", () -> () -> HamletStructure.CODEC);
    public static final RegistryObject<StructureType<DeepVeinStructure>> DEEP_VEIN = STRUCTURE_TYPES.register("deep_vein", () -> () -> DeepVeinStructure.CODEC);
    public static final RegistryObject<StructureType<HangingAlgaeStructure>> HANGING_ALGAE = STRUCTURE_TYPES.register("hanging_algae", () -> () -> HangingAlgaeStructure.CODEC);
    public static final RegistryObject<StructureType<AlgaeShroomStructure>> ALGAE_SHROOM = STRUCTURE_TYPES.register("algae_shroom", () -> () -> AlgaeShroomStructure.CODEC);
    public static final RegistryObject<StructureType<StalagmiteStructure>> STALAGMITE = STRUCTURE_TYPES.register("stalagmite", () -> () -> StalagmiteStructure.CODEC);
    public static final RegistryObject<StructureType<BloodCoralStructure>> BLOOD_CORAL = STRUCTURE_TYPES.register("blood_coral", () -> () -> BloodCoralStructure.CODEC);
    public static final RegistryObject<StructurePieceType> DEEP_CITY_PIECE = STRUCTURE_PIECE_TYPES.register("deep_city_piece", () -> DeepCityPiece::new);
    public static final RegistryObject<StructurePieceType> HAMLET_BUILDING_PIECE = STRUCTURE_PIECE_TYPES.register("hamlet_building_piece", () -> HamletPieces.HamletBuildingPiece::new);
    public static final RegistryObject<StructurePieceType> HAMLET_STREET_PIECE = STRUCTURE_PIECE_TYPES.register("hamlet_street_piece", () -> HamletPieces.StreetPiece::new);
    public static final RegistryObject<StructurePieceType> DEEP_VEIN_PIECE = STRUCTURE_PIECE_TYPES.register("deep_vein_piece", () -> DeepVeinStructure.DeepVeinPiece::new);
    public static final RegistryObject<StructurePieceType> HANGING_ALGAE_PIECE = STRUCTURE_PIECE_TYPES.register("hanging_algae_piece", () -> HangingAlgaeStructure.HangingAlgaePiece::new);
    public static final RegistryObject<StructurePieceType> ALGAE_SHROOM_PIECE = STRUCTURE_PIECE_TYPES.register("algae_shroom_piece", () -> AlgaeShroomStructure.AlgaeShroomPiece::new);
    public static final RegistryObject<StructurePieceType> STALAGMITE_PIECE = STRUCTURE_PIECE_TYPES.register("stalagmite_piece", () -> StalagmiteStructure.StalagmitePiece::new);
    public static final RegistryObject<StructurePieceType> BLOOD_CORAL_PIECE = STRUCTURE_PIECE_TYPES.register("blood_coral_piece", () -> BloodCoralStructure.BloodCoralPiece::new); // TODO
    public static final RegistryObject<StructureProcessorType<HamletBuildingsProcessor>> HAMLET_BUILDINGS_PROCESSOR = STRUCTURE_PROCESSORS.register("hamlet_buildings_processor", () -> () -> HamletBuildingsProcessor.CODEC);

    public static final RegistryObject<RecipeType<GearBenchRecipe>> GEAR_BENCH_RECIPE_TYPE = RECIPE_TYPES.register("gear_bench_recipe_type", () -> new RecipeType<>() {
        public String toString() {
            return "gear_bench";
        }
    });

    public static final RegistryObject<RecipeSerializer<?>> GEAR_BENCH_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("gear_bench", GearBenchRecipe.Serializer::new);

    public static final TagKey<Item> LARVA_FOOD = TagKey.create(Registries.ITEM, new ResourceLocation(References.MODID, "larva_food"));

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TAB.register("items", () -> CreativeModeTab.builder().icon(() -> new ItemStack(BLACK_MIRROR.get())).title(Component.translatable("creative_tab.beyondtheveil"))
            .displayItems((features, output) -> {

                output.accept(MEMORY_SIEVE.get());
                output.accept(LETTER_BOX.get());
                output.accept(DEMAND_PILLAR.get());
                output.accept(OFFER_PILLAR.get());
                output.accept(DREAM_FOCUS.get());
                output.accept(DREAM_FOCUS_FLUIDS.get());
                output.accept(CURTAIN.get());
                output.accept(FLASK_LARGE.get());
                output.accept(FLASK_MEDIUM.get());
                output.accept(FLASK_SMALL.get());
                output.accept(FLASK_ITEM.get());

                output.accept(DAMP_STONE.get());
                output.accept(DAMP_MOSSY_STONE.get());
                output.accept(DAMP_WOOD.get());
                output.accept(DARK_SAND.get());
                output.accept(ALGAE_BLOCK.get());
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
                output.accept(PATIENT_POD.get());
                output.accept(FLASK_SHELF.get());
                output.accept(FLEBO.get());
                output.accept(SURGERY_BED.get());
                output.accept(ALEMBICS.get());
                output.accept(LACRYMATORY.get());
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
                output.accept(VEIN_STONE.get());
                output.accept(VEIN_STONE_VESSEL.get());
                output.accept(SACRIFICE_ALTAR.get());
                output.accept(BLOOD_BASIN.get());
                output.accept(BLOOD_BRICK_SLAB.get());
                output.accept(BLOOD_BRICK_STAIRS.get());
                output.accept(BLOOD_SMOOTH_STONE.get());
                output.accept(BLOOD_SMOOTH_STONE_SLAB.get());
                output.accept(HEART.get());
                output.accept(BLACK_KELP.get());
                output.accept(BLACK_SEAGRASS.get());
                output.accept(DARK_GLASS.get());
                output.accept(ARENA.get());
                output.accept(DEEP_CHEST.get());

                output.accept(ONIRIC_INCENSE.get());
                output.accept(SLUG_CATCHER.get());
                output.accept(SLUG.get());
                output.accept(SEAWEED_STEW.get());
                output.accept(KELP_COD_BUNDLE.get());
                output.accept(TROPICAL_DELIGHT.get());
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
                output.accept(BLOOD_SHARD.get());
                output.accept(HELD_VILLAGER.get());
                output.accept(HELD_WEEPER.get());
                output.accept(HELD_FLETUM.get());
                output.accept(HELD_SHOGGOTH.get());
                output.accept(SURGERY_TOOLS.get());
                output.accept(BONE_TIARA.get());
                output.accept(BLEEDING_BELT.get());
                output.accept(DREAM_BOTTLE.get());
                output.accept(BLOOD_GEM.get());
                output.accept(BLOOD_THESIS.get());
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
                output.accept(SAMPLE_TUBE.get());
                output.accept(NECRONOMICON.get());
                output.accept(JOURNAL.get());
                output.accept(BLOOD_ORB.get());
                output.accept(GREAT_HEART.get());
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
                output.accept(ANTIDOTE_CAPSULE.get());
                output.accept(VEIN_MINER.get());
                output.accept(REPAIR_HAMMER.get());
                output.accept(NAUTILUS.get());
                output.accept(ARCHE_DIAL.get());
                output.accept(VESSEL_STONE.get());
                output.accept(SURGERY_REPORT.get());
                output.accept(SURGEON_BELL.get());
                output.accept(SURGEON_LARVA.get());
                output.accept(BLOOD_FIST.get());
                output.accept(PLUCKED_EYE.get());
                output.accept(SHELL.get());
                output.accept(TINY_SKULL.get());
                output.accept(ACID_GLAND.get());
                output.accept(FERTILIZER_GLAND.get());
                output.accept(MARROW_GLAND.get());
                output.accept(SILK_GLAND.get());
                output.accept(GUNPOWDER_BLADDER.get());
                output.accept(LIVING_IRON.get());
                output.accept(EMPTY_BLADDER.get());
                output.accept(EMERALD_GEM.get());


                output.accept(SEDATIVE_BUCKET.get());
                output.accept(SOFTENER_BUCKET.get());
                output.accept(COAGULANT_BUCKET.get());
                output.accept(MOVEMENT_SPEED_SERUM_BUCKET.get());
                output.accept(MOVEMENT_SLOWDOWN_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(DIG_SPEED_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(DIG_SLOWDOWN_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(DAMAGE_BOOST_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(HEAL_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(HARM_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(JUMP_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(CONFUSION_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(REGENERATION_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(DAMAGE_RESISTANCE_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(FIRE_RESISTANCE_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(WATER_BREATHING_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(INVISIBILITY_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(BLINDNESS_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(NIGHT_VISION_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(HUNGER_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(WEAKNESS_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(POISON_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(WITHER_SERUM_FLUID_SERUM_BUCKET.get());
                output.accept(MEMORY_HORMONES_FLUID_BUCKET.get());
                output.accept(OBEDIENCE_HORMONES_FLUID_BUCKET.get());
                output.accept(PARENTAL_HORMONES_FLUID_BUCKET.get());
                output.accept(GROWTH_STIMULANT_FLUID_BUCKET.get());
                output.accept(TEARS_FLUID_BUCKET.get());
                output.accept(DIAMOND_POWDER_FLUID_BUCKET.get());
                output.accept(GS121_SERUM_FLUID_BUCKET.get());
                output.accept(LIQUID_GLOWSTONE_FLUID_BUCKET.get());
                output.accept(LIQUID_GOLD_FLUID_BUCKET.get());
                output.accept(ORGANOCHLORIDE_FLUID_BUCKET.get());
                output.accept(PHEROMONES_FLUID_BUCKET.get());
                output.accept(SA245_SERUM_FLUID_BUCKET.get());
                output.accept(VASOCONSTRICTOR_FLUID_BUCKET.get());
                output.accept(WART_SERUM_FLUID_BUCKET.get());

                output.accept(DEEP_ONE_EGG.get());
                output.accept(BLOOD_SKELETON_EGG.get());
                output.accept(BLOOD_ZOMBIE_EGG.get());
                output.accept(BLOOD_WRAITH_EGG.get());
                output.accept(BLOOD_CULTIST_EGG.get());
                output.accept(SURGEON_EGG.get());
                output.accept(WEEPER_EGG.get());
                output.accept(FLETUM_EGG.get());
                output.accept(SHOREMAN_EGG.get());
                output.accept(CEPHALOPODIAN_EGG.get());
                output.accept(ANGLER_EGG.get());
                output.accept(SEA_SNAKE_EGG.get());
                output.accept(SEPIID_EGG.get());
                output.accept(ADELINE_EGG.get());
                output.accept(BONECAGE_EGG.get());
                output.accept(MAN_O_WAR_EGG.get());
                output.accept(OCTID_EGG.get());
                output.accept(UMANCALA_EGG.get());
                output.accept(SANDFLATTER_EGG.get());

            }).build());

    private static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> blockObject) {
        return ITEMS.register(blockObject.getId().getPath(), () -> new BlockItem(blockObject.get(), ITEM_PROPERTIES));
    }

    private static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> blockObject, Item.Properties properties) {
        return ITEMS.register(blockObject.getId().getPath(), () -> new BlockItem(blockObject.get(), properties));
    }


}
