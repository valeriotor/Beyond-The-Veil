package com.valeriotor.beyondtheveil.network.generic;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.events.DOSkillEvents;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlayerAnimation;
import com.valeriotor.beyondtheveil.worship.DOSkill;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
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
            EntityPlayerMP p = ctx.getServerHandler().player;
            p.getServerWorld().addScheduledTask(() -> {
                switch(message.message) {
                    case DEEP_ONE_CLIMB_JUMP:
                        DOSkillEvents.jumpEvent(p);
                        break;
                    case UPPERCUT_ANIMATION:
                        DOSkillEvents.doUppercutAnimation(p);
                        break;
                    case UPPERCUT:
                        DOSkillEvents.doUppercut(p);
                }
            });
            return null;
        }
    }

}
