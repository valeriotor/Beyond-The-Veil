package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.SandflatterModel;
import com.valeriotor.beyondtheveil.client.model.entity.UmancalaModel;
import com.valeriotor.beyondtheveil.entity.ictya.SandflatterEntity;
import com.valeriotor.beyondtheveil.entity.ictya.UmancalaEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SandflatterRenderer extends IctyaRenderer<SandflatterEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/ictya/sandflatter.png");

    public SandflatterRenderer(EntityRendererProvider.Context context) {
        super(context, new SandflatterModel(context.bakeLayer(SandflatterModel.LAYER_LOCATION)), 0.6F, TEXTURE);
    }

    @Override
    protected void setupRotations(SandflatterEntity pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        if (pEntityLiving.isAmbushing()) {
            pPoseStack.translate(0, -1.45, 0);
        }
    }

    @Override
    public void render(SandflatterEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.hasRepositioned()) {
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }
}
