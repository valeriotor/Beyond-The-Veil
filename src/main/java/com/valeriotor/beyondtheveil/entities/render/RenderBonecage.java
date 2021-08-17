package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityBonecage;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBonecage extends RenderLiving<EntityBonecage> {

    public RenderBonecage(RenderManager rendermanagerIn) {
        super(rendermanagerIn, ModelRegistry.bonecage, 0.5F);
    }
    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/bonecage.png");

    @Override
    protected ResourceLocation getEntityTexture(EntityBonecage entity) {
        return texture;
    }

}
