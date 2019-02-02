package com.valeriotor.BTV.network;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncDialogueData implements IMessage{
	
	public String dialogue;
	
	public MessageSyncDialogueData() {}
	
	public MessageSyncDialogueData(String dialogue) {
		this.dialogue = dialogue;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.dialogue = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.dialogue);
	}
	
	public static class SyncDialogueDataMessageHandler implements IMessageHandler<MessageSyncDialogueData, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncDialogueData message, MessageContext ctx) {
			ctx.getServerHandler().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(message.dialogue, false);
			return null;
		}
		
	}

}
