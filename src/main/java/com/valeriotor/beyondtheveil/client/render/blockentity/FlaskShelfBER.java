package com.valeriotor.beyondtheveil.client.render.blockentity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import com.valeriotor.beyondtheveil.tile.WateryCradleBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.opengl.GL11;

public class FlaskShelfBER implements BlockEntityRenderer<FlaskShelfBE> {

    public FlaskShelfBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(FlaskShelfBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockPos pos = pBlockEntity.getBlockPos();

        Player p = Minecraft.getInstance().player;
        BlockPos shelfPos = null;
        BlockHitResult bhr = null;
        boolean holdingFlask = false;
        if (p != null) {
            Block flaskBlock = Block.byItem(p.getMainHandItem().getItem());
            if (flaskBlock instanceof FlaskBlock) {
                holdingFlask = true;
            }
            HitResult hitResult = Minecraft.getInstance().hitResult;
            if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                if (hitResult instanceof BlockHitResult) {
                    bhr = (BlockHitResult) hitResult;
                    for (int i = -1; i <= 1; i++) {
                        for (int y = 0; y < 3; y++) {
                            Direction facing = pBlockEntity.getBlockState().getValue(FlaskShelfBlock.FACING);
                            int x = facing.getAxis() == Direction.Axis.X ? 0 : (facing == Direction.NORTH ? -i : i);
                            int z = facing.getAxis() == Direction.Axis.Z ? 0 : (facing == Direction.EAST ? -i : i);
                            shelfPos = pBlockEntity.getBlockPos().offset(x, y - 1, z);
                            if (bhr.getBlockPos().equals(shelfPos) && holdingFlask) {
                                BlockState blockState = p.level().getBlockState(shelfPos);
                                if (blockState.getBlock() == Registration.FLASK_SHELF.get()) {
                                    Vec3 locRelativeToBE = bhr.getLocation().subtract(pBlockEntity.getBlockPos().getX(), pBlockEntity.getBlockPos().getY(), pBlockEntity.getBlockPos().getZ());
                                    Vec3 locRelativeToSelectedBlock = bhr.getLocation().subtract(shelfPos.getX(), shelfPos.getY(), shelfPos.getZ());
                                    pPoseStack.pushPose();
                                    pPoseStack.translate(locRelativeToBE.x - 0.5, locRelativeToBE.y, locRelativeToBE.z - 0.5);
                                    boolean intersects = pBlockEntity.intersects(p.level(), shelfPos, bhr, (FlaskBlock) flaskBlock);
                                    Minecraft.getInstance().getBlockRenderer().renderSingleBlock(flaskBlock.defaultBlockState().setValue(FlaskBlock.COLOR, intersects ? 1 : 0), pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
                                    pPoseStack.popPose();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }
        FlaskShelfBE.Flask lookedAt = null;
        if (shelfPos != null && !Minecraft.getInstance().options.hideGui) {
            lookedAt = pBlockEntity.getLookedAtFlask(p.level(), shelfPos, bhr.getLocation());
        }
        for (FlaskShelfBE.Flask flask : pBlockEntity.flasks) {
            pPoseStack.pushPose();
            pPoseStack.translate(flask.getX() - pos.getX() - 0.5, flask.getY() - pos.getY(), flask.getZ() - pos.getZ() - 0.5);
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(FlaskBlock.sizeToBlock.get(flask.getSize()).defaultBlockState().setValue(FlaskBlock.COLOR, flask == lookedAt && !holdingFlask ? 2 : 0), pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
            FlaskBER.renderFlask(flask.getTank(), pBlockEntity.getLevel(), pBlockEntity.getBlockPos(), pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, flask.getSize()); // TODO maybe use exact location instead of center BE
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

    @Override
    public boolean shouldRenderOffScreen(FlaskShelfBE pBlockEntity) {
        return true;
    }





}
