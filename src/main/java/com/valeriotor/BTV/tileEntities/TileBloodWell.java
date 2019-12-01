package com.valeriotor.BTV.tileEntities;

import java.util.List;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.IPlayerGuardian;
import com.valeriotor.BTV.lib.BTVSounds;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileBloodWell extends TileEntity implements ITickable{
	
	public TileBloodWell() {}
	

	private int counter = 29;
	private int soundCounter = 30;
	private int bloodZombies = 0;
	private int bloodSkellies = 0;
	
	@Override
	public void update() {
		if(!this.world.isRemote) {
			counter--;
			if(counter % 10 == 0) {
				if(counter <= 0) {
					if(!BlockRegistry.BlockBloodWell.checkStructure(pos, world)) {
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
						return;
					}
					counter = 29;
					List<EntityLiving> undead = this.world.getEntities(EntityLiving.class, e -> e.isEntityUndead() && e.getDistanceSq(pos) < 1024 && !(e instanceof IPlayerGuardian));
					for(EntityLiving e : undead) {
						e.getNavigator().setPath(e.getNavigator().getPathToPos(this.pos), 0.9);
					}
				} else {
					List<EntityLiving> undead = this.world.getEntities(EntityLiving.class, e -> e.isEntityUndead() && e.getDistanceSq(pos) < 3 && !(e instanceof IPlayerGuardian));
					for(EntityLiving ent : undead) {
						if(ent instanceof EntityZombie || ent instanceof EntityPigZombie) {
							bloodZombies++;
						} else if(ent instanceof EntitySkeleton || ent instanceof EntityWitherSkeleton) {
							bloodSkellies++;
						}
						if(ent.isNonBoss()) world.removeEntity(ent);
					}
				}
			}
		} else {
			soundCounter--;
			if(soundCounter <= 0) {
				soundCounter = 20;
				this.world.playSound(pos.getX(), pos.getY(), pos.getZ(), BTVSounds.heartbeat, SoundCategory.AMBIENT, 1, 1, true);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("zombies", bloodZombies);
		compound.setInteger("skellies", bloodSkellies);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("zombies")) this.bloodZombies = compound.getInteger("zombies");
		if(compound.hasKey("skellies")) this.bloodSkellies = compound.getInteger("skellies");
		super.readFromNBT(compound);
	}

}
