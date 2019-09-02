package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.tileEntities.TileCityMapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class GuiCityMapper extends GuiScreen{
	
	private final BlockPos pos;
	private TileCityMapper te;
	
	public GuiCityMapper(BlockPos pos) {
		this.pos = pos;
		TileEntity te = Minecraft.getMinecraft().player.world.getTileEntity(pos);
		if(te instanceof TileCityMapper) {
			this.te = (TileCityMapper)te; 
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for(int x = 0; x < 201; x++) {
			for(int y = 0; y < 201; y++) {
				int cah = te.colorsAndHeights[x][y];
				int height = cah >> 24;
				int offset = (pos.getY() - height) * 4;
				int x2 = this.width / 2 - 100 + x;
				int y2 = this.height / 2 - 100 + y;
				int r = inBetween(0, 255, ((cah >> 16) & 255) - offset);
				int g = inBetween(0, 255, ((cah >> 8) & 255) - offset);
				int b = inBetween(0, 255, ((cah) & 255) - offset);
				drawRect(x2, y2, x2+1, y2+1, 0xFF000000 | (r << 16) | (g << 8) | b);
			}
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private static int inBetween(int lowerEnd, int higherEnd, int value) {
		return Math.min(higherEnd, Math.max(lowerEnd, value));
	}

}
