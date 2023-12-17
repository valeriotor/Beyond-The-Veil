package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MemoryPhialItem extends Item {

    public MemoryPhialItem() {
        super(new Item.Properties().stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));

        pTooltipComponents.add(m == null ? Component.translatable("tooltip.memory_phial.empty") : Component.translatable("tooltip.memory_phial.stored", Component.translatable(m.getLocalizationKey())));
    }

    public static class MemoryPhialColor implements ItemColor {

        @Override
        public int getColor(ItemStack pStack, int pTintIndex) {
            CompoundTag tag = pStack.getOrCreateTag();
            Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));
            if(m != null) return m.getColor();
            return -1;
        }
    }


}
