package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
import com.valeriotor.beyondtheveil.research.ResearchRegistry;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

public class NecronomiconGui extends Screen {

    private static final int MAX_BOOKMARKS = 16;
    private int topX;
    private int topY;
    private int factor = 3;
    private int pupilNextXOffset;
    private int pupilNextYOffset;
    private int pupilXOffset = 0;
    private int pupilYOffset = 0;
    private int highlightOriginX = 0;
    private int highlightOriginY = 0;
    private List<Research> newClickables = new ArrayList<>();
    private List<Research> clickables = new ArrayList<>();
    private List<Research> visibles = new ArrayList<>();
    private Set<Research> updated = new HashSet<>();
    private List<ResearchConnection> connections = new ArrayList<>();
    private List<Point> stars = new ArrayList<>();
    private int counter = 0;
    private Research highlightedMarkedResearch;
    private Iterator<Research> highlightIterator;
    private int highlightCounter = 0;
    private int connectionColor;
    private List<Research> bookmarks = new ArrayList<>();
    private boolean showBookmarkHint;

    private static final ResourceLocation RESEARCH_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_background.png");
    private static final ResourceLocation RESEARCH_BACKGROUND_WHITE = new ResourceLocation(References.MODID, "textures/gui/res_background_white.png");
    public static final ResourceLocation RESEARCH_HIGHLIGHT = new ResourceLocation(References.MODID, "textures/gui/res_highlight.png");
    private static final ResourceLocation RESEARCH_UPDATED_MARKER = new ResourceLocation(References.MODID, "textures/gui/res_marker.png");
    private static final ResourceLocation EYE = new ResourceLocation(References.MODID, "textures/gui/eye.png");
    private static final ResourceLocation EYE_PUPIL = new ResourceLocation(References.MODID, "textures/gui/eye_pupil.png");
    private static final ResourceLocation BOOKMARK = new ResourceLocation(References.MODID, "textures/gui/bookmark_grayed.png");
    private static final ResourceLocation MOUSE_RIGHT_CLICK = new ResourceLocation(References.MODID, "textures/gui/mouse_right_click.png");
    private int firstBookmarkMadeCounter = 0;

    public NecronomiconGui() {
        super(Component.translatable("gui.necronomicon")); // TODO change to TranslatableComponent("gui.necronomicon")
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
                    boolean a = b; // what's this for??
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
        showBookmarkHint = !data.getBoolean(PlayerDataLib.MADE_BOOKMARK) && map.get("FUMESPREADER").getStage() >= 1; // TODO this should be FUMESPREADER, change if otherwise
    }

    @Override
    public void init() {
        stars.clear();
        RandomSource r = minecraft.player.getRandom();
        int a = 100 + r.nextInt(50);
        for (int i = 0; i < a; i++) {
            stars.add(new Point(r.nextInt(this.width), r.nextInt(this.height)));
        }
        bookmarks.clear();
        PlayerData data = Minecraft.getInstance().player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get();
        for (int i = 0; i < MAX_BOOKMARKS; i++) {
            String resName = data.getString(PlayerDataLib.BOOKMARK.apply(i));
            if (resName != null) {
                bookmarks.add(ResearchRegistry.researches.get(resName));
            } else {
                break;
            }
        }
        //bookmarks.add(ResearchRegistry.researches.get("FIRSTDREAMS"));
        //bookmarks.add(ResearchRegistry.researches.get("FUMESPREADER"));
    };

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        PoseStack pPoseStack = guiGraphics.pose();
        if (highlightedMarkedResearch != null && counter - highlightCounter < 10) {
            float magnitude = (float) Math.log10((1 + pPartialTick + counter - highlightCounter));
            topX = (int) ((highlightedMarkedResearch.getX()*15*factor - width / 2 - highlightOriginX) * magnitude) + highlightOriginX;
            topY = (int) ((highlightedMarkedResearch.getY()*15*factor - height / 2 - highlightOriginY) * magnitude) + highlightOriginY;
        }


