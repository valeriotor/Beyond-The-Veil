package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentData;
import com.valeriotor.beyondtheveil.client.event.RenderEvents;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeHooks;

public class BloodPoolEntity {

    private final ConvalescentData convalescentData = new ConvalescentData();

    public static BloodPoolEntity fromPatient(PatientType patientType, CompoundTag entityData, ConvalescentData convalescentData, TriggerData triggerData) {
        BloodPoolEntityType bloodPoolEntityType;
        if (patientType == PatientType.VILLAGER && triggerData != null) {
            bloodPoolEntityType = BTVEntities.getTriggerEntity(convalescentData, triggerData).getB();
        } else {
            bloodPoolEntityType = BloodPoolEntityType.fromPatientType(patientType);
        }
        entityData.putFloat("Health", bloodPoolEntityType.getMaxHealth());
        return new BloodPoolEntity(bloodPoolEntityType, entityData);
    }

    private final CompoundTag entityData;

    private final BloodPoolEntityType type;

    private BloodPoolEntity(BloodPoolEntityType type, CompoundTag entityData) {
        this.type = type;
        this.entityData = entityData;
        convalescentData.loadFromNBT(entityData.getCompound("convalescent"));
    }

    public BloodPoolEntity(CompoundTag tag) {
        type = BloodPoolEntityType.valueOf(tag.getString("type"));
        entityData = tag.getCompound("entityData");
        convalescentData.loadFromNBT(entityData.getCompound("convalescent"));
    }

    public String textDescription() {
        float health = entityData.getFloat("Health");
        StringBuilder sb = new StringBuilder(Component.translatable("gui.blood_pool.health").getString() +  health);
        TriggerData triggerData = convalescentData.getTriggerData();
        if (triggerData != null) {
            for (String s : RenderEvents.triggerDataDescription(triggerData)) {
                sb.append("\\0");
                sb.append(s);
            }
        }
        return sb.toString();
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
