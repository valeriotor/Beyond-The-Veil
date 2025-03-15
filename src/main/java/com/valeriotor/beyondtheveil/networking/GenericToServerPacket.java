package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.block.HeartBlock;
import com.valeriotor.beyondtheveil.capability.CapabilityEvents;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.dreaming.dreams.Reminiscence;
import com.valeriotor.beyondtheveil.letters.ExchangeRegistry;
import com.valeriotor.beyondtheveil.letters.ExchangeTemplate;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    public static GenericToServerPacket sendLetter(String exchangeName, List<Integer> chosenOptions) {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", exchangeName);
        for (int i = 0; i < chosenOptions.size(); i++) {
            tag.putInt(String.valueOf(i), chosenOptions.get(i));
        }
        return new GenericToServerPacket(MessageType.SEND_LETTER, tag);
    }

    public static GenericToServerPacket redeemItems(String exchangeName, int index, int version) {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", exchangeName);
        tag.putInt("index", index);
        tag.putInt("version", version);
        return new GenericToServerPacket(MessageType.REDEEM_ITEMS, tag);
    }

    public static GenericToServerPacket openLetter(String exchangeName, int index, int version) {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", exchangeName);
        tag.putInt("index", index);
        tag.putInt("version", version);
        return new GenericToServerPacket(MessageType.OPEN_LETTER, tag);
    }

    public static GenericToServerPacket respawnNow() {
        return new GenericToServerPacket(MessageType.RESPAWN_NOW, new CompoundTag());
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
                        DataUtil.setBoolean(player, PlayerDataLib.REMINISCING, true, true);
                        Map<String, Reminiscence> reminiscences = DataUtil.getReminiscences(player);
                        for (String key : reminiscences.keySet()) {
                            DataUtil.setBooleanOnServerAndSync(player, PlayerDataLib.REMINISCED.apply(key), true, false);
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
                    case SEND_LETTER -> {
                        ExchangeTemplate template = ExchangeRegistry.byName(tag.getString("name"));
                        List<Integer> chosenOptions = tag.getAllKeys().stream().filter(StringUtils::isNumeric).sorted(Comparator.comparingInt(Integer::valueOf)).map(tag::getInt).toList();
                        player.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> c.sendLetter(player, template, chosenOptions, false));
                    }
                    case REDEEM_ITEMS -> {
                        ExchangeTemplate template = ExchangeRegistry.byName(tag.getString("name"));
                        int index = tag.getInt("index");
                        int version = tag.getInt("version");
                        player.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> c.redeemItems(player, template, index, version, true));
                    }
                    case OPEN_LETTER -> {
                        ExchangeTemplate template = ExchangeRegistry.byName(tag.getString("name"));
                        int index = tag.getInt("index");
                        int version = tag.getInt("version");
                        player.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> c.openLetter(player, template, index, version));
                    }
                    case RESPAWN_NOW -> HeartBlock.respawnNow(player);
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
        SYNC_REPORT,
        SEND_LETTER,
        REDEEM_ITEMS,
        OPEN_LETTER,
        RESPAWN_NOW
    }

}
