package com.valeriotor.beyondtheveil.network;

import com.valeriotor.beyondtheveil.bossfights.ArenaFightHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageArenaFight implements IMessage {
    private byte chosenFight;
    private BlockPos pos;

    public MessageArenaFight() {}

    public MessageArenaFight(int chosenFight, BlockPos pos) {
        this.chosenFight = (byte) chosenFight;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        chosenFight = buf.readByte();
        pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(chosenFight);
        buf.writeLong(pos.toLong());
    }

    public static class ArenaFightMessageHandler implements IMessageHandler<MessageArenaFight, IMessage> {

        @Override
        public IMessage onMessage(MessageArenaFight message, MessageContext ctx) {
            EntityPlayerMP p = ctx.getServerHandler().player;
            p.getServerWorld().addScheduledTask(() -> {
                BlockPos pos = message.pos;
                if(!ArenaFightHandler.isArenaOccupied(pos)) {
                    p.setPositionAndUpdate(pos.getX() + 3, pos.getY() + 5, pos.getZ() - 3);
                    ArenaFightHandler.startFight(p, pos, message.chosenFight);
                }
            });
            return null;
        }
    }

}
