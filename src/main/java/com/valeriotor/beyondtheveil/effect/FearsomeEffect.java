package com.valeriotor.beyondtheveil.effect;

import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FearsomeEffect extends MobEffect {

    public FearsomeEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        List<Entity> entities = pLivingEntity.level().getEntities(pLivingEntity, AABB.ofSize(pLivingEntity.position(), 10, 5, 10), e -> e instanceof LivingEntity l && !BTVEntities.isFearlessEntity(l));
        for (Entity entity : entities) {
            BTVEntities.moveEntity((LivingEntity) entity, pLivingEntity);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return (pDuration & 1) == 0;
    }
}
