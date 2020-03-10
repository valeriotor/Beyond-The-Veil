package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.EntityDeepOne;
import com.valeriotor.beyondtheveil.entities.models.ModelDeepOne;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDeepOne extends RenderLiving<EntityDeepOne>{
	
	public static final ResourceLocation deepOneTexture = new ResourceLocation(References.MODID +":textures/entity/deep_one.png");

	//public ResourceLocation getEntityTexture()
	//{
	//	final ResourceLocation deepOneTexture = new ResourceLocation(References.MODID + ":textures/entity/deep_one.png");
	//	return deepOneTexture;
	//}


	public RenderDeepOne(RenderManager rendermanagerIn) {
		super(rendermanagerIn, ModelRegistry.deep_one, 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityDeepOne entity) {
		
		return deepOneTexture;
		
	}
	
	@Override
	protected void applyRotations(EntityDeepOne entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
		// TODO Auto-generated method stub
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	
}
