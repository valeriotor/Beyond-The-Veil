package com.valeriotor.beyondtheveil.recipes;

import com.valeriotor.beyondtheveil.lib.BTVFluids;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.*;

public class AlembicsRecipeRegistry {

    private static final Map<Fluid, List<AlembicRecipe>> RECIPES = new HashMap<>();
    private static final Map<Fluid, Set<Tuple<AlembicRecipe, List<Item>>>> RECIPES_BY_OUTPUT = new HashMap<>();

    public static void registerRecipes() {
        registerRecipe(Fluids.WATER, new ItemStack(Items.HONEYCOMB), Fluids.WATER, 20, BTVFluids.SOURCE_FLUID_SEDATIVE.get());
    }

    private static void registerRecipe(Fluid input1, ItemStack stack, Fluid input2, int mBPerItem, Fluid output) {
        registerRecipe(input1, stack, input2, mBPerItem, output, List.of());
    }

    private static void registerRecipe(Fluid input1, ItemStack stack, Fluid input2, int mBPerItem, Fluid output, List<Item> mustKnowIngredients) {
        AlembicRecipe recipe = new AlembicRecipe(input1, stack, input2, mBPerItem, output);
        RECIPES.computeIfAbsent(input1, f -> new ArrayList<>()).add(recipe);
        RECIPES_BY_OUTPUT.computeIfAbsent(recipe.output, f -> new HashSet<>()).add(new Tuple<>(recipe, mustKnowIngredients));
    }

    public static Tuple<Fluid, Integer> getOutput(Fluid input1, ItemStack stack, Fluid input2) {
        List<AlembicRecipe> alembicRecipes = RECIPES.get(input1);
        if (alembicRecipes != null) {
            for (AlembicRecipe r : alembicRecipes) {
                if (input1 == r.input1 && ItemStack.isSameItemSameTags(stack, r.stack) && input2 == r.input2) {
                    return new Tuple<>(r.output, r.mBPerItem);
                }
            }
        }
        return null;
    }

    public static List<AlembicRecipe> knownRecipes(Fluid output, List<Item> knownIngredients) {
        Set<Tuple<AlembicRecipe, List<Item>>> tuples = RECIPES_BY_OUTPUT.getOrDefault(output, new HashSet<>());
        List<AlembicRecipe> list = new ArrayList<>();
        for (Tuple<AlembicRecipe, List<Item>> tuple : tuples) {
            boolean flag = true;
            for (Item item : tuple.getB()) {
                if (!knownIngredients.contains(item)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.add(tuple.getA());
            }
        }
        return list;
    }


    public record AlembicRecipe(Fluid input1, ItemStack stack, Fluid input2, int mBPerItem, Fluid output) {
    }

}

