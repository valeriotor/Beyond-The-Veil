package com.valeriotor.BTV.network.baubles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRevelationRingToServer implements IMessage{
	
	private int id;
	
	public MessageRevelationRingToServer() {}
	public MessageRevelationRingToServer(int id) {
		this.id = id;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
	}
	
	public static class RevelationRingToServerMessageHandler implements IMessageHandler<MessageRevelationRingToServer, IMessage>{

		@Override
		public IMessage onMessage(MessageRevelationRingToServer message, MessageContext ctx) {
			Entity e = ctx.getServerHandler().player.world.getEntityByID(message.id);
			if(e instanceof EntityLivingBase && ((EntityLivingBase)e).isPotionActive(MobEffects.INVISIBILITY)) {
				return new MessageRevelationRingToClient(message.id);
			}
			return null;
		}
		
	}
	
}
