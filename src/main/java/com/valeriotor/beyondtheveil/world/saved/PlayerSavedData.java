package com.valeriotor.beyondtheveil.world.saved;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerSavedData extends SavedData {

    private final Map<UUID, DeathPoint> deaths = new HashMap<>();


    public static PlayerSavedData getInstance(ServerLevel sl) {
        return sl.getDataStorage().computeIfAbsent(PlayerSavedData::load, PlayerSavedData::create, "playerSavedData");
    }

    private static PlayerSavedData load(CompoundTag tag) {
        PlayerSavedData data = create();
        CompoundTag deathTag = tag.getCompound("deaths");
        for (String key : deathTag.getAllKeys()) {
            data.deaths.put(UUID.fromString(key), DeathPoint.fromNBT(deathTag.getCompound(key)));
        }
        return data;
    }

    @NotNull
    private static PlayerSavedData create() {
        return new PlayerSavedData();
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        CompoundTag deathTag = new CompoundTag();
        pCompoundTag.put("deaths", deathTag);
        for (Map.Entry<UUID, DeathPoint> entry : deaths.entrySet()) {
            deathTag.put(entry.getKey().toString(), entry.getValue().saveToNBT());
        }
        return pCompoundTag;
    }

    public void death(ServerPlayer sp) {
        BlockPos pos = sp.blockPosition();
        deaths.put(sp.getUUID(), new DeathPoint(pos.getX(), pos.getY(), pos.getZ(), sp.level().dimension()));
        setDirty();
    }

    public BlockPos closestDeathExcluding(ServerPlayer sp) {
        //List<DeathPoint> deathPoints = deaths.values().stream().filter(d -> Objects.equals(d.dimension(), sp.level().dimension())).sorted(Comparator.comparingDouble(d -> sp.blockPosition().distSqr(new Vec3i(d.x, d.y, d.z)))).toList();
        List<DeathPoint> deathPoints = deaths.entrySet().stream().filter(d -> !sp.getUUID().equals(d.getKey()) && Objects.equals(d.getValue().dimension(), sp.level().dimension())).sorted(Comparator.comparingDouble(d -> sp.blockPosition().distSqr(new Vec3i(d.getValue().x, d.getValue().y, d.getValue().z)))).map(Map.Entry::getValue).toList();
        if (!deathPoints.isEmpty()) {
            DeathPoint closest = deathPoints.get(0);
            return new BlockPos(closest.x, closest.y, closest.z);
        }
        return null;
    }

    public BlockPos lastDeathFor(Player p) {
        DeathPoint deathPoint = deaths.get(p.getUUID());
        if (deathPoint != null) {
            return new BlockPos(deathPoint.x, deathPoint.y, deathPoint.z);
        }
        return null;
    }


    private record DeathPoint(int x, int y, int z, ResourceKey<Level> dimension) {

        private CompoundTag saveToNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putInt("x", x);
            tag.putInt("y", y);
            tag.putInt("z", z);
            tag.putString("dimension", dimension.location().getPath());
            return tag;
        }

        private static DeathPoint fromNBT(CompoundTag tag) {
            return new DeathPoint(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"), ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("dimension"))));
        }

    }
}
