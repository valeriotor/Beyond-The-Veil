package com.valeriotor.BTV.network.ritual;

import com.valeriotor.BTV.events.special.DrowningRitualEvents;
import com.valeriotor.BTV.worship.DrowningRitual;
import com.valeriotor.BTV.worship.DrowningRitual.Phase;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRitualToServer implements IMessage{
	
	public byte mode;
	
	public static final byte GREATDREAMER = 0;
	public static final byte ANCIENTGODS = 1;
	public static final byte YOURSELF = 2;
	public static final byte BELIEVE = 3;
	public static final byte KNOW = 4;
	
	
	public MessageRitualToServer() {}

	public MessageRitualToServer(byte mode) {
		this.mode = mode;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.mode = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.mode);
	}
	
	public static class RitualToServerMessageHandler implements IMessageHandler<MessageRitualToServer, IMessage>{

		@Override
		public IMessage onMessage(MessageRitualToServer message, MessageContext ctx) {
			DrowningRitual dr = DrowningRitualEvents.rituals.get(ctx.getServerHandler().player);
			if(message.mode == 0) {
				dr.greatDreamer = true;
				if(dr.ancientGods) dr.setNewPhase(Phase.DEITYYOURSELFCHOOSE);
				else dr.resetPhase();
			} else if(message.mode == 1) {	
				dr.ancientGods = true;
				if(dr.greatDreamer) dr.setNewPhase(Phase.DEITYYOURSELFCHOOSE);
				else dr.resetPhase();
			} else if(message.mode == 2) {
				dr.setNewPhase(Phase.YOURSELF);
			} else if(message.mode == 3) {
				dr.setNewPhase(Phase.BELIEVE);
			} else if(message.mode == 4) {
				DrowningRitualEvents.rituals.remove(ctx.getServerHandler().player);
			}
			return null;
		}
		
	}

}
