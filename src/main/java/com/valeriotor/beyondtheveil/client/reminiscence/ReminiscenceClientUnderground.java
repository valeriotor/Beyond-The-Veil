package com.valeriotor.beyondtheveil.client.reminiscence;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceUnderground;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ReminiscenceClientUnderground extends ReminiscenceClient {

    private class Count {
        public Count(ItemStack stack) {
            this.stack = stack;
        }

        private ItemStack stack;
        private int counter;
    }

    private final Map<Integer, BlockState[][]> layers = new HashMap<>();
    private final Map<String, Count> sortedCounter;
    private final Set<String> blocksToCount;
    private int ticks = 0;
    private int y;
    private final int maxY;
    private int seenY;
    private boolean wentDown = false;

    public ReminiscenceClientUnderground(ReminiscenceUnderground reminiscenceUnderground) {
        this.maxY = this.y = reminiscenceUnderground.getLayers().keySet().stream().max(Comparator.comparingInt(Integer::intValue)).orElse(0);
        seenY = maxY + 1;
        this.blocksToCount = reminiscenceUnderground.getCountedBlocks();
        this.sortedCounter = new TreeMap<>();
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
                    blockStates[toFill.getA()][toFill.getB()] = Blocks.STONE.defaultBlockState();
                }
            }
            layers.put(entry.getKey(), blockStates);
        }
    }

    @Override
    protected void render(RenderGuiOverlayEvent event) {
        PoseStack poseStack = event.getGuiGraphics().pose();
        poseStack.pushPose();
        Window window = Minecraft.getInstance().getWindow();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        int maxSideLengthForLayer = window.getGuiScaledHeight() * 3 / 4;
        //poseStack.translate(window.getGuiScaledWidth() / 2, window.getGuiScaledHeight() / 2, 0);
        //poseStack.scale(2.5F, 2.5F, 2.5F);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        int offset = 0;
        for (Count count : sortedCounter.values()) {
            BakedModel pBakedmodel = itemRenderer.getModel(count.stack, (Level) null, (LivingEntity) null, 0);
            //TODO is this necessary RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.pushPose();
            posestack.translate((double) window.getGuiScaledWidth() / 2 - (double) maxSideLengthForLayer / 2 + 100 * offset, (double) window.getGuiScaledHeight() / 8, (double) (100.0F /*+ itemRenderer.blitOffset*/)); // TODO check if blitoffset is needed
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

            itemRenderer.render(count.stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, pBakedmodel);
            multibuffersource$buffersource.endBatch();
            RenderSystem.enableDepthTest();
            if (flag) {
                Lighting.setupFor3DItems();
            }

            posestack.popPose();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setShaderColor(1, 1, 1, 1);
            poseStack.pushPose();
            poseStack.translate(window.getGuiScaledWidth() / 2 - maxSideLengthForLayer / 2 + 100 * (offset) + 30, window.getGuiScaledHeight() / 8, 0);
            poseStack.scale(2, 2, 1);
            event.getGuiGraphics().drawString(Minecraft.getInstance().font, "x" + Math.min(count.counter, ticks / 2), 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
            offset++;
        }
        BlockState[][] layer = layers.get(y);
        if (layer != null) {
            int sideForBlock = maxSideLengthForLayer / layer.length;
            poseStack.pushPose();
            poseStack.translate(window.getGuiScaledWidth() / 2 + maxSideLengthForLayer / 2 + 10, window.getGuiScaledHeight() + (layer.length * 0.5) * (sideForBlock + 1) - maxSideLengthForLayer - 15 - 15, 0);
            poseStack.scale(3, 3, 1);
            event.getGuiGraphics().drawString(Minecraft.getInstance().font, "y: " + y, 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
            for (int i = 0; i < layer.length; i++) {
                for (int j = 0; j < layer.length; j++) {
                    Random random = new Random();
                    random.setSeed(42L);
                    poseStack.pushPose();
                    Vec3 position = Minecraft.getInstance().cameraEntity.position();
                    poseStack.translate(window.getGuiScaledWidth() / 2 + i * (sideForBlock + 1) - maxSideLengthForLayer / 2, window.getGuiScaledHeight() + j * (sideForBlock + 1) - maxSideLengthForLayer - 15, 0);
                    BlockState state = layer[i][j];
                    List<BakedQuad> quads = Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getQuads(state, Direction.UP, Minecraft.getInstance().player.getRandom());
                    if (quads.size() > 0) {
                        TextureAtlasSprite sprite = quads.get(0).getSprite();
                        event.getGuiGraphics().blit(new ResourceLocation(sprite.contents().name().getNamespace(), "textures/" + sprite.contents().name().getPath() + ".png"), 0, 0, 0, 0, sideForBlock, sideForBlock, sideForBlock, sideForBlock);
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
        if (layers.containsKey(seenY - 1)) {
            seenY--;
            BlockState[][] layer = layers.get(seenY);
            if (layer != null) {
                for (int i = 0; i < layer.length; i++) {
                    for (int j = 0; j < layer.length; j++) {
                        Block block = layer[i][j].getBlock();
                        String name = ForgeRegistries.BLOCKS.getKey(block).toString();
                        if (blocksToCount.contains(name)) {
                            if (!sortedCounter.containsKey(name)) {
                                sortedCounter.put(name, new Count(new ItemStack(block)));
                            }
                            sortedCounter.get(name).counter++;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void reset() {
        ticks = 0;
        //y = maxY;
        //wentDown = false;
        if (wentDown) {
            sortedCounter.clear();
            seenY = maxY+1;
            while (layers.containsKey(seenY - 1)) {
                seenY--;
                BlockState[][] layer = layers.get(seenY);
                if (layer != null) {
                    for (int i = 0; i < layer.length; i++) {
                        for (int j = 0; j < layer.length; j++) {
                            Block block = layer[i][j].getBlock();
                            String name = ForgeRegistries.BLOCKS.getKey(block).toString();
                            if (blocksToCount.contains(name)) {
                                if (!sortedCounter.containsKey(name)) {
                                    sortedCounter.put(name, new Count(new ItemStack(block)));
                                }
                                sortedCounter.get(name).counter++;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void mouseScroll(InputEvent.MouseScrollingEvent event) {
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
