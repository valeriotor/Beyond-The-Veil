package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DelicacyItem extends Item {
    public DelicacyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof ServerPlayer sp && !DataUtil.getBoolean(sp, PlayerDataLib.ATE_DELICACY)) {
            DataUtil.setBooleanOnServerAndSync(sp, PlayerDataLib.ATE_DELICACY, true, false);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
