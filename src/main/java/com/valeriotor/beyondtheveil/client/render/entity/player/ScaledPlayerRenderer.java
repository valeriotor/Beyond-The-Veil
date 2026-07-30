package com.valeriotor.beyondtheveil.client.render.entity.player;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.model.entity.ScaledPlayerModel;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class ScaledPlayerRenderer extends LivingEntityRenderer<LivingEntity, ScaledPlayerModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/scaled_player.png");

    public ScaledPlayerRenderer(EntityRendererProvider.Context context) {
        super(context, new ScaledPlayerModel(context.bakeLayer(ScaledPlayerModel.LAYER_LOCATION)), 1);
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntity pEntity) {
        return TEXTURE;
    }

    public void renderHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm) {
        RenderSystem.setShaderTexture(0, getTextureLocation(pPlayer));
        ScaledPlayerModel playermodel = this.getModel();
        this.setModelProperties(pPlayer);
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        pRendererArm.xRot = 0.0F;
        pRendererArm.render(pPoseStack, pBuffer.getBuffer(RenderType.entitySolid(getTextureLocation(pPlayer))), pCombinedLight, OverlayTexture.NO_OVERLAY);
        //pRendererArmwear.xRot = 0.0F;
        //pRendererArmwear.render(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(pPlayer.getSkinTextureLocation())), pCombinedLight, OverlayTexture.NO_OVERLAY);
    }

    private void setModelProperties(AbstractClientPlayer pPlayer) {
        HumanoidModel.ArmPose humanoidmodel$armpose = PlayerRenderer.getArmPose(pPlayer, InteractionHand.MAIN_HAND);
        HumanoidModel.ArmPose humanoidmodel$armpose1 = PlayerRenderer.getArmPose(pPlayer, InteractionHand.OFF_HAND);
        if (humanoidmodel$armpose.isTwoHanded()) {
            humanoidmodel$armpose1 = pPlayer.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
        }
        if (pPlayer.getMainArm() == HumanoidArm.RIGHT) {
            getModel().rightArmPose = humanoidmodel$armpose;
            getModel().leftArmPose = humanoidmodel$armpose1;
        } else {
            getModel().rightArmPose = humanoidmodel$armpose1;
            getModel().leftArmPose = humanoidmodel$armpose;
        }
    }
}
