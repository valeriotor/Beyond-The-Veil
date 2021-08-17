package com.valeriotor.beyondtheveil.entities.AI.attacks;

import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.stream.Collectors;

public class AttackAreaShiftedCone extends AttackArea{

    private final double distance;
    private final double farArc;
    private final double closeArc;

    AttackAreaShiftedCone(double distance, double farArc, double closeArc) {
        this.distance = distance;
        this.farArc = farArc;
        this.closeArc = closeArc;
    }

    @Override
    public List<EntityLivingBase> getVictims(EntityLiving source, double initialRotation) {
        double r = distance*2;
        return source.world.getEntitiesInAABBexcluding(source, new AxisAlignedBB(source.getPosition().add(-r,-r,-r), source.getPosition().add(r,r,r)),
                target -> target != source && target instanceof EntityLivingBase && this.isInShiftedCone(source, (EntityLivingBase) target, initialRotation))
                .stream().map(e -> (EntityLivingBase)e).collect(Collectors.toList());
    }

    private boolean isInShiftedCone(EntityLivingBase source, EntityLivingBase target, double initialRotation) {
        double distance = source.getDistance(target);
        if(target instanceof EntityPlayer) {
            if(distance > this.distance) return false;
        } else if(distance - target.width > this.distance) return false;
        if(distance < this.distance/8) return true;
        double coneAngle = (farArc-closeArc) / distance;
        double innerRadius = closeArc/coneAngle;
        double coneAngleInDegrees = coneAngle*180 / 2 / Math.PI;
        Vec3d coneCenter = source.getPositionVector().subtract(Vec3d.fromPitchYaw(0, (float) initialRotation).scale(innerRadius));
        if(coneCenter.distanceTo(target.getPositionVector()) < innerRadius) return false;
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
        double rotation = MathHelperBTV.angleBetween(coneCenter.x, coneCenter.z, target.posX, target.posZ);
        double lowerBound = initialRotation - coneAngleInDegrees/2;
        double upperBound = initialRotation + coneAngleInDegrees/2;
        if(lowerBound < -180) {
            return rotation < upperBound || rotation > lowerBound + 360;
        } else if(upperBound > 180) {
            return rotation < upperBound - 360 || rotation > lowerBound;
        } else {
            return rotation < upperBound && rotation > lowerBound;
        }
    }
}
