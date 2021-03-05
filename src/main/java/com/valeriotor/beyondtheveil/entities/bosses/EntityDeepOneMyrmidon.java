package com.valeriotor.beyondtheveil.entities.bosses;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIProtectMaster;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.AI.AITelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.entities.IDamageCapper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDeepOneMyrmidon extends EntityArenaBoss implements IDamageCapper, IAnimatedAttacker {
    private Animation attackAnimation;
    public EntityDeepOneMyrmidon(World worldIn) {
        this(worldIn, null);
    }

    public EntityDeepOneMyrmidon(World worldIn, EntityPlayer adversary) {
        super(worldIn, adversary);
        ignoreFrustumCheck = true;
        setSize(1.4F, 3.5F);
    }


    @Override
    public float getMaxDamage() {
        return 10;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250);
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
        this.tasks.addTask(2, new AITelegraphedAttack(this, 2.5D, true, ATTACK_LIST));
        this.tasks.addTask(6, new EntityDeepOne.DeepOneWander(this, 1.0D));
        this.targetTasks.addTask(1, new AIProtectMaster(this));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(world.isRemote) {
            if(attackAnimation != null) {
                attackAnimation.update();
                if(attackAnimation.isDone()) {
                    attackAnimation = null;
                }
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
        AttackArea spearSwingArea = AttackArea.getConeAttack(5, 25, 25);
        AttackArea swordSwingArea = AttackArea.getConeAttack(3.5, 35, 70);

        TelegraphedAttackTemplate spearSwing = new TelegraphedAttackTemplate(AnimationRegistry.deep_one_myrmidon_spear_swing, 25, 8, 15, spearSwingArea, 5, 1.2);
        TelegraphedAttackTemplate swordSwing = new TelegraphedAttackTemplate(AnimationRegistry.deep_one_myrmidon_sword_swing, 25, 8, 15, swordSwingArea, 3, 1.5);

        attacks.addAttack(spearSwing, 8);
        attacks.addAttack(swordSwing, 8);

        ATTACK_LIST = AttackList.immutableAttackListOf(attacks);
    }

}
