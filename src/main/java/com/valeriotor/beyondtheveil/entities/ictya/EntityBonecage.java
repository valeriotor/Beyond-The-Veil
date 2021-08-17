package com.valeriotor.beyondtheveil.entities.ictya;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AITelegraphedAttack;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entities.AI.attacks.AttackList;
import com.valeriotor.beyondtheveil.entities.AI.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityBonecage extends EntityIctya implements IAnimatedAttacker {
    private Animation attackAnimation;

    public EntityBonecage(World worldIn) {
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
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
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
        return 400;
    }

    @Override
    public double getMaxFood() {
        return 400;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 3.2;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (world.isRemote) {
            if (attackAnimation != null) {
                attackAnimation.update();
                if (attackAnimation.isDone())
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
        AttackArea crunchArea = AttackArea.getConeAttack(11, 80, 80);

        TelegraphedAttackTemplate crunchAttack = new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(AnimationRegistry.bonecage_crunch, 34, 20, 35, crunchArea, 12)
                .addPostHitEffect((still, moving) -> MathHelperBTV.moveTowardsEntity(still, moving, 2)) //TODO check if it works in multiplayer
                .addPostHitEffect(EntityBonecage::extraDamageIfPlayer)
                .build();

        attacks.addAttack(crunchAttack, 5);

        ATTACK_LIST = AttackList.immutableAttackListOf(attacks);

    }

    private static void extraDamageIfPlayer(EntityLiving unused, EntityLivingBase target) {
        if (target instanceof EntityPlayer)
            target.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 10, 3));
    }

}
