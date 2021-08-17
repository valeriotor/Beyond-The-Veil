package com.valeriotor.beyondtheveil.world.arche;

import com.valeriotor.beyondtheveil.world.DimensionRegistry;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderArche extends WorldProvider{

	public WorldProviderArche() {
	}
	
	@Override
	protected void init() {
		this.biomeProvider = new BiomeProviderArche(this.world.getWorldInfo());
	}
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.ARCHE;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorArche(this.world, world.getSeed(), false);
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return false;
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		return new Vec3d(0.02F, 0.02F, 0.2F);
	}
	
}
