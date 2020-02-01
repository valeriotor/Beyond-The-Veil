package com.valeriotor.BTV.dreaming;

import java.util.Map.Entry;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.research.Research.SubResearch;
import com.valeriotor.BTV.research.ResearchStatus;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

public enum Memory {
	ANIMAL(Items.LEATHER, 0xFF401b00),
	CHANGE(Items.WHEAT_SEEDS, 0xFF4dff00, "metalDream"),
	CRYSTAL(Item.getItemFromBlock(Blocks.GLASS), 0xFFe6d8d8),
	DARKNESS(Items.COAL, 0xFF000000),
	DEATH(Items.ROTTEN_FLESH, 0xFF2a2a2c),
	ELDRITCH(Items.ENDER_EYE, 0xFF400021),
	HUMAN(Items.ARMOR_STAND, 0xFFFFFFFF, "metalDream"),
	LEARNING(Items.GHAST_TEAR, 0xFF998b69),	
	METAL(Items.IRON_INGOT, 0xFF8c8c8c),
	POWER(Items.BLAZE_POWDER, 0xFFff9300, "metalDream"),
	REPAIR(Item.getItemFromBlock(Blocks.WOOL), 0xFF99f19d),
	SENTIENCE(Items.BOOK, 0xFFd87474, "metalDream"),
	STILLNESS(Item.getItemFromBlock(Blocks.SOUL_SAND), 0xFF444444, "metalDream"),
	TOOL(Items.WOODEN_PICKAXE, 0xFF324eff, "memHUMAN"),
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
		if(reqs == null || reqs.length == 0) return true;
		IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(String s : reqs) {
			if(!data.getString(s)) return false;
		}
		return true;
	}
	
	public boolean isUnlocked(EntityPlayer p) {
		return p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(this.getDataName());
	}
	
	public void unlock(EntityPlayer p) {
		this.unlock(p, true);
	}
	
	public void unlock(EntityPlayer p, boolean sendMessage) {
		if(!this.isUnlocked(p) && this.isUnlockable(p)) {
			String dataName = this.getDataName();
			SyncUtil.addStringDataOnServer(p, false, dataName);	 
			String s = getFurtherData(this);
			if(s != null) SyncUtil.addStringDataOnServer(p, false, s);
			if(sendMessage) {
			p.sendMessage(new TextComponentTranslation("memory.unlock.message", this.getLocalizedName()));
				for(Entry<String,ResearchStatus> entry : p.getCapability(ResearchProvider.RESEARCH, null).getResearches().entrySet()) {
					for(SubResearch addendum : entry.getValue().res.getAddenda()) {
						for(String req : addendum.getRequirements()) {
							if(req.equals(dataName)) {
								p.sendMessage(new TextComponentTranslation("memory.unlock.addenda", new TextComponentTranslation(entry.getValue().res.getName()).getFormattedText()));
								return;
							}
						}
					}
				}
			}
		}
	}
	
	public int getColor() {
		return this.color;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public String getLocalizedKey() {
		return "memory.".concat(this.name().toLowerCase().concat(".name"));
	}
	
	public String getLocalizedName() {
		return new TextComponentTranslation(getLocalizedKey()).getFormattedText();
	}
	
	public static Memory getMemoryFromDataName(String key) {
		for(Memory m : Memory.values()) {
			if(key.equals(m.getDataName()))
				return m;
		}
		return null;
	}
	
	public static String getFurtherData(Memory m) {
		switch(m) {
		case ANIMAL: 	return null;
		case CHANGE: 	return "effectDream";
		case CRYSTAL:	return null;
		case DARKNESS:	return null;
		case DEATH:		return null;
		case ELDRITCH:	return null;
		case HUMAN:		return null;
		case LEARNING:	return null;
		case METAL:		return null;
		case POWER:		return "effectDream";
		case REPAIR:	return null;
		case SENTIENCE:	return null;
		case STILLNESS:	return "effectDream";
		case TOOL:		return null;
		case VOID:		return null;
		default: return null;
		
		}
	}
	
}
