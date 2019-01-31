package com.valeriotor.BTV.network;

import java.util.List;
import java.util.function.Predicate;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.EntityHamletDweller;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextComponentString;
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
			List<EntityHamletDweller> dwellers = ctx.getServerHandler().player.world.getEntities(EntityHamletDweller.class, (entity) -> entity.getTalkingPlayer() == ctx.getServerHandler().player);
			for(EntityHamletDweller e : dwellers) {
				ctx.getServerHandler().player.getCapability(PlayerDataProvider.PLAYERDATA, null).setDialogueType(0);
				if(message.openGui) {
					e.setCustomer(ctx.getServerHandler().player);
					ctx.getServerHandler().player.displayVillagerTradeGui(e);
				}
				e.resetTalkingPlayer();
			}
			return null;
		}
		
	}

}
