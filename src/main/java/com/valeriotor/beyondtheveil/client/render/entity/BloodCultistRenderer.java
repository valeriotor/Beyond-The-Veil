package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.BloodCultistModel;
import com.valeriotor.beyondtheveil.client.model.entity.WeeperModel;
import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BloodCultistRenderer extends LivingEntityRenderer<BloodCultistEntity, BloodCultistModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/blood_cultist.png");

    public BloodCultistRenderer(EntityRendererProvider.Context context) {
        super(context, new BloodCultistModel(context.bakeLayer(BloodCultistModel.LAYER_LOCATION)), 1F);
    }

    @Override
    protected boolean shouldShowName(BloodCultistEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public void render(BloodCultistEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        final float FACTOR = 1.2F;
        pPoseStack.scale(FACTOR, FACTOR, FACTOR);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
        if (pEntity.getHeldVillager() != null) {
            pPoseStack.pushPose();
            float f = Mth.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90 - f));
            pPoseStack.translate(-0.5F, 1.25F, 0F);
            Minecraft.getInstance().getEntityRenderDispatcher().render(pEntity.getHeldVillager(), 0, 0, 0, 0, 0, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }

    }

    @Override
    public ResourceLocation getTextureLocation(BloodCultistEntity pEntity) {
        return TEXTURE;
    }
}
