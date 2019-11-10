package com.valeriotor.BTV.network;

import java.util.UUID;

import com.valeriotor.BTV.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncTransformedPlayer implements IMessage{
	
	public UUID uuid;
	public boolean add;
	
	public MessageSyncTransformedPlayer() {}
	
	public MessageSyncTransformedPlayer(UUID uuid, boolean add) {
		this.uuid = uuid;
		this.add = add;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		if(!buf.isReadable()) return;
		this.uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		this.add = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if(this.uuid == null) return;
		ByteBufUtils.writeUTF8String(buf, this.uuid.toString());
		buf.writeBoolean(this.add);
	}
	
	public static class SyncTransformedPlayerMessageHandler implements IMessageHandler<MessageSyncTransformedPlayer, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncTransformedPlayer message, MessageContext ctx) {
			Minecraft minecraft = Minecraft.getMinecraft();
		    final WorldClient worldClient = minecraft.world;
		    minecraft.addScheduledTask(new Runnable()
		    {
		      public void run() {
				if(message.add)
					BeyondTheVeil.proxy.renderEvents.transformedPlayers.add(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
				else
					BeyondTheVeil.proxy.renderEvents.transformedPlayers.remove(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
		      }
		    });
			return null;
		}
		
	}

}
