package com.valeriotor.BTV.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMedallionEffect implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class MedallionEffectMessageHandler implements IMessageHandler<MessageMedallionEffect, IMessage>{

		@Override
		public IMessage onMessage(MessageMedallionEffect message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			AxisAlignedBB bb = new AxisAlignedBB(p.getPosition().add(-25, -10, -25), p.getPosition().add(25, 12, 25));
			List<Entity> entities = p.world.getEntitiesWithinAABBExcludingEntity(p, bb);
			entities.forEach(e -> {
				if(e instanceof EntityLivingBase) {
					((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 80, 1, false, true));
				}
			});
			return null;
		}
		
	}

}
