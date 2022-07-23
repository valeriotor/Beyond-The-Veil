package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncAllPlayerDataToClientPacket {

    private CompoundTag data;

    public SyncAllPlayerDataToClientPacket(CompoundTag data) {
        this.data = data;
    }

    public SyncAllPlayerDataToClientPacket(FriendlyByteBuf buf) {
        data = buf.readAnySizeNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(data);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DataUtilClient.loadPlayerDataNBT(data));
        });
        return true;
    }


}
