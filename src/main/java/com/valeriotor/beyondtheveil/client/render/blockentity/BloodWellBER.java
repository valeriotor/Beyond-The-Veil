package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.tile.BloodWellBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class BloodWellBER implements BlockEntityRenderer<BloodWellBE> {

    private final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(new ResourceLocation("minecraft", "textures/atlas/blocks.png")).apply(new ResourceLocation("beyondtheveil:block/blood_well"));
    private static final float MAX_Y = 0.65F;

    public BloodWellBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BloodWellBE be, float pPartialTick, PoseStack pose, MultiBufferSource buffers, int pPackedLight, int overlay) {
        VertexConsumer buffer = buffers.getBuffer(Sheets.translucentCullBlockSheet());
        pose.pushPose();
        pose.translate(0.5, 0, 0.5);
        //pose.mulPose(Axis.YP.rotation(1));
        Matrix4f model = pose.last().pose();
        float y = be.didStartup() ? MAX_Y : Math.min(MAX_Y, (be.getCounter() + pPartialTick) / BloodWellBE.STARTUP_TIME * MAX_Y);
        float DIST_FROM_SIDE = -3.5F;
        buffer.vertex(model,DIST_FROM_SIDE, y, DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();
        int pU = 16;
        buffer.vertex(model,DIST_FROM_SIDE, y, - DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(pU), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();
        buffer.vertex(model,- DIST_FROM_SIDE, y, - DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(pU), sprite.getV(pU)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();
        buffer.vertex(model,- DIST_FROM_SIDE, y, DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(0), sprite.getV(pU)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();
        pose.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(BloodWellBE pBlockEntity) {
        return true;
    }
}
