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

public class MinionDefendMasterTargetGoal<T extends Mob & PlayerMinion> extends TargetGoal {

    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private final T e;
    private int timestamp;

    public MinionDefendMasterTargetGoal(T mob, boolean pMustSee) {
        super(mob, pMustSee);
        this.e = mob;
    }

    @Override
    public boolean canUse() {
        ServerPlayer master = e.getMaster();
        if (master == null) {
            int i = this.mob.getLastHurtByMobTimestamp();
            LivingEntity livingentity = this.mob.getLastHurtByMob();
            if (i != this.timestamp && livingentity != null) {
                if (livingentity.getType() == EntityType.PLAYER && this.mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                    return false;
                } else {
                    if (livingentity instanceof PlayerMinion minion && Objects.equals(minion.getMasterID(), e.getMasterID())) {
                        return false;
                    }
                    return this.canAttack(livingentity, HURT_BY_TARGETING);
                }
            }
        } else {
            int i = master.getLastHurtByMobTimestamp();
            LivingEntity livingentity = master.getLastHurtByMob();
            if (i != this.timestamp && livingentity != null) {
                if (livingentity.getType() == EntityType.PLAYER && this.mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                    return false;
                } else {
                    if (livingentity == master || (livingentity instanceof PlayerMinion minion && Objects.equals(minion.getMasterID(), e.getMasterID()))) {
                        return false;
                    }
                    return this.canAttack(livingentity, HURT_BY_TARGETING);
                }
            }
        }
        return false;
    }

    public void start() {
        ServerPlayer master = e.getMaster();
        if (master == null) {
            this.mob.setTarget(this.mob.getLastHurtByMob());
            this.timestamp = this.mob.getLastHurtByMobTimestamp();
        } else {
            this.mob.setTarget(master.getLastHurtByMob());
            this.timestamp = master.getLastHurtByMobTimestamp();
        }
        this.targetMob = this.mob.getTarget();
        this.unseenMemoryTicks = 300;

        super.start();
    }
}
