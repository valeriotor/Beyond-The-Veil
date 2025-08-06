package com.valeriotor.beyondtheveil.entity.projectile;

import com.valeriotor.beyondtheveil.entity.ictya.UmancalaEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class UmancalaFireball extends Fireball {

    public UmancalaFireball(EntityType<? extends Fireball> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public UmancalaFireball(EntityType<? extends Fireball> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public UmancalaFireball(EntityType<? extends Fireball> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && tickCount > 90) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        Entity entity1 = this.getOwner();
        if (entity.hurt(this.damageSources().fireball(this, entity1), 5.0F)) {
            if (entity1 instanceof LivingEntity e1) {
                this.doEnchantDamageEffects(e1, entity);
                if (e1 instanceof UmancalaEntity u && u.isDeadOrDying() && level() instanceof ServerLevel sl && entity instanceof LivingEntity le) {
                    u.killedEntity(sl, le);
                }
            }
        }
    }
}
