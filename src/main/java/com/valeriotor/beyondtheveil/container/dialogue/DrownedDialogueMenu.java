package com.valeriotor.beyondtheveil.container.dialogue;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.container.DrownedContainer;
import com.valeriotor.beyondtheveil.dialogue.DialogueRegistry;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.timers.BaptismTimer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkHooks;

public class DrownedDialogueMenu  extends AloneDialogueMenu {

    public DrownedDialogueMenu(int pContainerId, Inventory playerInventory, Player player, FriendlyByteBuf byteBuf) {
        this(pContainerId, playerInventory, player, DialogueRegistry.getTemplate(DialogueType.DROWNED, byteBuf.readUtf()));
    }

    public DrownedDialogueMenu(int pContainerId, Inventory playerInventory, Player player, DialogueTemplate template) {
        super(Registration.DROWNED_DIALOGUE_MENU.get(), pContainerId, playerInventory, player, template);
    }

    @Override
    protected void onDialogueFinish(ServerPlayer sp) {
        int ordinal = BaptismTimer.Phase.TALK2.ordinal();
        if (!getTemplate().getID().equals("ocean")) {
            NetworkHooks.openScreen(sp, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new DrownedContainer(pContainerId, sp, ordinal), Component.translatable("gui.drowned.title")), b -> {
                b.writeInt(ordinal);
            });
        } else {
            sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> {
                PlayerTimer baptism = c.getTimer("baptism");
                if (baptism instanceof BaptismTimer bt) {
                    bt.complete(sp);
                }
            });
        }
    }
}
