package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.research.ResearchConnection;
import com.valeriotor.beyondtheveil.client.research.ResearchRegistryClient;
import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.lib.ConfigLib;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

public class NecronomiconGui extends Screen {

    int topX;
    int topY;
    int factor = 3;
    int pupilNextXOffset;
    int pupilNextYOffset;
    int pupilXOffset = 0;
    int pupilYOffset = 0;
    int highlightOriginX = 0;
    int highlightOriginY = 0;
    List<Research> newClickables = new ArrayList<>();
    List<Research> clickables = new ArrayList<>();
    List<Research> visibles = new ArrayList<>();
    Set<Research> updated = new HashSet<>();
    List<ResearchConnection> connections = new ArrayList<>();
    List<Point> stars = new ArrayList<>();
    int counter = 0;
    Research highlightedMarkedResearch;
    Iterator<Research> highlightIterator;
    int highlightCounter = 0;
    int connectionColor;

    private static final ResourceLocation RESEARCH_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_background.png");
    private static final ResourceLocation RESEARCH_BACKGROUND_WHITE = new ResourceLocation(References.MODID, "textures/gui/res_background_white.png");
    private static final ResourceLocation RESEARCH_HIGHLIGHT = new ResourceLocation(References.MODID, "textures/gui/res_highlight.png");
    private static final ResourceLocation RESEARCH_UPDATED_MARKER = new ResourceLocation(References.MODID, "textures/gui/res_marker.png");
    private static final ResourceLocation EYE = new ResourceLocation(References.MODID, "textures/gui/eye.png");
    private static final ResourceLocation EYE_PUPIL = new ResourceLocation(References.MODID, "textures/gui/eye_pupil.png");

    public NecronomiconGui() {
        super(new TranslatableComponent("gui.necronomicon")); // TODO change to TranslatableComponent("gui.necronomicon")
        Map<String, ResearchStatus> map = ResearchUtil.getResearches(Minecraft.getInstance().player);
        PlayerData data = Minecraft.getInstance().player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get();
        this.topX = data.getOrSetInteger(PlayerDataLib.NECRO_X, -400, false);
        this.topY = data.getOrSetInteger(PlayerDataLib.NECRO_Y, -200, false);
        if (map.get("FIRSTDREAMS").getStage() == -1) {
            this.topX = -400;
            this.topY = -200;
        }
        this.factor = data.getOrSetInteger(PlayerDataLib.NECRO_FACTOR, 3, false);
        for (Entry<String, ResearchStatus> entry : map.entrySet()) {
            if (entry.getValue().isKnown(map, data)) {
                if (entry.getValue().getStage() == -1) {
                    newClickables.add(entry.getValue().res);
                } else {
                    clickables.add(entry.getValue().res);
                    boolean b = entry.getValue().isHidden(Minecraft.getInstance().player);
                    boolean a = b;
                }
            } else if (entry.getValue().isVisible(map, data)) {
                visibles.add(entry.getValue().res);
            }
            if (entry.getValue().isUpdated()) {
                updated.add(entry.getValue().res);
            }
        }

        for (ResearchConnection rc : ResearchRegistryClient.connections) {
            if (rc.isVisible(map, data)) {
                connections.add(rc);
            }
        }
        this.connectionColor = (255 << 24) | (ConfigLib.connectionRed << 16) | (ConfigLib.connectionGreen << 8) | ConfigLib.connectionBlue;
    }

