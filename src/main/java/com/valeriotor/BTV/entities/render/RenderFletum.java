package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityFletum;
import com.valeriotor.BTV.entities.models.ModelRegistry;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFletum extends RenderLiving<EntityFletum>{

	public RenderFletum(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.fletum, 0.5F);
	}
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/weeper.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFletum entity) {
		return texture;
	}
}
