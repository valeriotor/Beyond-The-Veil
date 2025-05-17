package com.valeriotor.beyondtheveil.util.timers;

import com.valeriotor.beyondtheveil.entity.CanoeEntity;
import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class ContactTimer extends PlayerTimer {
    private int time;
    private boolean done;
    private static final Map<Integer, DeepOneEntity.ContactType> SPAWN_TIMERS = Map.of(100, DeepOneEntity.ContactType.MOVE1, 170, DeepOneEntity.ContactType.MOVE2, 210, DeepOneEntity.ContactType.MOVE3);

    public ContactTimer() {
        super(Integer.MAX_VALUE, "contact", null, new HashMap<>());
        time = 0;
    }

    @Override
    public boolean update(Player player) {
        time++;
        if(player.getVehicle() instanceof CanoeEntity canoe) {
            DeepOneEntity.ContactType contactType = SPAWN_TIMERS.get(time);
            if (contactType != null) {
                DeepOneEntity deepOne = new DeepOneEntity(BTVEntities.DEEP_ONE.get(), player.level());
                deepOne.setContact(contactType, player);
                double radius = 55D / contactType.getFactor() / 2;
                double x = player.getX() + Math.sin(contactType.getStartOffset() * Math.PI / 50 + canoe.getYRot()) * radius;
                double z = player.getZ() + -Math.cos(contactType.getStartOffset() * Math.PI / 50 + canoe.getYRot()) * radius;
                deepOne.setPos(x, player.getY() - 2.2, z);
                deepOne.setExtraCounterOffset((int) (canoe.getYRot() * 50 / Math.PI));
                player.level().addFreshEntity(deepOne);
            }
            if (time % 20 == 0) {
                Messages.sendToPlayer(GenericToClientPacket.renewContact(), (ServerPlayer) player);
            }
            if (time == 290) {
                DeepOneEntity deepOne = new DeepOneEntity(BTVEntities.DEEP_ONE.get(), player.level());
                double x = player.getX();// + Math.sin(contactType.getStartOffset() * Math.PI / 50) * radius;
                double z = player.getZ();// + -Math.cos(contactType.getStartOffset() * Math.PI / 50) * radius;
                deepOne.setPos(x, player.getY() - 1.2, z);
                player.level().addFreshEntity(deepOne);
                canoe.startRiding(deepOne);
                deepOne.setContact(DeepOneEntity.ContactType.TRADE, player);
            }
            if (time == 299) {
                player.level().playSound(null, player.getOnPos(), SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, SoundSource.NEUTRAL, 1, 1);
                Messages.sendToPlayer(GenericToClientPacket.shakeCamera(), (ServerPlayer) player);
            }
        }
        if (time > 400) {
            done = true;
        }
        return super.update(player);
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
