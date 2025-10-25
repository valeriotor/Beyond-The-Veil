package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.client.render.PatientHolderType;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.notes.Report;
import com.valeriotor.beyondtheveil.surgery.surgeon.BellData;
import com.valeriotor.beyondtheveil.surgery.surgeon.SurgeonProgress;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SurgeonEntity extends Mob implements PlayerMinion, DamageCapper {

    private UUID master;
    private Report report;
    private Mob heldPatientEntity;
    private List<ItemStack> stacks = new ArrayList<>();
    private SurgeonProgress progress;
    private static final EntityDataAccessor<Integer> HELD_TYPE = SynchedEntityData.defineId(SurgeonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<CompoundTag> HELD_ENTITY = SynchedEntityData.defineId(SurgeonEntity.class, EntityDataSerializers.COMPOUND_TAG);

    public SurgeonEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HELD_ENTITY, new CompoundTag());
        this.entityData.define(HELD_TYPE, -1);
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
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return BTVSounds.SURGEON_IDLE.get();
    }

    public Report getReport() {
        return report;
    }

    public BellData getBellData() {
        return null;
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

    public Mob getHeldPatientEntity(Level level) {
        if (entityData.get(HELD_TYPE) == -1) {
            return null;
        } else if (heldPatientEntity == null) {
            heldPatientEntity = getHeldPatientType().getMobFunction().apply(level);
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

    public void newProgress() {
        progress = new SurgeonProgress(this, report);
    }

    public SurgeonProgress getProgress() {
        return progress;
    }
}
