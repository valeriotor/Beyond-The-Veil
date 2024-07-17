package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.tile.WateryCradleBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class WateryCradleBER implements BlockEntityRenderer<WateryCradleBE> {

    private final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(new ResourceLocation("minecraft", "textures/atlas/blocks.png")).apply(new ResourceLocation("beyondtheveil:block/blood_splatter"));

    public WateryCradleBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WateryCradleBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource buffers, int pPackedLight, int overlay) {
        Mob entity = pBlockEntity.getBackupEntity() == null ? pBlockEntity.getEntity() : pBlockEntity.getBackupEntity();
        if (entity != null) {
            pPoseStack.pushPose();
            pPoseStack.scale(0.93F, 0.8F, 0.93F);
            //pPoseStack.mulPose(Quaternion.fromYXZ((float) Math.PI/2, 0, 0));
            Direction value = pBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            pPoseStack.translate(0.525, 0.31, 0.52);
            pPoseStack.mulPose(Axis.YP.rotation((float) ((-value.get2DDataValue() - 1) * Math.PI / 2)));
            Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0, 0, 0, 0, pPartialTick, pPoseStack, buffers, pPackedLight);


            pPoseStack.popPose();

            float weeperCreatedTicks = pBlockEntity.getWeeperCreatedTicks() - pPartialTick;
            if (weeperCreatedTicks > 0) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5, 0, 0.5);
                pPoseStack.mulPose(Axis.YP.rotation((float) ((-value.get2DDataValue()) * Math.PI / 2))); // oof I messed up x and z below, so -1 is not here for a quick fix
                Matrix4f model = pPoseStack.last().pose();
                VertexConsumer buffer = buffers.getBuffer(Sheets.translucentCullBlockSheet());
                final float a = 0.0625F;
                final float width1 = 40 / 46F * 16;
                final float width2 = 42 / 46F * 16;
                final float width3 = 44 / 46F * 16;
                final float width4 = 16;
                final float alpha = Math.min(1F, weeperCreatedTicks / 40F);
                buffer.vertex(model, 20 * a, 15 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 20 * a, 15 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 15 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width1), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 15 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width1), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, -20 * a, 15 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 15 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14 + 1 / 46F), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14 + 1 / 46F), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, -20 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(16), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(16), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, -21 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(15), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(15), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 13 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(15 + 1 / 46F), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 13 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(15 + 1 / 46F), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, -21 * a, 13 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 13 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(16), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(16), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, -22 * a, 13 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(12), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(12 + 16 * 12 / 46F), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 12 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(12 + 16 * 12 / 46F), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 12 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(12), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, -22 * a, 12 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 12 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(16), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(16), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(14), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, -23 * a, 12 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(12), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(12 + 16 * 12 / 46F), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 10 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(12 + 16 * 12 / 46F), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 10 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(12), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();


                buffer.vertex(model, 20 * a, 15 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 20 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width1), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 15 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width1), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 21 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 21 * a, 14 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, -4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 21 * a, 14 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 21 * a, 13 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 13 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 22 * a, 13 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 22 * a, 13 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, -5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 22 * a, 13 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(14)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 22 * a, 12 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 12 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(14)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 23 * a, 12 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 23 * a, 12 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, -6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 23 * a, 12 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 23 * a, 10 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 10 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, -7 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();


                buffer.vertex(model, 20 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 20 * a, 15 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 15 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width1), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -20 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width1), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 21 * a, 14 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 21 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, 4 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 21 * a, 13 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 21 * a, 14 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 14 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -21 * a, 13 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width2), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 22 * a, 13 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 22 * a, 13 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, 5 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 22 * a, 12 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(14)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 22 * a, 13 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 13 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(16)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -22 * a, 12 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width3), sprite.getV(14)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 23 * a, 12 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 23 * a, 12 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, 6 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(2)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                buffer.vertex(model, 23 * a, 10 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, 23 * a, 12 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 12 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();
                buffer.vertex(model, -23 * a, 10 * a, 7 * a).color(1, 0, 0, alpha).uv(sprite.getU(width4), sprite.getV(4)).overlayCoords(overlay).uv2(0xFFFFFF).normal(1, 0, 0)./*lightmap(upLMa, upLMb).*/endVertex();

                pPoseStack.popPose();
            }

        }
    }

    @Override
    public boolean shouldRenderOffScreen(WateryCradleBE pBlockEntity) {
        return true;
    }
}
