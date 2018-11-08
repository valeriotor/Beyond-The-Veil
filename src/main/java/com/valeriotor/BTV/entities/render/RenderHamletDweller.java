package com.valeriotor.BTV.entities.render;

import com.valeriotor.BTV.entities.EntityHamletDweller;
import com.valeriotor.BTV.lib.References;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHamletDweller extends RenderLiving<EntityHamletDweller>{
	
	
	public RenderHamletDweller(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelVillager(0.0F), 0.5F);
	}


	@Override
	protected ResourceLocation getEntityTexture(EntityHamletDweller entity) {
		
		return new ResourceLocation(References.MODID +":textures/entity/dweller/" + entity.getProfession().getName() + ".png");
	}

}
