package com.valeriotor.beyondtheveil.world.saved;

import com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class ArcheSavedData extends SavedData {

    private Set<BlockPos> altars = new HashSet<>();

    public static ArcheSavedData getInstance(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(ArcheSavedData::new, ArcheSavedData::new, "arche_data");
    }

    public ArcheSavedData() {
    }

    public ArcheSavedData(CompoundTag tag) {
        long[] altars1 = tag.getLongArray("altars");
        for (long l : altars1) {
            altars.add(BlockPos.of(l));
        }
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        long[] altarLongs = new long[altars.size()];
        int i = 0;
        for (BlockPos altar : altars) {
            altarLongs[i++] = altar.asLong();
        }
        pCompoundTag.putLongArray("altars", altarLongs);
        return pCompoundTag;
    }


    public void addAltar(BlockPos pos) {
        altars.add(pos);
        setDirty();
    }

    public Set<BlockPos> getAltars() {
        return altars;
    }
}
