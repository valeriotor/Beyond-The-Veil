package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
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

			Minecraft minecraft = Minecraft.getMinecraft();
		    final WorldClient worldClient = minecraft.world;
		    minecraft.addScheduledTask(new Runnable()
		    {
				public void run() {
			    	BeyondTheVeil.proxy.loadCustomResources();
				}
		    });
			return null;
		}
		
	}
	
}
