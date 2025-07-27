package com.valeriotor.beyondtheveil.entity.ai.attacks;

import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AttackAreaCone extends AttackArea {

    private final double radius;
    private final double degreesToTheLeft;
    private final double degreesToTheRight;

    AttackAreaCone(double radius, double degreesToTheLeft, double degreesToTheRight) {
        this.radius = radius;
        this.degreesToTheLeft = degreesToTheLeft;
        this.degreesToTheRight = degreesToTheRight;
    }

    @Override
    public List<LivingEntity> getVictims(LivingEntity source, double initialRotation) {
        double r = radius * 2;
        return source.level().getEntities(source, new AABB(source.position().add(-r, -r, -r), source.position().add(r, r, r)),
                        target -> target != source && target instanceof LivingEntity e && this.isInCone(source, e, initialRotation))
                .stream().map(e -> (LivingEntity) e).toList();
    }

    private boolean isInCone(LivingEntity source, LivingEntity target, double initialRotation) {
        double distance = source.distanceTo(target);
        if (target instanceof Player) {
            if (distance > radius) return false;
        } else if (distance - target.getType().getWidth() * 1.2 > radius) return false;

        if (distance < radius / 8) return true;
        return MathHelperBTV.isEntityWithinAngleOfEntity(source, target, initialRotation, degreesToTheLeft, degreesToTheRight);
    }
}
