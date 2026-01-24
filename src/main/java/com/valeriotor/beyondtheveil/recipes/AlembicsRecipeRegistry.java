package com.valeriotor.beyondtheveil.recipes;

import com.valeriotor.beyondtheveil.Registration;
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
        registerRecipe(Fluids.WATER, new ItemStack(Items.NETHER_WART), Fluids.WATER, 20, BTVFluids.FLUID_WART_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.SUGAR), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_MOVEMENT_SPEED_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.NAUTILUS_SHELL), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_MOVEMENT_SLOWDOWN_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.SUGAR_CANE), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_DIG_SPEED_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.OBSIDIAN), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_DIG_SLOWDOWN_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.BLAZE_POWDER), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_DAMAGE_BOOST_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.GLISTERING_MELON_SLICE), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_HEAL_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.WITHER_SKELETON_SKULL), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_HARM_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.RABBIT_FOOT), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_JUMP_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.FERMENTED_SPIDER_EYE), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_CONFUSION_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.GHAST_TEAR), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_REGENERATION_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.TURTLE_HELMET), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_DAMAGE_RESISTANCE_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.MAGMA_CREAM), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_FIRE_RESISTANCE_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.PUFFERFISH), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_WATER_BREATHING_SERUM.getA().get());
        //registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.NETHER_WART), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_INVISIBILITY_SERUM.getA().get()); TODO CHROMATOSPHORE GLAND
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Registration.DARK_SAND.get()), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_BLINDNESS_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.GOLDEN_CARROT), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_NIGHT_VISION_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.ROTTEN_FLESH), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_HUNGER_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.TURTLE_EGG), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_WEAKNESS_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.SPIDER_EYE), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_POISON_SERUM.getA().get());
        registerRecipe(BTVFluids.FLUID_WART_SERUM.getA().get(), new ItemStack(Items.WITHER_ROSE), BTVFluids.FLUID_WART_SERUM.getA().get(), 20, BTVFluids.FLUID_WITHER_SERUM.getA().get());
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

