package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.entities.EntityHamletDweller;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenTradeGui implements IMessage{
	boolean openGui;
	int id;
	
	public MessageOpenTradeGui() {}
	
	public MessageOpenTradeGui(boolean openGui, int id) {
		this.openGui = openGui;
		this.id = id;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.openGui = buf.readBoolean();
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.openGui);
		buf.writeInt(this.id);
	}
	
	public static class OpenTradeGuiMessageHandler implements IMessageHandler<MessageOpenTradeGui, IMessage>{

		@Override
		public IMessage onMessage(MessageOpenTradeGui message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				EntityHamletDweller e = (EntityHamletDweller)p.world.getEntityByID(message.id);
				if(e != null) {
					e.resetTalkingPlayer();
					if(message.openGui) {
						e.setCustomer(p);
						p.displayVillagerTradeGui(e);						
					}
				}
			});
			return null;
		}
		
	}

}
