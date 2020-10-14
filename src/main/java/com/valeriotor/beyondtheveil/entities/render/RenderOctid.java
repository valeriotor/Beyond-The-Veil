package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityOctid;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderOctid extends RenderLiving<EntityOctid>{

	public RenderOctid(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.octid, 0.5F);
	}
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/octid.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityOctid entity) {
		return texture;
	}

}
