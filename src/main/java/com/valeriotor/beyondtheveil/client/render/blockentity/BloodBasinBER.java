package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.tile.BloodBasinBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BloodBasinBER implements BlockEntityRenderer<BloodBasinBE> {
    private final ItemRenderer itemRenderer;

    public BloodBasinBER(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BloodBasinBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getItemEntity() != null) {
            pPoseStack.pushPose();
            //pPoseStack.scale(1.3F, 1.3F, 1.3F);
            Minecraft.getInstance().getEntityRenderDispatcher().render(pBlockEntity.getItemEntity(), 0.5, 1, 0.5, 0, pPartialTick, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }
    }
}
