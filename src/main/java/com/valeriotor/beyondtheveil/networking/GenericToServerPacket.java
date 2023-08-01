package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.capability.CapabilityEvents;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

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
            switch (type) {
                case ASK_DATA_SYNC -> {
                    CapabilityEvents.syncCapabilities(ctx.getSender());
                }
                case REMINISCING_START -> {
                    if(ctx.getSender() != null)
                        DataUtil.setBoolean(ctx.getSender(), PlayerDataLib.REMINISCING, true, true);
                }
                case REMINISCING_STOP -> {
                    if(ctx.getSender() != null)
                        DataUtil.setBoolean(ctx.getSender(), PlayerDataLib.REMINISCING, false, true);
                }
            }
        });
        return true;
    }


    public enum MessageType {
        ASK_DATA_SYNC,
        REMINISCING_START,
        REMINISCING_STOP;
    }

}
