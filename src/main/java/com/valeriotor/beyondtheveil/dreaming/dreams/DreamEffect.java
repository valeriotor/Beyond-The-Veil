package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DreamEffect extends Dream {

    public DreamEffect(Memory memory) {
        super(memory, 3, EffectReminiscence::new);
    }

    @Override
    public boolean activate(Player p, Level l) {
        boolean consumeVoid = DreamHandler.consumeVoid(p);
        int i = modifyEffects(p, consumeVoid);
        DataUtil.addReminiscence(p, memory.getDataName(consumeVoid), new EffectReminiscence("reminiscence." + memory.getDataName(), 0, i));
        return true;
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        boolean consumeVoid = DreamHandler.consumeVoid(caster);
        int i = modifyEffects(target, consumeVoid);
        DataUtil.addReminiscence(caster, memory.getDataName(consumeVoid), new EffectReminiscence("reminiscence." + memory.getDataName(), 1, i));
        return true;
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        int count = 0;
        boolean consumeVoid = DreamHandler.consumeVoid(p);
        List<Entity> entities = l.getEntities(((Entity) null), AABB.ofSize(pos.getCenter(), 50, 50, 50), e -> !(e instanceof Player));
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity e) {
                int i = modifyEffects(e, consumeVoid);
                if (i > 0) {
                    count++;
                }
            }
        }
        DataUtil.addReminiscence(p, memory.getDataName(consumeVoid), new EffectReminiscence("reminiscence." + memory.getDataName(), 2, count));
        return true;
    }

    private int modifyEffects(LivingEntity e, boolean hasVoid) {
        int count = 0;
        Collection<MobEffectInstance> effects = e.getActiveEffects();
        List<MobEffectInstance> newEffects = new ArrayList<>();
        List<MobEffect> toRemove = new ArrayList<>();
        for (MobEffectInstance effect : effects) {
            int newAmplifier = effect.getAmplifier() + (memory == Memory.POWER ? (hasVoid ? 2 : 1) : 0);
            newAmplifier = Math.max(effect.getAmplifier(), Math.min(newAmplifier, 10));
            if (effect.getEffect() == MobEffects.DAMAGE_RESISTANCE) {
                newAmplifier = Math.max(effect.getAmplifier(), Math.min(newAmplifier, 4));
            }
            int newDuration = (effect.getDuration() + (memory == Memory.STILLNESS ? (hasVoid ? 60 : 30) : 0)) * (memory == Memory.STILLNESS ? (hasVoid ? 4 : 2) : 1);
            MobEffect newEffect = effect.getEffect();
            if (memory == Memory.CHANGE) {
                newEffect = changeEffect(newEffect, hasVoid);
                if (newEffect != effect.getEffect()) {
                    toRemove.add(effect.getEffect());
                }
            }
            if (newAmplifier > effect.getAmplifier() || newDuration > effect.getDuration() || newEffect != effect.getEffect()) {
                count++;
            }
            if (newEffect != null) {
                newEffects.add(new MobEffectInstance(newEffect, newDuration, newAmplifier, effect.isAmbient(), effect.isVisible(), effect.showIcon()));
            }
        }
        toRemove.forEach(e::removeEffect);
        newEffects.forEach(e::addEffect);
        return count;
    }

    private MobEffect changeEffect(MobEffect effect, boolean hasVoid) {
        if (!hasVoid) {
            if (effect == MobEffects.HARM) {
                return MobEffects.HEAL;
            } else if (effect == MobEffects.MOVEMENT_SLOWDOWN) {
                return MobEffects.MOVEMENT_SPEED;
            } else if (effect == MobEffects.DIG_SLOWDOWN) {
                return MobEffects.DIG_SPEED;
            } else if (effect == MobEffects.CONFUSION) {
                return null;
            } else if (effect == MobEffects.BLINDNESS) {
                return MobEffects.NIGHT_VISION;
            } else if (effect == MobEffects.HUNGER) {
                return MobEffects.SATURATION;
            } else if (effect == MobEffects.WEAKNESS) {
                return MobEffects.DAMAGE_BOOST;
            } else if (effect == MobEffects.POISON) {
                return MobEffects.REGENERATION;
            } else if (effect == MobEffects.WITHER) {
                return MobEffects.REGENERATION;
            } else if (effect == MobEffects.UNLUCK) {
                return MobEffects.LUCK;
            } else if (effect == BTVEffects.VULNERABILITY.get()) {
                return MobEffects.DAMAGE_RESISTANCE;
            } else if (effect == BTVEffects.SINK.get()) {
                return MobEffects.SLOW_FALLING;
            } else if (effect == BTVEffects.TERROR.get()) {
                return BTVEffects.FEARSOME.get();
            } else if (!effect.isBeneficial()) {
                return null;
            }
        } else {
            if (effect == MobEffects.HEAL) {
                return MobEffects.HARM;
            } else if(effect == MobEffects.MOVEMENT_SPEED){
                return MobEffects.MOVEMENT_SLOWDOWN;
            } else if(effect == MobEffects.DIG_SPEED){
                return MobEffects.DIG_SLOWDOWN;
            } else if(effect == MobEffects.DAMAGE_BOOST){
                return MobEffects.WEAKNESS;
            } else if(effect == MobEffects.JUMP){
                return null;
            } else if(effect == MobEffects.REGENERATION){
                return MobEffects.POISON;
            } else if(effect == MobEffects.DAMAGE_RESISTANCE){
                return BTVEffects.VULNERABILITY.get();
            } else if(effect == MobEffects.FIRE_RESISTANCE){
                return null;
            } else if(effect == MobEffects.WATER_BREATHING){
                return null;
            } else if(effect == MobEffects.INVISIBILITY){
                return null;
            } else if(effect == MobEffects.NIGHT_VISION){
                return MobEffects.BLINDNESS;
            } else if(effect == MobEffects.HEALTH_BOOST){
                return null; // TODO HEARTBREAK
            } else if(effect == MobEffects.ABSORPTION){
                return null; // TODO HEARTBREAK
            } else if(effect == MobEffects.SATURATION){
                return MobEffects.HUNGER;
            } else if(effect == MobEffects.LUCK){
                return MobEffects.UNLUCK;
            } else if(effect == MobEffects.SLOW_FALLING){
                return BTVEffects.SINK.get();
            } else if(effect == MobEffects.CONDUIT_POWER){
                return null;
            } else if(effect == MobEffects.DOLPHINS_GRACE){
                return null;
            } else if (effect == BTVEffects.FEARSOME.get()) {
                return BTVEffects.TERROR.get();
            } else if (effect.isBeneficial()) {
                return null;
            }
        }
        return effect;
    }

    private static class EffectReminiscence extends Reminiscence.TextReminiscence {

        private int type;
        private int number;

        public EffectReminiscence() {
        }

        public EffectReminiscence(String textKey, int type, int number) {
            super(textKey);
            this.type = type;
            this.number = number;
        }

        @Override
        public CompoundTag save() {
            CompoundTag tag = super.save();
            tag.putInt("type", type);
            tag.putInt("number", number);
            return tag;
        }

        @Override
        public void load(CompoundTag tag) {
            super.load(tag);
            type = tag.getInt("type");
            number = tag.getInt("number");
        }

        @Override
        public Component getText() {
            return Component.translatable(getTextKey() + "." + type, number);
        }
    }


}
