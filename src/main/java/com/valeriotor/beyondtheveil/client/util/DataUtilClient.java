package com.valeriotor.beyondtheveil.client.util;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.networking.SyncPlayerDataPacket;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class DataUtilClient {

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void loadPlayerDataNBT(CompoundTag data) {
        if (Minecraft.getInstance().player != null) {
            Optional<PlayerData> resolve = Minecraft.getInstance().player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve();
            resolve.ifPresent(playerData -> playerData.loadFromNBT(data));
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

    public static void setIntAndSync(String key, int value, boolean temporary) {
        setInt(key, value, temporary);
        Messages.sendToServer(SyncPlayerDataPacket.toServer(key).setInt(value));
    }


}
