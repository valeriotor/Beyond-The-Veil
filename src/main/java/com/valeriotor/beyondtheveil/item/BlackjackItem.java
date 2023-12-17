package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlackjackItem extends Item {


    public BlackjackItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND || pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != this) {
            if (pInteractionTarget instanceof Villager villager) {
                Level l = pInteractionTarget.level();
                CrawlerEntity crawler = villager.convertTo(Registration.CRAWLER.get(), false);
                if (crawler != null) {
                    crawler.setData(villager);
                    return InteractionResult.SUCCESS;
                }
            }

        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }


}
