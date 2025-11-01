package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeonLarvaModel;
import com.valeriotor.beyondtheveil.entity.SurgeonLarvaEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class SurgeonLarvaRenderer extends LivingEntityRenderer<SurgeonLarvaEntity, SurgeonLarvaModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/surgeon_larva.png");

    public SurgeonLarvaRenderer(EntityRendererProvider.Context context) {
        super(context, new SurgeonLarvaModel(context.bakeLayer(SurgeonLarvaModel.LAYER_LOCATION)), 0.1F);
    }

    @Override
    protected boolean shouldShowName(SurgeonLarvaEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public void render(SurgeonLarvaEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pose, MultiBufferSource pBuffer, int pPackedLight) {
        int growth = pEntity.getGrowth();
        pose.pushPose();
        float scale = growth / 500F;
        pose.scale(scale, scale, scale);
        super.render(pEntity, pEntityYaw, pPartialTicks, pose, pBuffer, pPackedLight);
        pose.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(SurgeonLarvaEntity pEntity) {
        return TEXTURE;
    }
}
