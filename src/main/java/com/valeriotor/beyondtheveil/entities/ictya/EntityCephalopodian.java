package com.valeriotor.beyondtheveil.entities.ictya;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AITelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityCephalopodian extends EntityIctya implements IAnimatedAttacker {
    private Animation attackAnimation;

    public EntityCephalopodian(World worldIn) {
        super(worldIn);
        setSize(5F, 2.5F);
        ignoreFrustumCheck = true;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(2, new AITelegraphedAttack(this, 1.5D, true, ATTACK_LIST));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWander(this, 0.4D, 100));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(320.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(1D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12D);
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

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(world.isRemote) {
            if(attackAnimation != null) {
                attackAnimation.update();
                if(attackAnimation.isDone())
                    attackAnimation = null;
            }
        }
    }

    @Override
    public void setAttackAnimation(int id) {
        attackAnimation = new Animation(AnimationRegistry.getAnimationFromId(id));
    }

    @Override
    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    private static final AttackList ATTACK_LIST;

    static {
        AttackList attacks = new AttackList();
        AttackArea crunchArea = AttackArea.getConeAttack(6.5, 40, 40);
        AttackArea tentacleArea = AttackArea.getCircleAttack(5.5);

        TelegraphedAttackTemplate crunchAttack = new TelegraphedAttackTemplate(AnimationRegistry.cephalopodian_crunch, 25, 10, 30, crunchArea, 9, 2);
        TelegraphedAttackTemplate tentacleAttack = new TelegraphedAttackTemplate(AnimationRegistry.cephalopodian_tentacles, 13, 6, 1, tentacleArea, 3.5, 5);

        attacks.addAttack(crunchAttack, 5);
        attacks.addAttack(tentacleAttack, 15);

        ATTACK_LIST = AttackList.immutableAttackListOf(attacks);

    }
}
