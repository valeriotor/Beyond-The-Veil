package com.valeriotor.BTV.network;

import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.ThaumcraftApi;

public class MessageSyncDataToServer implements IMessage{

	private boolean isBTV;
	private String[] keys;
	
	public MessageSyncDataToServer() {}
	

	public MessageSyncDataToServer(boolean isBTV, String... keys) {
		this.isBTV = isBTV;
		this.keys = keys;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		this.isBTV = buf.readBoolean();
		List<String> list = Lists.newArrayList();
		while(buf.isReadable())
			list.add(ByteBufUtils.readUTF8String(buf));
		this.keys = new String[list.size()];
		for(int i = 0; i < list.size(); i++)
			this.keys[i] = list.get(i);
		
	}


	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.isBTV);
		for(String key : keys)
			ByteBufUtils.writeUTF8String(buf, key);
	}
	
	public static class SyncDataToServerMessageHandler implements IMessageHandler<MessageSyncDataToServer, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncDataToServer message, MessageContext ctx) {
			for(String s : message.keys) {
				if(!message.isBTV)
					ThaumcraftApi.internalMethods.progressResearch(ctx.getServerHandler().player, s);
				else
					ctx.getServerHandler().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(s, false);
			}
			return null;
		}
		
	}
	
}
