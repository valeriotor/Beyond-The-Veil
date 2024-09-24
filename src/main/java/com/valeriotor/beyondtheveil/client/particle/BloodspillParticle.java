package com.valeriotor.beyondtheveil.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class BloodspillParticle extends TextureSheetParticle {

    private final boolean still;

    protected BloodspillParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.lifetime = 24;
        this.yd *= 2.5;
        if (pYSpeed == 0) {
            this.yd = 0;
        }
        //this.yd += Math.random() / 2;
        double norm = pXSpeed * pXSpeed + pZSpeed * pZSpeed;
        this.xd *= norm;
        this.zd *= norm;
        still = pXSpeed == 0 && pYSpeed == 0 && pZSpeed == 0;
        //this.xd += Math.random() / 2;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= (double) this.gravity;
            if (still) {
                this.yd = 0;
            }
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= (double) 0.98F;
                this.yd *= (double) 0.98F;
                this.zd *= (double) 0.98F;
            }
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }


    protected void postMoveUpdate() {
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class BloodspillParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public BloodspillParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BloodspillParticle particle = new BloodspillParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setSprite(spriteSet.get(0, 24));
            return particle;
        }
    }
}
