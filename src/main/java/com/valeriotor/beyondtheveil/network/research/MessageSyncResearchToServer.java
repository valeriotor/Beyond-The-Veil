package com.valeriotor.beyondtheveil.network.research;

import com.valeriotor.beyondtheveil.capabilities.ResearchProvider;
import com.valeriotor.beyondtheveil.research.ResearchUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncResearchToServer implements IMessage{
	
	public ResearchSyncer sync;
	
	public MessageSyncResearchToServer() {}
	
	public MessageSyncResearchToServer(ResearchSyncer sync) {
		this.sync = sync;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.sync = new ResearchSyncer().readFromNBT(ByteBufUtils.readTag(buf));	
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, this.sync.writeToNBT(new NBTTagCompound()));		
	}
	
	public static class SyncResearchToServerMessageHandler implements IMessageHandler<MessageSyncResearchToServer, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncResearchToServer message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServer().addScheduledTask(() -> {
				ResearchSyncer sync = message.sync;
				sync.processServer(p);
			});
			return null;
		}
		
	}
	
	
	
}
