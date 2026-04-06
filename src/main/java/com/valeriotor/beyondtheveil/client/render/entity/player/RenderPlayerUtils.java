package com.valeriotor.beyondtheveil.client.render.entity.player;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.animation.Animation;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import com.valeriotor.beyondtheveil.client.model.entity.wrapper.PlayerDefaultModelWrapper;
import com.valeriotor.beyondtheveil.client.util.CrossSyncHolder;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

import java.util.List;

public class RenderPlayerUtils {

    public static void renderArms(PoseStack pPoseStack, Camera pActiveRenderInfo, float pPartialTicks, MultiBufferSource.BufferSource source) {
        if (ClientData.getInstance().getFirstPersonAnimations().isEmpty() || Minecraft.getInstance().player == null) {
            return;
        }
        ClientData.getInstance().toggleThirdPersonAnimations(false);
        CrossSync crossSync = CrossSyncHolder.getCrossSync(Minecraft.getInstance().player);
        if (crossSync != null && crossSync.getTransformation() != null) {
            return;
        }
        RenderSystem.clear(256, Minecraft.ON_OSX);
        Minecraft mc = Minecraft.getInstance();
        GameRenderer gr = mc.gameRenderer;
        gr.resetProjectionMatrix(gr.getProjectionMatrix(70));
        pPoseStack.setIdentity();
        pPoseStack.pushPose();
        //gr.bobHurt(pPoseStack, pPartialTicks);
        //if (mc.options.bobView().get()) {
        //    this.bobView(pPoseStack, pPartialTicks);
        //}

        boolean flag = mc.getCameraEntity() instanceof LivingEntity && ((LivingEntity) mc.getCameraEntity()).isSleeping();
        if (mc.options.getCameraType().isFirstPerson() && !flag && !mc.options.hideGui && mc.gameMode != null && mc.gameMode.getPlayerMode() != GameType.SPECTATOR && mc.player != null) {
            gr.lightTexture().turnOnLightLayer();
            renderHandsWithItems(pPartialTicks, pPoseStack, source, mc.player, mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, pPartialTicks));
            gr.lightTexture().turnOffLightLayer();
        }

        pPoseStack.popPose();
        //if (mc.options.getCameraType().isFirstPerson() && !flag) {
        //    ScreenEffectRenderer.renderScreenEffect(mc, pPoseStack);
        //    gr.bobHurt(pPoseStack, pPartialTicks);
        //}

