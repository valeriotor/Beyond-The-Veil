package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.EntityDreadfish;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDreadfish extends RenderLiving<EntityDreadfish>{

	public RenderDreadfish(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.dreadfish, 0.5F);
	}
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/dreadfish.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDreadfish entity) {
		return texture;
	}

}
