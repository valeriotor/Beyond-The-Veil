package com.valeriotor.beyondtheveil.network.ritual;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.gui.Guis;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRitualToClient implements IMessage{
	private byte phase;
	private boolean greatDreamer;
	private boolean ancientGods;
	
	public MessageRitualToClient() {}
	public MessageRitualToClient(byte phase, boolean greatDreamer, boolean ancientGods) {
		this.phase = phase;
		this.greatDreamer = greatDreamer;
		this.ancientGods = ancientGods;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.phase = buf.readByte();
		this.greatDreamer = buf.readBoolean();
		this.ancientGods = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.phase);
		buf.writeBoolean(this.greatDreamer);
		buf.writeBoolean(this.ancientGods);
	}
	
	public static class RitualToClientMessageHandler implements IMessageHandler<MessageRitualToClient, IMessage> {

		@Override
		public IMessage onMessage(MessageRitualToClient message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BeyondTheVeil.proxy.openGui(Guis.GuiBaptism, message.phase, message.greatDreamer, message.ancientGods);
			});
			return null;
		}
		
	}

}
