package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityBoneCage;
import com.valeriotor.beyondtheveil.entities.ictya.EntityCephalopodian;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBoneCage extends RenderLiving<EntityBoneCage> {

    public RenderBoneCage(RenderManager rendermanagerIn) {
        super(rendermanagerIn, ModelRegistry.boneCage, 0.5F);
    }
    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/bonecage.png");

    @Override
    protected ResourceLocation getEntityTexture(EntityBoneCage entity) {
        return texture;
    }

}
