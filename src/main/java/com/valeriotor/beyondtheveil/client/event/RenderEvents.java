package com.valeriotor.beyondtheveil.client.event;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
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
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Method;
import java.util.Arrays;

import static net.minecraft.client.renderer.blockentity.BeaconRenderer.BEAM_LOCATION;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvents {

    private static volatile double fov = 0;

    @SubscribeEvent
    public static void fieldOfViewEvent(EntityViewRenderEvent.FieldOfView event) {
        fov = event.getFOV();
    }

    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            int reminisceTimePressed = InputEvents.getReminisceTimePressed();
            if (reminisceTimePressed > 0) {
                PoseStack poseStack = event.getMatrixStack();
                poseStack.pushPose();
                Matrix4f matrix4f = poseStack.last().pose();
                Window window = Minecraft.getInstance().getWindow();
                int alpha = reminisceTimePressed >= 20 ? 255 : (int) (Math.pow((reminisceTimePressed - 1 + event.getPartialTicks()) / 40, 2) * 255);

                innerFill(matrix4f, 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight(), alpha << 24);
                RenderSystem.enableBlend();
                RenderSystem.setShaderTexture(0, NecronomiconGui.RESEARCH_HIGHLIGHT);
                GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
                poseStack.pushPose();
                //poseStack.last().pose().multiply(gameRenderer.getProjectionMatrix(fov));
                for (ClientData.Waypoint waypoint : ClientData.getInstance().waypoints) {
                    if (reminisceTimePressed < 20) {
                        continue;
                    }
                    Vec3 vec3 = gameRenderer.getMainCamera().getPosition();
                    double d0 = vec3.x();
                    double d1 = vec3.y();
                    double d2 = vec3.z();
                    MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                    BlockPos pos = waypoint.pos;
                    pos = new BlockPos(-366, 64, -134);

                    double relX = pos.getX() - d0;
                    double relZ = pos.getZ() - d2;

                    double v = Math.atan2(-relX, relZ) * 180 / Math.PI;
                    float yRot = player.getViewYRot(event.getPartialTicks()) % 360;
                    if (yRot > 180) {
                        yRot -= 360;
                    }
                    double yaw = (yRot - v) % 360;
                    if (yaw > 180) {
                        yaw -= 360;
                    }
                    yaw *= -1;
                    double relY = pos.getY() - d1;
                    double dist = Math.sqrt(relX * relX + relZ * relZ);
                    double v1 = -Math.atan2(relY, dist) * 180 / Math.PI;
                    double pitch = v1 - gameRenderer.getMainCamera().getXRot();
                    double scaleFactor = window.getGuiScaledHeight() / Math.tan((Minecraft.getInstance().options.fov / 2) * Math.PI / 180);
                    int y = (int) (window.getGuiScaledHeight() * pitch / 70);
                    int x = (int) (window.getGuiScaledWidth() * yaw / 102);
                    x = (int) (Math.tan((yaw / 2) * Math.PI / 180) * scaleFactor);
                    y = (int) (Math.tan((pitch / 2) * Math.PI / 180) * scaleFactor);
                    RenderSystem.setShaderColor(1,1,1,1);
                    int ticks = player.tickCount % 20;
                    while (ticks < 70) {
                        poseStack.pushPose();
                        poseStack.translate(x + window.getGuiScaledWidth() / 2 - 12 * (ticks) * 30/ 70, y + window.getGuiScaledHeight() / 2 - 12 * (ticks) * 30/ 70, 0);
                        poseStack.scale((float)(ticks) * 30/ 70, (float)(ticks) * 30 / 70, 1);
                        RenderSystem.setShaderColor(0.5F,0.1F,0.99F,1 - (float) ticks / 70);
                        GuiComponent.blit(poseStack, 0,0, 0, 0, 24, 24, 24, 24);
                        poseStack.popPose();
                        ticks += 20;
                    }
                    //player.getFieldOfViewModifier()
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "fov: " + String.valueOf(Minecraft.getInstance().options.fov), 10, 10, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "relY: " + String.valueOf(relY), 10, 30, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "relZ: " + String.valueOf(relZ), 10, 50, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "yaw: " + String.valueOf(yaw), 10, 70, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "pitch: " + String.valueOf(pitch), 10, 90, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "x: " + String.valueOf(x), 10, 110, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "y: " + String.valueOf(y), 10, 130, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "camera pitch: " + String.valueOf(gameRenderer.getMainCamera().getXRot()), 10, 150, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "v1: " + String.valueOf(v1), 10, 170, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "nearplane topLeft: " + String.valueOf(gameRenderer.getMainCamera().getNearPlane().getTopLeft()), 10, 190, 0xFFFFFFFF);
                    //GuiComponent.drawString(poseStack, Minecraft.getInstance().font, "nearplane topRight: " + String.valueOf(gameRenderer.getMainCamera().getNearPlane().getTopRight()), 10, 210, 0xFFFFFFFF);

                }
                poseStack.popPose();

                //poseStack.pushPose();
                //innerFill(matrix4f, window.getGuiScaledWidth()/3, window.getGuiScaledHeight()/3, window.getGuiScaledWidth()*2/3, window.getGuiScaledHeight()*2/3, (alpha << 24) | 0xFFFFFF);
                //poseStack.popPose();

                //for (ClientData.Waypoint waypoint : ClientData.getInstance().waypoints) {
                //    poseStack.pushPose();
                //    poseStack.translate(0,0,-1);
                //    innerFill(matrix4f, window.getWidth()/3, window.getHeight()/3, window.getWidth()*2/3, window.getHeight()*2/3, (alpha << 24) | 0xFFFFFF);
                //    poseStack.popPose();
                //}
                poseStack.popPose();
            }
        }
    }

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderLevelLastEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            for (ClientData.Waypoint waypoint : ClientData.getInstance().waypoints) {


                //draw(event.getPoseStack());
                float pPartialTicks = event.getPartialTick();
                Vec3 vec3 = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
                double d0 = vec3.x();
                double d1 = vec3.y();
                double d2 = vec3.z();
                MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                event.getPoseStack().pushPose();
                BlockPos pos = waypoint.pos;
                pos = new BlockPos(-366, 64, -134);
                double distanceSqr = vec3.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                Vec3 subtract = new Vec3(pos.getX(), pos.getY(), pos.getZ()).subtract(vec3);
                if (subtract.x * subtract.x + subtract.z * subtract.z > 256 * 256) {
                    double ratio = 256 / subtract.length();
                    subtract = new Vec3(subtract.x * ratio, subtract.y, subtract.z * ratio);
                    // pos = new BlockPos(pos.getX() / ratio, pos.getY() / ratio, pos.getZ() / ratio);
                }
                event.getPoseStack().translate(subtract.x(), pos.getY() - d1, subtract.z());
                //event.getPoseStack().translate(3, 3, 3);
                final float beamRadius = 0.5F;
                float f = (float) Math.floorMod(player.tickCount, 40) + event.getPartialTick();
                float f1 = 1024 < 0 ? f : -f;
                float f2 = Mth.frac(f1 * 0.2F - (float) Mth.floor(f1 * 0.1F));
                float f15 = -1.0F + f2;
                float f16 = (float) 1024 * 1 * (0.5F / beamRadius) + f15;
                event.getPoseStack().mulPose(Vector3f.YP.rotationDegrees(f * 2.25F - 45.0F));
                //renderBeaconBeam(event.getPoseStack(), bufferSource, BEAM_LOCATION, event.getPartialTick(), 1.0F, player.tickCount, 0, 1024, new float[]{1,0,0,1}, 0.2F, 0.25F);
                renderPart(event.getPoseStack(), bufferSource.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false)), 1, 0, 1, 1.0F, 0, 300, 0.0F, beamRadius, beamRadius, 0.0F, -beamRadius, 0.0F, 0.0F, -beamRadius, 0.0F, 1.0F, f16, f15);
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

    private static void innerFill(Matrix4f pMatrix, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
        if (pMinX < pMaxX) {
            int i = pMinX;
            pMinX = pMaxX;
            pMaxX = i;
        }

        if (pMinY < pMaxY) {
            int j = pMinY;
            pMinY = pMaxY;
            pMaxY = j;
        }

        float f3 = (float) (pColor >> 24 & 255) / 255.0F;
        float f = (float) (pColor >> 16 & 255) / 255.0F;
        float f1 = (float) (pColor >> 8 & 255) / 255.0F;
        float f2 = (float) (pColor & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }


}
