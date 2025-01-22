package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.Abomination0Model;
import com.valeriotor.beyondtheveil.client.render.entity.layer.BrokenBodyLayer;
import com.valeriotor.beyondtheveil.entity.Abomination0Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;

public class Abomination0Renderer extends MobRenderer<Abomination0Entity, Abomination0Model> {

    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");


    public Abomination0Renderer(EntityRendererProvider.Context context) {
        super(context, new Abomination0Model(context.bakeLayer(Abomination0Model.LAYER_LOCATION)), 1F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new VillagerProfessionLayer<>(this, context.getResourceManager(), "villager"));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new BrokenBodyLayer<>(this, context.getModelSet()));
    }

    @Override
    public void render(Abomination0Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Abomination0Entity pEntity) {
        return VILLAGER_BASE_SKIN;
    }
}
