package com.valeriotor.beyondtheveil.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class Teleport extends Teleporter {
	
	private final WorldServer world;
	private final double x;
	private final double y;
	private final double z;
	
	public Teleport(WorldServer worldIn, double posX, double posY, double posZ) {
		super(worldIn);
		this.world = worldIn;
		this.x = posX;
		this.y = posY;
		this.z = posZ;
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		entityIn.setPosition(x, y, z);
		entityIn.motionX = 0;
		entityIn.motionY = 0;
		entityIn.motionZ = 0;
	}
	
}
