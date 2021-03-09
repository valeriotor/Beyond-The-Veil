package com.valeriotor.beyondtheveil.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BTVChunkCacheStore extends WorldSavedData {
    private static final String DATA_NAME = "CHUNKCACHEDATA";
    private final Map<Long, BTVChunkCache> chunkPosCacheMap = new HashMap<>();
    private World world;

    public BTVChunkCacheStore(String name) {
        super(name);
    }

    public static BTVChunkCacheStore get(World w) {
        MapStorage storage = w.getPerWorldStorage();
        BTVChunkCacheStore data = (BTVChunkCacheStore) storage.getOrLoadData(BTVChunkCacheStore.class, DATA_NAME);
        if(data == null) {
            data = new BTVChunkCacheStore(DATA_NAME);
            storage.setData(DATA_NAME, data);
        }
        data.world = w;
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for(String s : nbt.getKeySet()) {
            BTVChunkCache cache = new BTVChunkCache();
            Long l = Long.parseLong(s);
            NBTTagCompound chunkNBT = nbt.getCompoundTag(s);
            cache.readFromNBT(chunkNBT);
            chunkPosCacheMap.put(l, cache);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for(Entry<Long, BTVChunkCache> entry : chunkPosCacheMap.entrySet()) {
            NBTTagCompound chunkNBT = new NBTTagCompound();
            entry.getValue().writeToNBT(chunkNBT);
            compound.setTag(Long.toString(entry.getKey()), chunkNBT);
        }
        return compound;
    }

    public void generateAndForgetChunk(int chunkX, int chunkZ) {
        BTVChunkCache chunkCache = chunkPosCacheMap.remove(ChunkPos.asLong(chunkX, chunkZ));
        if(chunkCache != null) {
            chunkCache.generate(world, chunkX, chunkZ);
        }
    }

    public Map<Long, BTVChunkCache> getChunkPosCacheMap() {
        return chunkPosCacheMap;
    }
}
