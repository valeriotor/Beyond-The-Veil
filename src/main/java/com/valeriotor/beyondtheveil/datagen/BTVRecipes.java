package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registration.ONIRIC_INCENSE.get())
                .requires(Items.CLAY_BALL)
                .requires(Items.FLINT)
                .requires(Blocks.GRAVEL)
                .group("Beyond the Veil")
                .unlockedBy("has_oniric_incense", has(Registration.ONIRIC_INCENSE.get()))
                .save(consumer);

    }
}
