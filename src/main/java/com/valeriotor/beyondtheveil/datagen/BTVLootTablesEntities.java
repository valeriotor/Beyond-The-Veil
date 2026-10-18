package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.stream.Stream;

public class BTVLootTablesEntities extends EntityLootSubProvider {

    protected BTVLootTablesEntities() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(BTVEntities.SHOREMAN.get(), LootTable.lootTable());
        add(BTVEntities.BLOOD_CULTIST.get(), LootTable.lootTable());
        add(BTVEntities.ABOMINATION_0.get(), LootTable.lootTable());
        add(BTVEntities.ABOMINATION_1.get(), LootTable.lootTable());
        add(BTVEntities.ABOMINATION_2.get(), LootTable.lootTable());
        add(BTVEntities.FLETUM.get(), LootTable.lootTable());
        add(BTVEntities.WEEPER.get(), LootTable.lootTable());
        add(BTVEntities.CRAWLER.get(), LootTable.lootTable());
        add(BTVEntities.BLOOD_WRAITH.get(), LootTable.lootTable());
        add(BTVEntities.BLOOD_ZOMBIE.get(), LootTable.lootTable());
        add(BTVEntities.BLOOD_SKELETON.get(), LootTable.lootTable());
        add(BTVEntities.SURGEON.get(), LootTable.lootTable());
        add(BTVEntities.SURGEON_LARVA.get(), LootTable.lootTable());
        add(BTVEntities.DEEP_ONE.get(), LootTable.lootTable());

