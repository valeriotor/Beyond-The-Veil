package com.valeriotor.beyondtheveil.world.Structures.arche;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.items.TestItem.JSonStructureBuilder;
import com.valeriotor.beyondtheveil.util.BlockCoords;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloodHome {
	private List<BlockCoords> coords;
	
	public BloodHome() {
		coords = new ArrayList<>();
	}
	
	public void registerBlocks() {
		String file;
		try {
			file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/buildings/blood_home.json"), Charsets.UTF_8);
			JSonStructureBuilder jssb = BeyondTheVeil.gson.fromJson(file, JSonStructureBuilder.class);
			HashMap<Block, byte[][]> map = jssb.getMap();
			coords.add(new BlockCoords(BlockRegistry.BlockBloodBrick, map.get(BlockRegistry.BlockBloodBrick)));
			for(Entry<Block, byte[][]> entry : map.entrySet()) {
				if(entry.getKey() != BlockRegistry.BlockBloodBrick) {
					coords.add(new BlockCoords(entry));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generate(World world, BlockPos center) {
		for(BlockCoords bc : coords) {
			bc.generate(world, center);
		}
	}
	
}
