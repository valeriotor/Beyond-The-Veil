package com.valeriotor.BTV.network;

import java.util.UUID;

import com.valeriotor.BTV.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncTransformedPlayer implements IMessage{
	
	public UUID uuid;
	public boolean add;
	public boolean removeAll;
	
	public MessageSyncTransformedPlayer() {}
	public MessageSyncTransformedPlayer(boolean removeAll) {
		this.removeAll = removeAll;
	}
	public MessageSyncTransformedPlayer(boolean removeAll, UUID uuid, boolean add) {
		this.removeAll = removeAll;
		this.uuid = uuid;
		this.add = add;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.removeAll = buf.readBoolean();
		if(!buf.isReadable()) return;
		this.uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		this.add = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.removeAll);
		if(this.uuid == null) return;
		ByteBufUtils.writeUTF8String(buf, this.uuid.toString());
		buf.writeBoolean(this.add);
	}
	
	public static class SyncTransformedPlayerMessageHandler implements IMessageHandler<MessageSyncTransformedPlayer, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncTransformedPlayer message, MessageContext ctx) {
			if(message.removeAll) {
				BeyondTheVeil.proxy.renderEvents.transformedPlayers.clear();
				return null;
			}
			
			if(message.add)
				BeyondTheVeil.proxy.renderEvents.transformedPlayers.add(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));
			else
				BeyondTheVeil.proxy.renderEvents.transformedPlayers.remove(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid));	
			return null;
		}
		
	}

}
