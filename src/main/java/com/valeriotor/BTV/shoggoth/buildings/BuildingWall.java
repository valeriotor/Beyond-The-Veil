package com.valeriotor.BTV.shoggoth.buildings;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.gui.GuiCityMapper;
import com.valeriotor.BTV.shoggoth.BlockBuffer;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BuildingWall extends BuildingTemplate{
	
	private DynamicTexture texture;
	
	public BuildingWall(String name, int index) {
		super(2, 2, true, name, index);
		
		HashMap<Block, byte[][]> map = new HashMap<>();
		map.put(BlockRegistry.BlockStoneElderBrick, this.elderStoneBricks);
		this.buffer = new BlockBuffer(map, 2, 3, 1);
	}
	
	@SideOnly(Side.CLIENT)
	private DynamicTexture getDynamicTexture() {
		if(this.texture == null) {
			BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			for(int x = 0; x < 32; x++) {
				for(int y = 14; y < 16; y++) {
					if(((y + x) & 1) == 0) image.setRGB(x, y, 0xFF94ADAD);
					else image.setRGB(x, y, 0xFF7085AA);
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
		//gui.drawTexturedModalRect(xTopLeft, yTopLeft, 0, 0, (int) (width*scale), (int) (32*scale));
		//GL11.glDeleteTextures(texture.getGlTextureId());
	}

	@Override
	public boolean isDefault() {
		return true;
	}
	
	
	byte[][] elderStoneBricks = {{0,0,0,0}, {0,1,0,0}, {0,2,0,0}, {-1,0,0,0}, {-1,1,0,0}, {-1,2,0,0}};
	
}
