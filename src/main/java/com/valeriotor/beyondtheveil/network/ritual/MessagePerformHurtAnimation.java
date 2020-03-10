package com.valeriotor.beyondtheveil.network.ritual;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePerformHurtAnimation implements IMessage{
	
	public byte sound = -1;
	
	public static final byte DEFAULT = 0;
	public static final byte DROWN = 1;
	public static final byte FIRE = 2;
	
	public MessagePerformHurtAnimation() {}
	public MessagePerformHurtAnimation(byte sound) {this.sound = sound;}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		if(buf.isReadable()) this.sound = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if(this.sound != -1) buf.writeByte(this.sound);
	}
	
	public static class PerformHurtAnimationMessageHandler implements IMessageHandler<MessagePerformHurtAnimation, IMessage>{

		@Override
		public IMessage onMessage(MessagePerformHurtAnimation message, MessageContext ctx) {
			EntityPlayer p = BeyondTheVeil.proxy.getPlayer();
			p.performHurtAnimation();
			SoundEvent s = null;
			if(message.sound == DEFAULT) {
				s = SoundEvents.ENTITY_PLAYER_HURT;
			} else if(message.sound == DROWN) {
				s = SoundEvents.ENTITY_PLAYER_HURT_DROWN;
			} else if(message.sound == FIRE) {
				s = SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE;
			}
			if(s != null) p.playSound(s, 1, 1);
			return null;
		}
		
	}
	
}
