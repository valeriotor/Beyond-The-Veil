package com.valeriotor.beyondtheveil.network.baubles;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRevelationRingToClient implements IMessage{
	
	private int id;
	public MessageRevelationRingToClient() {}
	public MessageRevelationRingToClient(int id) {
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
	}
	
	public static class RevelationRingToClientMessageHandler implements IMessageHandler<MessageRevelationRingToClient, IMessage>{

		@Override
		public IMessage onMessage(MessageRevelationRingToClient message, MessageContext ctx) {
			BeyondTheVeil.proxy.renderEvents.invisibilificationator(message.id);
			return null;
		}
		
	}

}
