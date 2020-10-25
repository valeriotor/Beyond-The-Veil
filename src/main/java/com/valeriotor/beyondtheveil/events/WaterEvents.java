package com.valeriotor.beyondtheveil.events;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.items.IDeepOneItem;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.ritual.MessagePerformHurtAnimation;
import com.valeriotor.beyondtheveil.world.DimensionRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;

public class WaterEvents {
	public static void playerTick(EntityPlayer p, IPlayerData data) {
		boolean transformed = data.getString(PlayerDataLib.TRANSFORMED);
		if(data.getString(PlayerDataLib.RITUALQUEST)) {
			if(p.isInWater()) {
				boolean arche = p.dimension == DimensionRegistry.ARCHE.getId();
				giveSpeedAndFlight(p, transformed);
				if((p.ticksExisted & 7) == 0) {
					giveVisionAndBreathing(p, arche);
					if((p.ticksExisted & 15) == 0)
						damageArchePlayer(p, arche, transformed);
				}
			} else if(transformed) {
				if(p.world.isRemote)
					p.capabilities.isFlying = false;
			}		
		}
		if(transformed && (p.ticksExisted & 15) == 0) {
			giveArcheHunger(p);
			dropItems(p, data);
			dropArmor(p);
		}
		
	}
	
	private static void giveSpeedAndFlight(EntityPlayer p, boolean transformed) {
		double motX = p.motionX * 1.2;
		double motY = p.motionY * 1.25;
		double motZ = p.motionZ * 1.2;
		boolean flying = p.capabilities.isFlying;
		if(transformed) {
			p.capabilities.isFlying = true;
			if(p.world.isRemote)
				p.capabilities.setFlySpeed(0.06F);
			if(!p.isPotionActive(MobEffects.REGENERATION) && p.dimension != DimensionRegistry.ARCHE.getId())
				p.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 300, 1, false, false));
		} else if(!flying) {
			if(Math.abs(p.motionX) < 1.3) p.motionX = motX;
			if((p.motionY > 0 || p.isSneaking()) && p.motionY < 1.3) p.motionY = motY;
			if(Math.abs(p.motionZ) < 1.3) p.motionZ = motZ;
		}
	}
	
	private static void giveVisionAndBreathing(EntityPlayer p, boolean arche) {
		BlockPos pos = p.getPosition();
		if(arche && (pos.getX() & 1023) == 0 && (pos.getZ() & 1023) == 0 && pos.getY() == 129) {
			return;
		} else {
			p.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 300, 0, false, false));
			p.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, false, false));
		}
	}
	
	private static void damageArchePlayer(EntityPlayer p, boolean arche, boolean transformed) {
		if(!transformed && arche && !p.capabilities.isCreativeMode && !p.isSpectator()) {
			if(!p.world.isRemote) {
				p.setHealth(p.getHealth()-1);
			} else {
				p.performHurtAnimation();
				p.playSound(SoundEvents.ENTITY_PLAYER_HURT_DROWN, 1, 1);
			}
		}
	}
	
	private static void giveArcheHunger(EntityPlayer p) {
		if((p.ticksExisted & 1023) == 0 && p.dimension == DimensionRegistry.ARCHE.getId()) {
			p.getFoodStats().addStats(-1, -1);
			if(p.isSprinting()) p.addExhaustion(1);
		}
	}
	
	private static void dropItems(EntityPlayer p, IPlayerData data) {
		ItemStack stack = p.getHeldItemMainhand();
		if(stack.getItem() != Items.AIR && !canDeepOneHold(stack.getItem(), p, data)) {
			dropItem(p, stack);
		}
		stack = p.getHeldItemOffhand();
		if(stack.getItem() != Items.AIR && !canDeepOneHold(stack.getItem(), p, data)) {
			dropItem(p, stack);
		}
	}
	
	private static void dropItem(EntityPlayer p, ItemStack stack) {
		ItemStack clone = stack.copy();
		if(stack.getItem() != ItemRegistry.slug)
			p.dropItem(clone, true);
		stack.setCount(0);
	}
	
	private static void dropArmor(EntityPlayer p) {
		for(int i = 0; i < 4; i++) {
			ItemHandlerHelper.giveItemToPlayer(p, p.inventory.armorInventory.get(i), 9+i);
			p.inventory.armorInventory.set(i, ItemStack.EMPTY);
		}
	}
	
	private static boolean canDeepOneHold(Item i, EntityPlayer p, IPlayerData data) {
		if(i instanceof IDeepOneItem) {
			return ((IDeepOneItem)i).canHold(p, data);
		}
		return isDeepOneVanillaItem(i);
	}
	
	private static boolean isDeepOneVanillaItem(Item i) {
		if(i instanceof ItemFishFood || 
				   i == Item.getItemFromBlock(Blocks.PRISMARINE) || 
				   i == Items.PRISMARINE_CRYSTALS ||
				   i == Item.getItemFromBlock(Blocks.SPONGE)) {
					return true;
		}
		return false;
	}
}
