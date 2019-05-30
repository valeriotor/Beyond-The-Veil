package com.valeriotor.BTV.gui;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.valeriotor.BTV.capabilities.DGProvider;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSyncStringDataToServer;
import com.valeriotor.BTV.network.MessageSyncDialogueData;
import com.valeriotor.BTV.worship.Deities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

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
		
		Dialogues d = getDialogueName(prof);
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
		String prefix = "dweller."/*.concat(getFriendlyhood())*/.concat(profession);
		EntityPlayer p = Minecraft.getMinecraft().player;
		
		String dialogueName = getDialogueName(profession).getName();
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
		return I18n.format(getDialogue(profession, talkCount, branch)).concat(" ");
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
	@SideOnly(Side.CLIENT)
	public static Dialogues getDialogueName(String profession) {
		EntityPlayer p = Minecraft.getMinecraft().player;
		for(Dialogues d : Dialogues.values()) {
			if(d != Dialogues.FIRST && d.getProf().equals(profession) && p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("dialogue".concat(d.getName()))) {
				return d;
			}
		}
		
		return Dialogues.FIRST;
	}
	
	/** Finds a prefix for the Dialogue lang key based on the Player's Dreaming God level.
	 *  Used by non-Gui Dwellers.
	 * 
	 * @return A prefix that indicates how friendly Dwellers are to the Player.
	 */
	public static String getFriendlyhood(EntityPlayer p) {
		int level = Deities.GREATDREAMER.cap(p).getLevel();
		if(level > 5)
			return "friend";
		if(level > 3)
			return "trusted";
		if(level > 1)
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
		String dName = getDialogueName(profession).getName();
		for(Branches b : Branches.values()) {
			if(b.isCorrectBranch(dName, profession, oldBranch, option)) return b.getName();
		}
		
		return "";
	}
	
	/** Iterates through Dialogues to find a dialogue that can be unlocked (based on the "req" variables as
	 *  well as profession) or removed, based on arbitrary logic. If found it adds the Dialogue.getName() 
	 *  to the PlayerData's strings, both client- and server-side.
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
		String dialogueName = getDialogueName(profession).getName();
		for(Dialogues d : Dialogues.values()) {
			if(d.getProf().equals(profession) && d.canUnlock(dialogueName, branch, option, talkCount)) {
				Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialogue".concat(d.getName()), false);
				BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncDialogueData(d.getName(), false));
				return true;
				
			}
		}
		updateAdditionalData(profession, option, talkCount);
		return false;
	}
	
	private static void updateAdditionalData(String profession, int option, int talkCount) {
		if(getDialogueName(profession) == Dialogues.OCEAN && !ThaumcraftCapabilities.getKnowledge(Minecraft.getMinecraft().player).isResearchComplete("FISHINGHAMLET")) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncStringDataToServer(false, "LHKeeper"));
		}
	}
	
	
	public enum Dialogues{
		OCEAN("lhkeeper", 1, 1),
		GREATDREAMER("lhkeeper", 2, 2),
		DREAMER("lhkeeper", 3, 2),
		GRATITUDE("lhkeeper", 1, 2),
		LECTURE2("lhkeeper", 1, 3),
		LECTURE("lhkeeper", 2, 1),
		HASKNOWLEDGE("lhkeeper", 1, 1),
		LACKKNOWLEDGE("lhkeeper", 2, 5),
		FIRST("any", 0, 0);
		
		private int talkCount;
		private String prof;
		private int unlocks;
		
		private Dialogues(String prof, int num, int unlocks) {
			this.talkCount = num;
			this.prof = prof;
			this.unlocks = unlocks;
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
		
		public boolean canUnlock(String dialogue, String branch, int opt, int TC) {			
			for(int i = 0; i < this.unlocks; i++) {
				String name = this.getName().concat(String.valueOf(i));
				if(DialogueRequirement.getMap().containsKey(name)) {
					if(DialogueRequirement.getMap().get(name).canUnlock(dialogue, branch, opt, TC)) return true;
				}else {
					System.out.println("Warning: Dialogue Requirement not found. Report this to mod author.");
				}
			}
			return false;
		}
	}
	
	public enum Branches{
		LIES("lhkeeper", 1, "dreamer", "", 1),
		THANKS("lhkeeper", 1, "lecture2", "lecture", 1),
		FRIENDSLECTURE("lhkeeper", 1, "lecture2", "lecture", 0),
		LECTURE("lhkeeper", 10, "lecture2", "", 0),
		TELLME("lhkeeper", 2, "lecture", "", 0),
		FIEND("lhkeeper", 2, "lecture", "", 1),
		INHUMAN("lhkeeper", 3, "lecture", "fiend", 0),
		PREJUDICE("lhkeeper", 2, "lecture", "fiend", 1),
		HAMLETLIKE("lhkeeper", 1, "first", "", 0),
		HAMLETARCHITECTURE("lhkeeper", 2, "first", "hamletlike", 0),
		HAMLETLOOT("lhkeeper", 1, "first", "hamletlike", 1),
		HAMLETARTIFACTS("lhkeeper", 1, "first", "hamletloot", 0),
		HAMLETSLUGS("lhkeeper", 4, "first", "hamletloot", 1),
		HAMLETDISLIKE("lhkeeper", 1, "first", "", 1),
		HAMLETDWELLERS("lhkeeper", 1, "first", "hamletdislike", 0),
		HAMLETIDOL("lhkeeper", 3, "first", "hamletdislike", 1);
		
		private int talkCount;
		private String prof;
		private String reqDialogue;
		private String reqBranch;
		private int reqOpt;
		
		private Branches(String prof, int num, String reqDialogue, String reqBranch, int reqOpt) {
			this.talkCount = num;
			this.prof = prof;
			this.reqDialogue = reqDialogue;
			this.reqBranch = reqBranch;
			this.reqOpt = reqOpt;
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
		
		public boolean isCorrectBranch(String dialogue, String profession, String previousBranch, int option) {
			if(dialogue.equals(this.reqDialogue) && profession.equals(this.prof) && previousBranch.equals(this.reqBranch) && option == this.reqOpt) {
				return true;
			}
			return false;
		}
	}
	
	
	
}
