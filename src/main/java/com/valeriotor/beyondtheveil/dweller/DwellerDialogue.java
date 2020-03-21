package com.valeriotor.beyondtheveil.dweller;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.entities.EntityHamletDweller;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageGiveDrink;
import com.valeriotor.beyondtheveil.network.MessageSyncDialogueData;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class DwellerDialogue {
	public static DwellerDialogue instance;
	
	public static void newInstance(int dwellerId) {
		instance = new DwellerDialogue(dwellerId);
	}
	public static void removeInstance() {
		instance = null;
	}
	
	private Dialogues dialogue;
	private Branches branch;
	private final EntityHamletDweller dweller;
	private final Minecraft mc;
	
	public DwellerDialogue(int id) {
		this.dweller = (EntityHamletDweller) Minecraft.getMinecraft().world.getEntityByID(id);
		this.mc = Minecraft.getMinecraft();
		this.dialogue = getFirstDialogue();
	}
	
	public int getDwellerID() {
		return this.dweller.getEntityId();
	}
	
	public Dialogues getDialogue() {
		return this.dialogue;
	}
	
	private Dialogues getFirstDialogue() {
		String prof = getProfession().name().toLowerCase();
		IPlayerData data = mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null);
		for(Dialogues d : Dialogues.values()) {
			if(d != Dialogues.FIRST && d.getProf().equals(prof) && data.getString("dialogue".concat(d.getLowerCaseName()))) {
				return d;
			}
		}
		
		return Dialogues.FIRST;
	}
	
	private String getDialogueLangKey(int talkCount) {
		String prefix = "dweller.".concat(this.getProfession().name().toLowerCase());
		
		String dialogueName = this.dialogue.getLowerCaseName();
		if(!dialogueName.equals("first")) prefix = prefix.concat(dialogueName);
		
		prefix = prefix.concat(".");
		if(this.branch != null)
			prefix = prefix.concat(branch.getLowerCaseName());
		
		prefix = prefix.concat(String.format("talk%d", talkCount));
		
		return prefix;
	}
	
	public int getTalkCount() {
		int a = getBranchTalkCount();
		if(a != -1) return a;
		if(dialogue != Dialogues.FIRST) return dialogue.getTalkCount();
		return dweller.getProfession().getTalkCount();
	}
	
	public int getBranchTalkCount() {
		if(branch != null)
			return branch.getTalkCount();
		return -1;
	}
	
	public EntityHamletDweller.ProfessionsEnum getProfession() {
		return dweller.getProfession();
	}
	
	public String getLocalizedDialogue(int talkCount) {
		return I18n.format(this.getDialogueLangKey(talkCount)).concat(" ");
	}
	
	public String getLocalizedDialogueOption(int talkCount, int option) {
		String string = I18n.format(String.format(this.getDialogueLangKey(talkCount).concat(".option%d"), option));
		if(string.length() > 6) {
			if(string.substring(0, 7).equals("dweller")) string = null;
			else string = string.concat(" ");
		}
		return string;
	}
	
	public boolean updateDialogueData(int talkCount, int option) {
		String prof = getProfession().name().toLowerCase();
		String currentName = this.dialogue.getLowerCaseName();
		String oldBranch = this.branch == null ? "" : this.branch.getLowerCaseName();
		performAdditionalEffects(option, talkCount);
		updateAdditionalData(option, talkCount);
		for(Dialogues d : Dialogues.values()) {
			if(d.getProf().equals(prof) && d.canUnlock(this.dialogue.getLowerCaseName(), oldBranch, option, talkCount)) {
				Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialogue".concat(d.getLowerCaseName()), false);
				BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncDialogueData(d.getLowerCaseName(), false));
				this.dialogue = d;
				this.branch = null;
				return true;
				
			}
		}
		if(option != -1) {
			for(Branches b : Branches.values()) {
				if(b.isCorrectBranch(currentName, prof, oldBranch, option)) {
					this.branch = b;
					return true;
				}
			}
		}
		if((branch != null && talkCount >= this.branch.getTalkCount())) {
			this.branch = null;
			return true;
		}
		return false;
	}
	
	private void updateAdditionalData(int option, int talkCount) {
		if(this.dialogue == Dialogues.OCEAN && !ResearchUtil.isResearchComplete(mc.player, "FISHINGHAMLET")) {
			SyncUtil.addStringDataOnClient(mc.player, false, "LHKeeper");
		} else if(this.dialogue == Dialogues.EASYJOB && ResearchUtil.getResearchStage(mc.player, "CANOE") == 0){
			SyncUtil.addStringDataOnClient(mc.player, false, "carpenter");
		} else if(this.dialogue == Dialogues.RITUAL && ResearchUtil.getResearchStage(mc.player, "BAPTISM") == 0 && talkCount == 6){
			SyncUtil.addStringDataOnClient(mc.player, false, "lhbaptism");
		} else if(this.dialogue == Dialogues.PAST){
			removeDialogue(Dialogues.IKNOW);
			removeDialogue(Dialogues.OLDTRUTH);
			removeDialogue(Dialogues.PAST);
		}
	}
	
	private void performAdditionalEffects(int option, int talkCount) {
		if(dialogue == Dialogues.TRUSTEDBAR && option == 0) BTVPacketHandler.INSTANCE.sendToServer(new MessageGiveDrink());
		else if(dialogue == Dialogues.WEEPER && talkCount == 4) Minecraft.getMinecraft().player.playSound(BTVSounds.weeper_idle, 1, 1);
	}
	
	private void removeDialogue(Dialogues d) {
		Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString("dialogue".concat(d.getLowerCaseName()));
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncDialogueData(d.getLowerCaseName(), true));
	}
	
	public boolean doesCloseDialogue(int talkCount, int option) {
		if(this.dialogue == Dialogues.LECTURE2 && this.branch == null && option == 1) return true;
		if(this.dialogue == Dialogues.TRUSTEDBAR && option == 1) return true;
		if(this.dialogue == Dialogues.END2) return true;
		if(this.dialogue == Dialogues.SEEYA || this.dialogue == Dialogues.SEEYA2 || this.dialogue == Dialogues.SEEYA3) return true;
		if(this.dialogue == Dialogues.WEEPER && talkCount == 3) return true;
		return false;
	}
}
