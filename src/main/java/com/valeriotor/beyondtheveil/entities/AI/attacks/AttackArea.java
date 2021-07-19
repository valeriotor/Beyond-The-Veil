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

    public static AttackArea getBoundingBoxAttack(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new AttackAreaBox(x1, y1, z1, x2, y2, z2);
    }
    //TODO perhaps subtract target.width in the predicates? like, e.getdistance(attacker) - e.width < radius?
    public abstract List<EntityLivingBase> getVictims(EntityLiving source, double initialRotation);

}
