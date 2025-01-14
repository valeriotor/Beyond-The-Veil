package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.tile.AlembicsBE;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class AlembicsBER implements BlockEntityRenderer<AlembicsBE> {

    private final ItemRenderer itemRenderer;

    public AlembicsBER(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(AlembicsBE pBlockEntity, float pPartialTick, PoseStack pose, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        FlaskShelfBE.Flask flask = pBlockEntity.getHeldFlask();
        if (flask != null) {
            BlockPos pos = pBlockEntity.getBlockPos();
            pose.pushPose();
            pose.translate(flask.getX() - pos.getX() - 0.5, flask.getY() - pos.getY(), flask.getZ() - pos.getZ() - 0.5);
            Direction direction = pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
            FlaskBER.renderFlask(direction, flask.getTank(), flask.getStackHandler(), pBlockEntity.getLevel(), pBlockEntity.getBlockPos(), pPartialTick, pose, pBuffer, pPackedLight, pPackedOverlay, flask.getSize(), itemRenderer); // TODO maybe use exact location instead of center BE
            pose.popPose();
        }
    }
}
