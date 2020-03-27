package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSawCleaverToClient implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
	
	public static class SawCleaverToClientMessageHandler implements IMessageHandler<MessageSawCleaverToClient, IMessage> {

		@Override
		public IMessage onMessage(MessageSawCleaverToClient message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() ->	BeyondTheVeil.proxy.cEvents.movePlayer());
			return null;
		}
		
	}

}