        //PLUCKED_EYE, shell, tiny_skull, ACID_GLAND, FERTILIZER_GLAND, marrow_gland, silk_gland, gunpowder_bladder, living_iron, EMPTY_BLADDER, SWOLLEN_GROWTH, periosteum_growth, chromatophore_gland, OSTEOCLAST_GLAND, SLIME_HEART
        //GROWTH_STIMULANT, DIAMOND_POWDER, ORGANOCHLORIDE, pheromones, VASOCONSTRICTOR
        add(BTVEntities.CEPHALOPODIAN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)) // guaranteed
                        .add(LootItem.lootTableItem(Registration.SWOLLEN_GROWTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)) // guaranteed
                        .add(LootItem.lootTableItem(Registration.OSTEOCLAST_GLAND.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.8F))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.5F))
                        .add(LootItem.lootTableItem(Registration.PLUCKED_EYE.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.ACID_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.FERTILIZER_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.SLIME_HEART.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, SHELL, tiny_skull, acid_gland, FERTILIZER_GLAND, MARROW_GLAND, silk_gland, gunpowder_bladder, living_iron, EMPTY_BLADDER, swollen_growth, PERIOSTEUM_GROWTH, chromatophore_gland, osteoclast_gland, slime_heart
        //GROWTH_STIMULANT, DIAMOND_POWDER, ORGANOCHLORIDE, pheromones, VASOCONSTRICTOR
        add(BTVEntities.BONECAGE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)) // guaranteed
                        .add(LootItem.lootTableItem(Registration.PERIOSTEUM_GROWTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.9F))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.9F))
                        .add(LootItem.lootTableItem(Registration.SHELL.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.FERTILIZER_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.MARROW_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, SHELL, TINY_SKULL, acid_gland, FERTILIZER_GLAND, marrow_gland, silk_gland, gunpowder_bladder, living_iron, EMPTY_BLADDER, swollen_growth, periosteum_growth, CHROMATOPHORE_GLAND, OSTEOCLAST_GLAND, slime_heart
        //GROWTH_STIMULANT, DIAMOND_POWDER, organochloride, PHEROMONES, VASOCONSTRICTOR
        add(BTVEntities.SANDFLATTER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)) // guaranteed
                        .add(LootItem.lootTableItem(Registration.CHROMATOPHORE_GLAND.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.9F))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.9F))
                        .add(LootItem.lootTableItem(Registration.SHELL.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.FERTILIZER_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.OSTEOCLAST_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, shell, tiny_skull, acid_gland, fertilizer_gland, marrow_gland, silk_gland, gunpowder_bladder, living_iron, EMPTY_BLADDER, swollen_growth, periosteum_growth, CHROMATOPHORE_GLAND, OSTEOCLAST_GLAND, slime_heart
        //growth_stimulant, DIAMOND_POWDER, organochloride, pheromones, VASOCONSTRICTOR
        add(BTVEntities.ADELINE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.CHROMATOPHORE_GLAND.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.OSTEOCLAST_GLAND.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, shell, tiny_skull, ACID_GLAND, FERTILIZER_GLAND, marrow_gland, silk_gland, gunpowder_bladder, living_iron, empty_bladder, SWOLLEN_GROWTH, periosteum_growth, chromatophore_gland, osteoclast_gland, slime_heart
        //growth_stimulant, DIAMOND_POWDER, ORGANOCHLORIDE, pheromones, VASOCONSTRICTOR
        add(BTVEntities.MAN_O_WAR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(Registration.ACID_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.FERTILIZER_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.SWOLLEN_GROWTH.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //PLUCKED_EYE, shell, tiny_skull, acid_gland, fertilizer_gland, marrow_gland, silk_gland, GUNPOWDER_BLADDER, living_iron, EMPTY_BLADDER, SWOLLEN_GROWTH, periosteum_growth, chromatophore_gland, osteoclast_gland, slime_heart
        //growth_stimulant, DIAMOND_POWDER, organochloride, pheromones, VASOCONSTRICTOR
        add(BTVEntities.UMANCALA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootItem.lootTableItem(Registration.PLUCKED_EYE.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.GUNPOWDER_BLADDER.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.SWOLLEN_GROWTH.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //PLUCKED_EYE, shell, tiny_skull, acid_gland, fertilizer_gland, marrow_gland, silk_gland, gunpowder_bladder, living_iron, EMPTY_BLADDER, SWOLLEN_GROWTH, PERIOSTEUM_GROWTH, chromatophore_gland, osteoclast_gland, slime_heart
        //growth_stimulant, DIAMOND_POWDER, organochloride, PHEROMONES, VASOCONSTRICTOR
        add(BTVEntities.ANGLER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.8F))
                        .add(LootItem.lootTableItem(Registration.PLUCKED_EYE.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.PERIOSTEUM_GROWTH.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.SWOLLEN_GROWTH.get()).setWeight(2)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, SHELL, TINY_SKULL, ACID_GLAND, fertilizer_gland, marrow_gland, silk_gland, gunpowder_bladder, living_iron, EMPTY_BLADDER, swollen_growth, periosteum_growth, chromatophore_gland, osteoclast_gland, slime_heart
        //growth_stimulant, DIAMOND_POWDER, organochloride, pheromones, VASOCONSTRICTOR
        add(BTVEntities.SEA_SNAKE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.8F))
                        .add(LootItem.lootTableItem(Registration.SHELL.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.TINY_SKULL.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.ACID_GLAND.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.EMPTY_BLADDER.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, shell, tiny_skull, acid_gland, fertilizer_gland, marrow_gland, silk_gland, gunpowder_bladder, living_iron, empty_bladder, swollen_growth, periosteum_growth, CHROMATOPHORE_GLAND, OSTEOCLAST_GLAND, slime_heart
        //growth_stimulant, DIAMOND_POWDER, organochloride, PHEROMONES, VASOCONSTRICTOR
        add(BTVEntities.OCTID.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.25F))
                        .add(LootItem.lootTableItem(Registration.CHROMATOPHORE_GLAND.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                        .add(LootItem.lootTableItem(Registration.OSTEOCLAST_GLAND.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, shell, TINY_SKULL, acid_gland, fertilizer_gland, marrow_gland, silk_gland, gunpowder_bladder, living_iron, empty_bladder, swollen_growth, periosteum_growth, chromatophore_gland, osteoclast_gland, slime_heart
        //growth_stimulant, DIAMOND_POWDER, organochloride, pheromones, VASOCONSTRICTOR
        add(BTVEntities.SEPIID.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(BinomialDistributionGenerator.binomial(1, 0.25F))
                        .add(LootItem.lootTableItem(Registration.TINY_SKULL.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));


        //plucked_eye, shell, tiny_skull, acid_gland, fertilizer_gland, marrow_gland, silk_gland, gunpowder_bladder, living_iron, empty_bladder, swollen_growth, periosteum_growth, chromatophore_gland, osteoclast_gland, slime_heart
        //growth_stimulant, diamond_powder, organochloride, pheromones, vasoconstrictor
        add(BTVEntities.JELLY.get(), LootTable.lootTable());
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(BTVEntities.SHOREMAN.get(),
                BTVEntities.BLOOD_CULTIST.get(),
                BTVEntities.ABOMINATION_0.get(),
                BTVEntities.ABOMINATION_1.get(),
                BTVEntities.ABOMINATION_2.get(),
                BTVEntities.FLETUM.get(),
                BTVEntities.WEEPER.get(),
                BTVEntities.CRAWLER.get(),
                BTVEntities.BLOOD_WRAITH.get(),
                BTVEntities.BLOOD_ZOMBIE.get(),
                BTVEntities.BLOOD_SKELETON.get(),
                BTVEntities.SURGEON.get(),
                BTVEntities.SURGEON_LARVA.get(),
                BTVEntities.DEEP_ONE.get(),
                BTVEntities.CEPHALOPODIAN.get(),
                BTVEntities.BONECAGE.get(),
                BTVEntities.SANDFLATTER.get(),
                BTVEntities.ADELINE.get(),
                BTVEntities.MAN_O_WAR.get(),
                BTVEntities.UMANCALA.get(),
                BTVEntities.ANGLER.get(),
                BTVEntities.SEA_SNAKE.get(),
                BTVEntities.OCTID.get(),
                BTVEntities.SEPIID.get(),
                BTVEntities.JELLY.get());
    }
}
