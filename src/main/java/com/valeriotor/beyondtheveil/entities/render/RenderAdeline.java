package com.valeriotor.beyondtheveil.entities.render;

import com.valeriotor.beyondtheveil.entities.ictya.EntityAdeline;
import com.valeriotor.beyondtheveil.entities.ictya.EntityDreadfish;
import com.valeriotor.beyondtheveil.entities.models.ModelRegistry;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderAdeline extends RenderLiving<EntityAdeline> {

    public RenderAdeline(RenderManager rendermanagerIn) {
        super(rendermanagerIn, ModelRegistry.adeline, 0.5F);
    }
    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/adeline.png");

    @Override
    protected ResourceLocation getEntityTexture(EntityAdeline entity) {
        return texture;
    }

    @Override
    public void doRender(EntityAdeline entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1F);
        GlStateManager.disableBlend();
    }
}
