package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.client.model.entity.DeepOneModel;
import com.valeriotor.beyondtheveil.client.model.entity.WeeperModel;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WeeperRenderer extends LivingEntityRenderer<WeeperEntity, WeeperModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/weeper.png");

    public WeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new WeeperModel(context.bakeLayer(WeeperModel.LAYER_LOCATION)), 1F);
    }

    @Override
    protected boolean shouldShowName(WeeperEntity pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public void render(WeeperEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
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
    public ResourceLocation getTextureLocation(WeeperEntity pEntity) {
        return TEXTURE;
    }
}
