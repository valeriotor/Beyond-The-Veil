package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.tile.MegydreaBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class MegydreaBER implements BlockEntityRenderer<MegydreaBE> {

    public MegydreaBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MegydreaBE be, float pPartialTick, PoseStack pPoseStack, MultiBufferSource buffers, int pPackedLight, int overlay) {
        FluidTank tank = be.getTank();
        if (tank.getFluidAmount() != 0 && be.getLevel() != null) {
            final int CAPACITY = 40000;
            final double a = 0.0625;
            float percentFilled = tank.getFluidAmount() / (float) CAPACITY;
            FluidState fluidState = tank.getFluid().getFluid().defaultFluidState();

            float maxHeight = (float) ((26 * a - 5 * a) * percentFilled + 5 * a);
            TextureAtlasSprite[] fluidSprites = ForgeHooksClient.getFluidSprites(be.getLevel(), be.getBlockPos(), fluidState);
            TextureAtlasSprite stillSprite = fluidSprites[0];
            float minX = (float) (4 * a), maxX = (float) (12 * a);
            float minHeight = (float) (5 * a);

            pPoseStack.pushPose();
            Matrix4f model = pPoseStack.last().pose();
            Matrix3f normal = pPoseStack.last().normal();

            VertexConsumer buffer = buffers.getBuffer(Sheets.solidBlockSheet());

            buffer.vertex(model, maxX, minHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, minHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, maxHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, maxHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

            buffer.vertex(model, maxX, maxHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, maxHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, maxHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, maxHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

            buffer.vertex(model, minX, minHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, minHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, maxHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, maxHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

            buffer.vertex(model, minX, minHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, minHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, maxHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, maxHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();

            buffer.vertex(model, maxX, minHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, minHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, minHeight, maxX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, minHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

            buffer.vertex(model, maxX, minHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, minHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, minX, maxHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();
            buffer.vertex(model, maxX, maxHeight, minX).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();

            pPoseStack.popPose();
        }
    }
}
