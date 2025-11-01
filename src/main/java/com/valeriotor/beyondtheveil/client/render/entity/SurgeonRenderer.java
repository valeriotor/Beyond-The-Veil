package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeonModel;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

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
    public void render(SurgeonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        if (pEntity.getHeldPatientEntity() != null) {
            pPoseStack.pushPose();
            float f = Mth.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90 - f));
            pPoseStack.translate(0.55F, 1.25F, 0F);
            Minecraft.getInstance().getEntityRenderDispatcher().render(pEntity.getHeldPatientEntity(), 0, 0, 0, 0, 0, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SurgeonEntity pEntity) {
        return TEXTURE;
    }
}
