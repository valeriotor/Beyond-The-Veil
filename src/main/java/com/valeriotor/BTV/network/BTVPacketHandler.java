package com.valeriotor.BTV.network;

import com.valeriotor.BTV.lib.References;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BTVPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.MODID);
	private static int count = 0;
	
	public static void registerPackets() {
		INSTANCE.registerMessage(MessageSleepChamber.SleepChamberMessageHandler.class, MessageSleepChamber.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSetPosition.SetPositionMessageHandler.class, MessageSetPosition.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageOpenTradeGui.OpenTradeGuiMessageHandler.class, MessageOpenTradeGui.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageMedallionEffect.MedallionEffectMessageHandler.class, MessageMedallionEffect.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncAntiqueNBT.SyncAntiqueNBTMessageHandler.class, MessageSyncAntiqueNBT.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncDialogueData.SyncDialogueDataMessageHandler.class, MessageSyncDialogueData.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncDataToServer.SyncDataToServerMessageHandler.class, MessageSyncDataToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageWateryCradle.WateryCradleMessageHandler.class, MessageWateryCradle.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessagePlaySound.PlaySoundMessageHandler.class, MessagePlaySound.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncDataToClient.SyncDataToClientMessageHandler.class, MessageSyncDataToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageOpenGuiToClient.OpenGuiToClientMessageHandler.class, MessageOpenGuiToClient.class, count++, Side.CLIENT);
		
	}
	
}
