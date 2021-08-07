package com.valeriotor.beyondtheveil.network.generic;

import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogueRegistry;
import com.valeriotor.beyondtheveil.blackmirror.MirrorDialogueTemplate;
import com.valeriotor.beyondtheveil.blackmirror.MirrorUtil;
import com.valeriotor.beyondtheveil.events.DOSkillEvents;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlayerAnimation;
import com.valeriotor.beyondtheveil.util.SyncUtil;
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
    private String optionalString;

    public MessageGenericToServer() {
    }

    public MessageGenericToServer(GenericMessageKey message) {
        this.message = message;
    }

    public MessageGenericToServer(GenericMessageKey message, String optionalString) {
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

    public static class GenericMessageHandler implements IMessageHandler<MessageGenericToServer, IMessage> {

        @Override
        public IMessage onMessage(MessageGenericToServer message, MessageContext ctx) {
            EntityPlayerMP p = ctx.getServerHandler().player;
            p.getServerWorld().addScheduledTask(() -> {
                switch(message.message) {
                    case DEEP_ONE_CLIMB_JUMP:
                        DOSkillEvents.jumpEvent(p);
                        break;
                    case MIRROR_UNLOCK_DATA:
                        MirrorDialogueTemplate template = MirrorUtil.getCurrentDialogue(p);
                        if (template != null) {
                            if (MirrorDialogueRegistry.getUnlockableDataPerDialogue(template.getID()).contains(message.optionalString)) {
                                SyncUtil.addStringDataOnServer(p, false, message.optionalString);
                            }
                        }
                        break;
                }
            });
            return null;
        }
    }

}
