package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.surgery.OperationRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;

public class MixinUtils {

    public static void creeperFearConvalescents(Mob mob) {
        if (mob instanceof Creeper creeper) {
            mob.goalSelector.addGoal(3, new AvoidEntityGoal<>(creeper, LivingEntity.class, 6, 1.0D, 1.2D, l -> {
                if (l.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).isPresent()) {
                    if (l.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).resolve().get().getFlags().getOrDefault(OperationRegistry.INSERT_PERIOSTEUM_GROWTH_CHEST.getName(), 0) > 0) {
                        return true;
                    }
                }
                return false;
            }));
        }
    }

}
