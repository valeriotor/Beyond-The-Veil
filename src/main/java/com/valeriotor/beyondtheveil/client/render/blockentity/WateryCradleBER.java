package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.tile.WateryCradleBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;

public class WateryCradleBER implements BlockEntityRenderer<WateryCradleBE> {

    public WateryCradleBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WateryCradleBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getEntity() != null) {
            pPoseStack.pushPose();
            pPoseStack.scale(0.93F, 0.8F, 0.93F);
            //pPoseStack.mulPose(Quaternion.fromYXZ((float) Math.PI/2, 0, 0));
            Direction value = pBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            pPoseStack.translate(0.525, 0.31, 0.52);
            pPoseStack.mulPose(Axis.YP.rotation((float) ((-value.get2DDataValue() - 1) * Math.PI / 2)));
            Minecraft.getInstance().getEntityRenderDispatcher().render(pBlockEntity.getEntity(), 0, 0, 0, 0, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
            pPoseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(WateryCradleBE pBlockEntity) {
        return true;
    }
}
