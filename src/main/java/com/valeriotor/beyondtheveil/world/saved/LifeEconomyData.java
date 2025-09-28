package com.valeriotor.beyondtheveil.world.saved;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.tile.PatientPodBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.*;

public class LifeEconomyData extends SavedData {

    private final Map<BlockPos, PillarData> pillars = new HashMap<>();
    private final Map<UUID, Pair<PillarData, PillarData>> pillarsByConnection = new HashMap<>();
    private final Map<ChunkPos, List<PillarData>> pillarsByChunk = new HashMap<>();
    private final Map<BlockPos, PodData> patientPods = new HashMap<>();
    private final Map<ChunkPos, List<PodData>> patientPodsByChunk = new HashMap<>();
    private final Map<PodData, Tuple<Integer, Integer>> patientPodReservations = new HashMap<>();


    public static LifeEconomyData getInstance(ServerLevel sl) {
        return sl.getDataStorage().computeIfAbsent(LifeEconomyData::load, LifeEconomyData::create, "lifeEconomyData");
    }

    private static LifeEconomyData load(CompoundTag tag) {
        LifeEconomyData data = create();
        if (tag.contains("pillarTag")) {
            CompoundTag pillarTag = tag.getCompound("pillarTag");
            for (String key : pillarTag.getAllKeys()) {
                data.addPillarFromTag(pillarTag.getCompound(key));
            }
        }
        if (tag.contains("podTag")) {
            CompoundTag podTag = tag.getCompound("podTag");
            for (String key : podTag.getAllKeys()) {
                data.addPod(podTag.getCompound(key));
            }
        }
        return data;
    }

    @NotNull
    private static LifeEconomyData create() {
        return new LifeEconomyData();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {

        CompoundTag pillarTag = new CompoundTag();
        compoundTag.put("pillarTag", pillarTag);
        int i = 0;
        for (Map.Entry<BlockPos, PillarData> entry : pillars.entrySet()) {
            pillarTag.put(String.valueOf(i), entry.getValue().save(new CompoundTag()));
            i++;
        }

        CompoundTag podTag = new CompoundTag();
        compoundTag.put("podTag", podTag);
        i = 0;
        for (Map.Entry<BlockPos, PodData> entry : patientPods.entrySet()) {
            podTag.put(String.valueOf(i), entry.getValue().save(new CompoundTag()));
            i++;
        }
        return compoundTag;
    }

    public void addEmptyPod(BlockPos pos) {
        PodData data = new PodData(pos);
        addPod(data);
    }

    private void addPod(CompoundTag tag) {
        addPod(new PodData(tag));
    }

    private void addPod(PodData value) {
        patientPods.put(value.currentPos, value);
        for (ChunkPos chunkPos : getChunkPoses(value.currentPos, 1)) {
            patientPodsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()).add(value);
        }
        setDirty();
    }

