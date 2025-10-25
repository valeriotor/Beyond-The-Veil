package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.SurgeonModel;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class SurgeonRenderer extends LivingEntityRenderer<SurgeonEntity, SurgeonModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/surgeon.png");

    public SurgeonRenderer(EntityRendererProvider.Context context) {
        super(context, new SurgeonModel(context.bakeLayer(SurgeonModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    protected boolean shouldShowName(SurgeonEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(SurgeonEntity pEntity) {
        return TEXTURE;
    }
}
