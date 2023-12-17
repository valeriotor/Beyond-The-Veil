package com.valeriotor.beyondtheveil.client.render.entity;

import com.valeriotor.beyondtheveil.client.model.entity.BloodSkeletonCrawlingModel;
import com.valeriotor.beyondtheveil.client.model.entity.CrawlerModel;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CrawlerRenderer extends MobRenderer<CrawlerEntity, CrawlerModel> {

    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");


    public CrawlerRenderer(EntityRendererProvider.Context context) {
        super(context, new CrawlerModel(context.bakeLayer(CrawlerModel.LAYER_LOCATION)), 1F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new VillagerProfessionLayer<>(this, context.getResourceManager(), "villager"));
        this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(CrawlerEntity pEntity) {
        return VILLAGER_BASE_SKIN;
    }
}
