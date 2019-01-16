package com.valeriotor.BTV.network;

import com.valeriotor.BTV.items.ItemTablet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerKnowledge.EnumKnowledgeType;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
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
				if(p.getHeldItem(h).getItem() instanceof ItemTablet) {
					hand = h;
					break;
				}
			}
			ItemStack stack = p.getHeldItem(hand);
			
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				
			}
			
			if(hand == null) return null;
			String key = message.key;
			if(this.isIntKey(key))
				stack.getTagCompound().setInteger(key, message.value);
			else if(this.isBooleanKey(key))
				stack.getTagCompound().setBoolean(key, message.value == 1);	
			
			if(key.equals("finished") && message.value == 1) {
				IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(p);
				
				if(!k.isResearchKnown("inscription_complete"))
					ThaumcraftApi.internalMethods.progressResearch(p, "inscription_complete");
				
				
				if(!k.isResearchComplete(String.format("inscription%d", stack.getTagCompound().getInteger("inscription")))) {
					ThaumcraftApi.internalMethods.addKnowledge(p, EnumKnowledgeType.OBSERVATION, ResearchCategories.getResearchCategory("BEYOND_THE_VEIL"), 16);
					ThaumcraftApi.internalMethods.progressResearch(p, String.format("inscription%d", stack.getTagCompound().getInteger("inscription")));
				
				}
			}
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
