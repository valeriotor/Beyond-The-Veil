package com.valeriotor.beyondtheveil.client.reminiscence;

import com.google.common.collect.Comparators;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceUnderground;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ReminiscenceClientUnderground extends ReminiscenceClient {

    private record Count(ItemStack stack, int count) {
    }

    private final Map<Integer, BlockState[][]> layers = new HashMap<>();
    private final List<Count> sortedCounter;
    private int ticks = 0;
    private int y;
    private boolean wentDown = false;

    public ReminiscenceClientUnderground(ReminiscenceUnderground reminiscenceUnderground) {
        this.sortedCounter = reminiscenceUnderground.getCounter().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new Count(new ItemStack(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entry.getKey()))), entry.getValue()))
                .toList();
        this.y = reminiscenceUnderground.getLayers().keySet().stream().max(Comparator.comparingInt(Integer::intValue)).orElse(0);
        for (Map.Entry<Integer, String[][]> entry : reminiscenceUnderground.getLayers().entrySet()) {
            BlockState[][] blockStates = new BlockState[entry.getValue().length][entry.getValue().length];
            for (int i = 0; i < entry.getValue().length; i++) {
                Map<Block, Integer> counters = Map.of(Blocks.STONE, 0, Blocks.DIORITE, 0, Blocks.ANDESITE, 0, Blocks.GRANITE, 0, Blocks.DEEPSLATE, 0);
                List<Tuple<Integer, Integer>> unplaced = new ArrayList<>();
                for (int j = 0; j < entry.getValue().length; j++) {
                    Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entry.getValue()[i][j]));
                    if (entry.getValue()[i][j].equals("null") || block == null) {
                        unplaced.add(new Tuple<>(i, j));
                    } else {
                        blockStates[i][j] = block.defaultBlockState();
                    }
                }
                Block filler = counters.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
                for (Tuple<Integer, Integer> toFill : unplaced) {
                    blockStates[toFill.getA()][toFill.getB()] = filler.defaultBlockState();
                }
            }
            layers.put(entry.getKey(), blockStates);
        }
    }

    @Override
    protected void render(RenderGameOverlayEvent event) {
        PoseStack poseStack = event.getMatrixStack();
        poseStack.pushPose();
        Window window = Minecraft.getInstance().getWindow();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        int maxSideLengthForLayer = window.getGuiScaledHeight() * 3 / 4;
        //poseStack.translate(window.getGuiScaledWidth() / 2, window.getGuiScaledHeight() / 2, 0);
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
            posestack.translate((double) window.getGuiScaledWidth() / 2 - (double) maxSideLengthForLayer / 2 + 100 * i, (double) window.getGuiScaledHeight() / 8, (double) (100.0F + itemRenderer.blitOffset));
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
            RenderSystem.setShaderColor(1, 1, 1, 1);
            poseStack.pushPose();
            poseStack.translate(window.getGuiScaledWidth() / 2 - maxSideLengthForLayer / 2 + 100 * (i) + 30, window.getGuiScaledHeight() / 8, 0);
            poseStack.scale(2, 2, 1);
            GuiComponent.drawString(event.getMatrixStack(), Minecraft.getInstance().font, "x" + Math.min(count.count, ticks / 2), 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
        }
        BlockState[][] layer = layers.get(y);
        if (layer != null) {
            int sideForBlock = maxSideLengthForLayer / layer.length;
            poseStack.pushPose();
            poseStack.translate(window.getGuiScaledWidth() / 2 + maxSideLengthForLayer / 2 + 10, window.getGuiScaledHeight() + (layer.length * 0.5) * (sideForBlock + 1) - maxSideLengthForLayer - 15 - 15, 0);
            poseStack.scale(3, 3, 1);
            GuiComponent.drawString(event.getMatrixStack(), Minecraft.getInstance().font, "y: " + y, 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
            for (int i = 0; i < layer.length; i++) {
                for (int j = 0; j < layer.length; j++) {
                    Random random = new Random();
                    random.setSeed(42L);
                    poseStack.pushPose();
                    Vec3 position = Minecraft.getInstance().cameraEntity.position();
                    poseStack.translate(window.getGuiScaledWidth() / 2 + i * (sideForBlock + 1) - maxSideLengthForLayer / 2, window.getGuiScaledHeight() + j * (sideForBlock + 1) - maxSideLengthForLayer - 15, 0);
                    BlockState state = layer[i][j];
                    List<BakedQuad> quads = Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getQuads(state, Direction.UP, random);
                    if (quads.size() > 0) {
                        TextureAtlasSprite sprite = quads.get(0).getSprite();
                        RenderSystem.setShaderTexture(0, new ResourceLocation(sprite.getName().getNamespace(), "textures/" + sprite.getName().getPath() + ".png"));
                        GuiComponent.blit(poseStack, 0, 0, 0, 0, sideForBlock, sideForBlock, sideForBlock, sideForBlock);
                    }
                    //Gui.blit(poseStack, 10, 10, 0, 138, 138, sprite);
                    //.getModelData(Minecraft.getInstance().level, new BlockPos(position), pState, ModelDataManager.getModelData(Minecraft.getInstance().level, new BlockPos(position)));
                    //Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY);
                    poseStack.popPose();
                }
            }
        }
        poseStack.popPose();
    }

    @Override
    protected void tick() {
        ticks++;
        if (!wentDown) {
            if (layers.containsKey(y - 1)) {
                y--;
            } else {
                wentDown = true;
            }
        }
    }

    @Override
    protected void reset() {
        ticks = 0;
    }

    @Override
    protected void mouseScroll(InputEvent.MouseScrollEvent event) {
        wentDown = true;
        if (Math.signum(event.getScrollDelta()) > 0) {
            if (layers.containsKey(y - 1)) {
                y--;
            }
        } else {
            if (layers.containsKey(y + 1)) {
                y++;
            }
        }
    }
}
