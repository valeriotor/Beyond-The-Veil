package com.valeriotor.BTV.network;

import com.valeriotor.BTV.lib.BTVSounds;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlaySound implements IMessage{
	
	public int id;
	public long pos;
	
	public MessagePlaySound() {}
	
	public MessagePlaySound(int idIn, long posIn) {
		this.id = idIn;
		this.pos = posIn;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		this.pos = buf.readLong();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
		buf.writeLong(this.pos);
		
	}
	
	public static class PlaySoundMessageHandler implements IMessageHandler<MessagePlaySound, IMessage>{

		@Override
		public IMessage onMessage(MessagePlaySound message, MessageContext ctx) {
			BlockPos p = BlockPos.fromLong(message.pos);
			Minecraft.getMinecraft().player.world.playSound(p.getX(), p.getY(), p.getZ(), BTVSounds.getSoundById(message.id), SoundCategory.PLAYERS, (float) 0.7, 1, false);
			return null;
		}
		
	}

}
