package com.valeriotor.beyondtheveil.block.interfaces;

import com.valeriotor.beyondtheveil.tile.DreamFocusBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface DyableFocus {
    default boolean setColor(ItemStack stack, Level l, BlockPos pos) {
        if (l.getBlockEntity(pos) instanceof DreamFocusBE be) {
            if (stack.getItem() instanceof DyeItem dye)
                be.setDyeColor(DyeColor.getColor(stack));
            else if (stack.getItem() == Items.CLAY_BALL) {
                be.toggleShowPath();
            } else {
                return false;
            }
            return true;
        }
        return false;
    }
}
