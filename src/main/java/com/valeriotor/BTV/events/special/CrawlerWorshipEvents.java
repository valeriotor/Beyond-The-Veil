package com.valeriotor.BTV.events.special;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.valeriotor.BTV.worship.CrawlerWorship;

import net.minecraft.entity.player.EntityPlayer;

public class CrawlerWorshipEvents {
	
	private static Map<UUID, CrawlerWorship> worships = new HashMap<>();
	
	public static void updateWorships(EntityPlayer p) {
		UUID u = p.getPersistentID();
		CrawlerWorship cw = worships.get(u);
		if(cw != null) {
			if(cw.update())
				worships.remove(u);
		}
	}
	
	public static CrawlerWorship getWorship(EntityPlayer p) {
		if(p == null) return null;
		return getWorship(p.getPersistentID());
	}
	
	public static CrawlerWorship getWorship(UUID u) {
		return worships.get(u);
	}
	
	public static void putWorship(EntityPlayer p, CrawlerWorship w) {
		putWorship(p.getPersistentID(), w);
	}
	
	public static void putWorship(UUID u, CrawlerWorship w) {
		worships.put(u, w);
	}
	
}
