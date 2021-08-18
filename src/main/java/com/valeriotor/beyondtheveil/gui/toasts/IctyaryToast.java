package com.valeriotor.beyondtheveil.gui.toasts;

import com.valeriotor.beyondtheveil.dreaming.Memory;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class IctyaryToast implements IToast {

    private final String ictyaLocalizedName;
    private boolean hasPlayedSound;

    public static IctyaryToast createIctyaryToast(String ictya) {
        return new IctyaryToast(ictya);
    }

    private IctyaryToast(String ictya) {
        this.ictyaLocalizedName = I18n.format("entity.beyondtheveil:" + ictya + ".name");
    }

    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        toastGui.drawTexturedModalRect(0, 0, 0, 0, 160, 32);

        List<String> list = toastGui.getMinecraft().fontRenderer.listFormattedStringToWidth(ictyaLocalizedName, 125);
        int i = 16746751;
        if (list.size() == 1) {
                toastGui.getMinecraft().fontRenderer.drawString(I18n.format("toast.ictyary"), 30, 7, i | -16777216);
            toastGui.getMinecraft().fontRenderer.drawString(ictyaLocalizedName, 30, 18, -1);
        } else {
            int j = 1500;
            float f = 300.0F;

            if (delta < 1500L) {
                int k = MathHelper.floor(MathHelper.clamp((float) (1500L - delta) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                toastGui.getMinecraft().fontRenderer.drawString(I18n.format("toast.ictyary"), 30, 11, i | k);
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
        //TODO maybe add icon?
//        toastGui.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) null, displayinfo.getIcon(), 8, 8);
        return delta >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }
}
