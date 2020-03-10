package com.valeriotor.beyondtheveil.shoggoth.buildings;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.gui.GuiCityMapper;
import com.valeriotor.beyondtheveil.shoggoth.BlockBuffer;
import com.valeriotor.beyondtheveil.shoggoth.BuildingTemplate;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BuildingRoad extends BuildingTemplate{

	private DynamicTexture texture;
	
	public BuildingRoad(String name, int index) {
		super(2, 2, true, name, index);
		HashMap<Block, byte[][]> map = new HashMap<>();
		map.put(BlockRegistry.SlabElderHalf, this.elderStoneSlabs);
		this.buffer = new BlockBuffer(map, 2, 1, 1);
	}
	
	@SideOnly(Side.CLIENT)
	private DynamicTexture getDynamicTexture() {
		if(this.texture == null) {
			BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			for(int x = 0; x < 32; x++) {
				for(int y = 14; y < 16; y++) {
					image.setRGB(x, y, 0xFF8A9D9D);
				}
			}
			texture = new DynamicTexture(image);
		}
		return this.texture;
	}

	@Override
	public void drawScaledTexture(GuiCityMapper gui, int xTopLeft, int yTopLeft, float scale, int width, int height) {
		GlStateManager.bindTexture(this.getDynamicTexture().getGlTextureId());
		gui.drawModalRectWithCustomSizedTexture(xTopLeft, yTopLeft, 0, 0, (int)(width*scale), (int)(height*scale), 32*scale, 32*scale);
	}

	@Override
	public boolean isDefault() {
		return true;
	}
	
	byte[][] elderStoneSlabs = {{0,0,0,0}, {-1,0,0,0}};
	
}
