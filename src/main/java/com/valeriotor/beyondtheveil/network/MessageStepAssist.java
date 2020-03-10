package com.valeriotor.beyondtheveil.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageStepAssist implements IMessage{
	
	private boolean increase = false;
	
	public MessageStepAssist() {}

	public MessageStepAssist(boolean increase) {
		this.increase = increase;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.increase = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.increase);
	}
	
	public static class StepAssistMessageHandler implements IMessageHandler<MessageStepAssist, IMessage> {

		@Override
		public IMessage onMessage(MessageStepAssist message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().player.stepHeight += (message.increase ? 0.5 : -0.5));
			return null;
		}
		
	}
	
}
