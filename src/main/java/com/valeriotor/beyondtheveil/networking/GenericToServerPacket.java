package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.capability.CapabilityEvents;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.EnumMap;
import java.util.function.Supplier;

public class GenericToServerPacket {

    private final MessageType type;

    public GenericToServerPacket(MessageType type) {
        this.type = type;
    }

    public GenericToServerPacket(FriendlyByteBuf buf) {
        this.type = buf.readEnum(MessageType.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                switch (type) {
                    case ASK_DATA_SYNC -> {
                        CapabilityEvents.syncCapabilities(player);
                    }
                    case REMINISCING_START -> {
                        ServerPlayer sender = player;
                        DataUtil.setBoolean(sender, PlayerDataLib.REMINISCING, true, true);
                        EnumMap<Memory, Reminiscence> reminiscences = DataUtil.getReminiscences(sender);
                        for (Memory memory : reminiscences.keySet()) {
                            DataUtil.setBooleanOnServerAndSync(sender, PlayerDataLib.REMINISCED.apply(memory), true, false);
                        }
                    }
                    case REMINISCING_STOP -> {
                        DataUtil.setBoolean(player, PlayerDataLib.REMINISCING, false, true);
                    }
                    case SLEEP_CHAMBER -> {
                        DreamHandler.dream(player, false);
                    }
                }

            }
        });
        return true;
    }


    public enum MessageType {
        ASK_DATA_SYNC,
        REMINISCING_START,
        REMINISCING_STOP,
        SLEEP_CHAMBER;
    }

}
