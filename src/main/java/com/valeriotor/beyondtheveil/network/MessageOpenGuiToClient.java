package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenGuiToClient implements IMessage{
	
	public String id;
	
	public MessageOpenGuiToClient() {}

	public MessageOpenGuiToClient(String id) {
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.id);
	}
	
	public static class OpenGuiToClientMessageHandler implements IMessageHandler<MessageOpenGuiToClient, IMessage>{

		@Override
		public IMessage onMessage(MessageOpenGuiToClient message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> BeyondTheVeil.proxy.openGui(message.id));
			return null;
		}
		
	}

}
