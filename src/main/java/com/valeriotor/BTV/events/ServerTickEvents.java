package com.valeriotor.BTV.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.valeriotor.BTV.util.DelayedMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

@Mod.EventBusSubscriber
public class ServerTickEvents {
	
	
	@SubscribeEvent
	public static void serverTickEvent(ServerTickEvent e) {
		Iterator<DelayedMessage> iter = messages.iterator();
		while(iter.hasNext()) {
			if(iter.next().update()) iter.remove();
		}
	}
	

	// ***************************************** DELAYED MESSAGES ***************************************** \\
	private static List<DelayedMessage> messages = new ArrayList<>();
	
	public static void addMessage(DelayedMessage dm) {
		messages.add(dm);
	}
	
	public static void removeMessage(DelayedMessage dm) {
		messages.remove(dm);
	}
	
	public static void removeMessage(EntityPlayer p) {
		Iterator<DelayedMessage> iter = messages.iterator();
		while(iter.hasNext()) {
			DelayedMessage dm = iter.next();
			if(dm.receiver == p) iter.remove();
		}
	}
	
	// **************************************************************************************************** \\
	
}
