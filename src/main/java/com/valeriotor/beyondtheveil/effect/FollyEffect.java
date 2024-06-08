package com.valeriotor.beyondtheveil.effect;

import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FollyEffect extends MobEffect {

    public FollyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        float yRot = pLivingEntity.getRandom().nextInt(360) - 180;
        float xRot = pLivingEntity.getRandom().nextInt(180) - 90;
        if (pLivingEntity instanceof ServerPlayer p) {
            Messages.sendToPlayer(GenericToClientPacket.rotateCamera(yRot, xRot, 20), (ServerPlayer) p);
        } else {
            pLivingEntity.setYRot(yRot);
            pLivingEntity.setXRot(xRot);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        pAmplifier = Math.min(pAmplifier, 3) + 1;
        return pDuration % (84 / pAmplifier) == 0;
    }
}
