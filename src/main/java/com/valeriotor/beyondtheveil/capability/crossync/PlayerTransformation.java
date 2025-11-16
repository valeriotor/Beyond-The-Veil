package com.valeriotor.beyondtheveil.capability.crossync;

import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public enum PlayerTransformation {
    DEEP_ONE(BTVEntities.DEEP_ONE::get),
    ABOMINATION_0(BTVEntities.ABOMINATION_0::get),
    ABOMINATION_1(BTVEntities.ABOMINATION_1::get);

    private final Supplier<EntityType<?>> entityType;

    PlayerTransformation(Supplier<EntityType<?>> entityType) {
        this.entityType = entityType;
    }

    public EntityType<?> getEntityType() {
        return entityType.get();
    }
}
