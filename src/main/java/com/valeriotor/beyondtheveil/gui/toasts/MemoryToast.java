package com.valeriotor.beyondtheveil.gui.toasts;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.util.ItemHelper;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class MemoryToast implements IToast{

    private final Memory memory;
    private boolean hasPlayedSound;
    private final ItemStack newStack;

    public static MemoryToast createMemoryToast(Memory memory) {
        return new MemoryToast(memory);
    }

    private MemoryToast(Memory memory) {
        this.memory = memory;
        newStack = new ItemStack(ItemRegistry.memory_phial);
        ItemHelper.checkTagCompound(newStack).setString("memory", memory.getDataName());
    }

    @Override
    public IToast.Visibility draw(GuiToast toastGui, long delta) {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        toastGui.drawTexturedModalRect(0, 0, 0, 0, 160, 32);

        List<String> list = toastGui.getMinecraft().fontRenderer.listFormattedStringToWidth(memory.getLocalizedName(), 125);
        int i = 16746751;
        if (list.size() == 1) {
            toastGui.getMinecraft().fontRenderer.drawString(I18n.format("toast.memory"), 30, 7, i | -16777216);
            toastGui.getMinecraft().fontRenderer.drawString(memory.getLocalizedName(), 30, 18, -1);
        } else {
            int j = 1500;
            float f = 300.0F;

            if (delta < 1500L) {
                int k = MathHelper.floor(MathHelper.clamp((float) (1500L - delta) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                toastGui.getMinecraft().fontRenderer.drawString(I18n.format("toast.memory"), 30, 11, i | k);
            } else {
                int i1 = MathHelper.floor(MathHelper.clamp((float) (delta - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                int l = 16 - list.size() * toastGui.getMinecraft().fontRenderer.FONT_HEIGHT / 2;

                for (String s : list) {
                    toastGui.getMinecraft().fontRenderer.drawString(s, 30, l, 16777215 | i1);
                    l += toastGui.getMinecraft().fontRenderer.FONT_HEIGHT;
                }
            }
        }

        if (!this.hasPlayedSound && delta > -500L) {
            this.hasPlayedSound = true;

        }

        RenderHelper.enableGUIStandardItemLighting();
        toastGui.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(null, newStack, 8, 8);
        return delta >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }

}
