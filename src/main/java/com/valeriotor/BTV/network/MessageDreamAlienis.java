package com.valeriotor.BTV.network;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.gui.GuiAlienisDream;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.lib.BTVSounds;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDreamAlienis implements IMessage{
	
	public MessageDreamAlienis() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
	
	public static class DreamAlienisMessageHandler implements IMessageHandler<MessageDreamAlienis, IMessage>{

		@Override
		public IMessage onMessage(MessageDreamAlienis message, MessageContext ctx) {
			BeyondTheVeil.proxy.openGui(Guis.GuiAlienisDream);
			return null;
		}
		
	}

}
