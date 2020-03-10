package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.EntityBloodSkeleton;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBloodSkeleton extends RenderLiving<EntityBloodSkeleton>{

	public RenderBloodSkeleton(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.blood_skeleton, 0.5F);
	}
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/blood_skeleton.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBloodSkeleton entity) {
		return texture;
	}

}
