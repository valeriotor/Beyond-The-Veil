package com.valeriotor.beyondtheveil.potions;

import java.util.List;

import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.BTVEntityRegistry;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageCameraRotatorClient;
import com.valeriotor.beyondtheveil.network.MessageMovePlayer;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class PotionTerror extends Potion{

	protected PotionTerror(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(References.MODID + ":terror");
	}
	
	
	@Override
	public void performEffect(EntityLivingBase e, int amplifier) {
		if(e instanceof EntityPlayer) {
			ItemStack stack = BaublesApi.getBaublesHandler((EntityPlayer)e).getStackInSlot(4);
			if(stack.getItem() == ItemRegistry.bone_tiara && 
			  ((EntityPlayer)e).getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, 4), 1, false) == 1	) return;
			
			EntityLivingBase entity = MathHelperBTV.getClosestLookedAtEntity((EntityPlayer)e, 7, ent -> ent != e);
			if(entity != null && isScaredByEntity(entity, amplifier)) {
				if(e.world.rand.nextBoolean()) {
					if(!e.world.isRemote) {
						boolean left = e.world.rand.nextBoolean();
						BTVPacketHandler.INSTANCE.sendTo(new MessageCameraRotatorClient(left ? -84 : 84, 0, 7), (EntityPlayerMP)e);
					}
				}	
				else {
					this.moveEntity(entity, e);
				}
			}
		}else if(!BTVEntityRegistry.isFearlessEntity(e)){
			List<EntityLivingBase> ents = e.world.getEntities(EntityLivingBase.class, ent -> ent.getDistance(e) < 7 && isScaredByEntity(ent, amplifier));
			for(EntityLivingBase entity : ents) {
				this.moveEntity(entity, e);
			}
		}
		super.performEffect(e, amplifier);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		if(duration % 5 == 0) return true;
		return false;
	}
	
	private void moveEntity(EntityLivingBase entity, EntityLivingBase e) {
		double xDist = entity.posX - e.posX;
		double zDist = entity.posZ - e.posZ;
		double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
		if(dist != 0) {
			if(e instanceof EntityPlayerMP) {
				BTVPacketHandler.INSTANCE.sendTo(new MessageMovePlayer(- xDist / dist, 0, - zDist / dist), (EntityPlayerMP)e);
			} else {
				e.motionZ = - zDist / dist;
				e.motionX = - xDist / dist;	
			}
		}
	}
	
	private boolean isScaredByEntity(EntityLivingBase lookedAt, int amplifier) {
		if(BTVEntityRegistry.isScaryEntity(lookedAt)) return true;
		if(amplifier > 0 && lookedAt instanceof EntityPlayer) return true;
		if(amplifier > 1 && BTVEntityRegistry.isHostileEntity(lookedAt) ) return true;
		if(amplifier > 2) return true;
		return false;
	}

}
