package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SlugItem extends Item {

    public SlugItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof ServerPlayer sp) {
            if (ResearchUtil.getResearchStage(sp, "CUSTOMS") == 0) {
                DataUtil.setBooleanOnServerAndSync(sp, PlayerDataLib.ATE_SLUG, true, false);
            }
            if (true) { // TODO
                sp.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10));
                sp.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 10, 1));
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
