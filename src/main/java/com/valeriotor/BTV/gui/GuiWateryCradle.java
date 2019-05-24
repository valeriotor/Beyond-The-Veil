package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.lib.References;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWateryCradle extends GuiOptionWheel{

	private final int[] coords = new int[3];
	
	
	public GuiWateryCradle(BlockPos pos) {
		this.coords[0] = pos.getX();
		this.coords[1] = pos.getY();
		this.coords[2] = pos.getZ();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		System.out.print(this.coords[0]);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public String getGuiLangName() {
		return "guiWateryCradle";
	}
	
	@Override
	protected boolean isNorthOptionAvailable() {
		return true;
	}
	
	private static final ResourceLocation spine = new ResourceLocation(References.MODID + ":textures/items/spine.png");
	@Override
	public ResourceLocation getNorthOptionTexture() {
		return spine;
	}

}