    @Override
    public void init() {
        stars.clear();
        Random r = minecraft.player.getRandom();
        int a = 100 + r.nextInt(50);
        for (int i = 0; i < a; i++) {
            stars.add(new Point(r.nextInt(this.width), r.nextInt(this.height)));
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        if (highlightedMarkedResearch != null && counter - highlightCounter < 10) {
            float magnitude = (float) Math.log10((1 + pPartialTick + counter - highlightCounter));
            topX = (int) ((highlightedMarkedResearch.getX()*15*factor - width / 2 - highlightOriginX) * magnitude) + highlightOriginX;
            topY = (int) ((highlightedMarkedResearch.getY()*15*factor - height / 2 - highlightOriginY) * magnitude) + highlightOriginY;
        }

        fill(pPoseStack, 0, 0, width, height, 0xFF000000);
        for (ResearchConnection rc : connections)
            this.drawConnection(pPoseStack, rc, pPartialTick);
        for (Point p : stars) {
            fill(pPoseStack, p.x, p.y, p.x + 1, p.y + 1, 0xFFFFFFFF);
        }
        RenderSystem.setShaderTexture(0, RESEARCH_BACKGROUND);
        RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, 1);
        for (Research r : clickables) this.drawResearchBackground(r, pPoseStack, pPartialTick);
        RenderSystem.setShaderColor(0.25F, 0.25F, 0.25F, 1);
        for (Research r : visibles) this.drawResearchBackground(r, pPoseStack, pPartialTick);
        float coloring = 0.52F + (float) (Math.sin((this.counter + pPartialTick) / 30 * 2 * Math.PI) / 4);
        RenderSystem.setShaderColor(coloring, coloring, coloring, 1);
        for (Research r : newClickables) this.drawResearchBackground(r, pPoseStack, pPartialTick);
        for (Research r : clickables) this.drawResearch(pPoseStack, r, pMouseX, pMouseY);
        for (Research r : visibles) this.drawResearch(pPoseStack, r, pMouseX, pMouseY);
        for (Research r : newClickables) this.drawResearch(pPoseStack, r, pMouseX, pMouseY);
        drawEye(pPoseStack, pPartialTick, pMouseX, pMouseY);

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    //@Override
    //public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    //    GlStateManager.color(0.8F, 0.8F, 0.8F);
    //    mc.renderEngine.bindTexture(RESEARCH_BACKGROUND);
    //    for (Research r : clickables) this.drawResearchBackground(r);
    //    GlStateManager.color(0.25F, 0.25F, 0.25F);
    //    for (Research r : visibles) this.drawResearchBackground(r);
    //    float coloring = 0.52F + (float) (Math.sin((this.counter + partialTicks) / 30 * 2 * Math.PI) / 4);
    //    GlStateManager.color(coloring, coloring, coloring);
    //    for (Research r : newClickables) this.drawResearchBackground(r);
//
    //    RenderHelper.enableStandardItemLighting();
    //    for (Research r : clickables) this.drawResearch(r, mouseX, mouseY);
    //    for (Research r : visibles) this.drawResearch(r, mouseX, mouseY);
    //    for (Research r : newClickables) this.drawResearch(r, mouseX, mouseY);
    //    super.drawScreen(mouseX, mouseY, partialTicks);
    //}


    @Override
    public void tick() {
        counter++;
        if ((counter & 31) == 0) {
            int mouseX = (int)(this.minecraft.mouseHandler.xpos() * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth());
            int mouseY = (int)(this.minecraft.mouseHandler.ypos() * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight());
            if (mouseX > width - 142 && mouseY > height - 142) {
                double degree = Math.atan2(mouseY-(height-142+64), mouseX-(width-142+64));
                double magnitude = Math.min(11, Math.sqrt(Math.pow(mouseX-(width-142+64), 2) + Math.pow(mouseY-(height-142+64), 2)))/2;
                double xMul = Math.cos(degree);
                double yMul = Math.sin(degree);
                pupilNextXOffset = (int) (magnitude * xMul);
                pupilNextYOffset = (int) (magnitude * yMul);
            } else {
                int degree = minecraft.player.getRandom().nextInt(360);
                int magnitude = minecraft.player.getRandom().nextInt(4, 12);
                double xMul = Math.cos(degree * Math.PI / 180);
                double yMul = Math.sin(degree * Math.PI / 180);
                pupilNextXOffset = (int) (magnitude * xMul);
                pupilNextYOffset = (int) (magnitude * yMul);
            }
        } else if ((counter & 31) == 4) {
            pupilXOffset = pupilNextXOffset;
            pupilYOffset = pupilNextYOffset;
        }
        if (counter - highlightCounter > 30) {
            highlightedMarkedResearch = null;
        }
    }

    private void drawResearch(PoseStack pPoseStack, Research res, int mouseX, int mouseY) {
        int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
        if (resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {
            ItemStack[] icons = res.getIconStacks();
            if (icons.length > 0)
                itemRenderer.renderGuiItem(icons[counter % 20 % icons.length], resX - topX, resY - topY);
            if (updated.contains(res)) {
                RenderSystem.setShaderTexture(0, RESEARCH_UPDATED_MARKER);
                blit(pPoseStack, resX - topX + 4, resY - topY - 12, 0, 0, 24, 24, 24, 24);
            }
            if (mouseX > resX - topX - 4 && mouseX < resX - topX + 20 && mouseY > resY - topY - 4 && mouseY < resY - topY + 20) {
                //RenderSystem.depthFunc(3);
                renderTooltip(pPoseStack, new TranslatableComponent(res.getName()), mouseX, mouseY);
            }
        }
    }

    private void drawResearchBackground(Research res, PoseStack pPoseStack, float partialTicks) {
        int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
        if (resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {
            if (res != highlightedMarkedResearch || counter - highlightCounter > 10) {
                blit(pPoseStack, resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
                if (res == highlightedMarkedResearch) {
                    RenderSystem.setShaderTexture(0, RESEARCH_HIGHLIGHT);
                    RenderSystem.enableBlend();
                    int increase = (int) ((partialTicks+counter- highlightCounter -10)*3);
                    RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, Math.max(0, Math.min(1, (highlightCounter + 31 - counter - partialTicks) / 11F)));
                    blit(pPoseStack, resX - topX - 4 - increase, resY - topY - 4 - increase, 0, 0, 24+increase*2, 24+increase*2, 24+increase*2, 24+increase*2);
                    RenderSystem.setShaderTexture(0, RESEARCH_BACKGROUND);
                    RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, 1);
                }
            } else {
                RenderSystem.setShaderTexture(0, RESEARCH_BACKGROUND_WHITE);
                RenderSystem.setShaderColor((partialTicks+counter- highlightCounter)/20F+0.5F, (partialTicks+counter- highlightCounter)/20F+0.5F, (partialTicks+counter- highlightCounter)/20F+0.5F, 1);
                blit(pPoseStack, resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
                RenderSystem.setShaderTexture(0, RESEARCH_BACKGROUND);
                RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, 1);

            }
            //drawModalRectWithCustomSizedTexture(resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
        }
        // TEST FOR FOREGROUND renderTooltip(pPoseStack, new TranslatableComponent(res.getName()), resX - topX, resY - topY);

    }

    private void drawConnection(PoseStack pPoseStack, ResearchConnection rc, float partialTicks) {
        if (rc.shouldRender(topX, topY, width, height)) {
            Point left = rc.getLeftPoint(), right = rc.getRightPoint();
            double dist = left.distance(right) * 15 * factor;
            int lx = left.x * 15 * factor, ly = left.y * 15 * factor, rx = right.y * 15 * factor, ry = right.y * 15 * factor;
            pPoseStack.pushPose();
            double phi = Math.asin((right.y - left.y) * 15 * factor / dist);
            pPoseStack.translate(lx - topX + 8, ly - topY + 8, 0);
            pPoseStack.mulPose(Vector3f.XP.rotation((float) (phi * 180 / Math.PI)));
            for (int i = 0; i < dist; i++) {
                int signum = (int) Math.signum(counter % 80 - 40);
                double amplifier = 15 * (signum * Math.pow((counter % 40 + partialTicks) / 20 - 1, 4) - signum);
                int y = (int) (amplifier * Math.sin(i * Math.PI / dist));
                fill(pPoseStack, i, y, i + 1, y + 1, connectionColor);
            }
            pPoseStack.popPose();
        }
    }

    private void drawEye(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        if (!updated.isEmpty()) {

            int counterMod32 = counter & 31;
            int pupilX = (int) (pupilXOffset + (pupilNextXOffset - pupilXOffset) * (counterMod32 + partialTicks) / 4);
            int pupilY = (int) (pupilYOffset + (pupilNextYOffset - pupilYOffset) * (counterMod32 + partialTicks) / 4);

            poseStack.pushPose();
            poseStack.translate(width - 142 + 64, height - 142 + 64, 0);
            if (mouseX > width - 142 && mouseY > height - 142) {
                poseStack.scale(1.1F, 1.1F, 0);
            }

            RenderSystem.setShaderTexture(0, EYE);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.enableBlend();
            blit(poseStack, -64, -64, 0, 0, 128, 128, 128, 128);
            RenderSystem.setShaderTexture(0, EYE_PUPIL);
            blit(poseStack, -64 + pupilX, -64 + pupilY, 0, 0, 128, 128, 128, 128);
            poseStack.popPose();
        }
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        topX = (int) MathHelperBTV.clamp(-700, 3840 - this.width / 2F, topX - pDragX);
        topY = (int) MathHelperBTV.clamp(-700, 2160 - this.height / 2F, topY - pDragY);
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        this.factor = MathHelperBTV.clamp(2, 5, this.factor + (int) Math.signum(pDelta));
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (super.mouseClicked(pMouseX, pMouseY, pButton)) {
            return true;
        }
        if ((!updated.isEmpty()) && (pMouseX > width - 142 && pMouseY > height - 142)) {
            if (highlightIterator == null || !highlightIterator.hasNext()) {
                highlightIterator = updated.iterator();
            }
            highlightedMarkedResearch = highlightIterator.next();
            highlightCounter = counter;
            highlightOriginX = topX;
            highlightOriginY = topY;
            //topX = highlightedMarkedResearch.getX()*15*factor - width/2;
            //topY = highlightedMarkedResearch.getY()*15*factor - height/2;
            return true;
        }
        for (Research res : this.clickables) {
            if (openResearch(res, pMouseX, pMouseY)) return true;
        }
        for (Research res : this.newClickables) {
            if (openResearch(res, pMouseX, pMouseY)) return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
        if (minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    private boolean openResearch(Research res, double mouseX, double mouseY) {
        int resX = res.getX() * 15 * factor - topX - 4, resY = res.getY() * 15 * factor - topY - 4;
        if (mouseX > resX - 4 && mouseX < resX + 24 && mouseY > resY - 4 && mouseY < resY + 24) {
            ResearchStatus status = ResearchUtil.getResearch(minecraft.player, res.getKey());
            if (status.getStage() == -1) ResearchUtilClient.progressResearchClientAndSync(res.getKey());
            if (status.isUpdated()) ResearchUtilClient.openUpdatedResearchClientAndSync(res.getKey());
            savePositionData();
            minecraft.setScreen(getResearchGui(status));
            return true;
        }
        return false;
    }

    private Screen getResearchGui(ResearchStatus status) {
        return switch (status.res.getKey()) {
            //case "ICTYARY" -> new GuiIctyary();
            //case "DOSKILLS" -> new GuiDOSkills();
            default -> new ResearchPageGui(status);
            //default -> null;
        };
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void onClose() {
        savePositionData();
        minecraft.setScreen(null);
    }

    private void savePositionData() {
        DataUtilClient.setIntAndSync(PlayerDataLib.NECRO_X, this.topX, false);
        DataUtilClient.setIntAndSync(PlayerDataLib.NECRO_Y, this.topY, false);
        DataUtilClient.setIntAndSync(PlayerDataLib.NECRO_FACTOR, this.factor, false);
    }
}
