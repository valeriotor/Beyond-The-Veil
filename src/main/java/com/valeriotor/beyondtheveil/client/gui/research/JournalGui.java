package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.DropdownLists;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableList;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalCategory;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalReportLine;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class JournalGui extends Screen implements ClientAdvancements.Listener {
    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private static final int BACKGROUND_BASE_WIDTH = 843;
    private static final int BACKGROUND_BASE_HEIGHT = 505;
    private static final int ENTRY_LIST_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 + 43;
    private static final int ENTRY_LIST_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 100;
    private static final int REPORT_BASE_LEFT_X = 30;
    private static final int REPORT_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 100;
    private static final int BOOKMARK_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 - 21;
    private static final int BOOKMARK_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 90;
    private static final int ENTRY_LIST_BASE_WIDTH = 345;
    private static final int ENTRY_LIST_BASE_HEIGHT = 357;
    private static final int ENTRY_BASE_WIDTH = 340;
    private static final int ENTRY_BASE_HEIGHT = 59;
    private static final int DROPDOWN_BASE_HEIGHT = 21;
    private static final int REPORT_BASE_HEIGHT = 37;
    private static final int BOOKMARK_BASE_WIDTH = 48;
    private static final int BOOKMARK_BASE_HEIGHT = 31;
    private static final int BOOKMARK_SEPARATION = 70;
    //private int entryListLeftX;
    //private int entryListTopY;
    private JournalCategory selectedCategory = JournalCategory.TOOLS;
    private final List<Item> knownIngredients = new ArrayList<>();
    private final List<Fluid> knownFluids = new ArrayList<>();
    private final Map<String, CompoundTag> reports = new HashMap<>();
    private DropdownLists overview;
    // 1022x1071
    private ScrollableList<ItemEntry> tools;
    // 1022x1071
    private ScrollableList<JournalReportLine> ingredients;
    private ScrollableList<ReportEntry> reportEntries;
    private ScrollableList<JournalReportLine> report;
    private CompoundTag chosenReportTag;
    private boolean editingReport;
    private EditBox reportName;
    private Button newButton;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private Button cancelButton;
    private List<JournalBookmark> bookmarks = new ArrayList<>();
    // 1022x177 -> 340x59
    private final ResourceLocation ITEM_ENTRY = new ResourceLocation(References.MODID, "textures/gui/journal/item_entry.png");
    private final ResourceLocation REPORT_ENTRY = new ResourceLocation(References.MODID, "textures/gui/journal/report_entry.png");
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
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection != null) {
            connection.getAdvancements().setListener(this);
        }

        Set<String> allBooleans = Minecraft.getInstance().player.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().getAllBooleans();
        List<Fluid> toSort = new ArrayList<>();
        for (String b : allBooleans) {
            if (b.startsWith("fluid_")) {
                String pPath = b.substring(6) + "_source";
                Optional<Holder<Fluid>> holder = ForgeRegistries.FLUIDS.getHolder(new ResourceLocation(References.MODID, pPath));
                holder.ifPresent(fluidHolder -> toSort.add(fluidHolder.get()));
            }
        }
        toSort.sort(Comparator.comparing(f -> new FluidStack(f, 1000).getDisplayName().getString()));
        knownFluids.add(Registration.SOURCE_FLUID_SEDATIVE.get());
        knownFluids.add(Registration.SOURCE_FLUID_COAGULANT.get());
        knownFluids.add(Registration.SOURCE_FLUID_SOFTENER.get());
        knownFluids.addAll(toSort);

        updateReports();
    }

    private void updateReports() {
        reports.clear();
        Minecraft.getInstance().player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> {
            Set<String> allTagKeys = data.getAllTagKeys();
            for (String key : allTagKeys) {
                if (key.startsWith("journal_report_")) {
                    CompoundTag tag = data.getTag(key);
                    String name = tag.getString("name");
                    reports.put(name, tag);
                }
            }
        });
        List<ReportEntry> reportEntryList = reports.values().stream().map(ReportEntry::new).sorted(Comparator.comparing(r -> r.name)).toList();
        reportEntries = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, reportEntryList, REPORT_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);

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
        //List<ItemEntry> entries = knownIngredients.stream().map((Item item) -> new ItemEntry(new ItemStack(item))).toList();
        List<ItemEntry> entries = getTools().stream().map((Item item) -> new ItemEntry(new ItemStack(item))).toList();
        tools = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, entries, ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
        //List<JournalReportLine> reportLines = List.of(JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids));
        //ingredients = new EditableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, reportLines, DROPDOWN_BASE_HEIGHT + 4, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH, () -> JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids));
        //ingredients.setVariableSize(true);

        bookmarks.clear();
        for (JournalCategory category : JournalCategory.values()) {
            if (category.isUnlocked(minecraft.player)) {
                bookmarks.add(new JournalBookmark(category));
            }
        }

        if (reportName != null) {
            removeWidget(reportName);
        }
        if (newButton != null) {
            removeWidget(newButton);
        }
        if (editButton != null) {
            removeWidget(editButton);
        }
        if (saveButton != null) {
            removeWidget(saveButton);
        }
        if (deleteButton != null) {
            removeWidget(deleteButton);
        }
        if (cancelButton != null) {
            removeWidget(cancelButton);
        }
        reportName = addRenderableWidget(new EditBox(minecraft.font, 150, 150, 120, 20, Component.literal("")));
        newButton = addRenderableWidget(Button.builder(Component.translatable("gui.journal.journal.new"), pButton -> {
            editingReport = true;
            chosenReportTag = null;
            selectReportFromNBT(null, true);
            updateWidgetVisibility();
        }).bounds(ENTRY_LIST_BASE_LEFT_X, ENTRY_LIST_BASE_TOP_Y - 25, ENTRY_BASE_WIDTH, 20).build());
        editButton = addRenderableWidget(Button.builder(Component.translatable("gui.journal.journal.edit"), pButton -> {
            editingReport = true;
            selectReportFromNBT(chosenReportTag, true);
            updateWidgetVisibility();
        }).bounds(70, 150, 50, 20).build());
        saveButton = addRenderableWidget(Button.builder(Component.translatable("gui.journal.journal.save"), pButton -> {
            editingReport = false;
            CompoundTag report = saveReportToNBT();
            chosenReportTag = report;
            selectReportFromNBT(chosenReportTag, false);
            GenericToServerPacket packet = GenericToServerPacket.syncJournalReport(chosenReportTag, false, false);
            Messages.sendToServer(packet);
            updateWidgetVisibility();
            DataUtil.setTag(Minecraft.getInstance().player, PlayerDataLib.JOURNAL_REPORT.apply(report.getString("name")), report);
            updateReports();
        }).bounds(70, 150, 50, 20).build());
        deleteButton = addRenderableWidget(Button.builder(Component.translatable("gui.journal.journal.delete"), pButton -> {
            if (chosenReportTag != null) {
                GenericToServerPacket packet = GenericToServerPacket.syncJournalReport(chosenReportTag, false, true);
                Messages.sendToServer(packet);
                DataUtil.removeTag(Minecraft.getInstance().player, PlayerDataLib.JOURNAL_REPORT.apply(chosenReportTag.getString("name")));
            }
            chosenReportTag = null;
            report = null;
            updateReports();
            updateWidgetVisibility();
        }).bounds(70, 175, 50, 20).build());
        cancelButton = addRenderableWidget(Button.builder(Component.translatable("gui.journal.journal.cancel"), pButton -> {
            editingReport = false;
            if (chosenReportTag != null) {
                selectReportFromNBT(chosenReportTag, false);
            } else {
                report = null;
            }
            updateWidgetVisibility();
        }).bounds(70, 175, 50, 20).build());
        updateWidgetVisibility();

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

        pGuiGraphics.fill(0, 0, width, height, 0xCC111111);

        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(BACKGROUND, -imageWidth / 2, -imageHeight / 2, imageWidth, imageHeight, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT);
        super.render(pGuiGraphics, (int) scaledMouseX(pMouseX), (int) scaledMouseY(pMouseY), pPartialTick);
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
            toRender.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY, pPartialTick);
            pose.popPose();
        }
        if (report != null && selectedCategory == JournalCategory.JOURNAL) {
            relativeMouseX = reportMouseX(pMouseX);
            relativeMouseY = reportMouseY(pMouseY);
            pose.pushPose();
            pose.translate(REPORT_BASE_LEFT_X, REPORT_BASE_TOP_Y, 0);
            pGuiGraphics.fill(-4, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x11111111);
            pGuiGraphics.fill(-0, -3, ENTRY_LIST_BASE_WIDTH - 2, 0, 0x44111111);
            pGuiGraphics.fill(-0, ENTRY_LIST_BASE_HEIGHT - 2, ENTRY_LIST_BASE_WIDTH - 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(-4, -3, 0, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            pGuiGraphics.fill(ENTRY_LIST_BASE_WIDTH - 2, -3, ENTRY_LIST_BASE_WIDTH + 2, ENTRY_LIST_BASE_HEIGHT + 2, 0x44111111);
            report.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY, pPartialTick);
            pose.popPose();
        }

        for (int i = 0; i < bookmarks.size(); i++) {
            JournalBookmark bookmark = bookmarks.get(i);
            relativeMouseX = bookmarkMouseX(pMouseX);
            relativeMouseY = bookmarkMouseY(pMouseY, i);
            pose.pushPose();
            pose.translate(BOOKMARK_BASE_LEFT_X, BOOKMARK_BASE_TOP_Y + 70 * i, 0);
            bookmark.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY, pPartialTick);
            pose.popPose();
        }

        pose.popPose();
        //pGuiGraphics.drawString(minecraft.font, String.format("X: %d, Y: %d", pMouseX, pMouseY), 0, 0, 0xFFFFFFFF);
        //pGuiGraphics.drawString(minecraft.font, String.format("X: %d, Y: %d", relativeMouseX, relativeMouseY), 0, 15, 0xFFFFFFFF);
    }

    private double scaledMouseX(double mouseX) {
        return (mouseX - width / 2D) * scaleFactor;
    }

    private double scaledMouseY(double mouseY) {
        return (mouseY - height / 2D) * scaleFactor;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseClicked(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        if (report != null && report.mouseClicked(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton)) {
            return true;
        }
        for (int i = 0; i < bookmarks.size(); i++) {
            JournalBookmark bookmark = bookmarks.get(i);
            if (bookmark.mouseClicked(bookmarkMouseX(pMouseX), bookmarkMouseY(pMouseY, i), pButton)) {
                return true;
            }
        }
        return super.mouseClicked(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (currentList() != null && currentList().mouseDragged(listMouseX(pMouseX), listMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        if (report != null && report.mouseDragged(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        return super.mouseDragged(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseReleased(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        if (report != null && report.mouseReleased(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseReleased(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (currentList() != null && currentList().mouseScrolled(listMouseX(pMouseX), listMouseY(pMouseY), pDelta)) {
            return true;
        }
        if (report != null && report.mouseScrolled(reportMouseX(pMouseX), reportMouseY(pMouseY), pDelta)) {
            return true;
        }
        return super.mouseScrolled(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pDelta);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (currentList() != null && currentList().keyPressed(pKeyCode, pScanCode, pModifiers)) { // TODO not current list but only journal one
            return true;
        }
        if (report != null && report.keyPressed(pKeyCode, pScanCode, pModifiers)) { // TODO not current list but only journal one
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (currentList() != null && currentList().charTyped(pCodePoint, pModifiers)) { // TODO not current list but only journal one
            return true;
        }
        if (report != null && report.charTyped(pCodePoint, pModifiers)) { // TODO not current list but only journal one
            return true;
        }
        return super.charTyped(pCodePoint, pModifiers);
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

    private int reportMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - REPORT_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int reportMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - REPORT_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    @Nullable
    private ScrollableList<? extends Element> currentList() {
        return switch (selectedCategory) {
            case OVERVIEW -> overview;
            case TOOLS -> tools;
            case INGREDIENTS -> ingredients;
            case JOURNAL -> reportEntries;
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

    private CompoundTag saveReportToNBT() {
        CompoundTag report = new CompoundTag();
        CompoundTag saved = new CompoundTag();
        for (int i = 0; i < this.report.rows().size(); i++) {
            saved.put(String.valueOf(i), this.report.rows().get(i).saveToNBT());
        }
        report.put("saved", saved);
        report.putString("name", reportName.getValue());
        report.putInt("success", 0);
        // TODO name and successfulness
        return report;
    }

    private void selectReportFromNBT(CompoundTag report, boolean editable) {
        List<JournalReportLine> lines = new ArrayList<>();
        if (report != null) {
            CompoundTag saved = report.getCompound("saved");
            saved.getAllKeys().stream().map(Integer::parseInt).sorted().forEach(key -> {
                JournalReportLine line = JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, editable);
                line.loadFromNBT(saved.getCompound(String.valueOf(key)));
                lines.add(line);
            });
            reportName.setValue(report.getString("name"));
        } else {
            JournalReportLine line = JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, editable);
            lines.add(line);
        }
        chosenReportTag = report;
        this.report = new EditableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, lines, DROPDOWN_BASE_HEIGHT + 4, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH, () -> JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, editable));
        this.report.setVariableSize(true);
        updateWidgetVisibility();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (currentList() != null) {
            currentList().tick();
        }
    }
    @Override public void onAddAdvancementRoot(Advancement pAdvancement) {}
    @Override public void onRemoveAdvancementRoot(Advancement pAdvancement) {}
    @Override public void onAddAdvancementTask(Advancement pAdvancement) {}
    @Override public void onRemoveAdvancementTask(Advancement pAdvancement) {}
    @Override public void onAdvancementsCleared() {}
    @Override public void onSelectedTabChanged(@Nullable Advancement pAdvancement) {}
    @Override
    public void onUpdateAdvancementProgress(Advancement pAdvancement, AdvancementProgress pProgress) {
        String s = pAdvancement.getId().toString();
        if (s.startsWith(References.MODID + ":ingredients/")) {
            ForgeRegistries.ITEMS.getHolder(new ResourceLocation(References.MODID, s.substring(s.indexOf('/') + 1))).ifPresent(h -> knownIngredients.add(h.get()));
        }
    }

    private class ItemEntry extends Element {

        private final ItemStack stack;

        protected ItemEntry(ItemStack stack) {
            super(ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            this.stack = stack;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
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

    private class ReportEntry extends Element {


        private final String name;
        private final CompoundTag saved;
        private final int success;
        private final CompoundTag report;

        protected ReportEntry(CompoundTag report) {
            super(ENTRY_BASE_WIDTH, REPORT_BASE_HEIGHT);
            this.name = report.getString("name");
            this.saved = report.getCompound("saved");
            this.success = report.getInt("success");
            this.report = report;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(REPORT_ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }
            if (this.report == chosenReportTag) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x33A88C00);
            }
            graphics.drawString(minecraft.font, name, 5, 8, color);
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                if (!editingReport) {
                    selectReportFromNBT(report, false);
                }
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
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
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
                setCategory(category);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1));
                return true;
            }
            return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }
    }

    private void setCategory(JournalCategory category) {
        selectedCategory = category;
        updateWidgetVisibility();
    }

    private void updateWidgetVisibility() {
        reportName.active = reportName.visible = (selectedCategory == JournalCategory.JOURNAL && report != null);
        newButton.visible = editButton.active = (selectedCategory == JournalCategory.JOURNAL);
        editButton.visible = editButton.active = (selectedCategory == JournalCategory.JOURNAL && !editingReport && chosenReportTag != null);
        saveButton.visible = saveButton.active = (selectedCategory == JournalCategory.JOURNAL && editingReport);
        deleteButton.visible = deleteButton.active = (selectedCategory == JournalCategory.JOURNAL && !editingReport && chosenReportTag != null);
        cancelButton.visible = cancelButton.active = (selectedCategory == JournalCategory.JOURNAL && editingReport);
    }

    private class Dropdown1 extends DropdownLists.Dropdown {

        private final String id;

        protected Dropdown1(String id) {
            super(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT);
            this.id = id;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
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
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
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
