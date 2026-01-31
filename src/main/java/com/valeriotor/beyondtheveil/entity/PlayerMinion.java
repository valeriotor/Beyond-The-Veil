package com.valeriotor.beyondtheveil.entity;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public interface PlayerMinion {

    UUID getMasterID();

    default ServerPlayer getMaster() {
        if (this instanceof LivingEntity entity) {
            MinecraftServer server = entity.getServer();
            UUID masterID = getMasterID();
            if (server != null && masterID != null) {
                return server.getPlayerList().getPlayer(masterID);
            }
        }
        return null;
    }

    void setMasterID(UUID uuid);

    default void setMaster(Player p) {
        setMasterID(p.getUUID());
    }

}
