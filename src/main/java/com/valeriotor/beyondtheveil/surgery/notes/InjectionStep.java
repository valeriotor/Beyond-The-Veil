package com.valeriotor.beyondtheveil.surgery.notes;

import com.valeriotor.beyondtheveil.item.SurgeryIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class InjectionStep extends ReportStep {

    private final int amount;
    private final Fluid fluid;

    public InjectionStep(CompoundTag tag) {
        super(ReportStepType.INJECTION);
        Fluid fluid = tag.contains("fluid") ? ForgeRegistries.FLUIDS.getValue(new ResourceLocation(tag.getString("fluid"))) : null;
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item")));
        if (fluid != null) {
            this.fluid = fluid;
        } else {
            this.fluid = null;
        }
        this.amount = tag.getInt("amount");
    }

    public InjectionStep(int amount, Fluid fluid) {
        super(ReportStepType.INJECTION);
        this.amount = amount;
        this.fluid = fluid;
    }

    public int getAmount() {
        return amount;
    }

    public Fluid getFluid() {
        return fluid;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = super.saveToNBT();
        ResourceLocation key = ForgeRegistries.FLUIDS.getKey(fluid);
        if (fluid != null && key != null) {
            tag.putString("fluid", key.toString());
        }
        tag.putInt("amount", amount);
        return tag;
    }
}
