package com.valeriotor.BTV.network;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.proxy.ClientProxy;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageReloadResources implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
	
	public static class ReloadResourcesMessageHandler implements IMessageHandler<MessageReloadResources, IMessage>{

		@Override
		public IMessage onMessage(MessageReloadResources message, MessageContext ctx) {
			BeyondTheVeil.proxy.loadCustomResources();
			return null;
		}
		
	}
	
}
