package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.valeriotor.beyondtheveil.util.BTVChunkCache;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class DeepCity {
	private List<DeepCityStructure> components = new LinkedList<>();
	private Map<Long, BTVChunkCache> chunks = new HashMap<>();
	private Set<Long> usedChunks = new HashSet<>();
	private World world;
	private BlockPos center;
	private boolean generated = false;
	
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
		DeepCityLayout layout = new DeepCityLayout(world.rand, center);
		layout.generate();
		components = layout.getAsList();
		for(DeepCityStructure dcs : components) {
			dcs.generate(chunks, usedChunks);
		}
		generated = true;
	}
	
	public boolean intersects(int chunkX, int chunkZ) {
		long pos = ChunkPos.asLong(chunkX, chunkZ);
		return chunks.containsKey(pos) || usedChunks.contains(pos) /*|| distance too small*/;
	}
	
	public synchronized void generateChunk(int chunkX, int chunkZ) {
		if(!generated) generate();
		BTVChunkCache cache = chunks.remove(ChunkPos.asLong(chunkX, chunkZ));
		if(cache != null) {
			cache.generate(world, chunkX, chunkZ);
			usedChunks.add(ChunkPos.asLong(chunkX, chunkZ));
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
		for(Long l : usedChunks) {
			usedChunksNBT.setLong(String.format("l%d", index++), l);
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
			usedChunks.add(usedChunksNBT.getLong(s));
		}
	}
	
	public synchronized void emptyCache() {
		chunks.clear();
		generated = false;
	}
	
}
