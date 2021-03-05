package com.valeriotor.beyondtheveil.entities.bosses;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIProtectMaster;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.AI.AITelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.entities.IDamageCapper;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityDeepOneBrute extends EntityArenaBoss implements IDamageCapper, IAnimatedAttacker {
    private Animation attackAnimation;

    public EntityDeepOneBrute(World world) {
        this(world, null);
    }

    public EntityDeepOneBrute(World worldIn, EntityPlayer adversary) {
        super(worldIn, adversary);
        setSize(1.8F, 2);
    }

    @Override
    public float getMaxDamage() {
        return 10;
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
        } else {
            if(this.getHealth() < this.getMaxHealth() && (this.ticksExisted & 7) == 0) {
                if(this.getHealth() < this.getMaxHealth() / 2)
                    this.heal(rand.nextInt(2)+1);
                else
                    this.heal(1);
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0.1D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);

        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8D);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(2, new AITelegraphedAttack(this, 1.5D, true, ATTACK_LIST));
        this.tasks.addTask(6, new EntityDeepOne.DeepOneWander(this, 1.0D));
        this.targetTasks.addTask(1, new AIProtectMaster(this));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
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
        AttackArea leftSwingArea = AttackArea.getConeAttack(3.8, 90, 30);
        AttackArea rightSwingArea = AttackArea.getConeAttack(3.8, 30, 90);
        AttackArea leftSwingFollowupArea = AttackArea.getConeAttack(3.5, 90, 30);
        AttackArea rightSwingFollowupArea = AttackArea.getConeAttack(3.5, 30, 90);
        AttackArea smashArea = AttackArea.getCircleAttack(5);
        AttackArea roarArea = AttackArea.getCircleAttack(7);
        TelegraphedAttackTemplate leftFollowupSwing = new TelegraphedAttackTemplate(AnimationRegistry.deep_one_brute_left_followup_swing, 22, 6, 28, leftSwingFollowupArea, 5.2, 2.5);
        TelegraphedAttackTemplate rightFollowupSwing = new TelegraphedAttackTemplate(AnimationRegistry.deep_one_brute_right_followup_swing, 22, 6, 28, rightSwingFollowupArea, 5.2, 2.5);
        TelegraphedAttackTemplate roarFollowupSwing = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_brute_roar_followup, 23, 7, 1, roarArea, 12.2)
                .setKnockback(4.5)
                .setAttackSound(BTVSounds.deep_one_brute_roar)
                .build();

        TelegraphedAttackTemplate leftSwing = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_brute_left_swing, 25, 9, 30, leftSwingArea, 4.2)
                .setKnockback(2.5)
                .addFollowup(rightFollowupSwing, 10)
                .setFollowupTime(15)
                .build();

        TelegraphedAttackTemplate rightSwing = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_brute_right_swing, 25, 9, 30, rightSwingArea, 4.2)
                .setKnockback(2.5)
                .addFollowup(leftFollowupSwing, 10)
                .setFollowupTime(15)
                .build();

        TelegraphedAttackTemplateBuilder smashBuilder = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_brute_smash, 22, 10, 20, smashArea, 4.5)
                .setKnockback(4.5)
                .setAttackSound(BTVSounds.deep_one_brute_smash)
                .addFollowup(roarFollowupSwing, 7)
                .setNoFollowupAttackWeight(10)
                .setFollowupTime(12);
        addSmashParticles(smashBuilder);

        TelegraphedAttackTemplate smash = smashBuilder.build();

        attacks.addAttack(leftSwing, 10);
        attacks.addAttack(rightSwing, 10);
        attacks.addAttack(smash, 6);
        ATTACK_LIST = AttackList.immutableAttackListOf(attacks);
    }

    private static void addSmashParticles(TelegraphedAttackTemplateBuilder smash) {
        final double y = 0;
        for(int i = 0; i < 5; i++) {
            final double radius = i * 9.0 / 5;
            for(int j = 0; j < 4 + i; j++) {
                double x = Math.sin(2*Math.PI*j/(4+i))*radius;
                double z = Math.cos(2*Math.PI*j/(4+i))*radius;
                smash.addParticle(EnumParticleTypes.SWEEP_ATTACK, x, y, z, 1, 0, 0, 0, 1, 1);
            }
        }
    }

}
