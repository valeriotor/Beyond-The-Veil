package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.entity.ai.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entity.ai.attacks.AttackList;
import com.valeriotor.beyondtheveil.entity.ai.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entity.ai.goals.TelegraphedAttackGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.BusBuilder;

public class CephalopodianEntity extends IctyaEntity implements AnimatedEntity {
    private Animation attackAnimation;

    public CephalopodianEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TelegraphedAttackGoal<>(this, 1.8D, true, initAttackList(), 0));
        //this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8D, false));

    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 5);
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        attackAnimation = new Animation(animationTemplate);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (attackAnimation != null) {
                attackAnimation.update();
                if (attackAnimation.isDone()) {
                    attackAnimation = null;
                }
            }
        }
    }

    @Override
    public IctyaSize getSize() {
        return IctyaSize.LARGE;
    }

    @Override
    public double getFoodValue() {
        return 450;
    }

    @Override
    public double getMaxFood() {
        return 500;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 5.4;
    }

    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    private AttackList initAttackList() {
        AttackList attacks = new AttackList();
        AttackArea crunchArea = AttackArea.getConeAttack(7.5, 60, 60);
        AttackArea tentacleArea = AttackArea.getCircleAttack(5.5);

        TelegraphedAttackTemplate crunchAttack = TelegraphedAttackTemplate.of(AnimationRegistry.cephalopodian_crunch, 25, 10, 30, crunchArea, 9, 2);
        TelegraphedAttackTemplate tentacleAttack = TelegraphedAttackTemplate.of(AnimationRegistry.cephalopodian_tentacles, 13, 6, 1, tentacleArea, 3.5, 5);

        attacks.addAttack(crunchAttack, 5);
        attacks.addAttack(tentacleAttack, 15);

        return AttackList.immutableAttackListOf(attacks);
    }
}
