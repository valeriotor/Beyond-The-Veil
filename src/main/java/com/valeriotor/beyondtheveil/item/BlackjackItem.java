package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.LegacyStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

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

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        //if (!context.getLevel().isClientSide) {
        //    BlockPos clickedPos = context.getClickedPos();
        //    Direction clickedFace = context.getClickedFace();
        //    int rad = 3;
        //    for (int i = -rad; i <= rad; i++) {
        //        for (int j = -rad; j <= rad; j++) {
        //            Vec3i offVec = clickedFace.getAxis() == Direction.Axis.X ? new Vec3i(i, 0, j) : new Vec3i(j, 0, i);
        //            BlockPos offset = clickedPos.offset(offVec);
        //            boolean b = (Math.abs(i) + Math.abs(j) >= 4 || Math.abs(i) == 3 || Math.abs(j) == 3) && Math.abs(i) + Math.abs(j) < 6;
        //            context.getLevel().setBlock(offset, b ? Registration.VEIN_STONE.get().defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
        //        }
        //    }
        //}
            //LegacyStructure.hamlet_storehouse2.generateStructure(context.getLevel(), context.getClickedPos());
        return super.onItemUseFirst(stack, context);
    }
}
