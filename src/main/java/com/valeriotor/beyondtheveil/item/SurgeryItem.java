package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SurgeryItem extends Item {

    private final SurgeryItemType type;
    private static final List<SurgeryItem> REGISTRY = new ArrayList<>();

    public SurgeryItem(SurgeryItemType type) {
        super(new Item.Properties().stacksTo(1));
        this.type = type;
        REGISTRY.add(this);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();
        Block block = level.getBlockState(clickedPos).getBlock();
        if (context.getPlayer() != null) {
            context.getPlayer().startUsingItem(context.getHand());
            return InteractionResult.PASS;
        }
        //if (block instanceof FlaskBlock || block == Registration.FLASK_SHELF.get()) {
        //}
        return InteractionResult.FAIL;
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() || event.phase == TickEvent.Phase.START) {
            return;
        }
        Player p = event.player;
        Level level = p.level();
        SurgeryItem usedItem = null;
        if (p.isUsingItem() && p.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SurgeryItem surgeryItem) {
            usedItem = surgeryItem;
            HitResult hitResult = p.pick(p.getEntityReach(), 0, false);
            if (hitResult instanceof BlockHitResult bhr) {
                BlockPos pos = bhr.getBlockPos();
                BlockState lookedAtState = level.getBlockState(pos);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                surgeryItem.interactWithBE(p, level, pos, lookedAtState, blockEntity, bhr);
            } else {
                DataUtil.setInt(p, surgeryItem.type.name(), 0, true);
            }
        }
        for (SurgeryItem surgeryItem : REGISTRY) {
            if (!surgeryItem.equals(usedItem)) { // that one is fully handled by the if statement
                DataUtil.setInt(p, surgeryItem.type.name(), 0, true);
            }
        }
    }

    protected void interactWithBE(Player p, Level level, BlockPos pos, BlockState lookedAtState, BlockEntity blockEntity, BlockHitResult bhr) {
        if (blockEntity instanceof SurgicalBE surgicalBE) {
            surgicalBE.interact(p, p.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);
            DataUtil.incrementOrSetInteger(p, this.type.name(), 1, 1, true);
        } else if (lookedAtState.getBlock() == Registration.SURGERY_BED.get()) {
            BlockPos centerPos = Registration.SURGERY_BED.get().findCenter(pos, lookedAtState);
            if (level.getBlockEntity(centerPos) instanceof SurgicalBE surgicalBE) {
                surgicalBE.interact(p, p.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);
                DataUtil.incrementOrSetInteger(p, this.type.name(), 1, 1, true);
            }
        } else {
            DataUtil.setInt(p, this.type.name(), 0, true);
        }
    }



    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    public enum SurgeryItemType {
        SCALPEL, FORCEPS, TONGS, SYRINGE
    }


}
