package com.valeriotor.beyondtheveil.entities.AI.attacks;

import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

public class AttackCone extends AttackArea{

    private final double radius;
    private final double degreesToTheLeft;
    private final double degreesToTheRight;

    AttackCone(double radius, double degreesToTheLeft, double degreesToTheRight) {
        this.radius = radius;
        this.degreesToTheLeft = degreesToTheLeft;
        this.degreesToTheRight = degreesToTheRight;
    }

    @Override
    public List<EntityLivingBase> getVictims(EntityLiving source, double initialRotation) {
        return source.world.getEntities(EntityLivingBase.class, target -> this.isInCone(source, target, initialRotation) && target != source);
    }

    private boolean isInCone(EntityLivingBase source, EntityLivingBase target, double initialRotation) {
        double distance = source.getDistance(target);
        if(distance > radius) return false;
        if(distance < radius/5) return true;
        double rotation = MathHelperBTV.angleBetween(source, target);
        double lowerBound = initialRotation - degreesToTheLeft;
        double upperBound = initialRotation + degreesToTheRight;
        if(lowerBound < -180) {
            return rotation < upperBound || rotation > lowerBound + 360;
        } else if(upperBound > 180) {
            return rotation < upperBound - 360 || rotation > lowerBound;
        } else {
            return rotation < upperBound || rotation > lowerBound;
        }
    }
}
