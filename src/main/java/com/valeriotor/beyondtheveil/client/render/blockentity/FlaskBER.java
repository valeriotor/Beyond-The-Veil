package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.ForgeHooksClient;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FlaskBER implements BlockEntityRenderer<FlaskBE> {

    private static final float HEIGHT = 0.0625F * 2;
    private static final double a = 0.0625;

    private final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(new ResourceLocation("minecraft", "textures/atlas/blocks.png")).apply(new ResourceLocation("beyondtheveil:block/heart"));

    public FlaskBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FlaskBE flask, float partialTicks, PoseStack pPoseStack, MultiBufferSource buffers, int pPackedLight, int overlay) {
        FlaskBlock.FlaskSize size = ((FlaskBlock) flask.getBlockState().getBlock()).size;
        renderFlask(flask, partialTicks, pPoseStack, buffers, pPackedLight, overlay, size);
    }

    public static void renderFlask(FlaskBE flask, float partialTicks, PoseStack pPoseStack, MultiBufferSource buffers, int pPackedLight, int overlay, FlaskBlock.FlaskSize size) {
        if (!flask.hasLevel() || flask.getTank().getFluidAmount() == 0) {
            return;
        }
        float percentFilled = flask.getTank().getFluidAmount() / (float) size.getCapacity();
        FluidState fluidState = flask.getTank().getFluid().getFluid().defaultFluidState();

        float maxHeight = (float) ((size.getMaxRenderHeight() - size.getMinRenderHeight()) * percentFilled + size.getMinRenderHeight());
        TextureAtlasSprite[] fluidSprites = ForgeHooksClient.getFluidSprites(flask.getLevel(), flask.getBlockPos(), fluidState);
        TextureAtlasSprite stillSprite = fluidSprites[0];
        float minX = (float) size.getMinRenderHorizontal(), maxX = (float) size.getMaxRenderHorizontal();
        float minHeight = (float) size.getMinRenderHeight();

        pPoseStack.pushPose();
        Matrix4f model = pPoseStack.last().pose();
        Matrix3f normal = pPoseStack.last().normal();

        VertexConsumer buffer = buffers.getBuffer(Sheets.solidBlockSheet());
        //buffer.vertex(model, 0.4675F + radius, HEIGHT + radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F + radius, HEIGHT + radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F + radius, HEIGHT - radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F + radius, HEIGHT - radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

        //buffer.vertex(model, 0.4675F - radius, HEIGHT + radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT + radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT - radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT - radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

        //buffer.vertex(model, 0.4675F + radius, HEIGHT + radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F + radius, HEIGHT + radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT + radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT + radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

        //buffer.vertex(model, 0.4675F + radius, HEIGHT - radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F + radius, HEIGHT - radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT - radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT - radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

        //buffer.vertex(model, 0.4675F + radius, HEIGHT + radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT + radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT - radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F + radius, HEIGHT - radius, 0.5325F + radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

        //buffer.vertex(model, 0.4675F + radius, HEIGHT + radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT + radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F - radius, HEIGHT - radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
        //buffer.vertex(model, 0.4675F + radius, HEIGHT - radius, 0.5325F - radius).color(1, 0, 0, 1F).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

        //pPoseStack.popPose();

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
