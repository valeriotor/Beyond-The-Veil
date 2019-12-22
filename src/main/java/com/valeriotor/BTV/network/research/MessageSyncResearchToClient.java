package com.valeriotor.BTV.network.research;

import com.valeriotor.BTV.capabilities.ResearchProvider;
import com.valeriotor.BTV.research.ResearchUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncResearchToClient implements IMessage{
	
public ResearchSyncer sync;
	
	public MessageSyncResearchToClient() {}
	
	public MessageSyncResearchToClient(ResearchSyncer sync) {
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
	
	public static class SyncResearchToClientMessageHandler implements IMessageHandler<MessageSyncResearchToClient, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncResearchToClient message, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();
			mc.addScheduledTask(() -> {
				ResearchSyncer sync = message.sync;
				if(sync.progress) ResearchUtil.progressResearch(mc.player, sync.key);
				//if(sync.learn)
				if(sync.status != null) mc.player.getCapability(ResearchProvider.RESEARCH, null).addResearchStatus(sync.status);
			});
			return null;
		}
		
	}
	
}
