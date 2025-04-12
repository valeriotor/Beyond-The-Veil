package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.timers.BaptismTimer;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Iterator;

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
            if (checkBaptism(sp)) {
                pLevel.setBlock(sp.blockPosition().above(2), Blocks.ICE.defaultBlockState(), 3);
                sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> {
                    if (!c.hasTimer("baptism")) {
                        c.addTimer(new BaptismTimer(sp.getHealth()));
                    }
                });
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private static boolean checkBaptism(ServerPlayer sp) {
        if (ResearchUtil.getResearchStage(sp, "CUSTOMS") < 1 || DataUtil.getBoolean(sp, PlayerDataLib.BAPTIZED)) { // TODO change customs to baptism
            return false;
        }
        if (sp.level().getBlockState(sp.getOnPos()).canBeReplaced()) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            FluidState fluidState = sp.level().getFluidState(sp.blockPosition().above(i));
            if (fluidState != Fluids.WATER.getSource(false)) {
                return false;
            }
            Iterator<Direction> iterator = Direction.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext()) {
                Direction next = iterator.next();
                if (sp.level().getBlockState(sp.blockPosition().above(i).relative(next)).canBeReplaced()) {
                    return false;
                }
            }
        }
        return true;
    }
}
