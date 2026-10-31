package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.tile.HeartBE;
import com.valeriotor.beyondtheveil.tile.MemorySieveBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class MemorySieveBER implements BlockEntityRenderer<MemorySieveBE> {

    public MemorySieveBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MemorySieveBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getEntity() != null) {
            int itemCounter = pBlockEntity.getItemCounter();
            pPoseStack.pushPose();
            float scale = 0.334F * pBlockEntity.getItemCounter();
            pPoseStack.translate(0.5, 1, 0.5);
            pPoseStack.scale(scale, scale, scale);
            Minecraft.getInstance().getEntityRenderDispatcher().render(pBlockEntity.getEntity(), 0, 0, 0, 0, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
            pPoseStack.popPose();
        }
    }

}
