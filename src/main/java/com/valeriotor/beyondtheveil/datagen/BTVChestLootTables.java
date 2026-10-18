package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class BTVChestLootTables implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {
        pOutput.accept(new ResourceLocation(References.MODID, "deep_city_altar"), deepCityAltar());
        pOutput.accept(new ResourceLocation(References.MODID, "deep_city_beacon"), deepCityBeacon());
        pOutput.accept(new ResourceLocation(References.MODID, "deep_city_corridor"), deepCityCorridor());
        pOutput.accept(new ResourceLocation(References.MODID, "hamlet_lighthouse"), hamletLighthouse());
        pOutput.accept(new ResourceLocation(References.MODID, "hamlet_storehouse"), hamletStorehouse());
        pOutput.accept(new ResourceLocation(References.MODID, "hamlet_hall"), hamletHall());
    }

    private static LootTable.Builder deepCityAltar() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 6.0F))
                        .add(LootItem.lootTableItem(Registration.ACID_GLAND.get())         .setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.PLUCKED_EYE.get())        .setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SHELL.get())              .setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.TINY_SKULL.get())         .setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.FERTILIZER_GLAND.get())   .setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.MARROW_GLAND.get())       .setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SILK_GLAND.get())         .setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.GUNPOWDER_BLADDER.get())  .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.LIVING_IRON.get())        .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get())      .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.EMERALD_GEM.get())        .setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SWOLLEN_GROWTH.get())     .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.PERIOSTEUM_GROWTH.get())  .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.CHROMATOPHORE_GLAND.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.OSTEOCLAST_GLAND.get())   .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SLIME_HEART.get())        .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT)                      .setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                        .add(LootItem.lootTableItem(Items.DIAMOND)                         .setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                        .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA)                .setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .add(LootItem.lootTableItem(Registration.SLUG.get())               .setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 14.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(EmptyLootItem.emptyItem().setWeight(120))
                        .add(LootItem.lootTableItem(Registration.VESSEL_STONE.get()).setWeight(5).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(Registration.BLOOD_GEM.get()).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(Registration.BLOOD_ORB.get()).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    private static LootTable.Builder deepCityBeacon() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(LootItem.lootTableItem(Items.NETHERITE_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.DIAMOND_SHOVEL).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.NETHERITE_SWORD).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.NETHERITE_BOOTS).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.NETHERITE_CHESTPLATE).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.NETHERITE_LEGGINGS).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.NETHERITE_HELMET).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure()))
                        .add(LootItem.lootTableItem(Items.NETHERITE_PICKAXE).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(25.0F, 39.0F)).allowTreasure())))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(14.0F, 32.0F))))
                        .add(LootItem.lootTableItem(Items.BEETROOT_SOUP).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(14.0F, 32.0F))))
                        .add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(5).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(32.0F, 64.0F)))));
    }

    private static LootTable.Builder deepCityCorridor() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS)              .setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(20.0F, 40.0F))))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD)                 .setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(20.0F, 40.0F))))
                        .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA)                 .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Registration.SLUG.get())                .setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 15.0F))))
                        .add(LootItem.lootTableItem(Registration.ACID_GLAND.get())          .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.PLUCKED_EYE.get())         .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SHELL.get())               .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.TINY_SKULL.get())          .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.FERTILIZER_GLAND.get())    .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.MARROW_GLAND.get())        .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SILK_GLAND.get())          .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.GUNPOWDER_BLADDER.get())   .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.LIVING_IRON.get())         .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get())       .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SWOLLEN_GROWTH.get())      .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.PERIOSTEUM_GROWTH.get())   .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.CHROMATOPHORE_GLAND.get()) .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.OSTEOCLAST_GLAND.get())    .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Registration.SLIME_HEART.get())         .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))));
    }

    private static LootTable.Builder hamletLighthouse() {
        CompoundTag phialTag = new CompoundTag();
        phialTag.putString("memory", Memory.DARKNESS.getDataName());
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 5.0F))
                        .add(LootItem.lootTableItem(Registration.SEAWEED_STEW.get()).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Registration.MEMORY_PHIAL.get()).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))).apply(SetNbtFunction.setTag(new CompoundTag())))
                        .add(LootItem.lootTableItem(Registration.MEMORY_PHIAL.get()).setWeight(3).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))).apply(SetNbtFunction.setTag(phialTag))));
    }

    private static LootTable.Builder hamletStorehouse() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 5.0F))
                        .add(LootItem.lootTableItem(Registration.SEAWEED_STEW.get()).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Registration.ANTIDOTE_CAPSULE.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Registration.SLUG_CATCHER.get()).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .add(LootItem.lootTableItem(Registration.DAMP_WOOD.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F))))
                        .add(LootItem.lootTableItem(Registration.DAMP_CANOPY.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F))))
                        .add(LootItem.lootTableItem(Registration.WORN_BRICKS.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F))))
                        .add(LootItem.lootTableItem(Registration.BLACK_KELP.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 16.0F))))
                        .add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(Registration.RUM.get()).setWeight(10).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .add(LootItem.lootTableItem(Registration.ALE.get()).setWeight(10).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .add(LootItem.lootTableItem(Registration.MEAD.get()).setWeight(10).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .add(LootItem.lootTableItem(Registration.WINE.get()).setWeight(10).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        .add(LootItem.lootTableItem(Registration.VODKA.get()).setWeight(10).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))));

    }

    private static LootTable.Builder hamletHall() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 10.0F))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                        .add(LootItem.lootTableItem(Items.MAP).setWeight(2))
                        .add(LootItem.lootTableItem(Items.COMPASS).setWeight(2))
                        .add(LootItem.lootTableItem(Registration.MEMORY_PHIAL.get()).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))).apply(SetNbtFunction.setTag(new CompoundTag())))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(6).apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30.0F)).allowTreasure())));
    }

    // deep city corridor
    // deep city house top
    // hamlet lighthouse
}
