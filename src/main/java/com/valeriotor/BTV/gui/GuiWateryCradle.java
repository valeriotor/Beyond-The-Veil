package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.lib.References;

import net.minecraft.util.ResourceLocation;

public class GuiWateryCradle extends GuiOptionWheel{

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
