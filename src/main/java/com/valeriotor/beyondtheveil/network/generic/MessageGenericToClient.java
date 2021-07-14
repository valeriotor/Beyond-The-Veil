package com.valeriotor.beyondtheveil.network.generic;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.events.DOSkillEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGenericToClient implements IMessage{
    private GenericMessageKey message;

    public MessageGenericToClient() {
    }

    public MessageGenericToClient(GenericMessageKey message) {
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

    public static class GenericMessageHandler implements IMessageHandler<MessageGenericToClient, IMessage> {

        @Override
        public IMessage onMessage(MessageGenericToClient message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                switch(message.message) {
                    case DEEP_ONE_CLIMB_JUMP:
                        Minecraft.getMinecraft().player.motionY = 0.85;
                        BeyondTheVeil.proxy.cEvents.deepOneClimbResetTimer();
                    case ROAR_PLAYER_SINK:
                        Minecraft.getMinecraft().player.motionY = -4;
                        break;
                }
            });
            return null;
        }
    }
}
