package com.valeriotor.BTV.network;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.worship.Deities;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncDataToClient implements IMessage{
	
	public String string;
	public int value = -999;
	
	public MessageSyncDataToClient() {}
	
	public MessageSyncDataToClient(String string) {
		this.string = string;
	}
	
	public MessageSyncDataToClient(String string, int value) {
		this.string = string;
		this.value = value;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.string = ByteBufUtils.readUTF8String(buf);
		if(buf.isReadable()) this.value = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.string);
		if(this.value != -999) buf.writeInt(this.value);
		
	}
	
	public static class SyncDataToClientMessageHandler implements IMessageHandler<MessageSyncDataToClient, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncDataToClient message, MessageContext ctx) {
			if(message.string == null) return null;
			if(Minecraft.getMinecraft().player == null) {
				System.err.println("BTV sync error: null player. No data was lost, but it wasn't synchronized to client.");
				return null;
			}
			if(message.value == -999)
				Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString(message.string, false);
			else {
				if(message.string.equals("level")) Minecraft.getMinecraft().player.getCapability(Deities.map.get(Deities.GREATDREAMER.getKey()), null).setLevel(message.value);
				else Minecraft.getMinecraft().player.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(message.string, message.value, false);
			}
			return null;
		}
		
	}

}
