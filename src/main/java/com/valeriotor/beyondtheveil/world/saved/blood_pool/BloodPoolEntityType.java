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
    ABOMINATION2(BTVEntities.ABOMINATION_2.get(), 70, l -> new Abomination1Entity(BTVEntities.ABOMINATION_2.get(), l)),
    BLOOD_SKELETON(BTVEntities.BLOOD_SKELETON.get(), 70, l -> new BloodSkeletonEntity(BTVEntities.BLOOD_SKELETON.get(), l), true),
    BLOOD_ZOMBIE(BTVEntities.BLOOD_ZOMBIE.get(), 50, l -> new BloodZombieEntity(BTVEntities.BLOOD_ZOMBIE.get(), l), true),
    VILLAGER(EntityType.VILLAGER, 20, l -> new Villager(EntityType.VILLAGER, l)),
    WEEPER(BTVEntities.WEEPER.get(), 20, l -> new WeeperEntity(BTVEntities.WEEPER.get(), l));

    private final EntityType<?> entityType;
    private final float maxHealth;
    private final Function<Level, Mob> mobFunction;
    private final boolean undead;


    BloodPoolEntityType(EntityType<?> entityType, float maxHealth, Function<Level, Mob> mobFunction) {
        this(entityType, maxHealth, mobFunction, false);
    }

    BloodPoolEntityType(EntityType<?> entityType, float maxHealth, Function<Level, Mob> mobFunction, boolean undead) {
        this.entityType = entityType;
        this.maxHealth = maxHealth;
        this.mobFunction = mobFunction;
        this.undead = undead;
    }

    public static BloodPoolEntityType fromPatientType(PatientType type) {
        return switch (type) {
            case VILLAGER -> VILLAGER;
            case WEEPER -> WEEPER;
            case PLAYER -> null;
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

    public boolean isUndead() {
        return undead;
    }
}
