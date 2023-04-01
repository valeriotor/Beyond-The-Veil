package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.capability.CapabilityEvents;
import net.minecraft.nbt.CompoundTag;
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
            }
        });
        return true;
    }


    public enum MessageType {
        ASK_DATA_SYNC;
    }

}
