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
import net.minecraftforge.items.ItemHandlerHelper;
import thaumcraft.api.ThaumcraftApi;

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
			int value = data.getOrSetInteger(PlayerDataLib.DAGON_DIALOGUE, 0, false);
			switch(value) {
			case 0:
				p.addItemStackToInventory(new ItemStack(Blocks.GOLD_BLOCK, 3));
				p.inventoryContainer.detectAndSendChanges();
				data.setInteger(PlayerDataLib.DAGON_DIALOGUE, 1, false);
				ThaumcraftApi.internalMethods.progressResearch(p, "hearing");
				sendMessage("dagon.bringgold", p);
				break;
			}
			SyncUtil.syncCapabilityData(p);
			return null;
		}
		
		private void sendMessage(String key, EntityPlayer p) {
			ServerTickEvents.addMessage(new DelayedMessage(150, new TextComponentTranslation(key), p));;
		}
	}
	
}
