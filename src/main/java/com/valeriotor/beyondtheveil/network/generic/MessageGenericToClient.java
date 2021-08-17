package com.valeriotor.beyondtheveil.network.generic;

import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.events.DOSkillEvents;
import com.valeriotor.beyondtheveil.gui.toasts.IctyaryToast;
import com.valeriotor.beyondtheveil.gui.toasts.MemoryToast;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGenericToClient implements IMessage{
    private GenericMessageKey message;
    private String optionalString;

    public MessageGenericToClient() {
    }

    public MessageGenericToClient(GenericMessageKey message) {
        this.message = message;
    }

    public MessageGenericToClient(GenericMessageKey message, String optionalString) {
        this.message = message;
        this.optionalString = optionalString;
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
        if(buf.isReadable())
            optionalString = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, message.name());
        if(optionalString != null) ByteBufUtils.writeUTF8String(buf, optionalString);
    }

    public static class GenericMessageHandler implements IMessageHandler<MessageGenericToClient, IMessage> {

        @Override
        public IMessage onMessage(MessageGenericToClient message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                switch(message.message) {
                    case DEEP_ONE_CLIMB_JUMP:
                        Minecraft.getMinecraft().player.motionY = 0.85;
                        BeyondTheVeil.proxy.cEvents.deepOneClimbResetTimer();
                        break;
                    case ROAR_PLAYER_SINK:
                        Minecraft.getMinecraft().player.motionY = -4;
                        break;
                    case UPPERCUT_ANIMATION:
                        BeyondTheVeil.proxy.cEvents.deepOneUppercutResetTimer();
                        break;
                    case ICTYARY_ENTRY:
                        Minecraft.getMinecraft().getToastGui().add(new IctyaryToast(message.optionalString));
                        break;
                    case MEMORY_ENTRY:
                        Minecraft.getMinecraft().getToastGui().add(new MemoryToast(Memory.getMemoryFromDataName(message.optionalString)));
                        break;
                }
            });
            return null;
        }
    }
}
