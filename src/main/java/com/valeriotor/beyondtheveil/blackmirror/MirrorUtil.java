package com.valeriotor.beyondtheveil.blackmirror;

import com.valeriotor.beyondtheveil.capabilities.MirrorCapInstance;
import com.valeriotor.beyondtheveil.capabilities.MirrorProvider;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.mirror.MessageMirrorDefaultToClient;
import com.valeriotor.beyondtheveil.network.mirror.MessageMirrorScheduledToClient;
import com.valeriotor.beyondtheveil.util.PlayerTimer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

public class MirrorUtil {
	
	public static void setScheduledDialogue(EntityPlayer p, String id) {
		ServerTickEvents.removePlayerTimer("timedmirror", p);
		getCap(p).setScheduledDialogue(id);
		BTVPacketHandler.INSTANCE.sendTo(new MessageMirrorScheduledToClient(id), (EntityPlayerMP)p);
	}
	private static final ItemStack MIRROR = new ItemStack(ItemRegistry.black_mirror);
	
	public static void trySetTimedScheduledDialogue(EntityPlayer p, String id, int time) {
		if(!p.inventory.hasItemStack(MIRROR)) return;
		
		if(getCap(p).trySetTimedScheduledDialogue(id)) {
			BTVPacketHandler.INSTANCE.sendTo(new MessageMirrorScheduledToClient(id), (EntityPlayerMP)p);
			PlayerTimer pt = new PlayerTimer.PlayerTimerBuilder(p)
										.setTimer(time)
										.setName("timedmirror")
										.addFinalAction(player -> player.sendMessage(new TextComponentTranslation("mirror.stopshivers")))
										.addFinalAction(MirrorUtil::removeScheduledDialogue)
										.toPlayerTimer();
			ServerTickEvents.addPlayerTimer(pt);
			p.sendMessage(new TextComponentTranslation("mirror.shivers"));
		}
	}
	
	public static void removeScheduledDialogue(EntityPlayer p) {
		getCap(p).removeScheduledDialogue();
		BTVPacketHandler.INSTANCE.sendTo(new MessageMirrorScheduledToClient(), (EntityPlayerMP)p);
	}
	
	public static void updateDefaultDialogue(EntityPlayer p, String id) {
		getCap(p).setDefaultDialogue(id);
		BTVPacketHandler.INSTANCE.sendTo(new MessageMirrorDefaultToClient(id), (EntityPlayerMP)p);
	}	
	
	public static MirrorDialogueTemplate getCurrentDialogue(EntityPlayer p) {
		MirrorDialogueTemplate scheduledDialogue = MirrorUtil.getCap(p).getScheduledDialogue();
		if(scheduledDialogue != null)
			return scheduledDialogue;
		else {
			MirrorDialogueTemplate defaultDialogue = MirrorUtil.getCap(p).getDefaultDialogue();
			return defaultDialogue;
		}
	}
	
	public static MirrorCapInstance getCap(EntityPlayer p) {
		return p.getCapability(MirrorProvider.MIRROR, null);
	}
}
