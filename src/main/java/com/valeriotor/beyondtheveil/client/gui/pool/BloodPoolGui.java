package com.valeriotor.beyondtheveil.client.gui.pool;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.gui.elements.*;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntity;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.BloodPoolEntityType;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.valeriotor.beyondtheveil.client.gui.research.ResearchPageGui.LEFT_ARROW;
import static com.valeriotor.beyondtheveil.client.gui.research.ResearchPageGui.RIGHT_ARROW;

public class BloodPoolGui extends Screen {

    private static final int RIGHT_PAGE_HEIGHT = 519;
    private static final int RIGHT_PAGE_WIDTH = 240;
    private static final int BACKGROUND_BASE_WIDTH = 1034;
    private static final int BACKGROUND_BASE_HEIGHT = 634;
    private static final int ENTRY_LIST_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 + 55;
    private static final int ENTRY_LIST_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 60;
    private static final int RIGHT_PAGE_LEFT_X = 225;
    private static final int RIGHT_PAGE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 20;
    private static final int ENTRY_LIST_BASE_WIDTH = 628;
    private static final int ENTRY_LIST_BASE_HEIGHT = 520;
    private static final int ENTRY_BASE_WIDTH = 615;
    private static final int ENTRY_BASE_HEIGHT = 104;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/blood_pool/background.png");
    private static final ResourceLocation ROW = new ResourceLocation(References.MODID, "textures/gui/blood_pool/row.png");
    private static final ResourceLocation TEXT_BLOCK_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/blood_pool/text_block_background.png");
    private static final ResourceLocation ICON_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/blood_pool/icon_background.png");
    private static final ResourceLocation BUTTON = new ResourceLocation(References.MODID, "textures/gui/blood_pool/button.png");
    private static final Map<BloodPoolEntityType, ResourceLocation> ICONS = new HashMap<>();

    static {
        for (BloodPoolEntityType value : BloodPoolEntityType.values()) {
            ICONS.put(value, new ResourceLocation(References.MODID, "textures/gui/blood_pool/" + value.name().toLowerCase() + ".png"));
        }
    }

    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private ScrollableList<RowEntry> rows;
    private RightPage rightPage;
    private ElementHolder buttonHolder;
    private TexturedButton spawnButton;

    public BloodPoolGui() {
        super(Component.translatable("gui.blood_pool.title"));
        /* TODO Data is stored server-side in SavedData (global).
           Synced entirely to client on login (order of MBs at worst??).
           Every time a change occurs, sync that single change.
           Otherwise try caching (avoid DoS...)
         */
    }

