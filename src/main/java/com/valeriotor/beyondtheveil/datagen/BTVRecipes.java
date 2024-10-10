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
                .unlockedBy("has_tear", has(Registration.MEMORY_SIEVE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.MEMORY_PHIAL.get(), 3)
                .pattern(" B ")
                .pattern("GWG")
                .pattern(" G ")
                .define('G', Blocks.GLASS)
                .define('W', Items.WATER_BUCKET)
                .define('B', ItemTags.WOODEN_BUTTONS)
                .group("Beyond the Veil")
                .unlockedBy("has_tear", has(Registration.MEMORY_PHIAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.FUME_SPREADER.get(), 1)
                .pattern("GOG")
                .pattern("G G")
                .pattern("OOO")
                .define('G', Blocks.GLASS)
                .define('O', Items.GOLD_INGOT)
                .group("Beyond the Veil")
                .unlockedBy("has_tear", has(Registration.FUME_SPREADER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registration.ONIRIC_INCENSE.get())
                .requires(Items.CLAY_BALL)
                .requires(Items.FLINT)
                .requires(Blocks.GRAVEL)
                .group("Beyond the Veil")
                .unlockedBy("has_tear", has(Registration.ONIRIC_INCENSE.get()))
                .save(consumer);

    }
}
