package com.valeriotor.BTV.entities.AI;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.entities.EntityHamletDweller;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class AIDwellerFollowPlayer extends EntityAIBase{
	
	private EntityHamletDweller dweller;
	private int counter = 0;
	
	public AIDwellerFollowPlayer(EntityHamletDweller e) {
		this.setMutexBits(1);
		this.dweller = e;
	}
	
	@Override
	public boolean shouldExecute() {
		return dweller.getFollowTime() > 0;
	}
	
	@Override
	public void updateTask() {
		if(counter == 0) {
			BlockPos pos = findSlugBait();
			if(pos != null) {
				dweller.getNavigator().setPath(dweller.getNavigator().getPathToXYZ(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5), 0.9);
				dweller.setFollowPlayer(null);
			} else {
				EntityPlayer p = dweller.getFollowPlayer();
				if(p != null) {
					dweller.getNavigator().setPath(dweller.getNavigator().getPathToEntityLiving(p), 0.8);
				}
			}
		}
		counter++;
		counter &= 31;
	}
	
	private BlockPos findSlugBait() {
		for(int x = -2; x < 3; x++) {
			for(int y = -2; y < 3; y++) {
				for(int z = -2; z < 3; z++) {
					BlockPos pos = dweller.getPosition().add(x, y, z);
					if(dweller.world.getBlockState(pos).getBlock() == BlockRegistry.BlockSlugBait) {
						return pos;
					}
				}
			}
		}
		return null;
	}

}
