package com.valeriotor.beyondtheveil.world.Structures.arche.deepcity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.items.TestItem.JSonStructureBuilder;
import com.valeriotor.beyondtheveil.util.BTVChunkCache;
import com.valeriotor.beyondtheveil.util.BlockCoords;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class DeepCityStructureTemplate {
	private List<BlockCoords> coords = new ArrayList<>();
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
				} else {
					coords.add(new BlockCoords(entry));
				}
			}
			if(air != null) coords.add(air);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generate(BlockPos center, Map<Long, BTVChunkCache> chunks, Set<Long> usedChunks) {
		for(BlockCoords bc : coords) {
			bc.generate(center, chunks, usedChunks);
		}
	}
	
	public int getDistanceDoorFromCenter() {
		return distanceDoorFromCenter;
	}
	
	public String getName() {
		return name;
	}
	
}
