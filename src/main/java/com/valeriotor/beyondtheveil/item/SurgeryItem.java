package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.registries.RegistryObject;

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

    public SurgeryItemType getType() {
        return type;
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
                surgeryItem.resetCounterAndStopSound(p);
            }
        }
        for (SurgeryItem surgeryItem : REGISTRY) {
            if (!surgeryItem.equals(usedItem)) { // that one is fully handled by the if statement
                surgeryItem.resetCounterAndStopSound(p);
            }
        }
    }

    protected void interactWithBE(Player p, Level level, BlockPos pos, BlockState lookedAtState, BlockEntity blockEntity, BlockHitResult bhr) {
        boolean usingItem = false;
        if (blockEntity instanceof SurgicalBE surgicalBE) {
            usingItem = surgicalBE.interact(p, p.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);
        } else if (lookedAtState.getBlock() == Registration.SURGERY_BED.get()) {
            BlockPos centerPos = Registration.SURGERY_BED.get().findCenter(pos, lookedAtState);
            if (level.getBlockEntity(centerPos) instanceof SurgicalBE surgicalBE) {
                usingItem = surgicalBE.interact(p, p.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);
            }
        }
        if (usingItem) {
            increaseCounterAndStartSound(p, pos);
        } else {
            resetCounterAndStopSound(p);
        }
    }

    private void increaseCounterAndStartSound(Player p, BlockPos bePos) {
        Integer newValue = DataUtil.incrementOrSetInteger(p, type.name(), 1, 1, true);
        if (newValue != null && newValue == 1) {
            DataUtil.setLong(p, type.name() + "_pos", bePos.asLong(), true);
            Messages.sendToTrackingAndSelf(GenericToClientPacket.startSurgerySound(type, bePos), p);
        }
    }

    protected void resetCounterAndStopSound(Player p) {
        int prev = DataUtil.getOrSetInteger(p, type.name(), 0, true);
        if (prev > 0) {
            DataUtil.setInt(p, type.name(), 0, true);
            long pos = DataUtil.getLong(p, type.name() + "_pos");
            Messages.sendToTrackingAndSelf(GenericToClientPacket.stopSurgerySound(BlockPos.of(pos)), p);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    public enum SurgeryItemType {
        SCALPEL(BTVSounds.INCISING), FORCEPS(null), TONGS(null), SYRINGE(null);

        private final RegistryObject<SoundEvent> sound;

        SurgeryItemType(RegistryObject<SoundEvent> sound) {
            this.sound = sound;
        }

        public RegistryObject<SoundEvent> getSound() {
            return sound;
        }
    }


}
