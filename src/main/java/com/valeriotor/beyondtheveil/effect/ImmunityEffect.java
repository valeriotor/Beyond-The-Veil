package com.valeriotor.beyondtheveil.effect;

import com.valeriotor.beyondtheveil.lib.BTVEntities;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Set;

public class ImmunityEffect extends MobEffect {

    public static final Set<MobEffect> CURABLE = Set.of(MobEffects.POISON, MobEffects.WITHER);

    public ImmunityEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity e, int pAmplifier) {
        CURABLE.forEach(e::removeEffect);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
