package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.tile.PatientPodBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class PatientPodBER implements BlockEntityRenderer<PatientPodBE> {

    public PatientPodBER(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PatientPodBE pBlockEntity, float pPartialTick, PoseStack pose, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getToRender() != null) {
            pose.pushPose();
            Direction value = pBlockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            pose.translate(0.5, 0.1, 0.5);
            pose.scale(0.9F, 0.9F, 0.9F);
            pose.mulPose(Axis.YP.rotation((float) ((-value.get2DDataValue()) * Math.PI / 2)));
            Minecraft.getInstance().getEntityRenderDispatcher().render(pBlockEntity.getToRender(), 0, 0, 0, 0, 0, pose, pBuffer, pPackedLight);
            pose.popPose();
        }
    }
}
