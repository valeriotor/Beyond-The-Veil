package com.valeriotor.beyondtheveil.entity;

import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.world.entity.Mob;

public interface AnimatedTalkable extends Talkable {

    void toggleDialogueAnimation(boolean start);

    default void animationFromServer(boolean start) {
        if (this instanceof Mob mob) {
            Messages.sendToTracking(GenericToClientPacket.dialogueAnimation(mob.getId(), start), mob);
        }
    }

}
