package com.valeriotor.beyondtheveil.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.layer.AbominationFlesh1Model;
import com.valeriotor.beyondtheveil.entity.LivingAmmunitionEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AbominationFlesh1Layer<T extends LivingAmmunitionEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation ABOMINATION_FLESH_TEXTURE = new ResourceLocation(References.MODID, "textures/entity/abomination_flesh.png");
    private final AbominationFlesh1Model abominationFlesh1Model;

    public AbominationFlesh1Layer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.abominationFlesh1Model = new AbominationFlesh1Model(pModelSet.bakeLayer(AbominationFlesh1Model.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (true || pLivingEntity.isExploding()) { // TODO check that it is type 1
            abominationFlesh1Model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
            abominationFlesh1Model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            renderColoredCutoutModel(abominationFlesh1Model, getTextureLocation(pLivingEntity), pPoseStack, pBuffer, pPackedLight, pLivingEntity, 1, 1, 1);
//            brokenBodyModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(brokenBodyModel.renderType(getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(T pEntity) {
        return ABOMINATION_FLESH_TEXTURE;
    }
}
