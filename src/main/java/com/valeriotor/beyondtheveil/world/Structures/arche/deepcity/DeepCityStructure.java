package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.valeriotor.beyondtheveil.world.BTVChunkCache;
import com.valeriotor.beyondtheveil.world.Structures.arche.ArcheStructuresRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class DeepCityStructure {
	private final DeepCityStructureTemplate template;
	private final BlockPos center;
	private final int minChunkX, minChunkZ, maxChunkX, maxChunkZ;
	private final EnumSet<EnumFacing> corridors = EnumSet.noneOf(EnumFacing.class);
	
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
		byte bits = nbt.getByte("corridors");
		if((bits & 1) == 1) corridors.add(EnumFacing.NORTH);
		if((bits & 2) == 2) corridors.add(EnumFacing.EAST );
		if((bits & 4) == 4) corridors.add(EnumFacing.SOUTH);
		if((bits & 8) == 8) corridors.add(EnumFacing.WEST );
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("name", template.getName());
		nbt.setLong("center", center.toLong());
		byte bits = 0;
		if(corridors.contains(EnumFacing.NORTH)) bits |= 1;
		if(corridors.contains(EnumFacing.EAST )) bits |= 2;
		if(corridors.contains(EnumFacing.SOUTH)) bits |= 4;
		if(corridors.contains(EnumFacing.WEST )) bits |= 8;
		nbt.setByte("corridors", bits);
	}
	
	public boolean intersects(int chunkX, int chunkZ) {	
		return chunkX <= maxChunkX && chunkX >= minChunkX  && chunkZ <= maxChunkZ && chunkZ >= minChunkZ ;
	}
	
	public void fillCache(Map<Long, BTVChunkCache> chunks, Map<Long, Boolean> usedChunks) {
		template.fillCache(center, chunks, usedChunks);
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			if(corridors.contains(facing)) {
				template.fillCacheForCorridor(center, chunks, usedChunks, facing);
				template.fillCacheForDoor(center, chunks, usedChunks, facing, true);
			} else {
				template.fillCacheForDoor(center, chunks, usedChunks, facing, false);
			}
		}
	}
	
	private void generateCorridors(Map<Long, BTVChunkCache> chunks, Set<Long> usedChunks) {
		
	}
	
	public void addCorridor(EnumFacing facing) {
		corridors.add(facing);
	}
	
	
}
