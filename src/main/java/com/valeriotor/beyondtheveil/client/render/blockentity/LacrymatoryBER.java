package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import com.valeriotor.beyondtheveil.tile.LacrymatoryBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.ForgeHooksClient;
import org.joml.Matrix4f;

public class LacrymatoryBER implements BlockEntityRenderer<LacrymatoryBE> {

    private static final float DIST_FROM_SIDE = 0;//0.0625 * 3;
    private static final float MIN_HEIGHT = 0.0625F * 3;
    private static final float MAX_HEIGHT = 0.0625F * 12;
    private final TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(new ResourceLocation("minecraft", "textures/atlas/blocks.png")).apply(new ResourceLocation("beyondtheveil:block/fluids/tears_still"));

    public LacrymatoryBER(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(LacrymatoryBE pBlockEntity, float partialTicks, PoseStack pose, MultiBufferSource buffers, int pPackedLight, int overlay) {
        int fluidAmount = pBlockEntity.getFluidAmount();
        if (fluidAmount > 0) {
            pose.pushPose();
            TextureAtlasSprite sprite = this.sprite;
            Matrix4f model = pose.last().pose();
            VertexConsumer buffer = buffers.getBuffer(Sheets.solidBlockSheet());
            float y = MIN_HEIGHT + (MAX_HEIGHT - MIN_HEIGHT) * fluidAmount / 5000;
            buffer.vertex(model,DIST_FROM_SIDE, y, DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(0), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();
            int pU = 14;
            buffer.vertex(model,DIST_FROM_SIDE, y, 1 - DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(pU), sprite.getV(0)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();
            buffer.vertex(model,1 - DIST_FROM_SIDE, y, 1 - DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(pU), sprite.getV(pU)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();
            buffer.vertex(model,1 - DIST_FROM_SIDE, y, DIST_FROM_SIDE).color(1, 1,1, 1F).uv(sprite.getU(0), sprite.getV(pU)).overlayCoords(overlay).uv2(0xFFFFFF).normal(0,1,0)./*lightmap(upLMa, upLMb).*/endVertex();

            pose.popPose();
        }
    }
}
