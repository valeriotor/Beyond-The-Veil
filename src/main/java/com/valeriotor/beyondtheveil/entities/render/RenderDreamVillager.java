package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.dreamfocus.EntityDreamVillager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDreamVillager extends RenderLiving<EntityDreamVillager>{

	public RenderDreamVillager(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelVillager(0.0F), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDreamVillager entity) {
		return entity.getProfession().getSkin();
	}

}
