package com.valeriotor.beyondtheveil.entities.AI.attacks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

public abstract class AttackArea {

    public static AttackArea getConeAttack(double radius, double degreesToTheLeft, double degreesToTheRight) {
        return new AttackAreaCone(radius, degreesToTheLeft, degreesToTheRight);
    }

    public static AttackArea getCircleAttack(double radius) {
        return new AttackAreaCone(radius, 180, 180);
    }

    public static AttackArea getShiftedConeAttack(double radius, double farArc, double closeArc) {
        return new AttackAreaShiftedCone(radius, farArc, closeArc);
    }

    public abstract List<EntityLivingBase> getVictims(EntityLiving source, double initialRotation);

}
