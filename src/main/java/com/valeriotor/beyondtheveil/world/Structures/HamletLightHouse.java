package com.valeriotor.beyondtheveil.world.Structures;

import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.EntityHamletDweller;
import com.valeriotor.beyondtheveil.world.Structures.loot.LootTables;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HamletLightHouse extends HamletStructure{
	
	public HamletLightHouse(World w) {
		this.world = w;
		this.radius = 12;
	}
	
	@Override
	public boolean isLarge() {
		return true;
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
			for(int[] n : this.air) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]));
			for(int[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]));
			for(int[] n : this.bricks_blue) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(int[] n : this.worn_bricks) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(int[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.seaLantern) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(int[] n : this.chest) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(int[] n : this.prismarine) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(int[] n : this.hellrock) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(int[] n : this.fire) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.FIRE.getStateFromMeta(n[3]));
			}else if(facing==EnumFacing.EAST){
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(int[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(int[] n : this.bricks_blue) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(int[] n : this.worn_bricks) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(int[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.WornBrickStairs.FACING, BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.WornBrickStairs.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.seaLantern) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(int[] n : this.chest) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(int[] n : this.prismarine) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(int[] n : this.hellrock) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(int[] n : this.fire) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.FIRE.getStateFromMeta(n[3]));
			}else if(facing==EnumFacing.SOUTH){
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.bricks_blue) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(int[] n : this.worn_bricks) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(int[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.WornBrickStairs.FACING, BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.WornBrickStairs.FACING).rotateYCCW().rotateYCCW()));
			for(int[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.seaLantern) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(int[] n : this.chest) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(int[] n : this.prismarine) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(int[] n : this.hellrock) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(int[] n : this.fire) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.FIRE.getStateFromMeta(n[3]));
			}else if(facing==EnumFacing.WEST){
			for(int[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), state);
			for(int[] n : this.air) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(int[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(int[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(int[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW()));
			for(int[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW()));
			for(int[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(int[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW()));
			for(int[] n : this.bricks_blue) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(int[] n : this.worn_bricks) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(int[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.WornBrickStairs.FACING, BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.WornBrickStairs.FACING).rotateYCCW()));
			for(int[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(int[] n : this.seaLantern) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(int[] n : this.chest) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(int[] n : this.prismarine) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(int[] n : this.hellrock) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(int[] n : this.fire) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.FIRE.getStateFromMeta(n[3]));
			}
		
		for(int[] n : this.chest) {
			TileEntity t = world.getTileEntity(this.getPosFromArray(n, facing));
			if(t instanceof TileEntityChest) {
				((TileEntityChest)t).setLootTable(LootTables.hamletLightHouse, r.nextLong());
			}
		}
		
	}
	
	@Override
	public void doorRoads() {		
		this.roadHelper(this.isItOnWater ? doorCoords2 : doorCoords);
	}
	
	@Override
	public void spawnHamletDwellers() {
		BlockPos home = this.getPosFromArray(this.facing.getHorizontalIndex() == 3 || this.facing.getHorizontalIndex() == 0 ? villagerCoordsWN : villagerCoordsES, this.facing);
		EntityHamletDweller h = new EntityHamletDweller(this.world);
		h.setProfession(EntityHamletDweller.ProfessionsEnum.LHKEEPER);
		h.setHome(home);
		h.setVillageCenter(this.villageCenter);
		h.setPosition(home.getX(), home.getY(), home.getZ());
		this.world.spawnEntity(h);
		
	}
	
	
	static int[][] dark_sand = { 

			{-8, -1, -6, 0}, {-8, -1, -5, 0}, {-8, -1, -4, 0}, {-8, -1, -3, 0}, {-8, -1, -2, 0}, {-8, -1, -1, 0}, {-8, -1, 0, 0}, {-8, -1, 1, 0}, {-8, -1, 2, 0}, {-8, -1, 3, 0}, 
			{-8, -1, 4, 0}, {-8, -1, 5, 0}, {-8, -1, 6, 0}, {-8, -1, 7, 0}, {-7, -1, -6, 0}, {-7, -1, -5, 0}, {-7, -1, -4, 0}, {-7, -1, -3, 0}, {-7, -1, -2, 0}, {-7, -1, -1, 0}, 
			{-7, -1, 0, 0}, {-7, -1, 1, 0}, {-7, -1, 2, 0}, {-7, -1, 3, 0}, {-7, -1, 4, 0}, {-7, -1, 5, 0}, {-7, -1, 6, 0}, {-7, -1, 7, 0}, {-6, -1, -6, 0}, {-6, -1, -5, 0}, 
			{-6, -1, -4, 0}, {-6, -1, -3, 0}, {-6, -1, -2, 0}, {-6, -1, -1, 0}, {-6, -1, 0, 0}, {-6, -1, 1, 0}, {-6, -1, 2, 0}, {-6, -1, 3, 0}, {-6, -1, 4, 0}, {-6, -1, 5, 0}, 
			{-6, -1, 6, 0}, {-6, -1, 7, 0}, {-5, -1, -6, 0}, {-5, -1, -5, 0}, {-5, -1, -4, 0}, {-5, -1, -3, 0}, {-5, -1, -2, 0}, {-5, -1, -1, 0}, {-5, -1, 0, 0}, {-5, -1, 1, 0}, 
			{-5, -1, 2, 0}, {-5, -1, 3, 0}, {-5, -1, 4, 0}, {-5, -1, 5, 0}, {-5, -1, 6, 0}, {-5, -1, 7, 0}, {-4, -1, -6, 0}, {-4, -1, -5, 0}, {-4, -1, -4, 0}, {-4, -1, -3, 0}, 
			{-4, -1, -2, 0}, {-4, -1, -1, 0}, {-4, -1, 0, 0}, {-4, -1, 1, 0}, {-4, -1, 2, 0}, {-4, -1, 3, 0}, {-4, -1, 4, 0}, {-4, -1, 5, 0}, {-4, -1, 6, 0}, {-4, -1, 7, 0}, 
			{-3, -1, -6, 0}, {-3, -1, -5, 0}, {-3, -1, -4, 0}, {-3, -1, -3, 0}, {-3, -1, -2, 0}, {-3, -1, -1, 0}, {-3, -1, 0, 0}, {-3, -1, 1, 0}, {-3, -1, 2, 0}, {-3, -1, 3, 0}, 
			{-3, -1, 4, 0}, {-3, -1, 5, 0}, {-3, -1, 6, 0}, {-3, -1, 7, 0}, {-2, -1, -6, 0}, {-2, -1, -5, 0}, {-2, -1, -4, 0}, {-2, -1, -3, 0}, {-2, -1, 4, 0}, {-2, -1, 5, 0}, 
			{-2, -1, 6, 0}, {-2, -1, 7, 0}, {-1, -1, -6, 0}, {-1, -1, -5, 0}, {-1, -1, -4, 0}, {-1, -1, 5, 0}, {-1, -1, 6, 0}, {-1, -1, 7, 0}, {0, -1, -6, 0}, {0, -1, -5, 0}, 
			{0, -1, 6, 0}, {0, -1, 7, 0}, {1, -1, -6, 0}, {1, -1, -5, 0}, {1, -1, 6, 0}, {1, -1, 7, 0}, {2, -1, -6, 0}, {2, -1, -5, 0}, {2, -1, 6, 0}, {2, -1, 7, 0}, 
			{3, -1, -6, 0}, {3, -1, -5, 0}, {3, -1, 6, 0}, {3, -1, 7, 0}, {4, -1, -6, 0}, {4, -1, -5, 0}, {4, -1, 6, 0}, {4, -1, 7, 0}, {5, -1, -6, 0}, {5, -1, -5, 0}, 
			{5, -1, 6, 0}, {5, -1, 7, 0}, {6, -1, -6, 0}, {6, -1, -5, 0}, {6, -1, -4, 0}, {6, -1, 5, 0}, {6, -1, 6, 0}, {6, -1, 7, 0}, {7, -1, -6, 0}, {7, -1, -5, 0}, 
			{7, -1, -4, 0}, {7, -1, -3, 0}, {7, -1, 4, 0}, {7, -1, 5, 0}, {7, -1, 6, 0}, {7, -1, 7, 0}, {8, -1, -6, 0}, {8, -1, -5, 0}, {8, -1, -4, 0}, {8, -1, -3, 0}, 
			{8, -1, -2, 0}, {8, -1, -1, 0}, {8, -1, 0, 0}, {8, -1, 1, 0}, {8, -1, 2, 0}, {8, -1, 3, 0}, {8, -1, 4, 0}, {8, -1, 5, 0}, {8, -1, 6, 0}, {8, -1, 7, 0}, 
			{9, -1, -6, 0}, {9, -1, -5, 0}, {9, -1, -4, 0}, {9, -1, -3, 0}, {9, -1, -2, 0}, {9, -1, -1, 0}, {9, -1, 0, 0}, {9, -1, 1, 0}, {9, -1, 2, 0}, {9, -1, 3, 0}, 
			{9, -1, 4, 0}, {9, -1, 5, 0}, {9, -1, 6, 0}, {9, -1, 7, 0}, 

			}; 
			 
	static int[][] air = { 

			{-8, 0, -5, 0}, {-8, 0, -4, 0}, {-8, 0, -3, 0}, {-8, 0, 4, 0}, {-8, 0, 5, 0}, {-8, 0, 6, 0}, {-8, 0, 7, 0}, {-8, 1, -6, 0}, {-8, 1, -5, 0}, {-8, 1, -4, 0}, 
			{-8, 1, -3, 0}, {-8, 1, 4, 0}, {-8, 1, 5, 0}, {-8, 1, 6, 0}, {-8, 1, 7, 0}, {-8, 2, -6, 0}, {-8, 2, -5, 0}, {-8, 2, -4, 0}, {-8, 2, -3, 0}, {-8, 2, 4, 0}, 
			{-8, 2, 5, 0}, {-8, 2, 6, 0}, {-8, 2, 7, 0}, {-8, 3, -6, 0}, {-8, 3, -5, 0}, {-8, 3, -4, 0}, {-8, 3, -3, 0}, {-8, 3, 4, 0}, {-8, 3, 5, 0}, {-8, 3, 6, 0}, 
			{-8, 3, 7, 0}, {-8, 4, -6, 0}, {-8, 4, -5, 0}, {-8, 4, -4, 0}, {-8, 4, -3, 0}, {-8, 4, 4, 0}, {-8, 4, 5, 0}, {-8, 4, 6, 0}, {-8, 4, 7, 0}, {-8, 5, -6, 0}, 
			{-8, 5, -5, 0}, {-8, 5, -3, 0}, {-8, 5, 4, 0}, {-8, 5, 6, 0}, {-8, 5, 7, 0}, {-8, 6, -6, 0}, {-8, 6, -5, 0}, {-8, 6, -4, 0}, {-8, 6, 5, 0}, {-8, 6, 6, 0}, 
			{-8, 6, 7, 0}, {-7, 0, -6, 0}, {-7, 0, -5, 0}, {-7, 0, -4, 0}, {-7, 0, -3, 0}, {-7, 0, -1, 0}, {-7, 0, 2, 0}, {-7, 0, 4, 0}, {-7, 0, 5, 0}, {-7, 0, 6, 0}, 
			{-7, 0, 7, 0}, {-7, 1, -6, 0}, {-7, 1, -5, 0}, {-7, 1, -4, 0}, {-7, 1, -3, 0}, {-7, 1, 0, 0}, {-7, 1, 1, 0}, {-7, 1, 2, 0}, {-7, 1, 4, 0}, {-7, 1, 5, 0}, 
			{-7, 1, 6, 0}, {-7, 1, 7, 0}, {-7, 2, -6, 0}, {-7, 2, -5, 0}, {-7, 2, -4, 0}, {-7, 2, -3, 0}, {-7, 2, -1, 0}, {-7, 2, 1, 0}, {-7, 2, 2, 0}, {-7, 2, 4, 0}, 
			{-7, 2, 5, 0}, {-7, 2, 6, 0}, {-7, 2, 7, 0}, {-7, 3, -6, 0}, {-7, 3, -5, 0}, {-7, 3, -4, 0}, {-7, 3, -3, 0}, {-7, 3, -1, 0}, {-7, 3, 0, 0}, {-7, 3, 4, 0}, 
			{-7, 3, 5, 0}, {-7, 3, 6, 0}, {-7, 3, 7, 0}, {-7, 4, -6, 0}, {-7, 4, -5, 0}, {-7, 4, -4, 0}, {-7, 4, -3, 0}, {-7, 4, -1, 0}, {-7, 4, 0, 0}, {-7, 4, 1, 0}, 
			{-7, 4, 2, 0}, {-7, 4, 4, 0}, {-7, 4, 5, 0}, {-7, 4, 6, 0}, {-7, 4, 7, 0}, {-7, 5, -6, 0}, {-7, 5, -5, 0}, {-7, 5, -3, 0}, {-7, 5, -1, 0}, {-7, 5, 0, 0}, 
			{-7, 5, 1, 0}, {-7, 5, 2, 0}, {-7, 5, 4, 0}, {-7, 5, 6, 0}, {-7, 5, 7, 0}, {-7, 6, -6, 0}, {-7, 6, -5, 0}, {-7, 6, -4, 0}, {-7, 6, -1, 0}, {-7, 6, 0, 0}, 
			{-7, 6, 1, 0}, {-7, 6, 2, 0}, {-7, 6, 5, 0}, {-7, 6, 6, 0}, {-7, 6, 7, 0}, {-6, 0, -6, 0}, {-6, 0, -5, 0}, {-6, 0, -4, 0}, {-6, 0, -3, 0}, {-6, 0, -1, 0}, 
			{-6, 0, 0, 0}, {-6, 0, 1, 0}, {-6, 0, 2, 0}, {-6, 0, 4, 0}, {-6, 0, 5, 0}, {-6, 0, 6, 0}, {-6, 0, 7, 0}, {-6, 1, -6, 0}, {-6, 1, -5, 0}, {-6, 1, -4, 0}, 
			{-6, 1, -3, 0}, {-6, 1, 0, 0}, {-6, 1, 1, 0}, {-6, 1, 2, 0}, {-6, 1, 3, 0}, {-6, 1, 4, 0}, {-6, 1, 5, 0}, {-6, 1, 6, 0}, {-6, 1, 7, 0}, {-6, 2, -6, 0}, 
			{-6, 2, -5, 0}, {-6, 2, -4, 0}, {-6, 2, -3, 0}, {-6, 2, -1, 0}, {-6, 2, 0, 0}, {-6, 2, 1, 0}, {-6, 2, 2, 0}, {-6, 2, 4, 0}, {-6, 2, 5, 0}, {-6, 2, 6, 0}, 
			{-6, 2, 7, 0}, {-6, 3, -6, 0}, {-6, 3, -5, 0}, {-6, 3, -4, 0}, {-6, 3, -3, 0}, {-6, 3, -1, 0}, {-6, 3, 0, 0}, {-6, 3, 1, 0}, {-6, 3, 2, 0}, {-6, 3, 4, 0}, 
			{-6, 3, 5, 0}, {-6, 3, 6, 0}, {-6, 3, 7, 0}, {-6, 4, -6, 0}, {-6, 4, -5, 0}, {-6, 4, -4, 0}, {-6, 4, -3, 0}, {-6, 4, -1, 0}, {-6, 4, 0, 0}, {-6, 4, 1, 0}, 
			{-6, 4, 4, 0}, {-6, 4, 5, 0}, {-6, 4, 6, 0}, {-6, 4, 7, 0}, {-6, 5, -6, 0}, {-6, 5, -5, 0}, {-6, 5, -3, 0}, {-6, 5, 2, 0}, {-6, 5, 4, 0}, {-6, 5, 6, 0}, 
			{-6, 5, 7, 0}, {-6, 6, -6, 0}, {-6, 6, -5, 0}, {-6, 6, -4, 0}, {-6, 6, -1, 0}, {-6, 6, 0, 0}, {-6, 6, 1, 0}, {-6, 6, 2, 0}, {-6, 6, 5, 0}, {-6, 6, 6, 0}, 
			{-6, 6, 7, 0}, {-5, 0, -6, 0}, {-5, 0, -5, 0}, {-5, 0, -4, 0}, {-5, 0, -3, 0}, {-5, 0, 0, 0}, {-5, 0, 1, 0}, {-5, 0, 2, 0}, {-5, 0, 4, 0}, {-5, 0, 5, 0}, 
			{-5, 0, 6, 0}, {-5, 0, 7, 0}, {-5, 1, -6, 0}, {-5, 1, -5, 0}, {-5, 1, -4, 0}, {-5, 1, -3, 0}, {-5, 1, -1, 0}, {-5, 1, 0, 0}, {-5, 1, 1, 0}, {-5, 1, 2, 0}, 
			{-5, 1, 4, 0}, {-5, 1, 5, 0}, {-5, 1, 6, 0}, {-5, 1, 7, 0}, {-5, 2, -6, 0}, {-5, 2, -5, 0}, {-5, 2, -4, 0}, {-5, 2, -3, 0}, {-5, 2, -1, 0}, {-5, 2, 0, 0}, 
			{-5, 2, 1, 0}, {-5, 2, 2, 0}, {-5, 2, 4, 0}, {-5, 2, 5, 0}, {-5, 2, 6, 0}, {-5, 2, 7, 0}, {-5, 3, -6, 0}, {-5, 3, -5, 0}, {-5, 3, -4, 0}, {-5, 3, -3, 0}, 
			{-5, 3, -1, 0}, {-5, 3, 0, 0}, {-5, 3, 1, 0}, {-5, 3, 2, 0}, {-5, 3, 4, 0}, {-5, 3, 5, 0}, {-5, 3, 6, 0}, {-5, 3, 7, 0}, {-5, 4, -6, 0}, {-5, 4, -5, 0}, 
			{-5, 4, -4, 0}, {-5, 4, -3, 0}, {-5, 4, -1, 0}, {-5, 4, 0, 0}, {-5, 4, 1, 0}, {-5, 4, 2, 0}, {-5, 4, 4, 0}, {-5, 4, 5, 0}, {-5, 4, 6, 0}, {-5, 4, 7, 0}, 
			{-5, 5, -6, 0}, {-5, 5, -5, 0}, {-5, 5, -3, 0}, {-5, 5, 4, 0}, {-5, 5, 6, 0}, {-5, 5, 7, 0}, {-5, 6, -6, 0}, {-5, 6, -5, 0}, {-5, 6, -4, 0}, {-5, 6, -1, 0}, 
			{-5, 6, 0, 0}, {-5, 6, 1, 0}, {-5, 6, 2, 0}, {-5, 6, 5, 0}, {-5, 6, 6, 0}, {-5, 6, 7, 0}, {-4, 0, -6, 0}, {-4, 0, -5, 0}, {-4, 0, -4, 0}, {-4, 0, -3, 0}, 
			{-4, 0, -1, 0}, {-4, 0, 0, 0}, {-4, 0, 1, 0}, {-4, 0, 2, 0}, {-4, 0, 4, 0}, {-4, 0, 5, 0}, {-4, 0, 6, 0}, {-4, 0, 7, 0}, {-4, 1, -6, 0}, {-4, 1, -5, 0}, 
			{-4, 1, -4, 0}, {-4, 1, -3, 0}, {-4, 1, -1, 0}, {-4, 1, 0, 0}, {-4, 1, 1, 0}, {-4, 1, 2, 0}, {-4, 1, 4, 0}, {-4, 1, 5, 0}, {-4, 1, 6, 0}, {-4, 1, 7, 0}, 
			{-4, 2, -6, 0}, {-4, 2, -5, 0}, {-4, 2, -4, 0}, {-4, 2, -3, 0}, {-4, 2, -1, 0}, {-4, 2, 0, 0}, {-4, 2, 1, 0}, {-4, 2, 2, 0}, {-4, 2, 4, 0}, {-4, 2, 5, 0}, 
			{-4, 2, 6, 0}, {-4, 2, 7, 0}, {-4, 3, -6, 0}, {-4, 3, -5, 0}, {-4, 3, -4, 0}, {-4, 3, -3, 0}, {-4, 3, -1, 0}, {-4, 3, 0, 0}, {-4, 3, 1, 0}, {-4, 3, 2, 0}, 
			{-4, 3, 4, 0}, {-4, 3, 5, 0}, {-4, 3, 6, 0}, {-4, 3, 7, 0}, {-4, 4, -6, 0}, {-4, 4, -5, 0}, {-4, 4, -4, 0}, {-4, 4, -3, 0}, {-4, 4, -1, 0}, {-4, 4, 0, 0}, 
			{-4, 4, 1, 0}, {-4, 4, 2, 0}, {-4, 4, 4, 0}, {-4, 4, 5, 0}, {-4, 4, 6, 0}, {-4, 4, 7, 0}, {-4, 5, -6, 0}, {-4, 5, -5, 0}, {-4, 5, -3, 0}, {-4, 5, 4, 0}, 
			{-4, 5, 6, 0}, {-4, 5, 7, 0}, {-4, 6, -6, 0}, {-4, 6, -5, 0}, {-4, 6, -4, 0}, {-4, 6, -1, 0}, {-4, 6, 0, 0}, {-4, 6, 1, 0}, {-4, 6, 2, 0}, {-4, 6, 5, 0}, 
			{-4, 6, 6, 0}, {-4, 6, 7, 0}, {-3, 0, -6, 0}, {-3, 0, -5, 0}, {-3, 0, -4, 0}, {-3, 0, -3, 0}, {-3, 0, -1, 0}, {-3, 0, 0, 0}, {-3, 0, 1, 0}, {-3, 0, 2, 0}, 
			{-3, 0, 4, 0}, {-3, 0, 5, 0}, {-3, 0, 6, 0}, {-3, 0, 7, 0}, {-3, 1, -6, 0}, {-3, 1, -5, 0}, {-3, 1, -4, 0}, {-3, 1, -3, 0}, {-3, 1, -1, 0}, {-3, 1, 0, 0}, 
			{-3, 1, 1, 0}, {-3, 1, 2, 0}, {-3, 1, 4, 0}, {-3, 1, 5, 0}, {-3, 1, 6, 0}, {-3, 1, 7, 0}, {-3, 2, -6, 0}, {-3, 2, -5, 0}, {-3, 2, -4, 0}, {-3, 2, -3, 0}, 
			{-3, 2, -1, 0}, {-3, 2, 0, 0}, {-3, 2, 1, 0}, {-3, 2, 2, 0}, {-3, 2, 4, 0}, {-3, 2, 5, 0}, {-3, 2, 6, 0}, {-3, 2, 7, 0}, {-3, 3, -6, 0}, {-3, 3, -5, 0}, 
			{-3, 3, -4, 0}, {-3, 3, -3, 0}, {-3, 3, -1, 0}, {-3, 3, 0, 0}, {-3, 3, 1, 0}, {-3, 3, 2, 0}, {-3, 3, 4, 0}, {-3, 3, 5, 0}, {-3, 3, 6, 0}, {-3, 3, 7, 0}, 
			{-3, 4, -6, 0}, {-3, 4, -5, 0}, {-3, 4, -4, 0}, {-3, 4, -3, 0}, {-3, 4, -1, 0}, {-3, 4, 0, 0}, {-3, 4, 1, 0}, {-3, 4, 2, 0}, {-3, 4, 4, 0}, {-3, 4, 5, 0}, 
			{-3, 4, 6, 0}, {-3, 4, 7, 0}, {-3, 5, -6, 0}, {-3, 5, -5, 0}, {-3, 5, -3, 0}, {-3, 5, 4, 0}, {-3, 5, 6, 0}, {-3, 5, 7, 0}, {-3, 6, -6, 0}, {-3, 6, -5, 0}, 
			{-3, 6, -4, 0}, {-3, 6, -1, 0}, {-3, 6, 0, 0}, {-3, 6, 1, 0}, {-3, 6, 2, 0}, {-3, 6, 5, 0}, {-3, 6, 6, 0}, {-3, 6, 7, 0}, {-2, 0, -6, 0}, {-2, 0, -5, 0}, 
			{-2, 0, -4, 0}, {-2, 0, 0, 0}, {-2, 0, 1, 0}, {-2, 0, 5, 0}, {-2, 0, 6, 0}, {-2, 0, 7, 0}, {-2, 1, -6, 0}, {-2, 1, -5, 0}, {-2, 1, -4, 0}, {-2, 1, 0, 0}, 
			{-2, 1, 1, 0}, {-2, 1, 5, 0}, {-2, 1, 6, 0}, {-2, 1, 7, 0}, {-2, 2, -6, 0}, {-2, 2, -5, 0}, {-2, 2, -4, 0}, {-2, 2, 5, 0}, {-2, 2, 6, 0}, {-2, 2, 7, 0}, 
			{-2, 3, -6, 0}, {-2, 3, -5, 0}, {-2, 3, -4, 0}, {-2, 3, 5, 0}, {-2, 3, 6, 0}, {-2, 3, 7, 0}, {-2, 4, -6, 0}, {-2, 4, -5, 0}, {-2, 4, -4, 0}, {-2, 4, 5, 0}, 
			{-2, 4, 6, 0}, {-2, 4, 7, 0}, {-2, 5, -6, 0}, {-2, 5, 7, 0}, {-2, 6, -6, 0}, {-2, 6, -5, 0}, {-2, 6, -4, 0}, {-2, 6, -1, 0}, {-2, 6, 0, 0}, {-2, 6, 1, 0}, 
			{-2, 6, 2, 0}, {-2, 6, 5, 0}, {-2, 6, 6, 0}, {-2, 6, 7, 0}, {-1, 0, -6, 0}, {-1, 0, -5, 0}, {-1, 0, -2, 0}, {-1, 0, -1, 0}, {-1, 0, 0, 0}, {-1, 0, 1, 0}, 
			{-1, 0, 2, 0}, {-1, 0, 3, 0}, {-1, 0, 6, 0}, {-1, 0, 7, 0}, {-1, 1, -6, 0}, {-1, 1, -5, 0}, {-1, 1, -2, 0}, {-1, 1, -1, 0}, {-1, 1, 0, 0}, {-1, 1, 1, 0}, 
			{-1, 1, 2, 0}, {-1, 1, 3, 0}, {-1, 1, 6, 0}, {-1, 1, 7, 0}, {-1, 2, -6, 0}, {-1, 2, -5, 0}, {-1, 2, -2, 0}, {-1, 2, -1, 0}, {-1, 2, 0, 0}, {-1, 2, 1, 0}, 
			{-1, 2, 2, 0}, {-1, 2, 3, 0}, {-1, 2, 6, 0}, {-1, 2, 7, 0}, {-1, 3, -6, 0}, {-1, 3, -5, 0}, {-1, 3, -2, 0}, {-1, 3, -1, 0}, {-1, 3, 0, 0}, {-1, 3, 1, 0}, 
			{-1, 3, 2, 0}, {-1, 3, 3, 0}, {-1, 3, 6, 0}, {-1, 3, 7, 0}, {-1, 4, -6, 0}, {-1, 4, -5, 0}, {-1, 4, -1, 0}, {-1, 4, 0, 0}, {-1, 4, 1, 0}, {-1, 4, 2, 0}, 
			{-1, 4, 6, 0}, {-1, 4, 7, 0}, {-1, 6, -6, 0}, {-1, 6, -5, 0}, {-1, 6, -2, 0}, {-1, 6, -1, 0}, {-1, 6, 0, 0}, {-1, 6, 1, 0}, {-1, 6, 2, 0}, {-1, 6, 3, 0}, 
			{-1, 6, 6, 0}, {-1, 6, 7, 0}, {0, 0, -6, 0}, {0, 0, -5, 0}, {0, 0, -3, 0}, {0, 0, -2, 0}, {0, 0, -1, 0}, {0, 0, 0, 0}, {0, 0, 1, 0}, {0, 0, 2, 0}, 
			{0, 0, 3, 0}, {0, 0, 4, 0}, {0, 0, 6, 0}, {0, 0, 7, 0}, {0, 1, -6, 0}, {0, 1, -5, 0}, {0, 1, -3, 0}, {0, 1, -2, 0}, {0, 1, -1, 0}, {0, 1, 0, 0}, 
			{0, 1, 1, 0}, {0, 1, 2, 0}, {0, 1, 3, 0}, {0, 1, 4, 0}, {0, 1, 6, 0}, {0, 1, 7, 0}, {0, 2, -6, 0}, {0, 2, -5, 0}, {0, 2, -3, 0}, {0, 2, -2, 0}, 
			{0, 2, -1, 0}, {0, 2, 0, 0}, {0, 2, 1, 0}, {0, 2, 2, 0}, {0, 2, 3, 0}, {0, 2, 4, 0}, {0, 2, 6, 0}, {0, 2, 7, 0}, {0, 3, -6, 0}, {0, 3, -5, 0}, 
			{0, 3, -3, 0}, {0, 3, -2, 0}, {0, 3, -1, 0}, {0, 3, 0, 0}, {0, 3, 1, 0}, {0, 3, 2, 0}, {0, 3, 3, 0}, {0, 3, 4, 0}, {0, 3, 6, 0}, {0, 3, 7, 0}, 
			{0, 4, -6, 0}, {0, 4, -5, 0}, {0, 4, -2, 0}, {0, 4, -1, 0}, {0, 4, 0, 0}, {0, 4, 1, 0}, {0, 4, 2, 0}, {0, 4, 3, 0}, {0, 4, 6, 0}, {0, 4, 7, 0}, 
			{0, 6, -6, 0}, {0, 6, -5, 0}, {0, 6, -3, 0}, {0, 6, -2, 0}, {0, 6, -1, 0}, {0, 6, 0, 0}, {0, 6, 1, 0}, {0, 6, 2, 0}, {0, 6, 3, 0}, {0, 6, 4, 0}, 
			{0, 6, 6, 0}, {0, 6, 7, 0}, {1, 0, -6, 0}, {1, 0, -5, 0}, {1, 0, -3, 0}, {1, 0, -2, 0}, {1, 0, -1, 0}, {1, 0, 0, 0}, {1, 0, 1, 0}, {1, 0, 2, 0}, 
			{1, 0, 3, 0}, {1, 0, 4, 0}, {1, 0, 6, 0}, {1, 0, 7, 0}, {1, 1, -6, 0}, {1, 1, -5, 0}, {1, 1, -3, 0}, {1, 1, -2, 0}, {1, 1, -1, 0}, {1, 1, 0, 0}, 
			{1, 1, 1, 0}, {1, 1, 2, 0}, {1, 1, 3, 0}, {1, 1, 4, 0}, {1, 1, 6, 0}, {1, 1, 7, 0}, {1, 2, -6, 0}, {1, 2, -5, 0}, {1, 2, -3, 0}, {1, 2, -2, 0}, 
			{1, 2, -1, 0}, {1, 2, 0, 0}, {1, 2, 1, 0}, {1, 2, 2, 0}, {1, 2, 3, 0}, {1, 2, 4, 0}, {1, 2, 6, 0}, {1, 2, 7, 0}, {1, 3, -6, 0}, {1, 3, -5, 0}, 
			{1, 3, -3, 0}, {1, 3, -2, 0}, {1, 3, -1, 0}, {1, 3, 0, 0}, {1, 3, 1, 0}, {1, 3, 2, 0}, {1, 3, 3, 0}, {1, 3, 4, 0}, {1, 3, 6, 0}, {1, 3, 7, 0}, 
			{1, 4, -6, 0}, {1, 4, -5, 0}, {1, 4, -3, 0}, {1, 4, -2, 0}, {1, 4, -1, 0}, {1, 4, 0, 0}, {1, 4, 1, 0}, {1, 4, 2, 0}, {1, 4, 3, 0}, {1, 4, 4, 0}, 
			{1, 4, 6, 0}, {1, 4, 7, 0}, {1, 6, -6, 0}, {1, 6, -5, 0}, {1, 6, -3, 0}, {1, 6, -2, 0}, {1, 6, -1, 0}, {1, 6, 0, 0}, {1, 6, 1, 0}, {1, 6, 2, 0}, 
			{1, 6, 3, 0}, {1, 6, 4, 0}, {1, 6, 6, 0}, {1, 6, 7, 0}, {2, 0, -6, 0}, {2, 0, -5, 0}, {2, 0, -3, 0}, {2, 0, -2, 0}, {2, 0, -1, 0}, {2, 0, 0, 0}, 
			{2, 0, 1, 0}, {2, 0, 2, 0}, {2, 0, 3, 0}, {2, 0, 4, 0}, {2, 0, 6, 0}, {2, 0, 7, 0}, {2, 1, -6, 0}, {2, 1, -5, 0}, {2, 1, -3, 0}, {2, 1, -2, 0}, 
			{2, 1, -1, 0}, {2, 1, 0, 0}, {2, 1, 1, 0}, {2, 1, 2, 0}, {2, 1, 3, 0}, {2, 1, 4, 0}, {2, 1, 6, 0}, {2, 1, 7, 0}, {2, 2, -6, 0}, {2, 2, -5, 0}, 
			{2, 2, -3, 0}, {2, 2, -2, 0}, {2, 2, -1, 0}, {2, 2, 0, 0}, {2, 2, 1, 0}, {2, 2, 2, 0}, {2, 2, 3, 0}, {2, 2, 4, 0}, {2, 2, 6, 0}, {2, 2, 7, 0}, 
			{2, 3, -6, 0}, {2, 3, -5, 0}, {2, 3, -3, 0}, {2, 3, -2, 0}, {2, 3, -1, 0}, {2, 3, 0, 0}, {2, 3, 1, 0}, {2, 3, 2, 0}, {2, 3, 3, 0}, {2, 3, 4, 0}, 
			{2, 3, 6, 0}, {2, 3, 7, 0}, {2, 4, -6, 0}, {2, 4, -5, 0}, {2, 4, -3, 0}, {2, 4, -2, 0}, {2, 4, -1, 0}, {2, 4, 0, 0}, {2, 4, 1, 0}, {2, 4, 2, 0}, 
			{2, 4, 3, 0}, {2, 4, 4, 0}, {2, 4, 6, 0}, {2, 4, 7, 0}, {2, 6, -6, 0}, {2, 6, -5, 0}, {2, 6, -4, 0}, {2, 6, -3, 0}, {2, 6, -2, 0}, {2, 6, -1, 0}, 
			{2, 6, 0, 0}, {2, 6, 1, 0}, {2, 6, 2, 0}, {2, 6, 3, 0}, {2, 6, 4, 0}, {2, 6, 5, 0}, {2, 6, 6, 0}, {2, 6, 7, 0}, {3, 0, -6, 0}, {3, 0, -5, 0}, 
			{3, 0, -3, 0}, {3, 0, -2, 0}, {3, 0, -1, 0}, {3, 0, 0, 0}, {3, 0, 1, 0}, {3, 0, 2, 0}, {3, 0, 3, 0}, {3, 0, 4, 0}, {3, 0, 6, 0}, {3, 0, 7, 0}, 
			{3, 1, -6, 0}, {3, 1, -5, 0}, {3, 1, -3, 0}, {3, 1, -2, 0}, {3, 1, -1, 0}, {3, 1, 0, 0}, {3, 1, 1, 0}, {3, 1, 2, 0}, {3, 1, 3, 0}, {3, 1, 4, 0}, 
			{3, 1, 6, 0}, {3, 1, 7, 0}, {3, 2, -6, 0}, {3, 2, -5, 0}, {3, 2, -3, 0}, {3, 2, -2, 0}, {3, 2, -1, 0}, {3, 2, 0, 0}, {3, 2, 1, 0}, {3, 2, 2, 0}, 
			{3, 2, 3, 0}, {3, 2, 4, 0}, {3, 2, 6, 0}, {3, 2, 7, 0}, {3, 3, -6, 0}, {3, 3, -5, 0}, {3, 3, -3, 0}, {3, 3, -2, 0}, {3, 3, -1, 0}, {3, 3, 0, 0}, 
			{3, 3, 1, 0}, {3, 3, 2, 0}, {3, 3, 3, 0}, {3, 3, 4, 0}, {3, 3, 6, 0}, {3, 3, 7, 0}, {3, 4, -6, 0}, {3, 4, -5, 0}, {3, 4, -3, 0}, {3, 4, -2, 0}, 
			{3, 4, -1, 0}, {3, 4, 0, 0}, {3, 4, 1, 0}, {3, 4, 2, 0}, {3, 4, 3, 0}, {3, 4, 4, 0}, {3, 4, 6, 0}, {3, 4, 7, 0}, {3, 6, -6, 0}, {3, 6, -5, 0}, 
			{3, 6, -4, 0}, {3, 6, -3, 0}, {3, 6, -2, 0}, {3, 6, -1, 0}, {3, 6, 0, 0}, {3, 6, 1, 0}, {3, 6, 2, 0}, {3, 6, 3, 0}, {3, 6, 4, 0}, {3, 6, 5, 0}, 
			{3, 6, 6, 0}, {3, 6, 7, 0}, {4, 0, -6, 0}, {4, 0, -5, 0}, {4, 0, -3, 0}, {4, 0, -2, 0}, {4, 0, -1, 0}, {4, 0, 0, 0}, {4, 0, 1, 0}, {4, 0, 2, 0}, 
			{4, 0, 3, 0}, {4, 0, 4, 0}, {4, 0, 6, 0}, {4, 0, 7, 0}, {4, 1, -6, 0}, {4, 1, -5, 0}, {4, 1, -3, 0}, {4, 1, -2, 0}, {4, 1, -1, 0}, {4, 1, 0, 0}, 
			{4, 1, 1, 0}, {4, 1, 2, 0}, {4, 1, 3, 0}, {4, 1, 4, 0}, {4, 1, 6, 0}, {4, 1, 7, 0}, {4, 2, -6, 0}, {4, 2, -5, 0}, {4, 2, -3, 0}, {4, 2, -2, 0}, 
			{4, 2, -1, 0}, {4, 2, 0, 0}, {4, 2, 1, 0}, {4, 2, 2, 0}, {4, 2, 3, 0}, {4, 2, 4, 0}, {4, 2, 6, 0}, {4, 2, 7, 0}, {4, 3, -6, 0}, {4, 3, -5, 0}, 
			{4, 3, -3, 0}, {4, 3, -2, 0}, {4, 3, -1, 0}, {4, 3, 0, 0}, {4, 3, 1, 0}, {4, 3, 2, 0}, {4, 3, 3, 0}, {4, 3, 4, 0}, {4, 3, 6, 0}, {4, 3, 7, 0}, 
			{4, 4, -6, 0}, {4, 4, -5, 0}, {4, 4, -3, 0}, {4, 4, -2, 0}, {4, 4, -1, 0}, {4, 4, 0, 0}, {4, 4, 1, 0}, {4, 4, 2, 0}, {4, 4, 3, 0}, {4, 4, 4, 0}, 
			{4, 4, 6, 0}, {4, 4, 7, 0}, {4, 6, -6, 0}, {4, 6, -5, 0}, {4, 6, -3, 0}, {4, 6, -2, 0}, {4, 6, -1, 0}, {4, 6, 0, 0}, {4, 6, 1, 0}, {4, 6, 2, 0}, 
			{4, 6, 3, 0}, {4, 6, 4, 0}, {4, 6, 6, 0}, {4, 6, 7, 0}, {5, 0, -6, 0}, {5, 0, -5, 0}, {5, 0, -3, 0}, {5, 0, -2, 0}, {5, 0, -1, 0}, {5, 0, 0, 0}, 
			{5, 0, 1, 0}, {5, 0, 2, 0}, {5, 0, 4, 0}, {5, 0, 6, 0}, {5, 0, 7, 0}, {5, 1, -6, 0}, {5, 1, -5, 0}, {5, 1, -3, 0}, {5, 1, -2, 0}, {5, 1, -1, 0}, 
			{5, 1, 0, 0}, {5, 1, 1, 0}, {5, 1, 2, 0}, {5, 1, 3, 0}, {5, 1, 4, 0}, {5, 1, 6, 0}, {5, 1, 7, 0}, {5, 2, -6, 0}, {5, 2, -5, 0}, {5, 2, -3, 0}, 
			{5, 2, -2, 0}, {5, 2, -1, 0}, {5, 2, 0, 0}, {5, 2, 1, 0}, {5, 2, 2, 0}, {5, 2, 3, 0}, {5, 2, 4, 0}, {5, 2, 6, 0}, {5, 2, 7, 0}, {5, 3, -6, 0}, 
			{5, 3, -5, 0}, {5, 3, -3, 0}, {5, 3, -2, 0}, {5, 3, -1, 0}, {5, 3, 0, 0}, {5, 3, 1, 0}, {5, 3, 2, 0}, {5, 3, 3, 0}, {5, 3, 4, 0}, {5, 3, 6, 0}, 
			{5, 3, 7, 0}, {5, 4, -6, 0}, {5, 4, -5, 0}, {5, 4, -2, 0}, {5, 4, -1, 0}, {5, 4, 0, 0}, {5, 4, 1, 0}, {5, 4, 2, 0}, {5, 4, 3, 0}, {5, 4, 6, 0}, 
			{5, 4, 7, 0}, {5, 6, -6, 0}, {5, 6, -5, 0}, {5, 6, -3, 0}, {5, 6, -2, 0}, {5, 6, -1, 0}, {5, 6, 0, 0}, {5, 6, 1, 0}, {5, 6, 2, 0}, {5, 6, 4, 0}, 
			{5, 6, 6, 0}, {5, 6, 7, 0}, {6, 0, -6, 0}, {6, 0, -5, 0}, {6, 0, -2, 0}, {6, 0, -1, 0}, {6, 0, 0, 0}, {6, 0, 1, 0}, {6, 0, 2, 0}, {6, 0, 6, 0}, 
			{6, 0, 7, 0}, {6, 1, -6, 0}, {6, 1, -5, 0}, {6, 1, -2, 0}, {6, 1, -1, 0}, {6, 1, 0, 0}, {6, 1, 1, 0}, {6, 1, 3, 0}, {6, 1, 6, 0}, {6, 1, 7, 0}, 
			{6, 2, -6, 0}, {6, 2, -5, 0}, {6, 2, -2, 0}, {6, 2, -1, 0}, {6, 2, 0, 0}, {6, 2, 2, 0}, {6, 2, 3, 0}, {6, 2, 6, 0}, {6, 2, 7, 0}, {6, 3, -6, 0}, 
			{6, 3, -5, 0}, {6, 3, -2, 0}, {6, 3, -1, 0}, {6, 3, 1, 0}, {6, 3, 2, 0}, {6, 3, 3, 0}, {6, 3, 6, 0}, {6, 3, 7, 0}, {6, 4, -6, 0}, {6, 4, -5, 0}, 
			{6, 4, 0, 0}, {6, 4, 1, 0}, {6, 4, 2, 0}, {6, 4, 6, 0}, {6, 4, 7, 0}, {6, 5, -1, 0}, {6, 5, 0, 0}, {6, 5, 1, 0}, {6, 6, -6, 0}, {6, 6, -5, 0}, 
			{6, 6, -2, 0}, {6, 6, -1, 0}, {6, 6, 0, 0}, {6, 6, 1, 0}, {6, 6, 2, 0}, {6, 6, 6, 0}, {6, 6, 7, 0}, {7, 0, -6, 0}, {7, 0, -5, 0}, {7, 0, -4, 0}, 
			{7, 0, 5, 0}, {7, 0, 6, 0}, {7, 0, 7, 0}, {7, 1, -6, 0}, {7, 1, -5, 0}, {7, 1, -4, 0}, {7, 1, 5, 0}, {7, 1, 6, 0}, {7, 1, 7, 0}, {7, 2, -6, 0}, 
			{7, 2, -5, 0}, {7, 2, -4, 0}, {7, 2, 5, 0}, {7, 2, 6, 0}, {7, 2, 7, 0}, {7, 3, -6, 0}, {7, 3, -5, 0}, {7, 3, -4, 0}, {7, 3, 5, 0}, {7, 3, 6, 0}, 
			{7, 3, 7, 0}, {7, 4, -6, 0}, {7, 4, -5, 0}, {7, 4, -4, 0}, {7, 4, 5, 0}, {7, 4, 6, 0}, {7, 4, 7, 0}, {7, 5, -6, 0}, {7, 5, 7, 0}, {7, 6, -6, 0}, 
			{7, 6, -5, 0}, {7, 6, -4, 0}, {7, 6, 5, 0}, {7, 6, 6, 0}, {7, 6, 7, 0}, {8, 0, -6, 0}, {8, 0, -5, 0}, {8, 0, -4, 0}, {8, 0, -3, 0}, {8, 0, -2, 0}, 
			{8, 0, -1, 0}, {8, 0, 0, 0}, {8, 0, 1, 0}, {8, 0, 2, 0}, {8, 0, 3, 0}, {8, 0, 4, 0}, {8, 0, 5, 0}, {8, 0, 6, 0}, {8, 0, 7, 0}, {8, 1, -6, 0}, 
			{8, 1, -5, 0}, {8, 1, -4, 0}, {8, 1, -3, 0}, {8, 1, -2, 0}, {8, 1, -1, 0}, {8, 1, 0, 0}, {8, 1, 1, 0}, {8, 1, 2, 0}, {8, 1, 3, 0}, {8, 1, 4, 0}, 
			{8, 1, 5, 0}, {8, 1, 6, 0}, {8, 1, 7, 0}, {8, 2, -6, 0}, {8, 2, -5, 0}, {8, 2, -4, 0}, {8, 2, -3, 0}, {8, 2, -2, 0}, {8, 2, -1, 0}, {8, 2, 0, 0}, 
			{8, 2, 1, 0}, {8, 2, 2, 0}, {8, 2, 3, 0}, {8, 2, 4, 0}, {8, 2, 5, 0}, {8, 2, 6, 0}, {8, 2, 7, 0}, {8, 3, -6, 0}, {8, 3, -5, 0}, {8, 3, -4, 0}, 
			{8, 3, -3, 0}, {8, 3, -2, 0}, {8, 3, -1, 0}, {8, 3, 0, 0}, {8, 3, 1, 0}, {8, 3, 2, 0}, {8, 3, 3, 0}, {8, 3, 4, 0}, {8, 3, 5, 0}, {8, 3, 6, 0}, 
			{8, 3, 7, 0}, {8, 4, -6, 0}, {8, 4, -5, 0}, {8, 4, -4, 0}, {8, 4, -3, 0}, {8, 4, -2, 0}, {8, 4, -1, 0}, {8, 4, 0, 0}, {8, 4, 1, 0}, {8, 4, 2, 0}, 
			{8, 4, 3, 0}, {8, 4, 4, 0}, {8, 4, 5, 0}, {8, 4, 6, 0}, {8, 4, 7, 0}, {8, 5, -6, 0}, {8, 5, -5, 0}, {8, 5, 6, 0}, {8, 5, 7, 0}, {8, 6, -6, 0}, 
			{8, 6, -5, 0}, {8, 6, -4, 0}, {8, 6, -3, 0}, {8, 6, -2, 0}, {8, 6, -1, 0}, {8, 6, 0, 0}, {8, 6, 1, 0}, {8, 6, 2, 0}, {8, 6, 3, 0}, {8, 6, 4, 0}, 
			{8, 6, 5, 0}, {8, 6, 6, 0}, {8, 6, 7, 0}, {9, 0, -6, 0}, {9, 0, -5, 0}, {9, 0, -4, 0}, {9, 0, -3, 0}, {9, 0, -2, 0}, {9, 0, -1, 0}, {9, 0, 0, 0}, 
			{9, 0, 1, 0}, {9, 0, 2, 0}, {9, 0, 3, 0}, {9, 0, 4, 0}, {9, 0, 5, 0}, {9, 0, 6, 0}, {9, 0, 7, 0}, {9, 1, -6, 0}, {9, 1, -5, 0}, {9, 1, -4, 0}, 
			{9, 1, -3, 0}, {9, 1, -2, 0}, {9, 1, -1, 0}, {9, 1, 0, 0}, {9, 1, 1, 0}, {9, 1, 2, 0}, {9, 1, 3, 0}, {9, 1, 4, 0}, {9, 1, 5, 0}, {9, 1, 6, 0}, 
			{9, 1, 7, 0}, {9, 2, -6, 0}, {9, 2, -5, 0}, {9, 2, -4, 0}, {9, 2, -3, 0}, {9, 2, -2, 0}, {9, 2, -1, 0}, {9, 2, 0, 0}, {9, 2, 1, 0}, {9, 2, 2, 0}, 
			{9, 2, 3, 0}, {9, 2, 4, 0}, {9, 2, 5, 0}, {9, 2, 6, 0}, {9, 2, 7, 0}, {9, 3, -6, 0}, {9, 3, -5, 0}, {9, 3, -4, 0}, {9, 3, -3, 0}, {9, 3, -2, 0}, 
			{9, 3, -1, 0}, {9, 3, 0, 0}, {9, 3, 1, 0}, {9, 3, 2, 0}, {9, 3, 3, 0}, {9, 3, 4, 0}, {9, 3, 5, 0}, {9, 3, 6, 0}, {9, 3, 7, 0}, {9, 4, -6, 0}, 
			{9, 4, -5, 0}, {9, 4, -4, 0}, {9, 4, -3, 0}, {9, 4, -2, 0}, {9, 4, -1, 0}, {9, 4, 0, 0}, {9, 4, 1, 0}, {9, 4, 2, 0}, {9, 4, 3, 0}, {9, 4, 4, 0}, 
			{9, 4, 5, 0}, {9, 4, 6, 0}, {9, 4, 7, 0}, {9, 5, -6, 0}, {9, 5, -5, 0}, {9, 5, -4, 0}, {9, 5, 5, 0}, {9, 5, 6, 0}, {9, 5, 7, 0}, {9, 6, -6, 0}, 
			{9, 6, -5, 0}, {9, 6, -4, 0}, {9, 6, -3, 0}, {9, 6, -2, 0}, {9, 6, -1, 0}, {9, 6, 0, 0}, {9, 6, 1, 0}, {9, 6, 2, 0}, {9, 6, 3, 0}, {9, 6, 4, 0}, 
			{9, 6, 5, 0}, {9, 6, 6, 0}, {9, 6, 7, 0}, {-6, 0, 3, 0} 

			}; 
			 
	static int[][] damp_log = { 

			{-8, 0, -2, 0}, {-8, 0, 3, 0}, {-8, 1, -2, 0}, {-8, 1, 3, 0}, {-8, 2, -2, 0}, {-8, 2, 3, 0}, {-8, 3, -2, 0}, {-8, 3, 3, 0}, {-8, 4, -2, 0}, {-8, 4, 3, 0}, 
			{-8, 5, -2, 0}, {-8, 5, 3, 0}, {-8, 6, -2, 0}, {-8, 6, 3, 0}, {-2, 0, -3, 0}, {-2, 0, 4, 0}, {-2, 1, -3, 0}, {-2, 1, 4, 0}, {-2, 2, -3, 0}, {-2, 2, 4, 0}, 
			{-2, 5, -3, 0}, {-2, 5, 4, 0}, {-2, 6, -3, 0}, {-2, 6, 4, 0}, {-2, 7, -3, 0}, {-2, 7, 4, 0}, {-2, 8, -3, 0}, {-2, 8, 4, 0}, {-2, 11, -3, 0}, {-2, 11, 4, 0}, 
			{-2, 12, -3, 0}, {-2, 12, 4, 0}, {-2, 13, -3, 0}, {-2, 13, 4, 0}, {-2, 14, -3, 0}, {-2, 14, 4, 0}, {-2, 25, -2, 0}, {-2, 25, 3, 0}, {-2, 26, -2, 0}, {-2, 26, 3, 0}, 
			{-2, 27, -2, 0}, {-2, 27, 3, 0}, {-1, 0, -4, 0}, {-1, 0, 5, 0}, {-1, 1, -4, 0}, {-1, 1, 5, 0}, {-1, 2, -4, 0}, {-1, 2, 5, 0}, {-1, 5, -4, 0}, {-1, 5, 5, 0}, 
			{-1, 6, -4, 0}, {-1, 6, 5, 0}, {-1, 7, -4, 0}, {-1, 7, 5, 0}, {-1, 8, -4, 0}, {-1, 8, 5, 0}, {-1, 11, -4, 0}, {-1, 11, 5, 0}, {-1, 12, -4, 0}, {-1, 12, 5, 0}, 
			{-1, 13, -4, 0}, {-1, 13, 5, 0}, {-1, 14, -4, 0}, {-1, 14, 5, 0}, {-1, 25, -3, 0}, {-1, 25, 4, 0}, {-1, 26, -3, 0}, {-1, 26, 4, 0}, {-1, 27, -3, 0}, {-1, 27, 4, 0}, 
			{0, 25, -4, 0}, {0, 25, 5, 0}, {0, 26, -4, 0}, {0, 26, 5, 0}, {0, 27, -4, 0}, {0, 27, 5, 0}, {5, 25, -4, 0}, {5, 25, 5, 0}, {5, 26, -4, 0}, {5, 26, 5, 0}, 
			{5, 27, -4, 0}, {5, 27, 5, 0}, {6, 0, -4, 0}, {6, 0, 5, 0}, {6, 1, -4, 0}, {6, 1, 5, 0}, {6, 2, -4, 0}, {6, 2, 5, 0}, {6, 5, -4, 0}, {6, 5, 5, 0}, 
			{6, 6, -4, 0}, {6, 6, 5, 0}, {6, 7, -4, 0}, {6, 7, 5, 0}, {6, 8, -4, 0}, {6, 8, 5, 0}, {6, 11, -4, 0}, {6, 11, 5, 0}, {6, 12, -4, 0}, {6, 12, 5, 0}, 
			{6, 13, -4, 0}, {6, 13, 5, 0}, {6, 14, -4, 0}, {6, 14, 5, 0}, {6, 25, -3, 0}, {6, 25, 4, 0}, {6, 26, -3, 0}, {6, 26, 4, 0}, {6, 27, -3, 0}, {6, 27, 4, 0}, 
			{7, 0, -3, 0}, {7, 0, 4, 0}, {7, 1, -3, 0}, {7, 1, 4, 0}, {7, 2, -3, 0}, {7, 2, 4, 0}, {7, 5, -3, 0}, {7, 5, 4, 0}, {7, 6, -3, 0}, {7, 6, 4, 0}, 
			{7, 7, -3, 0}, {7, 7, 4, 0}, {7, 8, -3, 0}, {7, 8, 4, 0}, {7, 11, -3, 0}, {7, 11, 4, 0}, {7, 12, -3, 0}, {7, 12, 4, 0}, {7, 13, -3, 0}, {7, 13, 4, 0}, 
			{7, 14, -3, 0}, {7, 14, 4, 0}, {7, 25, -2, 0}, {7, 25, 3, 0}, {7, 26, -2, 0}, {7, 26, 3, 0}, {7, 27, -2, 0}, {7, 27, 3, 0}, 

			}; 
			 
	static int[][] damp_wood = { 

			{-8, 0, -1, 0}, {-8, 0, 0, 0}, {-8, 0, 1, 0}, {-8, 0, 2, 0}, {-8, 1, -1, 0}, {-8, 1, 0, 0}, {-8, 1, 1, 0}, {-8, 1, 2, 0}, {-8, 2, -1, 0}, {-8, 2, 0, 0}, 
			{-8, 2, 1, 0}, {-8, 2, 2, 0}, {-8, 3, -1, 0}, {-8, 3, 0, 0}, {-8, 3, 1, 0}, {-8, 3, 2, 0}, {-8, 4, -1, 0}, {-8, 4, 0, 0}, {-8, 4, 1, 0}, {-8, 4, 2, 0}, 
			{-8, 5, -1, 0}, {-8, 5, 0, 0}, {-8, 5, 1, 0}, {-8, 5, 2, 0}, {-8, 6, -1, 0}, {-8, 6, 0, 0}, {-8, 6, 1, 0}, {-8, 6, 2, 0}, {-8, 7, -1, 0}, {-8, 7, 2, 0}, 
			{-8, 8, 0, 0}, {-8, 8, 1, 0}, {-7, 0, -2, 0}, {-7, 0, 3, 0}, {-7, 1, -2, 0}, {-7, 1, -1, 0}, {-7, 1, 3, 0}, {-7, 2, -2, 0}, {-7, 2, 3, 0}, {-7, 3, -2, 0}, 
			{-7, 3, 2, 0}, {-7, 3, 3, 0}, {-7, 4, -2, 0}, {-7, 4, 3, 0}, {-7, 5, -2, 0}, {-7, 5, 3, 0}, {-7, 6, -2, 0}, {-7, 6, 3, 0}, {-6, 0, -2, 0}, {-6, 1, -2, 0}, 
			{-6, 2, -2, 0}, {-6, 2, 3, 0}, {-6, 3, -2, 0}, {-6, 3, 3, 0}, {-6, 4, -2, 0}, {-6, 4, 3, 0}, {-6, 5, -2, 0}, {-6, 5, -1, 0}, {-6, 5, 0, 0}, {-6, 5, 1, 0}, 
			{-6, 5, 3, 0}, {-6, 6, -2, 0}, {-6, 6, 3, 0}, {-5, 0, -2, 0}, {-5, 0, 3, 0}, {-5, 1, -2, 0}, {-5, 1, 3, 0}, {-5, 2, -2, 0}, {-5, 2, 3, 0}, {-5, 3, -2, 0}, 
			{-5, 3, 3, 0}, {-5, 4, -2, 0}, {-5, 4, 3, 0}, {-5, 5, -2, 0}, {-5, 5, -1, 0}, {-5, 5, 0, 0}, {-5, 5, 1, 0}, {-5, 5, 3, 0}, {-5, 6, -2, 0}, {-5, 6, 3, 0}, 
			{-4, 0, -2, 0}, {-4, 0, 3, 0}, {-4, 1, -2, 0}, {-4, 1, 3, 0}, {-4, 2, -2, 0}, {-4, 2, 3, 0}, {-4, 3, -2, 0}, {-4, 3, 3, 0}, {-4, 4, -2, 0}, {-4, 4, 3, 0}, 
			{-4, 5, -2, 0}, {-4, 5, -1, 0}, {-4, 5, 0, 0}, {-4, 5, 1, 0}, {-4, 5, 2, 0}, {-4, 5, 3, 0}, {-4, 6, -2, 0}, {-4, 6, 3, 0}, {-3, 0, -2, 0}, {-3, 0, 3, 0}, 
			{-3, 1, -2, 0}, {-3, 1, 3, 0}, {-3, 2, -2, 0}, {-3, 2, 3, 0}, {-3, 3, -2, 0}, {-3, 3, 3, 0}, {-3, 4, -2, 0}, {-3, 4, 3, 0}, {-3, 5, -2, 0}, {-3, 5, -1, 0}, 
			{-3, 5, 0, 0}, {-3, 5, 1, 0}, {-3, 5, 2, 0}, {-3, 5, 3, 0}, {-3, 6, -2, 0}, {-3, 6, 3, 0}, 

			}; 
			 
	static int[][] damp_canopy = { 

			{-8, 5, -4, 2}, {-8, 5, 5, 4}, {-8, 6, -3, 2}, {-8, 6, 4, 4}, {-8, 9, 0, 8}, {-8, 9, 1, 8}, {-7, 5, -4, 2}, {-7, 5, 5, 4}, {-7, 6, -3, 2}, {-7, 6, 4, 4}, 
			{-7, 7, -2, 2}, {-7, 7, 3, 4}, {-7, 8, -1, 2}, {-7, 8, 2, 4}, {-7, 9, 0, 8}, {-7, 9, 1, 8}, {-6, 5, -4, 2}, {-6, 5, 5, 4}, {-6, 6, -3, 2}, {-6, 6, 4, 4}, 
			{-6, 7, -2, 2}, {-6, 7, 3, 4}, {-6, 8, -1, 2}, {-6, 8, 2, 4}, {-6, 9, 0, 8}, {-6, 9, 1, 8}, {-5, 5, -4, 2}, {-5, 5, 5, 4}, {-5, 6, -3, 2}, {-5, 6, 4, 4}, 
			{-5, 7, -2, 2}, {-5, 7, 3, 4}, {-5, 8, -1, 2}, {-5, 8, 2, 4}, {-5, 9, 0, 8}, {-5, 9, 1, 8}, {-4, 5, -4, 2}, {-4, 5, 5, 4}, {-4, 6, -3, 2}, {-4, 6, 4, 4}, 
			{-4, 7, -2, 2}, {-4, 7, 3, 4}, {-4, 8, -1, 2}, {-4, 8, 2, 4}, {-4, 9, 0, 8}, {-4, 9, 1, 8}, {-3, 5, -4, 2}, {-3, 5, 5, 4}, {-3, 6, -3, 2}, {-3, 6, 4, 4}, 
			{-3, 7, -2, 2}, {-3, 7, 3, 4}, {-3, 8, -1, 2}, {-3, 8, 2, 4}, {-3, 9, 0, 8}, {-3, 9, 1, 8}, {-3, 27, -4, 2}, {-3, 27, -3, 1}, {-3, 27, -2, 1}, {-3, 27, -1, 1}, 
			{-3, 27, 0, 1}, {-3, 27, 1, 1}, {-3, 27, 2, 1}, {-3, 27, 3, 1}, {-3, 27, 4, 1}, {-3, 27, 5, 1}, {-2, 27, -5, 1}, {-2, 27, -4, 1}, {-2, 27, 5, 4}, {-2, 27, 6, 1}, 
			{-2, 28, -3, 1}, {-2, 28, -2, 1}, {-2, 28, -1, 1}, {-2, 28, 0, 1}, {-2, 28, 1, 1}, {-2, 28, 2, 1}, {-2, 28, 3, 1}, {-2, 28, 4, 4}, {-1, 27, -5, 2}, {-1, 27, 6, 4}, 
			{-1, 28, -4, 1}, {-1, 28, -3, 2}, {-1, 28, 4, 1}, {-1, 28, 5, 4}, {-1, 29, -2, 2}, {-1, 29, -1, 1}, {-1, 29, 0, 1}, {-1, 29, 1, 1}, {-1, 29, 2, 1}, {-1, 29, 3, 1}, 
			{0, 27, -5, 2}, {0, 27, 6, 4}, {0, 28, -4, 2}, {0, 28, 5, 4}, {0, 29, -3, 2}, {0, 29, -2, 1}, {0, 29, 3, 4}, {0, 29, 4, 1}, {0, 30, -1, 2}, {0, 30, 0, 1}, 
			{0, 30, 1, 1}, {0, 30, 2, 1}, {1, 27, -5, 2}, {1, 27, 6, 4}, {1, 28, -4, 2}, {1, 28, 5, 4}, {1, 29, -3, 2}, {1, 29, 4, 4}, {1, 30, -2, 2}, {1, 30, -1, 1}, 
			{1, 30, 2, 4}, {1, 30, 3, 1}, {1, 31, 0, 8}, {1, 31, 1, 8}, {2, 27, -5, 2}, {2, 27, 6, 4}, {2, 28, -4, 2}, {2, 28, 5, 4}, {2, 29, -3, 2}, {2, 29, 4, 4}, 
			{2, 30, -2, 2}, {2, 30, 3, 4}, {2, 31, -1, 8}, {2, 31, 0, 8}, {2, 31, 1, 8}, {2, 31, 2, 8}, {3, 27, -5, 2}, {3, 27, 6, 4}, {3, 28, -4, 2}, {3, 28, 5, 4}, 
			{3, 29, -3, 2}, {3, 29, 4, 4}, {3, 30, -2, 2}, {3, 30, 3, 4}, {3, 31, -1, 8}, {3, 31, 0, 8}, {3, 31, 1, 8}, {3, 31, 2, 8}, {4, 27, -5, 2}, {4, 27, 6, 4}, 
			{4, 28, -4, 2}, {4, 28, 5, 4}, {4, 29, -3, 2}, {4, 29, 4, 4}, {4, 30, -2, 3}, {4, 30, -1, 2}, {4, 30, 2, 3}, {4, 30, 3, 4}, {4, 31, 0, 8}, {4, 31, 1, 8}, 
			{5, 27, -5, 2}, {5, 27, 6, 4}, {5, 28, -4, 2}, {5, 28, 5, 4}, {5, 29, -3, 3}, {5, 29, -2, 2}, {5, 29, 3, 3}, {5, 29, 4, 4}, {5, 30, -1, 3}, {5, 30, 0, 3}, 
			{5, 30, 1, 3}, {5, 30, 2, 4}, {6, 27, -5, 2}, {6, 27, 6, 4}, {6, 28, -4, 2}, {6, 28, -3, 3}, {6, 28, 4, 4}, {6, 28, 5, 3}, {6, 29, -2, 3}, {6, 29, -1, 3}, 
			{6, 29, 0, 3}, {6, 29, 1, 3}, {6, 29, 2, 3}, {6, 29, 3, 4}, {7, 27, -5, 3}, {7, 27, -4, 2}, {7, 27, 5, 3}, {7, 27, 6, 3}, {7, 28, -3, 2}, {7, 28, -2, 3}, 
			{7, 28, -1, 3}, {7, 28, 0, 3}, {7, 28, 1, 3}, {7, 28, 2, 3}, {7, 28, 3, 3}, {7, 28, 4, 3}, {8, 27, -4, 3}, {8, 27, -3, 3}, {8, 27, -2, 3}, {8, 27, -1, 3}, 
			{8, 27, 0, 3}, {8, 27, 1, 3}, {8, 27, 2, 3}, {8, 27, 3, 3}, {8, 27, 4, 3}, {8, 27, 5, 4}, 

			}; 
			 
	static int[][] damp_canopy_wood = { 

			{-8, 7, -2, 2}, {-8, 7, 3, 4}, {-8, 8, -1, 2}, {-8, 8, 2, 4}, 

			}; 
			 
	static int[][] barrel = { 

			{-7, 0, 0, 0}, {-7, 0, 1, 0}, 

			}; 
			 
	static int[][] damp_wood_stairs = { 

			{-7, 2, 0, 2}, {-7, 3, 1, 2}, {-6, 1, -1, 1}, {-6, 4, 2, 0}, {-5, 0, -1, 1}, {-5, 5, 2, 0}, 

			}; 
			
	static int[][] bricks_blue = { 

			{-2, -1, -2, 0}, {-2, -1, -1, 0}, {-2, -1, 0, 0}, {-2, -1, 1, 0}, {-2, -1, 2, 0}, {-2, -1, 3, 0}, {-1, -1, -3, 0}, {-1, -1, -2, 0}, {-1, -1, -1, 0}, {-1, -1, 0, 0}, 
			{-1, -1, 1, 0}, {-1, -1, 2, 0}, {-1, -1, 3, 0}, {-1, -1, 4, 0}, {-1, 5, -2, 0}, {-1, 5, -1, 0}, {-1, 5, 0, 0}, {-1, 5, 1, 0}, {-1, 5, 2, 0}, {-1, 5, 3, 0}, 
			{-1, 11, -2, 0}, {-1, 11, -1, 0}, {-1, 11, 0, 0}, {-1, 11, 1, 0}, {-1, 11, 2, 0}, {-1, 11, 3, 0}, {-1, 17, -2, 0}, {-1, 17, -1, 0}, {-1, 17, 0, 0}, {-1, 17, 1, 0}, 
			{-1, 17, 2, 0}, {-1, 17, 3, 0}, {-1, 23, -2, 0}, {-1, 23, -1, 0}, {-1, 23, 0, 0}, {-1, 23, 1, 0}, {-1, 23, 2, 0}, {-1, 23, 3, 0}, {0, -1, -4, 0}, {0, -1, -3, 0}, 
			{0, -1, -2, 0}, {0, -1, -1, 0}, {0, -1, 0, 0}, {0, -1, 1, 0}, {0, -1, 2, 0}, {0, -1, 3, 0}, {0, -1, 4, 0}, {0, -1, 5, 0}, {0, 5, -3, 0}, {0, 5, -2, 0}, 
			{0, 5, -1, 0}, {0, 5, 0, 0}, {0, 5, 1, 0}, {0, 5, 2, 0}, {0, 5, 3, 0}, {0, 5, 4, 0}, {0, 11, -3, 0}, {0, 11, -2, 0}, {0, 11, -1, 0}, {0, 11, 0, 0}, 
			{0, 11, 1, 0}, {0, 11, 2, 0}, {0, 11, 3, 0}, {0, 11, 4, 0}, {0, 17, -3, 0}, {0, 17, -2, 0}, {0, 17, -1, 0}, {0, 17, 0, 0}, {0, 17, 1, 0}, {0, 17, 2, 0}, 
			{0, 17, 3, 0}, {0, 17, 4, 0}, {0, 23, -3, 0}, {0, 23, -2, 0}, {0, 23, -1, 0}, {0, 23, 0, 0}, {0, 23, 1, 0}, {0, 23, 2, 0}, {0, 23, 3, 0}, {0, 23, 4, 0}, 
			{1, -1, -4, 0}, {1, -1, -3, 0}, {1, -1, -2, 0}, {1, -1, -1, 0}, {1, -1, 0, 0}, {1, -1, 1, 0}, {1, -1, 2, 0}, {1, -1, 3, 0}, {1, -1, 4, 0}, {1, -1, 5, 0}, 
			{1, 5, -3, 0}, {1, 5, -2, 0}, {1, 5, -1, 0}, {1, 5, 0, 0}, {1, 5, 1, 0}, {1, 5, 2, 0}, {1, 5, 3, 0}, {1, 5, 4, 0}, {1, 11, -3, 0}, {1, 11, -2, 0}, 
			{1, 11, -1, 0}, {1, 11, 0, 0}, {1, 11, 1, 0}, {1, 11, 2, 0}, {1, 11, 3, 0}, {1, 11, 4, 0}, {1, 17, -3, 0}, {1, 17, -2, 0}, {1, 17, -1, 0}, {1, 17, 0, 0}, 
			{1, 17, 1, 0}, {1, 17, 2, 0}, {1, 17, 3, 0}, {1, 17, 4, 0}, {1, 23, -3, 0}, {1, 23, -2, 0}, {1, 23, -1, 0}, {1, 23, 0, 0}, {1, 23, 1, 0}, {1, 23, 2, 0}, 
			{1, 23, 3, 0}, {1, 23, 4, 0}, {2, -1, -4, 0}, {2, -1, -3, 0}, {2, -1, -2, 0}, {2, -1, -1, 0}, {2, -1, 2, 0}, {2, -1, 3, 0}, {2, -1, 4, 0}, {2, -1, 5, 0}, 
			{2, 5, -3, 0}, {2, 5, -2, 0}, {2, 5, -1, 0}, {2, 5, 2, 0}, {2, 5, 3, 0}, {2, 5, 4, 0}, {2, 11, -3, 0}, {2, 11, -2, 0}, {2, 11, -1, 0}, {2, 11, 2, 0}, 
			{2, 11, 3, 0}, {2, 11, 4, 0}, {2, 17, -3, 0}, {2, 17, -2, 0}, {2, 17, -1, 0}, {2, 17, 2, 0}, {2, 17, 3, 0}, {2, 17, 4, 0}, {2, 23, -3, 0}, {2, 23, -2, 0}, 
			{2, 23, -1, 0}, {2, 23, 0, 0}, {2, 23, 1, 0}, {2, 23, 2, 0}, {2, 23, 3, 0}, {2, 23, 4, 0}, {3, -1, -4, 0}, {3, -1, -3, 0}, {3, -1, -2, 0}, {3, -1, -1, 0}, 
			{3, -1, 2, 0}, {3, -1, 3, 0}, {3, -1, 4, 0}, {3, -1, 5, 0}, {3, 5, -3, 0}, {3, 5, -2, 0}, {3, 5, -1, 0}, {3, 5, 2, 0}, {3, 5, 3, 0}, {3, 5, 4, 0}, 
			{3, 11, -3, 0}, {3, 11, -2, 0}, {3, 11, -1, 0}, {3, 11, 2, 0}, {3, 11, 3, 0}, {3, 11, 4, 0}, {3, 17, -3, 0}, {3, 17, -2, 0}, {3, 17, -1, 0}, {3, 17, 2, 0}, 
			{3, 17, 3, 0}, {3, 17, 4, 0}, {3, 23, -3, 0}, {3, 23, -2, 0}, {3, 23, -1, 0}, {3, 23, 0, 0}, {3, 23, 1, 0}, {3, 23, 2, 0}, {3, 23, 3, 0}, {3, 23, 4, 0}, 
			{4, -1, -4, 0}, {4, -1, -3, 0}, {4, -1, -2, 0}, {4, -1, -1, 0}, {4, -1, 0, 0}, {4, -1, 1, 0}, {4, -1, 2, 0}, {4, -1, 3, 0}, {4, -1, 4, 0}, {4, -1, 5, 0}, 
			{4, 5, -3, 0}, {4, 5, -2, 0}, {4, 5, -1, 0}, {4, 5, 0, 0}, {4, 5, 1, 0}, {4, 5, 2, 0}, {4, 5, 3, 0}, {4, 5, 4, 0}, {4, 11, -3, 0}, {4, 11, -2, 0}, 
			{4, 11, -1, 0}, {4, 11, 0, 0}, {4, 11, 1, 0}, {4, 11, 2, 0}, {4, 11, 3, 0}, {4, 11, 4, 0}, {4, 17, -3, 0}, {4, 17, -2, 0}, {4, 17, -1, 0}, {4, 17, 0, 0}, 
			{4, 17, 1, 0}, {4, 17, 2, 0}, {4, 17, 3, 0}, {4, 17, 4, 0}, {4, 23, -3, 0}, {4, 23, -2, 0}, {4, 23, -1, 0}, {4, 23, 0, 0}, {4, 23, 1, 0}, {4, 23, 2, 0}, 
			{4, 23, 3, 0}, {4, 23, 4, 0}, {5, -1, -4, 0}, {5, -1, -3, 0}, {5, -1, -2, 0}, {5, -1, -1, 0}, {5, -1, 0, 0}, {5, -1, 1, 0}, {5, -1, 2, 0}, {5, -1, 3, 0}, 
			{5, -1, 4, 0}, {5, -1, 5, 0}, {5, 5, -3, 0}, {5, 5, -2, 0}, {5, 5, -1, 0}, {5, 5, 0, 0}, {5, 5, 1, 0}, {5, 5, 2, 0}, {5, 5, 3, 0}, {5, 5, 4, 0}, 
			{5, 11, -3, 0}, {5, 11, -2, 0}, {5, 11, -1, 0}, {5, 11, 0, 0}, {5, 11, 1, 0}, {5, 11, 2, 0}, {5, 11, 3, 0}, {5, 11, 4, 0}, {5, 17, -3, 0}, {5, 17, -2, 0}, 
			{5, 17, -1, 0}, {5, 17, 0, 0}, {5, 17, 1, 0}, {5, 17, 2, 0}, {5, 17, 3, 0}, {5, 17, 4, 0}, {5, 23, -3, 0}, {5, 23, -2, 0}, {5, 23, -1, 0}, {5, 23, 0, 0}, 
			{5, 23, 1, 0}, {5, 23, 2, 0}, {5, 23, 3, 0}, {5, 23, 4, 0}, {6, -1, -3, 0}, {6, -1, -2, 0}, {6, -1, -1, 0}, {6, -1, 0, 0}, {6, -1, 1, 0}, {6, -1, 2, 0}, 
			{6, -1, 3, 0}, {6, -1, 4, 0}, {6, 5, 2, 0}, {6, 5, 3, 0}, {6, 11, 2, 0}, {6, 11, 3, 0}, {6, 17, 2, 0}, {6, 17, 3, 0}, {6, 23, 2, 0}, {6, 23, 3, 0}, 
			{7, -1, -2, 0}, {7, -1, -1, 0}, {7, -1, 0, 0}, {7, -1, 1, 0}, {7, -1, 2, 0}, {7, -1, 3, 0}, 

			}; 
			 
	static int[][] worn_bricks = { 

			{-2, 0, -2, 0}, {-2, 0, -1, 0}, {-2, 0, 2, 0}, {-2, 0, 3, 0}, {-2, 1, -2, 0}, {-2, 1, -1, 0}, {-2, 1, 2, 0}, {-2, 1, 3, 0}, {-2, 2, -2, 0}, {-2, 2, -1, 0}, 
			{-2, 2, 0, 0}, {-2, 2, 1, 0}, {-2, 2, 2, 0}, {-2, 2, 3, 0}, {-2, 3, -1, 0}, {-2, 3, 0, 0}, {-2, 3, 1, 0}, {-2, 3, 2, 0}, {-2, 4, -1, 0}, {-2, 4, 0, 0}, 
			{-2, 4, 1, 0}, {-2, 4, 2, 0}, {-2, 5, -2, 0}, {-2, 5, -1, 0}, {-2, 5, 0, 0}, {-2, 5, 1, 0}, {-2, 5, 2, 0}, {-2, 5, 3, 0}, {-2, 6, -2, 0}, {-2, 6, 3, 0}, 
			{-2, 7, -2, 0}, {-2, 7, 3, 0}, {-2, 8, -2, 0}, {-2, 8, -1, 0}, {-2, 8, 0, 0}, {-2, 8, 1, 0}, {-2, 8, 2, 0}, {-2, 8, 3, 0}, {-2, 11, -2, 0}, {-2, 11, -1, 0}, 
			{-2, 11, 0, 0}, {-2, 11, 1, 0}, {-2, 11, 2, 0}, {-2, 11, 3, 0}, {-2, 12, -2, 0}, {-2, 12, -1, 0}, {-2, 12, 0, 0}, {-2, 12, 1, 0}, {-2, 12, 2, 0}, {-2, 12, 3, 0}, 
			{-2, 13, -2, 0}, {-2, 13, -1, 0}, {-2, 13, 0, 0}, {-2, 13, 1, 0}, {-2, 13, 2, 0}, {-2, 13, 3, 0}, {-2, 14, -2, 0}, {-2, 14, -1, 0}, {-2, 14, 0, 0}, {-2, 14, 1, 0}, 
			{-2, 14, 2, 0}, {-2, 14, 3, 0}, {-2, 17, -2, 0}, {-2, 17, -1, 0}, {-2, 17, 0, 0}, {-2, 17, 1, 0}, {-2, 17, 2, 0}, {-2, 17, 3, 0}, {-2, 18, -2, 0}, {-2, 18, -1, 0}, 
			{-2, 18, 0, 0}, {-2, 18, 1, 0}, {-2, 18, 2, 0}, {-2, 18, 3, 0}, {-2, 19, -2, 0}, {-2, 19, -1, 0}, {-2, 19, 0, 0}, {-2, 19, 1, 0}, {-2, 19, 2, 0}, {-2, 19, 3, 0}, 
			{-2, 20, -2, 0}, {-2, 20, -1, 0}, {-2, 20, 0, 0}, {-2, 20, 1, 0}, {-2, 20, 2, 0}, {-2, 20, 3, 0}, {-2, 23, -2, 0}, {-2, 23, -1, 0}, {-2, 23, 0, 0}, {-2, 23, 1, 0}, 
			{-2, 23, 2, 0}, {-2, 23, 3, 0}, {-2, 24, -2, 0}, {-2, 24, -1, 0}, {-2, 24, 0, 0}, {-2, 24, 1, 0}, {-2, 24, 2, 0}, {-2, 24, 3, 0}, {-1, 0, -3, 0}, {-1, 0, 4, 0}, 
			{-1, 1, -3, 0}, {-1, 1, 4, 0}, {-1, 2, -3, 0}, {-1, 2, 4, 0}, {-1, 5, -3, 0}, {-1, 5, 4, 0}, {-1, 6, -3, 0}, {-1, 6, 4, 0}, {-1, 7, -3, 0}, {-1, 7, 4, 0}, 
			{-1, 8, -3, 0}, {-1, 8, 4, 0}, {-1, 11, -3, 0}, {-1, 11, 4, 0}, {-1, 12, -3, 0}, {-1, 12, 4, 0}, {-1, 13, -3, 0}, {-1, 13, 4, 0}, {-1, 14, -3, 0}, {-1, 14, 4, 0}, 
			{-1, 17, -3, 0}, {-1, 17, 4, 0}, {-1, 18, -3, 0}, {-1, 18, 4, 0}, {-1, 19, -3, 0}, {-1, 19, 4, 0}, {-1, 20, -3, 0}, {-1, 20, 4, 0}, {-1, 23, -3, 0}, {-1, 23, 4, 0}, 
			{-1, 24, -3, 0}, {-1, 24, 4, 0}, {0, 0, -4, 0}, {0, 0, 5, 0}, {0, 1, -4, 0}, {0, 1, 5, 0}, {0, 2, -4, 0}, {0, 2, 5, 0}, {0, 5, -4, 0}, {0, 5, 5, 0}, 
			{0, 6, -4, 0}, {0, 6, 5, 0}, {0, 7, -4, 0}, {0, 7, 5, 0}, {0, 8, -4, 0}, {0, 8, 5, 0}, {0, 11, -4, 0}, {0, 11, 5, 0}, {0, 12, -4, 0}, {0, 12, 5, 0}, 
			{0, 13, -4, 0}, {0, 13, 5, 0}, {0, 14, -4, 0}, {0, 14, 5, 0}, {0, 17, -4, 0}, {0, 17, 5, 0}, {0, 18, -4, 0}, {0, 18, 5, 0}, {0, 19, -4, 0}, {0, 19, 5, 0}, 
			{0, 20, -4, 0}, {0, 20, 5, 0}, {0, 23, -4, 0}, {0, 23, 5, 0}, {0, 24, -4, 0}, {0, 24, 5, 0}, {1, 0, -4, 0}, {1, 0, 5, 0}, {1, 1, -4, 0}, {1, 1, 5, 0}, 
			{1, 2, -4, 0}, {1, 2, 5, 0}, {1, 5, -4, 0}, {1, 5, 5, 0}, {1, 6, -4, 0}, {1, 6, 5, 0}, {1, 7, -4, 0}, {1, 7, 5, 0}, {1, 8, -4, 0}, {1, 8, 5, 0}, 
			{1, 11, -4, 0}, {1, 11, 5, 0}, {1, 12, -4, 0}, {1, 12, 5, 0}, {1, 13, -4, 0}, {1, 13, 5, 0}, {1, 14, -4, 0}, {1, 14, 5, 0}, {1, 17, -4, 0}, {1, 17, 5, 0}, 
			{1, 18, -4, 0}, {1, 18, 5, 0}, {1, 19, -4, 0}, {1, 19, 5, 0}, {1, 20, -4, 0}, {1, 20, 5, 0}, {1, 23, -4, 0}, {1, 23, 5, 0}, {1, 24, -4, 0}, {1, 24, 5, 0}, 
			{2, 0, -4, 0}, {2, 0, 5, 0}, {2, 1, -4, 0}, {2, 1, 5, 0}, {2, 2, -4, 0}, {2, 2, 5, 0}, {2, 5, -4, 0}, {2, 5, 5, 0}, {2, 8, -4, 0}, {2, 8, 5, 0}, 
			{2, 11, -4, 0}, {2, 11, 5, 0}, {2, 14, -4, 0}, {2, 14, 5, 0}, {2, 17, -4, 0}, {2, 17, 5, 0}, {2, 20, -4, 0}, {2, 20, 5, 0}, {2, 23, -4, 0}, {2, 23, 5, 0}, 
			{2, 24, -4, 0}, {2, 24, 5, 0}, {3, 0, -4, 0}, {3, 0, 5, 0}, {3, 1, -4, 0}, {3, 1, 5, 0}, {3, 2, -4, 0}, {3, 2, 5, 0}, {3, 5, -4, 0}, {3, 5, 5, 0}, 
			{3, 8, -4, 0}, {3, 8, 5, 0}, {3, 11, -4, 0}, {3, 11, 5, 0}, {3, 14, -4, 0}, {3, 14, 5, 0}, {3, 17, -4, 0}, {3, 17, 5, 0}, {3, 20, -4, 0}, {3, 20, 5, 0}, 
			{3, 23, -4, 0}, {3, 23, 5, 0}, {3, 24, -4, 0}, {3, 24, 5, 0}, {4, 0, -4, 0}, {4, 0, 5, 0}, {4, 1, -4, 0}, {4, 1, 5, 0}, {4, 2, -4, 0}, {4, 2, 5, 0}, 
			{4, 5, -4, 0}, {4, 5, 5, 0}, {4, 6, -4, 0}, {4, 6, 5, 0}, {4, 7, -4, 0}, {4, 7, 5, 0}, {4, 8, -4, 0}, {4, 8, 5, 0}, {4, 11, -4, 0}, {4, 11, 5, 0}, 
			{4, 12, -4, 0}, {4, 12, 5, 0}, {4, 13, -4, 0}, {4, 13, 5, 0}, {4, 14, -4, 0}, {4, 14, 5, 0}, {4, 17, -4, 0}, {4, 17, 5, 0}, {4, 18, -4, 0}, {4, 18, 5, 0}, 
			{4, 19, -4, 0}, {4, 19, 5, 0}, {4, 20, -4, 0}, {4, 20, 5, 0}, {4, 23, -4, 0}, {4, 23, 5, 0}, {4, 24, -4, 0}, {4, 24, 5, 0}, {5, 0, -4, 0}, {5, 0, 5, 0}, 
			{5, 1, -4, 0}, {5, 1, 5, 0}, {5, 2, -4, 0}, {5, 2, 5, 0}, {5, 5, -4, 0}, {5, 5, 5, 0}, {5, 6, -4, 0}, {5, 6, 5, 0}, {5, 7, -4, 0}, {5, 7, 5, 0}, 
			{5, 8, -4, 0}, {5, 8, 5, 0}, {5, 11, -4, 0}, {5, 11, 5, 0}, {5, 12, -4, 0}, {5, 12, 5, 0}, {5, 13, -4, 0}, {5, 13, 5, 0}, {5, 14, -4, 0}, {5, 14, 5, 0}, 
			{5, 17, -4, 0}, {5, 17, 5, 0}, {5, 18, -4, 0}, {5, 18, 5, 0}, {5, 19, -4, 0}, {5, 19, 5, 0}, {5, 20, -4, 0}, {5, 20, 5, 0}, {5, 23, -4, 0}, {5, 23, 5, 0}, 
			{5, 24, -4, 0}, {5, 24, 5, 0}, {6, 0, -3, 0}, {6, 0, 3, 0}, {6, 0, 4, 0}, {6, 1, -3, 0}, {6, 1, 4, 0}, {6, 2, -3, 0}, {6, 2, 4, 0}, {6, 5, -3, 0}, 
			{6, 5, 4, 0}, {6, 6, -3, 0}, {6, 6, 3, 0}, {6, 6, 4, 0}, {6, 7, -3, 0}, {6, 7, 4, 0}, {6, 8, -3, 0}, {6, 8, 4, 0}, {6, 11, -3, 0}, {6, 11, 4, 0}, 
			{6, 12, -3, 0}, {6, 12, 3, 0}, {6, 12, 4, 0}, {6, 13, -3, 0}, {6, 13, 4, 0}, {6, 14, -3, 0}, {6, 14, 4, 0}, {6, 17, -3, 0}, {6, 17, 4, 0}, {6, 18, -3, 0}, 
			{6, 18, 3, 0}, {6, 18, 4, 0}, {6, 19, -3, 0}, {6, 19, 4, 0}, {6, 20, -3, 0}, {6, 20, 4, 0}, {6, 23, -3, 0}, {6, 23, 4, 0}, {6, 24, -3, 0}, {6, 24, 4, 0}, 
			{7, 0, -2, 0}, {7, 0, -1, 0}, {7, 0, 0, 0}, {7, 0, 1, 0}, {7, 0, 2, 0}, {7, 0, 3, 0}, {7, 1, -2, 0}, {7, 1, -1, 0}, {7, 1, 0, 0}, {7, 1, 1, 0}, 
			{7, 1, 2, 0}, {7, 1, 3, 0}, {7, 2, -2, 0}, {7, 2, -1, 0}, {7, 2, 0, 0}, {7, 2, 1, 0}, {7, 2, 2, 0}, {7, 2, 3, 0}, {7, 5, -2, 0}, {7, 5, -1, 0}, 
			{7, 5, 0, 0}, {7, 5, 1, 0}, {7, 5, 2, 0}, {7, 5, 3, 0}, {7, 6, -2, 0}, {7, 6, -1, 0}, {7, 6, 0, 0}, {7, 6, 1, 0}, {7, 6, 2, 0}, {7, 6, 3, 0}, 
			{7, 7, -2, 0}, {7, 7, -1, 0}, {7, 7, 0, 0}, {7, 7, 1, 0}, {7, 7, 2, 0}, {7, 7, 3, 0}, {7, 8, -2, 0}, {7, 8, -1, 0}, {7, 8, 0, 0}, {7, 8, 1, 0}, 
			{7, 8, 2, 0}, {7, 8, 3, 0}, {7, 11, -2, 0}, {7, 11, -1, 0}, {7, 11, 0, 0}, {7, 11, 1, 0}, {7, 11, 2, 0}, {7, 11, 3, 0}, {7, 12, -2, 0}, {7, 12, -1, 0}, 
			{7, 12, 0, 0}, {7, 12, 1, 0}, {7, 12, 2, 0}, {7, 12, 3, 0}, {7, 13, -2, 0}, {7, 13, -1, 0}, {7, 13, 0, 0}, {7, 13, 1, 0}, {7, 13, 2, 0}, {7, 13, 3, 0}, 
			{7, 14, -2, 0}, {7, 14, -1, 0}, {7, 14, 0, 0}, {7, 14, 1, 0}, {7, 14, 2, 0}, {7, 14, 3, 0}, {7, 17, -2, 0}, {7, 17, -1, 0}, {7, 17, 0, 0}, {7, 17, 1, 0}, 
			{7, 17, 2, 0}, {7, 17, 3, 0}, {7, 18, -2, 0}, {7, 18, -1, 0}, {7, 18, 0, 0}, {7, 18, 1, 0}, {7, 18, 2, 0}, {7, 18, 3, 0}, {7, 19, -2, 0}, {7, 19, -1, 0}, 
			{7, 19, 0, 0}, {7, 19, 1, 0}, {7, 19, 2, 0}, {7, 19, 3, 0}, {7, 20, -2, 0}, {7, 20, -1, 0}, {7, 20, 0, 0}, {7, 20, 1, 0}, {7, 20, 2, 0}, {7, 20, 3, 0}, 
			{7, 23, -2, 0}, {7, 23, -1, 0}, {7, 23, 0, 0}, {7, 23, 1, 0}, {7, 23, 2, 0}, {7, 23, 3, 0}, {7, 24, -2, 0}, {7, 24, -1, 0}, {7, 24, 0, 0}, {7, 24, 1, 0}, 
			{7, 24, 2, 0}, {7, 24, 3, 0}, 

			}; 
			 
	static int[][] worn_brick_stairs = { 

			{-2, 3, -3, 0}, {-2, 3, -2, 2}, {-2, 3, 3, 0}, {-2, 3, 4, 3}, {-2, 4, -3, 4}, {-2, 4, -2, 6}, {-2, 4, 3, 4}, {-2, 4, 4, 7}, {-2, 9, -3, 0}, {-2, 9, -2, 0}, 
			{-2, 9, -1, 0}, {-2, 9, 0, 0}, {-2, 9, 1, 0}, {-2, 9, 2, 0}, {-2, 9, 3, 0}, {-2, 9, 4, 3}, {-2, 10, -3, 4}, {-2, 10, -2, 4}, {-2, 10, -1, 4}, {-2, 10, 0, 4}, 
			{-2, 10, 1, 4}, {-2, 10, 2, 4}, {-2, 10, 3, 4}, {-2, 10, 4, 7}, {-2, 15, -3, 0}, {-2, 15, -2, 0}, {-2, 15, -1, 0}, {-2, 15, 0, 0}, {-2, 15, 1, 0}, {-2, 15, 2, 0}, 
			{-2, 15, 3, 0}, {-2, 15, 4, 3}, {-2, 16, -3, 4}, {-2, 16, -2, 4}, {-2, 16, -1, 4}, {-2, 16, 0, 4}, {-2, 16, 1, 4}, {-2, 16, 2, 4}, {-2, 16, 3, 4}, {-2, 16, 4, 7}, 
			{-2, 21, -3, 2}, {-2, 21, -2, 0}, {-2, 21, -1, 0}, {-2, 21, 0, 0}, {-2, 21, 1, 0}, {-2, 21, 2, 0}, {-2, 21, 3, 0}, {-2, 21, 4, 0}, {-2, 22, -3, 6}, {-2, 22, -2, 4}, 
			{-2, 22, -1, 4}, {-2, 22, 0, 4}, {-2, 22, 1, 4}, {-2, 22, 2, 4}, {-2, 22, 3, 4}, {-2, 22, 4, 7}, {-1, 3, -4, 0}, {-1, 3, -3, 2}, {-1, 3, 4, 3}, {-1, 3, 5, 0}, 
			{-1, 4, -4, 4}, {-1, 4, -3, 6}, {-1, 4, 4, 7}, {-1, 4, 5, 4}, {-1, 9, -4, 0}, {-1, 9, -3, 2}, {-1, 9, 4, 3}, {-1, 9, 5, 0}, {-1, 10, -4, 4}, {-1, 10, -3, 6}, 
			{-1, 10, 4, 7}, {-1, 10, 5, 4}, {-1, 15, -4, 0}, {-1, 15, -3, 2}, {-1, 15, 4, 0}, {-1, 15, 5, 3}, {-1, 16, -4, 4}, {-1, 16, -3, 6}, {-1, 16, 4, 7}, {-1, 16, 5, 4}, 
			{-1, 21, -4, 0}, {-1, 21, -3, 0}, {-1, 21, 4, 3}, {-1, 21, 5, 0}, {-1, 22, -4, 4}, {-1, 22, -3, 4}, {-1, 22, 4, 7}, {-1, 22, 5, 4}, {0, 3, -4, 2}, {0, 3, 5, 3}, 
			{0, 4, -4, 6}, {0, 4, 5, 7}, {0, 9, -4, 2}, {0, 9, 5, 3}, {0, 10, -4, 6}, {0, 10, 5, 7}, {0, 15, -4, 2}, {0, 15, 5, 3}, {0, 16, -4, 6}, {0, 16, 5, 7}, 
			{0, 21, -4, 2}, {0, 21, 5, 3}, {0, 22, -4, 6}, {0, 22, 5, 7}, {1, 3, -4, 2}, {1, 3, 5, 3}, {1, 4, -4, 6}, {1, 4, 5, 7}, {1, 9, -4, 2}, {1, 9, 5, 3}, 
			{1, 10, -4, 6}, {1, 10, 5, 7}, {1, 15, -4, 2}, {1, 15, 5, 3}, {1, 16, -4, 6}, {1, 16, 5, 7}, {1, 21, -4, 2}, {1, 21, 5, 3}, {1, 22, -4, 6}, {1, 22, 5, 7}, 
			{2, 3, -4, 2}, {2, 3, 5, 3}, {2, 4, -4, 6}, {2, 4, 5, 7}, {2, 9, -4, 2}, {2, 9, 5, 3}, {2, 10, -4, 6}, {2, 10, 5, 7}, {2, 15, -4, 2}, {2, 15, 5, 3}, 
			{2, 16, -4, 6}, {2, 16, 5, 7}, {2, 21, -4, 2}, {2, 21, 5, 3}, {2, 22, -4, 6}, {2, 22, 5, 7}, {3, 3, -4, 2}, {3, 3, 5, 3}, {3, 4, -4, 6}, {3, 4, 5, 7}, 
			{3, 9, -4, 2}, {3, 9, 5, 3}, {3, 10, -4, 6}, {3, 10, 5, 7}, {3, 15, -4, 2}, {3, 15, 5, 3}, {3, 16, -4, 6}, {3, 16, 5, 7}, {3, 21, -4, 2}, {3, 21, 5, 3}, 
			{3, 22, -4, 6}, {3, 22, 5, 7}, {4, 3, -4, 2}, {4, 3, 5, 3}, {4, 4, -4, 6}, {4, 4, 5, 7}, {4, 9, -4, 2}, {4, 9, 5, 3}, {4, 10, -4, 6}, {4, 10, 5, 7}, 
			{4, 15, -4, 2}, {4, 15, 5, 3}, {4, 16, -4, 6}, {4, 16, 5, 7}, {4, 21, -4, 2}, {4, 21, 5, 3}, {4, 22, -4, 6}, {4, 22, 5, 7}, {5, 0, 3, 0}, {5, 3, -4, 2}, 
			{5, 3, 5, 3}, {5, 4, -4, 6}, {5, 4, 5, 7}, {5, 6, 3, 0}, {5, 9, -4, 2}, {5, 9, 5, 3}, {5, 10, -4, 6}, {5, 10, 5, 7}, {5, 12, 3, 0}, {5, 15, -4, 2}, 
			{5, 15, 5, 3}, {5, 16, -4, 6}, {5, 16, 5, 7}, {5, 18, 3, 0}, {5, 21, -4, 2}, {5, 21, 5, 3}, {5, 22, -4, 6}, {5, 22, 5, 7}, {6, 1, 2, 3}, {6, 2, 1, 3}, 
			{6, 3, -4, 1}, {6, 3, -3, 1}, {6, 3, 0, 3}, {6, 3, 4, 3}, {6, 3, 5, 1}, {6, 4, -4, 5}, {6, 4, -3, 6}, {6, 4, -1, 3}, {6, 4, 4, 7}, {6, 4, 5, 5}, 
			{6, 5, -2, 3}, {6, 7, 2, 3}, {6, 8, 1, 3}, {6, 9, -4, 1}, {6, 9, -3, 2}, {6, 9, 0, 3}, {6, 9, 4, 1}, {6, 9, 5, 1}, {6, 10, -4, 5}, {6, 10, -3, 6}, 
			{6, 10, -1, 3}, {6, 10, 4, 5}, {6, 10, 5, 5}, {6, 11, -2, 3}, {6, 13, 2, 3}, {6, 14, 1, 3}, {6, 15, -4, 2}, {6, 15, -3, 1}, {6, 15, 0, 3}, {6, 15, 4, 3}, 
			{6, 15, 5, 1}, {6, 16, -4, 5}, {6, 16, -3, 6}, {6, 16, -1, 3}, {6, 16, 4, 5}, {6, 16, 5, 7}, {6, 17, -2, 3}, {6, 19, 2, 3}, {6, 20, 1, 3}, {6, 21, -4, 1}, 
			{6, 21, -3, 2}, {6, 21, 0, 3}, {6, 21, 4, 1}, {6, 21, 5, 3}, {6, 22, -4, 5}, {6, 22, -3, 6}, {6, 22, -1, 3}, {6, 22, 4, 5}, {6, 22, 5, 5}, {6, 23, -2, 3}, 
			{7, 3, -3, 2}, {7, 3, -2, 1}, {7, 3, -1, 1}, {7, 3, 0, 1}, {7, 3, 1, 1}, {7, 3, 2, 1}, {7, 3, 3, 1}, {7, 3, 4, 1}, {7, 4, -3, 5}, {7, 4, -2, 5}, 
			{7, 4, -1, 5}, {7, 4, 0, 5}, {7, 4, 1, 5}, {7, 4, 2, 5}, {7, 4, 3, 5}, {7, 4, 4, 5}, {7, 9, -3, 2}, {7, 9, -2, 1}, {7, 9, -1, 1}, {7, 9, 0, 1}, 
			{7, 9, 1, 1}, {7, 9, 2, 1}, {7, 9, 3, 1}, {7, 9, 4, 3}, {7, 10, -3, 6}, {7, 10, -2, 5}, {7, 10, -1, 5}, {7, 10, 0, 5}, {7, 10, 1, 5}, {7, 10, 2, 5}, 
			{7, 10, 3, 5}, {7, 10, 4, 7}, {7, 15, -3, 2}, {7, 15, -2, 1}, {7, 15, -1, 1}, {7, 15, 0, 1}, {7, 15, 1, 1}, {7, 15, 2, 1}, {7, 15, 3, 1}, {7, 15, 4, 1}, 
			{7, 16, -3, 5}, {7, 16, -2, 5}, {7, 16, -1, 5}, {7, 16, 0, 5}, {7, 16, 1, 5}, {7, 16, 2, 5}, {7, 16, 3, 5}, {7, 16, 4, 7}, {7, 21, -3, 2}, {7, 21, -2, 1}, 
			{7, 21, -1, 1}, {7, 21, 0, 1}, {7, 21, 1, 1}, {7, 21, 2, 1}, {7, 21, 3, 1}, {7, 21, 4, 3}, {7, 22, -3, 6}, {7, 22, -2, 5}, {7, 22, -1, 5}, {7, 22, 0, 5}, 
			{7, 22, 1, 5}, {7, 22, 2, 5}, {7, 22, 3, 5}, {7, 22, 4, 7}, 

			}; 
			 
	static int[][] damp_wood_fence = { 

			{-2, 17, -3, 0}, {-2, 17, 4, 0}, {-2, 18, -3, 0}, {-2, 18, 4, 0}, {-2, 19, -3, 0}, {-2, 19, 4, 0}, {-2, 25, -1, 0}, {-2, 25, 0, 0}, {-2, 25, 1, 0}, {-2, 25, 2, 0}, 
			{-1, 17, -4, 0}, {-1, 17, 5, 0}, {-1, 18, -4, 0}, {-1, 18, 5, 0}, {-1, 19, -4, 0}, {-1, 19, 5, 0}, {1, 25, -4, 0}, {1, 25, 5, 0}, {2, 6, -4, 0}, {2, 6, 5, 0}, 
			{2, 12, -4, 0}, {2, 12, 5, 0}, {2, 18, -4, 0}, {2, 18, 5, 0}, {2, 25, -4, 0}, {2, 25, 5, 0}, {3, 6, -4, 0}, {3, 6, 5, 0}, {3, 12, -4, 0}, {3, 12, 5, 0}, 
			{3, 18, -4, 0}, {3, 18, 5, 0}, {3, 25, -4, 0}, {3, 25, 5, 0}, {4, 25, -4, 0}, {4, 25, 5, 0}, {6, 17, -4, 0}, {6, 17, 5, 0}, {6, 18, -4, 0}, {6, 18, 5, 0}, 
			{6, 19, -4, 0}, {6, 19, 5, 0}, {7, 17, -3, 0}, {7, 17, 4, 0}, {7, 18, -3, 0}, {7, 18, 4, 0}, {7, 19, -3, 0}, {7, 19, 4, 0}, {7, 25, -1, 0}, {7, 25, 0, 0}, 
			{7, 25, 1, 0}, {7, 25, 2, 0}, 
			
			}; 
			 
	static int[][] seaLantern = { 

			{-1, 4, -2, 0}, {-1, 4, 3, 0}, {-1, 10, -2, 0}, {-1, 10, 3, 0}, {-1, 16, -2, 0}, {-1, 16, 3, 0}, {-1, 22, -2, 0}, {-1, 22, 3, 0}, {0, 4, -3, 0}, 
			{0, 4, 4, 0}, {0, 10, -3, 0}, {0, 10, 4, 0}, {0, 16, -3, 0}, {0, 16, 4, 0}, {0, 22, -3, 0}, {0, 22, 4, 0}, {2, -1, 0, 0}, {2, -1, 1, 0}, {2, 5, 0, 0}, 
			{2, 5, 1, 0}, {2, 11, 0, 0}, {2, 11, 1, 0}, {2, 17, 0, 0}, {2, 17, 1, 0}, {3, -1, 0, 0}, {3, -1, 1, 0}, {3, 5, 0, 0}, {3, 5, 1, 0}, {3, 11, 0, 0}, 
			{3, 11, 1, 0}, {3, 17, 0, 0}, {3, 17, 1, 0}, {5, 4, -3, 0}, {5, 4, 4, 0}, {5, 10, -3, 0}, {5, 10, 4, 0}, {5, 16, -3, 0}, {5, 16, 4, 0}, {5, 22, -3, 0}, 
			{5, 22, 4, 0}, {6, 4, -2, 0}, {6, 4, 3, 0}, {6, 10, -2, 0}, {6, 10, 3, 0}, {6, 16, -2, 0}, {6, 16, 3, 0}, {6, 22, -2, 0}, {6, 22, 3, 0},  

			}; 
			 
	static int[][] chest = { 

			{-1, 12, -2, 5}, {-1, 12, -1, 5}, {-1, 12, 2, 5}, {-1, 12, 3, 5}, {-1, 13, -2, 5}, {-1, 13, -1, 5}, {-1, 13, 2, 5}, {-1, 13, 3, 5}, 

			}; 
			 
	static int[][] prismarine = { 

			{-2, 20, -3, 2}, {-2, 20, 4, 2}, {-1, 20, -4, 2}, {-1, 20, 5, 2}, {1, 24, 0, 2}, {1, 24, 1, 2}, {1, 25, 0, 2}, {1, 25, 1, 2}, {2, 24, -1, 2}, {2, 24, 2, 2}, 
			{2, 25, -1, 2}, {2, 25, 2, 2}, {3, 24, -1, 2}, {3, 24, 2, 2}, {3, 25, -1, 2}, {3, 25, 2, 2}, {4, 24, 0, 2}, {4, 24, 1, 2}, {4, 25, 0, 2}, {4, 25, 1, 2}, 
			{6, 20, -4, 2}, {6, 20, 5, 2}, {7, 20, -3, 2}, {7, 20, 4, 2}, 

			}; 
			 
	static int[][] hellrock = { 

			{2, 24, 0, 0}, {2, 24, 1, 0}, {2, 25, 0, 0}, {2, 25, 1, 0}, {3, 24, 0, 0}, {3, 24, 1, 0}, {3, 25, 0, 0}, {3, 25, 1, 0}, 

			};
			 
	static int[][] fire = { 

			{2, 26, 0, 0}, {2, 26, 1, 0}, {3, 26, 0, 0}, {3, 26, 1, 0}, 

			}; 
			
	static int[] doorCoords = {-6, -1, 3};
			
	static int[] doorCoords2 = {-6, -1, 7};
			
	static int[] villagerCoordsES = {4, 24, 4};
	
	static int[] villagerCoordsWN = {4, 24, 3};
	
}
