package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityWeeper;
import com.valeriotor.BTV.entities.models.ModelWeeper;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWeeper extends RenderLiving<EntityWeeper>{

	public RenderWeeper(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelWeeper(), 0.5F);
	}

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/weeper.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityWeeper entity) {
		return texture;
	}

}
