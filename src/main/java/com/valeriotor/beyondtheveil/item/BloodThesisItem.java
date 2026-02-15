package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.util.GuiType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BloodThesisItem extends Item {

    public BloodThesisItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) {
            pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1, 1);
            GuiHelper.openClientSideGui(GuiType.BLOOD_THESIS);
        } /*else {
            DataUtil.setBooleanOnServerAndSync(pPlayer, "thebeginning", true, false);
            ResearchUtil.getResearch(pPlayer, "FIRSTDREAMS");
        }*/
        return super.use(pLevel, pPlayer, pUsedHand);
    }

}
