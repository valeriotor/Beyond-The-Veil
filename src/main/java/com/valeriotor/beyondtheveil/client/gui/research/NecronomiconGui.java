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
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
    private final List<Research> newClickables = new ArrayList<>();
    private final List<Research> clickables = new ArrayList<>();
    private final List<Research> visibles = new ArrayList<>();
    private final Set<Research> updated = new HashSet<>();
    private final List<ResearchConnection> connections = new ArrayList<>();
    private final List<Point> stars = new ArrayList<>();
    private int counter = 0;
    private Research highlightedMarkedResearch;
    private Iterator<Research> highlightIterator;
    private int highlightCounter = 0;
    private final int connectionColor;
    private final List<Research> bookmarks = new ArrayList<>();
    private boolean showBookmarkHint;
    private int imageWidth; // helper variable to copy JournalGui's logic
    private int imageHeight; // helper variable to copy JournalGui's logic
    private float scaleFactor = 1.5F;
    private static final int BACKGROUND_BASE_WIDTH = 843;
    private static final int BACKGROUND_BASE_HEIGHT = 505;

    private static final ResourceLocation RESEARCH_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_background.png");
    private static final ResourceLocation RESEARCH_BACKGROUND_2 = new ResourceLocation(References.MODID, "textures/gui/res_background_2.png");
    private static final ResourceLocation RESEARCH_BACKGROUND_3 = new ResourceLocation(References.MODID, "textures/gui/res_background_3.png");
    private static final ResourceLocation RESEARCH_BACKGROUND_WHITE = new ResourceLocation(References.MODID, "textures/gui/res_background_white.png");
    public static final ResourceLocation RESEARCH_HIGHLIGHT = new ResourceLocation(References.MODID, "textures/gui/res_highlight.png");
    public static final ResourceLocation RESEARCH_UPDATED_MARKER = new ResourceLocation(References.MODID, "textures/gui/res_marker.png");
    private static final ResourceLocation EYE = new ResourceLocation(References.MODID, "textures/gui/eye.png");
    private static final ResourceLocation EYE_PUPIL = new ResourceLocation(References.MODID, "textures/gui/eye_pupil.png");
    private static final ResourceLocation TENDRIL_EYE = new ResourceLocation(References.MODID, "textures/gui/tendril_eye.png");
    private static final ResourceLocation BOOKMARK = new ResourceLocation(References.MODID, "textures/gui/bookmark_grayed.png");
    private static final ResourceLocation MOUSE_RIGHT_CLICK = new ResourceLocation(References.MODID, "textures/gui/mouse_right_click.png");
    private int firstBookmarkMadeCounter = 0;
    private static final int JSON_TO_REAL_COORD_FACTOR = 32;
    private static final int RESEARCH_BACKGROUND_SIZE = 48;
    private float baseFactor;
    private final int stage;

    public NecronomiconGui() {
        super(Component.translatable("gui.necronomicon")); // TODO change to TranslatableComponent("gui.necronomicon")
        LocalPlayer p = Minecraft.getInstance().player;
        Map<String, ResearchStatus> map = ResearchUtil.getResearches(p);
        PlayerData data = p.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get();
        this.topX = data.getOrSetInteger(PlayerDataLib.necro_x.name(), -400, false);
        this.topY = data.getOrSetInteger(PlayerDataLib.necro_y.name(), -200, false);
        if (map.get("FIRSTDREAMS").getStage() == -1) {
            this.topX = -400;
            this.topY = -200;
        }
        this.scaleFactor = data.getOrSetInteger(PlayerDataLib.necro_fac.name(), 3, false) / 4F;
        for (Entry<String, ResearchStatus> entry : map.entrySet()) {
            if (entry.getValue().isKnown(map, data)) {
                if (entry.getValue().getStage() == -1) {
                    newClickables.add(entry.getValue().res);
                } else {
                    clickables.add(entry.getValue().res);
                    boolean b = entry.getValue().isHidden(p);
                    boolean a = b; // what's this for??
                }
            } else if (entry.getValue().isVisible(map, data)) {
                visibles.add(entry.getValue().res);
            }
            if (entry.getValue().isUpdated()) {
                updated.add(entry.getValue().res);
            }
        }
        if (map.get("MEMORIES").isVisible(p)) {
            for (PlayerData.MemoryStatus status : DataUtil.getMemoryStatuses(p)) {
                if (status.isChanged()) {
                    updated.add(map.get("MEMORIES").res);
                    break;
                }
            }
        }

        for (ResearchConnection rc : ResearchRegistryClient.connections) {
            if (rc.isVisible(map, data)) {
                connections.add(rc);
            }
        }
        this.connectionColor = (255 << 24) | (ConfigLib.connectionRed << 16) | (20 << 8) | ConfigLib.connectionBlue;
        showBookmarkHint = !data.getBoolean(PlayerDataLib.made_bookmark.name()) && map.get("SLEEP_CHAMBER").getStage() >= 1; // TODO this should be SLEEP_CHAMBER, change if otherwise

        if (DataUtil.getBoolean(p, PlayerDataLib.slew_keeper.name()) && !DataUtil.getBoolean(p, PlayerDataLib.met_mirror.name())) {
            stage = 2;
        } else if (ResearchUtil.isResearchComplete(p, "COMMUNION")) {
            stage = 1;
        } else {
            stage = 0;
        }
    }

    @Override
    public void init() {
        // A scaleFactor of 1 will mean that 30 is one 30th of the screen height
        // this is achieved via the baseFactor
        int b = height / 30; // E.g., is the height 1800? Then we need to zoom everything 2x by default. Is the height 450? Zoom out by 2x.
        baseFactor = b / 30F;
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
        PoseStack pose = guiGraphics.pose();
        guiGraphics.fill(0, 0, width, height, 0xFF000000);
        //guiGraphics.drawString(minecraft.font, String.format("X: %f, Y: %f", ((pMouseX + topX)) / JSON_TO_REAL_COORD_FACTOR / scaleFactor / baseFactor, (pMouseY + topY) / JSON_TO_REAL_COORD_FACTOR / scaleFactor / baseFactor), 0, 15, 0xFFFFFFFF);
        for (Point p : stars) {
            pose.pushPose();
            pose.translate(p.x, p.y, 0);
            pose.scale(baseFactor * 2.5F, baseFactor * 2.5F, 1);
            guiGraphics.fill(0, 0, 1, 1, 0xFFFFFFFF);
            pose.popPose();
        }
        pose.pushPose();
        pose.scale(baseFactor, baseFactor, 1);
        pose.scale(scaleFactor, scaleFactor, 1);
        pMouseX /= scaleFactor * baseFactor;
        pMouseY /= scaleFactor * baseFactor;
        //guiGraphics.drawString(minecraft.font, String.format("X: %f, Y: %f", ((pMouseX + topX)) / JSON_TO_REAL_COORD_FACTOR / scaleFactor, (pMouseY + topY) / JSON_TO_REAL_COORD_FACTOR / scaleFactor), 0, 30, 0xFFFFFFFF);
        //guiGraphics.drawString(minecraft.font, String.format("X: %f, Y: %f", ((topX)) / JSON_TO_REAL_COORD_FACTOR / scaleFactor, (topY) / JSON_TO_REAL_COORD_FACTOR / scaleFactor), 0, 45, 0xFFFFFFFF);
        //guiGraphics.drawString(minecraft.font, String.format("X: %f, Y: %f", ((width)) / JSON_TO_REAL_COORD_FACTOR / scaleFactor / baseFactor / scaleFactor, (height) / JSON_TO_REAL_COORD_FACTOR / scaleFactor / baseFactor / scaleFactor), 0, 60, 0xFFFFFFFF);
        //guiGraphics.drawString(minecraft.font, String.format("X: %d, Y: %d", ((width)), (height)), 0, 75, 0xFFFFFFFF);
        if (highlightedMarkedResearch != null && counter - highlightCounter < 10) {
            float magnitude = (float) Math.log10((1 + pPartialTick + counter - highlightCounter));
            topX = (int) ((highlightedMarkedResearch.getX()*JSON_TO_REAL_COORD_FACTOR*scaleFactor - width / scaleFactor / baseFactor / 2 - highlightOriginX) * magnitude) + highlightOriginX;
            topY = (int) ((highlightedMarkedResearch.getY()*JSON_TO_REAL_COORD_FACTOR*scaleFactor - height / scaleFactor / baseFactor / 2 - highlightOriginY) * magnitude) + highlightOriginY;
        }


        for (ResearchConnection rc : connections)
            this.drawConnection(guiGraphics, rc, pPartialTick);

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
        pose.popPose();
        //guiGraphics.drawString(minecraft.font, String.format("X: %d, Y: %d", pMouseX, pMouseY), 0, 0, 0xFFFFFFFF);
        //guiGraphics.drawString(minecraft.font, String.format("Width: %d, Height: %d", guiGraphics.guiWidth(), guiGraphics.guiHeight()), 0, 15, 0xFFFFFFFF);
        //guiGraphics.drawString(minecraft.font, String.format("Width: %d, Height: %d", width, height), 0, 30, 0xFFFFFFFF);
        //super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void tick() {
        counter++;
        if ((counter & 31) == 0) {
            int mouseX = (int)(this.minecraft.mouseHandler.xpos() * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth());
            int mouseY = (int)(this.minecraft.mouseHandler.ypos() * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight());
            mouseX /= scaleFactor * baseFactor;
            mouseY /= scaleFactor * baseFactor;
            float adjustedWidth = width / scaleFactor / baseFactor;
            float adjustedHeight = height / scaleFactor / baseFactor;
            if (mouseX > adjustedWidth - 142 && mouseY > adjustedHeight - 142) {
                double degree = Math.atan2(mouseY-(adjustedHeight-142+64), mouseX-(adjustedWidth-142+64));
                double magnitude = Math.min(11, Math.sqrt(Math.pow(mouseX-(adjustedWidth-142+64), 2) + Math.pow(mouseY-(adjustedHeight-142+64), 2)))/2;
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
        double i1 = JSON_TO_REAL_COORD_FACTOR * scaleFactor;
        double resX = res.getX() * i1, resY = res.getY() * i1;
        if (resX > topX - 24 && resX < topX + this.width / baseFactor && resY > topY - 24 && resY < topY + this.height / baseFactor) {
            int rbs = RESEARCH_BACKGROUND_SIZE;
            ItemStack[] icons = res.getIconStacks();
            if (icons.length > 0) {
                guiGraphics.setColor(1, 1, 1,1);
                poseStack.pushPose();
                poseStack.translate(resX - topX, resY - topY, 0);
                poseStack.scale(1.25F, 1.25F, 1);
                guiGraphics.renderItem(icons[counter % 20 % icons.length], -8, -8);
                poseStack.popPose();
            }
            if (updated.contains(res)) {
                poseStack.pushPose();
                poseStack.translate(resX - topX, resY - topY, 0);
                poseStack.scale(1.25F, 1.25F, 1);
                guiGraphics.blit(RESEARCH_UPDATED_MARKER, -rbs / 2, -rbs / 2, 0, 0, rbs, rbs, rbs, rbs);
                poseStack.popPose();
            }
            if (mouseX > resX - topX - (rbs * 1.2) / 2D && mouseX < resX - topX + (rbs * 1.2) / 2D && mouseY > resY - topY - (rbs * 1.2) / 2D && mouseY < resY - topY + (rbs * 1.2) / 2D) {
                //RenderSystem.depthFunc(3);
                poseStack.pushPose();
                poseStack.translate(mouseX, mouseY, 0);
                poseStack.scale(1.3F, 1.3F, 1);
                guiGraphics.renderTooltip(minecraft.font, Component.translatable(res.getName()), 0, 0);
                poseStack.popPose();
                if (showBookmarkHint) {
                    RenderSystem.enableBlend();
                    guiGraphics.blit(MOUSE_RIGHT_CLICK, mouseX + 5, mouseY + 10, 0, 0, 32, 32, 32, 32);
                    RenderSystem.disableBlend();
                }
            }
        }
    }

    private void drawResearchBackground(Research res, GuiGraphics guiGraphics, float partialTicks) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        double resX = res.getX() * JSON_TO_REAL_COORD_FACTOR * scaleFactor, resY = res.getY() * JSON_TO_REAL_COORD_FACTOR * scaleFactor;
        if (resX > topX - 48 && resX < topX + this.width / baseFactor && resY > topY - 48 && resY < topY + this.height / baseFactor) {
            pose.translate(resX - topX, resY - topY, 0);
            int rbs = RESEARCH_BACKGROUND_SIZE;
            if (stage == 2) {
                rbs = rbs * 5 / 4;
            }
            if (res != highlightedMarkedResearch || counter - highlightCounter > 10) {
                RenderSystem.disableBlend();
                guiGraphics.blit(backgroundForStage(), -rbs / 2, -rbs / 2, 0, 0, rbs, rbs, rbs, rbs);
                if (res == highlightedMarkedResearch) {
                    RenderSystem.enableBlend();
                    int increase = (int) ((partialTicks + counter - highlightCounter - 10) * 3);
                    pose.translate(-increase, -increase, 0);
                    guiGraphics.setColor(0.8F, 0.8F, 0.8F, Math.max(0, Math.min(1, (highlightCounter + 31 - counter - partialTicks) / 11F)));
                    guiGraphics.blit(RESEARCH_HIGHLIGHT, -rbs / 2, -rbs / 2, 0, 0, rbs + increase * 2, rbs + increase * 2, rbs + increase * 2, rbs + increase * 2);
                    guiGraphics.setColor(0.8F, 0.8F, 0.8F, 1);
                }
            } else {
                guiGraphics.setColor((partialTicks + counter - highlightCounter) / 20F + 0.5F, (partialTicks + counter - highlightCounter) / 20F + 0.5F, (partialTicks + counter - highlightCounter) / 20F + 0.5F, 1);
                guiGraphics.blit(RESEARCH_BACKGROUND_WHITE, -rbs / 2, -rbs / 2, 0, 0, rbs, rbs, rbs, rbs);
                guiGraphics.setColor(0.8F, 0.8F, 0.8F, 1);

            }
            //drawModalRectWithCustomSizedTexture(resX - topX - 4, resY - topY - 4, 0, 0, 24, 24, 24, 24);
        }
        pose.popPose();
        // TEST FOR FOREGROUND renderTooltip(pPoseStack, new TranslatableComponent(res.getName()), resX - topX, resY - topY);

    }

    private void drawConnection(GuiGraphics guiGraphics, ResearchConnection rc, float partialTicks) {
        PoseStack pose = guiGraphics.pose();
        if (rc.shouldRender((int) (topX / JSON_TO_REAL_COORD_FACTOR / scaleFactor), (int) (topY / JSON_TO_REAL_COORD_FACTOR / scaleFactor), (int) (width / JSON_TO_REAL_COORD_FACTOR / scaleFactor / baseFactor / scaleFactor), (int) (height / JSON_TO_REAL_COORD_FACTOR / scaleFactor / baseFactor / scaleFactor))) {
            Point left = rc.getLeftPoint(), right = rc.getRightPoint();
            double i1 = JSON_TO_REAL_COORD_FACTOR * scaleFactor;
            float dist = (float) (left.distance(right) * i1);
            double lx = left.x * i1, ly = left.y * i1, rx = right.y * i1, ry = right.y * i1;
            pose.pushPose();
            double phi = Math.asin((right.y - left.y) * i1 / dist);
            pose.translate(lx - topX, ly - topY, 0);
            int counter1 = counter;
            if (stage == 2) {
                counter1 *= 2;
                partialTicks *= 2;
            }

            pose.mulPose(Axis.ZP.rotation((float) (phi)));
            for (int x = 0; x < (stage == 2 ? 2 : 1); x++) {
                for (int i = 0; i < dist; i+=4) {
                    int signum = (int) Math.signum(counter1 % 80 - 40);
                    float a = (counter1 % 40 + partialTicks) / 20 - 1;
                    a = a * a * a * a;
                    float amplifier = 12 * (signum * a - signum);
                    if (stage == 0) {
                        amplifier = 0;
                    }
                    int y = (int) (amplifier * Mth.sin(i * Mth.PI / dist));
                    boolean notEye = (i / 4) % 5 < 4 || i < 25 || i > dist - 35;
                    if ((notEye || stage != 2) && x == 0) {
                        guiGraphics.fill(i, y, i + 4, y + 4, notEye ? connectionColor : 0xFF00231A);
                        //guiGraphics.fill(i, y + (notEye ? 0 : -1), i + 4, y + (notEye ? 3 : 4), notEye ? connectionColor : 0xFF00231A);
                        pose.pushPose();
                        pose.translate(0, y + 2, 0);
                        pose.scale(1, 0.325F, 1);
                        guiGraphics.fill(i, -2, i + 4, 2, 0xFF002F00);
                        pose.popPose();
                    } else if (!(notEye || stage != 2) && x == 1) {
                        pose.pushPose();
                        int b = 10;
                        guiGraphics.blit(TENDRIL_EYE, i, y - 3, 0, 0, b, b, b, b);
                        pose.popPose();
                    }
                }

            }
            pose.popPose();
        }
    }

    private void drawEye(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        PoseStack poseStack = guiGraphics.pose();
        if (!updated.isEmpty() || !newClickables.isEmpty()) {

            int counterMod32 = counter & 31;
            int pupilX = (int) (pupilXOffset + (pupilNextXOffset - pupilXOffset) * (counterMod32 + partialTicks) / 4);
            int pupilY = (int) (pupilYOffset + (pupilNextYOffset - pupilYOffset) * (counterMod32 + partialTicks) / 4);

            poseStack.pushPose();
            poseStack.translate(width / scaleFactor / baseFactor - 142 + 64, height / scaleFactor / baseFactor - 142 + 64, 200);
            if (mouseX > width / scaleFactor / baseFactor - 142 && mouseY > height / scaleFactor / baseFactor - 142) {
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
        topX = (int) MathHelperBTV.clamp(-700, 3840 - this.width / 2F, topX - pDragX / baseFactor / 1.5);
        topY = (int) MathHelperBTV.clamp(-700, 2160 - this.height / 2F, topY - pDragY / baseFactor / 1.5);
        return true;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        //this.factor = MathHelperBTV.clamp(2, 5, this.factor + (int) Math.signum(pDelta));
        this.scaleFactor = Mth.clamp(this.scaleFactor + (int) Math.signum(pDelta) * 0.25F, 1.75F, 3.25F);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (super.mouseClicked(pMouseX, pMouseY, pButton)) {
            return true;
        }
        pMouseX /= scaleFactor * baseFactor;
        pMouseY /= scaleFactor * baseFactor;
        if (pMouseX > width / scaleFactor / baseFactor - 142 && pMouseY > height / scaleFactor / baseFactor - 142) {
            if (!updated.isEmpty()) {
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
            if (!newClickables.isEmpty()) {
                if (highlightIterator == null || !highlightIterator.hasNext()) {
                    highlightIterator = newClickables.iterator();
                }
                highlightedMarkedResearch = highlightIterator.next();
                highlightCounter = counter;
                highlightOriginX = topX;
                highlightOriginY = topY;
                //topX = highlightedMarkedResearch.getX()*15*factor - width/2;
                //topY = highlightedMarkedResearch.getY()*15*factor - height/2;
                return true;
            }
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
        float i1 = JSON_TO_REAL_COORD_FACTOR * scaleFactor;
        float resX = res.getX() * i1, resY = res.getY() * i1;
        int rbs = RESEARCH_BACKGROUND_SIZE;
        if (mouseX > resX - topX - (rbs * 1.2) / 2D && mouseX < resX - topX + (rbs * 1.2) / 2D && mouseY > resY - topY - (rbs * 1.2) / 2D && mouseY < resY - topY + (rbs * 1.2) / 2D) {
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
                        DataUtilClient.setBooleanAndSync(PlayerDataLib.made_bookmark.name(), true, false);
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
            //case "SENTIENCE" -> new JournalGui();
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
        DataUtilClient.setIntAndSync(PlayerDataLib.necro_x.name(), this.topX, false);
        DataUtilClient.setIntAndSync(PlayerDataLib.necro_y.name(), this.topY, false);
        DataUtilClient.setIntAndSync(PlayerDataLib.necro_fac.name(), (int) (this.scaleFactor * 4), false);
    }

    private ResourceLocation backgroundForStage() {
        return switch (stage) {
            case 0 -> RESEARCH_BACKGROUND;
            case 1 -> RESEARCH_BACKGROUND_2;
            default -> RESEARCH_BACKGROUND_3;
        };
    }
}
