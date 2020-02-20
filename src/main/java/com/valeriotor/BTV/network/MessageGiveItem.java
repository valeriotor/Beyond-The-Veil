package com.valeriotor.BTV.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemHandlerHelper;

public class MessageGiveItem implements IMessage{
	ItemStack stack;
	
	public MessageGiveItem() {}
	public MessageGiveItem(ItemStack stack) {
		this.stack = stack;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, stack);
	}
	
	public static class GiveItemMessageHandler implements IMessageHandler<MessageGiveItem, IMessage>{

		@Override
		public IMessage onMessage(MessageGiveItem message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> ItemHandlerHelper.giveItemToPlayer(p, message.stack));
			return null;
		}
		
	}
	
	
	
	

}
