package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

/** If the minion has a master then it no longer responds to provocations. It also never attacks its master
 */
public class MinionHurtByTargetGoal<T extends PathfinderMob & PlayerMinion> extends HurtByTargetGoal {
    private final T pMob;

    public MinionHurtByTargetGoal(T pMob) {
        super(pMob);
        this.pMob = pMob;
    }

    @Override
    public boolean canUse() {
        if (pMob.getMaster() != null) {
            return false;
        }
        return super.canUse();
    }
}
