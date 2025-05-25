package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.util.TeleportUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
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
                CrawlerEntity crawler = villager.convertTo(BTVEntities.CRAWLER.get(), false);
                if (crawler != null) {
                    crawler.setData(villager);
                    crawler.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(crawlerCap -> {
                        villager.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(villagerCap -> {
                            crawlerCap.loadFromNBT(villagerCap.saveToNBT(new CompoundTag()));
                        });
                    });
                    return InteractionResult.SUCCESS;
                }
            }

        }
        return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            //BlockPos nearestVein = ((ServerLevel) context.getLevel()).findNearestMapStructure(BTVTags.DEEP_VEIN, context.getClickedPos(), 200, false);
            //context.getPlayer().sendSystemMessage(Component.literal(nearestVein == null ? "null" : nearestVein.toString()));

        }
            //LegacyStructure.hamlet_storehouse2.generateStructure(context.getLevel(), context.getClickedPos());

        return super.onItemUseFirst(stack, context);
    }
}
