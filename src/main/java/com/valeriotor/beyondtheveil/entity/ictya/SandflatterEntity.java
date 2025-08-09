package com.valeriotor.beyondtheveil.entity.ictya;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.AnimatedEntity;
import com.valeriotor.beyondtheveil.entity.ai.attacks.AttackArea;
import com.valeriotor.beyondtheveil.entity.ai.attacks.AttackList;
import com.valeriotor.beyondtheveil.entity.ai.attacks.TelegraphedAttackTemplate;
import com.valeriotor.beyondtheveil.entity.ai.goals.TelegraphedAttackGoal;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class SandflatterEntity extends IctyaEntity implements AnimatedEntity {

    private static final EntityDataAccessor<Boolean> AMBUSHING = SynchedEntityData.defineId(SandflatterEntity.class, EntityDataSerializers.BOOLEAN);
    private Animation attackAnimation;
    private boolean mustBeRemoved;

    public SandflatterEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new SandflatterAttackGoal(this, 1.8D, true, initAttackList(), 0));
        this.goalSelector.addGoal(2, new TelegraphedAttackGoal<>(this, 1.8D, true, initAttackList(), 0));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AMBUSHING, true);
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
    public IctyaSize getSize() {
        return IctyaSize.LARGE;
    }

    @Override
    public double getFoodValue() {
        return 300;
    }

    @Override
    public double getMaxFood() {
        return 400;
    }

    @Override
    public double getFoodPer32Ticks() {
        return 0;
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        attackAnimation = new Animation(animationTemplate);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("ambushing", entityData.get(AMBUSHING));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ambushing") && pCompound.getBoolean("ambushing")) {
            entityData.set(AMBUSHING, true);
        } else {
            entityData.set(AMBUSHING, false);
        }
    }

    public boolean isAmbushing() {
        return entityData.get(AMBUSHING);
    }

    private void stopAmbushing() {
        entityData.set(AMBUSHING, false);
    }

    @Override
    public void onAddedToWorld() {
        /* TODO is onAddedToWorld also called on reload? We might want a onInitialSpawn
         */
        super.onAddedToWorld();
        if (level().dimension() == BTVDimensions.ARCHE_LEVEL && !level().isClientSide) {
            boolean success = false;
            int posX = getBlockX();
            int posY = getBlockY();
            int posZ = getBlockZ();
            for (int i = 0; i < getY() - 5; i++) {
                if (level().getBlockState(new BlockPos(posX, posY - i, posZ)).getBlock() == Registration.DARK_SAND.get()) {
                    boolean goodSpot = true;
                    for (int j = -3; j <= 3; j++) {
                        for (int k = -3; k <= 3; k++) {
                            Block block1 = level().getBlockState(new BlockPos(posX + j, posY - i, posZ + k)).getBlock();
                            Block block2 = level().getBlockState(new BlockPos(posX + j, posY - i + 1, posZ + k)).getBlock();
                            if (block1 != Registration.DARK_SAND.get() || (block2 != Blocks.WATER) && Math.abs(j) <= 1 && Math.abs(k) <= 1) {
                                goodSpot = false;
                                break;
                            }
                        }
                        if (!goodSpot) break;
                    }
                    if (!goodSpot) break;
                    setPos(new Vec3(posX, posY - i + 1, posZ));
                    //if (!level().getEntities().isEmpty()) {
                    //mustBeRemoved = true;
                    //break;
                    //}
                    success = true;
                    break;
                }
            }
            if (!success) {
                mustBeRemoved = true;
                discard(); // TODO shortcut
            }
        }
    }

    private AttackList initAttackList() {
        AttackList attacks = new AttackList();
        AttackArea ambushArea = AttackArea.getBoundingBoxAttack(-4, 0, -4, 4, 4.75, 4);
        AttackArea clawArea = AttackArea.getConeAttack(5, 30, 30);

        /*TelegraphedAttackTemplate ambushAttack = new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(AnimationRegistry.sandflatter_ambush, 29, 14, 40, ambushArea, 3)
                .setPredicate((sandflatter, unused) -> ((SandflatterEntity) sandflatter).isAmbushing())
                .addPostAttackEffect(sandflatter -> ((SandflatterEntity) sandflatter).stopAmbushing())
                //.addPostHitEffect((unused, target) -> target.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 30, 3))) //TODO maybe use setHealth?
                .build();

        TelegraphedAttackTemplate clawAttack = new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(AnimationRegistry.sandflatter_claw, 12, 6, 20, clawArea, 7)
                .setPredicate((sandflatter, unused) -> !((SandflatterEntity) sandflatter).isAmbushing())
                .build();


        attacks.addAttack(ambushAttack, 10);
        attacks.addAttack(clawAttack, 10);*/

        return AttackList.immutableAttackListOf(attacks);
    }

    private static class SandflatterAttackGoal extends TelegraphedAttackGoal<SandflatterEntity> {

        public SandflatterAttackGoal(SandflatterEntity attacker, double speedModifier, boolean followingTargetEvenIfNotSeen, AttackList attacks, int animationChannel) {
            super(attacker, speedModifier, followingTargetEvenIfNotSeen, attacks, animationChannel);
        }

        @Override
        public void start() {
        }

        @Override
        public void stop() {
            super.stop();
        }

        @Override
        public boolean canUse() {
            return super.canUse() && mob.isAmbushing();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && mob.isAmbushing();
        }

        @Override
        public void tick() {
            super.tick();
            mob.getNavigation().stop();
            mob.getLookControl().setLookAt(new Vec3(mob.getX(), mob.getY(), mob.getZ()));
        }
    }

}
