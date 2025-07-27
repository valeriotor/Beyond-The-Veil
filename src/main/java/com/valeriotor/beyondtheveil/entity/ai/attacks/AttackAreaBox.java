package com.valeriotor.beyondtheveil.entity.ai.attacks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AttackAreaBox extends AttackArea{

    private final AABB BOX;

    public AttackAreaBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        BOX = new AABB(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public List<LivingEntity> getVictims(LivingEntity source, double initialRotation) {
        return source.level().getEntities(source, BOX.move(source.getX(), source.getY(), source.getZ()), e -> e instanceof LivingEntity).stream().map(e -> (LivingEntity)e).toList();
    }
}
