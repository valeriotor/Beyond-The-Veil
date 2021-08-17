package com.valeriotor.beyondtheveil.entities.bosses;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIProtectMaster;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.AI.AITelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate.AttackSupplier;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder;
import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.entities.IDamageCapper;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityDeepOneMyrmidon extends EntityArenaBoss implements IDamageCapper, IAnimatedAttacker {
    private Animation attackAnimation;
    private AITelegraphedAttack attackAI;

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
        attackAI = new AITelegraphedAttack(this, 1.5D, true, ATTACK_LIST);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(2, attackAI);
        this.tasks.addTask(6, new EntityDeepOne.DeepOneWander(this, 1.0D));
        this.targetTasks.addTask(1, new AIProtectMaster(this));
        this.targetTasks.addTask(2, new AIRevenge(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(world.isRemote || attackAI == null || source.getTrueSource() == null) return super.attackEntityFrom(source, amount);
        double attackRotation = (MathHelperBTV.angleBetween(this, source.getTrueSource()) + 180) % 360;
        double thisRotation = (renderYawOffset + 180 ) % 360;
        double relativeRotation = attackRotation - thisRotation;
        if(relativeRotation > 180) relativeRotation -= 180;
        double upperBound = attackAI != null && attackAI.isAttacking() ? -10 : 30;
        if(relativeRotation < upperBound && relativeRotation > -80) {
            world.playSound(null, posX, posY, posZ, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.HOSTILE, 1, 1);
            return true;
        }
        return super.attackEntityFrom(source, amount);
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
    public void setAttackAnimation(int id) {
        attackAnimation = new Animation(AnimationRegistry.getAnimationFromId(id));
    }

    @Override
    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    private static final AttackList ATTACK_LIST;

    static {
        //vecTest();
        AttackList attacks = new AttackList();
        AttackArea spearImpaleArea = AttackArea.getShiftedConeAttack(5.5, 3.5, 2);
        AttackArea swordSwingArea = AttackArea.getConeAttack(3.8, 35, 70);
        AttackArea swordImpaleArea = AttackArea.getShiftedConeAttack(4.2, 4, 2.3);
        AttackArea spearRightSwingArea = AttackArea.getConeAttack(5, 30, 180);
        AttackArea swordBackSwingArea = AttackArea.getConeAttack(4.2, 30, 180);

        TelegraphedAttackTemplate spearImpaleFollowupSpearRightSwing = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_spear_impale_followup_spear_right_swing,
                28, 15, 30, spearRightSwingArea, 6)
                .setPredicate((source, target) -> MathHelperBTV.isEntityWithinAngleOfEntity(source, target, source.renderYawOffset, 20, 180))
                .setKnockback(3.5)
                .build();

        TelegraphedAttackTemplate swordImpaleFollowupSwordBackSwing = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_sword_impale_followup_sword_back_swing,
                26, 14, 30, swordBackSwingArea, 5.5)
                .setPredicate((source, target) -> MathHelperBTV.isEntityWithinAngleOfEntity(source, target, source.renderYawOffset, 20, 180))
                .setKnockback(3.5)
                .build();

        AttackSupplier spearImpaleFollowupSpearImpaleSupplier = new AttackSupplier();
        AttackSupplier swordImpaleFollowupSpearImpaleSupplier = new AttackSupplier();

        TelegraphedAttackTemplate spearImpaleFollowupSwordImpale = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_spear_impale_followup_sword_impale,
                24, 10, 20, swordImpaleArea, 6)
                .addFollowup(swordImpaleFollowupSpearImpaleSupplier::getAttack, 9)
                .addFollowup(swordImpaleFollowupSwordBackSwing, 25)
                .setNoFollowupAttackWeight(3)
                .setFollowupTime(12)
                .setKnockback(1.2)
                .build();

        TelegraphedAttackTemplate spearImpaleFollowupSpearImpale = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_spear_impale_followup_spear_impale,
                24, 8, 20, spearImpaleArea, 10)
                .addFollowup(spearImpaleFollowupSpearImpaleSupplier::getAttack, 8)
                .addFollowup(spearImpaleFollowupSpearRightSwing, 25)
                .addFollowup(spearImpaleFollowupSwordImpale, 12)
                .setNoFollowupAttackWeight(5)
                .setFollowupTime(10)
                .setKnockback(0.4)
                .setInitialRotationWeight(2)
                .build();

        TelegraphedAttackTemplate swordImpaleFollowupSpearImpale = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_sword_impale_followup_spear_impale,
                26, 10, 20, spearImpaleArea, 10)
                .addFollowup(spearImpaleFollowupSpearImpaleSupplier::getAttack, 8)
                .addFollowup(spearImpaleFollowupSpearRightSwing, 25)
                .addFollowup(spearImpaleFollowupSwordImpale, 12)
                .setNoFollowupAttackWeight(5)
                .setFollowupTime(12)
                .setKnockback(0.4)
                .setInitialRotationWeight(2)
                .build();

        spearImpaleFollowupSpearImpaleSupplier.setAttack(spearImpaleFollowupSpearImpale);
        swordImpaleFollowupSpearImpaleSupplier.setAttack(swordImpaleFollowupSpearImpale);

        TelegraphedAttackTemplate spearImpale = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_spear_impale, 24, 8, 20, spearImpaleArea, 5)
                .addFollowup(spearImpaleFollowupSpearImpale, 10)
                .addFollowup(spearImpaleFollowupSwordImpale, 16)
                .setNoFollowupAttackWeight(3)
                .setFollowupTime(14)
                .setKnockback(0.4)
                .build();

        TelegraphedAttackTemplate swordSwingFollowupSwordImpale = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_sword_swing_followup_sword_impale,
                24, 10, 20, swordImpaleArea, 8)
                .addFollowup(swordImpaleFollowupSpearImpale, 12)
                .addFollowup(swordImpaleFollowupSwordBackSwing, 25)
                .setNoFollowupAttackWeight(5)
                .setFollowupTime(12)
                .build();

        TelegraphedAttackTemplate swordSwing = new TelegraphedAttackTemplateBuilder(AnimationRegistry.deep_one_myrmidon_sword_swing, 24, 8, 20, swordSwingArea, 3)
                .addFollowup(swordSwingFollowupSwordImpale, 10)
                .setNoFollowupAttackWeight(1)
                .setFollowupTime(14)
                .setInitialRotationWeight(1)
                .build();

        attacks.addAttack(spearImpale, 3);
        attacks.addAttack(swordSwing, 8);

        ATTACK_LIST = AttackList.immutableAttackListOf(attacks);
    }

}
