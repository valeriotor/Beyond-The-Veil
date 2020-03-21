package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.dweller.DwellerDialogue;
import com.valeriotor.beyondtheveil.gui.Guis;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenDialogue implements IMessage{
	public int id;
	public MessageOpenDialogue() {}
	public MessageOpenDialogue(int id) {
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
	
	public static class OpenDialogueMessageHandler implements IMessageHandler<MessageOpenDialogue, IMessage> {

		@Override
		public IMessage onMessage(MessageOpenDialogue message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				DwellerDialogue.newInstance(message.id);
				BeyondTheVeil.proxy.openGui(Guis.GuiDialogueDweller);
			});
			return null;
		}
		
	}

}
