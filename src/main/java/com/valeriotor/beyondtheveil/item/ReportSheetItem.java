package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.surgery.notes.Report;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReportSheetItem extends Item {
    public ReportSheetItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.contains("report")) {
            Report report = Report.loadFromNBT(tag.getCompound("report"));
            String name = report.getName();
            pTooltipComponents.add(Component.literal(""));
            pTooltipComponents.add(Component.literal("§6§o" + name));
        }
    }
}
