package com.valeriotor.BTV.network;

import com.valeriotor.BTV.dreaming.Memory;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMemoryUnlockFromClient implements IMessage{

	public int ordinal;
	public boolean message;
	public MessageMemoryUnlockFromClient() {}
	
	public MessageMemoryUnlockFromClient(int ordinal) {
		this(ordinal, true);
	}
	
	public MessageMemoryUnlockFromClient(int ordinal, boolean message) {
		this.ordinal = ordinal;
		this.message = message;
	}
	
	public MessageMemoryUnlockFromClient(Memory m) {
		this(m, true);
	}
	
	public MessageMemoryUnlockFromClient(Memory m, boolean message) {
		this(m.ordinal(), message);
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.ordinal = buf.readInt();
		this.message = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(ordinal);
		buf.writeBoolean(message);
	}
	
	public static class MemoryUnlockFromClientMessageHandler implements IMessageHandler<MessageMemoryUnlockFromClient, IMessage>{

		@Override
		public IMessage onMessage(MessageMemoryUnlockFromClient message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				Memory m = Memory.values()[message.ordinal];
				m.unlock(p, message.message);
			});
			return null;
		}
		
	}

}
