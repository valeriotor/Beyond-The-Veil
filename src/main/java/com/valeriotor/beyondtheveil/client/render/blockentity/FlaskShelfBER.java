package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class FlaskShelfBER implements BlockEntityRenderer<FlaskShelfBE> {

    public FlaskShelfBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FlaskShelfBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Registration.FLASK_LARGE.get().defaultBlockState(), pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
    }

}
