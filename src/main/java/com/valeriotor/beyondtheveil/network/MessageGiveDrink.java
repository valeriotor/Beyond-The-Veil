package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.items.ItemRegistry;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemHandlerHelper;

public class MessageGiveDrink implements IMessage{
	
	public MessageGiveDrink() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class GiveItemMessageHandler implements IMessageHandler<MessageGiveDrink, IMessage>{

		@Override
		public IMessage onMessage(MessageGiveDrink message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> ItemHandlerHelper.giveItemToPlayer(p, new ItemStack(ItemRegistry.rum)));
			return null;
		}
		
	}
	
	
	
	

}
