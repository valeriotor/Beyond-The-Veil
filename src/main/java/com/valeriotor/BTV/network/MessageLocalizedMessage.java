package com.valeriotor.BTV.network;

import com.valeriotor.BTV.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageLocalizedMessage implements IMessage{
	
	String message;
	
	public MessageLocalizedMessage() {
		
	}
	
	public MessageLocalizedMessage(String msg) {
		this.message = msg;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.message = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.message);
	}
	
	public static class LocalizedMessageMessageHandler implements IMessageHandler<MessageLocalizedMessage, IMessage>{

		@Override
		public IMessage onMessage(MessageLocalizedMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(BeyondTheVeil.proxy.localizeMessage(message.message)));
			
			return null;
		}
		
	}

}
