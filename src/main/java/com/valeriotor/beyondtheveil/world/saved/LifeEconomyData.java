package com.valeriotor.beyondtheveil.world.saved;

import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.tile.PatientPodBE;
import com.valeriotor.beyondtheveil.tile.PillarBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LifeEconomyData extends SavedData {

    private final Map<BlockPos, PillarData> pillars = new HashMap<>();
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
                data.addPillar(pillarTag.getCompound(key));
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

    public void addPillar(BlockPos pos, BlockPos link, UUID connection) {
        addPillar(new PillarData(pos, connection, link));
    }

    private void addPillar(CompoundTag tag) {
        addPillar(new PillarData(tag));
    }

    private void addPillar(PillarData value) {
        pillars.put(value.currentPos, value);
        for (ChunkPos chunkPos : getChunkPoses(value.currentPos, 1)) {
            pillarsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()).add(value);
        }
        setDirty();
    }

    public void removePillar(BlockPos pos) {
        pillars.remove(pos);
        for (ChunkPos chunkPos : getChunkPoses(pos, 1)) {
            pillarsByChunk.computeIfAbsent(chunkPos, c -> new ArrayList<>()).removeIf(p -> Objects.equals(pos, p.currentPos));
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

    public boolean checkPillarConnection(BlockPos pillarPos, UUID connection) {
        if (pillars.containsKey(pillarPos) && Objects.equals(pillars.get(pillarPos).connection, connection)) {
            return true;
        }
        return false;
    }

    public void setPillarConnectionToBe(ServerLevel sl, BlockPos pillarPos, UUID connection) {
        if (!pillars.containsKey(pillarPos)) {
            addPillar(pillarPos, null, connection);
        } else {
            PillarData pillarData = pillars.get(pillarPos);
            pillarData.linkPos = null;
            pillarData.connection = connection;
        }
        if (sl.isLoaded(pillarPos)) {
            if (sl.getBlockEntity(pillarPos) instanceof PillarBE be) {
                be.setLink(null);
                be.setConnection(connection);
            }
        }
        setDirty();
    }

    public boolean setPillarLink(Level level, BlockPos pillarPos, UUID connection, BlockPos link) {
        if (!pillars.containsKey(pillarPos)) {
            return false;
        }
        PillarData pillarData = pillars.get(pillarPos);
        if (!Objects.equals(pillarData.connection, connection)) {
            return false;
        }
        pillarData.linkPos = link;
        if (level.isLoaded(pillarPos)) {
            if (level.getBlockEntity(pillarPos) instanceof PillarBE be) {
                be.setLink(link);
            }
        }
        setDirty();
        return true;
    }

    public void tick() {
        patientPodReservations.forEach((p, t) -> t.setB(t.getB() - 1));
        patientPodReservations.entrySet().removeIf(e -> e.getValue().getB() < 0);
    }

    public class PillarData {
        private BlockPos currentPos;
        private UUID connection;
        private BlockPos linkPos;

        public PillarData(BlockPos currentPos, UUID connection, BlockPos linkPos) {
            this.currentPos = currentPos;
            this.connection = connection;
            this.linkPos = linkPos;
        }

        public PillarData(CompoundTag tag) {
            tag.contains("currentPos");
            currentPos = BlockPos.of(tag.getLong("currentPos"));
            if(tag.contains("connection")){
                connection = UUID.fromString(tag.getString("connection"));
            }
            if(tag.contains("linkPos")){
                linkPos = BlockPos.of(tag.getLong("linkPos"));
            }
        }

        CompoundTag save(CompoundTag tag) {
            tag.putLong("currentPos", currentPos.asLong());
            if (connection != null) {
                tag.putString("connection", connection.toString());
            }
            if (linkPos != null) {
                tag.putLong("linkPos", linkPos.asLong());
            }
            return tag;
        }

        public BlockPos getCurrentPos() {
            return currentPos;
        }

        public UUID getConnection() {
            return connection;
        }

        public BlockPos getLinkPos() {
            return linkPos;
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

