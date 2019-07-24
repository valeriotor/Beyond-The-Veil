package com.valeriotor.BTV.worship.ActivePowers;

import com.valeriotor.BTV.util.MathHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class WaterTeleport implements IActivePower{

	private WaterTeleport() {}
	private static final WaterTeleport INSTANCE = new WaterTeleport();
	public static IActivePower getInstance() {return INSTANCE;}
	
	@Override
	public boolean activatePower(EntityPlayer p) {
		if(p.world.isRemote) return false;
		if(!p.isInWater()) return false;
		BlockPos pPos = p.getPosition();
		Vec3d vec3d = p.getPositionEyes(1.0F);
        Vec3d vec3d1 = p.getLook(1.0F);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * 90, vec3d1.y * 90, vec3d1.z * 90);
        EntityLiving e = MathHelper.getClosestLookedAtEntity(p, 150, ent -> ent.isInWater());
        RayTraceResult rt = p.world.rayTraceBlocks(vec3d, vec3d2, false, true, true);
        BlockPos rtPos = null;
        if(rt != null)
        	rtPos = rt.getBlockPos();
        if(e != null) {
        	BlockPos pos = e.getPosition();
        	double dist = pos.distanceSq(pPos.getX(), pPos.getY(), pPos.getZ());
        	if(rtPos == null || rtPos.distanceSq(pPos.getX(), pPos.getY(), pPos.getZ()) > dist) {
        		Vec3d vec3d3 = vec3d1.subtractReverse(new Vec3d(0,0,0));
        		pos = pos.add(new Vec3i((int)vec3d3.x, (int)vec3d3.y, (int)vec3d3.z));
        		p.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
        		return true;
        	}
        }
        if(rtPos != null) {
        	Vec3d vec3d3 = vec3d1.subtractReverse(new Vec3d(0,0,0));
        	rtPos = rtPos.add(new Vec3i((int)vec3d3.x, (int)vec3d3.y, (int)vec3d3.z));
        	p.setPositionAndUpdate(rtPos.getX(), rtPos.getY(), rtPos.getZ());
        	return true;
        }	
        
        return false;
	}
	
	private boolean isBlockInWater(World w, BlockPos pos) {
		for(int x = -1; x < 2; x++) {
			for(int y = -1; y < 2; y++) {
				for(int z = -1; z < 2; z++) {
					if(w.getBlockState(pos).getBlock() == Blocks.WATER) return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getCooldownTicks() {
		return 100;
	}

	@Override
	public Deities getDeity() {
		return Deities.GREATDREAMER;
	}

	@Override
	public int getIndex() {
		return 3;
	}

	@Override
	public boolean hasRequirement(EntityPlayer p) {
		return true; // TODO: Change this to check for research
	}

}
