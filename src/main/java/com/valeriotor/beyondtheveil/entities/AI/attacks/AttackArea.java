package com.valeriotor.beyondtheveil.entities.AI.attacks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

public abstract class AttackArea {

    public static AttackArea getConeAttack(double radius, double degreesToTheLeft, double degreesToTheRight) {
        return new AttackCone(radius, degreesToTheLeft, degreesToTheRight);
    }

    public static AttackArea getCircleAttack(double radius) {
        return new AttackCone(radius, 180, 180);
    }

    public abstract List<EntityLivingBase> getVictims(EntityLiving source, double initialRotation);

}
