package com.valeriotor.beyondtheveil.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.client.model.entity.layer.ChestWoundModel;
import com.valeriotor.beyondtheveil.client.model.entity.layer.WoundModel;
import com.valeriotor.beyondtheveil.lib.References;
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

public class PatientWoundLayer<T extends LivingEntity & SurgeryPatient, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation WOUND_TEXTURE = new ResourceLocation(References.MODID, "textures/entity/wound.png");
    private final WoundModel<T> woundModel;
    private final ChestWoundModel<T> chestWoundModel;

    public PatientWoundLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.woundModel = new WoundModel<>(pModelSet.bakeLayer(WoundModel.LAYER_LOCATION));
        this.chestWoundModel = new ChestWoundModel<>(pModelSet.bakeLayer(ChestWoundModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity.isSurgeryPatient() && pLivingEntity.getPatientStatus().isIncised()) {
            SurgicalLocation exposedLocation = pLivingEntity.getPatientStatus().getExposedLocation();
            if (exposedLocation == SurgicalLocation.BACK) {
                woundModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(woundModel.renderType(getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            } else if (exposedLocation == SurgicalLocation.CHEST) {
                chestWoundModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                chestWoundModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(chestWoundModel.renderType(getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }

        }
    }

    @Override
    protected ResourceLocation getTextureLocation(T pEntity) {
        return WOUND_TEXTURE;
    }
}
