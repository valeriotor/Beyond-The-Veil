package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.rituals.bindings.BindingEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BloodFistItem extends Item {

    public BloodFistItem() {
        super(new Properties().stacksTo(1));
    }


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return BindingEvents.useFistOnBlock(pContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        boolean startUsing = BindingEvents.useFistInAir(pLevel, pPlayer, pUsedHand);
        if (startUsing) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof ServerPlayer sp) {
            BindingEvents.usingFist(pLevel, sp);
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
}
