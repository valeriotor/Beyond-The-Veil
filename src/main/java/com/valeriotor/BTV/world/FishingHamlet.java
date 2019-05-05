package com.valeriotor.BTV.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.EntityCanoe;
import com.valeriotor.BTV.world.Structures.HamletHouse1;
import com.valeriotor.BTV.world.Structures.HamletStructure;
import com.valeriotor.BTV.world.Structures.HamletStructuresRegistry;
import com.valeriotor.BTV.world.Structures.HamletTownHall;
import com.valeriotor.BTV.world.Structures.Idol;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class FishingHamlet {
	
	private int size;
	private UUID u;
	private final List<HamletStructure> structureList = new ArrayList<HamletStructure>();
	private final Map<BlockPos, HamletStructure> outerRing = new HashMap<BlockPos, HamletStructure>();
	
	public FishingHamlet(World w,  Random r, BlockPos pos) {
		u = UUID.randomUUID();
		StartStructure(w, r, pos);
	}
	
	public void StartStructure(World w,  Random r, BlockPos pos) {
		
		
		//HamletList.get(w).registerHamlet(u, pos);
		pos.add(0, w.getHeight(pos.getX(), pos.getZ()), 0);
		//System.out.println(r);
		//Biome bob = w.getBiome(pos);
			
			
			int xOffset = pos.getX()+r.nextInt(17)-8;
			int zOffset = pos.getZ()+r.nextInt(17)-8;
			int height = w.getHeight(xOffset, zOffset);
			if(height == 0) return;
			BlockPos randomised = new BlockPos(xOffset, height, zOffset);
			HamletList.get(w).registerHamlet(u, randomised);
			Idol idol = new Idol(w);
			idol.setCenter(randomised);
			int tom = idol.getRadius() + 7;
			int george = r.nextInt(tom*4-4)+1;
			int x = george>tom*2 ? (george>tom*3 ? -tom : tom) : r.nextInt(17)-8;
			int z = george<=tom*2 ? r.nextInt(17)-8 : (george<=tom ? -8 : 8); 
			BlockPos todd = randomised.add(x, 0, z);
			this.size = r.nextInt(15)+14;
			HamletStructuresRegistry hsr = new HamletStructuresRegistry();
			
			
			for(int i = 0; i<this.size; i++) {
				structureList.add(hsr.getRandom(r,w));
				if(structureList.get(i) instanceof HamletTownHall && i > 3) {
					structureList.add(0, structureList.remove(i));
				}
			}
			
			
			for(int i = 0; i<4; i++) {
				HamletStructure o = structureList.get(i);
				int radius = o.getRadius();
				int randie = r.nextInt(8);
				int newX = randomised.getX() + (i<2 ? radius + randie : -radius - randie);
				int newZ = randomised.getZ() + (i == 0 || i ==3 ? radius+7-randie : -radius-7+randie);
				int newY = w.getHeight(newX, newZ);
				BlockPos newPos = new BlockPos(newX, newY, newZ);
				if(newPos.getY()==0 || newPos.getY()<2) newPos.add(0, randomised.getY(), 0);
				if(!w.isAreaLoaded(newPos, newPos)) {
					HamletList.get(w).unregisterHamlet(u);
					return;
				}
				
				o.setFacing(r);
				o.setGeneration(0);
				o.setCenter(newPos);
				o.setVillageCenter(randomised);
				o.setQuadrant(i);
				outerRing.put(newPos, (HamletStructure)structureList.get(i));
			}
			
			for(int i = 4; i<structureList.size(); i++) {
				HamletStructure o = structureList.get(i);
				HamletStructure p = (HamletStructure) outerRing.values().toArray()[r.nextInt(outerRing.size())];
				BlockPos oldPos = p.getCenter();
				int XorZ;
				if(p.getDirection()[0]==true && p.getDirection()[1]==true) {
					XorZ = r.nextInt(2);
				}else if(p.getDirection()[0] == true) {
					XorZ = 0;
				}else {
					XorZ = 1;
				}
				int quad = p.getQuadrant();
				int xChange;
				int zChange;
				boolean bornFromX = true;
				if(XorZ==0) {
					xChange = o.getRadius() + p.getRadius() + 3;
					zChange = o.getRadius()-p.getRadius();
					if(o.isLarge()) xChange--;
				}else {
					xChange = o.getRadius()-p.getRadius();
					zChange = o.getRadius() + p.getRadius() + 3;
					bornFromX = false;
					if(o.isLarge()) zChange--;
				}
				int newX = oldPos.getX() + (quad<2 ? xChange : -xChange);
				int newZ = oldPos.getZ() + (quad == 0 || quad == 3 ? zChange : -zChange);
				//BlockPos newPos = new BlockPos(oldPos.getX(), oldPos.getY(), oldPos.getZ());
				BlockPos newPos = new BlockPos(newX, w.getHeight(newX,newZ), newZ);
				if(newPos.getY()==0 || newPos.getY()<2) newPos.add(0, randomised.getY(), 0);
				if(!w.isAreaLoaded(newPos, newPos)) {
					HamletList.get(w).unregisterHamlet(u);
					return;
				}
				
				if(!p.getDirection()[Math.abs(XorZ-1)]) {
					o.setDirection(Math.abs(XorZ-1), false);
				}
				
				o.setGeneration(p.getGeneration()+1);
				o.setCenter(newPos);
				o.setVillageCenter(randomised);
				o.setQuadrant(quad);
				o.setFacing(r);
				o.setBornFromX(bornFromX);
				if(o.getGeneration()<this.size / 5) {
					outerRing.put(newPos, o);
				}
				
				if(o.getFacing() == p.getFacing().getOpposite()) o.changeFacing();
				
				
				p.setDirection(XorZ, false);
				if(p.getDirection()[0] == false && p.getDirection()[1] == false) {
					outerRing.remove(p.getCenter());
				}
				
				
				
			}
			
			for(int j = 0; j<structureList.size();j++) {
				
				HamletStructure o = structureList.get(j);
				o.StartStructure(r);
				o.createRoads(randomised);
				o.doorRoads();
				o.spawnHamletDwellers();
				
			}
			
			boolean isOnWater = idol.isIdolOnWater();
			for(EnumFacing facing : EnumFacing.HORIZONTALS) {
				IBlockState state = BlockRegistry.BricksBlue.getDefaultState();
				int length = r.nextInt(20) + 42;
				for(int path = 3; path < length; path++) {
					
					BlockPos pathPos = randomised.offset(facing, path);
					BlockPos pathPos1 = new BlockPos(pathPos.getX(), w.getHeight(pathPos.getX(),pathPos.getZ())-1, pathPos.getZ());
					if(pathPos1.getY()==0) pathPos.add(0, w.getHeight(), 0);
					
					if(isOnWater) {
						state = BlockRegistry.DampWood.getDefaultState();
					}else if(((path-3) & 3) == 0) {
						if(w.getBlockState(pathPos1).getBlock() == Blocks.WATER || w.getBlockState(pathPos1.offset(facing)).getBlock() == Blocks.WATER ||
						   w.getBlockState(pathPos1.offset(facing, 2)).getBlock() == Blocks.WATER) {
							state = BlockRegistry.DampWood.getDefaultState();
						}else {
							state = BlockRegistry.BricksBlue.getDefaultState();
						}
					}
					
					BlockPos pathPos2 = pathPos1.offset(facing.rotateYCCW());
					BlockPos pathPos3 = pathPos1.offset(facing.rotateYCCW().rotateYCCW().rotateYCCW());
					w.setBlockState(pathPos1, state);
					w.setBlockState(pathPos2, state);
					w.setBlockState(pathPos3, state);
					/*if(w.getBlockState(pathPos1) != Blocks.WATER.getDefaultState() && w.getBlockState(pathPos1.offset(facing)) != Blocks.WATER.getDefaultState()) {
						w.setBlockState(pathPos1, BlockRegistry.DampStone.getDefaultState());
						w.setBlockState(pathPos2, BlockRegistry.DampStone.getDefaultState());
						w.setBlockState(pathPos3, BlockRegistry.DampStone.getDefaultState());
					}else {
						w.setBlockState(pathPos1, BlockRegistry.DampWood.getDefaultState());
						w.setBlockState(pathPos2, BlockRegistry.DampWood.getDefaultState());
						w.setBlockState(pathPos3, BlockRegistry.DampWood.getDefaultState());
					}*/
					w.setBlockToAir(pathPos3.offset(EnumFacing.UP));
					w.setBlockToAir(pathPos2.offset(EnumFacing.UP));
					w.setBlockToAir(pathPos1.offset(EnumFacing.UP));
					w.setBlockToAir(pathPos3.offset(EnumFacing.UP,2));
					w.setBlockToAir(pathPos2.offset(EnumFacing.UP,2));
					w.setBlockToAir(pathPos1.offset(EnumFacing.UP,2));
					
					if(path == length - 1 || path == length / 2 || path == length / 3 - 1) {
						BlockPos canoePos = pathPos1.offset(facing, 4).offset(facing.rotateYCCW(), path == length - 1 ? 2 : 4);
						if(w.getBlockState(canoePos) == Blocks.WATER.getDefaultState() || w.getBlockState(canoePos.add(0, 1, 0)) == Blocks.WATER.getDefaultState()) {
							EntityCanoe canoe = new EntityCanoe(w);
							canoe.setPositionAndRotation(canoePos.getX(), canoePos.getY()+1, canoePos.getZ(), r.nextFloat()*360-90, 0);
							for(EnumFacing facing1 : EnumFacing.HORIZONTALS) {
								w.setBlockState(canoePos.offset(facing1), Blocks.WATER.getDefaultState());
								w.setBlockState(canoePos.offset(facing1.rotateYCCW()), Blocks.WATER.getDefaultState());
							}
							w.spawnEntity(canoe);
						}
					}
				}
			}
			

			idol.StartStructure(r);
			
			
			
	}
}