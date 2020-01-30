package com.valeriotor.BTV.network;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.util.DelayedMessage;
import com.valeriotor.BTV.util.SyncUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDagonDialogue implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class DagonDialogueMessageHandler implements IMessageHandler<MessageDagonDialogue, IMessage>{

		@Override
		public IMessage onMessage(MessageDagonDialogue message, MessageContext ctx) {
			EntityPlayer p = ctx.getServerHandler().player;
			IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
			int value = SyncUtil.getOrSetIntDataOnServer(p, false, PlayerDataLib.DAGON_DIALOGUE, 0);
			switch(value) {
			case 0:
				p.addItemStackToInventory(new ItemStack(Blocks.GOLD_BLOCK, 3));
				p.inventoryContainer.detectAndSendChanges();
				data.setInteger(PlayerDataLib.DAGON_DIALOGUE, 1, false);
				SyncUtil.addStringDataOnServer(p, false, "hearing");
				sendMessage("dagon.bringgold", p);
				break;
			}
			return null;
		}
		
		private void sendMessage(String key, EntityPlayer p) {
			ServerTickEvents.addMessage(new DelayedMessage(150, new TextComponentTranslation(key), p));;
		}
	}
	
}
