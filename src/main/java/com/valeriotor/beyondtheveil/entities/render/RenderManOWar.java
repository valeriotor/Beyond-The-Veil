package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityManOWar;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderManOWar extends RenderLiving<EntityManOWar>{

	public RenderManOWar(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.man_o_war, 0.5F);
	}

	public static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/manowar.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityManOWar entity) {
		return texture;
	}
	
	@Override
	public void doRender(EntityManOWar entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.85F);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1F);
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

}
