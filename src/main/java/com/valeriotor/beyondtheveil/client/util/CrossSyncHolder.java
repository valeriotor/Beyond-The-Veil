package com.valeriotor.beyondtheveil.client.util;

import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrossSyncHolder {

    private static final Map<UUID, CrossSync> holder = new HashMap<>();

    public static void setCrossSync(CompoundTag tag) {
        UUID uuid = UUID.fromString(tag.getString("id"));
        CompoundTag crossSyncTag = tag.getCompound("cross_sync");
        if (!holder.containsKey(uuid)) {
            holder.put(uuid, new CrossSync());
        }
        holder.get(uuid).loadFromNBT(crossSyncTag);
    }

    public static void stopCrossSync(CompoundTag tag) {
        UUID uuid = UUID.fromString(tag.getString("id"));
        holder.remove(uuid);
    }

    public static CrossSync getCrossSync(Player player) {
        return holder.get(player.getUUID());
    }


}
