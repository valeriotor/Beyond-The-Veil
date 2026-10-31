package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.surgery.notes.Report;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class DecrepitHeartItem extends Item {

    public DecrepitHeartItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.contains("player") && tag.contains("username")) {
            //UUID uuid = UUID.fromString("player");
            pTooltipComponents.add(Component.translatable("tooltip.decrepit_heart.1"));
            pTooltipComponents.add(Component.translatable("tooltip.decrepit_heart.2"));
            pTooltipComponents.add(Component.translatable("tooltip.decrepit_heart.3", tag.getString("username")));
        }
    }
}
