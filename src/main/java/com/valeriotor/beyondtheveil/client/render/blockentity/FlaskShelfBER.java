package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FlaskShelfBER implements BlockEntityRenderer<FlaskShelfBE> {

    public FlaskShelfBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FlaskShelfBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockPos pos = pBlockEntity.getBlockPos();
        for (FlaskShelfBE.Flask flask : pBlockEntity.flasks) {
            pPoseStack.pushPose();
            pPoseStack.translate(flask.getX() - pos.getX() - 0.5, flask.getY() - pos.getY(), flask.getZ() - pos.getZ() - 0.5);
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(FlaskBlock.sizeToBlock.get(flask.getSize()).defaultBlockState(), pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
            pPoseStack.popPose();
        }
        HitResult hitResult = Minecraft.getInstance().hitResult;
        //if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
        //    if (hitResult instanceof BlockHitResult bhr && bhr.getBlockPos().equals(pBlockEntity.getBlockPos())) {
        //        Vec3 loc = bhr.getLocation().subtract(pBlockEntity.getBlockPos().getX(), pBlockEntity.getBlockPos().getY(), pBlockEntity.getBlockPos().getZ());
        //        int y = 0;
        //        if (loc.x > 0.5) {
        //            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Registration.FLASK_LARGE.get().defaultBlockState(), pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
        //        }
        //    }
        //}
    }

}
