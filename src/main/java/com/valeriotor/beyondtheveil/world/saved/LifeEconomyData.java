package com.valeriotor.beyondtheveil.world.saved;

import com.valeriotor.beyondtheveil.tile.PillarBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LifeEconomyData extends SavedData {

    private final Map<BlockPos, PillarData> pillars = new HashMap<>();
    private final Map<ChunkPos, List<PillarData>> pillarsByChunk = new HashMap<>();


    public static LifeEconomyData getInstance(ServerLevel sl) {
        return sl.getDataStorage().computeIfAbsent(LifeEconomyData::load, LifeEconomyData::create, "lifeEconomyData");
    }

    private static LifeEconomyData load(CompoundTag tag) {
        LifeEconomyData data = create();
        if (tag.contains("pillarTag")) {
            CompoundTag pillarTag = tag.getCompound("pillarTag");
            for (String key : pillarTag.getAllKeys()) {
                PillarData pillarData = new PillarData(pillarTag.getCompound(key));
                data.pillars.put(pillarData.currentPos, pillarData);
            }
        }
        return data;
    }

    @NotNull
    private static LifeEconomyData create() {
        return new LifeEconomyData();
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        CompoundTag compoundTag = new CompoundTag();
        CompoundTag pillarTag = new CompoundTag();
        compoundTag.put("pillarTag", pillarTag);
        int i = 0;
        for (Map.Entry<BlockPos, PillarData> entry : pillars.entrySet()) {
            pillarTag.put(String.valueOf(i), entry.getValue().save(new CompoundTag()));
            i++;
        }
        return compoundTag;
    }


    public void addPillar(BlockPos pos, BlockPos link, UUID connection) {
        pillars.put(pos, new PillarData(pos, connection, link));
    }

    public void removePillar(BlockPos pos) {
        pillars.remove(pos);
    }

    public PillarData getPillarData(BlockPos pos) {
        return pillars.get(pos);
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

    public static class PillarData {
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

        public UUID getConnection() {
            return connection;
        }

        public BlockPos getLinkPos() {
            return linkPos;
        }
    }

}

