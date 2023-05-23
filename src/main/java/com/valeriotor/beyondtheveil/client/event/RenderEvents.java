package com.valeriotor.beyondtheveil.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.client.renderer.blockentity.BeaconRenderer.BEAM_LOCATION;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvents {

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderLevelLastEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            for (ClientData.Waypoint waypoint: ClientData.getInstance().waypoints) {


                //draw(event.getPoseStack());
                float pPartialTicks = event.getPartialTick();
                Vec3 vec3 = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
                double d0 = vec3.x();
                double d1 = vec3.y();
                double d2 = vec3.z();
                MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                event.getPoseStack().pushPose();
                BlockPos pos = waypoint.pos;
                event.getPoseStack().translate(pos.getX() - d0, pos.getY() - d1, pos.getZ() - d2);
                //event.getPoseStack().translate(3, 3, 3);
                float f = (float) Math.floorMod(player.tickCount, 40) + event.getPartialTick();
                float f1 = 1024 < 0 ? f : -f;
                float f2 = Mth.frac(f1 * 0.2F - (float) Mth.floor(f1 * 0.1F));
                float f15 = -1.0F + f2;
                float f16 = (float) 1024 * 1 * (0.5F / 0.25F) + f15;
                event.getPoseStack().mulPose(Vector3f.YP.rotationDegrees(f * 2.25F - 45.0F));
                //renderBeaconBeam(event.getPoseStack(), bufferSource, BEAM_LOCATION, event.getPartialTick(), 1.0F, player.tickCount, 0, 1024, new float[]{1,0,0,1}, 0.2F, 0.25F);
                renderPart(event.getPoseStack(), bufferSource.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false)), 1, 1, 1, 1.0F, 0, 300, 0.0F, 0.2F, 0.25F, 0.0F, -0.25F, 0.0F, 0.0F, -0.25F, 0.0F, 1.0F, f16, f15);
                event.getPoseStack().popPose();
                RenderSystem.clear(256, Minecraft.ON_OSX);
                bufferSource.endBatch();
                //renderPart(event.getPoseStack(), bufferSource.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false)), 1, 1, 1, 1.0F, 0, 100, 0.0F, 0.2F, f7, pGlowRadius, -pGlowRadius, pGlowRadius, pGlowRadius, pGlowRadius, 0.0F, 1.0F, f16, f15);


                //float texScale = 1.0F;
                //int heightOffset = 360;
                ////float texOffset = -((float)-player.tickCount * 0.2F - Mth.m_14143_((float)-gameTime * 0.1F)) * 0.6F;
                //float red = 1;
                //float blue = 1;
                //float green = 1;
                //float alpha = 1;
                //VertexConsumer beamBuffer = bufferSource.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false));
                ////float f2 = Mth.lerp(pPartialTicks, player.xBobO, player.xBob);
                ////float f3 = Mth.lerp(pPartialTicks, player.yBobO, player.yBob);
//
                //float f = player.walkDist - player.walkDistO;
                //float f1 = -(player.walkDist + f * pPartialTicks);
                //float f2 = Mth.lerp(pPartialTicks, player.oBob, player.bob);
                //event.getPoseStack().translate((double)-(Mth.sin(f1 * (float)Math.PI) * f2 * 0.5F), (double)-(-Math.abs(Mth.cos(f1 * (float)Math.PI) * f2)), 0.0D);
                //event.getPoseStack().mulPose(Vector3f.ZP.rotationDegrees(-Mth.sin(f1 * (float)Math.PI) * f2 * 3.0F));
                //event.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(-Math.abs(Mth.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));
//
                //float V2 = -1.0F + 0;
                //float innerV1 = 360 * texScale * 0.5F / 2 + V2;
                //renderPart(event.getPoseStack(), beamBuffer, red, blue, green, alpha, 0, heightOffset, 0.0F, 2, 2, 0.0F, -2, 0.0F, 0.0F, -2, 0.0F, 1.0F, innerV1, V2);
                //event.getPoseStack().popPose();
                //Minecraft.getInstance().gameRenderer.resetProjectionMatrix(Minecraft.getInstance().gameRenderer.getProjectionMatrix(fov));
//
                ////bufferSource.endLastBatch();
            }
        }
    }

    public static void renderBeaconBeam(PoseStack pPoseStack, MultiBufferSource.BufferSource pBufferSource, ResourceLocation pBeamLocation, float pPartialTick, float pTextureScale, long pGameTime, int pYOffset, int pHeight, float[] pColors, float pBeamRadius, float pGlowRadius) {
        int i = pYOffset + pHeight;
        pPoseStack.pushPose();
        pPoseStack.translate(0.5D, 0.0D, 0.5D);
        float f = (float) Math.floorMod(pGameTime, 40) + pPartialTick;
        float f1 = pHeight < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float) Mth.floor(f1 * 0.1F));
        float f3 = pColors[0];
        float f4 = pColors[1];
        float f5 = pColors[2];
        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(f * 2.25F - 45.0F));
        float f6 = 0.0F;
        float f8 = 0.0F;
        float f9 = -pBeamRadius;
        float f10 = 0.0F;
        float f11 = 0.0F;
        float f12 = -pBeamRadius;
        float f13 = 0.0F;
        float f14 = 1.0F;
        float f15 = -1.0F + f2;
        float f16 = (float) pHeight * pTextureScale * (0.5F / pBeamRadius) + f15;
        renderPart(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(pBeamLocation, false)), f3, f4, f5, 1.0F, pYOffset, i, 0.0F, pBeamRadius, pBeamRadius, 0.0F, f9, 0.0F, 0.0F, f12, 0.0F, 1.0F, f16, f15);
        pPoseStack.popPose();
        f6 = -pGlowRadius;
        float f7 = -pGlowRadius;
        f8 = -pGlowRadius;
        f9 = -pGlowRadius;
        f13 = 0.0F;
        f14 = 1.0F;
        f15 = -1.0F + f2;
        f16 = (float) pHeight * pTextureScale + f15;
        renderPart(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(pBeamLocation, true)), f3, f4, f5, 0.125F, pYOffset, i, f6, f7, pGlowRadius, f8, f9, pGlowRadius, pGlowRadius, pGlowRadius, 0.0F, 1.0F, f16, f15);
        pPoseStack.popPose();

    }

    private static void renderPart(PoseStack pPoseStack, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pMinY, int pMaxY, float pX0, float pZ0, float pX1, float pZ1, float pX2, float pZ2, float pX3, float pZ3, float pMinU, float pMaxU, float pMinV, float pMaxV) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX0, pZ0, pX1, pZ1, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX3, pZ3, pX2, pZ2, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX1, pZ1, pX3, pZ3, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX2, pZ2, pX0, pZ0, pMinU, pMaxU, pMinV, pMaxV);
    }

    private static void renderQuad(Matrix4f pPose, Matrix3f pNormal, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pMinY, int pMaxY, float pMinX, float pMinZ, float pMaxX, float pMaxZ, float pMinU, float pMaxU, float pMinV, float pMaxV) {
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMinX, pMinZ, pMaxU, pMinV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMinX, pMinZ, pMaxU, pMaxV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxX, pMaxZ, pMinU, pMaxV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMaxX, pMaxZ, pMinU, pMinV);
    }

    private static void addVertex(Matrix4f pPose, Matrix3f pNormal, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pY, float pX, float pZ, float pU, float pV) {
        pConsumer.vertex(pPose, pX, (float) pY, pZ).color(pRed, pGreen, pBlue, pAlpha).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }


}
