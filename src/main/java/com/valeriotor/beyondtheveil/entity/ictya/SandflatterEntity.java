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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SandflatterEntity extends IctyaEntity implements AnimatedEntity {

    private static final EntityDataAccessor<Boolean> AMBUSHING = SynchedEntityData.defineId(SandflatterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_REPOSITIONED = SynchedEntityData.defineId(SandflatterEntity.class, EntityDataSerializers.BOOLEAN);
    private Animation attackAnimation;
    private boolean mustBeRemoved;
    private boolean mustReposition = true;

    public SandflatterEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        //super.registerGoals();

        //this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IctyaEntity.class, 6.0F, 1.2F, 1.2F, this::shouldFlee));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            protected boolean canAttack(@Nullable LivingEntity pPotentialTarget, @NotNull TargetingConditions pTargetPredicate) {
                return super.canAttack(pPotentialTarget, pTargetPredicate) && shouldDefend(pPotentialTarget);
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, false, false, this::shouldAttack));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IctyaEntity.class, 10, false, false, this::shouldAttack));
        this.goalSelector.addGoal(0, new SandflatterAttackGoal(this, 2.8D, true, initAttackList(), 0));
        this.goalSelector.addGoal(2, new TelegraphedAttackGoal<>(this, 1.8D, true, initAttackList(), 0));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AMBUSHING, true);
        this.entityData.define(HAS_REPOSITIONED, false);
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
    protected boolean shouldAttack(LivingEntity attacked) {
        if (attacked instanceof Player) {
            return true;
        }
        return super.shouldAttack(attacked) && !isAmbushing();
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
        } else {
            if (!hasRepositioned() && tickCount > 5) {
                entityData.set(HAS_REPOSITIONED, true);
            }
        }
    }

    public boolean hasRepositioned() {
        return entityData.get(HAS_REPOSITIONED);
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

    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("ambushing", entityData.get(AMBUSHING));
        pCompound.putBoolean("has_repositioned", entityData.get(HAS_REPOSITIONED));
        pCompound.putBoolean("mustReposition", mustReposition);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(AMBUSHING, pCompound.contains("ambushing") && pCompound.getBoolean("ambushing"));
        entityData.set(HAS_REPOSITIONED, pCompound.contains("has_repositioned") && pCompound.getBoolean("has_repositioned"));
        mustReposition = pCompound.contains("mustReposition") && pCompound.getBoolean("mustReposition");
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
        if (level().dimension() == BTVDimensions.ARCHE_LEVEL && !level().isClientSide && mustReposition) {
            mustReposition = false;
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
                            boolean blockUp = block2 != Blocks.WATER && block2 != Registration.BLACK_KELP.get() && block2 != Registration.ALGAE_BLOCK.get();
                            if (block1 != Registration.DARK_SAND.get() || blockUp && Math.abs(j) <= 1 && Math.abs(k) <= 1) {
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
        AttackArea clawArea = AttackArea.getConeAttack(6, 40, 40);

        TelegraphedAttackTemplate ambushAttack = new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(AnimationRegistry.sandflatter_ambush, 29, 14, 40, ambushArea, 3)
                .setPredicate((sandflatter, unused) -> ((SandflatterEntity) sandflatter).isAmbushing())
                .addPostAttackEffect(sandflatter -> ((SandflatterEntity) sandflatter).stopAmbushing())
                //.addPostHitEffect((unused, target) -> target.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 30, 3))) //TODO maybe use setHealth?
                .build();

        TelegraphedAttackTemplate clawAttack = new TelegraphedAttackTemplate.TelegraphedAttackTemplateBuilder(AnimationRegistry.sandflatter_claw, 12, 6, 20, clawArea, 7)
                .setPredicate((sandflatter, unused) -> !((SandflatterEntity) sandflatter).isAmbushing())
                .build();


        attacks.addAttack(ambushAttack, 10);
        attacks.addAttack(clawAttack, 10);

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
