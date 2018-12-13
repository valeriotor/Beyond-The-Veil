package com.valeriotor.BTV.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetPosition implements IMessage{
	
	long pos;
	
	public MessageSetPosition() {
		
	}
	
	public MessageSetPosition(long pos) {
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.pos);
	}
	
	public static class SetPositionMessageHandler implements IMessageHandler<MessageSetPosition, IMessage>{

		@Override
		public MessageSetPosition onMessage(MessageSetPosition message, MessageContext ctx) {
			BlockPos pos = BlockPos.fromLong(message.pos);
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.connection.setPlayerLocation(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, p.getRotationYawHead(), 0);
			return null;
		}
	}

}
