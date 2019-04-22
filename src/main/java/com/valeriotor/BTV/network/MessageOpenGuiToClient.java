package com.valeriotor.BTV.network;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.gui.GuiAlienisDream;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.lib.BTVSounds;

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
			BeyondTheVeil.proxy.openGui(message.id);
			return null;
		}
		
	}

}
