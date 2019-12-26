package com.valeriotor.BTV.dreaming;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public enum Memory {
	ANIMAL(Items.LEATHER, 0xFF401b00),
	CHANGE(Items.WHEAT_SEEDS, 0xFF4dff00),
	CRYSTAL(Item.getItemFromBlock(Blocks.GLASS), 0xFFe6d8d8),
	DARKNESS(Items.COAL, 0xFF000000),
	DEATH(Items.ROTTEN_FLESH, 0xFF2a2a2c),
	ELDRITCH(Items.ENDER_EYE, 0xFF400021),
	HUMAN(Items.ARMOR_STAND, 0xFFFFFFFF),
	LEARNING(Items.GHAST_TEAR, 0xFF998b69),
	METAL(Items.GOLD_NUGGET, 0xFF8c8c8c),
	POWER(Items.BLAZE_POWDER, 0xFFff9300),
	REPAIR(Item.getItemFromBlock(Blocks.WOOL), 0xFF99f19d),
	SENTIENCE(Items.BOOK, 0xFFd87474),
	STILLNESS(Item.getItemFromBlock(Blocks.SOUL_SAND), 0xFF444444),
	TOOL(Items.WOODEN_PICKAXE, 0xFF324eff),
	VOID(Item.getItemFromBlock(Blocks.OBSIDIAN), 0xFF000000);
	
	private Item item;
	private int color;
	private String[] reqs;
	
	private Memory(Item item, int color, String... reqs) {
		this.item = item;
		this.color = color;
		this.reqs = reqs;
	}
	
	public String getDataName() {
		return "mem".concat(this.name());
	}
	
	public boolean isUnlockable(EntityPlayer p) {
		if(reqs == null || reqs.length == 0) return false;
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(String s : reqs) {
			if(!data.getString(s)) return false;
		}
		return true;
	}
	
	public void unlock(EntityPlayer p) {
		SyncUtil.addStringDataOnServer(p, false, this.getDataName());		
	}
	
	public int getColor() {
		return this.color;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public static Memory getMemoryFromDataName(String key) {
		for(Memory m : Memory.values()) {
			if(key.equals(m.getDataName()))
				return m;
		}
		return null;
	}
	
}
