package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.BloodSkeletonCrawlingModel;
import com.valeriotor.beyondtheveil.client.model.entity.BloodZombieModel;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class BloodZombieRenderer extends LivingEntityRenderer<LivingEntity, BloodZombieModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/blood_zombie.png");

    public BloodZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new BloodZombieModel(context.bakeLayer(BloodZombieModel.LAYER_LOCATION)), 1F);
    }

    @Override
    protected boolean shouldShowName(LivingEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntity pEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(LivingEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        pMatrixStack.scale(0.5F, 0.5F, 0.5F);
        super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
    }
}
