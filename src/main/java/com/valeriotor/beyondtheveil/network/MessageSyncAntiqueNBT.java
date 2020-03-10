package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.capabilities.PlayerDataProvider;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.items.ItemTablet;
import com.valeriotor.beyondtheveil.util.SyncUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
				
				ItemStack newStack = new ItemStack(ItemRegistry.tablet, 1, 1);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("inscription", stack.getTagCompound().getInteger("inscription"));
				nbt.setInteger("oddDiff", 0);
				nbt.setInteger("evenDiff", 0);
				newStack.setTagCompound(nbt);
				
				
				p.setHeldItem(hand, newStack);
				IPlayerData data = p.getCapability(PlayerDataProvider.PLAYERDATA, null);
				
				if(!data.getString("inscription_complete"))
					SyncUtil.addStringDataOnServer(p, false, "inscription_complete");
				
				String dataKey = String.format("inscription%d", stack.getTagCompound().getInteger("inscription"));
				if(!data.getString(dataKey)) {
					SyncUtil.addStringDataOnServer(p, false, dataKey);
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
			//case "finished":
			//return true;	
			}
			return false;
		}
		
	}

}
