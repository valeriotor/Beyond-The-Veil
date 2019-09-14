package com.valeriotor.BTV.potions;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.BTVEntityRegistry;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
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
			
			e.rotationYaw += e.world.rand.nextInt(20 + 10*amplifier)- 10 - 5*amplifier;
		}
		e.rotationPitch += e.world.rand.nextInt(20 + 10*amplifier)- 10 - 5*amplifier + (e.rotationPitch > 100 ? -10 : (e.rotationPitch < -100 ? +10 : 0));
		super.performEffect(e, amplifier);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

}
