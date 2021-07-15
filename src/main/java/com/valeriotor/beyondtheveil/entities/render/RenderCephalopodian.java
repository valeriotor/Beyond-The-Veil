package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityCephalopodian;
import com.valeriotor.beyondtheveil.entities.ictya.EntityDeepAngler;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCephalopodian extends RenderLiving<EntityCephalopodian> {

    public RenderCephalopodian(RenderManager rendermanagerIn) {
        super(rendermanagerIn, ModelRegistry.cephalopodian, 0.5F);
    }
    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/cephalopodian.png");

    @Override
    protected ResourceLocation getEntityTexture(EntityCephalopodian entity) {
        return texture;
    }

}
