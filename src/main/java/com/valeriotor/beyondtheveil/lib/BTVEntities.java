package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BTVEntities {

    public static boolean isFearlessEntity(LivingEntity entity) {
        return entity instanceof DeepOneEntity; // TODO bosses too?
    }

    public static boolean isScaryEntity(Entity entity) {
        // Transformed players
        return entity instanceof DeepOneEntity;
    }

    public static void moveEntity(LivingEntity scared, Entity scary) {
        double xDist = scared.getX() - scary.getX();
        double zDist = scared.getZ() - scary.getZ();
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
        if (dist != 0) {
            if (scared instanceof ServerPlayer player) {
                Messages.sendToPlayer(GenericToClientPacket.movePlayer(xDist / dist * 1.2, 0, zDist / dist * 1.2), player);
            } else {
                scared.setDeltaMovement(new Vec3(xDist / dist, 0, zDist / dist));
            }
        }
    }
}
