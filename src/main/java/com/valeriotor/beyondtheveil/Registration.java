package com.valeriotor.beyondtheveil;

import com.valeriotor.beyondtheveil.block.*;
import com.valeriotor.beyondtheveil.container.GearBenchContainer;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.GearBenchBE;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
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

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(References.ITEM_GROUP);
    public static final BlockBehaviour.Properties DAMP_WOOD_PROPERTIES = BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD);
    public static final BlockBehaviour.Properties DAMP_CANOPY_PROPERTIES = BlockBehaviour.Properties.of(Material.WOOD).strength(2f).sound(SoundType.WOOD).noOcclusion();
    public static final BlockBehaviour.Properties DARK_SAND_PROPERTIES = BlockBehaviour.Properties.of(Material.SAND).strength(3f).sound(SoundType.SAND);
    public static final BlockBehaviour.Properties BRICK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 7.0F);

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

    public static final RegistryObject<Item> ONIRIC_INCENSE = ITEMS.register("oniric_incense", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> SLUG_CATCHER = ITEMS.register("slug_catcher", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> SLUG = ITEMS.register("slug", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> CANOE = ITEMS.register("canoe", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> RUM = ITEMS.register("drink_rum", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> WINE = ITEMS.register("drink_wine", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> ALE = ITEMS.register("drink_ale", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> VODKA = ITEMS.register("drink_vodka", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> MEAD = ITEMS.register("drink_mead", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> CUP = ITEMS.register("drink_empty", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> FLUTE = ITEMS.register("flute", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<BlockEntityType<GearBenchBE>> GEAR_BENCH_BE = BLOCK_ENTITIES.register(GEAR_BENCH.getId().getPath(), () -> BlockEntityType.Builder.of(GearBenchBE::new, GEAR_BENCH.get()).build(null));


    public static final RegistryObject<MenuType<GearBenchContainer>> GEAR_BENCH_CONTAINER = CONTAINERS.register(GEAR_BENCH.getId().getPath(), () -> IForgeMenuType.create((windowId, inv, data) -> new GearBenchContainer(windowId, data.readBlockPos(), inv, inv.player)));


    private static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> blockObject) {
        return ITEMS.register(blockObject.getId().getPath(), () -> new BlockItem(blockObject.get(), ITEM_PROPERTIES));
    }


}
