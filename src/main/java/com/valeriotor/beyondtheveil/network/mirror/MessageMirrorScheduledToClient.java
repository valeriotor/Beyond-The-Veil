package com.valeriotor.beyondtheveil.network.mirror;

import com.valeriotor.beyondtheveil.capabilities.MirrorProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMirrorScheduledToClient implements IMessage{

	private String id;
	
	/** Call this constructor to remove scheduled dialogue
	 */
	public MessageMirrorScheduledToClient() {}
	
	/** Call this constructor to add scheduled dialogue
	 */
	public MessageMirrorScheduledToClient(String id) {
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		if(buf.isReadable())
		 this.id = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if(id != null)
			ByteBufUtils.writeUTF8String(buf, id);
	}
	
	public static class MirrorScheduledToClientMessageHandler implements IMessageHandler<MessageMirrorScheduledToClient, IMessage> {

		@Override
		public IMessage onMessage(MessageMirrorScheduledToClient message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if(message.id != null)
					Minecraft.getMinecraft().player.getCapability(MirrorProvider.MIRROR, null).setScheduledDialogue(message.id);
				else
					Minecraft.getMinecraft().player.getCapability(MirrorProvider.MIRROR, null).removeScheduledDialogue();;
			});
			return null;
		}
		
	}

}
