package com.valeriotor.BTV.world.Structures;

import java.util.Random;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.EntityHamletDweller;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HamletHouse2 extends HamletStructure{

	
	
	public HamletHouse2(World w) {
		this.world = w;
		this.radius = 7;
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
		
		//this.facing = EnumFacing.getFront(r.nextInt(4)+2);
		
		if(facing==EnumFacing.NORTH) {
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]));
		}else if(facing==EnumFacing.EAST) {
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).cycleProperty(BlockRegistry.DampCanopyWood.FACING).cycleProperty(BlockRegistry.DampCanopyWood.FACING).cycleProperty(BlockRegistry.DampCanopyWood.FACING));	
		}else if(facing==EnumFacing.SOUTH) {
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).cycleProperty(BlockRegistry.DampCanopyWood.FACING));
		}else if(facing==EnumFacing.WEST) {
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW()));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW()));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).cycleProperty(BlockRegistry.DampCanopyWood.FACING).cycleProperty(BlockRegistry.DampCanopyWood.FACING));
		}
		
		//this.roadHelper(doorCoords);
	}
	
	@Override
	public void spawnHamletDwellers() {
		BlockPos home = this.center.add(0,1,0);
		EntityHamletDweller h = new EntityHamletDweller(this.world);
		h.setProfession(EntityHamletDweller.ProfessionsEnum.FISHERMAN);
		h.setHome(home);
		h.setVillageCenter(this.villageCenter);
		h.setPosition(home.getX(), home.getY(), home.getZ());
		this.world.spawnEntity(h);
		
	}
	
	@Override
	public void doorRoads() {
		this.roadHelper(doorCoords);
	}
	
	
	/*private int rotateMeta(int meta, int facing) {
		if(facing==1) {
			if(meta==1) return 4;
			else if(meta==2) return 1;
			else if(meta==3) return 2;
			else if(meta==4) return 3;
				
		}else if(facing==2) {
			if(meta==1) return 3;
			else if(meta==2) return 4;
			else if(meta==3) return 1;
			else if(meta==4) return 2;
		}else {
			if(meta==1) return 2;
			else if(meta==2) return 3;
			else if(meta==3) return 4;
			else if(meta==4) return 1;
		}
	}*/
	
	static int[][] dark_sand = { 

			{-4, -1, -4, 0}, {-4, -1, -3, 0}, {-4, -1, -2, 0}, {-4, -1, -1, 0}, {-4, -1, 0, 0}, {-4, -1, 1, 0}, {-4, -1, 2, 0}, {-4, -1, 3, 0}, {-4, -1, 4, 0}, {-4, -1, 5, 0}, 
			{-3, -1, -4, 0}, {-3, -1, -3, 0}, {-3, -1, -2, 0}, {-3, -1, -1, 0}, {-3, -1, 0, 0}, {-3, -1, 1, 0}, {-3, -1, 2, 0}, {-3, -1, 3, 0}, {-3, -1, 4, 0}, {-3, -1, 5, 0}, 
			{-2, -1, -4, 0}, {-2, -1, -3, 0}, {-2, -1, -2, 0}, {-2, -1, -1, 0}, {-2, -1, 0, 0}, {-2, -1, 1, 0}, {-2, -1, 2, 0}, {-2, -1, 3, 0}, {-2, -1, 4, 0}, {-2, -1, 5, 0}, 
			{-1, -1, -4, 0}, {-1, -1, -3, 0}, {-1, -1, -2, 0}, {-1, -1, -1, 0}, {-1, -1, 0, 0}, {-1, -1, 1, 0}, {-1, -1, 2, 0}, {-1, -1, 3, 0}, {-1, -1, 4, 0}, {-1, -1, 5, 0}, 
			{0, -1, -4, 0}, {0, -1, -3, 0}, {0, -1, -2, 0}, {0, -1, -1, 0}, {0, -1, 0, 0}, {0, -1, 1, 0}, {0, -1, 2, 0}, {0, -1, 3, 0}, {0, -1, 4, 0}, {0, -1, 5, 0}, 
			{1, -1, -4, 0}, {1, -1, -3, 0}, {1, -1, -2, 0}, {1, -1, -1, 0}, {1, -1, 0, 0}, {1, -1, 1, 0}, {1, -1, 2, 0}, {1, -1, 3, 0}, {1, -1, 4, 0}, {1, -1, 5, 0}, 
			{2, -1, -4, 0}, {2, -1, -3, 0}, {2, -1, -2, 0}, {2, -1, -1, 0}, {2, -1, 0, 0}, {2, -1, 1, 0}, {2, -1, 2, 0}, {2, -1, 3, 0}, {2, -1, 4, 0}, {2, -1, 5, 0}, 
			{3, -1, -4, 0}, {3, -1, -3, 0}, {3, -1, -2, 0}, {3, -1, -1, 0}, {3, -1, 0, 0}, {3, -1, 1, 0}, {3, -1, 2, 0}, {3, -1, 3, 0}, {3, -1, 4, 0}, {3, -1, 5, 0}, 
			{4, -1, -4, 0}, {4, -1, -3, 0}, {4, -1, -2, 0}, {4, -1, -1, 0}, {4, -1, 0, 0}, {4, -1, 1, 0}, {4, -1, 2, 0}, {4, -1, 3, 0}, {4, -1, 4, 0}, {4, -1, 5, 0}, 
			{5, -1, -4, 0}, {5, -1, -3, 0}, {5, -1, -2, 0}, {5, -1, -1, 0}, {5, -1, 0, 0}, {5, -1, 1, 0}, {5, -1, 2, 0}, {5, -1, 3, 0}, {5, -1, 4, 0}, {5, -1, 5, 0}, 


			}; 
			 
	static int[][] air = { 

			{-4, 0, -4, 0}, {-4, 0, -3, 0}, {-4, 0, -2, 0}, {-4, 0, -1, 0}, {-4, 0, 0, 0}, {-4, 0, 1, 0}, {-4, 0, 2, 0}, {-4, 0, 3, 0}, {-4, 0, 4, 0}, {-4, 0, 5, 0}, 
			{-4, 1, -4, 0}, {-4, 1, -3, 0}, {-4, 1, -2, 0}, {-4, 1, -1, 0}, {-4, 1, 0, 0}, {-4, 1, 1, 0}, {-4, 1, 2, 0}, {-4, 1, 3, 0}, {-4, 1, 4, 0}, {-4, 1, 5, 0}, 
			{-4, 3, -4, 0}, {-4, 3, -3, 0}, {-4, 3, -2, 0}, {-4, 3, -1, 0}, {-4, 3, 0, 0}, {-4, 3, 1, 0}, {-4, 3, 2, 0}, {-4, 3, 3, 0}, {-4, 3, 4, 0}, {-4, 3, 5, 0}, 
			{-4, 4, -4, 0}, {-4, 4, -3, 0}, {-4, 4, -2, 0}, {-4, 4, -1, 0}, {-4, 4, 0, 0}, {-4, 4, 1, 0}, {-4, 4, 2, 0}, {-4, 4, 3, 0}, {-4, 4, 4, 0}, {-4, 4, 5, 0}, 
			{-3, 0, -4, 0}, {-3, 0, 5, 0}, {-3, 1, -4, 0}, {-3, 1, 5, 0}, {-3, 3, -4, 0}, {-3, 3, 5, 0}, {-3, 4, -4, 0}, {-3, 4, -3, 0}, {-3, 4, -2, 0}, {-3, 4, -1, 0}, 
			{-3, 4, 0, 0}, {-3, 4, 1, 0}, {-3, 4, 2, 0}, {-3, 4, 3, 0}, {-3, 4, 4, 0}, {-3, 4, 5, 0}, {-2, 0, -4, 0}, {-2, 0, -2, 0}, {-2, 0, -1, 0}, {-2, 0, 0, 0}, 
			{-2, 0, 1, 0}, {-2, 0, 2, 0}, {-2, 0, 3, 0}, {-2, 0, 5, 0}, {-2, 1, -4, 0}, {-2, 1, -2, 0}, {-2, 1, -1, 0}, {-2, 1, 0, 0}, {-2, 1, 1, 0}, {-2, 1, 2, 0}, 
			{-2, 1, 3, 0}, {-2, 1, 5, 0}, {-2, 2, -2, 0}, {-2, 2, -1, 0}, {-2, 2, 0, 0}, {-2, 2, 1, 0}, {-2, 2, 2, 0}, {-2, 2, 3, 0}, {-2, 3, -4, 0}, {-2, 3, 5, 0}, 
			{-2, 4, -4, 0}, {-2, 4, -3, 0}, {-2, 4, 4, 0}, {-2, 4, 5, 0}, {-1, 0, -4, 0}, {-1, 0, -2, 0}, {-1, 0, -1, 0}, {-1, 0, 0, 0}, {-1, 0, 1, 0}, {-1, 0, 2, 0}, 
			{-1, 0, 3, 0}, {-1, 0, 4, 0}, {-1, 0, 5, 0}, {-1, 1, -4, 0}, {-1, 1, -2, 0}, {-1, 1, -1, 0}, {-1, 1, 0, 0}, {-1, 1, 1, 0}, {-1, 1, 2, 0}, {-1, 1, 3, 0}, 
			{-1, 1, 4, 0}, {-1, 1, 5, 0}, {-1, 2, -2, 0}, {-1, 2, -1, 0}, {-1, 2, 0, 0}, {-1, 2, 1, 0}, {-1, 2, 2, 0}, {-1, 2, 3, 0}, {-1, 3, -4, 0}, {-1, 3, -1, 0}, 
			{-1, 3, 0, 0}, {-1, 3, 1, 0}, {-1, 3, 2, 0}, {-1, 3, 5, 0}, {-1, 4, -4, 0}, {-1, 4, -3, 0}, {-1, 4, 4, 0}, {-1, 4, 5, 0}, {0, 0, -4, 0}, {0, 0, -2, 0}, 
			{0, 0, -1, 0}, {0, 0, 0, 0}, {0, 0, 1, 0}, {0, 0, 2, 0}, {0, 0, 3, 0}, {0, 0, 5, 0}, {0, 1, -4, 0}, {0, 1, -2, 0}, {0, 1, -1, 0}, {0, 1, 0, 0}, 
			{0, 1, 1, 0}, {0, 1, 2, 0}, {0, 1, 3, 0}, {0, 1, 5, 0}, {0, 2, -2, 0}, {0, 2, -1, 0}, {0, 2, 0, 0}, {0, 2, 1, 0}, {0, 2, 2, 0}, {0, 2, 3, 0}, 
			{0, 3, -4, 0}, {0, 3, -1, 0}, {0, 3, 0, 0}, {0, 3, 1, 0}, {0, 3, 2, 0}, {0, 3, 5, 0}, {0, 4, -4, 0}, {0, 4, -3, 0}, {0, 4, 4, 0}, {0, 4, 5, 0}, 
			{1, 0, -4, 0}, {1, 0, -2, 0}, {1, 0, -1, 0}, {1, 0, 0, 0}, {1, 0, 1, 0}, {1, 0, 2, 0}, {1, 0, 3, 0}, {1, 0, 5, 0}, {1, 1, -4, 0}, {1, 1, -2, 0}, 
			{1, 1, -1, 0}, {1, 1, 0, 0}, {1, 1, 1, 0}, {1, 1, 2, 0}, {1, 1, 3, 0}, {1, 1, 5, 0}, {1, 2, -2, 0}, {1, 2, -1, 0}, {1, 2, 0, 0}, {1, 2, 1, 0}, 
			{1, 2, 2, 0}, {1, 2, 3, 0}, {1, 3, -4, 0}, {1, 3, -1, 0}, {1, 3, 0, 0}, {1, 3, 1, 0}, {1, 3, 2, 0}, {1, 3, 5, 0}, {1, 4, -4, 0}, {1, 4, -3, 0}, 
			{1, 4, 4, 0}, {1, 4, 5, 0}, {2, 0, -4, 0}, {2, 0, -2, 0}, {2, 0, -1, 0}, {2, 0, 0, 0}, {2, 0, 1, 0}, {2, 0, 2, 0}, {2, 0, 3, 0}, {2, 0, 5, 0}, 
			{2, 1, -4, 0}, {2, 1, -2, 0}, {2, 1, -1, 0}, {2, 1, 0, 0}, {2, 1, 1, 0}, {2, 1, 2, 0}, {2, 1, 3, 0}, {2, 1, 5, 0}, {2, 2, -2, 0}, {2, 2, -1, 0}, 
			{2, 2, 0, 0}, {2, 2, 1, 0}, {2, 2, 2, 0}, {2, 2, 3, 0}, {2, 3, -4, 0}, {2, 3, -1, 0}, {2, 3, 0, 0}, {2, 3, 1, 0}, {2, 3, 2, 0}, {2, 3, 5, 0}, 
			{2, 4, -4, 0}, {2, 4, -3, 0}, {2, 4, 4, 0}, {2, 4, 5, 0}, {3, 0, -4, 0}, {3, 0, -2, 0}, {3, 0, -1, 0}, {3, 0, 0, 0}, {3, 0, 1, 0}, {3, 0, 2, 0}, 
			{3, 0, 3, 0}, {3, 0, 5, 0}, {3, 1, -4, 0}, {3, 1, -2, 0}, {3, 1, -1, 0}, {3, 1, 0, 0}, {3, 1, 1, 0}, {3, 1, 2, 0}, {3, 1, 3, 0}, {3, 1, 5, 0}, 
			{3, 2, -2, 0}, {3, 2, -1, 0}, {3, 2, 0, 0}, {3, 2, 1, 0}, {3, 2, 2, 0}, {3, 2, 3, 0}, {3, 3, -4, 0}, {3, 3, 5, 0}, {3, 4, -4, 0}, {3, 4, -3, 0}, 
			{3, 4, 4, 0}, {3, 4, 5, 0}, {4, 0, -4, 0}, {4, 0, 5, 0}, {4, 1, -4, 0}, {4, 1, 5, 0}, {4, 3, -4, 0}, {4, 3, 5, 0}, {4, 4, -4, 0}, {4, 4, -3, 0}, 
			{4, 4, -2, 0}, {4, 4, -1, 0}, {4, 4, 0, 0}, {4, 4, 1, 0}, {4, 4, 2, 0}, {4, 4, 3, 0}, {4, 4, 4, 0}, {4, 4, 5, 0}, {5, 0, -4, 0}, {5, 0, -3, 0}, 
			{5, 0, -2, 0}, {5, 0, -1, 0}, {5, 0, 0, 0}, {5, 0, 1, 0}, {5, 0, 2, 0}, {5, 0, 3, 0}, {5, 0, 4, 0}, {5, 0, 5, 0}, {5, 1, -4, 0}, {5, 1, -3, 0}, 
			{5, 1, -2, 0}, {5, 1, -1, 0}, {5, 1, 0, 0}, {5, 1, 1, 0}, {5, 1, 2, 0}, {5, 1, 3, 0}, {5, 1, 4, 0}, {5, 1, 5, 0}, {5, 3, -4, 0}, {5, 3, -3, 0}, 
			{5, 3, -2, 0}, {5, 3, -1, 0}, {5, 3, 0, 0}, {5, 3, 1, 0}, {5, 3, 2, 0}, {5, 3, 3, 0}, {5, 3, 4, 0}, {5, 3, 5, 0}, {5, 4, -3, 0}, {5, 4, -2, 0}, 
			{5, 4, -1, 0}, {5, 4, 0, 0}, {5, 4, 1, 0}, {5, 4, 2, 0}, {5, 4, 3, 0}, {5, 4, 4, 0}, {5, 4, 5, 0}, 

			}; 
			 
	static int[][] damp_canopy = { 

			{-4, 2, -4, 1}, {-4, 2, -3, 1}, {-4, 2, -2, 1}, {-4, 2, -1, 1}, {-4, 2, 0, 1}, {-4, 2, 1, 1}, {-4, 2, 2, 1}, {-4, 2, 3, 1}, {-4, 2, 4, 1}, {-4, 2, 5, 1}, 
			{-3, 2, -4, 2}, {-3, 2, 5, 4}, {-3, 3, -3, 2}, {-3, 3, -2, 1}, {-3, 3, -1, 1}, {-3, 3, 0, 1}, {-3, 3, 1, 1}, {-3, 3, 2, 1}, {-3, 3, 3, 1}, {-3, 3, 4, 4}, 
			{-2, 2, -4, 2}, {-2, 2, 5, 4}, {-2, 3, -3, 2}, {-2, 3, 4, 4}, {-2, 4, -2, 8}, {-2, 4, -1, 8}, {-2, 4, 0, 8}, {-2, 4, 1, 8}, {-2, 4, 2, 8}, {-2, 4, 3, 8}, 
			{-1, 2, -4, 2}, {-1, 2, 5, 4}, {-1, 3, -3, 2}, {-1, 4, -2, 8}, {-1, 4, -1, 8}, {-1, 4, 0, 8}, {-1, 4, 1, 8}, {-1, 4, 2, 8}, {-1, 4, 3, 8}, {0, 2, -4, 2}, 
			{0, 2, 5, 4}, {0, 3, -3, 2}, {0, 4, -2, 8}, {0, 4, -1, 8}, {0, 4, 0, 8}, {0, 4, 1, 8}, {0, 4, 2, 8}, {0, 4, 3, 8}, {1, 2, -4, 2}, {1, 2, 5, 4}, 
			{1, 3, -3, 2}, {1, 4, -2, 8}, {1, 4, -1, 8}, {1, 4, 0, 8}, {1, 4, 1, 8}, {1, 4, 2, 8}, {1, 4, 3, 8}, {2, 2, -4, 2}, {2, 2, 5, 4}, {2, 3, -3, 2}, 
			{2, 4, -2, 8}, {2, 4, -1, 8}, {2, 4, 0, 8}, {2, 4, 1, 8}, {2, 4, 2, 8}, {2, 4, 3, 8}, {3, 2, -4, 2}, {3, 2, 5, 4}, {3, 3, -3, 2}, {3, 3, 4, 4}, 
			{3, 4, -2, 8}, {3, 4, -1, 8}, {3, 4, 0, 8}, {3, 4, 1, 8}, {3, 4, 2, 8}, {3, 4, 3, 8}, {4, 2, -4, 2}, {4, 2, 5, 4}, {4, 3, -3, 3}, {4, 3, -2, 3}, 
			{4, 3, -1, 3}, {4, 3, 0, 3}, {4, 3, 1, 3}, {4, 3, 2, 3}, {4, 3, 3, 3}, {4, 3, 4, 3}, {5, 2, -4, 2}, {5, 2, -3, 3}, {5, 2, -2, 3}, {5, 2, -1, 3}, 
			{5, 2, 0, 3}, {5, 2, 1, 3}, {5, 2, 2, 3}, {5, 2, 3, 3}, {5, 2, 4, 3}, {5, 2, 5, 3}, 

			}; 
			 
	static int[][] damp_log = { 

			{-3, 0, -3, 0}, {-3, 0, 4, 0}, {-3, 1, -3, 0}, {-3, 1, 4, 0}, {-3, 2, -3, 0}, {-3, 2, 4, 0}, {4, 0, -3, 0}, {4, 0, 4, 0}, {4, 1, -3, 0}, {4, 1, 4, 0}, 
			{4, 2, -3, 0}, {4, 2, 4, 0}, 

			}; 
			 
	static int[][] damp_wood = { 

			{-3, 0, -2, 0}, {-3, 0, -1, 0}, {-3, 0, 0, 0}, {-3, 0, 1, 0}, {-3, 0, 2, 0}, {-3, 0, 3, 0}, {-3, 1, -2, 0}, {-3, 1, -1, 0}, {-3, 1, 0, 0}, {-3, 1, 1, 0}, 
			{-3, 1, 2, 0}, {-3, 1, 3, 0}, {-3, 2, -2, 0}, {-3, 2, -1, 0}, {-3, 2, 0, 0}, {-3, 2, 1, 0}, {-3, 2, 2, 0}, {-3, 2, 3, 0}, {-2, 0, -3, 0}, {-2, 0, 4, 0}, 
			{-2, 1, -3, 0}, {-2, 1, 4, 0}, {-2, 2, -3, 0}, {-2, 2, 4, 0}, {-1, 0, -3, 0}, {-1, 1, -3, 0}, {-1, 2, -3, 0}, {-1, 2, 4, 0}, {0, 0, -3, 0}, {0, 0, 4, 0}, 
			{0, 1, -3, 0}, {0, 1, 4, 0}, {0, 2, -3, 0}, {0, 2, 4, 0}, {1, 0, -3, 0}, {1, 0, 4, 0}, {1, 1, -3, 0}, {1, 1, 4, 0}, {1, 2, -3, 0}, {1, 2, 4, 0}, 
			{2, 0, -3, 0}, {2, 0, 4, 0}, {2, 1, -3, 0}, {2, 1, 4, 0}, {2, 2, -3, 0}, {2, 2, 4, 0}, {3, 0, -3, 0}, {3, 0, 4, 0}, {3, 1, -3, 0}, {3, 1, 4, 0}, 
			{3, 2, -3, 0}, {3, 2, 4, 0}, {4, 0, -2, 0}, {4, 0, -1, 0}, {4, 0, 0, 0}, {4, 0, 1, 0}, {4, 0, 2, 0}, {4, 0, 3, 0}, {4, 1, -2, 0}, {4, 1, -1, 0}, 
			{4, 1, 0, 0}, {4, 1, 1, 0}, {4, 1, 2, 0}, {4, 1, 3, 0}, {4, 2, -2, 0}, {4, 2, -1, 0}, {4, 2, 0, 0}, {4, 2, 1, 0}, {4, 2, 2, 0}, {4, 2, 3, 0}, 


			}; 
			 
	static int[][] damp_wood_stairs = { 

			{-2, 3, -2, 5}, {-2, 3, -1, 5}, {-2, 3, 0, 5}, {-2, 3, 1, 5}, {-2, 3, 2, 5}, {-2, 3, 3, 6}, {-1, 3, -2, 7}, {-1, 3, 3, 6}, {0, 3, -2, 7}, {0, 3, 3, 6}, 
			{1, 3, -2, 7}, {1, 3, 3, 6}, {2, 3, -2, 7}, {2, 3, 3, 6}, {3, 3, -2, 4}, {3, 3, -1, 4}, {3, 3, 0, 4}, {3, 3, 1, 4}, {3, 3, 2, 4}, {3, 3, 3, 6}, 


			}; 
			 
	static int[][] damp_canopy_wood = { 

			{-1, 3, 4, 4}, {0, 3, 4, 4}, {1, 3, 4, 4}, {2, 3, 4, 4}, 

			}; 

	static int[] doorCoords = {-1,-1,4};
	
	
	

}
