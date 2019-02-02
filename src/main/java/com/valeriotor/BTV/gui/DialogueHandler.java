package com.valeriotor.BTV.gui;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncDialogueData;

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
	
	/** Method that returns number of lines a dialogue has. It gets the HamletDweller directly from the map.
	 * 	First checks for Branches, if the corresponding one isn't found it looks for Dialogues. 
	 *  If those aren't found either then it returns the TalkCount in EntityHamletDweller.ProfessionsEnum
	 *  
	 * 
	 * @param branch The branch that GuiDialogueDweller is currently in. Can be empty: "", indicating it's in the Main Dialogue. 
	 * @return The number of lines the current dialogue has.
	 */
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
	
	/** Returns the lang key for the line of dialogue based on current data.
	 * 
	 * @param profession The Dweller's profession
	 * @param talkCount The current line of dialogue
	 * @param branch The current dialogue branch
	 * @return The unlocalized line of dialogue to display
	 */
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
	
	/** Calls GuiDialogueDweller::getDialogue and localizes it
	 * 
	 * @param profession The Dweller's profession
	 * @param talkCount The current line of dialogue
	 * @param branch The current dialogue branch
	 * 
	 * @return The localized line of dialogue to display
	 */
	@SideOnly(Side.CLIENT)
	public static String getLocalizedDialogue(String profession, int talkCount, String branch) {
		return I18n.format(getDialogue(profession, talkCount, branch));
	}
	
	/** Looks for a DialogueOption in the lang file, and attempts localization. If it fails, it
	 *  returns null so that no dialogue options popup (due to a null check in GuiDialogueDweller).
	 * 
	 * @param profession The Dweller's profession
	 * @param talkCount The current line of dialogue
	 * @param id 0 or 1 based on left or right option
	 * @param branch The current dialogue branch
	 * 
	 * @return The localized dialogue option to display, or null if not found
	 */
	@SideOnly(Side.CLIENT)
	public static String getLocalizedDialogueOption(String profession, int talkCount, int id, String branch) {
		String string = I18n.format(String.format(getDialogue(profession, talkCount, branch).concat(".option%d"), id));
		if(string.length() > 6)
			if(string.substring(0, 7).equals("dweller")) string = null;
		
		return string;
	}
	
	/** Method that iterates the Branches enum to check for the specified {@code branch}. If found,
	 *  it returns its talk count, otherwise it returns -1.
	 *  
	 * @param profession The Dweller's profession
	 * @param branch The specified dialogue branch
	 * 
	 * @return The talkCount for that Branch, or -1 if not found.
	 */
	@SideOnly(Side.CLIENT)
	public static int getBranchTalkCount(String profession, String branch) {
		for(Branches b : Branches.values()) {
			if(b.getName().equals(branch) && b.getProf().equals(profession)) return b.getTalkCount();
		}
		
		return -1;
	}
	
	/** Finds the name suffix for the dialogue lang key. Iterates the Dialogues enum from top to bottom 
	 *  and checks for the first match. To be matched two things are required: the right profession and
	 *  that the dialogue's name is included in the Player's capability (this means there is a dialogue 
	 *  progression, as new dialogues will lock older ones unless removed). If no match is found, then
	 *  {@code Dialogues.FIRST} is returned, and the getDialogue method checks for that so that no suffix
	 *  is added.
	 * 
	 * @param p The Player
	 * @param profession The Dweller's profession
	 * 
	 * @return The found dialogue
	 */
	private static Dialogues getDialogueName(EntityPlayer p, String profession) {
		for(Dialogues d : Dialogues.values()) {
			if(d != Dialogues.FIRST && d.getProf().equals(profession) && p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString(d.getName())) {
				return d;
			}
		}
		
		return Dialogues.FIRST;
	}
	
	/** Currently unused, not sure if it'll ever be used. Finds a prefix for the Dialogue
	 *  lang key based on the Player's Dreaming God level.
	 * 
	 * @return A prefix that indicates how friendly Dwellers are to the Player.
	 */
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
	
	/** Gets a new branch based on the current branch and the selected option. Currently not automated,
	 *  and relies on arbitrary logic.
	 * 
	 * @param profession The Dweller's profession
	 * @param oldBranch The current branch
	 * @param option The selected option
	 * 
	 * @return The name of the new branch (aka Branch.getName())
	 */
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
	
	/** Iterates through Dialogues to find a dialogue that can be unlocked, based on the "req" variables,
	 *  as well as profession. If found it adds the Dialogue.getName() to the PlayerData's strings, both client-
	 *  and server-side.
	 * 
	 * @param profession The Dweller's profession
	 * @param branch The current branch
	 * @param option The selected option (-1, 0 or 1)
	 * @param talkCount The current talkCount (ignored unless {@code option} is -1)
	 * 
	 * @return True if an unlockable dialogue was found, false otherwise
	 */
	@SideOnly(Side.CLIENT)
	public static boolean updateDialogueData(String profession, String branch, int option, int talkCount) {
		for(Dialogues d : Dialogues.values()) {
			if(d.getProf().equals(profession) && d.getUnlockBranch().equals(branch)) {
				if(option == d.getUnlockOption() || (option == -1 && talkCount == d.getUnlockTalkCount())) {
					Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(d.getName(), false);
					BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncDialogueData(d.getName()));
					System.out.println("SUCCESS!!");
					return true;
				}
			}
		}
		return false;
	}
	
	
	private enum Dialogues{
		MET("lhkeeper", 2, Branches.GENOCIDEDISAGREE.getName(), 0, 0),
		FIRST("any", 0, "", 0, 0);
		
		private int talkCount;
		private String prof;
		private String reqBranch;
		private int reqOpt;
		private int reqTC;
		
		private Dialogues(String prof, int num, String reqBranch, int reqOpt, int reqTC) {
			this.talkCount = num;
			this.prof = prof;
			this.reqBranch = reqBranch;
			this.reqOpt = reqOpt;
			this.reqTC = reqTC;
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
		
		public String getUnlockBranch() {
			return this.reqBranch;
		}
		
		public int getUnlockOption() {
			return this.reqOpt;
		}
		
		public int getUnlockTalkCount() {
			return this.reqTC;
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
	
	
	
}
