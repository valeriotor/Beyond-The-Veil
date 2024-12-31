package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.capability.CapabilityEvents;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class GenericToServerPacket {

    public static GenericToServerPacket syncJournalReport(CompoundTag tag, boolean editing, boolean delete) {
        if (editing) {
            tag.putBoolean("editing", true);
        }
        if (delete) {
            tag.putBoolean("delete", true);
        }
        return new GenericToServerPacket(MessageType.SYNC_REPORT, tag);
    }

    private final MessageType type;
    private final CompoundTag tag;


    public GenericToServerPacket(MessageType type) {
        this(type, new CompoundTag());
    }

    public GenericToServerPacket(MessageType type, CompoundTag tag) {
        this.type = type;
        this.tag = tag;
    }

    public GenericToServerPacket(FriendlyByteBuf buf) {
        this.type = buf.readEnum(MessageType.class);
        this.tag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeNbt(tag);
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
                        Map<String, Reminiscence> reminiscences = DataUtil.getReminiscences(sender);
                        for (String key : reminiscences.keySet()) {
                            DataUtil.setBooleanOnServerAndSync(sender, PlayerDataLib.REMINISCED.apply(key), true, false);
                        }
                    }
                    case REMINISCING_STOP -> {
                        DataUtil.setBoolean(player, PlayerDataLib.REMINISCING, false, true);
                    }
                    case SLEEP_CHAMBER -> {
                        DreamHandler.dream(player, false);
                    }
                    case SYNC_REPORT -> {
                        if (tag.contains("editing")) {
                            DataUtil.setTag(player, PlayerDataLib.EDITING_JOURNAL_REPORT, tag);
                        } else if (tag.contains("delete")) {
                            DataUtil.removeTag(player, PlayerDataLib.JOURNAL_REPORT.apply(tag.getString("name")));
                        } else {
                            DataUtil.setTag(player, PlayerDataLib.JOURNAL_REPORT.apply(tag.getString("name")), tag);
                        }
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
        SLEEP_CHAMBER,
        SYNC_REPORT
    }

}
