package com.valeriotor.beyondtheveil.capability.crossync;

import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public enum PlayerTransformation {
    DEEP_ONE(BTVEntities.DEEP_ONE::get, false),
    ABOMINATION_0(BTVEntities.ABOMINATION_0::get, true),
    ABOMINATION_1(BTVEntities.ABOMINATION_1::get, true);

    private final Supplier<EntityType<?>> entityType;
    private final boolean canExplode;

    PlayerTransformation(Supplier<EntityType<?>> entityType, boolean canExplode) {
        this.entityType = entityType;
        this.canExplode = canExplode;
    }

    public EntityType<?> getEntityType() {
        return entityType.get();
    }

    public boolean isCanExplode() {
        return canExplode;
    }
}
