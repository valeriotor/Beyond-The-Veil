package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.IFluidTank;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FlaskBER implements BlockEntityRenderer<FlaskBE> {

    private static final float HEIGHT = 0.0625F * 2;
    private static final double a = 0.0625;

    private final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(new ResourceLocation("minecraft", "textures/atlas/blocks.png")).apply(new ResourceLocation("beyondtheveil:block/heart"));
    private final ItemRenderer itemRenderer;

    public FlaskBER(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(FlaskBE flask, float partialTicks, PoseStack pPoseStack, MultiBufferSource buffers, int pPackedLight, int overlay) {
        FlaskBlock.FlaskSize size = ((FlaskBlock) flask.getBlockState().getBlock()).size;
        if(flask.hasLevel())
            renderFlask(flask.getTank(), flask.getLevel(), flask.getBlockPos(), partialTicks, pPoseStack, buffers, pPackedLight, overlay, size, itemRenderer);
    }

    public static void renderFlask(IFluidTank tank, Level level, BlockPos pos, float partialTicks, PoseStack pPoseStack, MultiBufferSource buffers, int pPackedLight, int overlay, FlaskBlock.FlaskSize size, ItemRenderer itemRenderer) {

        //if(size == FlaskBlock.FlaskSize.LARGE) {
        //    for (int i = 0; i < 6; i++) {
        //        pPoseStack.pushPose();
        //        pPoseStack.translate(0.4F, 0.44, 0.6);
        //        pPoseStack.translate(0, -0.3125F, -i * 0.0475);
        //        pPoseStack.scale(0.25F, 0.25F, 0.25F);
        //        pPoseStack.mulPose(Axis.XP.rotationDegrees(20));
        //        itemRenderer.renderStatic(new ItemStack(Items.REDSTONE), ItemDisplayContext.FIXED, pPackedLight, overlay, pPoseStack, buffers, level, (int) pos.asLong());
        //        pPoseStack.popPose();
        //    }
        //    for (int i = 0; i < 6; i++) {
        //        pPoseStack.pushPose();
        //        pPoseStack.translate(0.6F, 0.44, 0.6);
        //        pPoseStack.translate(0, -0.3125F, -i * 0.0475);
        //        pPoseStack.scale(0.25F, 0.25F, 0.25F);
        //        pPoseStack.mulPose(Axis.XP.rotationDegrees(20));
        //        itemRenderer.renderStatic(new ItemStack(Items.REDSTONE), ItemDisplayContext.FIXED, pPackedLight, overlay, pPoseStack, buffers, level, (int) pos.asLong());
        //        pPoseStack.popPose();
        //    }
        //    for (int i = 0; i < 6; i++) {
        //        pPoseStack.pushPose();
        //        pPoseStack.translate(0.4F, 0.64, 0.6);
        //        pPoseStack.translate(0, -0.3125F, -i * 0.0475);
        //        pPoseStack.scale(0.25F, 0.25F, 0.25F);
        //        pPoseStack.mulPose(Axis.XP.rotationDegrees(20));
        //        itemRenderer.renderStatic(new ItemStack(Items.REDSTONE), ItemDisplayContext.FIXED, pPackedLight, overlay, pPoseStack, buffers, level, (int) pos.asLong());
        //        pPoseStack.popPose();
        //    }
        //    for (int i = 0; i < 6; i++) {
        //        pPoseStack.pushPose();
        //        pPoseStack.translate(0.6F, 0.64, 0.6);
        //        pPoseStack.translate(0, -0.3125F, -i * 0.0475);
        //        pPoseStack.scale(0.25F, 0.25F, 0.25F);
        //        pPoseStack.mulPose(Axis.XP.rotationDegrees(20));
        //        itemRenderer.renderStatic(new ItemStack(Items.REDSTONE), ItemDisplayContext.FIXED, pPackedLight, overlay, pPoseStack, buffers, level, (int) pos.asLong());
        //        pPoseStack.popPose();
        //    }
        //}
        if (tank.getFluidAmount() != 0) {
            float percentFilled = tank.getFluidAmount() / (float) size.getCapacity();
            FluidState fluidState = tank.getFluid().getFluid().defaultFluidState();

            float maxHeight = (float) ((size.getMaxRenderHeight() - size.getMinRenderHeight()) * percentFilled + size.getMinRenderHeight());
            TextureAtlasSprite[] fluidSprites = ForgeHooksClient.getFluidSprites(level, pos, fluidState);
            TextureAtlasSprite stillSprite = fluidSprites[0];
            float minX = (float) size.getMinRenderHorizontal(), maxX = (float) size.getMaxRenderHorizontal();
            float minHeight = (float) size.getMinRenderHeight();

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
        } else if (size == FlaskBlock.FlaskSize.ITEM) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.45F, 0.44, 0.45);
            pPoseStack.translate(0, Mth.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 40) / 40 - 0.2795F, 0);
            pPoseStack.scale(0.175F, 0.175F, 0.175F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(20));
            itemRenderer.renderStatic(new ItemStack(Registration.PLUCKED_EYE.get()), ItemDisplayContext.FIXED, pPackedLight, overlay, pPoseStack, buffers, level, (int) pos.asLong());
            pPoseStack.popPose();
            pPoseStack.pushPose();
            pPoseStack.translate(0.55F, 0.44, 0.45);
            pPoseStack.translate(0, Mth.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 40) / 40 - 0.2325F, 0);
            pPoseStack.scale(0.175F, 0.175F, 0.175F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-20));
            itemRenderer.renderStatic(new ItemStack(Registration.PLUCKED_EYE.get()), ItemDisplayContext.FIXED, pPackedLight, overlay, pPoseStack, buffers, level, (int) pos.asLong());
            pPoseStack.popPose();
            pPoseStack.pushPose();
            pPoseStack.translate(0.5F, 0.44, 0.45);
            pPoseStack.translate(0, Mth.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 40) / 40 - 0.1425F, 0);
            pPoseStack.scale(0.175F, 0.175F, 0.175F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(40));
            itemRenderer.renderStatic(new ItemStack(Registration.PLUCKED_EYE.get()), ItemDisplayContext.FIXED, pPackedLight, overlay, pPoseStack, buffers, level, (int) pos.asLong());
            pPoseStack.popPose();
        }


    }
}
