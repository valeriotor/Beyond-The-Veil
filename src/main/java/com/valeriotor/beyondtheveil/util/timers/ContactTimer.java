package com.valeriotor.beyondtheveil.util.timers;

import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class ContactTimer extends PlayerTimer {
    private int time;
    private boolean done;
    private static final Map<Integer, DeepOneEntity.ContactType> SPAWN_TIMERS = Map.of(50, DeepOneEntity.ContactType.MOVE1, 110, DeepOneEntity.ContactType.MOVE2, 150, DeepOneEntity.ContactType.MOVE3);

    public ContactTimer() {
        super(Integer.MAX_VALUE, "contact", null, new HashMap<>());
        time = 0;
    }

    @Override
    public boolean update(Player player) {
        time++;
        DeepOneEntity.ContactType contactType = SPAWN_TIMERS.get(time);
        if (contactType != null) {
            DeepOneEntity deepOne = new DeepOneEntity(BTVEntities.DEEP_ONE.get(), player.level());
            deepOne.setContact(contactType, player);
            double x = player.getX() + Math.sin(contactType.getStartOffset() * Math.PI / 50) * contactType.getFactor();
            double z = player.getZ() + Math.cos(contactType.getStartOffset() * Math.PI / 50) * contactType.getFactor();
            deepOne.setPos(x, player.getY() - 0.5, z);
            player.level().addFreshEntity(deepOne);
        }
        if (time % 20 == 0) {
            Messages.sendToPlayer(GenericToClientPacket.renewContact(), (ServerPlayer) player);
        }
        if (time > 200) {
            done = true;
        }
        return super.update(player);
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
