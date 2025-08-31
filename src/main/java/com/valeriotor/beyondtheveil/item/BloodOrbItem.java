package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BloodOrbItem extends Item {

    public BloodOrbItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) {
            pPlayer.playSound(SoundEvents.MUD_PLACE, 1, 1);
            GuiHelper.openClientSideGui(GuiHelper.GuiType.BLOOD_POOL);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
