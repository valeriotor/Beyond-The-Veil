package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityShoggoth;
import com.valeriotor.BTV.entities.models.ModelRegistry;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderShoggoth extends RenderLiving<EntityShoggoth>{

	public RenderShoggoth(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.shoggoth, 2.0F);
	}
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/shoggoth.png");
	
	@Override
	protected ResourceLocation getEntityTexture(EntityShoggoth entity) {
		return texture;
	}

}
