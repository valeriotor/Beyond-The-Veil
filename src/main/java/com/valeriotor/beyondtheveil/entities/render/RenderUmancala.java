package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntitySandflatter;
import com.valeriotor.beyondtheveil.entities.ictya.EntityUmancala;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderUmancala extends RenderLiving<EntityUmancala> {

    public RenderUmancala(RenderManager rendermanagerIn) {
        super(rendermanagerIn, ModelRegistry.umancala, 0.5F);
    }

    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/umancala.png");

    @Override
    protected ResourceLocation getEntityTexture(EntityUmancala entity) {
        return texture;
    }

}
