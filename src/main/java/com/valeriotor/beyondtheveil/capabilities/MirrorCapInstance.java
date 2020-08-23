package com.valeriotor.beyondtheveil.capabilities;

import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogueRegistry;
import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogueTemplate;

import net.minecraft.nbt.NBTTagCompound;

public class MirrorCapInstance {
	
	private MirrorDialogueTemplate defaultDialogue;
	private MirrorScheduler scheduledDialogue;
	
	public void setDefaultDialogue(String id) {
		defaultDialogue = MirrorDialogueRegistry.getDialogueTemplate(id);
	}
	
	public MirrorDialogueTemplate getDefaultDialogue() {
		if(defaultDialogue != null)
			return defaultDialogue;
		else {
			defaultDialogue = MirrorDialogueRegistry.getNoDialogueTemplate();
			return defaultDialogue;
		}
	}
	
	public void setScheduledDialogue(String id) {
		scheduledDialogue = new MirrorScheduler(id);
	}
	
	public boolean trySetTimedScheduledDialogue(String id) {
		if(scheduledDialogue == null) {
			scheduledDialogue = new MirrorSchedulerTimed(id);
			return true;
		}
		return false;
	}
	
	public MirrorDialogueTemplate getScheduledDialogue() {
		if(scheduledDialogue != null)
			return scheduledDialogue.template;
		else
			return null;
	}
	
	public void removeScheduledDialogue() {
		scheduledDialogue = null;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		if(defaultDialogue != null)
			nbt.setString("default", defaultDialogue.getID());
		if(scheduledDialogue != null)
			scheduledDialogue.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("default"))
			defaultDialogue = MirrorDialogueRegistry.getDialogueTemplate(nbt.getString("default"));
		if(nbt.hasKey("template_id"))
			scheduledDialogue = new MirrorScheduler(nbt);
	}
	
	private class MirrorScheduler {
		
		private final MirrorDialogueTemplate template;
		
		private MirrorScheduler(String id) {
			this.template = MirrorDialogueRegistry.getDialogueTemplate(id);
		}
		
		private MirrorScheduler(NBTTagCompound nbt) {
			this.template = MirrorDialogueRegistry.getDialogueTemplate(nbt.getString("template_id"));
		}
		
		protected void writeToNBT(NBTTagCompound nbt) {
			nbt.setString("template_id", template.getID());
		}
		
	}
	
	private class MirrorSchedulerTimed extends MirrorScheduler {
		
		private MirrorSchedulerTimed(String id) {
			super(id);
		}
		
		@Override
		protected void writeToNBT(NBTTagCompound nbt) {
			
		}
		
	}
	
}
