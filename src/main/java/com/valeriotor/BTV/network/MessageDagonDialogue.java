package com.valeriotor.BTV.network;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.ThaumcraftApi;

public class MessageDagonDialogue implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class DagonDialogueMessageHandler implements IMessageHandler<MessageDagonDialogue, IMessage>{

		@Override
		public IMessage onMessage(MessageDagonDialogue message, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().player;
			IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
			int value = data.getOrSetInteger(PlayerDataLib.DAGON_DIALOGUE, 0, false);
			switch(value) {
			case 0:
				data.incrementOrSetInteger(PlayerDataLib.DAGON_DIALOGUE, 1, 0, false);
				ThaumcraftApi.internalMethods.progressResearch(p, "hearing");
				break;
			}
			return null;
		}
		
	}
	
}
