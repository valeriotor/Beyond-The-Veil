package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public enum BloodPoolEntityType {
    ABOMINATION0(BTVEntities.ABOMINATION_0.get(), 30),
    ABOMINATION1(BTVEntities.ABOMINATION_1.get(), 50),
    ABOMINATION2(null, 70),
    BLOOD_SKELETON(BTVEntities.BLOOD_SKELETON.get(), 70),
    BLOOD_ZOMBIE(BTVEntities.BLOOD_ZOMBIE.get(), 50),
    VILLAGER(EntityType.VILLAGER, 20),
    WEEPER(BTVEntities.WEEPER.get(), 20);

    private final EntityType<?> entityType;
    private final float maxHealth;

    BloodPoolEntityType(EntityType<?> entityType, float maxHealth) {
        this.entityType = entityType;
        this.maxHealth = maxHealth;
    }

    public static BloodPoolEntityType fromPatientType(PatientType type) {
        return switch (type) {
            case VILLAGER -> VILLAGER;
            case WEEPER -> WEEPER;
        };
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public float getMaxHealth() {
        return maxHealth;
    }
}
