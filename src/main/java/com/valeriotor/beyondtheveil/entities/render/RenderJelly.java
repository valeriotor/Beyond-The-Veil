package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityJelly;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderJelly extends RenderLiving<EntityJelly>{

	public RenderJelly(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.jelly, 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityJelly entity) {
		return RenderManOWar.texture;
	}
	
	@Override
	public void doRender(EntityJelly entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.6F);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1F);
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

}
