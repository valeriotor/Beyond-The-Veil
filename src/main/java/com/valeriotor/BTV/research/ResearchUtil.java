package com.valeriotor.BTV.research;

import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.research.MessageSyncResearchToClient;
import com.valeriotor.BTV.network.research.MessageSyncResearchToServer;
import com.valeriotor.BTV.network.research.ResearchSyncer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ResearchUtil {
	
	public static void progressResearchServer(EntityPlayer p, String key) {
		p.getCapability(ResearchProvider.RESEARCH, null).getResearch(key).progressStage(p);
		BTVPacketHandler.INSTANCE.sendTo(new MessageSyncResearchToClient(new ResearchSyncer(key).setProgress(true)), (EntityPlayerMP)p);
	}
	
	public static void progressResearchClient(EntityPlayer p, String key) {
		progressResearch(p, key);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncResearchToServer(new ResearchSyncer(key).setProgress(true)));
	}
	
	public static void progressResearch(EntityPlayer p, String key) {
		p.getCapability(ResearchProvider.RESEARCH, null).getResearch(key).progressStage(p);		
	}
	
	public static void learnResearchServer(EntityPlayer p, String key) {
		learnResearch(p, key);
		BTVPacketHandler.INSTANCE.sendTo(new MessageSyncResearchToClient(new ResearchSyncer(key).setLearn(true)), (EntityPlayerMP)p);
	}
	
	public static void learnResearchClient(EntityPlayer p, String key) {
		learnResearch(p, key);
		BTVPacketHandler.INSTANCE.sendToServer(new MessageSyncResearchToServer(new ResearchSyncer(key).setLearn(true)));
	}
	
	public static void learnResearch(EntityPlayer p, String key) {
		p.getCapability(ResearchProvider.RESEARCH, null).getResearch(key).learn();
	}
	
	
	/*public static boolean knowsResearch(EntityPlayer p, String key) {
		return false;
	}*/
	
	
	
}
