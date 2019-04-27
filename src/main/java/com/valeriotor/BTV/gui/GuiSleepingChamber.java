package com.valeriotor.BTV.gui;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.valeriotor.BTV.blocks.BlockFumeSpreader;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreams.DreamHandler;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageSleepChamber;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSleepingChamber extends GuiChat{
	// I don't have the slightest clue how the 1920x1080 black texture I made for this will work on different resolutions
	// Also, if you do know how to make the screen progressively blacker without using such a texture, please do tell
	private static final ResourceLocation texture = new ResourceLocation(References.MODID + ":textures/gui/black.png");
	private int timePassed = 0;
	
	public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("gui.sleep_chamber.wake")));
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, ((float)this.timePassed)/100);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(0, 0, 0, 0, width, height);
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		if(this.timePassed < 100) this.timePassed++;
		if(this.timePassed >= 100) {
			List<BlockPos> list = DreamHandler.checkBlocks(this.mc.player.world, this.mc.player.getPosition(), BlockRegistry.FumeSpreader.getDefaultState().withProperty(BlockFumeSpreader.ISFULL, true), 3);
			boolean flag = true;
			List<String> aspects = Lists.newArrayList();
			for(BlockPos pos : list) {
				String aspect = this.mc.player.world.getTileEntity(pos).getTileData().getString("containing");
				if(aspect != null) aspects.add(aspect);
			}
			if(aspects.contains("Alienis")) {
				if(this.mc.player.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("vacuos") || aspects.contains("Vacuos")) flag = false;
			}
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSleepChamber(true));
			if(flag) this.mc.displayGuiScreen((GuiScreen)null);
			return;
		}
		super.updateScreen();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 1) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSleepChamber(false));
			this.mc.displayGuiScreen((GuiScreen)null);
		}
		super.actionPerformed(button);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 1) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageSleepChamber(false));
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
