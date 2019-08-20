package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityStarspawn;
import com.valeriotor.BTV.entities.models.ModelRegistry;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderStarspawn extends RenderLiving<EntityStarspawn>{

	public RenderStarspawn(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.starspawn, 0.5F);
	}

	public static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/starspawn.png");
	@Override
	protected ResourceLocation getEntityTexture(EntityStarspawn entity) {
		return texture;
	}
	
	@Override
	public void doRender(EntityStarspawn entity, double x, double y, double z, float entityYaw, float partialTicks) {
		//GlStateManager.pushMatrix();
		//GlStateManager.scale(0.5, 0.5, 0.5);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		//GlStateManager.popMatrix();
	}

}
