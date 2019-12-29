package com.valeriotor.BTV.network;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncIntDataToServer implements IMessage{

	private String key;
	private int value;
	
	public MessageSyncIntDataToServer() {}

	public MessageSyncIntDataToServer(String key, int value) {
		this.key = key;
		this.value = value;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.value = buf.readInt();
		this.key = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.value);
		ByteBufUtils.writeUTF8String(buf, this.key);
	}
	
	public static class SyncIntDataToServerMessageHandler implements IMessageHandler<MessageSyncIntDataToServer, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncIntDataToServer message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(message.key, message.value, false);
			});
			return null;
		}
		
		
	}

}
