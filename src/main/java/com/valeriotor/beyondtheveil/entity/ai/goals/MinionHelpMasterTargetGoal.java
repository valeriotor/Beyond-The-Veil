package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;

import java.util.Objects;

public class MinionHelpMasterTargetGoal<T extends Mob & PlayerMinion> extends TargetGoal {

    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private final T e;
    private int timestamp;
    private LivingEntity masterLastHurt;

    public MinionHelpMasterTargetGoal(T mob, boolean pMustSee) {
        super(mob, pMustSee);
        this.e = mob;
    }

    @Override
    public boolean canUse() {
        if (true) {
            ServerPlayer master = e.getMaster();
            if (master == null) {
                return false;
            } else {
                if (master.getLastHurtMob() == master || (master.getLastHurtMob() instanceof PlayerMinion minion && Objects.equals(minion.getMasterID(), e.getMasterID()))) {
                    return false;
                }
                this.masterLastHurt = master.getLastHurtMob();
                int i = master.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.masterLastHurt, TargetingConditions.DEFAULT);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.masterLastHurt);
        ServerPlayer master = e.getMaster();
        if (master != null) {
            this.timestamp = master.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
