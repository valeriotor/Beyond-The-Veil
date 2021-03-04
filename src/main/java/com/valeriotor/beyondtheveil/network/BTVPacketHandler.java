package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.baubles.MessageRevelationRingToClient;
import com.valeriotor.beyondtheveil.network.baubles.MessageRevelationRingToServer;
import com.valeriotor.beyondtheveil.network.baubles.MessageWolfMedallionToClient;
import com.valeriotor.beyondtheveil.network.mirror.MessageMirrorDefaultToClient;
import com.valeriotor.beyondtheveil.network.mirror.MessageMirrorScheduledToClient;
import com.valeriotor.beyondtheveil.network.mirror.MessageMirrorToServer;
import com.valeriotor.beyondtheveil.network.research.MessageSyncResearchToClient;
import com.valeriotor.beyondtheveil.network.research.MessageSyncResearchToServer;
import com.valeriotor.beyondtheveil.network.ritual.MessagePerformHurtAnimation;
import com.valeriotor.beyondtheveil.network.ritual.MessageRitualToClient;
import com.valeriotor.beyondtheveil.network.ritual.MessageRitualToServer;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BTVPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.MODID);
	private static int count = 0;
	
	public static void registerPackets() {
		INSTANCE.registerMessage(MessageSleepChamber.SleepChamberMessageHandler.class, MessageSleepChamber.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageOpenTradeGui.OpenTradeGuiMessageHandler.class, MessageOpenTradeGui.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncAntiqueNBT.SyncAntiqueNBTMessageHandler.class, MessageSyncAntiqueNBT.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncDialogueData.SyncDialogueDataMessageHandler.class, MessageSyncDialogueData.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncStringDataToServer.SyncDataToServerMessageHandler.class, MessageSyncStringDataToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncIntDataToServer.SyncIntDataToServerMessageHandler.class, MessageSyncIntDataToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageActivateBauble.ActivateBaubleMessageHandler.class, MessageActivateBauble.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageActivatePower.ActivatePowerMessageHandler.class, MessageActivatePower.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageWateryCradle.WateryCradleMessageHandler.class, MessageWateryCradle.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageGiveDrink.GiveItemMessageHandler.class, MessageGiveDrink.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageCityMapper.CityMapperMessageHandler.class, MessageCityMapper.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageRitualToServer.RitualToServerMessageHandler.class, MessageRitualToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSawCleaverToServer.SawCleaverToServerMessageHandler.class, MessageSawCleaverToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageDagonDialogue.DagonDialogueMessageHandler.class, MessageDagonDialogue.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageRevelationRingToServer.RevelationRingToServerMessageHandler.class, MessageRevelationRingToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSyncResearchToServer.SyncResearchToServerMessageHandler.class, MessageSyncResearchToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageSurgeon.SurgeonMessageHandler.class, MessageSurgeon.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageMirrorToServer.MirrorToServerMessageHandler.class, MessageMirrorToServer.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessageArenaFight.ArenaFightMessageHandler.class, MessageArenaFight.class, count++, Side.SERVER);
		INSTANCE.registerMessage(MessagePlaySound.PlaySoundMessageHandler.class, MessagePlaySound.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncDataToClient.SyncDataToClientMessageHandler.class, MessageSyncDataToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageOpenGuiToClient.OpenGuiToClientMessageHandler.class, MessageOpenGuiToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageReloadResources.ReloadResourcesMessageHandler.class, MessageReloadResources.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessagePlayerAnimation.PlayerAnimationMessageHandler.class, MessagePlayerAnimation.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessagePerformHurtAnimation.PerformHurtAnimationMessageHandler.class, MessagePerformHurtAnimation.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageCovenantData.CovenantDataMessageHandler.class, MessageCovenantData.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageRevelationRingToClient.RevelationRingToClientMessageHandler.class, MessageRevelationRingToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageWolfMedallionToClient.WolfMedallionToClientMessageHandler.class, MessageWolfMedallionToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageCameraRotatorClient.CameraRotatorClientMessageHandler.class, MessageCameraRotatorClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageEntityAnimation.EntityAnimationMessageHandler.class, MessageEntityAnimation.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageRemoveStringToClient.RemoveStringToClientMessageHandler.class, MessageRemoveStringToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncResearchToClient.SyncResearchToClientMessageHandler.class, MessageSyncResearchToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageStepAssist.StepAssistMessageHandler.class, MessageStepAssist.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageOpenDialogue.OpenDialogueMessageHandler.class, MessageOpenDialogue.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageRitualToClient.RitualToClientMessageHandler.class, MessageRitualToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSawCleaverToClient.SawCleaverToClientMessageHandler.class, MessageSawCleaverToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageMovePlayer.MovePlayerMessageHandler.class, MessageMovePlayer.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncPlayerRender.SyncPlayerRenderMessageHandler.class, MessageSyncPlayerRender.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSurgeonToClient.SurgeonToClientMessageHandler.class, MessageSurgeonToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageMirrorScheduledToClient.MirrorScheduledToClientMessageHandler.class, MessageMirrorScheduledToClient.class, count++, Side.CLIENT);
		INSTANCE.registerMessage(MessageMirrorDefaultToClient.MirrorDefaultToClientMessageHandler.class, MessageMirrorDefaultToClient.class, count++, Side.CLIENT);
		
	}
	
}
