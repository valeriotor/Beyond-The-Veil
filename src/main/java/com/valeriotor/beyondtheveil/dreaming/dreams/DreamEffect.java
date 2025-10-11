package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import java.util.function.Supplier;

public class DreamEffect extends Dream {

    public DreamEffect(Memory memory) {
        super(memory, 3, EffectReminiscence::new);
    }

    @Override
    public boolean activate(Player p, Level l) {
        boolean consumeVoid = DreamHandler.consumeVoid(p);
        int i = powerEffects(p, consumeVoid);
        DataUtil.addReminiscence(p, memory.getDataName(consumeVoid), new EffectReminiscence("reminiscence." + memory.getDataName(), 0, i));
        return true;
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        boolean consumeVoid = DreamHandler.consumeVoid(caster);
        int i = powerEffects(target, consumeVoid);
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
                int i = powerEffects(e, consumeVoid);
                if (i > 0) {
                    count++;
                }
            }
        }
        DataUtil.addReminiscence(p, memory.getDataName(consumeVoid), new EffectReminiscence("reminiscence." + memory.getDataName(), 2, count));
        return true;
    }

    private int powerEffects(LivingEntity e, boolean hasVoid) {
        int count = 0;
        Collection<MobEffectInstance> effects = e.getActiveEffects();
        List<MobEffectInstance> newEffects = new ArrayList<>();
        for (MobEffectInstance effect : effects) {
            int newAmplifier = effect.getAmplifier() + (memory == Memory.POWER ? (hasVoid ? 2 : 1) : 0);
            newAmplifier = Math.max(effect.getAmplifier(), Math.min(newAmplifier, 10));
            if (effect.getEffect() == MobEffects.DAMAGE_RESISTANCE) {
                newAmplifier = Math.max(effect.getAmplifier(), Math.min(newAmplifier, 4));
            }
            int newDuration = (effect.getDuration() + (memory == Memory.STILLNESS ? (hasVoid ? 60 : 30) : 0)) * (memory == Memory.STILLNESS ? (hasVoid ? 4 : 2) : 1);
            if (newAmplifier > effect.getAmplifier() || newDuration > effect.getDuration()) {
                count++;
            }
            newEffects.add(new MobEffectInstance(effect.getEffect(), newDuration, newAmplifier, effect.isAmbient(), effect.isVisible(), effect.showIcon()));
        }
        newEffects.forEach(e::addEffect);
        return count;
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
