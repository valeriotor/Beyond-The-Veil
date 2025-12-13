package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.entity.ai.control.SuspiciousBodyRotationControl;
import com.valeriotor.beyondtheveil.entity.ai.goals.SuspiciousLookAtPlayerGoal;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BloodZombieEntity extends Monster implements Suspicious, PlayerGuardian, AnimatedEntity {

    private static final EntityDataAccessor<Boolean> SUSPICIOUS_LOOK = SynchedEntityData.defineId(BloodZombieEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_TARGETING = SynchedEntityData.defineId(BloodZombieEntity.class, EntityDataSerializers.BOOLEAN);
    private UUID master;
    private Animation jawAnimation;

    public BloodZombieEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new SuspiciousLookAtPlayerGoal<>(this, Player.class, 12));
        //this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8D, false));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, BloodPoolEntityType.BLOOD_ZOMBIE.getMaxHealth())
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 2);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SUSPICIOUS_LOOK, false);
        this.entityData.define(DATA_TARGETING, false);
    }

    public boolean isTargeting() {
        return entityData.get(DATA_TARGETING);
    }

    @Override
    public boolean getSuspiciousLook() {
        return entityData.get(SUSPICIOUS_LOOK);
    }

    @Override
    public void setSuspiciousLook(boolean value) {
        entityData.set(SUSPICIOUS_LOOK, value);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new SuspiciousBodyRotationControl<>(this);
    }

    @Override
    public UUID getMasterID() {
        return master;
    }

    @Override
    public void setMasterID(UUID uuid) {
        master = uuid;
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        if (channel == 0) {
            jawAnimation = new Animation(animationTemplate);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (master != null) {
            pCompound.putString("master", master.toString());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("master")) {
            master = UUID.fromString(pCompound.getString("master"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (jawAnimation != null) {
                jawAnimation.update();
                if (jawAnimation.isDone()) {
                    jawAnimation = null;
                }
            }
        } else {
            if (getTarget() == null) {
                entityData.set(DATA_TARGETING, false);
            } else if(getNavigation().isInProgress()){
                entityData.set(DATA_TARGETING, true);
            }
        }
    }

    public Animation getJawAnimation() {
        return jawAnimation;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return random.nextInt(3) == 0 ? BTVSounds.BLOOD_ZOMBIE_LONG.get() : BTVSounds.BLOOD_ZOMBIE_SHORT.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    @Override
    public void playSound(@NotNull SoundEvent pSound, float pVolume, float pPitch) {
        if (!this.isSilent() && !level().isClientSide) {
            this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), pSound, this.getSoundSource(), pVolume, pPitch);
            if (pSound == BTVSounds.BLOOD_ZOMBIE_LONG.get()) {
                sendAnimation(AnimationRegistry.blood_zombie_open_jaw_long, 0);
            } else if (pSound == BTVSounds.BLOOD_ZOMBIE_SHORT.get()) {
                sendAnimation(AnimationRegistry.blood_zombie_open_jaw_short, 0);
            }
        }
    }
}
