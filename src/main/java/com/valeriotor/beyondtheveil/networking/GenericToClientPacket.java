package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.capability.CapabilityEvents;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GenericToClientPacket {

    public static GenericToClientPacket createWaypoint(String unlocalizedName, BlockPos pos, int color) {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", unlocalizedName);
        tag.putLong("pos", pos.asLong());
        tag.putInt("color", color);
        return new GenericToClientPacket(MessageType.WAYPOINT, tag);
    }

    private final MessageType type;
    private final CompoundTag tag;

    public GenericToClientPacket(MessageType type, CompoundTag tag) {
        this.type = type;
        this.tag = tag;
    }

    public GenericToClientPacket(FriendlyByteBuf buf) {
        this.type = buf.readEnum(MessageType.class);
        this.tag = buf.readAnySizeNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeNbt(tag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                switch (type) {
                    case WAYPOINT -> {
                        ClientData.getInstance().addWaypoint(tag);
                    }
                }

            });
        });
        return true;
    }


    private enum MessageType {
        WAYPOINT;
    }

}
