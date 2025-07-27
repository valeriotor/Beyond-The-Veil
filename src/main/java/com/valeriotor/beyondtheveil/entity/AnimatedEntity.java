package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.client.animation.AnimationTemplate;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.world.entity.Mob;

public interface AnimatedEntity {

    void startAnimation(AnimationTemplate animationTemplate, int channel);

    default void sendAnimation(AnimationTemplate animationTemplate, int channel) {
        if (this instanceof Mob mob) {
            Messages.sendToTracking(GenericToClientPacket.startAnimation(animationTemplate, mob.getId(), channel), mob);
        }
    }

}
