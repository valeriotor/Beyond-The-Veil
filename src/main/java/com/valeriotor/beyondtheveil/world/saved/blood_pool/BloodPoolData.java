package com.valeriotor.beyondtheveil.world.saved.blood_pool;

import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class BloodPoolData extends SavedData {

    private static final int MAX_ENTITIES_PER_ROW = 1000;

    public static BloodPoolData getInstance(ServerLevel sl) {
        return sl.getServer().overworld().getDataStorage().computeIfAbsent(BloodPoolData::load, BloodPoolData::create, "bloodPoolData");
    }

    public static BloodPoolData getInstance(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(BloodPoolData::load, BloodPoolData::create, "bloodPoolData");
    }

    public static BloodPoolData load(CompoundTag mainTag) {
        BloodPoolData bloodPoolData = create();
        CompoundTag playerToPoolTag = mainTag.getCompound("playerToPoolTag");
        for (String key : playerToPoolTag.getAllKeys()) {
            UUID uuid = UUID.fromString(key);
            Map<ColorTriplet, List<BloodPoolEntity>> map = new HashMap<>();
            bloodPoolData.pools.put(uuid, map);
            CompoundTag forPlayer = playerToPoolTag.getCompound(key);
            for (String tripletKey : forPlayer.getAllKeys()) {
                CompoundTag forTriplet = forPlayer.getCompound(tripletKey);
                ColorTriplet triplet = ColorTriplet.fromTag(forTriplet);
                List<Tuple<Integer, BloodPoolEntity>> entityList = new ArrayList<>();
                for (String number : forTriplet.getAllKeys()) {
                    if (StringUtils.isNumeric(number)) {
                        int i = Integer.parseInt(number);
                        BloodPoolEntity entity = new BloodPoolEntity(forTriplet.getCompound(number));
                        entityList.add(new Tuple<>(i, entity));
                    }
                }
                List<BloodPoolEntity> entities = entityList.stream().sorted(Comparator.comparing(Tuple::getA)).map(Tuple::getB).collect(Collectors.toCollection(LinkedList::new));
                map.put(triplet, entities);
            }
        }
        if (mainTag.contains("archePlayerTag", Tag.TAG_COMPOUND)) {
            CompoundTag archePlayerTag = mainTag.getCompound("archePlayerTag");
            for (String uuid : archePlayerTag.getAllKeys()) {
                bloodPoolData.playerMetaData.put(UUID.fromString(uuid), new BloodPoolPlayerData(archePlayerTag.getCompound(uuid)));
            }
        }
        return bloodPoolData;
    }

    @NotNull
    private static BloodPoolData create() {
        return new BloodPoolData();
    }

    private final Map<UUID, Map<ColorTriplet, List<BloodPoolEntity>>> pools = new HashMap<>();
    private final Map<UUID, BloodPoolPlayerData> playerMetaData = new HashMap<>();

    public void everyTenTicks(ServerPlayer player) {
        BloodPoolPlayerData metadata = playerMetaData.computeIfAbsent(player.getUUID(), uuid -> new BloodPoolPlayerData());
        if (metadata.levelIncreasedMessage) {
            metadata.levelIncreasedMessage = false;
            player.sendSystemMessage(Component.translatable("gui.blood_pool.levelup"));

        }
    }

    public void setArchePlayer(UUID playerId, boolean arche) {
        playerMetaData.computeIfAbsent(playerId, uuid -> new BloodPoolPlayerData()).arche = arche;
    }

    public boolean isArchePlayer(UUID playerId) {
        return playerMetaData.computeIfAbsent(playerId, uuid -> new BloodPoolPlayerData()).arche;
    }

    private static final List<Tuple<Integer, Integer>> MULTIPLIER_THRESHOLDS = List.of(
            new Tuple<>(30, 2),
            new Tuple<>(100, 3),
            new Tuple<>(200, 4),
            new Tuple<>(350, 5)
            );

    public int getBaseMultiplier(UUID playerId) {
        BloodPoolPlayerData bloodPoolPlayerData = playerMetaData.computeIfAbsent(playerId, uuid -> new BloodPoolPlayerData());
        int returnValue = 1;
        for (Tuple<Integer, Integer> threshold : MULTIPLIER_THRESHOLDS) {
            if (bloodPoolPlayerData.totalAdditions > threshold.getA()) {
                returnValue = threshold.getB();
            } else {
                break;
            }
        }
        return returnValue;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag playerToPoolTag = new CompoundTag();
        for (UUID uuid : pools.keySet()) {
            forPlayer(uuid, playerToPoolTag);
        }
        tag.put("playerToPoolTag", playerToPoolTag);
        CompoundTag archePlayerTag = new CompoundTag();
        for (Map.Entry<UUID, BloodPoolPlayerData> e : playerMetaData.entrySet()) {
            archePlayerTag.put(e.getKey().toString(), e.getValue().save(new CompoundTag()));
        }
        tag.put("archePlayerTag", archePlayerTag);
        return tag;
    }

    /** This is for data that gets synced to client
     */
    public void forPlayer(UUID uuid, CompoundTag tag) {
        CompoundTag forPlayer = new CompoundTag();
        for (Map.Entry<ColorTriplet, List<BloodPoolEntity>> entry2 : pools.computeIfAbsent(uuid, uuid1 -> new HashMap<>()).entrySet()) {
            CompoundTag forTriplet = new CompoundTag();
            forPlayer.put(entry2.getKey().toString(), forTriplet);
            entry2.getKey().saveToTag(forTriplet);
            List<BloodPoolEntity> value = entry2.getValue();
            for (int i = 0; i < value.size(); i++) {
                BloodPoolEntity entity = value.get(i);
                forTriplet.put(String.valueOf(i), entity.save());
            }
        }
        tag.put(uuid.toString(), forPlayer);
    }

    public boolean addEntity(UUID playerId, ColorTriplet triplet, BloodPoolEntity entity, Level level, boolean increaseAdditions) {
        Map<ColorTriplet, List<BloodPoolEntity>> map = pools.computeIfAbsent(playerId, id -> new HashMap<>());
        List<BloodPoolEntity> entities = map.computeIfAbsent(triplet, t -> new LinkedList<>());
        if (entities.size() < MAX_ENTITIES_PER_ROW) {
            entities.add(entity);
            if (!level.isClientSide && level instanceof ServerLevel sl && sl.getPlayerByUUID(playerId) instanceof ServerPlayer sp) {
                CompoundTag tag = new CompoundTag();
                tag.putString("modification", PoolModification.ADD.name());
                tag.putString("UUID", playerId.toString());
                tag.put("color", triplet.saveToTag(new CompoundTag()));
                tag.put("entity", entity.save());
                Messages.sendToPlayer(GenericToClientPacket.modifyBloodPool(tag), sp);
                if (increaseAdditions) {
                    BloodPoolPlayerData metadata = playerMetaData.computeIfAbsent(playerId, uuid -> new BloodPoolPlayerData());
                    for (Tuple<Integer, Integer> multiplierThreshold : MULTIPLIER_THRESHOLDS) {
                        if (metadata.totalAdditions == multiplierThreshold.getA()) {
                            metadata.levelIncreasedMessage = true;
                        }
                    }
                    metadata.totalAdditions++;
                }
            }
            setDirty();
            return true;
        }
        return false;
    }

    public Map<ColorTriplet, List<BloodPoolEntity>> getEntitiesByTriplet(UUID uuid) {
        return pools.computeIfAbsent(uuid, uuid1 -> new HashMap<>());
    }

    public Optional<BloodPoolEntity> takeFirst(UUID playerId, ColorTriplet triplet, Level level) {
        List<BloodPoolEntity> list = pools.getOrDefault(playerId, new HashMap<>()).getOrDefault(triplet, new LinkedList<>());
        if (list.isEmpty()) {
            return Optional.empty();
        }
        Optional<BloodPoolEntity> remove = Optional.of(list.get(0));
        if (level instanceof ServerLevel sl && sl.getPlayerByUUID(playerId) instanceof ServerPlayer sp) {
            CompoundTag tag = new CompoundTag();
            tag.putString("UUID", playerId.toString());
            tag.putString("modification", PoolModification.TAKE_FIRST.name());
            tag.put("color", triplet.saveToTag(new CompoundTag()));
            Messages.sendToPlayer(GenericToClientPacket.modifyBloodPool(tag), sp);
        }
        setDirty();
        return remove;
    }

    /** Only called client side */
    public void modifyPool(Level level, CompoundTag tag) {
        String modification = tag.getString("modification");
        PoolModification poolModification = PoolModification.valueOf(modification);
        UUID uuid = UUID.fromString(tag.getString("UUID"));
        ColorTriplet color = ColorTriplet.fromTag(tag.getCompound("color"));
        if (poolModification == PoolModification.ADD) {
            BloodPoolEntity entity = new BloodPoolEntity(tag.getCompound("entity"));
            addEntity(uuid, color, entity, level, false);
        } else if (poolModification == PoolModification.TAKE_FIRST) {
            takeFirst(uuid, color, level);
        } else if (poolModification == PoolModification.TAKE_UUID) {
            UUID entityUUID = tag.getUUID("entityUUID");
            takeUuid(uuid, color, level, entityUUID);
        }
    }

    private Optional<BloodPoolEntity> takeUuid(UUID playerId, ColorTriplet triplet, Level level, UUID entityUUID) {
        List<BloodPoolEntity> entitiesInRow = pools.getOrDefault(playerId, new HashMap<>()).getOrDefault(triplet, new LinkedList<>());
        if (entitiesInRow.isEmpty()) {
            return Optional.empty();
        }
        for (Iterator<BloodPoolEntity> iterator = entitiesInRow.iterator(); iterator.hasNext(); ) {
            BloodPoolEntity bloodPoolEntity = iterator.next();
            if (bloodPoolEntity.getUuid().equals(entityUUID)) {
                iterator.remove();
                if (level instanceof ServerLevel sl && sl.getPlayerByUUID(playerId) instanceof ServerPlayer sp) {
                    CompoundTag tag = new CompoundTag();
                    tag.putString("UUID", playerId.toString());
                    tag.putString("modification", PoolModification.TAKE_UUID.name());
                    tag.put("color", triplet.saveToTag(new CompoundTag()));
                    tag.putUUID("entityUUID", entityUUID);
                    Messages.sendToPlayer(GenericToClientPacket.modifyBloodPool(tag), sp);
                }
                return Optional.of(bloodPoolEntity);
            }
        }
        setDirty();
        return Optional.empty();
    }

    public void spawnEntity(ServerPlayer player, UUID playerUUID, ColorTriplet triplet, UUID entityUUID) {
        Optional<BloodPoolEntity> bloodPoolEntity = takeUuid(playerUUID, triplet, player.level(), entityUUID);
        bloodPoolEntity.ifPresent(e -> e.spawn(player));
    }

    public void spawnFirst(ServerPlayer player, UUID playerUUID, ColorTriplet triplet) {
        Optional<BloodPoolEntity> bloodPoolEntity = takeFirst(playerUUID, triplet, player.level());
        bloodPoolEntity.ifPresent(e -> e.spawn(player));
    }

    public enum PoolModification {
        ADD, TAKE_FIRST, TAKE_UUID
    }

    private static class BloodPoolPlayerData {
        private boolean arche;
        private int totalAdditions;
        private boolean levelIncreasedMessage;

        private BloodPoolPlayerData() {

        }

        private BloodPoolPlayerData(CompoundTag tag) {
            arche = tag.getBoolean("arche");
            totalAdditions = tag.getInt("level");
            levelIncreasedMessage = tag.getBoolean("levelIncreasedMessage");
        }

        private CompoundTag save(CompoundTag tag) {
            tag.putBoolean("arche", arche);
            tag.putInt("level", totalAdditions);
            tag.putBoolean("levelIncreasedMessage", levelIncreasedMessage);
            return tag;
        }
    }



}
