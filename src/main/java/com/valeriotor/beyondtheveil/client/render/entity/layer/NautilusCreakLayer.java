package com.valeriotor.beyondtheveil.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.TestNautilus;
import com.valeriotor.beyondtheveil.client.model.entity.layer.AbominationFlesh1Model;
import com.valeriotor.beyondtheveil.client.model.entity.layer.NautilusCreakModel;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class NautilusCreakLayer extends RenderLayer<NautilusEntity, TestNautilus<NautilusEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/nautilus_creak.png");
    private final NautilusCreakModel<NautilusEntity> nautilusCreakModel;

    public NautilusCreakLayer(RenderLayerParent<NautilusEntity, TestNautilus<NautilusEntity>> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        nautilusCreakModel = new NautilusCreakModel<>(pModelSet.bakeLayer(NautilusCreakModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, NautilusEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        nautilusCreakModel.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        nautilusCreakModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(nautilusCreakModel.renderType(getTextureLocation(pLivingEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(NautilusEntity pEntity) {
        return TEXTURE;
    }
}
