package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MemoryPhialItem extends Item {

    public MemoryPhialItem() {
        super(new Item.Properties().stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));

        pTooltipComponents.add(m == null ? Component.translatable("tooltip.memory_phial.empty") : Component.translatable("tooltip.memory_phial.stored", Component.translatable(m.getLocalizationKey())));
    }

    public ItemStack finishUsingItem(ItemStack stack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        CompoundTag tag = stack.getOrCreateTag();
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (!pLevel.isClientSide && player != null) {
            Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));
            if (m != null) {
                DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.DRANK_MEMORY.apply(m), true, false);
                DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.DRANK_ANY_MEMORY, true, false);
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Registration.MEMORY_PHIAL.get());
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Registration.MEMORY_PHIAL.get()));
            }
        }

        pEntityLiving.gameEvent(GameEvent.DRINK);
        return stack;
    }

    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        CompoundTag tag = stack.getOrCreateTag();
        Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));
        if (m != null) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
        }
        return super.use(pLevel, pPlayer, pHand);
    }

    public static class MemoryPhialColor implements ItemColor {

        @Override
        public int getColor(ItemStack pStack, int pTintIndex) {
            CompoundTag tag = pStack.getOrCreateTag();
            Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));
            if (m != null) return m.getColor();
            return -1;
        }
    }


}
