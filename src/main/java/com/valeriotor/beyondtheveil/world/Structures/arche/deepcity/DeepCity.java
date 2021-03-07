package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.util.*;
import java.util.Map.Entry;

import com.valeriotor.beyondtheveil.util.BTVChunkCache;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class DeepCity {
	private List<DeepCityStructure> components = new LinkedList<>();
	private Map<Long, BTVChunkCache> chunks = new HashMap<>();
	private Map<Long, Boolean> usedChunks = new LinkedHashMap<>();
	private World world;
	private BlockPos center;
	private volatile boolean generated = false;
	
	public DeepCity(World w, BlockPos pos) {
		this.world = w;
		this.center = pos;
	}
	
	public DeepCity() {
		
	}
	
	public void setWorld(World w) {
		world = w;
	}
	
	public synchronized void generate() {
		if(components.isEmpty()) {
			DeepCityLayout layout = new DeepCityLayout(world.rand, center);
			layout.generate();
			components = layout.getAsList();
		}
		for(DeepCityStructure dcs : components) {
			dcs.generate(chunks, usedChunks);
		}
		generated = true;
	}
	
	public boolean intersects(int chunkX, int chunkZ) {
		long pos = ChunkPos.asLong(chunkX, chunkZ);
		return usedChunks.containsKey(pos) /*|| distance too small*/;
	}
	
	public synchronized void generateChunk(int chunkX, int chunkZ) {
		if(!generated) generate();
		BTVChunkCache cache = chunks.remove(ChunkPos.asLong(chunkX, chunkZ));
		if(cache != null) {
			cache.generate(world, chunkX, chunkZ);
			usedChunks.put(ChunkPos.asLong(chunkX, chunkZ), Boolean.valueOf(true));
		}
	}
	
	public synchronized void writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("center", center.toLong());
		writeBuildings(nbt);
		writeUsedChunks(nbt);
	}
	
	private void writeBuildings(NBTTagCompound nbt) {
		NBTTagCompound buildings = new NBTTagCompound();
		int index = 0;
		for(DeepCityStructure dcs : components) {
			NBTTagCompound building = new NBTTagCompound();
			dcs.writeToNBT(building);
			buildings.setTag(String.format("b%d", index++), building);
		}
		nbt.setTag("buildings", buildings);
	}
	
	private void writeUsedChunks(NBTTagCompound nbt) {
		NBTTagCompound usedChunksNBT = new NBTTagCompound();
		int index = 0;
		for(Entry<Long, Boolean> l : usedChunks.entrySet()) {
			NBTTagCompound chunkNBT = new NBTTagCompound();
			chunkNBT.setLong("l", l.getKey());
			chunkNBT.setBoolean("v", l.getValue());
			usedChunksNBT.setTag(String.format("c%d", index++), chunkNBT);
		}
		nbt.setTag("usedChunks", usedChunksNBT);
	}
	
	public synchronized void readFromNBT(NBTTagCompound nbt) {
		center = BlockPos.fromLong(nbt.getLong("center"));
		readBuildings(nbt.getCompoundTag("buildings"));
		readUsedChunks(nbt.getCompoundTag("usedChunks"));
		generated = false;
	}
	
	private void readBuildings(NBTTagCompound buildings) {
		for(String s : buildings.getKeySet()) {
			components.add(new DeepCityStructure(buildings.getCompoundTag(s)));
		}
	}
	
	private void readUsedChunks(NBTTagCompound usedChunksNBT) {
		for(String s : usedChunksNBT.getKeySet()) {
			NBTTagCompound nbt = usedChunksNBT.getCompoundTag(s);
			usedChunks.put(nbt.getLong("l"), nbt.getBoolean("v"));
		}
	}
	
	public synchronized void emptyCache() {
		chunks.clear();
		generated = false;
	}
	
	public BlockPos getCenter() {
		return center;
	}

	public Set<Long> getChunkCoords() {
		return usedChunks.keySet();
	}
	
}
