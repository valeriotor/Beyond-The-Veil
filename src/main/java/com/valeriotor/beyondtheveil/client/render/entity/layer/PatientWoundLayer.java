package com.valeriotor.beyondtheveil.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.CrawlerModel;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.client.model.entity.layer.ChestWoundModel;
import com.valeriotor.beyondtheveil.client.model.entity.layer.WoundModel;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.OperationRegistry;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class PatientWoundLayer extends RenderLayer<CrawlerEntity, CrawlerModel> {
    public static final ResourceLocation WOUND_TEXTURE = new ResourceLocation(References.MODID, "textures/entity/wound.png");
    public static final ResourceLocation CHEST_WOUND_TEXTURE = new ResourceLocation(References.MODID, "textures/entity/chest_wound.png");
    public static WoundModel<CrawlerEntity> woundModel;
    public static ChestWoundModel<CrawlerEntity> chestWoundModel;

    public PatientWoundLayer(RenderLayerParent<CrawlerEntity, CrawlerModel> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        woundModel = new WoundModel<>(pModelSet.bakeLayer(WoundModel.LAYER_LOCATION));
        chestWoundModel = new ChestWoundModel<>(pModelSet.bakeLayer(ChestWoundModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CrawlerEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        PatientStatus status = pLivingEntity.getPatientStatus();
        if (pLivingEntity.isSurgeryPatient() && status.isIncised()) {
            SurgicalLocation exposedLocation = status.getExposedLocation();
            if (exposedLocation == SurgicalLocation.BACK) {
                woundModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                woundModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(woundModel.renderType(getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, !status.getFlags().containsKey(OperationRegistry.SPINELESS));
            } else if (exposedLocation == SurgicalLocation.CHEST) {
                chestWoundModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                chestWoundModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(chestWoundModel.renderType(getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, !status.getFlags().containsKey("extract_heart"));
            }

        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(CrawlerEntity pEntity) {
        if (pEntity.isSurgeryPatient() && pEntity.getPatientStatus().getExposedLocation() == SurgicalLocation.CHEST) {
            return CHEST_WOUND_TEXTURE;
        }
        return WOUND_TEXTURE;
    }
}
