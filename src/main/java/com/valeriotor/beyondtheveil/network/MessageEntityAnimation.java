package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.entities.IAnimatedAttacker;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageEntityAnimation implements IMessage{

	public int animID;
	public int entityID;
	
	public MessageEntityAnimation() {}
	public MessageEntityAnimation(EntityLivingBase e, int animID) {
		this.entityID = e.getEntityId();
		this.animID = animID;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		this.animID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeInt(this.animID);
	}
	
	public static class EntityAnimationMessageHandler implements IMessageHandler<MessageEntityAnimation, IMessage>{

		@Override
		public IMessage onMessage(MessageEntityAnimation message, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();
			final WorldClient w = mc.world;
			mc.addScheduledTask(() -> {
				Entity e = w.getEntityByID(message.entityID);
				if(e instanceof IAnimatedAttacker) {
					((IAnimatedAttacker)e).setAttackAnimation(message.animID);
				}
			});
			return null;
		}
		
	}
	
}
