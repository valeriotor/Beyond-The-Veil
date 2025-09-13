package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import com.valeriotor.beyondtheveil.entity.*;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public enum BloodPoolEntityType {
    ABOMINATION0(BTVEntities.ABOMINATION_0.get(), 30, l -> new Abomination0Entity(BTVEntities.ABOMINATION_0.get(), l)),
    ABOMINATION1(BTVEntities.ABOMINATION_1.get(), 50, l -> new Abomination1Entity(BTVEntities.ABOMINATION_1.get(), l)),
    ABOMINATION2(null, 70, null),
    BLOOD_SKELETON(BTVEntities.BLOOD_SKELETON.get(), 70, l -> new BloodSkeletonEntity(BTVEntities.BLOOD_SKELETON.get(), l)),
    BLOOD_ZOMBIE(BTVEntities.BLOOD_ZOMBIE.get(), 50, l -> new BloodZombieEntity(BTVEntities.BLOOD_ZOMBIE.get(), l)),
    VILLAGER(EntityType.VILLAGER, 20, l -> new Villager(EntityType.VILLAGER, l)),
    WEEPER(BTVEntities.WEEPER.get(), 20, l -> new WeeperEntity(BTVEntities.WEEPER.get(), l));

    private final EntityType<?> entityType;
    private final float maxHealth;
    private final Function<Level, Mob> mobFunction;


    BloodPoolEntityType(EntityType<?> entityType, float maxHealth, Function<Level, Mob> mobFunction) {
        this.entityType = entityType;
        this.maxHealth = maxHealth;
        this.mobFunction = mobFunction;
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

    public Function<Level, Mob> getMobFunction() {
        return mobFunction;
    }
}
