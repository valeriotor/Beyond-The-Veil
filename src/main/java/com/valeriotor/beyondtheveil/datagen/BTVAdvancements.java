package com.valeriotor.beyondtheveil.datagen;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.fluid.SurgicalFluidType;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BTVAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        //Advancement advancement = Advancement.Builder.recipeAdvancement().addCriterion("has_plucked_eye", has(Registration.PLUCKED_EYE.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/plucked_eye"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_plucked_eye", has(Registration.PLUCKED_EYE.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/plucked_eye"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_shell", has(Registration.SHELL.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/shell"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_tiny_skull", has(Registration.TINY_SKULL.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/tiny_skull"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_acid_gland", has(Registration.ACID_GLAND.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/acid_gland"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_fertilizer_gland", has(Registration.FERTILIZER_GLAND.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/fertilizer_gland"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_marrow_gland", has(Registration.MARROW_GLAND.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/marrow_gland"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_silk_gland", has(Registration.SILK_GLAND.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/silk_gland"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_gunpowder_bladder", has(Registration.GUNPOWDER_BLADDER.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/gunpowder_bladder"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_living_iron", has(Registration.LIVING_IRON.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/living_iron"), existingFileHelper);
        Advancement.Builder.recipeAdvancement().addCriterion("has_empty_bladder", has(Registration.EMPTY_BLADDER.get())).save(saver, new ResourceLocation(References.MODID, "ingredients/empty_bladder"), existingFileHelper);
    }

    @NotNull
    private InventoryChangeTrigger.TriggerInstance has(ItemLike item) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(item).build()});
    }

    @NotNull
    private InventoryChangeTrigger.TriggerInstance has(ItemStack item) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(item.getItem()).hasNbt(item.getOrCreateTag()).build()});
    }



}
