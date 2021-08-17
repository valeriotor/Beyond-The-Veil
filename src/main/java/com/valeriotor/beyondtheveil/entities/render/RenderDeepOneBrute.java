package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.bosses.EntityDeepOneBrute;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDeepOneBrute extends RenderLiving<EntityDeepOneBrute> {

    public RenderDeepOneBrute(RenderManager rendermanagerIn) {
        super(rendermanagerIn, ModelRegistry.deepOneBrute, 1F);
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/deep_one_brute.png");

    @Override
    protected ResourceLocation getEntityTexture(EntityDeepOneBrute entity) {
        return TEXTURE;
    }
}
