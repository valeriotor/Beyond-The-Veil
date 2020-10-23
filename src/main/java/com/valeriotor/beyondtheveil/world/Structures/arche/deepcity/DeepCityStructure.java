package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.util.Map;
import java.util.Set;

import com.valeriotor.beyondtheveil.util.BTVChunkCache;
import com.valeriotor.beyondtheveil.world.Structures.arche.ArcheStructuresRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class DeepCityStructure {
	private final DeepCityStructureTemplate template;
	private final BlockPos center;
	private final int minChunkX, minChunkZ, maxChunkX, maxChunkZ;
	
	public DeepCityStructure(DeepCityStructureTemplate template, BlockPos center) {
		this.template = template;
		this.center = center;
		int size = template.getDistanceDoorFromCenter();
		this.minChunkX = (center.getX() - size) >> 4;
		this.minChunkZ = (center.getZ() - size) >> 4;
		this.maxChunkX = (center.getX() + size) >> 4;
		this.maxChunkZ = (center.getZ() + size) >> 4;
	}
	
	public DeepCityStructure(NBTTagCompound nbt) {
		this.template = ArcheStructuresRegistry.getStructure(nbt.getString("name"));
		this.center = BlockPos.fromLong(nbt.getLong("center"));
		int size = template.getDistanceDoorFromCenter();
		this.minChunkX = (center.getX() - size) >> 4;
		this.minChunkZ = (center.getZ() - size) >> 4;
		this.maxChunkX = (center.getX() + size) >> 4;
		this.maxChunkZ = (center.getZ() + size) >> 4;
	}
	
	public boolean intersects(int chunkX, int chunkZ) {	
		return chunkX <= maxChunkX && chunkX >= minChunkX  && chunkZ <= maxChunkZ && chunkZ >= minChunkZ ;
	}
	
	public void generate(Map<Long, BTVChunkCache> chunks, Set<Long> usedChunks) {
		template.generate(center, chunks, usedChunks);
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("name", template.getName());
		nbt.setLong("center", center.toLong());
	}
	
}
