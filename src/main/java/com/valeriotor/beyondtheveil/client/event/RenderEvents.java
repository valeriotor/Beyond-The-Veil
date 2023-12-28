package com.valeriotor.beyondtheveil.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.client.reminiscence.ReminiscenceClient;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.awt.*;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvents {

    //private static volatile double fov = 0;
//
    //@SubscribeEvent
    //public static void fieldOfViewEvent(EntityViewRenderEvent.FieldOfView event) {
    //    fov = event.getFOV();
    //}

    @SubscribeEvent
    public static void renderFlaskShelfOutline(RenderHighlightEvent.Block event) {
        BlockPos blockPos = event.getTarget().getBlockPos();
        Level l = Minecraft.getInstance().level;
        if (l != null) {
            BlockState blockState = l.getBlockState(blockPos);
            if (blockState.getBlock() == Registration.FLASK_SHELF.get()) {
                event.setCanceled(true);
                VertexConsumer vertexconsumer2 = event.getMultiBufferSource().getBuffer(RenderType.lines());

                Vec3 vec3 = event.getCamera().getPosition();
                double d0 = vec3.x();
                double d1 = vec3.y();
                double d2 = vec3.z();
                renderHitOutline(event.getPoseStack(), vertexconsumer2, event.getCamera().getEntity(), d0, d1, d2, event.getTarget().getBlockPos(), blockState, l, FlaskShelfBlock.getBaseShape(blockState));
            }
        }
    }

    private static void renderHitOutline(PoseStack pPoseStack, VertexConsumer pConsumer, Entity pEntity, double pCamX, double pCamY, double pCamZ, BlockPos pPos, BlockState pState, Level l, VoxelShape shape) {
        renderShape(pPoseStack, pConsumer, shape, (double)pPos.getX() - pCamX, (double)pPos.getY() - pCamY, (double)pPos.getZ() - pCamZ, 0.0F, 0.0F, 0.0F, 0.4F);
    }

    private static void renderShape(PoseStack pPoseStack, VertexConsumer pConsumer, VoxelShape pShape, double pX, double pY, double pZ, float pRed, float pGreen, float pBlue, float pAlpha) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        pShape.forAllEdges((p_234280_, p_234281_, p_234282_, p_234283_, p_234284_, p_234285_) -> {
            float f = (float)(p_234283_ - p_234280_);
            float f1 = (float)(p_234284_ - p_234281_);
            float f2 = (float)(p_234285_ - p_234282_);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f /= f3;
            f1 /= f3;
            f2 /= f3;
            pConsumer.vertex(posestack$pose.pose(), (float)(p_234280_ + pX), (float)(p_234281_ + pY), (float)(p_234282_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
            pConsumer.vertex(posestack$pose.pose(), (float)(p_234283_ + pX), (float)(p_234284_ + pY), (float)(p_234285_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
        });
    }

    @SubscribeEvent
    public static void renderGameOverlay(RenderGuiOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ReminiscenceClient.renderReminiscence(event);
            renderSyringeContents(event);
        }
    }

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderLevelStageEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
        }
    }


    public static void innerFill(Matrix4f pMatrix, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
        if (pMinX < pMaxX) {
            int i = pMinX;
            pMinX = pMaxX;
            pMaxX = i;
        }

        if (pMinY < pMaxY) {
            int j = pMinY;
            pMinY = pMaxY;
            pMaxY = j;
        }

        float f3 = (float) (pColor >> 24 & 255) / 255.0F;
        float f = (float) (pColor >> 16 & 255) / 255.0F;
        float f1 = (float) (pColor >> 8 & 255) / 255.0F;
        float f2 = (float) (pColor & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        //RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end()); // TODO with shader or just draw()?
        //RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private static final ResourceLocation SYRINGE_TANK_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/overlay/syringe_tank.png");
    private static void renderSyringeContents(RenderGuiOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem() != Registration.SYRINGE.get() || !itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            return;
        }
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        final int TOP_Y = height / 20;
        final int LEFT_X = width / 40;
        final float SIZE_MULTIPLIER = 1.5F;
        guiGraphics.blit(SYRINGE_TANK_TEXTURE, LEFT_X, TOP_Y, (int) (44*SIZE_MULTIPLIER), (int) (142*SIZE_MULTIPLIER), (float) 0, (float) 0, (int) (44*SIZE_MULTIPLIER), (int) (142*SIZE_MULTIPLIER), (int) (44*SIZE_MULTIPLIER), (int) (142*SIZE_MULTIPLIER));
        IFluidHandlerItem syringe = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
        FluidStack fluidInTank = syringe.getFluidInTank(0);
        if(!fluidInTank.isEmpty()) {
            FluidState defaultFluidState = fluidInTank.getFluid().defaultFluidState();
            IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(defaultFluidState);
            BlockPos pos = player.getOnPos();
            ClientLevel level = player.clientLevel;
            ResourceLocation stillTexture = props.getStillTexture();
            TextureAtlasSprite stillSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillTexture);
            //TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(props.getStillTexture(defaultFluidState, level, pos));
            float percentFilled = fluidInTank.getAmount() / (float) syringe.getTankCapacity(0);
            final int X_BASE_OFFSET = 6; // WITHOUT MULTIPLIER
            final int Y_BASE_OFFSET = 133;
            for (int i = 0; i < 4; i++) {
                if (i * 0.25F >= percentFilled) {
                    break;
                }
                int heightOfQuartile = (int) (SIZE_MULTIPLIER * 128 * (percentFilled - 0.25F * i));
                float currentBaseX = LEFT_X + X_BASE_OFFSET * SIZE_MULTIPLIER;
                float currentBaseY = TOP_Y + (Y_BASE_OFFSET - 32 * i) * SIZE_MULTIPLIER;
                float currentTopY = currentBaseY - heightOfQuartile;
                if(fluidInTank.getFluid() == Fluids.WATER) {
                    int averageWaterColor = BiomeColors.getAverageWaterColor(level, pos);
                    float r = ((averageWaterColor >> 16) & 255) / 255F;
                    float g = ((averageWaterColor >> 8) & 255) / 255F;
                    float b = ((averageWaterColor) & 255) / 255F;
                    guiGraphics.blit((int) currentBaseX, (int) (currentBaseY - 32 * SIZE_MULTIPLIER), 0, (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER), stillSprite, r, g, b, 1);
                } else {
                    guiGraphics.blit((int) currentBaseX, (int) (currentBaseY - 32 * SIZE_MULTIPLIER), 0, (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER), stillSprite);
                }
                if (heightOfQuartile < SIZE_MULTIPLIER * 32)
                    guiGraphics.blit(SYRINGE_TANK_TEXTURE, (int) currentBaseX, (int) (currentBaseY - 32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER - heightOfQuartile), X_BASE_OFFSET*SIZE_MULTIPLIER, Y_BASE_OFFSET*SIZE_MULTIPLIER - 32 * SIZE_MULTIPLIER, (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER - heightOfQuartile), (int) (44 * SIZE_MULTIPLIER), (int) (142 * SIZE_MULTIPLIER));
//            guiGraphics.blit(stillTexture, currentBaseX, currentTopY, 32*SIZE_MULTIPLIER, heightOfQuartile, 0, 0, 32*SIZE_MULTIPLIER, heightOfQuartile, 32*SIZE_MULTIPLIER, 32*SIZE_MULTIPLIER);

            }
            guiGraphics.drawString(Minecraft.getInstance().font, String.format("%d mB", fluidInTank.getAmount()), (int) (LEFT_X + 48 * SIZE_MULTIPLIER), (int) (TOP_Y + (142 / 2) * SIZE_MULTIPLIER), 0xFFFFFF00);
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(fluidInTank.getTranslationKey()), (int) (LEFT_X + 48 * SIZE_MULTIPLIER), (int) (TOP_Y + (142 / 2) * SIZE_MULTIPLIER + 7), 0xFFFFFF00);
        }
        for (int i = 0; i < 4; i++) {
            float currentBaseX = LEFT_X + 35*SIZE_MULTIPLIER;
            float currentBaseY = TOP_Y + (133 - 32*(i+1))*SIZE_MULTIPLIER;
            guiGraphics.fill((int) currentBaseX, (int) currentBaseY, (int) (currentBaseX+3*SIZE_MULTIPLIER), (int) (currentBaseY+1*SIZE_MULTIPLIER), 0xFF000000);
        }
    }

}
