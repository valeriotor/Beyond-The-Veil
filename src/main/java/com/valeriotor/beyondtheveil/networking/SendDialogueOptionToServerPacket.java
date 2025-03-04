package com.valeriotor.beyondtheveil.networking;

import com.valeriotor.beyondtheveil.container.dialogue.EntityDialogueMenu;
import com.valeriotor.beyondtheveil.container.dialogue.MirrorDialogueMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendDialogueOptionToServerPacket {

    private final int optionIndex;

    public SendDialogueOptionToServerPacket(int optionIndex) {
        this.optionIndex = optionIndex;
    }

    public SendDialogueOptionToServerPacket(FriendlyByteBuf buf) {
        this.optionIndex = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(optionIndex);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                AbstractContainerMenu abstractContainerMenu = player.containerMenu;
                if (abstractContainerMenu instanceof EntityDialogueMenu menu) { // TODO not only shoreman? Make superclass
                    menu.chooseOptionOnServer(player, optionIndex);
                } else if (abstractContainerMenu instanceof MirrorDialogueMenu menu) {
                    menu.chooseOptionOnServer(player, optionIndex);
                }
            }
        });
        return true;
    }


}
