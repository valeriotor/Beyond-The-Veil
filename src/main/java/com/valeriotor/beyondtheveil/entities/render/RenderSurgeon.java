package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.EntitySurgeon;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSurgeon extends RenderLiving<EntitySurgeon>{

	public RenderSurgeon(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.surgeon, 0.5F);
	}

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/surgeon.png");
	@Override
	protected ResourceLocation getEntityTexture(EntitySurgeon entity) {
		return texture;
	}

}
