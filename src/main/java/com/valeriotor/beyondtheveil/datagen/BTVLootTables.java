package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BTVLootTables extends BlockLootSubProvider {

    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Registration.IDOL.get()).map(ItemLike::asItem).collect(Collectors.toSet());

    protected BTVLootTables() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(Registration.DAMP_STONE.get());
        dropSelf(Registration.DAMP_WOOD.get());
        dropSelf(Registration.DARK_SAND.get());
        dropSelf(Registration.ALGAE_BLOCK.get());
        dropSelf(Registration.DAMP_LOG.get());
        dropSelf(Registration.DAMP_WOOD_STAIRS.get());
        dropSelf(Registration.DAMP_CANOPY.get());
        dropSelf(Registration.DAMP_FILLED_CANOPY.get());
        dropSelf(Registration.WORN_BRICKS.get());
        dropSelf(Registration.SLUG_BAIT.get());
        dropSelf(Registration.LAMP.get());
        dropSelf(Registration.BLUE_BRICKS.get());
        dropSelf(Registration.WORN_BRICK_STAIRS.get());
        dropSelf(Registration.DAMP_WOOD_FENCE.get());
        dropSelf(Registration.FUME_SPREADER.get());
        dropSelf(Registration.SLEEP_CHAMBER.get());
        dropSelf(Registration.GEAR_BENCH.get());
        dropSelf(Registration.WATERY_CRADLE.get());
        dropSelf(Registration.PATIENT_POD.get());
        dropSelf(Registration.FLASK_SHELF.get());
        dropSelf(Registration.FLEBO.get());
        dropSelf(Registration.SURGERY_BED.get());
        dropSelf(Registration.ALEMBICS.get());
        dropSelf(Registration.LACRYMATORY.get());
        dropSelf(Registration.ELDER_STONE_BRICK.get());
        dropSelf(Registration.ELDER_STONE_BRICK_CHISELED.get());
        dropSelf(Registration.ELDER_STONE_BRICK_SLAB.get());
        dropSelf(Registration.ELDER_STONE_BRICK_STAIRS.get());
        dropSelf(Registration.ELDER_BRICK.get());
        dropSelf(Registration.ELDER_BRICK_SLAB.get());
        dropSelf(Registration.ELDER_BRICK_STAIRS.get());
        dropSelf(Registration.ELDER_SMOOTH_STONE.get());
        dropSelf(Registration.ELDER_SMOOTH_STONE_SLAB.get());
        dropSelf(Registration.BLOOD_BRICK.get());
        dropSelf(Registration.VEIN_STONE.get());
        dropSelf(Registration.SACRIFICE_ALTAR.get());
        dropSelf(Registration.BLOOD_BASIN.get());
        dropSelf(Registration.BLOOD_BRICK_SLAB.get());
        dropSelf(Registration.BLOOD_BRICK_STAIRS.get());
        dropSelf(Registration.BLOOD_SMOOTH_STONE.get());
        dropSelf(Registration.BLOOD_SMOOTH_STONE_SLAB.get());
        dropSelf(Registration.HEART.get());
        add(Registration.BLOOD_WELL.get(), noDrop());
        dropSelf(Registration.MEMORY_SIEVE.get());
        dropSelf(Registration.LETTER_BOX.get());
        dropSelf(Registration.DARK_GLASS.get());
        dropSelf(Registration.ARENA.get());
        dropSelf(Registration.DEEP_CHEST.get());
        dropSelf(Registration.BLACK_KELP.get());
        dropOther(Registration.BLACK_KELP_PLANT.get(), Registration.BLACK_KELP.get());
        add(Registration.BLACK_SEAGRASS.get(), BlockLootSubProvider::createShearsOnlyDrop);
        add(Registration.BLACK_TALL_SEAGRASS.get(), createDoublePlantShearsDrop(Registration.BLACK_SEAGRASS.get()));

        dropSelf(Registration.IDOL.get());
        dropSelf(Registration.FISH_BARREL.get());
        dropSelf(Registration.FLASK_LARGE.get());
        dropSelf(Registration.FLASK_MEDIUM.get());
        dropSelf(Registration.FLASK_SMALL.get());
        dropSelf(Registration.FLASK_ITEM.get());
        pillarTable(Registration.DEMAND_PILLAR.get(), BTVBlockEntities.DEMAND_PILLAR_BE.get(), "link");
        pillarTable(Registration.OFFER_PILLAR.get(), BTVBlockEntities.OFFER_PILLAR_BE.get(), "link");
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(References.MODID))
                .map(Map.Entry::getValue)
                .filter(b -> !(b instanceof LiquidBlock))
                .collect(Collectors.toList());
    }

    private void pillarTable(Block block, BlockEntityType<?> type, String... tags) {
        LootPoolSingletonContainer.Builder<?> lti = LootItem.lootTableItem(block);
        lti.apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY));
        for (String tag : tags) {
            lti.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy(tag, "BlockEntityTag." + tag, CopyNbtFunction.MergeStrategy.REPLACE));
        }
        CompoundTag pTag = new CompoundTag();
        pTag.putBoolean("fromItem", true);
        CompoundTag blockEntityTag = new CompoundTag();
        blockEntityTag.put("BlockEntityTag", pTag);
        lti.apply(SetNbtFunction.setTag(blockEntityTag));
        lti.apply(SetContainerContents.setContents(type).withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents"))));

        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(lti);
        add(block, LootTable.lootTable().withPool(builder));
    }


}
