package com.valeriotor.BTV.worship;

import com.google.common.collect.ImmutableMap;
import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.IWorship;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;

public enum Deities {
	GREATDREAMER("greatdreamer"); // This is gonna stay like this for a loooooooooooooong time
	
	private static ImmutableMap<String, Capability<IWorship>> map = ImmutableMap.of(
			"greatdreamer", DGProvider.LEVEL_CAP
			);
	private String key;
	
	private Deities(String key) {
		this.key = key;
	}
	
	public IWorship cap(EntityPlayer p) {
		return p.getCapability(map.get(this.key), null);
	}
	
	
}
