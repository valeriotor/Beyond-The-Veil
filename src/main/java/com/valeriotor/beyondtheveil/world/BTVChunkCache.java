package com.valeriotor.beyondtheveil.world;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BTVChunkCache {
	private final List<ShortState> blocks = new LinkedList<>();
	
	public void setBlockState(int x, int y, int z, IBlockState state) {
		short coords = (short) (((y << 8) | ((z & 15) << 4) | (x & 15)) & 65535);
		blocks.add(new ShortState(coords, state));
	}
	
	public void setBlockState(BlockPos pos, IBlockState state) {
		setBlockState(pos.getX(), pos.getY(), pos.getZ(), state);
	}
	
	public void generate(World w, int chunkX, int chunkZ) {
		for(ShortState ss : blocks)
			ss.generate(w, chunkX, chunkZ);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		Map<String, List<Integer>> blockNamesToCoordsAndMetas = blocks.stream().collect(Collectors.groupingBy(ShortState::getRegistryName,
																	 					   LinkedHashMap::new,
																	 					   Collectors.mapping(ShortState::getCoordsAndMeta, Collectors.toCollection(ArrayList::new))));
		for(Entry<String, List<Integer>> entry : blockNamesToCoordsAndMetas.entrySet()) {
			List<Integer> coordsAndMetasList = entry.getValue();
			int[] coordsAndMetas = new int[coordsAndMetasList.size()];
			for(int i = 0; i < coordsAndMetasList.size(); i++) {
				coordsAndMetas[i] = coordsAndMetasList.get(i);
			}
			nbt.setIntArray(entry.getKey(), coordsAndMetas);
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		if(!blocks.isEmpty()) throw new IllegalStateException();
		for(String s : nbt.getKeySet()) {
			int[] coordsAndMetas = nbt.getIntArray(s);
			for(int i : coordsAndMetas)
				blocks.add(new ShortState(s, i));
		}
	}
	
	private static class ShortState {
		private final short coords; // (15 downto 8) = y | (7 downto 4) = z | (3 downto 0) = x
		private final IBlockState state;
		
		private ShortState(short coords, IBlockState state) {
			this.coords = coords;
			this.state = state;
		}

		private ShortState(String registryName, int coordsAndMeta) {
			Block b = Block.getBlockFromName(registryName);
			state = b.getStateFromMeta(coordsAndMeta >> 16);
			coords = (short) (coordsAndMeta & 32767);
		}
		
		private void generate(World w, int chunkX, int chunkZ) {
			BlockPos pos = new BlockPos(chunkX << 4 | (coords & 15), (coords >> 8) & 255, chunkZ << 4 | ((coords >> 4) & 15));
			w.setBlockState(pos, state, 18);
		}

		private Integer getCoordsAndMeta() { // 31 downto 16 = meta | 15 downto 0 = coords
			return Integer.valueOf((state.getBlock().getMetaFromState(state) << 16) | coords);
		}

		private String getRegistryName() {
			return state.getBlock().getRegistryName().toString();
		}

	}
	
}
