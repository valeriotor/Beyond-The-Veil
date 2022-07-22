package com.valeriotor.beyondtheveil.client.util;

import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class DataUtilClient {

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


}
