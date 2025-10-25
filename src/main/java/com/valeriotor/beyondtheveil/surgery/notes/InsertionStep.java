package com.valeriotor.beyondtheveil.surgery.notes;

import com.valeriotor.beyondtheveil.item.SurgeryIngredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class InsertionStep extends ReportStep.CompletableStep {

    private final SurgeryIngredient ingredient;

    public InsertionStep(CompoundTag tag) {
        super(ReportStepType.INSERTION, tag);
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item")));
        if (item instanceof SurgeryIngredient s) {
            this.ingredient = s;
        } else {
            this.ingredient = null;
        }
    }

    public InsertionStep(boolean complete, SurgeryIngredient ingredient) {
        super(ReportStepType.INSERTION, complete);
        this.ingredient = ingredient;
    }

    public SurgeryIngredient getIngredient() {
        return ingredient;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = super.saveToNBT();
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(ingredient);
        tag.putString("item", ingredient == null || key == null ? "minecraft:air" : key.toString());
        return tag;
    }
}
