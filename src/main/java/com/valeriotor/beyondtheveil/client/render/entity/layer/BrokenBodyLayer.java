package com.valeriotor.beyondtheveil.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.client.model.entity.layer.BrokenBodyModel;
import com.valeriotor.beyondtheveil.client.model.entity.layer.ChestWoundModel;
import com.valeriotor.beyondtheveil.client.model.entity.layer.WoundModel;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class BrokenBodyLayer<T extends LivingAmmunitionEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation BROKEN_BODY_TEXTURE = new ResourceLocation(References.MODID, "textures/entity/broken_body.png");
    private final BrokenBodyModel brokenBodyModel;

    public BrokenBodyLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.brokenBodyModel = new BrokenBodyModel(pModelSet.bakeLayer(BrokenBodyModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity.isExploding()) {
            brokenBodyModel.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
            brokenBodyModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            renderColoredCutoutModel(brokenBodyModel, getTextureLocation(pLivingEntity), pPoseStack, pBuffer, pPackedLight, pLivingEntity, 1, 1, 1);
//            brokenBodyModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(brokenBodyModel.renderType(getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(T pEntity) {
        return BROKEN_BODY_TEXTURE;
    }
}
