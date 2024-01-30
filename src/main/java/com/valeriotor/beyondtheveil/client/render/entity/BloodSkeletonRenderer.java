package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.BloodSkeletonCrawlingModel;
import com.valeriotor.beyondtheveil.client.model.entity.BloodSkeletonModel;
import com.valeriotor.beyondtheveil.entity.BloodSkeletonEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class BloodSkeletonRenderer extends LivingEntityRenderer<BloodSkeletonEntity, BloodSkeletonCrawlingModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/blood_skeleton.png");

    public BloodSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new BloodSkeletonCrawlingModel(context.bakeLayer(BloodSkeletonCrawlingModel.LAYER_LOCATION)), 1F);
    }

    @Override
    protected boolean shouldShowName(BloodSkeletonEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(BloodSkeletonEntity pEntity) {
        return TEXTURE;
    }
}