        //if (mc.options.bobView().get()) {
        //    gr.bobView(pPoseStack, pPartialTicks);
        //}
        ClientData.getInstance().toggleThirdPersonAnimations(true);
    }

    private static void renderHandsWithItems(float pPartialTicks, PoseStack pPoseStack, MultiBufferSource.BufferSource pBuffer, LocalPlayer pPlayerEntity, int pCombinedLight) {
        float f = pPlayerEntity.getAttackAnim(pPartialTicks);
        InteractionHand interactionhand = MoreObjects.firstNonNull(pPlayerEntity.swingingArm, InteractionHand.MAIN_HAND);
        float f1 = Mth.lerp(pPartialTicks, pPlayerEntity.xRotO, pPlayerEntity.getXRot());
        float f2 = Mth.lerp(pPartialTicks, pPlayerEntity.xBobO, pPlayerEntity.xBob);
        float f3 = Mth.lerp(pPartialTicks, pPlayerEntity.yBobO, pPlayerEntity.yBob);
        pPoseStack.mulPose(Axis.XP.rotationDegrees((pPlayerEntity.getViewXRot(pPartialTicks) - f2) * 0.1F));
        pPoseStack.mulPose(Axis.YP.rotationDegrees((pPlayerEntity.getViewYRot(pPartialTicks) - f3) * 0.1F));
        if (true) {
            float f4 = interactionhand == InteractionHand.MAIN_HAND ? f : 0.0F;
            float f5 = 1.0F - Mth.lerp(pPartialTicks, /*this.oMainHandHeight*/1, /*this.mainHandHeight*/1);
            renderArmWithItem(pPlayerEntity, pPartialTicks, f1, InteractionHand.MAIN_HAND, f4, ItemStack.EMPTY, f5, pPoseStack, pBuffer, pCombinedLight);
        }

        if (true) {
            float f6 = interactionhand == InteractionHand.OFF_HAND ? f : 0.0F;
            float f7 = 1.0F - Mth.lerp(pPartialTicks, /*this.oOffHandHeight*/1, /*this.offHandHeight*/1);
            renderArmWithItem(pPlayerEntity, pPartialTicks, f1, InteractionHand.OFF_HAND, f6, ItemStack.EMPTY, f7, pPoseStack, pBuffer, pCombinedLight);
        }

        pBuffer.endBatch();
    }

    private static void renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight) {
        if (!pPlayer.isScoping()) {
            boolean flag = pHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidarm = !flag ? pPlayer.getMainArm() : pPlayer.getMainArm().getOpposite();
            pPoseStack.pushPose();
            if (pStack.isEmpty()) {
                if (!pPlayer.isInvisible()) {
                    renderPlayerArm(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, humanoidarm);
                }
            }
            pPoseStack.popPose();
        }
    }

    private static void renderPlayerArm(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, float pEquippedProgress, float pSwingProgress, HumanoidArm pSide) {
        Minecraft mc = Minecraft.getInstance();
        boolean flag = pSide != HumanoidArm.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = Mth.sqrt(pSwingProgress);
        float f2 = -0.3F * Mth.sin(f1 * (float) Math.PI);
        float f3 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
        float f4 = -0.4F * Mth.sin(pSwingProgress * (float) Math.PI);
        pPoseStack.translate(f * (f2 + 0.64000005F), f3 + -0.6F + pEquippedProgress * -0.6F, f4 + -0.71999997F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(f * 45.0F));
        float f5 = Mth.sin(pSwingProgress * pSwingProgress * (float) Math.PI);
        float f6 = Mth.sin(f1 * (float) Math.PI);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(f * f6 * 70.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(f * f5 * -20.0F));
        AbstractClientPlayer abstractclientplayer = mc.player;
        if (abstractclientplayer != null) {
            RenderSystem.setShaderTexture(0, abstractclientplayer.getSkinTextureLocation());
            pPoseStack.translate(f * -1.0F, 3.6F, 3.5F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
            pPoseStack.translate(f * 5.6F, 0.0F, 0.0F);
            PlayerRenderer playerrenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(abstractclientplayer);

            String modelName = abstractclientplayer.getModelName();
            PlayerDefaultModelWrapper model = "slim".equals(modelName) ? AnimatedModel.playerSlimModel : AnimatedModel.playerModel;
            model.markDirty();
            model.resetParts();
            Animation firstPersonAnimation = ClientData.getInstance().getFirstPersonAnimations(model);
            if (flag) {
                //pPoseStack.mulPose(Axis.XP.rotation(5.7F));
                //pPoseStack.translate(0, 0.25, 0);
                if (firstPersonAnimation != null) {
                    firstPersonAnimation.apply(mc.getPartialTick());
                }
                //playerrenderer.getModel().rightArm.yRot = -0.5F;
                //playerrenderer.getModel().rightArm.xRot = -0.655F;
                //playerrenderer.getModel().rightArm.zRot = -0.3F;
                //playerrenderer.getModel().rightArm.y = 0.8F;

                //playerrenderer.getModel().rightArm.xRot = -0.855F;
                //playerrenderer.getModel().rightArm.xRot = -0.38F;
                //playerrenderer.getModel().rightArm.zRot = 0.48F;
                //playerrenderer.getModel().rightArm.y = 6.8F;
                //playerrenderer.getModel().rightArm.zRot = 0.18F;
                //playerrenderer.getModel().rightArm.xRot = -0.58F;

                //playerrenderer.getModel().rightArm.zRot = 0.88F;
                playerrenderer.getModel().rightArm.translateAndRotate(pPoseStack);
                playerrenderer.renderHand(pPoseStack, pBuffer, pCombinedLight, abstractclientplayer, playerrenderer.getModel().rightArm, playerrenderer.getModel().rightSleeve);
            } else {
                //pPoseStack.mulPose(Axis.XP.rotation(5.7F));
                //pPoseStack.translate(0, 0.25, 0);
                if (firstPersonAnimation != null) {
                    firstPersonAnimation.apply(mc.getPartialTick());
                }
                //playerrenderer.getModel().leftArm.yRot = 0.5F;
                //playerrenderer.getModel().leftArm.xRot = -0.655F;
                //playerrenderer.getModel().leftArm.zRot = 0.3F;
                //playerrenderer.getModel().leftArm.y = 0.8F;

                //playerrenderer.getModel().leftArm.xRot = -0.455F;
                //playerrenderer.getModel().leftArm.xRot = -1.18F;
                //playerrenderer.getModel().leftArm.zRot = -0.68F;
                //playerrenderer.getModel().leftArm.y = 5.8F;
                //playerrenderer.getModel().leftArm.z = -7.8F;
                //playerrenderer.getModel().leftArm.zRot = -0.28F;
                //playerrenderer.getModel().leftArm.xRot = -0.78F;
                //playerrenderer.getModel().leftArm.z = -4.8F;

                //playerrenderer.getModel().leftArm.zRot = -0.88F;
                playerrenderer.getModel().leftArm.translateAndRotate(pPoseStack);
                playerrenderer.renderHand(pPoseStack, pBuffer, pCombinedLight, abstractclientplayer, playerrenderer.getModel().leftArm, playerrenderer.getModel().leftSleeve);
            }
            model.resetParts();
        }

    }

}
