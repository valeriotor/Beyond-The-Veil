package com.valeriotor.beyondtheveil.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public interface IItemRenderGui {
	
	public RenderItem getItemRender();
	public void renderTooltip(ItemStack stack, int x, int y);
}
