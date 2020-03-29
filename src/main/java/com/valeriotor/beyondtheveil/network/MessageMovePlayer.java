package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.BeyondTheVeil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMovePlayer implements IMessage{
	private double mx;
	private double my;
	private double mz;
	public MessageMovePlayer() {}
	public MessageMovePlayer(double motionX, double motionY, double motionZ) {
		this.mx = motionX;
		this.my = motionY;
		this.mz = motionZ;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.mx = buf.readDouble();
		this.my = buf.readDouble();
		this.mz = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(this.mx);
		buf.writeDouble(this.my);
		buf.writeDouble(this.mz);
	}
	
	public static class MovePlayerMessageHandler implements IMessageHandler<MessageMovePlayer, IMessage> {

		@Override
		public IMessage onMessage(MessageMovePlayer message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> BeyondTheVeil.proxy.cEvents.movePlayer(message.mx, message.my, message.mz));
			return null;
		}
		
	}

}
