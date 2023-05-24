package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SyncPlayerDataPacket;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class DataUtil {

    public static void createWaypoint(Player p, WaypointType type, int time, BlockPos pos) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setLong(type.posDataKey, pos.asLong(), false);
            playerData.createCounter(type, time);
            Messages.sendToPlayer(GenericToClientPacket.createWaypoint(type, pos, 0), (ServerPlayer) p);
        });
    }

    public static void setBooleanOnServerAndSync(Player p, String key, boolean value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setBoolean(key, value, temporary);
            Messages.sendToPlayer(SyncPlayerDataPacket.toClient(key).setBoolean(value), (ServerPlayer) p);
            if (value) {
                ResearchUtil.markResearchAsUpdated(p, key);
            }
        });
    }

    public static void setStringOnServerAndSync(Player p, String key, String value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setString(key, value, temporary);
            Messages.sendToPlayer(SyncPlayerDataPacket.toClient(key).setString(value), (ServerPlayer) p);
        });
    }

    public static void setIntOnServerAndSync(Player p, String key, int value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setInteger(key, value, temporary);
            Messages.sendToPlayer(SyncPlayerDataPacket.toClient(key).setInt(value), (ServerPlayer) p);
        });
    }

    public static void setLongOnServerAndSync(Player p, String key, long value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setLong(key, value, temporary);
            Messages.sendToPlayer(SyncPlayerDataPacket.toClient(key).setLong(value), (ServerPlayer) p);
        });
    }

    public static void setBoolean(Player p, String key, boolean value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setBoolean(key, value, temporary);
            if (value) {
                ResearchUtil.markResearchAsUpdated(p, key);
            }
        });
    }

    public static void setString(Player p, String key, String value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setString(key, value, temporary);
        });
    }

    public static void setInt(Player p, String key, int value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setInteger(key, value, temporary);
        });
    }

    public static void setLong(Player p, String key, long value, boolean temporary) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.setLong(key, value, temporary);
        });
    }

    public static Integer getOrSetIntegerOnServerAndSync(Player p, String key, int valueIfAbsent, boolean temporary) {
        Integer value = getOrSetInteger(p, key, valueIfAbsent, temporary);
        if (value != null) {
            Messages.sendToPlayer(SyncPlayerDataPacket.toClient(key).setInt(value), (ServerPlayer) p);
        }
        return value;
    }

    public static Integer getOrSetInteger(Player p, String key, int valueIfAbsent, boolean temporary) {
        Optional<PlayerData> capability = p.getCapability(PlayerDataProvider.PLAYER_DATA, null).resolve();
        Integer value = null;
        if (capability.isPresent()) {
            value = capability.get().getOrSetInteger(key, valueIfAbsent, temporary);
        }
        return value;
    }

    public static Integer incrementOrSetIntegerOnServerAndSync(Player p, String key, int amount, int valueIfAbsent, boolean temporary) {
        Integer value = incrementOrSetInteger(p, key, amount, valueIfAbsent, temporary);
        if (value != null) {
            Messages.sendToPlayer(SyncPlayerDataPacket.toClient(key).setInt(value), (ServerPlayer) p);
        }
        return value;
    }

    public static Integer incrementOrSetInteger(Player p, String key, int amount, int valueIfAbsent, boolean temporary) {
        Optional<PlayerData> capability = p.getCapability(PlayerDataProvider.PLAYER_DATA, null).resolve();
        Integer value = null;
        if (capability.isPresent()) {
            value = capability.get().incrementOrSetInteger(key, amount, valueIfAbsent, temporary);
        }
        return value;
    }

    public static boolean getBoolean(Player p, String key) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY).getBoolean(key);
    }

    public static String getString(Player p, String key) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY).getString(key);
    }

    public static int getInt(Player p, String key) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY).getInteger(key);
    }

    public static long getLong(Player p, String key) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY).getLong(key);
    }

    public static boolean hasString(Player p, String key) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY).getString(key) != null;
    }

    public static boolean hasInt(Player p, String key) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY).getInteger(key) != null;
    }

    public static boolean hasLong(Player p, String key) {
        return p.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY).getLong(key) != null;
    }


    public static void removeString(Player p, String key) {
        p.getCapability(PlayerDataProvider.PLAYER_DATA, null).ifPresent(playerData -> {
            playerData.removeString(key);
        });
    }
}
