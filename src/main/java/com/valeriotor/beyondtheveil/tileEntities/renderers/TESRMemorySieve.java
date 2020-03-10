package com.valeriotor.beyondtheveil.tileEntities.renderers;

import org.lwjgl.opengl.GL11;

import com.valeriotor.beyondtheveil.tileEntities.TileMemorySieve;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class TESRMemorySieve extends TileEntitySpecialRenderer<TileMemorySieve>{
	
	@Override
	public void render(TileMemorySieve te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		EntityItem item = te.getItemEntity();
		if(item != null) {
			RenderHelper.enableStandardItemLighting();
			GlStateManager.disableLighting();
			final int lightmapCoords = 15728881;

			final int skyLight = lightmapCoords % 65536;
			final int blockLight = lightmapCoords / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, skyLight, blockLight);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().getRenderManager().renderEntity(item, x + 0.5, y + 1, z + 0.5, 0, partialTicks, false);
		}
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}

}
