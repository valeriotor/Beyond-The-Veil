package com.valeriotor.beyondtheveil.client.gui.pool;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.lib.References;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.valeriotor.beyondtheveil.client.gui.research.ResearchPageGui.LEFT_ARROW;
import static com.valeriotor.beyondtheveil.client.gui.research.ResearchPageGui.RIGHT_ARROW;

public class BloodPoolGui extends Screen {

    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private ScrollableList<RowEntry> rows;
    private static final int BACKGROUND_BASE_WIDTH = 1034;
    private static final int BACKGROUND_BASE_HEIGHT = 634;
    private static final int ENTRY_LIST_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 + 55;
    private static final int ENTRY_LIST_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 60;
    private static final int ENTRY_LIST_BASE_WIDTH = 628;
    private static final int ENTRY_LIST_BASE_HEIGHT = 520;
    private static final int ENTRY_BASE_WIDTH = 615;
    private static final int ENTRY_BASE_HEIGHT = 104;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/blood_pool/background.png");
    private static final ResourceLocation ROW = new ResourceLocation(References.MODID, "textures/gui/blood_pool/row.png");
    private static final Map<BloodPoolEntityType, ResourceLocation> ICONS = new HashMap<>();

    static {
        for (BloodPoolEntityType value : BloodPoolEntityType.values()) {
            ICONS.put(value, new ResourceLocation(References.MODID, "textures/gui/blood_pool/" + value.name().toLowerCase() + ".png"));
        }
    }

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

        List<RowEntry> entryList = new ArrayList<>();
        Map<ColorTriplet, List<BloodPoolEntity>> entitiesByTriplet = ClientData.getInstance().getBloodPoolData().getEntitiesByTriplet(Minecraft.getInstance().player.getUUID());
        for (Map.Entry<ColorTriplet, List<BloodPoolEntity>> entry : entitiesByTriplet.entrySet()) {
            entryList.add(new RowEntry(entry.getKey(), new ArrayList<>(entry.getValue())));
        }

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
        pose.popPose();
    }

    private int listMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - ENTRY_LIST_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int listMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - ENTRY_LIST_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (rows.mouseClicked(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (rows.mouseScrolled(listMouseX(pMouseX), listMouseY(pMouseY), pDelta)) {
            return true;
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private static class RowEntry extends Element {

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
            if (hoveringLeftArrow(relativeMouseX, relativeMouseY)) {
                offset = Math.max(0, offset - 1);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                return true;
            } else if (hoveringRightArrow(relativeMouseX, relativeMouseY)) {
                offset = Math.min(entities.size() - 1, offset + 1);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                return true;
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


}
