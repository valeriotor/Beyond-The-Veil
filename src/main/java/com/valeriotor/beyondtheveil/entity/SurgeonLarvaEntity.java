package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.animation.AnimationRegistry;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class SurgeonLarvaEntity extends Mob implements PlayerMinion {

    private static final EntityDataAccessor<Integer> GROWTH = SynchedEntityData.defineId(SurgeonLarvaEntity.class, EntityDataSerializers.INT);

    private UUID master;
    private int growthTicks = 0;
    public SurgeonLarvaEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setPersistenceRequired();

    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GROWTH, 20);
    }


    @Override
    public UUID getMasterID() {
        return master;
    }

    @Override
    public void setMasterID(UUID uuid) {
        master = uuid;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (master != null) {
            pCompound.putUUID("master", uuid);
        }
        pCompound.putInt("growth", entityData.get(GROWTH));
        pCompound.putInt("growthTicks", growthTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("master")) {
            master = pCompound.getUUID("master");
        }
        entityData.set(GROWTH, pCompound.getInt("growth"));
        growthTicks = pCompound.getInt("growthTicks");
    }

    @Override
    public float getScale() {
        return entityData.get(GROWTH) / 500F;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (growthTicks > 0) {
                growthTicks--;
                entityData.set(GROWTH, entityData.get(GROWTH) + 1);
                if (entityData.get(GROWTH) > 800) {
                    level().playSound(null, blockPosition(), BTVSounds.HEART_RIP.get(), SoundSource.NEUTRAL, 1, 1);
                    SurgeonEntity surgeon = new SurgeonEntity(BTVEntities.SURGEON.get(), level());
                    surgeon.setMasterID(master);
                    surgeon.setPos(position());
                    level().addFreshEntity(surgeon);
                    surgeon.sendAnimation(AnimationRegistry.surgeon_standup, 0);
                    discard();
                }
            }
        }
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.is(Registration.LARVA_FOOD)) {
            if (level().isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            if (growthTicks < 100) {
                growthTicks += 50;
                for(int i = 0; i < 7; ++i) {
                    double d0 = this.random.nextGaussian() * 0.02D;
                    double d1 = this.random.nextGaussian() * 0.02D;
                    double d2 = this.random.nextGaussian() * 0.02D;
                    ((ServerLevel) level()).sendParticles(ParticleTypes.SMOKE, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 1, d0, d1, d2, 0);
                }
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public int getGrowth() {
        return entityData.get(GROWTH);
    }
}
