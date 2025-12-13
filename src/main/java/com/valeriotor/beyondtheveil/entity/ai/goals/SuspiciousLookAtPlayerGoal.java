package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.entity.Suspicious;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class SuspiciousLookAtPlayerGoal<T extends Mob & Suspicious> extends LookAtPlayerGoal {

    private final T pMob;

    public SuspiciousLookAtPlayerGoal(T pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        super(pMob, pLookAtType, pLookDistance, 0.1F);
        this.pMob = pMob;
    }

    @Override
    public void start() {
        super.start();
        pMob.setSuspiciousLook(true);
    }

    @Override
    public void stop() {
        super.stop();
        pMob.setSuspiciousLook(false);
    }
}
