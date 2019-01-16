package com.valeriotor.BTV.network;

import com.valeriotor.BTV.items.ItemAntique;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.research.ResearchCategories;

public class MessageSyncAntiqueNBT implements IMessage{
	
	String key;
	int value;
	
	public MessageSyncAntiqueNBT() {
	}
	
	public MessageSyncAntiqueNBT(String key, int value) {
		this.key = key;
		this.value = value;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.key = ByteBufUtils.readUTF8String(buf);
		this.value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.key);
		buf.writeInt(this.value);
		
	}
	
	public static class SyncAntiqueNBTMessageHandler implements IMessageHandler<MessageSyncAntiqueNBT, IMessage>{

		@Override
		public IMessage onMessage(MessageSyncAntiqueNBT message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			EnumHand hand = null;
			for(EnumHand h : EnumHand.values()) {
				if(p.getHeldItem(h).getItem() instanceof ItemAntique) {
					hand = h;
					break;
				}
			}
			
			if(!p.getHeldItem(hand).hasTagCompound()) {
				p.getHeldItem(hand).setTagCompound(new NBTTagCompound());
				
			}
			
			if(hand == null) return null;
			String key = message.key;
			if(this.isIntKey(key))
			p.getHeldItem(hand).getTagCompound().setInteger(key, message.value);
			else if(this.isBooleanKey(key))
			p.getHeldItem(hand).getTagCompound().setBoolean(key, message.value == 1);	
			
			if(key.equals("finished") && message.value == 1) ThaumcraftApi.internalMethods.addKnowledge(p, EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BEYOND_THE_VEIL"), 16);
			
			return null;
		}
		
		private boolean isIntKey(String key) {
			switch(key) {
			case "evenDiff":
			case "oddDiff":
			case "inscription":	
			return true;	
			}
			return false;
		}
		
		private boolean isBooleanKey(String key) {
			switch(key) {
			case "finished":
			return true;	
			}
			return false;
		}
		
	}

}
