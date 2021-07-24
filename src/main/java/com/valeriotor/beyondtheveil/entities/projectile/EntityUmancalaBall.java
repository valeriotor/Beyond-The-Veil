package com.valeriotor.beyondtheveil.entities.projectile;

import com.valeriotor.beyondtheveil.entities.ictya.EntityIctya;
import com.valeriotor.beyondtheveil.entities.ictya.EntityUmancala;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityUmancalaBall extends EntityFireball {

    public EntityUmancalaBall(World worldIn) {
        super(worldIn);
    }

    public EntityUmancalaBall(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
    }

    public EntityUmancalaBall(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.shootingEntity), 17.0F);

                if (flag) {
                    this.applyEnchantments(this.shootingEntity, result.entityHit);
                }
                if (result.entityHit.isDead) {
                    if (shootingEntity instanceof EntityUmancala && result.entityHit instanceof EntityIctya) {
                        ((EntityUmancala)shootingEntity).feed(((EntityIctya)result.entityHit).getFoodValue());
                    }
                }
            }

            this.setDead();
        }
    }

    @Override
    protected boolean isFireballFiery() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
}
