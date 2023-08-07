package com.valeriotor.beyondtheveil.client.reminiscence;

import com.google.common.collect.Comparators;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceUnderground;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ReminiscenceClientUnderground extends ReminiscenceClient {

    private record Count(ItemStack stack, int count) {
    }

    private final ReminiscenceUnderground reminiscenceUnderground;
    private final List<Count> sortedCounter;
    private int ticks = 0;

    public ReminiscenceClientUnderground(ReminiscenceUnderground reminiscenceUnderground) {
        this.reminiscenceUnderground = reminiscenceUnderground;
        this.sortedCounter = reminiscenceUnderground.getCounter().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new Count(new ItemStack(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entry.getKey()))), entry.getValue()))
                .toList();

    }

    @Override
    protected void render(RenderGameOverlayEvent event) {
        PoseStack poseStack = event.getMatrixStack();
        poseStack.pushPose();
        Window window = Minecraft.getInstance().getWindow();
        RenderSystem.setShaderColor(1,1,1,1);
        poseStack.translate(window.getGuiScaledWidth() / 2, window.getGuiScaledHeight() / 2, 0);
        //poseStack.scale(2.5F, 2.5F, 2.5F);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (int i = 0; i < sortedCounter.size(); i++) {
            Count count = sortedCounter.get(i);
            BakedModel pBakedmodel = itemRenderer.getModel(count.stack, (Level) null, (LivingEntity) null, 0);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            posestack.translate((double)window.getGuiScaledWidth() / 2, (double)window.getGuiScaledHeight() / 2, (double)(100.0F + itemRenderer.blitOffset));
            posestack.translate(8.0D, 8.0D, 0.0D);
            posestack.scale(1.0F, -1.0F, 1.0F);
            posestack.scale(16.0F, 16.0F, 16.0F);
            posestack.scale(2.5F, 2.5F, 0);
            RenderSystem.applyModelViewMatrix();
            PoseStack posestack1 = new PoseStack();
            MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
            boolean flag = !pBakedmodel.usesBlockLight();
            if (flag) {
                Lighting.setupForFlatItems();
            }

            itemRenderer.render(count.stack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, pBakedmodel);
            multibuffersource$buffersource.endBatch();
            RenderSystem.enableDepthTest();
            if (flag) {
                Lighting.setupFor3DItems();
            }

            posestack.popPose();
            RenderSystem.applyModelViewMatrix();
            GuiComponent.drawString(event.getMatrixStack(), Minecraft.getInstance().font, "x" + Math.min(count.count, ticks/2), 0, 25 + 30 * i, 0xFFFFFFFF);
        }
        poseStack.popPose();
    }

    @Override
    protected void tick() {
        ticks++;
    }

    @Override
    protected void reset() {
        ticks = 0;
    }
}
