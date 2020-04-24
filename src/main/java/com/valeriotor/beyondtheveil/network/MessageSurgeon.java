package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.entities.EntitySurgeon;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSurgeon implements IMessage{
	
	private int id;
	private int option;
	public MessageSurgeon() {}
	public MessageSurgeon(int surgeonId, int option) {
		this.id = surgeonId;
		this.option = option;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		this.option = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
		buf.writeInt(this.option);
	}
	
	public static class SurgeonMessageHandler implements IMessageHandler<MessageSurgeon, IMessage> {

		@Override
		public IMessage onMessage(MessageSurgeon message, MessageContext ctx) {

			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				Entity e = p.world.getEntityByID(message.id);
				if(e instanceof EntitySurgeon) {
					EntitySurgeon s = (EntitySurgeon)e;
					if(p.getPersistentID().equals(s.getMasterID())) {
						s.setOperation(message.option);
					}
				}
			});
			return null;
		}
		
	}

}
