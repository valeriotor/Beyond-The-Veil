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
import net.minecraft.network.chat.TextComponent;
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
    List<Research> newClickables = new ArrayList<>();
    List<Research> clickables = new ArrayList<>();
    List<Research> visibles = new ArrayList<>();
    Set<String> updated = new HashSet<>();
    List<ResearchConnection> connections = new ArrayList<>();
    List<Point> stars = new ArrayList<>();
    int counter = 0;
    int connectionColor;

    private static final ResourceLocation RESEARCH_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_background.png");
    private static final ResourceLocation RESEARCH_UPDATED_MARKER = new ResourceLocation(References.MODID, "textures/gui/res_marker.png");

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
                }
            } else if (entry.getValue().isVisible(map, data)) {
                visibles.add(entry.getValue().res);
            }
            if (entry.getValue().isUpdated()) {
                updated.add(entry.getKey());
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
        fill(pPoseStack, 0, 0, width, height, 0xFF000000);
        for (ResearchConnection rc : connections)
            this.drawConnection(pPoseStack, rc, pPartialTick);
        for (Point p : stars) {
            fill(pPoseStack, p.x, p.y, p.x + 1, p.y + 1, 0xFFFFFFFF);
        }
        RenderSystem.setShaderTexture(0, RESEARCH_BACKGROUND);
        RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, 1);
        for (Research r : clickables) this.drawResearchBackground(r, pPoseStack);
        RenderSystem.setShaderColor(0.25F, 0.25F, 0.25F, 1);
        for (Research r : visibles) this.drawResearchBackground(r, pPoseStack);
        float coloring = 0.52F + (float) (Math.sin((this.counter + pPartialTick) / 30 * 2 * Math.PI) / 4);
        RenderSystem.setShaderColor(coloring, coloring, coloring, 1);
        for (Research r : newClickables) this.drawResearchBackground(r, pPoseStack);
        for (Research r : clickables) this.drawResearch(pPoseStack, r, pMouseX, pMouseY);
        for (Research r : visibles) this.drawResearch(pPoseStack, r, pMouseX, pMouseY);
        for (Research r : newClickables) this.drawResearch(pPoseStack, r, pMouseX, pMouseY);
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
    }

    private void drawResearch(PoseStack pPoseStack, Research res, int mouseX, int mouseY) {
        int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
        if (resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {
            ItemStack[] icons = res.getIconStacks();
            if (icons.length > 0)
                itemRenderer.renderGuiItem(icons[counter % 20 % icons.length], resX - topX, resY - topY);
            if (mouseX > resX - topX - 4 && mouseX < resX - topX + 20 && mouseY > resY - topY - 4 && mouseY < resY - topY + 20) {
                renderTooltip(pPoseStack, new TranslatableComponent(res.getName()), mouseX, mouseY);
            }
            if (updated.contains(res.getKey())) {
                pPoseStack.pushPose();
                pPoseStack.translate(0, 0, 1000);
                RenderSystem.setShaderTexture(2, RESEARCH_UPDATED_MARKER);
                blit(pPoseStack, resX - topX + 4, resY - topY - 12, 24, 24, 0, 0, 24, 24);
                pPoseStack.popPose();
            }
        }
    }

    private void drawResearchBackground(Research res, PoseStack pPoseStack) {
        int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
        if (resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {
            blit(pPoseStack, resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
            //drawModalRectWithCustomSizedTexture(resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
        }
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
