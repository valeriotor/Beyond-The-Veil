package com.valeriotor.beyondtheveil.network;

import java.util.HashMap;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageCovenantData implements IMessage{
	
	boolean clear;
	HashMap<String, BlockPos> players;
	
	public MessageCovenantData() {}
	
	public MessageCovenantData(boolean clear, HashMap<String, BlockPos> players) {
		this.clear = clear;
		this.players = players;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.clear = buf.readBoolean();
		if(buf.isReadable()) players = new HashMap<String, BlockPos>();
		while(buf.isReadable()) {
			String s = ByteBufUtils.readUTF8String(buf);
			BlockPos b = BlockPos.fromLong(buf.readLong());
			players.put(s, b);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.clear);
		if(this.players != null) {
			for(HashMap.Entry<String, BlockPos> entry : players.entrySet()) {
				ByteBufUtils.writeUTF8String(buf, entry.getKey());
				buf.writeLong(entry.getValue().toLong());
			}
		}
	}
	
	public static class CovenantDataMessageHandler implements IMessageHandler<MessageCovenantData, IMessage>{

		@Override
		public IMessage onMessage(MessageCovenantData message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BeyondTheVeil.proxy.renderEvents.covenantPlayers.clear();
				if(!message.clear) {
					if(message.players != null)
						BeyondTheVeil.proxy.renderEvents.covenantPlayers.putAll(message.players);
				}
			});
			return null;
		}
		
	}
	
	

}
