package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.Abomination1Model;
import com.valeriotor.beyondtheveil.client.render.entity.layer.AbominationFlesh1Layer;
import com.valeriotor.beyondtheveil.entity.Abomination1Entity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;

public class Abomination1Renderer extends MobRenderer<Abomination1Entity, Abomination1Model> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/abomination1.png");


    public Abomination1Renderer(EntityRendererProvider.Context context) {
        super(context, new Abomination1Model(context.bakeLayer(Abomination1Model.LAYER_LOCATION)), 1F);
    }

    @Override
    public void render(Abomination1Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.scale(1.08F, 1.08F, 1.08F);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(Abomination1Entity pEntity) {
        return TEXTURE;
    }
}
