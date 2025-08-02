package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.CephalopodianModel;
import com.valeriotor.beyondtheveil.entity.ictya.CephalopodianEntity;
import com.valeriotor.beyondtheveil.entity.ictya.IctyaEntity;
import com.valeriotor.beyondtheveil.entity.ictya.SeaSnakeEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class IctyaRenderer<T extends IctyaEntity> extends LivingEntityRenderer<T, EntityModel<T>> {

    private final ResourceLocation texture;

    public IctyaRenderer(EntityRendererProvider.Context pContext, EntityModel<T> pModel, float pShadowRadius, ResourceLocation texture) {
        super(pContext, pModel, pShadowRadius);
        this.texture = texture;
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        float f = pEntityLiving.getSwimAmount(pPartialTicks);
        if (f >= 10.0) {
            float f3 = pEntityLiving.isInWater() || pEntityLiving.isInFluidType((fluidType, height) -> pEntityLiving.canSwimInFluidType(fluidType)) ? -pEntityLiving.getXRot() : -0;
            float f4 = Mth.lerp(f, 0.1F, f3);
            //pPoseStack.mulPose(Axis.XP.rotationDegrees(f4));
            if (pEntityLiving.isVisuallySwimming()) {
                pPoseStack.translate(0.0F, -1.0F, 0.3F);
            }
        }

    }

    @Override
    protected boolean shouldShowName(T pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return texture;
    }
}