    public void removePod(BlockPos pos) {
        patientPods.remove(pos);
        for (ChunkPos chunkPos : getChunkPoses(pos, 1)) {
            patientPodsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()).removeIf(p -> Objects.equals(pos, p.currentPos));
        }
        setDirty();
    }

    public PodData findClosestEmptyPod(BlockPos pos, int chunkRadius, double maxDist) {
        List<PodData> pods = new ArrayList<>();
        for (ChunkPos chunkPos : getChunkPoses(pos, chunkRadius)) {
            pods.addAll(patientPodsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()));
        }
        List<PodData> podData = pods.stream().filter(p -> p.getPatient() == null && p.currentPos.distSqr(pos) < maxDist * maxDist).sorted(Comparator.comparing(p -> p.currentPos.distSqr(pos))).toList();
        if (!podData.isEmpty()) {
            return podData.get(0);
        }
        return null;
    }

    public List<PodData> findClosestEmptyPods(BlockPos pos, int chunkRadius, double maxDist) {
        List<PodData> pods = new ArrayList<>();
        for (ChunkPos chunkPos : getChunkPoses(pos, chunkRadius)) {
            pods.addAll(patientPodsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()));
        }
        return pods.stream().filter(p -> p.getPatient() == null && p.currentPos.distSqr(pos) < maxDist * maxDist).sorted(Comparator.comparing(p -> p.currentPos.distSqr(pos))).toList();
    }

    public PodData getPodData(BlockPos pos) {
        return patientPods.get(pos);
    }

    public boolean isReserved(PodData data) {
        return patientPodReservations.containsKey(data);
    }

    public void reserve(PodData data, int cultistId) {
        patientPodReservations.put(data, new Tuple<>(cultistId, 150));
    }

    public void createdPillarConnection(UUID connectionId, BlockPos oldPillar, boolean isOldPillarOffer) {
        if (!pillars.containsKey(oldPillar)) { // this shouldn't happen
            addPillar(isOldPillarOffer, oldPillar, connectionId);
        } else {
            PillarData pillarData = pillars.get(oldPillar);
            UUID oldConnection = pillarData.connection; // destroy any previous connection the old pillar may have been involved in
            removeConnection(oldConnection);
            pillarData.connection = connectionId; // and replace with new one
        }
        PillarData pillarData = pillars.get(oldPillar);
        pillarsByConnection.put(connectionId, new Pair<>(isOldPillarOffer ? pillarData : null, !isOldPillarOffer ? pillarData : null));
        setDirty();
    }

    private void removeConnection(UUID oldConnection) {
        if (pillarsByConnection.containsKey(oldConnection)) {
            Pair<PillarData, PillarData> pair = pillarsByConnection.get(oldConnection);
            if (pair.getA() != null) {
                pair.getA().connection = null;
            }
            if (pair.getB() != null) {
                pair.getB().connection = null;
            }
            pillarsByConnection.remove(oldConnection);
        }
    }

    public void addPillarFromItem(BlockPos pos, ItemStack stack) {
        // three possibilities: either has no connection, or the old connection is alive and well, or the old connection was replaced and we need to remove this from the new data
        CompoundTag tag = stack.getOrCreateTag().copy();
        tag.putLong("currentPos", pos.asLong());
        boolean isOffer = stack.getItem() == Registration.OFFER_PILLAR_ITEM.get();
        tag.putBoolean("isOffer", isOffer);
        PillarData data = null;
        if (tag.contains("connection")) {
            UUID connection = tag.getUUID("connection");
            Pair<PillarData, PillarData> pair = pillarsByConnection.get(connection);
            if (pair == null) {
                tag.remove("connection");
            } else {
                PillarData other = isOffer ? pair.getB() : pair.getA();
                if (other == null) {
                    tag.remove("connection");
                } else {
                    data = new PillarData(tag);
                    pillarsByConnection.put(connection, isOffer ? new Pair<>(data, pair.getB()) : new Pair<>(pair.getA(), data));
                }
            }
        }
        if (data == null) {
            data = new PillarData(tag);
        }
        addPillar(data);
    }

    public void addPillar(boolean isOffer, BlockPos pos, UUID connection) {
        addPillar(new PillarData(isOffer, pos, connection, null, 0, 0));
    }

    private void addPillarFromTag(CompoundTag tag) {
        PillarData data = new PillarData(tag);
        if (tag.contains("connection")) {
            UUID connection = tag.getUUID("connection");
            boolean isOffer = tag.getBoolean("isOffer");
            Pair<PillarData, PillarData> pair = pillarsByConnection.get(connection);
            if (pair != null) {
                if (isOffer) {
                    pillarsByConnection.put(connection, new Pair<>(data, pair.getB()));
                } else {
                    pillarsByConnection.put(connection, new Pair<>(pair.getA(), data));
                }
            } else {
                if (isOffer) {
                    pillarsByConnection.put(connection, new Pair<>(data, null));
                } else {
                    pillarsByConnection.put(connection, new Pair<>(null, data));
                }
            }
        }
        addPillar(data);
    }

    /**
     * Does NOT hadle adding to pillarsByConnection (must be done upstream)
     */
    private void addPillar(PillarData value) {
        pillars.put(value.currentPos, value);
        for (ChunkPos chunkPos : getChunkPoses(value.currentPos, 1)) {
            pillarsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()).add(value);
        }
        setDirty();
    }

    public void removePillar(BlockPos pos) {
        PillarData remove = pillars.remove(pos);
        for (ChunkPos chunkPos : getChunkPoses(pos, 1)) {
            pillarsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()).removeIf(p -> Objects.equals(pos, p.currentPos));
        }
        if (remove != null && remove.connection != null) {
            removeConnection(remove.connection);
        }
        if (remove != null && false) {
            Pair<PillarData, PillarData> pair = pillarsByConnection.get(remove.connection);
            if (pair != null) {
                if (remove.isOffer) {
                    if (pair.getB() != null) {
                        pillarsByConnection.put(remove.connection, new Pair<>(null, pair.getB()));
                    } else {
                        pillarsByConnection.remove(remove.connection);
                    }
                } else {
                    if (pair.getA() != null) {
                        pillarsByConnection.put(remove.connection, new Pair<>(pair.getA(), null));
                    } else {
                        pillarsByConnection.remove(remove.connection);
                    }
                }
            }
        }
        setDirty();
    }

    private List<ChunkPos> getChunkPoses(BlockPos center, int chunkRadius) {
        List<ChunkPos> poses = new ArrayList<>();
        for (int i = -chunkRadius; i <= chunkRadius; i++) {
            for (int j = -1; j <= 1; j++) {
                poses.add(new ChunkPos(SectionPos.blockToSectionCoord(center.getX()) + i, SectionPos.blockToSectionCoord(center.getZ()) + j));
            }
        }
        return poses;
    }

    public PillarData getPillarData(BlockPos pos) {
        return pillars.get(pos);
    }

    public List<PillarData> getActivePillarsInChunk(BlockPos pos) {
        return pillarsByChunk.getOrDefault(new ChunkPos(pos), new ArrayList<>());
    }

    public BlockPos getLink(PillarData data) {
        if (data != null) {
            Pair<PillarData, PillarData> pair = pillarsByConnection.get(data.getConnection());
            if (pair != null) {
                if (data.isOffer && pair.getB() != null) {
                    return pair.getB().getCurrentPos();
                } else if (!data.isOffer && pair.getA() != null) {
                    return pair.getA().getCurrentPos();
                }
            }
        }
        return null;
    }

    public void tick() {
        patientPodReservations.forEach((p, t) -> t.setB(t.getB() - 1));
        patientPodReservations.entrySet().removeIf(e -> e.getValue().getB() < 0);
    }

    public class PillarData {
        private BlockPos currentPos;
        private UUID connection;
        private EntityType<?> boundEntity;
        private int entityProgressNeeded;
        private int entityProgressAchieved;
        private final boolean isOffer;

        public PillarData(boolean isOffer, BlockPos currentPos, UUID connection, EntityType<?> boundEntity, int entityProgressNeeded, int entityProgressAchieved) {
            this.isOffer = isOffer;
            this.currentPos = currentPos;
            this.connection = connection;
            this.boundEntity = boundEntity;
            this.entityProgressNeeded = entityProgressNeeded;
            this.entityProgressAchieved = entityProgressAchieved;
        }

        public PillarData(CompoundTag tag) {
            this.isOffer = tag.getBoolean("isOffer");
            if (tag.contains("currentPos")) {
                currentPos = BlockPos.of(tag.getLong("currentPos"));
            } else {
                currentPos = BlockPos.ZERO;
            }
            if (tag.contains("connection")) {
                connection = tag.getUUID("connection");
            }
            if (tag.contains("boundEntity")) {
                boundEntity = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(tag.getString("boundEntity")));
                if (tag.contains("entityProgressNeeded")) {
                    entityProgressNeeded = tag.getInt("entityProgressNeeded");
                } else if (boundEntity != null) {
                    double health = ForgeHooks.getAttributesView().get(boundEntity).getValue(Attributes.MAX_HEALTH);
                    entityProgressNeeded = (int) Math.max(Math.pow(health, 0.8), 4);
                }
            }
            entityProgressAchieved = tag.getInt("entityProgressAchieved");
        }

        CompoundTag save(CompoundTag tag) {
            tag.putBoolean("isOffer", isOffer);
            tag.putLong("currentPos", currentPos.asLong());
            if (connection != null) {
                tag.putUUID("connection", connection);
            }
            if (boundEntity != null) {
                ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(boundEntity);
                if (key != null) {
                    tag.putString("boundEntity", key.getPath());
                }
            }
            tag.putInt("entityProgressAchieved", entityProgressAchieved);
            tag.putInt("entityProgressNeeded", entityProgressNeeded);
            return tag;
        }

        public BlockPos getCurrentPos() {
            return currentPos;
        }

        public UUID getConnection() {
            return connection;
        }

        public boolean isOffer() {
            return isOffer;
        }
    }

    public class PodData {
        private final BlockPos currentPos;
        private PatientType patient;
        private CompoundTag entity;

        public PodData(BlockPos currentPos) {
            this.currentPos = currentPos;
        }

        public PodData(CompoundTag tag) {
            currentPos = BlockPos.of(tag.getLong("currentPos"));
            if (tag.contains("type")) {
                this.patient = PatientType.valueOf(tag.getString("type"));
                this.entity = tag.getCompound("entity");
            }
        }

        public CompoundTag save(CompoundTag tag) {
            tag.putLong("currentPos", currentPos.asLong());
            if (patient != null) {
                tag.putString("type", patient.name());
                tag.put("entity", entity);
            }
            return tag;
        }

        public PatientType getPatient() {
            return patient;
        }

        public CompoundTag getEntity() {
            return entity;
        }

        public BlockPos getCurrentPos() {
            return currentPos;
        }

        public void setPatient(PatientType type, CompoundTag data) {
            this.patient = type;
            this.entity = data;
            setDirty();
        }

        public void setPatientAndSync(PatientType type, CompoundTag data, ServerLevel sl) {
            this.patient = type;
            this.entity = data;
            setDirty();
            if (sl.isLoaded(currentPos) && sl.getBlockEntity(currentPos) instanceof PatientPodBE be) {
                be.sync();
            }
        }

    }

}

