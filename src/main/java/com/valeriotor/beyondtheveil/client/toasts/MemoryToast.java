package com.valeriotor.beyondtheveil.client.toasts;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class MemoryToast implements Toast {

    private static final Component TITLE_TEXT = Component.translatable("memory.toast.title");
    private final Component DESCRIPTION_TEXT;
    private final Memory memory;

    public MemoryToast(Memory memory) {
        this.memory = memory;
        DESCRIPTION_TEXT = memory.getTranslationComponentWithPrefix();
    }


    @Override
    public Visibility render(GuiGraphics pGuiGraphics, ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 32, this.width(), this.height());
        pGuiGraphics.drawString(pToastComponent.getMinecraft().font, TITLE_TEXT, 30, 7, -11534256, false);
        pGuiGraphics.drawString(pToastComponent.getMinecraft().font, DESCRIPTION_TEXT, 30, 18, -16777216, false);
        ItemStack itemstack = memory.getItem();
        //pGuiGraphics.pose().pushPose();
        //pGuiGraphics.pose().scale(0.6F, 0.6F, 1.0F);
        //pGuiGraphics.renderFakeItem(itemstack, 3, 3);
        //pGuiGraphics.pose().popPose();
        pGuiGraphics.renderFakeItem(memory.getItem(), 8, 8);
        return (double)(pTimeSinceLastVisible) >= 5000.0D * pToastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }
}
