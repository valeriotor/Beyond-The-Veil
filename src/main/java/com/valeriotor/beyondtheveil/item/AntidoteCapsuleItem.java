package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AntidoteCapsuleItem extends Item {

    public AntidoteCapsuleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer instanceof ServerPlayer sp && !pPlayer.hasEffect(BTVEffects.IMMUNITY.get())) {
            ItemStack stack = sp.getItemInHand(pUsedHand);
            if (stack.getItem() == this) {
                applyCapsule(sp, stack);
                return InteractionResultHolder.consume(stack);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static void applyCapsule(ServerPlayer sp, ItemStack stack) {
        stack.shrink(1);
        sp.addEffect(new MobEffectInstance(BTVEffects.IMMUNITY.get(), 40 * 20));
        sp.level().playSound(null, sp.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS);
        if (!DataUtil.getBoolean(sp, PlayerDataLib.used_antidote.name())) {
            DataUtil.setBooleanOnServerAndSync(sp, PlayerDataLib.used_antidote.name(), true, false);
        }
    }
}
