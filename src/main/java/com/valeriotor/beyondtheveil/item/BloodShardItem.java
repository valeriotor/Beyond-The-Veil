package com.valeriotor.beyondtheveil.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BloodShardItem extends Item {


    public BloodShardItem() {
        super(new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        if (tag != null) {
            String type = tag.getString("type");
            EntityType<?> value = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(type));
            if (value != null && tag.contains("type")) {
                Component description = value.getDescription();
                pTooltipComponents.add(Component.literal(Component.translatable("tooltip.pillar.bound").getString() + description.getString()));
            }
        }
    }
}
