package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import com.valeriotor.beyondtheveil.surgery.PatientType;
import net.minecraft.nbt.CompoundTag;

public class BloodPoolEntity {

    public static BloodPoolEntity fromPatient(PatientType patientType, CompoundTag entityData) {
        return new BloodPoolEntity(BloodPoolEntityType.fromPatientType(patientType), entityData);
    }

    private final CompoundTag entityData;

    private final BloodPoolEntityType type;

    private BloodPoolEntity(BloodPoolEntityType type, CompoundTag entityData) {
        this.type = type;
        this.entityData = entityData;
    }

    public BloodPoolEntity(CompoundTag tag) {
        type = BloodPoolEntityType.valueOf(tag.getString("type"));
        entityData = tag.getCompound("entityData");
    }

    public BloodPoolEntityType getType() {
        return type;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.name());
        tag.put("entityData", entityData);
        return tag;
    }



}
