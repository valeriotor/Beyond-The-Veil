package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class BTVRecipes extends RecipeProvider {

    public BTVRecipes(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.MEMORY_SIEVE.get())
                .pattern("SWS")
                .pattern(" S ")
                .pattern("SSS")
                .define('S', Blocks.STONE)
                .define('W', Items.WATER_BUCKET)
                .group("Beyond the Veil")
                .unlockedBy("has_memory_sieve", has(Registration.MEMORY_SIEVE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.MEMORY_PHIAL.get(), 3)
                .pattern(" B ")
                .pattern("GWG")
                .pattern(" G ")
                .define('G', Blocks.GLASS)
                .define('W', Items.WATER_BUCKET)
                .define('B', ItemTags.WOODEN_BUTTONS)
                .group("Beyond the Veil")
                .unlockedBy("has_memory_phial", has(Registration.MEMORY_PHIAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.FUME_SPREADER.get(), 1)
                .pattern("GOG")
                .pattern("G G")
                .pattern("OOO")
                .define('G', Blocks.GLASS)
                .define('O', Items.GOLD_INGOT)
                .group("Beyond the Veil")
                .unlockedBy("has_fume_spreader", has(Registration.FUME_SPREADER.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.GEAR.get(), 1)
                .pattern(" S ")
                .pattern("SGS")
                .pattern(" S ")
                .define('G', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .group("Beyond the Veil")
                .unlockedBy("has_gear", has(Registration.GEAR.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.GEAR_BENCH.get(), 1)
                .pattern("GGG")
                .pattern("LGL")
                .pattern("LLL")
                .define('G', Registration.GEAR.get())
                .define('L', ItemTags.LOGS)
                .group("Beyond the Veil")
                .unlockedBy("has_gear_bench", has(Registration.GEAR_BENCH.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.TROPICAL_DELIGHT.get(), 1)
                .pattern("TTT")
                .pattern("SCB")
                .pattern("   ")
                .define('C', Items.CLAY_BALL)
                .define('T', Items.TROPICAL_FISH)
                .define('B', Items.BEETROOT)
                .define('S', Items.SWEET_BERRIES)
                .group("Beyond the Veil")
                .unlockedBy("has_tropical_delight", has(Registration.TROPICAL_DELIGHT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.DAMP_WOOD_FENCE.get(), 3)
                .pattern("   ")
                .pattern("WSW")
                .pattern("WSW")
                .define('W', Registration.DAMP_WOOD.get())
                .define('S', Items.STICK)
                .group("Beyond the Veil")
                .unlockedBy("has_damp_wood", has(Registration.DAMP_WOOD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.DAMP_WOOD_STAIRS.get(), 4)
                .pattern("W  ")
                .pattern("WW ")
                .pattern("WWW")
                .define('W', Registration.DAMP_WOOD.get())
                .group("Beyond the Veil")
                .unlockedBy("has_damp_wood", has(Registration.DAMP_WOOD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.DAMP_CANOPY.get(), 4)
                .pattern("W  ")
                .pattern(" W ")
                .pattern("  W")
                .define('W', Registration.DAMP_WOOD.get())
                .group("Beyond the Veil")
                .unlockedBy("has_damp_wood", has(Registration.DAMP_WOOD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.DAMP_FILLED_CANOPY.get(), 1)
                .pattern("C  ")
                .pattern("W  ")
                .pattern("   ")
                .define('W', Registration.DAMP_WOOD.get())
                .define('C', Registration.DAMP_CANOPY.get())
                .group("Beyond the Veil")
                .unlockedBy("has_damp_canopy", has(Registration.DAMP_CANOPY.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.CANOE.get(), 1)
                .pattern("   ")
                .pattern("W W")
                .pattern("WWW")
                .define('W', Registration.DAMP_WOOD.get())
                .group("Beyond the Veil")
                .unlockedBy("has_damp_wood", has(Registration.DAMP_WOOD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ANTIDOTE_CAPSULE.get(), 1)
                .pattern(" G ")
                .pattern("GHG")
                .pattern(" G ")
                .define('G', Items.GLASS)
                .define('H', Items.HEART_OF_THE_SEA)
                .group("Beyond the Veil")
                .unlockedBy("has_antidote_capsule", has(Registration.ANTIDOTE_CAPSULE.get()))
                .save(consumer, BuiltInRegistries.ITEM.getKey(Registration.ANTIDOTE_CAPSULE.get().asItem()) + "1");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registration.ONIRIC_INCENSE.get(), 2)
                .requires(Registration.ANTIDOTE_CAPSULE.get())
                .requires(Items.INK_SAC)
                .group("Beyond the Veil")
                .unlockedBy("has_antidote_capsule", has(Registration.ANTIDOTE_CAPSULE.get()))
                .save(consumer, BuiltInRegistries.ITEM.getKey(Registration.ANTIDOTE_CAPSULE.get().asItem()) + "2");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registration.ONIRIC_INCENSE.get())
                .requires(Items.CLAY_BALL)
                .requires(Items.FLINT)
                .requires(Blocks.GRAVEL)
                .group("Beyond the Veil")
                .unlockedBy("has_oniric_incense", has(Registration.ONIRIC_INCENSE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registration.DAMP_WOOD.get(), 4)
                .requires(Registration.DAMP_LOG.get())
                .group("Beyond the Veil")
                .unlockedBy("has_damp_log", has(Registration.DAMP_LOG.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registration.SEAWEED_STEW.get(), 1)
                .requires(Items.BOWL)
                .requires(Items.SEAGRASS, 3)
                .group("Beyond the Veil")
                .unlockedBy("has_seaweed_stew", has(Registration.SEAWEED_STEW.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registration.KELP_COD_BUNDLE.get(), 1)
                .requires(Items.COD)
                .requires(Items.KELP, 2)
                .group("Beyond the Veil")
                .unlockedBy("has_kelp_cod_bundle", has(Registration.KELP_COD_BUNDLE.get()))
                .save(consumer);

    }
}
