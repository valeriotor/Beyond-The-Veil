package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityCrazedWeeper;
import com.valeriotor.BTV.entities.models.ModelRegistry;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCrazedWeeper extends RenderLiving<EntityCrazedWeeper>{

	public RenderCrazedWeeper(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.crazed_weeper, 0.5F);
	}
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/weeper.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCrazedWeeper entity) {
		return texture;
	}

}
