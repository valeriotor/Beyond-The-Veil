package com.valeriotor.beyondtheveil.network.mirror;

import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogueTemplate;
import com.valeriotor.beyondtheveil.blackmirror.MirrorUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMirrorToServer implements IMessage{

	public MessageMirrorToServer() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class MirrorToServerMessageHandler implements IMessageHandler<MessageMirrorToServer, IMessage> {

		@Override
		public IMessage onMessage(MessageMirrorToServer message, MessageContext ctx) {
			EntityPlayerMP p = ctx.getServerHandler().player;
			p.getServerWorld().addScheduledTask(() -> {
				MirrorDialogueTemplate template = MirrorUtil.getCurrentDialogue(p);
				if(template != null && template.getDefaultDialogueUnlockedWhenFinished() != null)
					MirrorUtil.updateDefaultDialogue(p, template.getDefaultDialogueUnlockedWhenFinished());
				MirrorUtil.removeScheduledDialogue(p);
			});
			return null;
		}
		
	}

}
