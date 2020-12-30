package com.valeriotor.beyondtheveil.entities;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIProtectMaster;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.AI.AITelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityDeepOneBrute extends EntityMob implements IDamageCapper, IAnimatedAttacker {
    private Animation attackAnimation;
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    public EntityDeepOneBrute(World worldIn) {
        super(worldIn);
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

    protected void initEntityAI() {
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(2, new AITelegraphedAttack(this, 1.5D, true, ATTACK_LIST));
        this.tasks.addTask(6, new EntityDeepOne.DeepOneWander(this, 1.0D));
        this.targetTasks.addTask(1, new AIProtectMaster(this));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true, false));
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    @Override
    public void setAttackAnimation(int id) {
        attackAnimation = new Animation(AnimationRegistry.getAnimationFromId(id));
    }

    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }


    private static final AttackList ATTACK_LIST;

    static {
        AttackList attacks = new AttackList();
        AttackArea leftSwingArea = AttackArea.getConeAttack(3.8, 90, 30);
        AttackArea rightSwingArea = AttackArea.getConeAttack(3.8, 30, 90);
        AttackArea leftSwingFollowupArea = AttackArea.getConeAttack(3.5, 90, 30);
        AttackArea rightSwingFollowupArea = AttackArea.getConeAttack(3.5, 30, 90);
        TelegraphedAttackTemplate leftFollowupSwing = new TelegraphedAttackTemplate(AnimationRegistry.deep_one_brute_left_followup_swing, 22, 6, 28, leftSwingFollowupArea, 5.2, 3);
        TelegraphedAttackTemplate rightFollowupSwing = new TelegraphedAttackTemplate(AnimationRegistry.deep_one_brute_right_followup_swing, 22, 6, 28, rightSwingFollowupArea, 5.2, 3);

        TelegraphedAttackTemplate leftSwing = new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_brute_left_swing, 25, 9, 30, leftSwingArea, 4.2)
                .setKnockback(3)
                .addFollowup(rightFollowupSwing, 10)
                .setFollowupTime(15)
                .build();

        TelegraphedAttackTemplate rightSwing = new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_brute_right_swing, 25, 9, 30, rightSwingArea, 4.2)
                .setKnockback(3)
                .addFollowup(leftFollowupSwing, 10)
                .setFollowupTime(15)
                .build();
        attacks.addAttack(leftSwing, 10);
        attacks.addAttack(rightSwing, 10);
        ATTACK_LIST = AttackList.immutableAttackListOf(attacks);
    }

}
