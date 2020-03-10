package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncDialogueData implements IMessage{
	
	public String dialogue;
	public boolean remove;
	
	public MessageSyncDialogueData() {}
	
	public MessageSyncDialogueData(String dialogue, boolean remove) {
		this.dialogue = dialogue;
		this.remove = remove;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.dialogue = ByteBufUtils.readUTF8String(buf);
		this.remove = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.dialogue);
		buf.writeBoolean(this.remove);
	}
	
	public static class SyncDialogueDataMessageHandler implements IMessageHandler<MessageSyncDialogueData, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncDialogueData message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServer().addScheduledTask(() -> {
			if(message.remove) {
				ctx.getServerHandler().player.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString("dialogue".concat(message.dialogue));
			} else {
				ctx.getServerHandler().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialogue".concat(message.dialogue), false);
			}
			});
			return null;
		}
		
	}

}
