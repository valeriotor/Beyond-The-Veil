package com.valeriotor.beyondtheveil.rituals;

import com.google.common.collect.Lists;
import com.valeriotor.beyondtheveil.Registration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RitualRegistry {

    private static final Map<Item, Map<List<Item>, RitualTemplate>> DICTIONARY = new HashMap<>();
    private static final Map<String, RitualTemplate> BY_NAME = new HashMap<>();


    public static final RitualTemplate BLOOD_BRICKS_1 = new RitualTemplate.RitualTemplateBuilder("blood_bricks_1", 0, 0, 0)
            .setIngredients(Lists.newArrayList(Registration.BLOOD_BRICK_ITEM.get()))
            .setOutputs(((itemStacks, player) -> byNumber(itemStacks, Registration.BLOOD_BRICK_ITEM.get(), 1)))
            .toTemplate(DICTIONARY, BY_NAME);

    public static final RitualTemplate BLOOD_BRICKS_2 = new RitualTemplate.RitualTemplateBuilder("blood_bricks_2", 0, 0, 0)
            .setIngredients(Lists.newArrayList(Registration.BLOOD_BRICK_ITEM.get(), Registration.BLOOD_BRICK_ITEM.get()))
            .setOutputs(((itemStacks, player) -> byNumber(itemStacks, Registration.BLOOD_BRICK_ITEM.get(), 2)))
            .toTemplate(DICTIONARY, BY_NAME);

    public static final RitualTemplate BLOOD_BRICKS_3 = new RitualTemplate.RitualTemplateBuilder("blood_bricks_3", 0, 0, 0)
            .setIngredients(Lists.newArrayList(Registration.BLOOD_BRICK_ITEM.get(), Registration.BLOOD_BRICK_ITEM.get(), Registration.BLOOD_BRICK_ITEM.get()))
            .setOutputs(((itemStacks, player) -> byNumber(itemStacks, Registration.BLOOD_BRICK_ITEM.get(), 3)))
            .toTemplate(DICTIONARY, BY_NAME);

    public static final RitualTemplate CORAL_STAFF = new RitualTemplate.RitualTemplateBuilder("coral_staff", 0, 0, 0)
            .setIngredients(Lists.newArrayList(Items.FIRE_CORAL, Items.HEART_OF_THE_SEA, Registration.HEART_ITEM.get()))
            .setOutputs(List.of(new ItemStack(Registration.CORAL_STAFF.get())))
            .toTemplate(DICTIONARY, BY_NAME);


    private static List<ItemStack> byNumber(List<ItemStack> input, Item outputItem, int max) {
        List<ItemStack> output = new ArrayList<>();
        for (int i = 0; i < input.size() && i < max; i++) {
            ItemStack s = input.get(i);
            output.add(new ItemStack(outputItem, s.getCount()));
        }
        return output;
    }

    public static RitualTemplate longestMatch(List<Item> items) {
        // we accept list of itemstacks for now, but it could just be list of items..
        if (items.isEmpty()) {
            return null;
        }
        items = Lists.reverse(items);
        Map<List<Item>, RitualTemplate> templatesByInverseIngredients = DICTIONARY.get(items.get(0));
        if (templatesByInverseIngredients == null) {
            return null;
        }
        int maxLength = 0;
        RitualTemplate bestMatch = null;
        for (Map.Entry<List<Item>, RitualTemplate> entry : templatesByInverseIngredients.entrySet()) {
            if (entry.getKey().size() > maxLength && matches(entry.getKey(), items)) {
                maxLength = entry.getKey().size();
                bestMatch = entry.getValue();
            }
        }
        return bestMatch;
    }

    private static boolean matches(List<Item> template, List<Item> provided) {
        if (provided.size() < template.size()) {
            return false;
        }
        for (int i = 0; i < template.size(); i++) {
            if (template.get(i) != provided.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static RitualTemplate byName(String name) {
        return BY_NAME.get(name);
    }

}
