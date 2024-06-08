package com.valeriotor.beyondtheveil.effect;

import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SinkEffect extends MobEffect {

    public SinkEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity e, int pAmplifier) {
        if (e instanceof ServerPlayer player) {
            Messages.sendToPlayer(GenericToClientPacket.movePlayer(0, -(pAmplifier + 1) * 5, 0, false, true, false), player);
        } else if (!(e instanceof Player)) {
            e.setDeltaMovement(e.getDeltaMovement().x, -(pAmplifier + 1) * 5, e.getDeltaMovement().z);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
