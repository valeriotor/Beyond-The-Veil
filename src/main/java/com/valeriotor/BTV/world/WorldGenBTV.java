package com.valeriotor.BTV.world;

import java.util.Random;
import java.util.UUID;

import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGenBTV implements IWorldGenerator{
	
	private ChunkPrimer cp = new ChunkPrimer();
	private static final boolean DEBUG = true;
	public WorldGenBTV() {
		
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
		case -1: generateNether(world, random, chunkX*16, chunkZ*16);
			break;
		case 0: generateOverworld(world, random, chunkX*16, chunkZ*16, chunkX, chunkZ);
		}
				
	}

	
	
	public void generateNether(World w, Random r, int x, int y) {
		
	}
	
	public void generateOverworld(World w, Random r, int x, int z, int cx, int cz) {
		if(w.isRemote) return;
		
		BlockPos pos = new BlockPos(x,0,z);
		if(HamletList.get(w).isTooClose(pos)) return;
		if(w.getBiome(pos) != BiomeRegistry.innsmouth && (w.getBiome(pos) != Biomes.PLAINS || !DEBUG)) return;
		if(r.nextInt(12) < 11 && w.getBiome(pos) != BiomeRegistry.innsmouth) return;
		if(!areChunksGood(w, pos)) {
			pos = areaLoaded(w,pos);
		}
		
		if(pos!=null) {
			new FishingHamlet(w,r,pos);
		}
		
	}
	
	public boolean areChunksGood(World w, BlockPos pos) {
		
		
		BlockPos pos4 = new BlockPos(pos.getX()-3*16, pos.getY(), pos.getZ()-3*16);
		BlockPos pos5 = new BlockPos(pos.getX()-3*16, pos.getY(), pos.getZ()-3*16);
		if(!w.isAreaLoaded(pos4, pos5)) return false;
		
		for(int chunkX = -3; chunkX < 4; chunkX++) {
			for(int chunkZ = -3; chunkZ < 4; chunkZ++) {
				BlockPos pos1 = new BlockPos(pos.getX()+chunkX*16, 0, pos.getZ()+chunkZ*16);
				if (w.getBiome(pos1) != BiomeRegistry.innsmouth && w.getBiome(pos1) != Biomes.PLAINS) return false;
				
			}
		}
		return true;
	}
	
	public BlockPos areaLoaded(World w, BlockPos pos) {
		BlockPos pos1 = null;
		for(int chunkX = -3; chunkX<4; chunkX++) {
			for(int chunkZ = -3; chunkZ < 4; chunkZ++) {
				pos1 = pos.add(chunkX*16, 0, chunkZ*16);
				if(areChunksGood(w, pos1)) {
					break;
				}else pos1 = null;
			}
		}	
		return pos1;
	}


}
