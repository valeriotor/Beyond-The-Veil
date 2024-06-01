package com.valeriotor.beyondtheveil.surgery.arsenal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.List;

public abstract class BurstType {

    private final String name;

    public BurstType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void createParticles(Mob attacker, int extension);

    public abstract List<LivingEntity> getHitEntities(Mob attacker, int extension);

}
