package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.tile.SacrificeAltarBE;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class SacrificeAltarBER implements BlockEntityRenderer<SacrificeAltarBE> {

    public SacrificeAltarBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SacrificeAltarBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getEntity() != null) {
            pPoseStack.pushPose();
            //pPoseStack.scale(0.85F, 0.8F, 0.85F);
            Direction value = pBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            pPoseStack.translate(0.5, 0.9375, 0.5);
            pPoseStack.translate(0, 6.1/16, 0);
            //pPoseStack.mulPose(Axis.XP.rotation((float) Math.PI));
            pPoseStack.mulPose(Axis.YP.rotation((float) ((-value.get2DDataValue() - 1) * Math.PI / 2)));
            pPoseStack.translate(-0.5, 0, 0);
            Minecraft.getInstance().getEntityRenderDispatcher().render(pBlockEntity.getEntity(), 0, 0, 0, 0, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
            pPoseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(SacrificeAltarBE pBlockEntity) {
        return true;
    }

}
