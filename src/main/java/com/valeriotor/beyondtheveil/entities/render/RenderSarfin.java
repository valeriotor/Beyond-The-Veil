package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntitySarfin;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSarfin extends RenderLiving<EntitySarfin>{

	public RenderSarfin(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.sarfin, 0.5F);
	}
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/sarfin.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySarfin entity) {
		return texture;
	}

}
