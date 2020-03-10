package com.valeriotor.beyondtheveil.network;

import java.util.UUID;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerAnimation implements IMessage{
	
	public UUID uuid;
	public int animID;
	
	public MessagePlayerAnimation() {}
	
	public MessagePlayerAnimation(UUID UUID, int animID) {
		this.uuid = UUID;
		this.animID = animID;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		this.animID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.uuid.toString());
		buf.writeInt(this.animID);
	}
	
	public static class PlayerAnimationMessageHandler implements IMessageHandler<MessagePlayerAnimation, IMessage>{

		@Override
		public IMessage onMessage(MessagePlayerAnimation message, MessageContext ctx) {
			BeyondTheVeil.proxy.cEvents.playerAnimations.put(Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(message.uuid), new Animation(AnimationRegistry.getAnimationFromId(message.animID)));
			return null;
		}
		
	}

}
