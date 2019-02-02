package com.valeriotor.BTV.gui;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityHamletDweller;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DialogueHandler {
	
	private static HashMap<UUID, EntityHamletDweller> map = new HashMap<>();
	
	public static void newDialogue(EntityPlayer p, EntityHamletDweller e) {
		map.put(p.getUniqueID(), e);
	}
	
	public static void removeDialogue(EntityPlayer p) {
		map.remove(p.getUniqueID());
	}
	
	public static EntityHamletDweller getDweller(EntityPlayer p) {
		return map.get(p.getUniqueID());
	}
	
	public static String getProfession(EntityPlayer p) {
		return map.get(p.getUniqueID()).getProfession().getName().toLowerCase();
	}
	
	public static EntityPlayer getPlayer(EntityHamletDweller e) {
		for(Entry<UUID, EntityHamletDweller> entry : map.entrySet()) {
			if(entry.getValue().equals(e))  return e.world.getPlayerEntityByUUID(entry.getKey());
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public static int getTalkCount(String branch) {
		EntityPlayer p = Minecraft.getMinecraft().player;
		EntityHamletDweller e = getDweller(p);
		String prof = getProfession(p);
		int a = getBranchTalkCount(prof, branch);
		if(a != -1) return a;
		
		Dialogues d = getDialogueName(p, prof);
		if(!d.getName().equals("first")) return d.getTalkCount();
		
		return e.getProfession().getTalkCount();
		
	}
	
	@SideOnly(Side.CLIENT)
	public static String getDialogue(String profession, int talkCount, String branch) {
		String prefix = "dweller.".concat(getFriendlyhood()).concat(profession);
		EntityPlayer p = Minecraft.getMinecraft().player;
		
		String dialogueName = getDialogueName(p, profession).getName();
		if(!dialogueName.equals("first")) prefix = prefix.concat(dialogueName);
		
		prefix = prefix.concat(".");
		
		for(Branches b : Branches.values()) {
			if(b.getProf().equals(profession) && b.getName().equals(branch)) {
				prefix = prefix.concat(b.getName());
				break;
			}
		}
		
		prefix = prefix.concat(String.format("talk%d", talkCount));
		
		return prefix;
		
	}
	
	@SideOnly(Side.CLIENT)
	public static String getLocalizedDialogue(String profession, int talkCount, String branch) {
		return I18n.format(getDialogue(profession, talkCount, branch));
	}
	
	@SideOnly(Side.CLIENT)
	public static String getLocalizedDialogueOption(String profession, int talkCount, int id, String branch) {
		String string = I18n.format(String.format(getDialogue(profession, talkCount, branch).concat(".option%d"), id));
		if(string.length() > 6)
			if(string.substring(0, 7).equals("dweller")) string = null;
		
		return string;
	}
	
	@SideOnly(Side.CLIENT)
	public static int getBranchTalkCount(String profession, String branch) {
		for(Branches b : Branches.values()) {
			if(b.getName().equals(branch) && b.getProf().equals(profession)) return b.getTalkCount();
		}
		
		return -1;
	}
	
	private static Dialogues getDialogueName(EntityPlayer p, String profession) {
		for(Dialogues d : Dialogues.values()) {
			if(d.getProf().equals(profession) && p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(d.getName())) {
				return d;
			}
		}
		
		return Dialogues.FIRST;
	}
	
	private static String getFriendlyhood() {
		int level = Minecraft.getMinecraft().player.getCapability(DGProvider.LEVEL_CAP, null).getLevel();
		if(level > 10)
			return "friend";
		if(level > 5)
			return "trusted";
		if(level > 2)
			return "tolerated";
		
		return "";
		
	}
	
	
	// TODO Method to sync data, whether to add or remove dialogue to PlayerData
	
	
	private enum Dialogues{
		MET("lhkeeper", 2),
		FIRST("any", 0);
		
		private int talkCount;
		private String prof;
		
		private Dialogues(String prof, int num) {
			this.talkCount = num;
			this.prof = prof;
		}
		
		public int getTalkCount() {
			return this.talkCount;
		}
		
		public String getProf() {
			return this.prof;
		}
		
		public String getName() {
			return this.name().toLowerCase();
		}
	}
	
	public enum Branches{
		TEST1("lhkeeper", 1),
		TEST2("lhkeeper", 2),
		GENOCIDEAGREE("lhkeeper", 1),
		GENOCIDEDISAGREE("lhkeeper", 1);
		
		private int talkCount;
		private String prof;
		
		private Branches(String prof, int num) {
			this.talkCount = num;
			this.prof = prof;
		}
		
		public int getTalkCount() {
			return this.talkCount;
		}

		public String getProf() {
			return this.prof;
		}
		
		public String getName() {
			return this.name().toLowerCase();
		}
	}
	
	
	@SideOnly(Side.CLIENT)
	public static String getBranch(String profession, String oldBranch, int option) {
		String dName = getDialogueName(Minecraft.getMinecraft().player, profession).getName();
		if(dName.equals("first") && profession.equals("lhkeeper")) {
			if(oldBranch.equals(""))
			return option == 0 ? Branches.GENOCIDEDISAGREE.getName() : Branches.GENOCIDEAGREE.getName();
			if(oldBranch.equals("genocidedisagree")) {
				return option == 0 ? Branches.TEST1.getName() : Branches.TEST2.getName();
			}
		}
		
		return "";
	}
	
	
}
