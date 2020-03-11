package com.valeriotor.beyondtheveil.world.Structures;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.EntityHamletDweller;
import com.valeriotor.beyondtheveil.items.TestItem.JSonStructureBuilder;
import com.valeriotor.beyondtheveil.world.Structures.loot.LootTables;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HamletStorehouse extends HamletStructure{
	
	public static void registerBlocks() {
		String file;
		try {
			file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/buildings/storehouse.json"), Charsets.UTF_8);
			JSonStructureBuilder jssb = BeyondTheVeil.gson.fromJson(file, JSonStructureBuilder.class);
			HashMap<Block, byte[][]> map = jssb.getMap();
			air = map.get(Blocks.AIR);
			dark_sand = map.get(BlockRegistry.DarkSand);
			damp_log = map.get(BlockRegistry.DampLog);
			damp_wood = map.get(BlockRegistry.DampWood);
			damp_canopy_wood = map.get(BlockRegistry.DampCanopyWood);
			damp_canopy = map.get(BlockRegistry.DampCanopy);
			damp_wood_stairs = map.get(BlockRegistry.DampWoodStairs);
			barrel = map.get(BlockRegistry.BlockBarrel);
			chest = map.get(Blocks.CHEST);
			fence = map.get(BlockRegistry.DampWoodFence);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HamletStorehouse(World w) {
		this.world = w;
		this.radius = 10;
	}


	@Override
	public void StartStructure(Random r) {
		this.isItOnWater = this.isOnWater(dark_sand);
		//this.fillFloor(dark_sand);
		IBlockState state = BlockRegistry.DarkSand.getDefaultState();
		if(this.isOnWater(dark_sand)) {
			state = BlockRegistry.DampWood.getDefaultState();
		}else {
			this.fillFloor(dark_sand);
		}
		
		if(facing==EnumFacing.NORTH) {
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]));
			for(byte[] n : this.barrel) this.getRandomBarrel(r, new BlockPos(x+n[0], y+n[1], z+n[2]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(byte[] n : this.fence) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
		}else if(facing==EnumFacing.EAST) {
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));	
			for(byte[] n : this.barrel) this.getRandomBarrel(r, new BlockPos(x-n[2], y+n[1], z+n[0]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.CHEST.getStateFromMeta(n[3]).withProperty(Blocks.CHEST.FACING, Blocks.CHEST.getStateFromMeta(n[3]).getValue(Blocks.CHEST.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(byte[] n : this.fence) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
		}else if(facing==EnumFacing.SOUTH) {
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.barrel) this.getRandomBarrel(r, new BlockPos(x-n[0], y+n[1], z-n[2]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.CHEST.getStateFromMeta(n[3]).withProperty(Blocks.CHEST.FACING, Blocks.CHEST.getStateFromMeta(n[3]).getValue(Blocks.CHEST.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.fence) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
		}else if(facing==EnumFacing.WEST) {
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW()));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW()));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW()));
			for(byte[] n : this.barrel) this.getRandomBarrel(r, new BlockPos(x+n[2], y+n[1], z-n[0]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.CHEST.getStateFromMeta(n[3]).withProperty(Blocks.CHEST.FACING, Blocks.CHEST.getStateFromMeta(n[3]).getValue(Blocks.CHEST.FACING).rotateYCCW()));
			for(byte[] n : this.fence) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			
		}
		for(byte[] n : this.chest) {
			TileEntity t = world.getTileEntity(this.getPosFromArray(n, facing));
			if(t instanceof TileEntityChest) {
				((TileEntityChest)t).setLootTable(LootTables.hamletStoreHouse1, r.nextLong());
			}
		}
		
		
		//this.roadHelper(doorCoords);
	}
	
	@Override
	public void doorRoads() {
		this.roadHelper(doorCoords);
	}
	
	@Override
	public void spawnHamletDwellers() {
		BlockPos home = this.getPosFromArray(villagerCoords, this.facing);
		EntityHamletDweller h = new EntityHamletDweller(this.world);
		h.setProfession(EntityHamletDweller.ProfessionsEnum.STOCKPILER);
		h.setHome(home);
		h.setVillageCenter(this.villageCenter);
		h.setPosition(home.getX(), home.getY(), home.getZ());
		this.world.spawnEntity(h);
		
	}
	
	
	
	static byte[][] dark_sand; 
			 
	static byte[][] air; 
			 
	static byte[][] damp_canopy; 
			 
	static byte[][] damp_log; 
			 
	static byte[][] damp_wood; 
			 
	static byte[][] damp_canopy_wood; 
			 
	static byte[][] damp_wood_stairs;
	
	static byte[][] barrel;
			 
	static byte[][] chest; 
			 
	static byte[][] fence; 
	
	static byte[] doorCoords = {-3,-1,6};
	
	static byte[] villagerCoords = {0, 4, 1};
			 
			
			 
	
	
}
