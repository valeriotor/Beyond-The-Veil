package com.valeriotor.beyondtheveil.potions;

import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.BTVEntityRegistry;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageCameraRotatorClient;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.CameraRotatorClient.RotatorFunction;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class PotionFolly extends Potion{

	protected PotionFolly(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(References.MODID + ":folly");
	}
	
	@Override
	public void performEffect(EntityLivingBase e, int amplifier) {
		if(BTVEntityRegistry.isFearlessEntity(e)) return;
		if(e instanceof EntityMob) {
			
			((EntityMob) e).setAttackTarget(null);
			e.setRotationYawHead(e.world.rand.nextInt(360));
		}
		if(e instanceof EntityPlayer) {
			ItemStack stack = BaublesApi.getBaublesHandler((EntityPlayer)e).getStackInSlot(4);
			if(stack.getItem() == ItemRegistry.bone_tiara && 
			  ((EntityPlayer)e).getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.PASSIVE_BAUBLE, 4), 1, false) == 1	) return;
			if(!e.world.isRemote) {
				boolean left = e.world.rand.nextBoolean(), up = e.rotationPitch > 30 ? true : (e.rotationPitch < -30 ? false : e.world.rand.nextBoolean());
				BTVPacketHandler.INSTANCE.sendTo(new MessageCameraRotatorClient(e.world.rand.nextInt(10) * 20 - 100, up ? -42 : 42, 7, RotatorFunction.QUADRATIC, RotatorFunction.QUADRATIC), (EntityPlayerMP)e);
			}
		} else
			e.rotationPitch += e.world.rand.nextInt(20 + 10*amplifier)- 10 - 5*amplifier + (e.rotationPitch > 100 ? -10 : (e.rotationPitch < -100 ? +10 : 0));
		super.performEffect(e, amplifier);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		if(duration % 5 == 0) return true;
		return false;
	}

}
