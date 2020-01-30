package com.valeriotor.BTV.worship;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.ActivePowers.IActivePower;
import com.valeriotor.BTV.worship.ActivePowers.SummonDeepOnes;
import com.valeriotor.BTV.worship.ActivePowers.TransformDeepOne;
import com.valeriotor.BTV.worship.ActivePowers.WaterTeleport;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Worship {
	
	public static void setSelectedDeity(EntityPlayer p, Deities deity) {
		SyncUtil.addIntDataOnServer(p, false, PlayerDataLib.SELECTED_DEITY, deity.ordinal());
	}
	
	public static Deities getSelectedDeity(EntityPlayer p) {
		return Deities.values()[p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SELECTED_DEITY, 0, false)];
	}
	
	public static int getSelectedDeityLevel(EntityPlayer p) {
		return getDeityLevel(p, getSelectedDeity(p));
	}
	
	public static int getDeityLevel(EntityPlayer p, Deities deity) {
		switch(deity) {
		case NONE: return 0;
		case GREATDREAMER: return Deities.GREATDREAMER.cap(p).getLevel(); // IS SUBJECT TO CHANGE WITH UPCOMING CHANGES TO WORSHIP
		default: return 0;
		}
	}
	
	private static final IActivePower NULL_POWER = new IActivePower() {
		@Override public boolean hasRequirement(EntityPlayer p) {return false;}
		@Override public int getIndex() {return 0;}
		@Override public Deities getDeity() {return null;}
		@Override public int getCooldownTicks() {return 0;}
		@Override public boolean activatePower(EntityPlayer p) {return false;}
		@Override public ResourceLocation getGuiTexture() {return null;}
	};
	
	public static IActivePower getPower(EntityPlayer p) {
		int index = getSelectedPowerIndex(p);
		return getSpecificPower(p, index);
	}
	
	public static IActivePower getSpecificPower(EntityPlayer p, int index) {
		Deities deity = getSelectedDeity(p);
		if(deity == Deities.NONE) return NULL_POWER;
		if(deity == Deities.GREATDREAMER) {
			switch(index) {
			case 0: return SummonDeepOnes.getInstance();
			case 1: return TransformDeepOne.getInstance();
			case 3: return WaterTeleport.getInstance();
			}
		}
		return NULL_POWER;
	}
	
	public static int getSelectedPowerIndex(EntityPlayer p) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SELECTED_POWER, 0, false);
	}
	
	public static void setPowerCooldown(EntityPlayer p, int ticks) {
		setPowerCooldown(p, ticks, getSelectedPowerIndex(p));
	}
	
	public static int getPowerCooldown(EntityPlayer p) {
		return getPowerCooldown(p, getSelectedPowerIndex(p));
	}
	
	public static void setPowerCooldown(EntityPlayer p, int ticks, int powerIndex) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(String.format(PlayerDataLib.POWER_COOLDOWN, powerIndex), Math.max(-1, ticks), false);
	}
	
	public static int getPowerCooldown(EntityPlayer p, int powerIndex) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(String.format(PlayerDataLib.POWER_COOLDOWN, powerIndex), 0, false);
	}
	
}
