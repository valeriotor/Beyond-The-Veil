package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class SyncResearchToServerPacket {
    private final ResearchSyncer syncer;

    public SyncResearchToServerPacket(ResearchSyncer syncer) {
        this.syncer = syncer;
    }

    public SyncResearchToServerPacket(FriendlyByteBuf buf) {
        syncer = ResearchSyncer.fromClient(Objects.requireNonNull(buf.readNbt()));
    }

    public void toBytes(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        syncer.writeToNBT(nbt);
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            syncer.process(ctx.getSender());
        });
        return true;
    }
}
