package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MemoryPhialItem extends Item {

    public MemoryPhialItem() {
        super(new Item.Properties().tab(References.ITEM_GROUP).stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));

        pTooltipComponents.add(m == null ? new TranslatableComponent("tooltip.memory_phial.empty") : new TranslatableComponent("tooltip.memory_phial.stored", new TranslatableComponent(m.getLocalizationKey())));
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
