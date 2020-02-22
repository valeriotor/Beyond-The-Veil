package com.valeriotor.BTV.worship.ActivePowers;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.MathHelperBTV;
import com.valeriotor.BTV.worship.DGWorshipHelper;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class WaterTeleport implements IActivePower{

	private WaterTeleport() {}
	private static final WaterTeleport INSTANCE = new WaterTeleport();
	public static IActivePower getInstance() {return INSTANCE;}
	
	@Override
	public boolean activatePower(EntityPlayer p) {
		if(p.world.isRemote) return false;
		if(!p.isInWater()) {
			p.sendMessage(new TextComponentTranslation("teleport.inwater"));
			return false;
		}
		BlockPos pPos = p.getPosition();
		if(!p.isSneaking()) {
			Vec3d vec3d = p.getPositionEyes(1.0F);
	        Vec3d vec3d1 = p.getLook(1.0F);
	        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * 90, vec3d1.y * 90, vec3d1.z * 90);
	        EntityLivingBase e = MathHelperBTV.getClosestLookedAtEntity(p, 150, ent -> ent.isInWater() && ent != p);
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
		} else {
			IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
			Long l = data.getLong(PlayerDataLib.WATERTPDEST); 
			if(l == null) {
				data.setLong(PlayerDataLib.WATERTPDEST, pPos.toLong());
				data.setInteger(PlayerDataLib.WATERTPDIM, p.dimension, false);
				p.sendMessage(new TextComponentTranslation("teleport.setposition"));
				return true;
			} else {
				if(p.dimension != data.getInteger(PlayerDataLib.WATERTPDIM))
					return false;
				data.removeLong(PlayerDataLib.WATERTPDEST);
				BlockPos newPos = BlockPos.fromLong(l);
				p.setPosition(newPos.getX(), newPos.getY(), newPos.getZ());
				if(p instanceof EntityPlayerMP)
					((EntityPlayerMP) p).connection.setPlayerLocation(newPos.getX() + 0.5, newPos.getY(), newPos.getZ() + 0.5, p.rotationYaw, p.rotationPitch);
				return true;
			}
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
		return DGWorshipHelper.canTeleport(p);
	}
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID + ":textures/gui/powers/water_teleport.png");
	
	@Override
	public ResourceLocation getGuiTexture() {
		return TEXTURE;
	}

}
