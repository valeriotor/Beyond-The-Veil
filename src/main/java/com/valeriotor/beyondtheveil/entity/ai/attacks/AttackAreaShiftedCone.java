package com.valeriotor.beyondtheveil.entity.ai.attacks;

import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AttackAreaShiftedCone extends AttackArea {

    private final double distance;
    private final double farArc;
    private final double closeArc;

    AttackAreaShiftedCone(double distance, double farArc, double closeArc) {
        this.distance = distance;
        this.farArc = farArc;
        this.closeArc = closeArc;
    }

    @Override
    public List<LivingEntity> getVictims(LivingEntity source, double initialRotation) {
        double r = distance * 2;
        return source.level().getEntities(source, new AABB(source.position().add(-r, -r, -r), source.position().add(r, r, r)),
                        target -> target != source && target instanceof LivingEntity e && this.isInShiftedCone(source, e, initialRotation))
                .stream().map(e -> (LivingEntity) e).toList();
    }
    private boolean isInShiftedCone(LivingEntity source, LivingEntity target, double initialRotation) {
        double distance = source.distanceTo(target);
        if (target instanceof Player) {
            if (distance > this.distance) return false;
        } else if (distance - target.getType().getWidth() > this.distance) return false;

        if (distance < this.distance / 8) return true;
        double coneAngle = (farArc - closeArc) / distance;
        double innerRadius = closeArc / coneAngle;
        double coneAngleInDegrees = coneAngle * 180 / 2 / Math.PI;
        Vec3 coneCenter = source.position().subtract(Vec3.directionFromRotation(0, (float) initialRotation).scale(innerRadius));
        if (coneCenter.distanceTo(target.position()) < innerRadius) return false;
        /*for(int i = 0; i < 11; i++) {
            Vec3d particleVec1 = coneCenter.add(Vec3d.fromPitchYaw(0, (float)(initialRotation-coneAngleInDegrees/2)).scale((innerRadius+distance)*i/10));
            Vec3d particleVec2 = coneCenter.add(Vec3d.fromPitchYaw(0, (float)(initialRotation+coneAngleInDegrees/2)).scale((innerRadius+distance)*i/10));
            ((WorldServer)source.world).spawnParticle(EnumParticleTypes.REDSTONE, particleVec1.x, particleVec1.y, particleVec1.z, 10, 0, 0,0, (double)0, 0);
            ((WorldServer)source.world).spawnParticle(EnumParticleTypes.REDSTONE, particleVec2.x, particleVec2.y, particleVec2.z, 10, 0, 0,0, (double)0, 0);
            if(i == 10) {
                double test = particleVec1.distanceTo(source.getPositionVector());
                System.out.println(test);
            }
        }*/
        double rotation = MathHelperBTV.angleBetween(coneCenter.x, coneCenter.z, target.position().x, target.position().z);
        double lowerBound = initialRotation - coneAngleInDegrees / 2;
        double upperBound = initialRotation + coneAngleInDegrees / 2;
        return MathHelperBTV.wrapAngle(rotation, lowerBound, upperBound);
    }


}
