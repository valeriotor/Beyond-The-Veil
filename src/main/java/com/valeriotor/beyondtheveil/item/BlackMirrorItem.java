package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.capability.DialogueData;
import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.container.dialogue.MirrorDialogueMenu;
import com.valeriotor.beyondtheveil.container.dialogue.ShoremanDialogueMenu;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;
import com.valeriotor.beyondtheveil.dialogue.DialogueType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class BlackMirrorItem extends Item {

    public BlackMirrorItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        DialogueTemplate template = DialogueData.for_(player).getDialogue(DialogueType.BLACK_MIRROR);
        if (player instanceof ServerPlayer p) {
            NetworkHooks.openScreen(p, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new MirrorDialogueMenu(pContainerId, pPlayerInventory, player, template), Component.translatable("gui.dialogue.black_mirror.display_name")), b -> {
                b.writeUtf(template.getID());
            });
        }
        return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
    }

}
