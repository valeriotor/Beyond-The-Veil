package com.valeriotor.beyondtheveil.world.Structures;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
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

public class HamletLightHouse extends HamletStructure{
	
	public static void registerBlocks(){
		String file;
		try {
			file = Resources.toString(BeyondTheVeil.class.getResource("/assets/beyondtheveil/buildings/lighthouse.json"), Charsets.UTF_8);
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
			bricks_blue = map.get(BlockRegistry.BricksBlue);
			worn_bricks = map.get(BlockRegistry.WornBricks);
			worn_brick_stairs = map.get(BlockRegistry.WornBrickStairs);
			damp_wood_fence = map.get(BlockRegistry.DampWoodFence);
			seaLantern = map.get(Blocks.SEA_LANTERN);
			chest = map.get(Blocks.CHEST);
			prismarine = map.get(Blocks.PRISMARINE);
			hellrock = map.get(Blocks.NETHERRACK);
			fire = map.get(Blocks.FIRE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]));
			for(byte[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]));
			for(byte[] n : this.bricks_blue) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_bricks) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(byte[] n : this.seaLantern) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(byte[] n : this.prismarine) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(byte[] n : this.hellrock) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(byte[] n : this.fire) world.setBlockState(new BlockPos(x+n[0], y+n[1], z+n[2]), Blocks.FIRE.getStateFromMeta(n[3]));
			}else if(facing==EnumFacing.EAST){
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(byte[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(byte[] n : this.bricks_blue) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_bricks) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.WornBrickStairs.FACING, BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.WornBrickStairs.FACING).rotateYCCW().rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(byte[] n : this.seaLantern) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(byte[] n : this.prismarine) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(byte[] n : this.hellrock) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(byte[] n : this.fire) world.setBlockState(new BlockPos(x-n[2], y+n[1], z+n[0]), Blocks.FIRE.getStateFromMeta(n[3]));
			}else if(facing==EnumFacing.SOUTH){
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.bricks_blue) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_bricks) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.WornBrickStairs.FACING, BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.WornBrickStairs.FACING).rotateYCCW().rotateYCCW()));
			for(byte[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(byte[] n : this.seaLantern) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(byte[] n : this.prismarine) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(byte[] n : this.hellrock) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(byte[] n : this.fire) world.setBlockState(new BlockPos(x-n[0], y+n[1], z-n[2]), Blocks.FIRE.getStateFromMeta(n[3]));
			}else if(facing==EnumFacing.WEST){
			for(byte[] n : this.dark_sand) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), state);
			for(byte[] n : this.air) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.AIR.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_log) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampLog.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWood.getStateFromMeta(n[3]));
			for(byte[] n : this.damp_canopy) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopy.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopy.FACING, BlockRegistry.DampCanopy.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopy.FACING).rotateYCCW()));
			for(byte[] n : this.damp_canopy_wood) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampCanopyWood.FACING, BlockRegistry.DampCanopyWood.getStateFromMeta(n[3]).getValue(BlockRegistry.DampCanopyWood.FACING).rotateYCCW()));
			for(byte[] n : this.barrel) this.getRandomBarrel(r, this.getPosFromArray(n, facing));
			for(byte[] n : this.damp_wood_stairs) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.DampWoodStairs.FACING, BlockRegistry.DampWoodStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.DampWoodStairs.FACING).rotateYCCW()));
			for(byte[] n : this.bricks_blue) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.BricksBlue.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_bricks) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.WornBricks.getStateFromMeta(n[3]));
			for(byte[] n : this.worn_brick_stairs) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).withProperty(BlockRegistry.WornBrickStairs.FACING, BlockRegistry.WornBrickStairs.getStateFromMeta(n[3]).getValue(BlockRegistry.WornBrickStairs.FACING).rotateYCCW()));
			for(byte[] n : this.damp_wood_fence) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), BlockRegistry.DampWoodFence.getStateFromMeta(n[3]));
			for(byte[] n : this.seaLantern) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.SEA_LANTERN.getStateFromMeta(n[3]));
			for(byte[] n : this.chest) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.CHEST.getStateFromMeta(n[3]));
			for(byte[] n : this.prismarine) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.PRISMARINE.getStateFromMeta(n[3]));
			for(byte[] n : this.hellrock) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.NETHERRACK.getStateFromMeta(n[3]));
			for(byte[] n : this.fire) world.setBlockState(new BlockPos(x+n[2], y+n[1], z-n[0]), Blocks.FIRE.getStateFromMeta(n[3]));
			}
		
		for(byte[] n : this.chest) {
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
		EnumFacing facing = this.facing;
		BlockPos home = this.getPosFromArray(facing.getHorizontalIndex() == 3 || facing.getHorizontalIndex() == 0 ? villagerCoordsWN : villagerCoordsWN, facing);
		EntityHamletDweller h = new EntityHamletDweller(this.world);
		h.setProfession(EntityHamletDweller.ProfessionsEnum.LHKEEPER);
		h.setHome(home);
		h.setVillageCenter(this.villageCenter);
		h.setPosition(home.getX() + 0.5, home.getY(), home.getZ() + 0.5);
		this.world.spawnEntity(h);
		
	}
	
	
	static byte[][] dark_sand; 
			 
	static byte[][] air; 
			 
	static byte[][] damp_log; 
			 
	static byte[][] damp_wood; 
			 
	static byte[][] damp_canopy; 
			 
	static byte[][] damp_canopy_wood; 
			 
	static byte[][] barrel; 
			 
	static byte[][] damp_wood_stairs; 
			
	static byte[][] bricks_blue; 
			 
	static byte[][] worn_bricks; 
			 
	static byte[][] worn_brick_stairs; 
			 
	static byte[][] damp_wood_fence; 
			 
	static byte[][] seaLantern; 
			 
	static byte[][] chest; 
			 
	static byte[][] prismarine; 
			 
	static byte[][] hellrock;
			 
	static byte[][] fire;
			
	static byte[] doorCoords = {-6, -1, 3};
			
	static byte[] doorCoords2 = {-6, -1, 7};
			
	static byte[] villagerCoordsES = {4, 24, 4};
	
	static byte[] villagerCoordsWN = {4, 24, 3};
	
}
