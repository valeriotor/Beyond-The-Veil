package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.Abomination1Model;
import com.valeriotor.beyondtheveil.client.model.entity.Abomination0Model;
import com.valeriotor.beyondtheveil.client.render.entity.layer.AbominationFlesh1Layer;
import com.valeriotor.beyondtheveil.client.render.entity.layer.BrokenBodyLayer;
import com.valeriotor.beyondtheveil.entity.Abomination1Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;

public class Abomination1Renderer extends MobRenderer<Abomination1Entity, Abomination1Model> {

    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");


    public Abomination1Renderer(EntityRendererProvider.Context context) {
        super(context, new Abomination1Model(context.bakeLayer(Abomination1Model.LAYER_LOCATION)), 1F);
        this.addLayer(new VillagerProfessionLayer<>(this, context.getResourceManager(), "villager"));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new AbominationFlesh1Layer<>(this, context.getModelSet()));
    }

    @Override
    public void render(Abomination1Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Abomination1Entity pEntity) {
        return VILLAGER_BASE_SKIN;
    }
}
