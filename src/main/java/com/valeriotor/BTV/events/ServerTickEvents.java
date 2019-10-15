package com.valeriotor.BTV.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.valeriotor.BTV.events.special.DrowningRitualEvents;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageCovenantData;
import com.valeriotor.BTV.util.DelayedMessage;
import com.valeriotor.BTV.util.PlayerTimer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

@Mod.EventBusSubscriber
public class ServerTickEvents {
	
	
	@SubscribeEvent
	public static void serverTickEvent(ServerTickEvent e) {
		decreaseMessageTimers();
		decreaseCovenantTimers();
		DrowningRitualEvents.update();
	}
	

	// ***************************************** DELAYED MESSAGES ***************************************** \\
	private static List<DelayedMessage> messages = new ArrayList<>();
	
	private static void decreaseMessageTimers() {
		if(messages.isEmpty()) return;
		Iterator<DelayedMessage> iter = messages.iterator();
		while(iter.hasNext()) {
			if(iter.next().update()) iter.remove();
		}
	}
	
	public static void addMessage(DelayedMessage dm) {
		messages.add(dm);
	}
	
	public static void removeMessage(DelayedMessage dm) {
		messages.remove(dm);
	}
	
	public static void removeMessage(EntityPlayer p) {
		if(messages.isEmpty()) return;
		Iterator<DelayedMessage> iter = messages.iterator();
		while(iter.hasNext()) {
			DelayedMessage dm = iter.next();
			if(dm.receiver == p) iter.remove();
		}
	}
	
	// ******************************************* BLOOD COVENANT ***************************************** \\
	

	private static List<PlayerTimer> covenants = new ArrayList<>();
	
	private static void decreaseCovenantTimers() {
		if(covenants.isEmpty()) return;
		Iterator<PlayerTimer> iterator = covenants.iterator();
		while(iterator.hasNext()) {
			PlayerTimer ct = iterator.next();
			if(ct.update()) {
				iterator.remove();
				BTVPacketHandler.INSTANCE.sendTo(new MessageCovenantData(true, null), (EntityPlayerMP)ct.player);
			}
		}
	}
	
	public static void addCovenantTimer(PlayerTimer ct) {
		if(covenants.contains(ct)) covenants.remove(ct);
		covenants.add(ct);
	}
	
	public static boolean containsCovenantTimer(EntityPlayer p) {
		for(PlayerTimer ct : covenants) {
			if(ct.player.equals(p)) return true;
		}
		return false;
	}
	
	public static boolean removeCovenantTimer(EntityPlayer p) {
		if(covenants.isEmpty()) return false;		
		Iterator<PlayerTimer> iterator = covenants.iterator();
		while(iterator.hasNext()) {
			if(iterator.next().player == p) {
				iterator.remove();
				BTVPacketHandler.INSTANCE.sendTo(new MessageCovenantData(true, null), (EntityPlayerMP)p);
				return true;
			}
		}
		return false;
	}
	
	// **************************************************************************************************** \\
	
}