    @Override
    protected void init() {
        scaleFactor = 1;
        imageWidth = BACKGROUND_BASE_WIDTH;
        imageHeight = BACKGROUND_BASE_HEIGHT;
        //entryListWidth = 343;
        //entryListHeight = 357;

        float heightRatio = 85F;
        if (imageHeight > height * heightRatio / 100) {
            scaleFactor = height * heightRatio / 100 / imageHeight;
        }
        float widthRatio = 90F;
        if (imageWidth > width * widthRatio / 100) {
            scaleFactor = Math.min(width * widthRatio / 100 / imageWidth, scaleFactor);
        }

        updateEntities();

        buttonHolder = ElementHolder.makeHolder(width, height);
        int buttonWidth = 150;
        spawnButton = buttonHolder.addElement((RIGHT_PAGE_LEFT_X + RIGHT_PAGE_WIDTH / 2) * 10 / 15  - buttonWidth / 2, (RIGHT_PAGE_TOP_Y + RIGHT_PAGE_HEIGHT * 101 / 100) * 10 / 15, new TexturedButton(buttonWidth, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.blood_pool.spawn"), texturedButton -> {
            if (rightPage != null) {
                GenericToServerPacket packet = GenericToServerPacket.spawnBloodPoolEntity(rightPage.triplet, rightPage.entity.getUuid());
                Messages.sendToServer(packet);
                rightPage = null;
                spawnButton.active = spawnButton.visible = false;
            }
        }));
        spawnButton.visible = spawnButton.active = rightPage != null;
    }

    public void updateEntities() {
        List<RowEntry> entryList = new ArrayList<>();
        Map<ColorTriplet, List<BloodPoolEntity>> entitiesByTriplet = ClientData.getInstance().getBloodPoolData().getEntitiesByTriplet(Minecraft.getInstance().player.getUUID());
        for (Map.Entry<ColorTriplet, List<BloodPoolEntity>> entry : entitiesByTriplet.entrySet()) {
            entryList.add(new RowEntry(entry.getKey(), new ArrayList<>(entry.getValue())));
        }
        entryList.sort(Comparator.comparing(RowEntry::getTriplet));

        rows = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, entryList, ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.fill(0, 0, width, height, 0xCC111111);
        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(BACKGROUND, -imageWidth / 2, -imageHeight / 2, imageWidth, imageHeight, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (rows != null) {
            int relativeMouseX = listMouseX(pMouseX);
            int relativeMouseY = listMouseY(pMouseY);
            pose.pushPose();
            pose.translate(ENTRY_LIST_BASE_LEFT_X, ENTRY_LIST_BASE_TOP_Y, 0);
            pGuiGraphics.fill(-4, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x11111111);
            pGuiGraphics.fill(-0, -3, ENTRY_LIST_BASE_WIDTH - 2, 0, 0x44111111);
            pGuiGraphics.fill(-0, ENTRY_LIST_BASE_HEIGHT - 2, ENTRY_LIST_BASE_WIDTH - 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(-4, -3, 0, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(ENTRY_LIST_BASE_WIDTH - 2, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            rows.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY, pPartialTick);
            pose.popPose();
        }

        if (rightPage != null) {
            int relativeMouseX = listMouseX(pMouseX);
            int relativeMouseY = listMouseY(pMouseY);
            pose.pushPose();
            pose.translate(RIGHT_PAGE_LEFT_X, RIGHT_PAGE_TOP_Y, 0);
            rightPage.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY, pPartialTick);
            pose.popPose();
        }

        pose.pushPose();
        pose.scale(1.5F, 1.5F, 1);
        buttonHolder.render(pose, pGuiGraphics, 0xFFFFFFFF, (int) scaledMouseX(pMouseX), (int) scaledMouseY(pMouseY), pPartialTick);
        pose.popPose();

        pose.popPose();
    }

    private double scaledMouseX(double mouseX) {
        return (mouseX - width / 2D) / scaleFactor / 1.5;
    }

    private double scaledMouseY(double mouseY) {
        return (mouseY - height / 2D) / scaleFactor / 1.5;
    }

    private int listMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - ENTRY_LIST_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int listMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - ENTRY_LIST_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    private int pageMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - RIGHT_PAGE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int pageMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - RIGHT_PAGE_TOP_Y * scaleFactor) / scaleFactor);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (rows.mouseClicked(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        } else if (rightPage != null && rightPage.mouseClicked(pageMouseX(pMouseX), pageMouseY(pMouseY), pButton)) {
            return true;
        } else if (buttonHolder.mouseClicked(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (rows.mouseScrolled(listMouseX(pMouseX), listMouseY(pMouseY), pDelta)) {
            return true;
        } else if (rightPage != null && rightPage.mouseScrolled(pageMouseX(pMouseX), pageMouseY(pMouseY), pDelta)) {
            return true;
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
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

    private class RowEntry extends Element {

        private final ColorTriplet triplet;
        private final List<BloodPoolEntity> entities;
        private final ItemStack[] dyeItems;
        private int offset = 0;

        protected RowEntry(ColorTriplet triplet, List<BloodPoolEntity> entities) {
            super(ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            this.triplet = triplet;
            this.entities = entities;
            dyeItems = new ItemStack[3];
            for (int i = 0; i < 3; i++) {
                dyeItems[i] = triplet.index(i) != null ? new ItemStack(DyeItem.byColor(triplet.index(i))) : null;
            }
        }

        public ColorTriplet getTriplet() {
            return triplet;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(ROW, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            for (int i = 0; i < 3; i++) {
                ItemStack dyeItem = dyeItems[i];
                if (dyeItem != null) {
                    poseStack.pushPose();
                    poseStack.translate(24, 20 + 32 * i, 0);
                    poseStack.scale(1.25F, 1.25F, 1);
                    graphics.renderItem(dyeItem, -8, -8);
                    poseStack.popPose();
                    if (relativeMouseX > 16 && relativeMouseX < 32 && relativeMouseY > 12 + 32 * i && relativeMouseY < 28 + 32 * i) {
                        graphics.renderTooltip(Minecraft.getInstance().font, dyeItem, relativeMouseX, relativeMouseY);
                    }
                }
            }
            RenderSystem.enableBlend();
            if (offset > 0) {
                poseStack.pushPose();
                poseStack.translate(80 - 24 + 20, 49, 0); // honestly completely random trial and error
                poseStack.scale(0.5F, 0.5F, 1);
                if (hoveringLeftArrow(relativeMouseX, relativeMouseY)) {
                    poseStack.scale(1.25F, 1.25F, 1);
                }
                graphics.blit(LEFT_ARROW, -47, -13, 0, 0, 54, 53, 54, 53);
                poseStack.popPose();
            }
            if (offset < entities.size() - 1) {
                poseStack.pushPose();
                poseStack.translate(576 + 10, 49, 0);
                poseStack.scale(0.5F, 0.5F, 1);
                if (hoveringRightArrow(relativeMouseX, relativeMouseY)) {
                    poseStack.scale(1.25F, 1.25F, 1);
                }
                graphics.blit(RIGHT_ARROW, -13, -13, 0, 0, 54, 53, 54, 53);
                poseStack.popPose();
            }
            RenderSystem.disableBlend();
            for (int i = 0; i < 5; i++) {
                int x = 87 + 100 * i;
                int y = 11;
                if (entities.size() > i + offset) {
                    poseStack.pushPose();
                    poseStack.translate(x, y, 0);
                    if (relativeMouseX >= x && relativeMouseX <= x + 83 && relativeMouseY >= y && relativeMouseY <= y + 83) {
                        graphics.fill(0, 0, 83, 83, 0x33444444);
                    }
                    graphics.blit(ICONS.get(entities.get(i + offset).getType()), 0, 0, 0, 0, 82, 82, 82, 82);
                    poseStack.popPose();
                }
            }
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (hoveringLeftArrow(relativeMouseX, relativeMouseY) && offset > 0) {
                offset = Math.max(0, offset - 1);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                return true;
            } else if (hoveringRightArrow(relativeMouseX, relativeMouseY) && offset < entities.size() - 1) {
                offset = Math.min(entities.size() - 1, offset + 1);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                return true;
            } else {
                for (int i = 0; i < 5; i++) {
                    int x = 87 + 100 * i;
                    int y = 11;
                    if (entities.size() > i + offset && relativeMouseX > x && relativeMouseX < x + 82 && relativeMouseY > y && relativeMouseY < y + 82) {
                        rightPage = new RightPage(entities.get(i + offset), triplet, i + offset);
                        spawnButton.visible = spawnButton.active = true;
                        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.MUD_PLACE, 1));
                        return true;
                    }
                }
            }
            return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }

        private boolean hoveringLeftArrow(double relativeMouseX, double relativeMouseY) {
            return relativeMouseX > 80 - 24 - 13 && relativeMouseX < 80 - 24 + 25 && relativeMouseY > 49 - 13 && relativeMouseY < 49 + 25;
        }

        private boolean hoveringRightArrow(double relativeMouseX, double relativeMouseY) {
            return relativeMouseX > 576 + 10 - 13 && relativeMouseX < 576 + 10 + 25 && relativeMouseY > 49 - 13 && relativeMouseY < 49 + 25;
        }
    }

    private static class RightPage extends Element {

        private static final int TEXT_BLOCK_X = RIGHT_PAGE_WIDTH / 20 + 10;
        private static final int TEXT_BLOCK_Y = RIGHT_PAGE_HEIGHT * 45 / 100 + 20;
        private static final int TEXT_BLOCK_WIDTH = RIGHT_PAGE_WIDTH * 9 / 10;
        private static final int TEXT_BLOCK_HEIGHT = RIGHT_PAGE_HEIGHT * 5 / 10;
        private final BloodPoolEntity entity;
        private final ColorTriplet triplet;
        private final int index;
        private final ScrollableList<Element> lines;
        private final float SCALE = 1.5F;

        protected RightPage(BloodPoolEntity entity, ColorTriplet triplet, int index) {
            super(RIGHT_PAGE_WIDTH, RIGHT_PAGE_HEIGHT);
            this.entity = entity;
            this.triplet = triplet;
            this.index = index;
            String description = entity.textDescription();
            List<Element> lines = new TextUtil().parseText(description, (int) ((TEXT_BLOCK_WIDTH - 15) * 92 / 100 / SCALE), Minecraft.getInstance().font);
            lines.removeIf(e -> e instanceof Separators.Separator);
            Element element = lines.get(0);
            for (int i = 0; i < 50; i++) {
                //lines.add(element);
            }
            this.lines = new ScrollableList<>((int) ((TEXT_BLOCK_WIDTH - 15) / SCALE), (int) ((TEXT_BLOCK_HEIGHT - 15) / SCALE), lines, 15, TEXT_BLOCK_HEIGHT * 5 / 100);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            poseStack.pushPose();
            poseStack.translate(getWidth() / 2F, getHeight() / 5.1F, 0);
            poseStack.scale(1.5F, 1.5F, 1);
            poseStack.pushPose();
            poseStack.translate(-87F/2, -87F/2, 0);
            graphics.blit(ICON_BACKGROUND, 0, 0, 0, 0, 87, 87, 87, 87);
            poseStack.popPose();
            graphics.blit(ICONS.get(entity.getType()), -41, -41, 0, 0, 82, 82, 82, 82);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(getWidth() / 2F, getHeight() * 0.35F, 0);
            poseStack.scale(2.2F, 2.2F, 1);
            graphics.drawCenteredString(Minecraft.getInstance().font, Component.translatable(entity.getType().getEntityType().getDescriptionId()), 0, 0, 0xFF000000 | Color.YELLOW.getRGB());
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(TEXT_BLOCK_X, TEXT_BLOCK_Y, 0);
            graphics.blit(TEXT_BLOCK_BACKGROUND, -12, -15, 0, 0, 220, 270, 220, 270);
            poseStack.pushPose();
            poseStack.scale(SCALE, SCALE, 1);
            lines.render(poseStack, graphics, color, textBlockMouseX(relativeMouseX), textBlockMouseY(relativeMouseY), pPartialTick);
            poseStack.popPose();
            poseStack.popPose();
        }

        private int textBlockMouseX(double pMouseX) {
            return (int) ((pMouseX - TEXT_BLOCK_X) / SCALE);
        }

        private int textBlockMouseY(double pMouseY) {
            return (int) ((pMouseY - TEXT_BLOCK_Y) / SCALE);
        }

        @Override
        public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
            if (lines.mouseScrolled(textBlockMouseX(relativeMouseX), textBlockMouseY(relativeMouseY), pDelta)) {
                return true;
            }
            return super.mouseScrolled(relativeMouseX, relativeMouseY, pDelta);
        }
    }



}
