package com.valeriotor.BTV.network;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.items.baubles.IActiveBauble;
import com.valeriotor.BTV.lib.PlayerDataLib;

import baubles.api.BaublesApi;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageActivateBauble implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}
	
	public static class ActivateBaubleMessageHandler implements IMessageHandler<MessageActivateBauble, IMessage>{

		@Override
		public IMessage onMessage(MessageActivateBauble message, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().player;
			int selected = p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(PlayerDataLib.SELECTED_BAUBLE, -1, false);
			if(selected == -1) return null;
			ItemStack stack = BaublesApi.getBaublesHandler(p).getStackInSlot(selected);
			if(stack.getItem() instanceof IActiveBauble) {
				String key = String.format(PlayerDataLib.BAUBLE_COOLDOWN, selected);
				if(p.getCapability(PlayerDataProvider.PLAYERDATA, null).getOrSetInteger(key, 0, false) > 0) return null; //Maybe add audio clue?
				((IActiveBauble)stack.getItem()).activate(p);
				p.getCapability(PlayerDataProvider.PLAYERDATA, null).setInteger(key, ((IActiveBauble)stack.getItem()).getCooldown(), false);
			}
			return null;
		}
		
	}
}
