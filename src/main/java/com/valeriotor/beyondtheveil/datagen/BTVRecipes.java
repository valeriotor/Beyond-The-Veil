package com.valeriotor.beyondtheveil.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class BTVRecipes extends RecipeProvider {

    public BTVRecipes(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
    }
}
