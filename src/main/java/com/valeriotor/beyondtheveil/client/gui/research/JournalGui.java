package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.gui.elements.DropdownLists;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableList;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalCategory;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalReportLine;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JournalGui extends Screen {
    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private static final int BACKGROUND_BASE_WIDTH = 843;
    private static final int BACKGROUND_BASE_HEIGHT = 505;
    private static final int ENTRY_LIST_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 + 43;
    private static final int ENTRY_LIST_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 100;
    private static final int BOOKMARK_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 - 21;
    private static final int BOOKMARK_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 90;
    private static final int ENTRY_LIST_BASE_WIDTH = 345;
    private static final int ENTRY_LIST_BASE_HEIGHT = 357;
    private static final int ENTRY_BASE_WIDTH = 340;
    private static final int ENTRY_BASE_HEIGHT = 59;
    private static final int DROPDOWN_BASE_HEIGHT = 21;
    private static final int BOOKMARK_BASE_WIDTH = 48;
    private static final int BOOKMARK_BASE_HEIGHT = 31;
    private static final int BOOKMARK_SEPARATION = 70;
    //private int entryListLeftX;
    //private int entryListTopY;
    private JournalCategory selectedCategory = JournalCategory.TOOLS;
    private DropdownLists overview;
    // 1022x1071
    private ScrollableList<ItemEntry> tools;
    // 1022x1071
    private ScrollableList<JournalReportLine> ingredients;
    private List<JournalBookmark> bookmarks = new ArrayList<>();
    // 1022x177 -> 340x59
    private final ResourceLocation ITEM_ENTRY = new ResourceLocation(References.MODID, "textures/gui/journal/item_entry.png");
    // 2530x1517 -> 843x505
    private final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/journal/background.png");
    // 144x93 -> 48x31
    private final ResourceLocation BOOKMARK = new ResourceLocation(References.MODID, "textures/gui/journal/bookmark.png");
    private final ResourceLocation BOOKMARK_DESELECTED = new ResourceLocation(References.MODID, "textures/gui/journal/bookmark_deselected.png");
    private final ResourceLocation BOOKMARK_SELECTED = new ResourceLocation(References.MODID, "textures/gui/journal/bookmark_selected.png");
    private final ResourceLocation HOUSE_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/house_icon.png");
    private final ResourceLocation TOOLS_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/tools_icon.png");
    private final ResourceLocation INGREDIENTS_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/ingredients_icon.png");
    private final ResourceLocation JOURNAL_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/journal_icon.png");
    private final ResourceLocation ABOMINATION_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/abomination_icon.png");
    private final ResourceLocation DROPDOWN_1 = new ResourceLocation(References.MODID, "textures/gui/journal/dropdown_1.png");
    private final ResourceLocation DROPDOWN_2 = new ResourceLocation(References.MODID, "textures/gui/journal/dropdown_2.png");
    private final ResourceLocation PLUS = new ResourceLocation(References.MODID, "textures/gui/plus.png");
    private final ResourceLocation MINUS = new ResourceLocation(References.MODID, "textures/gui/journal/minus.png");

    //private final ScrollableList overview;
    public JournalGui() {
        super(Component.translatable("gui.journal.title"));

    }


    @Override
    protected void init() {
        imageWidth = BACKGROUND_BASE_WIDTH;
        imageHeight = BACKGROUND_BASE_HEIGHT;
        //entryListWidth = 343;
        //entryListHeight = 357;

        if (imageHeight > height * 99 / 100) {
            scaleFactor = height * 99F / 100 / imageHeight;
        }
        if (imageWidth > width * 95 / 100) {
            scaleFactor = Math.min(width * 95F / 100 / imageWidth, scaleFactor);
        }
        //entryListLeftX = pageX() + 42;
        //entryListTopY = pageY() + 80;

        overview = DropdownLists.makeList(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, makeOverviewList(), DROPDOWN_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
        List<ItemEntry> entries = getTools().stream().map((Item item) -> new ItemEntry(new ItemStack(item))).toList();
        tools = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, entries, ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
//        List<JournalReportLine> reportLines = List.of(JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4));
//        ingredients = new EditableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, reportLines, DROPDOWN_BASE_HEIGHT + 4, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH, () -> JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4));

        bookmarks.clear();
        for (JournalCategory category : JournalCategory.values()) {
            if (category.isUnlocked(minecraft.player)) {
                bookmarks.add(new JournalBookmark(category));
            }
        }


        /*if (width > height * 16 / 9) {
            imageWidth = height * 16 / 9;
        } else if (height > width * 9 / 16) {
            imageHeight = width * 9 / 16;
        }

        final double MIN_STRING_PROPORTION = 30 / 1440D;
        final double MAX_STRING_PROPORTION = 60 / 1440D;
        if (MIN_STRING_PROPORTION > 15D / imageHeight) {
            scaleFactor = (float) ((MIN_STRING_PROPORTION) / (15D / imageHeight));
        } else if (MAX_STRING_PROPORTION < 15D / imageHeight) {
            scaleFactor = (float) ((MAX_STRING_PROPORTION) / (15D / imageHeight));
        }*/
        //displayedLines = (int) (imageHeight / 3 / 15 / scaleFactor);
        //int listWidth = (int) computeListWidth();
        //updateList();
    }

    private List<Element> makeOverviewList() {
        List<Element> list = new ArrayList<>();
        list.add(new Dropdown1("fundamentals"));
        list.add(new Dropdown2("patients"));
        list.add(new Dropdown2("starting"));
        list.add(new Dropdown2("managing"));
        list.add(new Dropdown2("concluding"));
        list.add(new Dropdown2("journal"));
        list.add(new Dropdown1("ingredients"));
        list.add(new Dropdown2("fluids"));
        list.add(new Dropdown2("solids"));
        list.add(new Dropdown1("types"));
        list.add(new Dropdown2("extraction"));
        list.add(new Dropdown2("incision"));
        list.add(new Dropdown2("injection"));
        list.add(new Dropdown2("insertion"));
        list.add(new Dropdown2("stitching"));
        list.add(new Dropdown1("infrastructure"));
        list.add(new Dropdown2("distillation"));
        list.add(new Dropdown2("storage"));
        list.add(new Dropdown2("holding"));
        list.add(new Dropdown2("surgery"));
        return list;
    }
    private List<Item> getTools() {
        return List.of(Registration.FORCEPS.get(), Registration.SCALPEL.get(), Registration.SEWING_NEEDLE.get(), Registration.SYRINGE.get(), Registration.TONGS.get(), Registration.FLASK_LARGE_ITEM.get(), Registration.FLASK_MEDIUM_ITEM.get(), Registration.FLASK_SMALL_ITEM.get(), Registration.FLASK_SHELF_ITEM.get(), Registration.SURGERY_BED_ITEM.get(), Registration.WATERY_CRADLE_ITEM.get());
    }

    private void updateLists() {

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.fill(0, 0, width, height, 0xCC111111);

        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(BACKGROUND, -imageWidth / 2, -imageHeight / 2, imageWidth, imageHeight, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT);
        ScrollableList<? extends Element> toRender = currentList();
        int relativeMouseX = listMouseX(pMouseX);
        int relativeMouseY = listMouseY(pMouseY);
        if (toRender != null) {
            pose.pushPose();
            pose.translate(ENTRY_LIST_BASE_LEFT_X, ENTRY_LIST_BASE_TOP_Y, 0);
            pGuiGraphics.fill(-4, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x11111111);
            pGuiGraphics.fill(-0, -3, ENTRY_LIST_BASE_WIDTH - 2, 0, 0x44111111);
            pGuiGraphics.fill(-0, ENTRY_LIST_BASE_HEIGHT - 2, ENTRY_LIST_BASE_WIDTH - 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(-4, -3, 0, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(ENTRY_LIST_BASE_WIDTH - 2, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            toRender.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY);
            pose.popPose();
        }

        for (int i = 0; i < bookmarks.size(); i++) {
            JournalBookmark bookmark = bookmarks.get(i);
            relativeMouseX = bookmarkMouseX(pMouseX);
            relativeMouseY = bookmarkMouseY(pMouseY, i);
            pose.pushPose();
            pose.translate(BOOKMARK_BASE_LEFT_X, BOOKMARK_BASE_TOP_Y + 70 * i, 0);
            bookmark.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY);
            pose.popPose();
        }

        pose.popPose();
        //pGuiGraphics.drawString(minecraft.font, String.format("X: %d, Y: %d", pMouseX, pMouseY), 0, 0, 0xFFFFFFFF);
        //pGuiGraphics.drawString(minecraft.font, String.format("X: %d, Y: %d", relativeMouseX, relativeMouseY), 0, 15, 0xFFFFFFFF);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseClicked(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        for (int i = 0; i < bookmarks.size(); i++) {
            JournalBookmark bookmark = bookmarks.get(i);
            if (bookmark.mouseClicked(bookmarkMouseX(pMouseX), bookmarkMouseY(pMouseY, i), pButton)) {
                return true;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (currentList() != null && currentList().mouseDragged(listMouseX(pMouseX), listMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseReleased(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (currentList() != null && currentList().mouseScrolled(listMouseX(pMouseX), listMouseY(pMouseY), pDelta)) {
            return true;
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private int bookmarkMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - BOOKMARK_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int bookmarkMouseY(double pMouseY, int i) {
        return (int) ((pMouseY - height / 2 - (BOOKMARK_BASE_TOP_Y + 70 * i) * scaleFactor) / scaleFactor);
    }

    private int listMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - ENTRY_LIST_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int listMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - ENTRY_LIST_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    @Nullable
    private ScrollableList<? extends Element> currentList() {
        return switch (selectedCategory) {
            case OVERVIEW -> overview;
            case TOOLS -> tools;
            case INGREDIENTS -> ingredients;
            case JOURNAL -> null;
            case ABOMINATIONS -> null;
        };
    }


    private int pageX() {
        return (int) ((width / 2) - (imageWidth / 2) * scaleFactor);
    }

    private int pageY() {
        return (int) ((height / 2) - (imageHeight / 2) * scaleFactor);
    }


    private int listWidth() {
        return (int) (ENTRY_LIST_BASE_WIDTH);
    }

    private int listHeight() {
        return (int) (ENTRY_LIST_BASE_HEIGHT);
    }

    /*private int entryWidth() {
        return (int) (ENTRY_BASE_WIDTH);
    }

    private int entryHeight() {
        return (int) (ENTRY_BASE_HEIGHT);
    }

    private int scrollbarWidth() {
        return listWidth() - entryWidth();
    }*/

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private class ItemEntry extends Element {

        private final ItemStack stack;
        private final float scaleFactor;

        protected ItemEntry(ItemStack stack) {
            super(ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            this.stack = stack;
            scaleFactor = Math.min(1, getHeight() / 40F);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
            graphics.blit(ITEM_ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }
            poseStack.pushPose();
            poseStack.translate(28, 28, 0);
            poseStack.scale(2.2F, 2.2F, 1);
            //poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.renderItem(stack, -8, -8);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(56 + 140, getHeight() / 5F - 4, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            //poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.drawCenteredString(minecraft.font, Component.translatable(stack.getDescriptionId()), 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                // TODO selectEntry(stack.getItem(), recipe);
                // TODO selectedEntry = this;
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1));
                return true;
            }
            return false;
        }
    }

    private class JournalBookmark extends Element {

        private final JournalCategory category;

        protected JournalBookmark(JournalCategory category) {
            super(BOOKMARK_BASE_WIDTH, BOOKMARK_BASE_HEIGHT);
            this.category = category;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.renderTooltip(minecraft.font, Component.translatable("gui.journal.bookmark." + category.name().toLowerCase()), relativeMouseX, relativeMouseY);
                //graphics.renderTooltip(minecraft.font, Component.translatable("Overview"), relativeMouseX, relativeMouseY);
            }
            if (selectedCategory == category) {
                graphics.blit(BOOKMARK_SELECTED, 0, 0, getWidth(), getHeight(), 0, 0, BOOKMARK_BASE_WIDTH, BOOKMARK_BASE_HEIGHT, BOOKMARK_BASE_WIDTH, BOOKMARK_BASE_HEIGHT);
            } else if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.blit(BOOKMARK, 0, 0, getWidth(), getHeight(), 0, 0, BOOKMARK_BASE_WIDTH, BOOKMARK_BASE_HEIGHT, BOOKMARK_BASE_WIDTH, BOOKMARK_BASE_HEIGHT);
            } else {
                graphics.blit(BOOKMARK_DESELECTED, 0, 0, getWidth(), getHeight(), 0, 0, BOOKMARK_BASE_WIDTH, BOOKMARK_BASE_HEIGHT, BOOKMARK_BASE_WIDTH, BOOKMARK_BASE_HEIGHT);
            }
            ResourceLocation icon = switch (category) {
                case OVERVIEW -> HOUSE_ICON;
                case TOOLS -> TOOLS_ICON;
                case INGREDIENTS -> INGREDIENTS_ICON;
                case JOURNAL -> JOURNAL_ICON;
                case ABOMINATIONS -> ABOMINATION_ICON;
            };
            RenderSystem.enableBlend();
            graphics.blit(icon, 18, 7, 20, 20, 0, 0, 20, 20, 20, 20);

        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                selectedCategory = category;
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1));
                return true;
            }
            return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }
    }

    private class Dropdown1 extends DropdownLists.Dropdown {

        private final String id;

        protected Dropdown1(String id) {
            super(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT);
            this.id = id;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
            graphics.blit(DROPDOWN_1, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT, ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x4499875A);
            }
            ResourceLocation icon = isOpen() ? MINUS : PLUS;
            graphics.blit(icon, 4, 4, 14, 14, 0, 0, 14, 14, 14, 14);

            graphics.drawString(minecraft.font, Component.translatable(String.format("gui.journal.overview.%s", id)), 24, 7, 0xFFFFFFFF);
        }
    }

    private class Dropdown2 extends Element {

        private final String id;

        protected Dropdown2(String id) {
            super(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT);
            this.id = id;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
            graphics.blit(DROPDOWN_2, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT, ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x4499875A);
            }
            graphics.drawString(minecraft.font, Component.translatable(String.format("gui.journal.overview.%s", id)), 36, 7, 0xFFFFFFFF);
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                // TODO
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1));
                return true;
            }
            return false;
        }
    }



}
