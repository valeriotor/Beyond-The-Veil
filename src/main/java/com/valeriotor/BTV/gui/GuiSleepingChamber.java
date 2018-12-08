package com.valeriotor.BTV.gui;

import java.io.IOException;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.SleepChamberMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiSleepingChamber extends GuiChat{
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID + ":textures/gui/black.png");
	private int timePassed = 0;
	
	public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("gui.sleep_chamber")));
    }
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//if(partialTicks > 1) drawDefaultBackground();
		if(this.timePassed < 301) this.timePassed++;;
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, ((float)this.timePassed)/300);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(0, 0, 0, 0, width, height);
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 1) {
			BTVPacketHandler.INSTANCE.sendToServer(new SleepChamberMessage());
			this.mc.displayGuiScreen((GuiScreen)null);
		}
		super.actionPerformed(button);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 1) {
			BTVPacketHandler.INSTANCE.sendToServer(new SleepChamberMessage());
			this.mc.displayGuiScreen((GuiScreen)null);
		}else if (keyCode != 28 && keyCode != 156)
        {
            super.keyTyped(typedChar, keyCode);
        }
        else
        {
            String s = this.inputField.getText().trim();

            if (!s.isEmpty())
            {
                this.sendChatMessage(s);
            }

            this.inputField.setText("");
            this.mc.ingameGUI.getChatGUI().resetScroll();
        }
	}
}
