package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityCephalopodian;
import com.valeriotor.beyondtheveil.entities.ictya.EntitySandflatter;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSandflatter extends RenderLiving<EntitySandflatter> {

    public RenderSandflatter(RenderManager rendermanagerIn) {
        super(rendermanagerIn, ModelRegistry.sandflatter, 0.5F);
    }
    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/sandflatter.png");

    @Override
    protected ResourceLocation getEntityTexture(EntitySandflatter entity) {
        return texture;
    }


}
