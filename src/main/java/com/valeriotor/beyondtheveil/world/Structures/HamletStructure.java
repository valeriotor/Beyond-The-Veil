package com.valeriotor.beyondtheveil.world.Structures;

import java.util.Random;

import com.valeriotor.beyondtheveil.blocks.BlockBarrel;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.tileEntities.TileBarrel;
import com.valeriotor.beyondtheveil.tileEntities.TileBarrel.FishType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class HamletStructure{
	protected BlockPos center;
	protected BlockPos villageCenter;
	protected BlockPos doorPos;
	protected World world;
	protected int radius;
	protected int x;
	protected int y;
	protected int z;
	protected int quad;
	protected int generation;
	protected boolean isItOnWater;
	protected boolean bornFromX;
	protected boolean tooMuchStone;
	protected EnumFacing facing;
	protected boolean[] directions = {true, true};
	
	
	public void setCenter(BlockPos pos) {
		this.center = pos;
		this.x = center.getX();
		this.y = center.getY();
		this.z = center.getZ();
	}
	
	
	public void StartStructure(Random r) {
		
	}
	
	
	public void doorRoads() {
		
	}
	
	public boolean isLarge() {
		return false;
	}
	
	protected void roadHelper(int[] doorCoords) {
		int doorX = doorCoords[0];
		int doorZ = doorCoords[2];
		switch(facing.getHorizontalIndex()) {
		case 0:
			doorX = -doorX;
			doorZ = -doorZ;
			break;
		case 1:
			doorX = doorCoords[2];
			doorZ = -doorCoords[0];
			break;
		case 2:
			break;
		case 3:
			doorX = -doorCoords[2];
			doorZ = doorCoords[0];
			break;
		}
		doorPos = center.add(doorX, doorCoords[1], doorZ);
		if(this.isLarge()) makeDoorRoads(12);
		else makeDoorRoads(8);
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public BlockPos getCenter() {
		return this.center;
	}
	
	public void setQuadrant(int q) {
		this.quad = q;
	}
	
	public int getQuadrant() {
		return quad;
	}
	
	public boolean intersects(BlockPos pos) {
		
		if(pos.getX()<x+radius && pos.getX()>x-radius && pos.getZ()<z+radius && pos.getZ()>z-radius) {
			return true;
		}else return false;
	}
	
	public void setDirection(int index, boolean value) {
		this.directions[index] = value;
	}
	
	public boolean[] getDirection() {
		return this.directions;
	}
	
	public void setGeneration(int gen) {
		this.generation = gen;
	}
	
	public int getGeneration() {
		return this.generation;
	}
	
	public EnumFacing getFacing() {
		return this.facing;
	}
	
	public void setFacing(Random r) {
		this.facing = EnumFacing.getFront(r.nextInt(4)+2);
		
	}
	
	public void changeFacing() {
		this.facing = EnumFacing.getHorizontal(this.facing.getHorizontalIndex()+1);
	}
	
	public void createRoads(BlockPos villageCenter) {
		if(facing == null) return;
		boolean shouldExecute = true;
		if(this.generation==0) {
			if(((facing == EnumFacing.NORTH || facing == EnumFacing.WEST) && this.quad == 0) || 
			   ((facing == EnumFacing.SOUTH || facing == EnumFacing.WEST) && this.quad == 1) ||
			   ((facing == EnumFacing.SOUTH || facing == EnumFacing.EAST) && this.quad == 2) ||
			   ((facing == EnumFacing.NORTH || facing == EnumFacing.EAST) && this.quad == 3)) {
				shouldExecute = true;
			}else {
				shouldExecute = false;
			}
		}else if(this.generation == 1){
			if(bornFromX) {
				if((facing == EnumFacing.SOUTH && this.quad == 0) ||
				   (facing == EnumFacing.NORTH && this.quad == 1) ||
				   (facing == EnumFacing.NORTH && this.quad == 2) ||
				   (facing == EnumFacing.SOUTH && this.quad == 3)) {
					shouldExecute = false;
				}
			}else {
				if((facing == EnumFacing.EAST && this.quad == 0) ||
				   (facing == EnumFacing.EAST && this.quad == 1) ||
				   (facing == EnumFacing.WEST && this.quad == 2) ||
				   (facing == EnumFacing.WEST && this.quad == 3)) {
				    shouldExecute = false;
				 }
			}
		}
		IBlockState state = BlockRegistry.BricksBlue.getDefaultState();
		IBlockState state1 = BlockRegistry.BricksBlue.getDefaultState();
		int distance = this.radius+1;
		if(Math.abs(this.center.getX() - this.villageCenter.getX()) < 10 || Math.abs(this.center.getZ() - this.villageCenter.getZ()) < this.radius+7) {
			distance = Math.min(Math.abs(this.center.getX() - this.villageCenter.getX()), Math.abs(this.center.getZ() - this.villageCenter.getZ()));
		}
		for(int i = 0; i<this.radius*2+5; i++) {
			BlockPos pos = this.center.offset(facing.getOpposite(), distance).offset(facing.getOpposite().rotateYCCW(),this.radius+3-i);
			BlockPos pos1 = new BlockPos(pos.getX(), this.world.getHeight(pos.getX(),pos.getZ())-1,pos.getZ());
			IBlockState state2 = this.world.getBlockState(pos1);
			if(i == 0 || i == this.radius+3) {
				
				
				if(this.isOnWater(this.dark_sand) || state2.getBlock() == Blocks.WATER || 
				   this.world.getBlockState(pos1.offset(facing.getOpposite().rotateYCCW(), -2)).getBlock() == Blocks.WATER ||
				   this.world.getBlockState(pos1.offset(facing.getOpposite().rotateYCCW(), -4)).getBlock() == Blocks.WATER) {
					state = BlockRegistry.DampWood.getDefaultState();
				}
			}
			
			if(shouldExecute /*&& state2.getBlock() != BlockRegistry.DampStone*/ && state2.getBlock() != BlockRegistry.DampWood && state2.getBlock()!= BlockRegistry.DampCanopy 
					&& state2.getBlock() != BlockRegistry.DampCanopyWood && state2.getBlock()!= BlockRegistry.DampLog) this.setRoadBlocks(pos1, state, facing);
			//System.out.println(pos1);
			/*if((this.world.getBlockState(pos1).getBlock() == BlockRegistry.DarkSand || this.world.getBlockState(pos1).getBlock() == Blocks.GRASS) && pos1.getY() > 2) {
				if(shouldExecute) {
					this.setRoadBlocks(pos1, BlockRegistry.DampStone.getDefaultState(), facing);
				}
				
			}else if((this.world.getBlockState(pos1).getBlock() == Blocks.WATER || this.world.getBlockState(pos1.offset(facing.rotateYCCW())).getBlock() == Blocks.WATER) && pos1.getY() > 2) {
				if(shouldExecute){
					this.setRoadBlocks(pos1, BlockRegistry.DampWood.getDefaultState(), facing);
				}
			}*/
		}
		
		for(int i = 0; i<this.radius*2+5; i++) {
			BlockPos pos = this.center.offset(facing, this.radius+3-i).offset(facing.rotateYCCW().getOpposite(), distance);
			BlockPos pos1 = new BlockPos(pos.getX(), this.world.getHeight(pos.getX(), pos.getZ())-1, pos.getZ());
			IBlockState state2 = this.world.getBlockState(pos1);
			if(i == 0 || i == this.radius+3) {
				
				if(this.isOnWater(this.dark_sand) || state2.getBlock() == Blocks.WATER || 
				   this.world.getBlockState(pos1.offset(facing, -2)).getBlock() == Blocks.WATER ||
				   this.world.getBlockState(pos1.offset(facing, -4)).getBlock() == Blocks.WATER || state2.getBlock() == BlockRegistry.DampWood || 
				   this.world.getBlockState(pos1.offset(facing, -2)).getBlock() == BlockRegistry.DampWood ||
				   this.world.getBlockState(pos1.offset(facing, -4)).getBlock() == BlockRegistry.DampWood) {
					state1 = BlockRegistry.DampWood.getDefaultState();
				}
			}
			
			if(state2.getBlock() != BlockRegistry.BricksBlue && state2.getBlock() != BlockRegistry.DampWood && state2.getBlock()!= BlockRegistry.DampCanopy && 
			   state2.getBlock() != BlockRegistry.DampCanopyWood && state2.getBlock()!= BlockRegistry.DampLog) this.setRoadBlocks(pos1, state1, facing.rotateYCCW());
			/*if((this.world.getBlockState(pos1).getBlock() == BlockRegistry.DarkSand || this.world.getBlockState(pos1).getBlock() == Blocks.GRASS) && pos1.getY() > 2) {
				//if(shouldExecute) {
					this.setRoadBlocks(pos1, BlockRegistry.DampStone.getDefaultState(), facing.rotateYCCW());
				//}
				
			}else if((this.world.getBlockState(pos1).getBlock() == Blocks.WATER || this.world.getBlockState(pos1.offset(facing.getOpposite())).getBlock() == Blocks.WATER || this.world.getBlockState(pos1.offset(facing)).getBlock() == BlockRegistry.DampWood) && pos1.getY() > 2) {
				//if(shouldExecute){
					this.setRoadBlocks(pos1, BlockRegistry.DampWood.getDefaultState(), facing.rotateYCCW());
				//}
			}*/
		}
		
		//this.makeDoorRoads();
		
		if(this.generation<1 && !shouldExecute) {
			if(bornFromX) {
				
			}else {
				
			}
		}
	}
	
	public boolean isOnWater(int[][] dark_sand) {
		int counter = 0;
		for(int i = 0; i < dark_sand.length; i++) {
			if((i & 7) == 0) {
				Block block = this.world.getBlockState(this.center.add(dark_sand[i][0], -1, dark_sand[i][2])).getBlock();
				if(block == Blocks.WATER) {
					counter++;
				}
			}
		}
		if(counter > dark_sand.length/10) return true;
		
		return false;
	}
	
	protected void fillFloor(int[][] floor) {
		
		for(int[] n : floor) {
			int counter = 2;
			int xChange = 0;
			int zChange = 0;
			if(facing == EnumFacing.NORTH) {
				xChange = n[0];
				zChange = n[2];
			}else if(facing == EnumFacing.EAST) {
				xChange = -n[2];
				zChange = n[0];
			}else if(facing == EnumFacing.SOUTH) {
				xChange = -n[0];
				zChange = -n[2];
			}else if(facing == EnumFacing.WEST) {
				xChange = n[2];
				zChange = -n[0];
			}	
			int test = 0;	
			for(int i = 2; i < 10; i++) {
				BlockPos pos = new BlockPos(this.center.getX()+xChange, this.center.getY()-i, this.center.getZ()+zChange);
				Block block = this.world.getBlockState(pos).getBlock();
				if(block != Blocks.AIR && block != Blocks.WATER) {
					test++;
					if(test==2) break;
				}
				counter++;
				
			}
			for(int j = 0; j<= counter; j++) {
				BlockPos pos = new BlockPos(this.center.getX()+xChange, this.center.getY()-counter+j, this.center.getZ()+zChange);
				this.world.setBlockState(pos, BlockRegistry.DarkSand.getDefaultState());
			}
		}
	}
	
	private void setRoadBlocks(BlockPos pos1, IBlockState state, EnumFacing face) {
		if(this.world.getBlockState(pos1) == BlockRegistry.DampCanopy) return;
		this.world.setBlockState(pos1, state);
		this.world.setBlockState(pos1.offset(face), state);
		this.world.setBlockState(pos1.offset(face.getOpposite()), state);
	}
	
	public void setBornFromX(boolean yesOrNo) {
		this.bornFromX = yesOrNo;
	}
	
	public void setVillageCenter(BlockPos pos) {
		this.villageCenter = pos;
	}
	
	protected void setTooMuchStone(boolean yesOrNo) {
		this.tooMuchStone = yesOrNo;
	}
	
	protected void makeDoorRoads(int blocks) {
		if(this.doorPos != null) {
			int lastPosY = this.doorPos.getY();
			for(int i = 1; i <= blocks; i++) {
				BlockPos newPos = this.doorPos.offset(this.facing.getOpposite(),i);
				if(i>2) newPos = newPos.add(0, this.world.getHeight(newPos.getX(), newPos.getZ())-this.doorPos.getY()-1, 0);
				if(newPos.getY()-lastPosY >= 2) newPos = newPos.add(0, lastPosY-newPos.getY()+1, 0);
				lastPosY = newPos.getY();
				IBlockState state = BlockRegistry.BricksBlue.getDefaultState();
				Block block = this.world.getBlockState(newPos).getBlock();
				if(i >= 3 && (block == BlockRegistry.DampWood || block == BlockRegistry.BricksBlue)) break;
				if(this.isItOnWater || block == Blocks.WATER) {
					state = BlockRegistry.DampWood.getDefaultState();
				}
				this.setRoadBlocks(newPos, state, this.facing.rotateYCCW());
				if(i>1) {
					this.setRoadBlocks(newPos.add(0, 1, 0), Blocks.AIR.getDefaultState(), this.facing.rotateYCCW());
					this.setRoadBlocks(newPos.add(0, 2, 0), Blocks.AIR.getDefaultState(), this.facing.rotateYCCW());
					this.setRoadBlocks(newPos.add(0, 3, 0), Blocks.AIR.getDefaultState(), this.facing.rotateYCCW());
				}
			}
		}
	}
	
	
	public void spawnHamletDwellers() {
		
	}
	
	protected BlockPos getPosFromArray(int[] n, EnumFacing facing) {
		int[] newArray = {n[0], n[1], n[2]};
		switch(facing.getHorizontalIndex()) {
		case 0: 
			newArray[0] = -n[0];
			newArray[2] = -n[2];
			break;
		case 1:
			newArray[0] = n[2];
			newArray[2] = -n[0];
			break;
		case 3:
			newArray[0] = -n[2];
			newArray[2] = n[0];
			break;
		}
		BlockPos pos = new BlockPos(x+newArray[0], y+newArray[1], z+newArray[2]);
		return pos;
	}
	
	protected void getRandomBarrel(Random r, BlockPos pos) {
		this.world.setBlockState(pos, BlockRegistry.BlockBarrel.getDefaultState());
		TileEntity te = this.world.getTileEntity(pos);
		if(te instanceof TileBarrel) {
			TileBarrel b = (TileBarrel)te;
			int s = r.nextInt(40);
			if(s > 32)
				b.setFishType(FishType.SLUGS);
			else 
				b.setFishType(FishType.COD);
			b.setFishCount(s % 33);
		}
		
	}
	
	int[][] dark_sand = {{0,0,0,0}};
	int[] doorCoords = {0};
	
}
