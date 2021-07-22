package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.capabilities.IPlayerData;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.worship.DOSkill;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDOSkillsGui implements IMessage {

    public enum DOGUIActionType {
        UNLOCK, TOGGLE;
    }

    private DOGUIActionType type;
    private DOSkill skill;


    public MessageDOSkillsGui(DOGUIActionType type, DOSkill skill) {
        this.type = type;
        this.skill = skill;
    }

    public MessageDOSkillsGui() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = DOGUIActionType.values()[buf.readInt()];
        skill = DOSkill.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type.ordinal());
        buf.writeInt(skill.ordinal());
    }

    public static class DOSkillsGuiMessageHandler implements IMessageHandler<MessageDOSkillsGui, IMessage> {
        @Override
        public IMessage onMessage(MessageDOSkillsGui message, MessageContext ctx) {
            EntityPlayerMP p = ctx.getServerHandler().player;
            p.getServerWorld().addScheduledTask(() -> {
                switch (message.type) {
                    case TOGGLE: message.skill.toggle(p);
                        break;
                    case UNLOCK:
                        IPlayerData data = PlayerDataLib.getCap(p);
                        if(message.skill.isUnlockable(data) && message.skill.hasPlayerKilledEnoughIctya(data)) {
                        message.skill.unlock(data);
                        data.incrementOrSetInteger("ictya-used-" + message.skill.getRequiredIctyaSize().name().toLowerCase(), 1, 1, false);
                    }
                }
            });

            return null;
        }
    }

}
