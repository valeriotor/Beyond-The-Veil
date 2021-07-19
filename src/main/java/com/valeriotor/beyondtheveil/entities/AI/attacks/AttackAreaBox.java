package com.valeriotor.beyondtheveil.entities.AI.attacks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.stream.Collectors;

public class AttackAreaBox extends AttackArea{

    private final AxisAlignedBB BOX;

    public AttackAreaBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        BOX = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public List<EntityLivingBase> getVictims(EntityLiving source, double initialRotation) {
        if(true) {
            AxisAlignedBB BOX = this.BOX.offset(source.posX, source.posY, source.posZ);
            double sizeX = (BOX.maxX- BOX.minX)/3;
            double sizeY = (BOX.maxY- BOX.minY)/3;
            double sizeZ = (BOX.maxZ- BOX.minZ)/3;
            for (int i = 0; i <= 3; i++) {
                for (int j = 0; j <= 3; j++) {
                    for (int k = 0; k <= 3; k++) {
                        Vec3d particleVec1 = new Vec3d(BOX.minX + sizeX*i, BOX.minY + sizeY*j, BOX.minZ + sizeZ*k);
                        ((WorldServer)source.world).spawnParticle(EnumParticleTypes.REDSTONE, particleVec1.x, particleVec1.y, particleVec1.z, 10, 0, 0,0, (double)0, 0);
                    }
                }
            }
        }
        return source.world.getEntitiesInAABBexcluding(source, BOX.offset(source.posX, source.posY, source.posZ), e -> e instanceof EntityLivingBase)
        .stream().map(e -> (EntityLivingBase)e).collect(Collectors.toList());
    }
}
