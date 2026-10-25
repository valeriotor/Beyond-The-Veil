package com.valeriotor.beyondtheveil.world.saved;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HamletSavedData extends SavedData {

    public static HamletSavedData getInstance(@NotNull ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(HamletSavedData::new, HamletSavedData::new, "hamlet_saved_data");
    }
    private Map<ChunkPos, BlockPos> hamletChunks = new HashMap<>();

    public HamletSavedData() {
    }

    public HamletSavedData(CompoundTag tag) {
        for (String villageCenter : tag.getAllKeys()) {
            try {
                long center = Long.parseLong(villageCenter);
                long[] chunks1 = tag.getLongArray(villageCenter);
                for (long l : chunks1) {
                    hamletChunks.put(new ChunkPos(l), BlockPos.of(center));
                }
            } catch (NumberFormatException ignored) {

            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        Map<BlockPos, List<ChunkPos>> inverse = new HashMap<>();
        for (Map.Entry<ChunkPos, BlockPos> e : hamletChunks.entrySet()) {
            inverse.computeIfAbsent(e.getValue(), p -> new ArrayList<>()).add(e.getKey());
        }
        for (Map.Entry<BlockPos, List<ChunkPos>> e : inverse.entrySet()) {
            long[] chunkLongs = new long[e.getValue().size()];
            int i = 0;
            for (ChunkPos chunkPos : e.getValue()) {
                chunkLongs[i++] = chunkPos.toLong();
            }
            pCompoundTag.putLongArray(String.valueOf(e.getKey().asLong()), chunkLongs);
        }
        return pCompoundTag;
    }

    public void addHamletChunk(ChunkPos pos, BlockPos villageCenter) {
        hamletChunks.put(pos, villageCenter);
        setDirty();
    }

    public boolean isHamletChunk(BlockPos pos) {
        return hamletChunks.containsKey(new ChunkPos(pos));
    }

    private Map<BlockPos, Integer> creakSounds = new HashMap<>();
    private static final int WOOD_CREAK_COOLDOWN = 90;

    public void tick() {
        creakSounds.replaceAll((pos, integer) -> integer - 1);
        creakSounds.entrySet().removeIf(e -> e.getValue() <= 0);
    }

    public boolean woodCreak(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        BlockPos center = hamletChunks.get(chunkPos);
        if (center != null) {
            if (creakSounds.containsKey(center)) {
                return false;
            }
            creakSounds.put(center, WOOD_CREAK_COOLDOWN);
            return true;
        }
        return false;
    }

}
