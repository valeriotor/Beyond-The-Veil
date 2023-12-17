package com.valeriotor.beyondtheveil.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.client.reminiscence.ReminiscenceClient;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvents {

    //private static volatile double fov = 0;
//
    //@SubscribeEvent
    //public static void fieldOfViewEvent(EntityViewRenderEvent.FieldOfView event) {
    //    fov = event.getFOV();
    //}

    @SubscribeEvent
    public static void renderFlaskShelfOutline(RenderHighlightEvent.Block event) {
        BlockPos blockPos = event.getTarget().getBlockPos();
        Level l = Minecraft.getInstance().level;
        if (l != null) {
            BlockState blockState = l.getBlockState(blockPos);
            if (blockState.getBlock() == Registration.FLASK_SHELF.get()) {
                event.setCanceled(true);
                VertexConsumer vertexconsumer2 = event.getMultiBufferSource().getBuffer(RenderType.lines());

                Vec3 vec3 = event.getCamera().getPosition();
                double d0 = vec3.x();
                double d1 = vec3.y();
                double d2 = vec3.z();
                renderHitOutline(event.getPoseStack(), vertexconsumer2, event.getCamera().getEntity(), d0, d1, d2, event.getTarget().getBlockPos(), blockState, l, FlaskShelfBlock.getBaseShape(blockState));
            }
        }
    }

    private static void renderHitOutline(PoseStack pPoseStack, VertexConsumer pConsumer, Entity pEntity, double pCamX, double pCamY, double pCamZ, BlockPos pPos, BlockState pState, Level l, VoxelShape shape) {
        renderShape(pPoseStack, pConsumer, shape, (double)pPos.getX() - pCamX, (double)pPos.getY() - pCamY, (double)pPos.getZ() - pCamZ, 0.0F, 0.0F, 0.0F, 0.4F);
    }

    private static void renderShape(PoseStack pPoseStack, VertexConsumer pConsumer, VoxelShape pShape, double pX, double pY, double pZ, float pRed, float pGreen, float pBlue, float pAlpha) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        pShape.forAllEdges((p_234280_, p_234281_, p_234282_, p_234283_, p_234284_, p_234285_) -> {
            float f = (float)(p_234283_ - p_234280_);
            float f1 = (float)(p_234284_ - p_234281_);
            float f2 = (float)(p_234285_ - p_234282_);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f /= f3;
            f1 /= f3;
            f2 /= f3;
            pConsumer.vertex(posestack$pose.pose(), (float)(p_234280_ + pX), (float)(p_234281_ + pY), (float)(p_234282_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
            pConsumer.vertex(posestack$pose.pose(), (float)(p_234283_ + pX), (float)(p_234284_ + pY), (float)(p_234285_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
        });
    }

    @SubscribeEvent
    public static void renderGameOverlay(RenderGuiOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ReminiscenceClient.renderReminiscence(event);

        }
    }

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderLevelStageEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
        }
    }

    /*@Deprecated
    private static void legacyWaypointVisualization(RenderLevelStageEvent event, LocalPlayer player) {
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
    }*/

    public static void innerFill(Matrix4f pMatrix, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
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
        //RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end()); // TODO with shader or just draw()?
        //RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }


}
