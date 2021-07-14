package com.valeriotor.beyondtheveil.network.generic;

import com.valeriotor.beyondtheveil.events.DOSkillEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGenericToServer implements IMessage {

    private GenericMessageKey message;

    public MessageGenericToServer() {
    }

    public MessageGenericToServer(GenericMessageKey message) {
        this.message = message;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        String read = ByteBufUtils.readUTF8String(buf);
        for (GenericMessageKey key : GenericMessageKey.values()) {
            if (key.name().equals(read)) {
                message = key;
                break;
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, message.name());
    }

    public static class GenericMessageHandler implements IMessageHandler<MessageGenericToServer, IMessage> {

        @Override
        public IMessage onMessage(MessageGenericToServer message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                switch(message.message) {
                    case DEEP_ONE_CLIMB_JUMP:
                        DOSkillEvents.jumpEvent(player);
                        break;
                }
            });
            return null;
        }
    }

}
