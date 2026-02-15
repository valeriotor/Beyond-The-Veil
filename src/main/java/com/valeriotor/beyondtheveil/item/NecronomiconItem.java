package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.util.GuiType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;

public class NecronomiconItem extends Item {
    public NecronomiconItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) {
            pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1, 1);
            GuiHelper.openClientSideGui(GuiType.NECRONOMICON);
        } /*else {
            DataUtil.setBooleanOnServerAndSync(pPlayer, "thebeginning", true, false);
            ResearchUtil.getResearch(pPlayer, "FIRSTDREAMS");
        }*/
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public Component getName(ItemStack pStack) {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
            return super.getName(pStack);
        }
        String s = DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> ClientMethods::necronomiconDescriptionId);
        return Component.translatable(s);
    }
}
