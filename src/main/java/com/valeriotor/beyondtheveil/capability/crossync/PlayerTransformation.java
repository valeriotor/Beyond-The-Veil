package com.valeriotor.beyondtheveil.capability.crossync;

import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public enum PlayerTransformation {
    DEEP_ONE(BTVEntities.DEEP_ONE::get, false, false),
    SCALED(() -> null, false, true),
    ABOMINATION_0(BTVEntities.ABOMINATION_0::get, true, false),
    ABOMINATION_1(BTVEntities.ABOMINATION_1::get, true, false),
    ABOMINATION_2(BTVEntities.ABOMINATION_2::get, true, false);

    private final Supplier<EntityType<?>> entityType;
    private final boolean canExplode;
    private final boolean persistsOnDeath;

    PlayerTransformation(Supplier<EntityType<?>> entityType, boolean canExplode, boolean persistsOnDeath) {
        this.entityType = entityType;
        this.canExplode = canExplode;
        this.persistsOnDeath = persistsOnDeath;
    }

    public EntityType<?> getEntityType() {
        return entityType.get();
    }

    public boolean isCanExplode() {
        return canExplode;
    }

    public boolean isPersistsOnDeath() {
        return persistsOnDeath;
    }
}
