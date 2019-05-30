package com.valeriotor.BTV.worship;

import com.google.common.collect.ImmutableList;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.worship.ActivePowers.IActivePower;
import com.valeriotor.BTV.worship.ActivePowers.SummonDeepOnes;

import net.minecraft.entity.player.EntityPlayer;

public class Worship {
	
	public static void setSelectedDeity(EntityPlayer p, Deities deity) {
		p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(PlayerDataLib.SELECTED_DEITY, deity.ordinal(), false);
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
	
	private static final ImmutableList<IActivePower> powers = new ImmutableList.Builder<IActivePower>()
			.add(SummonDeepOnes.getInstance())
			.build();
	
	public static IActivePower getPower(EntityPlayer p) {
		Deities deity = getSelectedDeity(p);
		System.out.println(deity.name());
		if(deity == Deities.NONE) return null;
		int index = getSelectedPower(p);
		System.out.println(index);
		for(IActivePower pow : powers) {
			if(pow.getDeity() == deity && pow.getIndex() == index) {
				if(pow.hasRequirement(p)) return pow;
				break;
			}
		}
		return null;
	}
	
	public static int getSelectedPower(EntityPlayer p) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SELECTED_POWER, 0, false);
	}
}
