package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Quaternionf;

public class SurgeryBedBER implements BlockEntityRenderer<SurgeryBedBE> {

    public SurgeryBedBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SurgeryBedBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getEntity() != null) {
            pPoseStack.pushPose();
            //pPoseStack.scale(0.85F, 0.8F, 0.85F);
            pPoseStack.translate(0.55, 1.0625, 0.5);
            if (pBlockEntity.getPatientStatus().getExposedLocation() == SurgicalLocation.CHEST) {
                pPoseStack.translate(0, 6.1/16, 0);
                //pPoseStack.mulPose(Axis.XP.rotation((float) Math.PI));
            }
            Minecraft.getInstance().getEntityRenderDispatcher().render(pBlockEntity.getEntity(), 0, 0, 0, 0, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
            pPoseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(SurgeryBedBE pBlockEntity) {
        return true;
    }
}
