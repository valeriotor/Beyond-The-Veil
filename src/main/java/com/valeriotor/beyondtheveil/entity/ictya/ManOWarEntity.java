package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.ai.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entity.ai.attacks.AttackList;
import com.valeriotor.beyondtheveil.entity.ai.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entity.ai.goals.TelegraphedAttackGoal;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ManOWarEntity extends IctyaEntity {

    public ManOWarEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(2, new ManOWarAttackGoal(this));

    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5);
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.MEDIUM;
    }

    @Override
    public double getFoodValue() {
        return 150;
    }

    @Override
    public double getMaxFood() {
        return 250;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 2.4;
    }

    private static class ManOWarAttackGoal extends Goal {

        private final ManOWarEntity mob;
        private int counter;

        public ManOWarAttackGoal(ManOWarEntity mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
            }
        }

        @Override
        public void start() {
            mob.setAggressive(true);
        }

        @Override
        public void stop() {
            mob.setAggressive(false);
        }

        @Override
        public void tick() {
            super.tick();
            counter++;
            if (counter % 40 == 0) {
                JellyEntity jelly = new JellyEntity(BTVEntities.JELLY.get(), mob.level());
                jelly.setPos(mob.position());
                jelly.setMaster(mob);
                mob.level().addFreshEntity(jelly);
            }
        }
    }


}
