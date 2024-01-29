package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.BloodSkeletonCrawlingModel;
import com.valeriotor.beyondtheveil.client.model.entity.CrawlerModel;
import com.valeriotor.beyondtheveil.client.render.entity.layer.PatientWoundLayer;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.client.renderer.MultiBufferSource;
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
        this.addLayer(new PatientWoundLayer<>(this, context.getModelSet()));
    }

    @Override
    public void render(CrawlerEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isSurgeryPatient() && pEntity.getPatientStatus().getExposedLocation() == SurgicalLocation.CHEST) {
            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(180));
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        if (pEntity.isSurgeryPatient() && pEntity.getPatientStatus().getExposedLocation() == SurgicalLocation.CHEST) {
            pPoseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(CrawlerEntity pEntity) {
        return VILLAGER_BASE_SKIN;
    }
}
