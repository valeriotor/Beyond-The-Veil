package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.client.render.PatientHolderType;
import com.valeriotor.beyondtheveil.entity.ai.goals.SurgeonSurgeryGoal;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.notes.PositionStep;
import com.valeriotor.beyondtheveil.surgery.notes.Report;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.surgery.surgeon.BellData;
import com.valeriotor.beyondtheveil.surgery.surgeon.SurgeonProgress;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class SurgeonEntity extends PathfinderMob implements PlayerMinion, DamageCapper, AnimatedEntity {

    private UUID master;
    private Report report;
    private Mob heldPatientEntity;
    private List<ItemStack> stacks = new ArrayList<>();
    private SurgeonProgress progress;
    public final BellData bellData = new BellData();
    private static final EntityDataAccessor<Integer> HELD_TYPE = SynchedEntityData.defineId(SurgeonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PERFORMING_SURGERY = SynchedEntityData.defineId(SurgeonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<CompoundTag> HELD_ENTITY = SynchedEntityData.defineId(SurgeonEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private Animation mainAnimation;
    private int performingSurgery = 0; // used for anims, server only
    private boolean wasPerformingSurgery = false; // used for anims, client only

    public SurgeonEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(0, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new SurgeonSurgeryGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HELD_ENTITY, new CompoundTag());
        this.entityData.define(HELD_TYPE, -1);
        this.entityData.define(PERFORMING_SURGERY, false);
    }

    @Override
    public float getDamageCap() {
        return 20;
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
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (master != null) {
            pCompound.putUUID("master", master);
        }
        if (report != null) {
            pCompound.put("report", report.saveToNBT());
        }
        pCompound.put("heldEntityData", entityData.get(HELD_ENTITY));
        pCompound.putInt("heldEntityType", entityData.get(HELD_TYPE));
        CompoundTag stacks = new CompoundTag();
        for (int i = 0; i < this.stacks.size(); i++) {
            stacks.put(String.valueOf(i), this.stacks.get(i).save(new CompoundTag()));
        }
        pCompound.put("stacks", stacks);
        if (progress != null) {
            pCompound.put("progress", progress.saveToNBT());
        }
        pCompound.put("bellData", bellData.saveToNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("master")) {
            master = pCompound.getUUID("master");
        }
        if (pCompound.contains("report")) {
            report = Report.loadFromNBT(pCompound.getCompound("report"));
        }
        entityData.set(HELD_ENTITY, pCompound.getCompound("heldEntityData"));
        entityData.set(HELD_TYPE, pCompound.getInt("heldEntityType"));
        CompoundTag stacks1 = pCompound.getCompound("stacks");
        stacks1.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::parseInt)).forEach(s -> stacks.add(ItemStack.of(stacks1.getCompound(s))));
        if (pCompound.contains("progress")) {
            progress = SurgeonProgress.fromNBT(this, pCompound.getCompound("progress"));
        }
        bellData.loadNBT(pCompound.getCompound("bellData"));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return BTVSounds.SURGEON_IDLE.get();
    }

    public Report getReport() {
        return report;
    }

    public CompoundTag getHeldPatientData() {
        return entityData.get(HELD_ENTITY);
    }

    public PatientType getHeldPatientType() {
        if (entityData.get(HELD_TYPE) == -1) {
            return null;
        }
        return PatientType.values()[entityData.get(HELD_TYPE)];
    }

    public Mob getHeldPatientEntity() {
        if (entityData.get(HELD_TYPE) == -1) {
            return null;
        } else if (heldPatientEntity == null) {
            heldPatientEntity = getHeldPatientType().getMobFunction().apply(level());
            heldPatientEntity.readAdditionalSaveData(entityData.get(HELD_ENTITY));
            ((SurgeryPatient) heldPatientEntity).setHeld(true);
            ((SurgeryPatient) heldPatientEntity).setHolderType(PatientHolderType.SURGEON);
            heldPatientEntity.getCapability(ConvalescentDataProvider.CONVALESCENT_DATA).ifPresent(c -> {
                if (entityData.get(HELD_ENTITY).contains("convalescent")) {
                    c.loadFromNBT(entityData.get(HELD_ENTITY).getCompound("convalescent"));
                }
            });
        }
        return heldPatientEntity;
    }

    public void setHeldPatient(PatientType heldPatientType, @NotNull CompoundTag heldPatientData) {
        entityData.set(HELD_TYPE, heldPatientType == null ? -1 : heldPatientType.ordinal());
        entityData.set(HELD_ENTITY, heldPatientData);
        heldPatientEntity = null;
    }

    public void giveItem(ItemStack stack) {
        stacks.add(stack);
    }

    public List<ItemStack> getItems() {
        return stacks;
    }

    public void removeProgress() {
        progress = null;
    }

    public void newProgress() {
        progress = new SurgeonProgress(this, report);
    }

    public SurgeonProgress getProgress() {
        return progress;
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack held = pPlayer.getItemInHand(pHand);
        if (held.getItem() == Registration.SURGEON_BELL.get()) {
            if (level().isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            CompoundTag tag = held.getOrCreateTag();
            if (tag.contains("surgeon") && uuid.equals(tag.getUUID("surgeon"))) {
                tag.remove("surgeon");
            } else {
                tag.putUUID("surgeon", this.uuid);
            }
            return InteractionResult.SUCCESS;
        } else if (held.getItem() == Registration.SURGERY_REPORT.get()) {
            if (level().isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            CompoundTag tag = held.getOrCreateTag();
            if (tag.contains("report")) {
                removeProgress();
                this.report = Report.loadFromNBT(tag.getCompound("report"));
                if (bellData.getSurgicalBE() != null && level().getBlockEntity(bellData.getSurgicalBE()) instanceof SurgicalBE be) {
                    for (ReportStep step : this.report.getSteps()) {
                        if (step instanceof PositionStep ps && !be.allowedLocations().contains(ps.getLocation().getLocation())) {
                            pPlayer.sendSystemMessage(Component.translatable(be instanceof SurgeryBedBE ? "interact.surgeon.bad_report_location_bed" : "interact.surgeon.bad_report_location_cradle"));
                            break;
                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void startAnimation(AnimationTemplate animationTemplate, int channel) {
        if (channel == 0) {
            mainAnimation = new Animation(animationTemplate);
        }
    }

    public Animation getMainAnimation() {
        return mainAnimation;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (mainAnimation != null) {
                mainAnimation.update();
                if (mainAnimation.isDone()) {
                    mainAnimation = null;
                }
            }
            if (entityData.get(PERFORMING_SURGERY)) {
                if (!wasPerformingSurgery) {
                    wasPerformingSurgery = true;
                    mainAnimation = new Animation(AnimationRegistry.surgeon_operate_start);
                }
            } else {
                if (wasPerformingSurgery) {
                    wasPerformingSurgery = false;
                    mainAnimation = new Animation(AnimationRegistry.surgeon_operate_stop);
                }
            }
        } else {
            if (performingSurgery > 0) {
                performingSurgery--;
                if (performingSurgery == 0) {
                    entityData.set(PERFORMING_SURGERY, false);
                } else {
                    entityData.set(PERFORMING_SURGERY, true);
                }
            }
        }
    }

    public void setPerformingSurgery() {
        this.performingSurgery = 10;
    }

    public boolean isPerformingSurgery() {
        return entityData.get(PERFORMING_SURGERY);
    }
}
