package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class SyncResearchToClientPacket {

    private final ResearchSyncer syncer;

    public SyncResearchToClientPacket(ResearchSyncer syncer) {
        this.syncer = syncer;
    }

    public SyncResearchToClientPacket(FriendlyByteBuf buf) {
        syncer = ResearchSyncer.fromServer(Objects.requireNonNull(buf.readNbt()));
    }

    public void toBytes(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        syncer.writeToNBT(nbt);
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ResearchUtilClient.processResearchSyncer(syncer));
        });
        return true;
    }

}
