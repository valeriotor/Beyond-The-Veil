package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.entities.EntityHamletDweller;
import com.valeriotor.beyondtheveil.gui.DialogueHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenTradeGui implements IMessage{
	boolean openGui;
	
	public MessageOpenTradeGui() {}
	
	public MessageOpenTradeGui(boolean openGui) {
		this.openGui = openGui;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.openGui = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.openGui);
	}
	
	public static class OpenTradeGuiMessageHandler implements IMessageHandler<MessageOpenTradeGui, IMessage>{

		@Override
		public IMessage onMessage(MessageOpenTradeGui message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				EntityHamletDweller e = DialogueHandler.getDweller(p);
				DialogueHandler.removeDialogue(p);
				if(e != null && message.openGui) {
					e.setCustomer(p);
					p.displayVillagerTradeGui(e);
				}
			});
			return null;
		}
		
	}

}
