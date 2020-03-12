package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DelayedMessage;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemHandlerHelper;

public class MessageDagonDialogue implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class DagonDialogueMessageHandler implements IMessageHandler<MessageDagonDialogue, IMessage>{

		@Override
		public IMessage onMessage(MessageDagonDialogue message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
				if(ResearchUtil.getResearchStage(p, "ALLIANCE") == 1) {
					PlayerTimer pt = new PlayerTimer(p, player -> ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Blocks.GOLD_BLOCK, 3)), 150);
					ServerTickEvents.addPlayerTimer(pt);
					SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.DAGON_DIALOGUE.apply(0));
					SyncUtil.addStringDataOnServer(p, false, "hearing");
					sendMessage("dagon.bringgold", p);
				} else if(ResearchUtil.getResearchStage(p, "METAMORPHOSIS") == 0) {
					SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.DAGON_DIALOGUE.apply(1));
					SyncUtil.addStringDataOnServer(p, false, "hearing2");
					sendMessage("dagon.slayguardians", p);
				} else if(ResearchUtil.getResearchStage(p, "WATERWALKING") == 0) {
					SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.DAGON_DIALOGUE.apply(2));
					SyncUtil.addStringDataOnServer(p, false, "hearing3");
					sendMessage("dagon.endbath", p);
				} else if(ResearchUtil.getResearchStage(p, "FINALQUEST") == 0) {
					SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.DAGON_DIALOGUE.apply(3));
					SyncUtil.addStringDataOnServer(p, false, "hearing4");
					SyncUtil.addStringDataOnServer(p, false, "dialogueend");
					sendMessage("dagon.lastwords", p);
				}
			});
			return null;
		}
		
		private void sendMessage(String key, EntityPlayer p) {
			ServerTickEvents.addMessage(new DelayedMessage(150, new TextComponentTranslation(key), p));;
		}
	}
	
}
