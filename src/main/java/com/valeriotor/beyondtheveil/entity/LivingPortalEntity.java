package com.valeriotor.beyondtheveil.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LivingPortalEntity extends Mob implements DamageCapper {

    private int counter = 0;

    public LivingPortalEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    private final WeightedRandomList<WeightedEntry.Wrapper<EntityType<?>>> list = WeightedRandomList.create(
            WeightedEntry.wrap(EntityType.BLAZE, 10),
            WeightedEntry.wrap(EntityType.GHAST, 5),
            WeightedEntry.wrap(EntityType.HOGLIN, 5),
            WeightedEntry.wrap(EntityType.MAGMA_CUBE, 10),
            WeightedEntry.wrap(EntityType.PIGLIN, 5),
            WeightedEntry.wrap(EntityType.PIGLIN_BRUTE, 7),
            WeightedEntry.wrap(EntityType.WITHER_SKELETON, 6),
            WeightedEntry.wrap(EntityType.ZOGLIN, 8),
            WeightedEntry.wrap(EntityType.ZOMBIFIED_PIGLIN, 5));

    private final Set<EntityType<?>> types = list.unwrap().stream().map(WeightedEntry.Wrapper::getData).collect(Collectors.toSet());


    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            counter++;
            if (counter > 200 && level() instanceof ServerLevel sl) {
                if (sl.getEntities((Entity) null, AABB.ofSize(position(), 40, 40, 40), e -> types.contains(e.getType())).size() < 30) {
                    Optional<WeightedEntry.Wrapper<EntityType<?>>> random1 = list.getRandom(getRandom());
                    random1.ifPresent(w -> {
                        w.getData().spawn(sl, blockPosition(), MobSpawnType.SPAWNER);
                    });
                }
                counter = 0;
            }
            if (counter % 16 == 0) {
                heal(1);
            }
        } else {
            portalAnimateTick(level(), position().add(new Vec3(-0.7, 0.7, 0)), getRandom());
        }
    }

    private void portalAnimateTick(Level pLevel, Vec3 position, RandomSource pRandom) {
        if (pRandom.nextInt(100) == 0) {
            pLevel.playLocalSound((double) position.x + 0.5D, (double) position.y + 0.5D, (double) position.z + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, pRandom.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double d0 = (double) position.x + pRandom.nextDouble() * 1.2;
            double d1 = (double) position.y + pRandom.nextDouble() * 1.2;
            double d2 = (double) position.z + pRandom.nextDouble() * 1.2;
            double d3 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double) pRandom.nextFloat() - 0.5D) * 0.5D;
            int j = pRandom.nextInt(2) * 2 - 1;
            d2 = (double) position.z + 0.5D + 0.25D * (double) j;
            d5 = (double) (pRandom.nextFloat() * 2.0F * (float) j);

            pLevel.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public void aiStep() {
        if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("counter", counter);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        counter = pCompound.getInt("counter");
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PORTAL_TRIGGER;
    }

    @Override
    public float getDamageCap() {
        return 100;
    }
}
