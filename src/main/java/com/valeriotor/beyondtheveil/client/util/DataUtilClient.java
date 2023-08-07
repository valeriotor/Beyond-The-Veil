package com.valeriotor.beyondtheveil.client.util;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerData.Counter;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Dream;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SyncPlayerDataPacket;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.WaypointType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Optional;

public class DataUtilClient {

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void askSyncToServer() {

    }

    public static void loadPlayerDataNBT(CompoundTag data) {
        if (Minecraft.getInstance().player != null) {
            ClientData.newInstance();
            Optional<PlayerData> resolve = Minecraft.getInstance().player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
            PlayerData playerData = resolve.get();
            playerData.loadFromNBT(data);
            List<Counter> counters = playerData.getCounters();
            for (Counter c : counters) {
                if (c.getType() instanceof WaypointType waypointType) {
                    ClientData.getInstance().addWaypoint(waypointType, BlockPos.of(playerData.getLong(waypointType.posDataKey)), waypointType.unlocalizedName);
                }
            }
        }
    }

    public static void loadReminiscences(CompoundTag tag) {
        if (Minecraft.getInstance().player != null) {
            DataUtil.clearReminiscences(getPlayer());
            for (String key : tag.getAllKeys()) {
                Memory m = Memory.getMemoryFromDataName(key);
                Reminiscence r = Dream.REMINISCENCE_REGISTRY.get(m).get();
                r.load(tag.getCompound(key));
                DataUtil.addReminiscence(Minecraft.getInstance().player, m, r);
            }
        }
    }

    public static void setBoolean(String key, boolean value, boolean temporary) {
        if (Minecraft.getInstance().player != null) {
            DataUtil.setBoolean(Minecraft.getInstance().player, key, value, temporary);
        }
    }

    public static void setString(String key, String value, boolean temporary) {
        if (Minecraft.getInstance().player != null) {
            DataUtil.setString(Minecraft.getInstance().player, key, value, temporary);
        }
    }

    public static void setInt(String key, int value, boolean temporary) {
        if (Minecraft.getInstance().player != null) {
            DataUtil.setInt(Minecraft.getInstance().player, key, value, temporary);
        }
    }

    public static void setLong(String key, long value, boolean temporary) {
        if (Minecraft.getInstance().player != null) {
            DataUtil.setLong(Minecraft.getInstance().player, key, value, temporary);
        }
    }

    public static void removeString(String key) {
        if (Minecraft.getInstance().player != null) {
            DataUtil.removeString(Minecraft.getInstance().player, key);
        }
    }

    public static void setBooleanAndSync(String key, boolean value, boolean temporary) {
        setBoolean(key, value, temporary);
        Messages.sendToServer(SyncPlayerDataPacket.toServer(key).setBoolean(value));
    }

    public static void setStringAndSync(String key, String value, boolean temporary) {
        setString(key, value, temporary);
        Messages.sendToServer(SyncPlayerDataPacket.toServer(key).setString(value));
    }

    public static void setIntAndSync(String key, int value, boolean temporary) {
        setInt(key, value, temporary);
        Messages.sendToServer(SyncPlayerDataPacket.toServer(key).setInt(value));
    }

    public static void removeStringAndSync(String key) {
        removeString(key);
        Messages.sendToServer(SyncPlayerDataPacket.toServer(key).removeString());
    }

}
