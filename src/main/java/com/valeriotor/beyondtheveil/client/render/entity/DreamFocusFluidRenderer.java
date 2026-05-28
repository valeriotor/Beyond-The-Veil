package com.valeriotor.beyondtheveil.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.entity.CanoeEntity;
import com.valeriotor.beyondtheveil.entity.dream_focus.DreamFocusFluidEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.function.DoubleUnaryOperator;

public class DreamFocusFluidRenderer extends EntityRenderer<DreamFocusFluidEntity> {

    public DreamFocusFluidRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(DreamFocusFluidEntity e, float pEntityYaw, float partialTicks, PoseStack pPoseStack, MultiBufferSource buffers, int pPackedLight) {
        FluidStack fluid = e.getFluid();
        if (!fluid.isEmpty()) {
            FluidState fluidState = fluid.getFluid().defaultFluidState();
            int a = fluid.getAmount();
            IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluidState);
            TextureAtlasSprite stillSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(props.getStillTexture(fluidState, e.level(), e.blockPosition()));

            int number = a <= 100 ? 1 : (a <= 300 ? 2 : (a <= 600 ? 3 : 4));
            for (int i = 0; i < number; i++) {
                float xOff = (float) (baseOffsets[i][0] * 2.5 + 3 * funcs[i].applyAsDouble(e.tickCount + partialTicks));
                float yOff = (float) (baseOffsets[i][1] * 2.5 - 3 * funcs[i].applyAsDouble(e.tickCount + partialTicks));
                float zOff = (float) (baseOffsets[i][2] * 2.5 + 3 * funcs[i].applyAsDouble(e.tickCount + partialTicks));
                float radius = 0.075F;
                pPoseStack.pushPose();
                Matrix4f model = pPoseStack.last().pose();
                Matrix3f normal = pPoseStack.last().normal();

                VertexConsumer buffer = buffers.getBuffer(Sheets.solidBlockSheet());

                buffer.vertex(model, xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

                buffer.vertex(model, xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

                buffer.vertex(model, xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(-1, 0, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

                buffer.vertex(model, xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff + radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, 1)/*.lightmap(upLMa, upLMb)*/.endVertex();

                buffer.vertex(model, xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff - radius, zOff + radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, -1, 0)/*.lightmap(upLMa, upLMb)*/.endVertex();

                buffer.vertex(model, xOff + radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff - radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(0)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff - radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(4), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();
                buffer.vertex(model, xOff + radius, yOff + radius, zOff - radius).color(1, 1, 1, 1F).uv(stillSprite.getU(0), stillSprite.getV(4)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xFFFFFF).normal(0, 0, -1)/*.lightmap(upLMa, upLMb)*/.endVertex();

                pPoseStack.popPose();
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(DreamFocusFluidEntity pEntity) {
        return null;
    }

    private static float[][] baseOffsets = {{-0.03F, 0, 0}, {0, 0.03F, 0}, {-0.06F, 0.02F, 0.04F}, {0.03F, 0.06F, 0}};
    private static DoubleUnaryOperator[] funcs = {
            i -> Math.sin(i%40 / 6.366)/20,
            i -> Math.sin(i%50 / 7.95)/16,
            i -> -Math.sin(i%40 / 6.366)/20,
            i -> -Math.sin(i%50 / 7.95)/16};
}
