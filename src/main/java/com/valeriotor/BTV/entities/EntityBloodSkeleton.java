package com.valeriotor.BTV.entities;

import java.util.UUID;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBloodSkeleton extends EntityMob implements IPlayerGuardian{
	
	private int animCounter = -1;
	
	public EntityBloodSkeleton(World worldIn) {
		super(worldIn);
	}

	@Override
	public EntityPlayer getMaster() {
		return null;
	}

	@Override
	public UUID getMasterID() {
		return null;
	}

	@Override
	public void setMaster(EntityPlayer p) {
		
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(world.isRemote) {
			if(animCounter > 0) {
				animCounter--;
			} else {
				animCounter = world.rand.nextInt(15)*200 + 800;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getAnimCounter() {
		return this.animCounter;
	}

}
