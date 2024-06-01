package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {

    public static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(References.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(SyncResearchToClientPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncResearchToClientPacket::new)
                .encoder(SyncResearchToClientPacket::toBytes)
                .consumerNetworkThread(SyncResearchToClientPacket::handle)
                .add();
        net.messageBuilder(SyncResearchToServerPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncResearchToServerPacket::new)
                .encoder(SyncResearchToServerPacket::toBytes)
                .consumerNetworkThread(SyncResearchToServerPacket::handle)
                .add();

        net.messageBuilder(SyncPlayerDataPacket.ToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncPlayerDataPacket.ToClient::new)
                .encoder(SyncPlayerDataPacket.ToClient::toBytes)
                .consumerNetworkThread(SyncPlayerDataPacket.ToClient::handle)
                .add();
        net.messageBuilder(SyncPlayerDataPacket.ToServer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncPlayerDataPacket.ToServer::new)
                .encoder(SyncPlayerDataPacket.ToServer::toBytes)
                .consumerNetworkThread(SyncPlayerDataPacket.ToServer::handle)
                .add();

        net.messageBuilder(SyncAllPlayerDataToClientPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncAllPlayerDataToClientPacket::new)
                .encoder(SyncAllPlayerDataToClientPacket::toBytes)
                .consumerNetworkThread(SyncAllPlayerDataToClientPacket::handle)
                .add();

        net.messageBuilder(GenericToServerPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GenericToServerPacket::new)
                .encoder(GenericToServerPacket::toBytes)
                .consumerNetworkThread(GenericToServerPacket::handle)
                .add();

        net.messageBuilder(GenericToClientPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(GenericToClientPacket::new)
                .encoder(GenericToClientPacket::toBytes)
                .consumerNetworkThread(GenericToClientPacket::handle)
                .add();

        net.messageBuilder(FlaskShelfBlock.BreakFlaskMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FlaskShelfBlock.BreakFlaskMessage::new)
                .encoder(FlaskShelfBlock.BreakFlaskMessage::toBytes)
                .consumerNetworkThread(FlaskShelfBlock.BreakFlaskMessage::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToTracking(MSG message, Entity entity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
    }

    public static <MSG> void sendToTrackingAndSelf(MSG message, Player player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), message);
    }

}
