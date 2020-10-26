package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.items.TestItem.JSonStructureBuilder;
import com.valeriotor.beyondtheveil.util.BTVChunkCache;
import com.valeriotor.beyondtheveil.util.BlockCoords;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;

public class DeepCityStructureTemplate {
	private static final int DOOR_HEIGHT = 7;
	private List<BlockCoords> coords = new ArrayList<>();
	private EnumMap<EnumFacing, byte[][]> doors = new EnumMap(EnumFacing.class);
	private final String name;
	private final int distanceDoorFromCenter;
	

	public DeepCityStructureTemplate(String name, int distanceDoorFromCenter) {
		this.name = name;
		this.distanceDoorFromCenter = distanceDoorFromCenter;
	}
	
	public void registerBlocks() {
		String file;
		try {
			file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/buildings/" + name + ".json"), Charsets.UTF_8);
			JSonStructureBuilder jssb = BeyondTheVeil.gson.fromJson(file, JSonStructureBuilder.class);
			HashMap<Block, byte[][]> map = jssb.getMap();
			BlockCoords air = null;
			for(Entry<Block, byte[][]> entry : map.entrySet()) {
				if(entry.getKey() == Blocks.AIR) {
					air = new BlockCoords(entry);
				} else if(entry.getKey() == Blocks.DIRT) {
					registerDoors(entry.getValue());
				} else if(entry.getKey() == Blocks.GLASS) {
					coords.add(new BlockCoords(BlockRegistry.BlockDarkGlass, entry.getValue()));
				} else {
					coords.add(new BlockCoords(entry));
				}
			}
			if(air != null) coords.add(air);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void registerDoors(byte[][] coords) {
		for(EnumFacing facing : EnumFacing.HORIZONTALS) {
			doors.put(facing, new byte[9][3]);
		}
		int[] indexes = {0,0,0,0};
		for(byte[] coord : coords) {
			byte x = coord[0];
			byte y = coord[1];
			byte z = coord[2];
			EnumFacing facing = null;
			if(x >= distanceDoorFromCenter) facing = EnumFacing.EAST;
			else if(x <= -distanceDoorFromCenter) facing = EnumFacing.WEST;
			else if(z >= distanceDoorFromCenter) facing = EnumFacing.SOUTH;
			else facing = EnumFacing.NORTH;
			byte[] doorCoord = doors.get(facing)[indexes[facing.ordinal()-2]];
			doorCoord[0] = x;
			doorCoord[1] = y;
			doorCoord[2] = z;
			indexes[facing.ordinal()-2]++;
		}
	}
	
	public void generate(BlockPos center, Map<Long, BTVChunkCache> chunks, Map<Long, Boolean> usedChunks) {
		for(BlockCoords bc : coords) {
			bc.generate(center, chunks, usedChunks);
		}
	}
	
	public void generateDoor(BlockPos center, Map<Long, BTVChunkCache> chunks, Map<Long, Boolean> usedChunks, EnumFacing facing, boolean corridor) {
		IBlockState state = corridor ? Blocks.AIR.getDefaultState() : BlockRegistry.BlockDarkGlass.getDefaultState();
		byte[][] coords = doors.get(facing);
		BlockPos offsetCenter = null;;
		for(byte[] coord : coords) {
			offsetCenter = center.add(coord[0], coord[1], coord[2]);
			int chunkX = offsetCenter.getX() >> 4;
			int chunkZ = offsetCenter.getZ() >> 4;
			Long cPos = ChunkPos.asLong(chunkX, chunkZ);
			BTVChunkCache cache = chunks.get(cPos);
			if(cache == null) {
				cache = new BTVChunkCache();
				chunks.put(cPos, cache);
			}
			cache.setBlockState(offsetCenter, state);
		}
	}
	
	public void generateCorridor(BlockPos center, Map<Long, BTVChunkCache> chunks, Map<Long, Boolean> usedChunks, EnumFacing facing) {
		IBlockState glass = BlockRegistry.BlockDarkGlass.getDefaultState();
		int distance = DeepCityLayout.INDIVIDUAL_WIDTH/2 + 1  - distanceDoorFromCenter;
		BlockPos pos = center.offset(EnumFacing.UP, DOOR_HEIGHT).offset(facing, distanceDoorFromCenter);
		BlockPos fromAir = pos.offset(facing.rotateYCCW(), 1).up();
		BlockPos toAir   = pos.offset(facing.rotateYCCW().getOpposite(), 1).offset(facing, distance).offset(EnumFacing.UP, 3);
		BlockPos fromLeftGlass = pos.offset(facing.rotateYCCW(), 2).up();
		BlockPos toLeftGlass   = pos.offset(facing.rotateYCCW(), 2).offset(facing, distance).offset(EnumFacing.UP, 3);
		BlockPos fromRightGlass = pos.offset(facing.rotateYCCW().getOpposite(), 2).up();
		BlockPos toRightGlass   = pos.offset(facing.rotateYCCW().getOpposite(), 2).offset(facing, distance).offset(EnumFacing.UP, 3);
		BlockPos fromGlassCeiling = pos.offset(facing.rotateYCCW()).offset(EnumFacing.UP, 4);
		BlockPos toGlassCeiling   = pos.offset(facing.rotateYCCW().getOpposite()).offset(facing, distance).offset(EnumFacing.UP, 4);
		BlockPos fromPrismarineFloor = pos.offset(facing.rotateYCCW(), 2);
		BlockPos toPrismarineFloor   = pos.offset(facing.rotateYCCW().getOpposite(), 2).offset(facing, distance);
		fillWithBlocks(fromPrismarineFloor, toPrismarineFloor, Blocks.PRISMARINE.getStateFromMeta(BlockPrismarine.DARK_META), chunks, usedChunks);
		fillWithBlocks(fromGlassCeiling, toGlassCeiling, glass, chunks, usedChunks);
		fillWithBlocks(fromRightGlass, toRightGlass, glass, chunks, usedChunks);
		fillWithBlocks(fromLeftGlass, toLeftGlass, glass, chunks, usedChunks);
		fillWithBlocks(fromAir, toAir, Blocks.AIR.getDefaultState(), chunks, usedChunks);
	}
	
	private void fillWithBlocks(BlockPos from, BlockPos to, IBlockState state, Map<Long,BTVChunkCache> chunks, Map<Long, Boolean> usedChunks) {
		MutableBlockPos pos = new MutableBlockPos();
		for(int x = Math.min(from.getX(), to.getX()); x <= Math.max(from.getX(), to.getX()); x++) {
			for(int y = Math.min(from.getY(), to.getY()); y <= Math.max(from.getY(), to.getY()); y++) {
				for(int z = Math.min(from.getZ(), to.getZ()); z <= Math.max(from.getZ(), to.getZ()); z++) {
					int chunkX = x >> 4;
					int chunkZ = z >> 4;
					long cPos = ChunkPos.asLong(chunkX, chunkZ);
					if(!usedChunks.containsKey(cPos)) 
						usedChunks.put(cPos, false);
					if(!usedChunks.get(cPos)) {
						pos.setPos(x, y, z);
						BTVChunkCache cache = chunks.get(cPos);
						if(cache == null) {
							cache = new BTVChunkCache();
							chunks.put(cPos, cache);
						}
						cache.setBlockState(pos, state);
					}
					
				}
			}
		}
	}
	
	public int getDistanceDoorFromCenter() {
		return distanceDoorFromCenter;
	}
	
	public String getName() {
		return name;
	}
	
}
