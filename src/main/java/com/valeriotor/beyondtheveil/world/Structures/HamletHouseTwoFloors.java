package com.valeriotor.beyondtheveil.world.Structures;

import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.EntityHamletDweller;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HamletHouseTwoFloors extends HamletStructure{
	
	public HamletHouseTwoFloors(World w) {
		this.world = w;
		this.radius = 7;
	}
	
	@Override
	public void StartStructure(Random r) {
		this.isItOnWater = this.isOnWater(dark_sand);
		IBlockState state = BlockRegistry.DarkSand.getDefaultState();
		if(this.isOnWater(dark_sand)) {
			state = BlockRegistry.DampWood.getDefaultState();
		}else {
			this.fillFloor(dark_sand);
		}
		
		if(facing==EnumFacing.NORTH){
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.AIR.getStateFromMeta(n[3]+2));
			for(int[] n : this.fence) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.lamp) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.BlockLamp.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]));
			for(int[] n : this.ladder) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.LADDER.getStateFromMeta(n[3]));
			
			}else if(facing==EnumFacing.EAST){
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.fence) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.lamp) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.BlockLamp.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));	
			for(int[] n : this.ladder) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.LADDER.getStateFromMeta(3));
			
			}else if(facing==EnumFacing.SOUTH){
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.fence) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.lamp) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.BlockLamp.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.ladder) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.LADDER.getStateFromMeta(n[3]+1));
			
			}else if(facing==EnumFacing.WEST){
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.fence) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.lamp) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.BlockLamp.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW()));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW()));
			for(int[] n : this.ladder) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.LADDER.getStateFromMeta(2));
			
		
			}
		
	}
	
	@Override
	public void doorRoads() {
		this.roadHelper(doorCoords);
	}
	
	@Override
	public void spawnHamletDwellers() {
		BlockPos home = this.getPosFromArray(villagerCoords, this.facing);
		EntityHamletDweller h = new EntityHamletDweller(this.world);
		h.setProfession(EntityHamletDweller.ProfessionsEnum.CARPENTER);
		h.setHome(home);
		h.setVillageCenter(this.villageCenter);
		h.setPosition(home.getX(), home.getY(), home.getZ());
		this.world.spawnEntity(h);
		
	}
	
	static int[][] dark_sand = { 

			{-4, -1, -4, 0}, {-4, -1, -3, 0}, {-4, -1, -2, 0}, {-4, -1, -1, 0}, {-4, -1, 0, 0}, {-4, -1, 1, 0}, {-4, -1, 2, 0}, {-4, -1, 3, 0}, {-4, -1, 4, 0}, {-3, -1, -4, 0}, 
			{-3, -1, -3, 0}, {-3, -1, -2, 0}, {-3, -1, -1, 0}, {-3, -1, 0, 0}, {-3, -1, 1, 0}, {-3, -1, 2, 0}, {-3, -1, 3, 0}, {-3, -1, 4, 0}, {-2, -1, -4, 0}, {-2, -1, -3, 0}, 
			{-2, -1, -2, 0}, {-2, -1, -1, 0}, {-2, -1, 0, 0}, {-2, -1, 1, 0}, {-2, -1, 2, 0}, {-2, -1, 3, 0}, {-2, -1, 4, 0}, {-1, -1, -4, 0}, {-1, -1, -3, 0}, {-1, -1, -2, 0}, 
			{-1, -1, -1, 0}, {-1, -1, 0, 0}, {-1, -1, 1, 0}, {-1, -1, 2, 0}, {-1, -1, 3, 0}, {-1, -1, 4, 0}, {0, -1, -4, 0}, {0, -1, -3, 0}, {0, -1, -2, 0}, {0, -1, -1, 0}, 
			{0, -1, 0, 0}, {0, -1, 1, 0}, {0, -1, 2, 0}, {0, -1, 3, 0}, {0, -1, 4, 0}, {1, -1, -4, 0}, {1, -1, -3, 0}, {1, -1, -2, 0}, {1, -1, -1, 0}, {1, -1, 0, 0}, 
			{1, -1, 1, 0}, {1, -1, 2, 0}, {1, -1, 3, 0}, {1, -1, 4, 0}, {2, -1, -4, 0}, {2, -1, -3, 0}, {2, -1, -2, 0}, {2, -1, -1, 0}, {2, -1, 0, 0}, {2, -1, 1, 0}, 
			{2, -1, 2, 0}, {2, -1, 3, 0}, {2, -1, 4, 0}, {3, -1, -4, 0}, {3, -1, -3, 0}, {3, -1, -2, 0}, {3, -1, -1, 0}, {3, -1, 0, 0}, {3, -1, 1, 0}, {3, -1, 2, 0}, 
			{3, -1, 3, 0}, {3, -1, 4, 0}, {4, -1, -4, 0}, {4, -1, -3, 0}, {4, -1, -2, 0}, {4, -1, -1, 0}, {4, -1, 0, 0}, {4, -1, 1, 0}, {4, -1, 2, 0}, {4, -1, 3, 0}, 
			{4, -1, 4, 0}, 

			}; 
			 
	static int[][] air = { 

			{-4, 0, -4, 0}, {-4, 0, -3, 0}, {-4, 0, -2, 0}, {-4, 0, -1, 0}, {-4, 0, 0, 0}, {-4, 0, 1, 0}, {-4, 0, 2, 0}, {-4, 0, 3, 0}, {-4, 0, 4, 0}, {-4, 1, -4, 0}, 
			{-4, 1, -3, 0}, {-4, 1, -2, 0}, {-4, 1, -1, 0}, {-4, 1, 0, 0}, {-4, 1, 1, 0}, {-4, 1, 2, 0}, {-4, 1, 3, 0}, {-4, 1, 4, 0}, {-4, 2, -4, 0}, {-4, 2, -3, 0}, 
			{-4, 2, -2, 0}, {-4, 2, -1, 0}, {-4, 2, 0, 0}, {-4, 2, 1, 0}, {-4, 2, 2, 0}, {-4, 2, 3, 0}, {-4, 2, 4, 0}, {-4, 3, -4, 0}, {-4, 3, -3, 0}, {-4, 3, -2, 0}, 
			{-4, 3, -1, 0}, {-4, 3, 0, 0}, {-4, 3, 1, 0}, {-4, 3, 2, 0}, {-4, 3, 3, 0}, {-4, 3, 4, 0}, {-4, 4, -4, 0}, {-4, 4, -3, 0}, {-4, 4, -2, 0}, {-4, 4, -1, 0}, 
			{-4, 4, 0, 0}, {-4, 4, 1, 0}, {-4, 4, 2, 0}, {-4, 4, 3, 0}, {-4, 4, 4, 0}, {-4, 6, -4, 0}, {-4, 6, -3, 0}, {-4, 6, -2, 0}, {-4, 6, -1, 0}, {-4, 6, 0, 0}, 
			{-4, 6, 1, 0}, {-4, 6, 2, 0}, {-4, 6, 3, 0}, {-4, 6, 4, 0}, {-4, 7, -4, 0}, {-4, 7, -3, 0}, {-4, 7, -2, 0}, {-4, 7, -1, 0}, {-4, 7, 0, 0}, {-4, 7, 1, 0}, 
			{-4, 7, 2, 0}, {-4, 7, 3, 0}, {-3, 0, -4, 0}, {-3, 0, 4, 0}, {-3, 1, -4, 0}, {-3, 1, 4, 0}, {-3, 2, -4, 0}, {-3, 2, 4, 0}, {-3, 3, -4, 0}, {-3, 3, 4, 0}, 
			{-3, 4, -4, 0}, {-3, 4, 4, 0}, {-3, 6, -4, 0}, {-3, 6, 4, 0}, {-3, 7, -4, 0}, {-3, 7, -3, 0}, {-3, 7, -2, 0}, {-3, 7, -1, 0}, {-3, 7, 0, 0}, {-3, 7, 1, 0}, 
			{-3, 7, 2, 0}, {-3, 7, 3, 0}, {-3, 7, 4, 0}, {-2, 0, -4, 0}, {-2, 0, -1, 0}, {-2, 0, 0, 0}, {-2, 0, 1, 0}, {-2, 0, 2, 0}, {-2, 0, 4, 0}, {-2, 1, -4, 0}, 
			{-2, 1, -1, 0}, {-2, 1, 0, 0}, {-2, 1, 1, 0}, {-2, 1, 2, 0}, {-2, 1, 4, 0}, {-2, 2, -4, 0}, {-2, 2, 4, 0}, {-2, 3, -4, 0}, {-2, 3, -2, 0}, {-2, 3, -1, 0}, 
			{-2, 3, 0, 0}, {-2, 3, 1, 0}, {-2, 3, 2, 0}, {-2, 3, 4, 0}, {-2, 4, -4, 0}, {-2, 4, -2, 0}, {-2, 4, -1, 0}, {-2, 4, 0, 0}, {-2, 4, 1, 0}, {-2, 4, 2, 0}, 
			{-2, 4, 3, 0}, {-2, 4, 4, 0}, {-2, 6, -4, 0}, {-2, 6, -2, 0}, {-2, 6, -1, 0}, {-2, 6, 0, 0}, {-2, 6, 1, 0}, {-2, 6, 2, 0}, {-2, 6, 4, 0}, {-2, 7, -4, 0}, 
			{-2, 7, -3, 0}, {-2, 7, 3, 0}, {-2, 7, 4, 0}, {-1, 0, -4, 0}, {-1, 0, -2, 0}, {-1, 0, -1, 0}, {-1, 0, 0, 0}, {-1, 0, 1, 0}, {-1, 0, 2, 0}, {-1, 0, 3, 0}, 
			{-1, 0, 4, 0}, {-1, 1, -4, 0}, {-1, 1, -2, 0}, {-1, 1, -1, 0}, {-1, 1, 0, 0}, {-1, 1, 1, 0}, {-1, 1, 2, 0}, {-1, 1, 3, 0}, {-1, 1, 4, 0}, {-1, 2, -4, 0}, 
			{-1, 2, 4, 0}, {-1, 3, -4, 0}, {-1, 3, -2, 0}, {-1, 3, -1, 0}, {-1, 3, 0, 0}, {-1, 3, 1, 0}, {-1, 3, 2, 0}, {-1, 3, 4, 0}, {-1, 4, -4, 0}, {-1, 4, -2, 0}, 
			{-1, 4, -1, 0}, {-1, 4, 0, 0}, {-1, 4, 1, 0}, {-1, 4, 2, 0}, {-1, 4, 3, 0}, {-1, 4, 4, 0}, {-1, 6, -4, 0}, {-1, 6, -2, 0}, {-1, 6, -1, 0}, {-1, 6, 0, 0}, 
			{-1, 6, 4, 0}, {-1, 7, -4, 0}, {-1, 7, -3, 0}, {-1, 7, 1, 0}, {-1, 7, 2, 0}, {-1, 7, 3, 0}, {-1, 7, 4, 0}, {0, 0, -4, 0}, {0, 0, -2, 0}, {0, 0, -1, 0}, 
			{0, 0, 0, 0}, {0, 0, 1, 0}, {0, 0, 2, 0}, {0, 0, 4, 0}, {0, 1, -4, 0}, {0, 1, -2, 0}, {0, 1, -1, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 1, 2, 0}, 
			{0, 1, 4, 0}, {0, 2, -4, 0}, {0, 2, 4, 0}, {0, 3, -4, 0}, {0, 3, -2, 0}, {0, 3, -1, 0}, {0, 3, 0, 0}, {0, 3, 1, 0}, {0, 3, 2, 0}, {0, 3, 4, 0}, 
			{0, 4, -4, 0}, {0, 4, -2, 0}, {0, 4, -1, 0}, {0, 4, 0, 0}, {0, 4, 1, 0}, {0, 4, 2, 0}, {0, 4, 3, 0}, {0, 4, 4, 0}, {0, 6, -4, 0}, {0, 6, 0, 0}, 
			{0, 6, 2, 0}, {0, 6, 3, 0}, {0, 6, 4, 0}, {0, 7, -4, 0}, {0, 7, -3, 0}, {0, 7, -2, 0}, {0, 7, -1, 0}, {0, 7, 1, 0}, {0, 7, 2, 0}, {0, 7, 3, 0}, 
			{0, 7, 4, 0}, {1, 0, -4, 0}, {1, 0, -2, 0}, {1, 0, 4, 0}, {1, 1, -4, 0}, {1, 1, -2, 0}, {1, 1, 4, 0}, {1, 2, -4, 0}, {1, 2, 4, 0}, {1, 4, -4, 0}, 
			{1, 4, 4, 0}, {1, 5, -1, 0}, {1, 5, 0, 0}, {1, 5, 1, 0}, {1, 5, 3, 0}, {1, 5, 4, 0}, {1, 6, -4, 0}, {1, 6, -3, 0}, {1, 6, -2, 0}, {1, 6, 2, 0}, 
			{1, 6, 3, 0}, {1, 6, 4, 0}, {1, 7, -4, 0}, {1, 7, -3, 0}, {1, 7, -2, 0}, {1, 7, -1, 0}, {1, 7, 0, 0}, {1, 7, 1, 0}, {1, 7, 2, 0}, {1, 7, 3, 0}, 
			{1, 7, 4, 0}, {2, 0, -4, 0}, {2, 0, -2, 0}, {2, 0, 0, 0}, {2, 0, 1, 0}, {2, 0, 2, 0}, {2, 0, 4, 0}, {2, 1, -4, 0}, {2, 1, -2, 0}, {2, 1, 0, 0}, 
			{2, 1, 1, 0}, {2, 1, 2, 0}, {2, 1, 4, 0}, {2, 2, -4, 0}, {2, 2, 4, 0}, {2, 3, -2, 0}, {2, 3, -1, 0}, {2, 3, 0, 0}, {2, 3, 1, 0}, {2, 3, 2, 0}, 
			{2, 4, -4, 0}, {2, 4, -2, 0}, {2, 4, -1, 0}, {2, 4, 0, 0}, {2, 4, 1, 0}, {2, 4, 2, 0}, {2, 4, 4, 0}, {2, 5, -4, 0}, {2, 5, -3, 0}, {2, 5, 3, 0}, 
			{2, 5, 4, 0}, {2, 6, -4, 0}, {2, 6, -3, 0}, {2, 6, -2, 0}, {2, 6, -1, 0}, {2, 6, 0, 0}, {2, 6, 1, 0}, {2, 6, 2, 0}, {2, 6, 3, 0}, {2, 6, 4, 0}, 
			{2, 7, -4, 0}, {2, 7, -3, 0}, {2, 7, -2, 0}, {2, 7, -1, 0}, {2, 7, 0, 0}, {2, 7, 1, 0}, {2, 7, 2, 0}, {2, 7, 3, 0}, {2, 7, 4, 0}, {3, 0, -4, 0}, 
			{3, 0, -2, 0}, {3, 0, -1, 0}, {3, 0, 0, 0}, {3, 0, 1, 0}, {3, 0, 2, 0}, {3, 0, 4, 0}, {3, 1, -4, 0}, {3, 1, -2, 0}, {3, 1, -1, 0}, {3, 1, 0, 0}, 
			{3, 1, 2, 0}, {3, 1, 4, 0}, {3, 2, -4, 0}, {3, 2, -2, 0}, {3, 2, -1, 0}, {3, 2, 4, 0}, {3, 4, -4, 0}, {3, 4, 4, 0}, {3, 5, -4, 0}, {3, 5, -3, 0}, 
			{3, 5, -2, 0}, {3, 5, -1, 0}, {3, 5, 0, 0}, {3, 5, 1, 0}, {3, 5, 2, 0}, {3, 5, 3, 0}, {3, 5, 4, 0}, {3, 6, -4, 0}, {3, 6, -3, 0}, {3, 6, -2, 0}, 
			{3, 6, -1, 0}, {3, 6, 0, 0}, {3, 6, 1, 0}, {3, 6, 2, 0}, {3, 6, 3, 0}, {3, 6, 4, 0}, {3, 7, -4, 0}, {3, 7, -3, 0}, {3, 7, -2, 0}, {3, 7, -1, 0}, 
			{3, 7, 0, 0}, {3, 7, 1, 0}, {3, 7, 2, 0}, {3, 7, 3, 0}, {3, 7, 4, 0}, {4, 0, -4, 0}, {4, 0, 4, 0}, {4, 1, -4, 0}, {4, 1, 4, 0}, {4, 2, -4, 0}, 
			{4, 2, 4, 0}, {4, 4, -4, 0}, {4, 4, -3, 0}, {4, 4, -2, 0}, {4, 4, -1, 0}, {4, 4, 0, 0}, {4, 4, 1, 0}, {4, 4, 2, 0}, {4, 4, 3, 0}, {4, 4, 4, 0}, 
			{4, 5, -4, 0}, {4, 5, -3, 0}, {4, 5, -2, 0}, {4, 5, -1, 0}, {4, 5, 0, 0}, {4, 5, 1, 0}, {4, 5, 2, 0}, {4, 5, 3, 0}, {4, 5, 4, 0}, {4, 6, -4, 0}, 
			{4, 6, -3, 0}, {4, 6, -2, 0}, {4, 6, -1, 0}, {4, 6, 0, 0}, {4, 6, 1, 0}, {4, 6, 2, 0}, {4, 6, 3, 0}, {4, 6, 4, 0}, {4, 7, -4, 0}, {4, 7, -3, 0}, 
			{4, 7, -2, 0}, {4, 7, -1, 0}, {4, 7, 0, 0}, {4, 7, 1, 0}, {4, 7, 2, 0}, {4, 7, 3, 0}, {4, 7, 4, 0}, 

			}; 
			 
	static int[][] damp_canopy = { 

			{-4, 5, -4, 2}, {-4, 5, -3, 1}, {-4, 5, -2, 1}, {-4, 5, -1, 1}, {-4, 5, 0, 1}, {-4, 5, 1, 1}, {-4, 5, 2, 1}, {-4, 5, 3, 1}, {-4, 5, 4, 1}, {-3, 5, -4, 2}, 
			{-3, 5, 4, 4}, {-3, 6, -3, 1}, {-3, 6, -2, 1}, {-3, 6, -1, 1}, {-3, 6, 0, 1}, {-3, 6, 1, 1}, {-3, 6, 2, 1}, {-3, 6, 3, 1}, {-2, 5, -4, 2}, {-2, 5, 4, 4}, 
			{-2, 6, -3, 2}, {-2, 6, 3, 4}, {-2, 7, -2, 8}, {-2, 7, -1, 8}, {-2, 7, 0, 8}, {-2, 7, 1, 8}, {-2, 7, 2, 8}, {-1, 5, -4, 2}, {-1, 5, 4, 4}, {-1, 6, -3, 2}, 
			{-1, 6, 1, 3}, {-1, 6, 2, 3}, {-1, 6, 3, 4}, {-1, 7, -2, 8}, {-1, 7, -1, 8}, {-1, 7, 0, 8}, {0, 5, -4, 2}, {0, 5, 2, 4}, {0, 5, 3, 3}, {0, 5, 4, 4}, 
			{0, 6, -3, 2}, {0, 6, -2, 3}, {0, 6, -1, 3}, {0, 6, 1, 4}, {0, 7, 0, 8}, {1, 3, -4, 2}, {1, 3, 4, 4}, {1, 4, -3, 2}, {1, 5, -4, 2}, {1, 5, -3, 3}, 
			{1, 5, -2, 2}, {1, 6, -1, 2}, {1, 6, 0, 3}, {1, 6, 1, 4}, {2, 3, -4, 2}, {2, 3, 4, 4}, {2, 4, -3, 2}, {2, 4, 3, 4}, {2, 5, -2, 2}, {2, 5, -1, 3}, 
			{2, 5, 0, 3}, {2, 5, 1, 3}, {2, 5, 2, 4}, {3, 3, -4, 2}, {3, 3, 4, 4}, {3, 4, -3, 2}, {3, 4, -2, 3}, {3, 4, -1, 3}, {3, 4, 0, 3}, {3, 4, 1, 3}, 
			{3, 4, 2, 3}, {3, 4, 3, 3}, {4, 3, -4, 3}, {4, 3, 4, 4}, 

			}; 
			 
			 
	static int[][] damp_log = { 

			{-3, 0, -3, 0}, {-3, 0, 3, 0}, {-3, 1, -3, 0}, {-3, 1, 3, 0}, {-3, 2, -3, 0}, {-3, 2, 3, 0}, {-3, 3, -3, 0}, {-3, 3, 3, 0}, {-3, 4, -3, 0}, {-3, 4, 3, 0}, 
			{-3, 5, -3, 0}, {-3, 5, 3, 0}, {4, 0, -3, 0}, {4, 0, 3, 0}, {4, 1, -3, 0}, {4, 1, 3, 0}, {4, 2, -3, 0}, {4, 2, 3, 0}, 

			}; 
			 
	static int[][] damp_wood = { 

			{-3, 0, -2, 0}, {-3, 0, -1, 0}, {-3, 0, 0, 0}, {-3, 0, 1, 0}, {-3, 0, 2, 0}, {-3, 1, -2, 0}, {-3, 1, -1, 0}, {-3, 1, 0, 0}, {-3, 1, 1, 0}, {-3, 1, 2, 0}, 
			{-3, 2, -2, 0}, {-3, 2, -1, 0}, {-3, 2, 0, 0}, {-3, 2, 1, 0}, {-3, 2, 2, 0}, {-3, 3, -2, 0}, {-3, 3, -1, 0}, {-3, 3, 0, 0}, {-3, 3, 1, 0}, {-3, 3, 2, 0}, 
			{-3, 4, -2, 0}, {-3, 4, -1, 0}, {-3, 4, 0, 0}, {-3, 4, 1, 0}, {-3, 4, 2, 0}, {-3, 5, -2, 0}, {-3, 5, -1, 0}, {-3, 5, 0, 0}, {-3, 5, 1, 0}, {-3, 5, 2, 0}, 
			{-2, 0, -3, 0}, {-2, 0, 3, 0}, {-2, 1, -3, 0}, {-2, 1, 3, 0}, {-2, 2, -3, 0}, {-2, 2, -1, 0}, {-2, 2, 0, 0}, {-2, 2, 1, 0}, {-2, 2, 2, 0}, {-2, 2, 3, 0}, 
			{-2, 3, -3, 0}, {-2, 4, -3, 0}, {-2, 5, -3, 0}, {-2, 5, -2, 0}, {-2, 5, -1, 0}, {-2, 5, 0, 0}, {-2, 5, 1, 0}, {-2, 5, 2, 0}, {-2, 5, 3, 0}, {-1, 0, -3, 0}, 
			{-1, 1, -3, 0}, {-1, 2, -3, 0}, {-1, 2, -2, 0}, {-1, 2, -1, 0}, {-1, 2, 0, 0}, {-1, 2, 1, 0}, {-1, 2, 2, 0}, {-1, 2, 3, 0}, {-1, 3, -3, 0}, {-1, 4, -3, 0}, 
			{-1, 5, -3, 0}, {-1, 5, -2, 0}, {-1, 5, -1, 0}, {-1, 5, 0, 0}, {-1, 5, 1, 0}, {-1, 5, 2, 0}, {-1, 5, 3, 0}, {0, 0, -3, 0}, {0, 0, 3, 0}, {0, 1, -3, 0}, 
			{0, 1, 3, 0}, {0, 2, -3, 0}, {0, 2, -2, 0}, {0, 2, -1, 0}, {0, 2, 0, 0}, {0, 2, 1, 0}, {0, 2, 2, 0}, {0, 2, 3, 0}, {0, 3, -3, 0}, {0, 4, -3, 0}, 
			{0, 5, -3, 0}, {0, 5, -2, 0}, {0, 5, -1, 0}, {0, 5, 0, 0}, {0, 5, 1, 0}, {1, 0, -3, 0}, {1, 0, -1, 0}, {1, 0, 0, 0}, {1, 0, 1, 0}, {1, 0, 2, 0}, 
			{1, 0, 3, 0}, {1, 1, -3, 0}, {1, 1, -1, 0}, {1, 1, 0, 0}, {1, 1, 1, 0}, {1, 1, 2, 0}, {1, 1, 3, 0}, {1, 2, -3, 0}, {1, 2, -2, 0}, {1, 2, -1, 0}, 
			{1, 2, 0, 0}, {1, 2, 1, 0}, {1, 2, 2, 0}, {1, 2, 3, 0}, {1, 3, -3, 0}, {1, 3, -2, 0}, {1, 3, -1, 0}, {1, 3, 0, 0}, {1, 3, 1, 0}, {1, 3, 2, 0}, 
			{1, 3, 3, 0}, {1, 4, -2, 0}, {1, 4, -1, 0}, {1, 4, 0, 0}, {1, 4, 1, 0}, {1, 4, 2, 0}, {2, 0, -3, 0}, {2, 0, -1, 0}, {2, 0, 3, 0}, {2, 1, -3, 0}, 
			{2, 1, -1, 0}, {2, 1, 3, 0}, {2, 2, -3, 0}, {2, 2, -2, 0}, {2, 2, -1, 0}, {2, 2, 0, 0}, {2, 2, 1, 0}, {2, 2, 2, 0}, {2, 2, 3, 0}, {2, 3, -3, 0}, 
			{2, 3, 3, 0}, {3, 0, -3, 0}, {3, 0, 3, 0}, {3, 1, -3, 0}, {3, 1, 3, 0}, {3, 2, -3, 0}, {3, 2, 0, 0}, {3, 2, 1, 0}, {3, 2, 2, 0}, {3, 2, 3, 0}, 
			{3, 3, -3, 0}, {3, 3, -2, 0}, {3, 3, -1, 0}, {3, 3, 0, 0}, {3, 3, 1, 0}, {3, 3, 2, 0}, {3, 3, 3, 0}, {4, 0, -2, 0}, {4, 0, -1, 0}, {4, 0, 0, 0}, 
			{4, 0, 1, 0}, {4, 0, 2, 0}, {4, 1, -2, 0}, {4, 1, -1, 0}, {4, 1, 0, 0}, {4, 1, 1, 0}, {4, 1, 2, 0}, {4, 2, -2, 0}, {4, 2, -1, 0}, {4, 2, 0, 0}, 
			{4, 2, 1, 0}, {4, 2, 2, 0}, 

			}; 
			 
	static int[][] ladder = { 

			{-2, 0, -2, 3}, {-2, 1, -2, 3}, {-2, 2, -2, 3}, 

			}; 
			 
	static int[][] fence = { 

			{-2, 3, 3, 0}, {-1, 3, 3, 0}, {0, 3, 3, 0}, 

			}; 
			 
	static int[][] damp_canopy_wood = { 

			{1, 4, 3, 4}, {1, 5, 2, 4}, {4, 3, -3, 3}, {4, 3, -2, 3}, {4, 3, -1, 3}, {4, 3, 0, 3}, {4, 3, 1, 3}, {4, 3, 2, 3}, {4, 3, 3, 3}, 

			}; 
			 
	static int[][] lamp = { 

			{3, 1, 1, 0}, 

			}; 
			
	static int[] doorCoords = {-1,-1,3};
			
	static int[] villagerCoords = {-1, 3, 1};
			
}
