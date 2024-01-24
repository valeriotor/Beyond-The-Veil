package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SyringeItem extends Item {

    public SyringeItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, 100);
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
        if (event.side.isClient()) {
            return;
        }
        Player p = event.player;
        Level level = p.level();
        if (p.isUsingItem() && p.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Registration.SYRINGE.get()) {
            HitResult hitResult = p.pick(p.getEntityReach(), 0, false);
            if (hitResult instanceof BlockHitResult bhr) {
                BlockPos pos = bhr.getBlockPos();
                BlockState lookedAtState = level.getBlockState(pos);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (lookedAtState.getBlock() == Registration.FLASK_SHELF.get()) {
                    BlockPos centerPos = Registration.FLASK_SHELF.get().findCenter(pos, lookedAtState);
                    if (level.getBlockEntity(centerPos) instanceof FlaskShelfBE be) {
                        ItemStack itemStack = p.getItemInHand(InteractionHand.MAIN_HAND);
                        Item held = itemStack.getItem();
                        be.interactLiquid(level, pos, p, InteractionHand.MAIN_HAND, bhr);
                    }
                } else if (lookedAtState.getBlock() instanceof FlaskBlock && blockEntity instanceof FlaskBE flaskBE) {
                    flaskBE.tryFillFromItem(level, pos, p, InteractionHand.MAIN_HAND, bhr);
                } else if (blockEntity instanceof SurgicalBE surgicalBE) {
                    surgicalBE.interact(p, p.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);
                    // IMPORTANT: SurgicalBE base Block should return PASS in the use method when wielding syringe
                } else if (lookedAtState.getBlock() == Registration.SURGERY_BED.get()) {
                    BlockPos centerPos = Registration.SURGERY_BED.get().findCenter(pos, lookedAtState);
                    if (level.getBlockEntity(centerPos) instanceof SurgicalBE surgicalBE) {
                        surgicalBE.interact(p, p.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    //
    //@Override
    //public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
    //    return true;
    //}
//
    //@Override
    //public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
    //    super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    //}

    //@Override
    //public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
    //    return true;
    //}
}
