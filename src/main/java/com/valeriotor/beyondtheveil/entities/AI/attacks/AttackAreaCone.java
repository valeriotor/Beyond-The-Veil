package com.valeriotor.beyondtheveil.entities.AI.attacks;

import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.stream.Collectors;

public class AttackAreaCone extends AttackArea{

    private final double radius;
    private final double degreesToTheLeft;
    private final double degreesToTheRight;

    AttackAreaCone(double radius, double degreesToTheLeft, double degreesToTheRight) {
        this.radius = radius;
        this.degreesToTheLeft = degreesToTheLeft;
        this.degreesToTheRight = degreesToTheRight;
    }

    @Override
    public List<EntityLivingBase> getVictims(EntityLiving source, double initialRotation) {
        double r = radius*2;
        return source.world.getEntitiesInAABBexcluding(source, new AxisAlignedBB(source.getPosition().add(-r,-r,-r), source.getPosition().add(r,r,r)),
                target -> target != source && target instanceof EntityLivingBase && this.isInCone(source, (EntityLivingBase) target, initialRotation))
                .stream().map(e -> (EntityLivingBase)e).collect(Collectors.toList());
    }

    private boolean isInCone(EntityLivingBase source, EntityLivingBase target, double initialRotation) {
        double distance = source.getDistance(target);
        if(distance > radius) return false;
        if(distance < radius/8) return true;
        return MathHelperBTV.isEntityWithinAngleOfEntity(source, target, initialRotation, degreesToTheLeft, degreesToTheRight);/*
        double rotation = MathHelperBTV.angleBetween(source, target);
        double lowerBound = initialRotation - degreesToTheLeft;
        double upperBound = initialRotation + degreesToTheRight;
        if(lowerBound < -180) {
            return rotation < upperBound || rotation > lowerBound + 360;
        } else if(upperBound > 180) {
            return rotation < upperBound - 360 || rotation > lowerBound;
        } else {
            return rotation < upperBound && rotation > lowerBound;
        }*/
    }
}