        guiGraphics.fill(0, 0, width, height, 0xFF000000);
        for (ResearchConnection rc : connections)
            this.drawConnection(guiGraphics, rc, pPartialTick);
        for (Point p : stars) {
            guiGraphics.fill(p.x, p.y, p.x + 1, p.y + 1, 0xFFFFFFFF);
        }
        guiGraphics.setColor(0.8F, 0.8F, 0.8F, 1);
        for (Research r : clickables) this.drawResearchBackground(r, guiGraphics, pPartialTick);
        guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1);
        for (Research r : visibles) this.drawResearchBackground(r, guiGraphics, pPartialTick);
        float coloring = 0.52F + (float) (Math.sin((this.counter + pPartialTick) / 30 * 2 * Math.PI) / 4);
        guiGraphics.setColor(coloring, coloring, coloring, 1);
        for (Research r : newClickables) this.drawResearchBackground(r, guiGraphics, pPartialTick);
        for (Research r : clickables) this.drawResearch(guiGraphics, r, pMouseX, pMouseY);
        for (Research r : visibles) this.drawResearch(guiGraphics, r, pMouseX, pMouseY);
        for (Research r : newClickables) this.drawResearch(guiGraphics, r, pMouseX, pMouseY);
        drawBookmarks(guiGraphics, pMouseX, pMouseY, pPartialTick);
        drawEye(guiGraphics, pPartialTick, pMouseX, pMouseY);

        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        //super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }

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
        if (firstBookmarkMadeCounter > 0) {
            firstBookmarkMadeCounter--;
        }
    }

    private void drawBookmarks(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        Research hovered = getHoveredBookmark(mouseX, mouseY);
        if (firstBookmarkMadeCounter > 0) {
            guiGraphics.drawString(minecraft.font, Component.translatable("gui.necronomicon.bookmarkmade"), 2, 25, 0xFFFFFF | ((0xFF * Math.min(20, firstBookmarkMadeCounter) / 20) << 24));
        }
        poseStack.translate(0, 0, 120);
        RenderSystem.enableBlend();
        //RenderSystem.setShaderTexture(0, BOOKMARK);
        int y = 50;
        for (Research res : bookmarks) {
            int x = res == hovered ? 0 : -65;
            guiGraphics.blit(BOOKMARK, x, y, 0, 0, 80, 20, 80, 20);
            if (x == 0) {
                guiGraphics.drawString(minecraft.font, Component.translatable(res.getName()), x + 2, y + 6, 0xFFAAE2E2);
                RenderSystem.enableBlend();
            }
            y += 24;
        }
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    private void drawResearch(GuiGraphics guiGraphics, Research res, int mouseX, int mouseY) {
        PoseStack poseStack = guiGraphics.pose();
        int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
        if (resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {
            ItemStack[] icons = res.getIconStacks();
            if (icons.length > 0) {
                guiGraphics.setColor(1, 1, 1,1);
                guiGraphics.renderItem(icons[counter % 20 % icons.length], resX - topX, resY - topY);
            }
            if (updated.contains(res)) {
                guiGraphics.blit(RESEARCH_UPDATED_MARKER, resX - topX + 4, resY - topY - 12, 0, 0, 24, 24, 24, 24);
            }
            if (mouseX > resX - topX - 4 && mouseX < resX - topX + 20 && mouseY > resY - topY - 4 && mouseY < resY - topY + 20) {
                //RenderSystem.depthFunc(3);
                guiGraphics.renderTooltip(minecraft.font, Component.translatable(res.getName()), mouseX, mouseY);
                if (showBookmarkHint) {
                    RenderSystem.enableBlend();
                    guiGraphics.blit(MOUSE_RIGHT_CLICK, mouseX + 5, mouseY + 10, 0, 0, 32, 32, 32, 32);
                    RenderSystem.disableBlend();
                }
            }
        }
    }

    private void drawResearchBackground(Research res, GuiGraphics guiGraphics, float partialTicks) {
        PoseStack pPoseStack = guiGraphics.pose();
        int resX = res.getX() * 15 * factor, resY = res.getY() * 15 * factor;
        if (resX > topX - 24 && resX < topX + this.width && resY > topY - 24 && resY < topY + this.height) {
            if (res != highlightedMarkedResearch || counter - highlightCounter > 10) {
                guiGraphics.blit(RESEARCH_BACKGROUND, resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
                if (res == highlightedMarkedResearch) {
                    RenderSystem.enableBlend();
                    int increase = (int) ((partialTicks + counter - highlightCounter - 10) * 3);
                    guiGraphics.setColor(0.8F, 0.8F, 0.8F, Math.max(0, Math.min(1, (highlightCounter + 31 - counter - partialTicks) / 11F)));
                    guiGraphics.blit(RESEARCH_HIGHLIGHT, resX - topX - 4 - increase, resY - topY - 4 - increase, 0, 0, 24 + increase * 2, 24 + increase * 2, 24 + increase * 2, 24 + increase * 2);
                    guiGraphics.setColor(0.8F, 0.8F, 0.8F, 1);
                }
            } else {
                guiGraphics.setColor((partialTicks + counter - highlightCounter) / 20F + 0.5F, (partialTicks + counter - highlightCounter) / 20F + 0.5F, (partialTicks + counter - highlightCounter) / 20F + 0.5F, 1);
                guiGraphics.blit(RESEARCH_BACKGROUND_WHITE, resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
                guiGraphics.setColor(0.8F, 0.8F, 0.8F, 1);

            }
            //drawModalRectWithCustomSizedTexture(resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
        }
        // TEST FOR FOREGROUND renderTooltip(pPoseStack, new TranslatableComponent(res.getName()), resX - topX, resY - topY);

    }

    private void drawConnection(GuiGraphics guiGraphics, ResearchConnection rc, float partialTicks) {
        PoseStack pPoseStack = guiGraphics.pose();
        if (rc.shouldRender(topX, topY, width, height)) {
            Point left = rc.getLeftPoint(), right = rc.getRightPoint();
            double dist = left.distance(right) * 15 * factor;
            int lx = left.x * 15 * factor, ly = left.y * 15 * factor, rx = right.y * 15 * factor, ry = right.y * 15 * factor;
            pPoseStack.pushPose();
            double phi = Math.asin((right.y - left.y) * 15 * factor / dist);
            pPoseStack.translate(lx - topX + 8, ly - topY + 8, 0);

            pPoseStack.mulPose(Axis.ZP.rotation((float) (phi)));
            for (int i = 0; i < dist; i++) {
                int signum = (int) Math.signum(counter % 80 - 40);
                double amplifier = 15 * (signum * Math.pow((counter % 40 + partialTicks) / 20 - 1, 4) - signum);
                int y = (int) (amplifier * Math.sin(i * Math.PI / dist));
                guiGraphics.fill(i, y, i + 1, y + 1, connectionColor);
            }
            pPoseStack.popPose();
        }
    }

    private void drawEye(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        PoseStack poseStack = guiGraphics.pose();
        if (!updated.isEmpty()) {

            int counterMod32 = counter & 31;
            int pupilX = (int) (pupilXOffset + (pupilNextXOffset - pupilXOffset) * (counterMod32 + partialTicks) / 4);
            int pupilY = (int) (pupilYOffset + (pupilNextYOffset - pupilYOffset) * (counterMod32 + partialTicks) / 4);

            poseStack.pushPose();
            poseStack.translate(width - 142 + 64, height - 142 + 64, 0);
            if (mouseX > width - 142 && mouseY > height - 142) {
                poseStack.scale(1.1F, 1.1F, 0);
            }

            guiGraphics.setColor(1, 1, 1, 1);
            RenderSystem.enableBlend();
            guiGraphics.blit(EYE, -64, -64, 0, 0, 128, 128, 128, 128);
            guiGraphics.blit(EYE_PUPIL, -64 + pupilX, -64 + pupilY, 0, 0, 128, 128, 128, 128);
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
        Research bookmark = getHoveredBookmark(pMouseX, pMouseY);
        if (bookmark != null) {
            if (pButton == 0) {
                highlightedMarkedResearch = bookmark;
                highlightCounter = counter;
                highlightOriginX = topX;
                highlightOriginY = topY;
            } else {
                int index = bookmarks.indexOf(bookmark);
                bookmarks.remove(index);
                // we scaled back the bookmarks list, let's scale back the capability data as well
                for (; index < bookmarks.size(); index++) {
                    DataUtilClient.setStringAndSync(PlayerDataLib.BOOKMARK.apply(index), bookmarks.get(index).getKey(), false);
                }
                DataUtilClient.removeStringAndSync(PlayerDataLib.BOOKMARK.apply(index));
            }
        }
        for (Research res : this.clickables) {
            if (openResearch(res, pMouseX, pMouseY, pButton)) return true;
        }
        for (Research res : this.newClickables) {
            if (openResearch(res, pMouseX, pMouseY, pButton)) return true;
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

    private boolean openResearch(Research res, double mouseX, double mouseY, int mouseButton) {
        int resX = res.getX() * 15 * factor - topX - 4, resY = res.getY() * 15 * factor - topY - 4;
        if (mouseX > resX - 4 && mouseX < resX + 24 && mouseY > resY - 4 && mouseY < resY + 24) {
            if (mouseButton == 0) {
                ResearchStatus status = ResearchUtil.getResearch(minecraft.player, res.getKey());
                if (status.getStage() == -1) ResearchUtilClient.progressResearchClientAndSync(res.getKey());
                if (status.isUpdated()) ResearchUtilClient.openUpdatedResearchClientAndSync(res.getKey());
                savePositionData();
                minecraft.setScreen(getResearchGui(status));
                return true;
            } else {
                if (bookmarks.size() < MAX_BOOKMARKS && !bookmarks.contains(res)) {
                    bookmarks.add(res);
                    DataUtilClient.setStringAndSync(PlayerDataLib.BOOKMARK.apply(bookmarks.size()-1), res.getKey(), false);
                    minecraft.player.playSound(SoundEvents.BOOK_PUT, 1, 1);
                    if (showBookmarkHint) {
                        DataUtilClient.setBooleanAndSync(PlayerDataLib.MADE_BOOKMARK, true, false);
                        showBookmarkHint = false;
                        firstBookmarkMadeCounter = 80;
                    }
                } else {
                    minecraft.player.playSound(SoundEvents.LEVER_CLICK, 1, 1);
                }

            }
        }
        return false;
    }

    private Research getHoveredBookmark(double mouseX, double mouseY) {
        if (mouseX < 81 && mouseY > 50) {
            int y = 50;
            for (Research res : bookmarks) {
                y += 24;
                if(mouseY <= y) {
                    return res;
                }
            }
        }
        return null;
    }

    private Screen getResearchGui(ResearchStatus status) {
        return switch (status.res.getKey()) {
            case "CRAFTING" -> new CraftingRegistryGui(status);
            case "MEMORIES" -> new MemoryRegistryGui(status);
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
