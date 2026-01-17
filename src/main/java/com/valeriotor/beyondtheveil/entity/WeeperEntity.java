package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.client.render.PatientHolderType;
import com.valeriotor.beyondtheveil.entity.ai.goals.LivingAmmunitionGoal;
import com.valeriotor.beyondtheveil.entity.ai.goals.WeepGoal;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.BTVParticles;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.surgery.OperationRegistry;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffect;
import com.valeriotor.beyondtheveil.surgery.arsenal.Burst;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WeeperEntity extends PathfinderMob implements AnimatedEntity, AmmunitionEntity, PlayerMinion, SurgeryPatient, Weeping {

    private static final EntityDataAccessor<Integer> DATA_BLEEDING = SynchedEntityData.defineId(WeeperEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_TARGETING = SynchedEntityData.defineId(WeeperEntity.class, EntityDataSerializers.BOOLEAN);
    private Animation explodingAnimation;
    private Animation standUpAnimation;
    private boolean wasBleeding = false;
    private int attackTimer = -1;
    private int deathTimer = -1;
    private boolean surgeryPatient;
    private PatientStatus patientStatus;
    private boolean held;
    private BlockPos lacrymatoryPos;
    private UUID master;
    private int ticksToFletum = -1;
    private boolean inPod;


    public WeeperEntity(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        //this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D) {
            @Override
            public boolean canUse() {
                return super.canUse() && lacrymatoryPos == null && tickCount > 60;
            }
        });
        this.goalSelector.addGoal(0, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new LivingAmmunitionGoal<>(this, 1.8D, false));
        this.goalSelector.addGoal(3, new WeepGoal<>(this, 1));
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, BloodPoolEntityType.WEEPER.getMaxHealth())
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BLEEDING, -1);
        this.entityData.define(DATA_TARGETING, false);
    }

    @Override
    public List<GenericToClientPacket> getAnimationPackets() {
        List<GenericToClientPacket> animationPackets = new ArrayList<>();
        animationPackets.add(GenericToClientPacket.startAnimation(AnimationRegistry.weeper_explode, getId(), 0));
        return animationPackets;
    }

    @Override
    public void startBurst() {
        attackTimer = 30;
    }

    @Override
    public boolean bursting() {
        return attackTimer >= 0;
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        if (channel == 0) {
            explodingAnimation = new Animation(animationTemplate);
        } else if (channel == 1) {
            standUpAnimation = new Animation(animationTemplate);
        }
    }

    public Animation getExplodingAnimation() {
        return explodingAnimation;
    }

    public Animation getStandUpAnimation() {
        return standUpAnimation;
    }

    public void standUp() {
        LazyOptional<ConvalescentData> capability = getCapability(ConvalescentDataProvider.CONVALESCENT_DATA);
        boolean becomesFletum = capability.isPresent() && capability.resolve().get().getFlags().containsKey(OperationRegistry.SPINELESS);
        AnimationTemplate template = becomesFletum ? AnimationRegistry.weeper_get_up_spineless : AnimationRegistry.weeper_get_up;
        Messages.sendToTracking(GenericToClientPacket.startAnimation(template, getId(), 1), this);
        if (becomesFletum) {
            ticksToFletum = 94;
            goalSelector.removeAllGoals(t -> true);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (explodingAnimation != null) {
                if (!explodingAnimation.isDone()) {
                    explodingAnimation.update();
                } else {
                    explodingAnimation = null;
                }
            }
            if (standUpAnimation != null) {
                if (!standUpAnimation.isDone()) {
                    standUpAnimation.update();
                } else {
                    standUpAnimation = null;
                }
            }
            int bleeding = entityData.get(DATA_BLEEDING);
            if (bleeding >= 0) {
                double xComponent = -Math.sin(Math.toRadians(getYRot()));
                double zComponent = Math.cos(Math.toRadians(getYRot()));
                if (!wasBleeding) {
                    wasBleeding = true;
                    for (int i = 0; i < 30; i++) {
                        double direction = Math.random() * 2 * Math.PI;
                        double xSpeed = -Math.sin(direction);
                        double zSpeed = Math.cos(direction);
                        for (int j = 0; j < 5 * (1 + bleeding); j++) {
                            level().addAlwaysVisibleParticle(BTVParticles.TEARSPILL.get(), getX(), getY() + 1.5, getZ(), xSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()), 1.5, zSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()));
                            level().addAlwaysVisibleParticle(BTVParticles.TEARSPILL.get(), getX(), getY() + 1.5, getZ(), xSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()), 0, zSpeed * (1.5 + bleeding / 3D) * (1 + Math.random()));
                        }
                    }
                }
                //for (int i = 0; i < 2; i++) {
                //    level().addParticle(BTVParticles.TEARSPILL.get(), getX() + xComponent / 2, getY() + 1, getZ() + zComponent / 2, xComponent * (2 + Math.random()), 1.5, zComponent * (2 + Math.random()));
                //}

            }

        } else {
            if (ticksToFletum > 0) {
                ticksToFletum--;
                if (ticksToFletum == 0) {
                    for (int i = 0; i < 10; i++) {
                        ((ServerLevel) level()).sendParticles(BTVParticles.TEARSPILL.get(), getX(), getY() - 1 + 0.12 * i, getZ(), 5, 0, 0, 0, 1);
                    }
                    FletumEntity fletum = new FletumEntity(BTVEntities.FLETUM.get(), level());
                    fletum.setPos(position().add(0, 1.5, 0));
                    level().addFreshEntity(fletum);
                    fletum.setMasterID(master);
                    if (master != null) {
                        ServerPlayer player = getMaster();
                        if (player != null) {
                            DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.created_fletum.name(), true, false);
                        }
                    }
                    discard();
                    level().playSound(null, getX(), getY(), getZ(), BTVSounds.HEAD_EXPLODE.get(), SoundSource.NEUTRAL, 1, 1);
                    return;
                }
            }
            if (getTarget() == null) {
                entityData.set(DATA_TARGETING, false);
            } else if(getNavigation().isInProgress()){
                entityData.set(DATA_TARGETING, true);
            }
            if (deathTimer >= 0) {
                getNavigation().stop();
                //yBodyRot = fixedLookAngle;
                LivingEntity target = getTarget();
                if (target != null) {
                    getLookControl().setLookAt(target);
                }
                deathTimer--;
                if (deathTimer == 0) {
                    remove(RemovalReason.KILLED);
                }
            } else if (attackTimer >= 0) {
                getNavigation().stop();
                //yBodyRot = fixedLookAngle;
                LivingEntity target = getTarget();
                if (target != null) {
                    getLookControl().setLookAt(target);
                }
                attackTimer--;
                if (attackTimer == 0) {
                    TriggerData data = getTriggerData();
                    Burst burst = data.getBurst();
                    List<ArsenalEffect> effects = data.getEffects();
                    if (burst != null) {
                        List<LivingEntity> hitEntities = burst.getHitEntities(this);
                        for (LivingEntity hitEntity : hitEntities) {
                            if (hitEntity != this) {
                                for (ArsenalEffect arsenalEffect : effects) {
                                    arsenalEffect.process(this, hitEntity);
                                }
                            }
                        }
                        entityData.set(DATA_BLEEDING, burst.getExtension());
                    } else {
                        entityData.set(DATA_BLEEDING, 2);
                    }
                    deathTimer = 15;
                }
            }
        }
    }

    public boolean isTargeting() {
        return entityData.get(DATA_TARGETING);
    }

    public void setInPod(boolean inPod) {
        this.inPod = inPod;
    }

    public boolean isInPod() {
        return inPod;
    }

    @Override
    public void markAsPatient() {
        surgeryPatient = true;
    }

    @Override
    public boolean isSurgeryPatient() {
        return surgeryPatient;
    }

    @Override
    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    @Override
    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    @Override
    public PatientType getPatientType() {
        return PatientType.WEEPER;
    }

    @Override
    public void setHeld(boolean held) {
        this.held = held;
    }

    @Override
    public boolean isHeld() {
        return held;
    }

    @Override
    public void setHolderType(PatientHolderType type) {
        // TODO
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        setHeld(false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("held", held);
        if (lacrymatoryPos != null) {
            pCompound.putLong("lacrymatory", lacrymatoryPos.asLong());
        }
        if (master != null) {
            pCompound.putString("master", master.toString());
        }
        pCompound.putInt("ticksToFletum", ticksToFletum);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("held")) {
            held = pCompound.getBoolean("held");
        }
        if (pCompound.contains("lacrymatory")) {
            lacrymatoryPos = BlockPos.of(pCompound.getLong("lacrymatory"));
        }
        if (pCompound.contains("master")) {
            master = UUID.fromString(pCompound.getString("master"));
        }
        if (pCompound.contains("ticksToFletum")) {
            ticksToFletum = pCompound.getInt("ticksToFletum");
        }
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        if (pPlayer.isShiftKeyDown() && pPlayer.getItemInHand(pHand).isEmpty() && pHand == InteractionHand.MAIN_HAND && (pPlayer.getUUID().equals(master) || pPlayer.isCreative())) {
            if (!level().isClientSide) {
                setLacrymatoryPos(null);
                pPlayer.getCapability(CrossSyncDataProvider.CROSS_SYNC_DATA).ifPresent(data -> data.getCrossSync().setHeldPatient(this, pPlayer));
                discard();
            }
            return InteractionResult.SUCCESS;
        }
        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Override
    public BlockPos getLacrymatoryPos() {
        return lacrymatoryPos;
    }

    @Override
    public void setLacrymatoryPos(BlockPos pos) {
        lacrymatoryPos = pos;
    }

    @Override
    public int mbWept() {
        return 15;
    }

    @Override
    public UUID getMasterID() {
        return master;
    }

    @Override
    public void setMasterID(UUID uuid) {
        this.master = uuid;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return BTVSounds.WEEPING.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 140;
    }
}
