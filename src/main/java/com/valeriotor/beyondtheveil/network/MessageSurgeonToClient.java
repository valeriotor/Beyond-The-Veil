package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.gui.Guis;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSurgeonToClient implements IMessage{
	
	private int id;
	
	public MessageSurgeonToClient() {}
	
	public MessageSurgeonToClient(int id) {
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
	}
	
	public static class SurgeonToClientMessageHandler implements IMessageHandler<MessageSurgeonToClient, IMessage> {

		@Override
		public IMessage onMessage(MessageSurgeonToClient message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BeyondTheVeil.proxy.openGui(Guis.GuiSurgeon, message.id);
			});
			return null;
		}
		
	}

}
