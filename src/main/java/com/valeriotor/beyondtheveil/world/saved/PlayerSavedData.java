package com.valeriotor.beyondtheveil.world.saved;

import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.rituals.bindings.BindingEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerSavedData extends SavedData {

    private final Map<UUID, BloodPoint> deaths = new HashMap<>();
    private final Map<UUID, BloodPoint> respawns = new HashMap<>();
    private final Map<UUID, List<ArcheBindingNode>> archieBindingNodes = new HashMap<>();


    public static PlayerSavedData getInstance(ServerLevel sl) {
        return sl.getDataStorage().computeIfAbsent(PlayerSavedData::load, PlayerSavedData::create, "playerSavedData");
    }

    private static PlayerSavedData load(CompoundTag tag) {
        PlayerSavedData data = create();
        CompoundTag deathTag = tag.getCompound("deaths");
        for (String key : deathTag.getAllKeys()) {
            data.deaths.put(UUID.fromString(key), BloodPoint.fromNBT(deathTag.getCompound(key)));
        }

        CompoundTag respawnTag = tag.getCompound("respawns");
        for (String key : respawnTag.getAllKeys()) {
            data.respawns.put(UUID.fromString(key), BloodPoint.fromNBT(respawnTag.getCompound(key)));
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
        for (Map.Entry<UUID, BloodPoint> entry : deaths.entrySet()) {
            deathTag.put(entry.getKey().toString(), entry.getValue().saveToNBT());
        }
        CompoundTag respawnTag = new CompoundTag();
        pCompoundTag.put("respawns", respawnTag);
        for (Map.Entry<UUID, BloodPoint> entry : respawns.entrySet()) {
            respawnTag.put(entry.getKey().toString(), entry.getValue().saveToNBT());
        }
        return pCompoundTag;
    }

    public void death(ServerPlayer sp) {
        BlockPos pos = sp.blockPosition();
        deaths.put(sp.getUUID(), new BloodPoint(pos.getX(), pos.getY(), pos.getZ(), sp.level().dimension()));
        setDirty();
    }

    public void respawn(ServerPlayer sp) {
        BlockPos pos = sp.blockPosition();
        respawns.put(sp.getUUID(), new BloodPoint(pos.getX(), pos.getY(), pos.getZ(), sp.level().dimension()));
        setDirty();
    }

    public void removeDeath(UUID uuid) {
        deaths.remove(uuid);
        setDirty();
    }

    public void removeRespawn(UUID uuid) {
        respawns.remove(uuid);
        setDirty();
    }

    public List<BlockPos> deathsInRange(ServerPlayer sp, double range) {
        return deaths.values().stream().filter(d -> Objects.equals(d.dimension(), sp.level().dimension())).map(d -> new BlockPos(d.x, d.y, d.z)).filter(pos -> sp.blockPosition().distSqr(pos) < range * range).toList();
    }

    public List<BlockPos> respawnsInRange(ServerPlayer sp, double range) {
        return respawns.values().stream().filter(d -> Objects.equals(d.dimension(), sp.level().dimension())).map(d -> new BlockPos(d.x, d.y, d.z)).filter(pos -> sp.blockPosition().distSqr(pos) < range * range).toList();
    }

    public Triple<UUID, BlockPos, Boolean> closestBloodPointExcluding(ServerPlayer sp) {
        BlockPos pos = sp.blockPosition();
        Tuple<UUID, BlockPos> death = closestBloodPointExcluding(sp, true);
        Tuple<UUID, BlockPos> respawn = closestBloodPointExcluding(sp, false);
        if (death != null) {
            if (respawn == null) {
                return Triple.of(death.getA(), death.getB(), true);
            } else {
                if (pos.distSqr(death.getB()) < pos.distSqr(respawn.getB())) {
                    return Triple.of(death.getA(), death.getB(), true);
                } else {
                    return Triple.of(respawn.getA(), respawn.getB(), false);
                }
            }
        } else if (respawn != null) {
            return Triple.of(respawn.getA(), respawn.getB(), false);
        }
        return null;
    }

    private Tuple<UUID, BlockPos> closestBloodPointExcluding(ServerPlayer sp, boolean deaths) {
        //List<DeathPoint> deathPoints = deaths.values().stream().filter(d -> Objects.equals(d.dimension(), sp.level().dimension())).sorted(Comparator.comparingDouble(d -> sp.blockPosition().distSqr(new Vec3i(d.x, d.y, d.z)))).toList();
        Map<UUID, BloodPoint> map = deaths ? this.deaths : respawns;
        Optional<Tuple<UUID, BlockPos>> closest = map.entrySet().stream()
                //.filter(e -> !sp.getUUID().equals(e.getKey()) && Objects.equals(e.getValue().dimension(), sp.level().dimension()))
                .sorted(Comparator.comparingDouble(e -> sp.blockPosition().distSqr(new Vec3i(e.getValue().x, e.getValue().y, e.getValue().z))))
                .map(e -> new Tuple<>(e.getKey(), new BlockPos(e.getValue().x, e.getValue().y, e.getValue().z))).findFirst();
        return closest.orElse(null);
    }

    public BlockPos lastDeathFor(Player p) {
        BloodPoint deathPoint = deaths.get(p.getUUID());
        if (deathPoint != null) {
            return new BlockPos(deathPoint.x, deathPoint.y, deathPoint.z);
        }
        return null;
    }

    public BlockPos lastRespawnFor(Player p) {
        BloodPoint respawnPoint = respawns.get(p.getUUID());
        if (respawnPoint != null) {
            return new BlockPos(respawnPoint.x, respawnPoint.y, respawnPoint.z);
        }
        return null;
    }

    public void tick(ServerLevel sl) {
        for (Map.Entry<UUID, List<ArcheBindingNode>> entry : archieBindingNodes.entrySet()) {
            for (Iterator<ArcheBindingNode> iterator = entry.getValue().iterator(); iterator.hasNext(); ) {
                ArcheBindingNode archeBindingNode = iterator.next();
                sl.sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, archeBindingNode.pos.getX() + 0.5, archeBindingNode.pos.getY() + 0.5, archeBindingNode.pos.getZ() + 0.5, 20, 0.3, 0.3, 0.3, 0.05);
                if (archeBindingNode.timer % 5 == 0) {
                    List<Entity> entities = sl.getEntities(((Entity) null), AABB.ofSize(archeBindingNode.pos.getCenter(), 30, 20, 30), e -> {
                        if (!(e instanceof LivingEntity)) return false;
                        if(Objects.equals(e.getUUID(), entry.getKey())) return false;
                        if (!(e instanceof PlayerMinion minion)) return true;
                        return !Objects.equals(minion.getMasterID(), entry.getKey());
                    });
                    boolean playSound = true;
                    for (Entity entity : entities) {
                        BindingEvents.moveEntityWithCenter(entity, archeBindingNode.pos.getCenter(), archeBindingNode.away, false, playSound && Math.random() < 0.015);
                        playSound = false;
                    }
                }
                if (archeBindingNode.tick()) {
                    iterator.remove();
                }
            }
        }
    }

    public void addArcheBindingNode(ServerPlayer sp, BlockPos pos) {
        List<ArcheBindingNode> archeBindingNodes = archieBindingNodes.computeIfAbsent(sp.getUUID(), uuid -> new ArrayList<>());
        if (archeBindingNodes.size() < 3) {
            archeBindingNodes.add(new ArcheBindingNode(pos, sp.isShiftKeyDown(), 20 * 60));
        }
    }


    private record BloodPoint(int x, int y, int z, ResourceKey<Level> dimension) {

        private CompoundTag saveToNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putInt("x", x);
            tag.putInt("y", y);
            tag.putInt("z", z);
            tag.putString("dimension", dimension.location().getPath());
            return tag;
        }

        private static BloodPoint fromNBT(CompoundTag tag) {
            return new BloodPoint(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"), ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("dimension"))));
        }
    }

    private static final class ArcheBindingNode {
        private final BlockPos pos;
        private final boolean away;
        private int timer;

        private ArcheBindingNode(BlockPos pos, boolean away, int startTimer) {
            this.pos = pos;
            this.away = away;
            this.timer = startTimer;
        }

        public BlockPos pos() {
            return pos;
        }

        public boolean away() {
            return away;
        }

        public boolean tick() {
            timer--;
            return timer <= 0;
        }

    }

}
