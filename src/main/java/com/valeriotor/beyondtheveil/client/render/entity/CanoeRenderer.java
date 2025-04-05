package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.CanoeModel;
import com.valeriotor.beyondtheveil.client.model.entity.TestNautilus;
import com.valeriotor.beyondtheveil.entity.CanoeEntity;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CanoeRenderer extends EntityRenderer<CanoeEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/canoe.png");
    private final CanoeModel model;

    public CanoeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new CanoeModel(context.bakeLayer(CanoeModel.LAYER_LOCATION));
    }

    @Override
    protected boolean shouldShowName(CanoeEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(CanoeEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(CanoeEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, 1.576D, 0.0D);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(- pEntityYaw));


        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
        //pMatrixStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        model.prepareMobModel(pEntity, 0, 0, pPartialTicks);
        model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
        model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
