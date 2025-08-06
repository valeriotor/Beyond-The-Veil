package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.JellyModel;
import com.valeriotor.beyondtheveil.entity.ictya.JellyEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class JellyRenderer extends LivingEntityRenderer<JellyEntity, JellyModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/man_o_war.png");

    public JellyRenderer(EntityRendererProvider.Context context) {
        super(context, new JellyModel(context.bakeLayer(JellyModel.LAYER_LOCATION)), 0.6F);
    }

    @Override
    protected boolean shouldShowName(JellyEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(JellyEntity pEntity) {
        return TEXTURE;
    }
}
