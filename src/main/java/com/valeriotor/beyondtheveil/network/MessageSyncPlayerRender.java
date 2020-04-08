package com.valeriotor.beyondtheveil.network;

import java.util.UUID;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncPlayerRender implements IMessage{
	
	public enum Type {
		DEEPONE, PARASITE, DREAMFOCUS;
	}

	public UUID uuid;
	public boolean add;
	public Type type;
	
	public MessageSyncPlayerRender() {}
	
	public MessageSyncPlayerRender(UUID uuid, boolean add, Type type) {
		this.uuid = uuid;
		this.add = add;
		this.type = type;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		if(!buf.isReadable()) return;
		this.uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		this.add = buf.readBoolean();
		this.type = Type.values()[buf.readInt()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if(this.uuid == null) return;
		ByteBufUtils.writeUTF8String(buf, this.uuid.toString());
		buf.writeBoolean(this.add);
		buf.writeInt(this.type.ordinal());
	}
	
	public static class SyncPlayerRenderMessageHandler implements IMessageHandler<MessageSyncPlayerRender, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncPlayerRender message, MessageContext ctx) {
			Minecraft minecraft = Minecraft.getMinecraft();
		    final WorldClient worldClient = minecraft.world;
		    minecraft.addScheduledTask(() ->
		    {
		    	switch(message.type) {
				case DREAMFOCUS:
					  if(message.add)
							BeyondTheVeil.proxy.renderEvents.dreamFocusPlayers.add(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
					  else
							BeyondTheVeil.proxy.renderEvents.dreamFocusPlayers.remove(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));	
					break;
				case PARASITE:
					  if(message.add)
							BeyondTheVeil.proxy.renderEvents.parasitePlayers.add(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
					  else
							BeyondTheVeil.proxy.renderEvents.parasitePlayers.remove(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
					break;
				case DEEPONE:
					if(message.add)
						BeyondTheVeil.proxy.renderEvents.transformedPlayers.add(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
					else
						BeyondTheVeil.proxy.renderEvents.transformedPlayers.remove(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
					break;
				default:
					break;
		    	
		    	}
				
		    });
			return null;
		}
		
	}

}
