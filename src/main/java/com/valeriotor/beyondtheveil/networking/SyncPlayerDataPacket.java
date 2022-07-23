package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class SyncPlayerDataPacket {

    private Type type;
    private final String key;
    private boolean valueBoolean;
    private String valueString;
    private int valueInt;
    private long valueLong;

    public static SyncPlayerDataPacket toServer(String key) {
        return new ToServer(key);
    }

    public static SyncPlayerDataPacket toClient(String key) {
        return new ToClient(key);
    }

    public final SyncPlayerDataPacket setBoolean(boolean value) {
        this.type = Type.BOOLEAN;
        valueBoolean = value;
        return this;
    }

    public final SyncPlayerDataPacket setString(String value) {
        this.type = Type.STRING;
        valueString = value;
        return this;
    }

    public final SyncPlayerDataPacket setInt(int value) {
        this.type = Type.INT;
        valueInt = value;
        return this;
    }

    public final SyncPlayerDataPacket setLong(long value) {
        this.type = Type.LONG;
        valueLong = value;
        return this;
    }

    public SyncPlayerDataPacket(String key) {
        this.key = key;
    }

    public SyncPlayerDataPacket(FriendlyByteBuf buf) {
        this.type = Type.values()[buf.readByte()];
        this.key = buf.readUtf();
        switch (type) {
            case BOOLEAN -> valueBoolean = buf.readBoolean();
            case STRING -> valueString = buf.readUtf();
            case INT -> valueInt = buf.readInt();
            case LONG -> valueLong = buf.readLong();
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(type.ordinal());
        buf.writeUtf(key);
        switch (type) {
            case BOOLEAN -> buf.writeBoolean(valueBoolean);
            case STRING -> buf.writeUtf(valueString);
            case INT -> buf.writeInt(valueInt);
            case LONG -> buf.writeLong(valueLong);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide().equals(LogicalSide.CLIENT)) {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    switch (type) {
                        case BOOLEAN -> DataUtilClient.setBoolean(key, valueBoolean, false);
                        case STRING -> DataUtilClient.setString(key, valueString, false);
                        case INT -> DataUtilClient.setInt(key, valueInt, false);
                        case LONG -> DataUtilClient.setLong(key, valueLong, false);
                    }
                });
            } else {
                // TODO add safe logic for adding strings on server from client
            }
        });
        return true;
    }

    public static class ToServer extends SyncPlayerDataPacket{

        public ToServer(String key) {
            super(key);
        }

        public ToServer(FriendlyByteBuf buf) {
            super(buf);
        }
    }

    public static class ToClient extends SyncPlayerDataPacket{

        public ToClient(String key) {
            super(key);
        }

        public ToClient(FriendlyByteBuf buf) {
            super(buf);
        }
    }

    private enum Type {
        BOOLEAN, STRING, INT, LONG;
    }

}
