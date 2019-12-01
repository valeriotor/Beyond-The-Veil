package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityBloodZombie;
import com.valeriotor.BTV.entities.models.ModelRegistry;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBloodZombie extends RenderLiving<EntityBloodZombie>{

	public RenderBloodZombie(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.blood_zombie, 0.5F);
	}

	private static final ResourceLocation TEX = new ResourceLocation(References.MODID, "textures/entity/blood_zombie.png");
	@Override
	protected ResourceLocation getEntityTexture(EntityBloodZombie entity) {
		return TEX;
	}

}
