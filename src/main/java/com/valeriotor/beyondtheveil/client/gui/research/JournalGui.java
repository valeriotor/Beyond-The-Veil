package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.research.ResearchProvider;
import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.client.gui.elements.*;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalCategory;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalReportLine;
import com.valeriotor.beyondtheveil.client.util.DataUtilClient;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.recipes.AlembicsRecipeRegistry;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.surgery.notes.Report;
import com.valeriotor.beyondtheveil.surgery.notes.ReportPatientType;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class JournalGui extends Screen implements ClientAdvancements.Listener {
    private boolean firstOpen;
    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private final Style style = Style.EMPTY.withFont(new ResourceLocation("minecraft", "uniform"));
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
    public static final int ARROW_WIDTH = 16;
    public static final int ARROW_HEIGHT = 16;
    public static final int SUCCESSFUL_BOX_X = 35;
    public static final int SUCCESSFUL_BOX_Y = ENTRY_LIST_BASE_TOP_Y - 33;
    //private int entryListLeftX;
    //private int entryListTopY;
    private JournalCategory selectedCategory = JournalCategory.OVERVIEW;
    private final List<Item> knownIngredients = new ArrayList<>();
    private final List<Fluid> knownFluids = new ArrayList<>();
    private final Map<String, CompoundTag> reports = new HashMap<>();
    private DropdownLists overview;
    // 1022x1071
    private ScrollableList<ItemEntry> tools;
    // 1022x1071
    private ScrollableList<ItemEntry> ingredients;
    private ScrollableList<ReportEntry> reportEntries;
    private Page overviewPage;
    private Page toolPage;
    private CraftingRegistryGui.CraftingGrid toolGrid;
    private Page ingredientPage;
    private ScrollableList<JournalReportLine> reportLineList;
    private Report chosenReport;
    private boolean editingReport;
    private ElementHolder buttonHolder;
    private EditBox reportName;
    private TexturedButton newButton;
    private TexturedButton saveButton;
    private TexturedButton editButton;
    private TexturedButton deleteButton;
    private TexturedButton cancelButton;
    private TexturedButton printButton;
    private EditableDropdownBox<Successfulness> successfulnessBox;
    private EditableDropdownBox<PatientType> patientBox;
    private final List<JournalBookmark> bookmarks = new ArrayList<>();
    // 1022x177 -> 340x59
    private static final ResourceLocation ITEM_ENTRY = new ResourceLocation(References.MODID, "textures/gui/journal/item_entry.png");
    private static final ResourceLocation REPORT_ENTRY = new ResourceLocation(References.MODID, "textures/gui/journal/report_entry.png");
    private static final ResourceLocation TICK = new ResourceLocation(References.MODID, "textures/gui/journal/tick.png");
    private static final ResourceLocation TILDE = new ResourceLocation(References.MODID, "textures/gui/journal/tilde.png");
    private static final ResourceLocation CROSS = new ResourceLocation(References.MODID, "textures/gui/journal/cross.png");
    private static final ResourceLocation QUESTION_MARK = new ResourceLocation(References.MODID, "textures/gui/journal/question_mark.png");
    // 2530x1517 -> 843x505
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/journal/background.png");
    // 144x93 -> 48x31
    private static final ResourceLocation BOOKMARK = new ResourceLocation(References.MODID, "textures/gui/journal/bookmark.png");
    private static final ResourceLocation BOOKMARK_DESELECTED = new ResourceLocation(References.MODID, "textures/gui/journal/bookmark_deselected.png");
    private static final ResourceLocation BOOKMARK_SELECTED = new ResourceLocation(References.MODID, "textures/gui/journal/bookmark_selected.png");
    private static final ResourceLocation HOUSE_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/house_icon.png");
    private static final ResourceLocation TOOLS_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/tools_icon.png");
    private static final ResourceLocation INGREDIENTS_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/ingredients_icon.png");
    private static final ResourceLocation JOURNAL_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/journal_icon.png");
    private static final ResourceLocation ABOMINATION_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/abomination_icon.png");
    private static final ResourceLocation FLUID_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/fluid_icon.png");
    private static final ResourceLocation FLUID_FILLING_ICON = new ResourceLocation(References.MODID, "textures/gui/journal/fluid_filling_icon.png");
    private static final ResourceLocation DROPDOWN_1 = new ResourceLocation(References.MODID, "textures/gui/journal/dropdown_1.png");
    private static final ResourceLocation DROPDOWN_2 = new ResourceLocation(References.MODID, "textures/gui/journal/dropdown_2.png");
    private static final ResourceLocation PLUS = new ResourceLocation(References.MODID, "textures/gui/plus.png");
    private static final ResourceLocation MINUS = new ResourceLocation(References.MODID, "textures/gui/journal/minus.png");
    private static final ResourceLocation BUTTON = new ResourceLocation(References.MODID, "textures/gui/journal/button.png");
    public static final ResourceLocation LEFT_ARROW = new ResourceLocation(References.MODID, "textures/gui/journal/left_arrow.png");
    public static final ResourceLocation RIGHT_ARROW = new ResourceLocation(References.MODID, "textures/gui/journal/right_arrow.png");
    private static final ResourceLocation ALEMBICS_PROFILE = new ResourceLocation(References.MODID, "textures/gui/journal/alembics_profile.png");
    private final int PAGE_X = 50;
    private final int PAGE_Y = -200;
    private Component mainTitle;
    private boolean abominationsUnlocked;

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
        knownFluids.add(BTVFluids.SOURCE_FLUID_SEDATIVE.get());
        knownFluids.add(BTVFluids.SOURCE_FLUID_COAGULANT.get());
        knownFluids.add(BTVFluids.SOURCE_FLUID_SOFTENER.get());
        knownFluids.addAll(toSort);

        firstOpen = true;
        updateReports();

    }

    private void updateReports() {
        int currentFirstRow = reportEntries == null ? 0 : reportEntries.getCurrentFirstRow();
        reports.clear();
        Map<String, Report> allReports = DataUtil.getAllReports(Minecraft.getInstance().player);
        List<ReportEntry> reportEntryList = allReports.values().stream().map(ReportEntry::new).sorted(Comparator.comparing(r -> r.name.toLowerCase())).toList();
        reportEntries = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, reportEntryList, REPORT_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
        reportEntries.setCurrentFirstRow(currentFirstRow);
    }


    @Override
    protected void init() {
        scaleFactor = 1;
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
        List<ItemEntry> toolEntries = getTools();
        tools = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, toolEntries, ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
        ingredients = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, getIngredients(), ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);

        bookmarks.clear();
        for (JournalCategory category : JournalCategory.values()) {
            if (category.isUnlocked(minecraft.player)) {
                bookmarks.add(new JournalBookmark(category));
            }
        }

        if (reportName != null) {
            removeWidget(reportName);
        }

        successfulnessBox = EditableDropdownBox.makeBox(Arrays.stream(Report.Successfulness.values()).map(Successfulness::new).toArray(Successfulness[]::new), 23, 8, 0xFF7D633F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, false, 150, 40);
        patientBox = EditableDropdownBox.makeBox(Arrays.stream(ReportPatientType.values()).map(PatientType::new).toArray(PatientType[]::new), 23, 8, 0xFF7D633F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, false, 150, 40);
        buttonHolder = ElementHolder.makeHolder(width, height);
        reportName = addRenderableWidget(new EditBox(minecraft.font, 150, 150, 120, 20, Component.literal("")));
        newButton = buttonHolder.addElement(ENTRY_LIST_BASE_LEFT_X, ENTRY_LIST_BASE_TOP_Y - 25, new TexturedButton(ENTRY_BASE_WIDTH, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.journal.journal.new"), pButton -> {
            setEditingReport(true);
            chosenReport = null;
            reportName.setValue("");
            selectReport(null, true);
            //updateWidgetVisibility();
        }));
        editButton = buttonHolder.addElement(70, 150, new TexturedButton(50, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.journal.journal.edit"), pButton -> {
            setEditingReport(true);
            selectReport(chosenReport, true);
            //updateWidgetVisibility();
        }));
        saveButton = buttonHolder.addElement(70, 150, new TexturedButton(50, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.journal.journal.save"), pButton -> {
            setEditingReport(false);
            if (chosenReport != null) {
                GenericToServerPacket packet = GenericToServerPacket.deleteJournalReport(chosenReport.getName());
                Messages.sendToServer(packet);
                DataUtil.deleteReport(Minecraft.getInstance().player, chosenReport.getName());
            }
            String currentName = reportName.getValue();
            int i = 1;
            while (DataUtil.getReport(Minecraft.getInstance().player, currentName) != null) {
                if (currentName.matches(".*\\(\\d+\\)")) {
                    boolean flag = false;
                    while (!flag) {
                        char c = currentName.charAt(currentName.length() - 1);
                        currentName = currentName.substring(0, currentName.length() - 1);
                        if (c == '(') {
                            flag = true;
                            if (currentName.endsWith(" ")) {
                                currentName = currentName.substring(0, currentName.length() - 1);
                            }
                        }
                    }
                }
                currentName += " (" + i + ")";
                i++;
            }
            reportName.setValue(currentName);
            chosenReport = reportFromList();
            selectReport(chosenReport, false);
            GenericToServerPacket packet = GenericToServerPacket.addJournalReport(chosenReport.saveToNBT(), false);
            Messages.sendToServer(packet);
            //updateWidgetVisibility();
            DataUtil.addReport(Minecraft.getInstance().player, chosenReport);
            updateReports();
        }));
        deleteButton = buttonHolder.addElement(70, 175, new TexturedButton(50, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.journal.journal.delete"), pButton -> {
            if (chosenReport != null) {
                GenericToServerPacket packet = GenericToServerPacket.deleteJournalReport(chosenReport.getName());
                Messages.sendToServer(packet);
                DataUtil.deleteReport(Minecraft.getInstance().player, chosenReport.getName());
            }
            chosenReport = null;
            reportLineList = null;
            updateReports();
            //updateWidgetVisibility();
        }));
        cancelButton = buttonHolder.addElement(70, 175, new TexturedButton(50, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.journal.journal.cancel"), pButton -> {
            setEditingReport(false);
            if (chosenReport != null) {
                selectReport(chosenReport, false);
            } else {
                reportLineList = null;
            }
            //updateWidgetVisibility();
        }));
        printButton = buttonHolder.addElement(300, 150, new TexturedButton(50, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.journal.journal.print"), pButton -> {
            GenericToServerPacket packet = GenericToServerPacket.printReport(chosenReport.getName());
            Messages.sendToServer(packet);
        }) {
            @Override
            public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
                super.render(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick);
                if (insideBounds(relativeMouseX, relativeMouseY) && visible && !active) {
                    graphics.renderTooltip(Minecraft.getInstance().font, Component.translatable("gui.journal.journal.need_paper"), relativeMouseX, relativeMouseY);
                }
            }
        });
        updateWidgetVisibility();

        if (firstOpen) {
            Triple<Report, Boolean, String> currentReport = DataUtil.getCurrentReport(Minecraft.getInstance().player);
            if (currentReport.getLeft() != null) {
                setEditingReport(currentReport.getMiddle());
                selectReport(currentReport.getLeft(), currentReport.getMiddle());
            }
            if (currentReport.getRight() != null) {
                chosenReport = DataUtil.getReport(Minecraft.getInstance().player, currentReport.getRight());
            } else {
                chosenReport = null;
            }
            Integer orSetInteger = DataUtil.getOrSetInteger(Minecraft.getInstance().player, PlayerDataLib.open_journal_page.name(), 0, true);
            setCategory(JournalCategory.values()[orSetInteger]);
            firstOpen = false;
        } else {
            setCategory(selectedCategory);

        }
    }

    private void setEditingReport(boolean val) {
        editingReport = val;
        reportName.moveCursorToStart();
        reportName.setEditable(val);
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
        if (abominationsUnlocked) {
            list.add(new Dropdown1("abominations"));
            list.add(new Dropdown2("what_is"));
            list.add(new Dropdown2("capacity"));
            list.add(new Dropdown2("modifiers"));
            list.add(new Dropdown2("triggering"));
            list.add(new Dropdown2("priorities"));
            if (ResearchUtil.getResearchStage(Minecraft.getInstance().player, "WEEPERS") >= 0) {
                list.add(new Dropdown2("weepers"));

            }
        }
        return list;
    }

    private List<ItemEntry> getTools() {
        List<ItemEntry> entries = new ArrayList<>();
        entries.add(new StacksItemEntry(Registration.FORCEPS.get(), "forceps", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.SCALPEL.get(), "scalpel", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.SEWING_NEEDLE.get(), "sewing_needle", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.SYRINGE.get(), "syringe", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.TONGS.get(), "tongs", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.SAMPLE_TUBE.get(), "sample_tube", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(List.of(Registration.FLASK_LARGE_ITEM.get(), Registration.FLASK_MEDIUM_ITEM.get(), Registration.FLASK_SMALL_ITEM.get()), "flasks", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.FLASK_ITEM_ITEM.get(), "item_flask", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.ALEMBICS_ITEM.get(), "alembics", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.FLASK_SHELF_ITEM.get(), "flask_shelf", JournalCategory.TOOLS));
        entries.add(new StacksItemEntry(Registration.SURGERY_BED_ITEM.get(), "surgery_bed", JournalCategory.TOOLS));
        Minecraft.getInstance().player.getCapability(ResearchProvider.RESEARCH).ifPresent(c -> {
            if (ResearchUtil.getKnownRecipes(Minecraft.getInstance().player).containsKey("beyondtheveil:watery_cradle")) { // TODO check if key contains namespace
                entries.add(new StacksItemEntry(Registration.WATERY_CRADLE_ITEM.get(), "watery_cradle", JournalCategory.TOOLS));
            }
        });
        return entries;
    }

    private List<ItemEntry> getIngredients() {
        List<ItemEntry> entries = new ArrayList<>();
        entries.add(new FluidItemEntry(BTVFluids.SOURCE_FLUID_SEDATIVE.get(), "sedative", JournalCategory.INGREDIENTS));
        entries.add(new FluidItemEntry(BTVFluids.SOURCE_FLUID_COAGULANT.get(), "coagulant", JournalCategory.INGREDIENTS));
        entries.add(new FluidItemEntry(BTVFluids.SOURCE_FLUID_SOFTENER.get(), "softener", JournalCategory.INGREDIENTS));
        if (ResearchUtil.getResearchStage(Minecraft.getInstance().player, "WATERY_CRADLE") >= 1) {
            entries.add(new FluidItemEntry(BTVFluids.FLUID_OBEDIENCE_HORMONES.getA().get(), "obedience_hormones", JournalCategory.INGREDIENTS));
            entries.add(new FluidItemEntry(BTVFluids.FLUID_PARENTAL_HORMONES.getA().get(), "parental_hormones", JournalCategory.INGREDIENTS));
        }
        if (abominationsUnlocked) {
            entries.add(new FluidItemEntry(BTVFluids.FLUID_GS121_SERUM.getA().get(), "gs121_serum", JournalCategory.INGREDIENTS));
            entries.add(new FluidItemEntry(BTVFluids.FLUID_GS121_SERUM.getA().get(), "gs121_serum", JournalCategory.INGREDIENTS));
            List<Fluid> wartSerums = Stream.of(BTVFluids.FLUID_WART_SERUM,
                    BTVFluids.FLUID_MOVEMENT_SPEED_SERUM,
                    BTVFluids.FLUID_MOVEMENT_SLOWDOWN_SERUM,
                    BTVFluids.FLUID_DIG_SPEED_SERUM,
                    BTVFluids.FLUID_DIG_SLOWDOWN_SERUM,
                    BTVFluids.FLUID_DAMAGE_BOOST_SERUM,
                    BTVFluids.FLUID_HEAL_SERUM,
                    BTVFluids.FLUID_HARM_SERUM,
                    BTVFluids.FLUID_JUMP_SERUM,
                    BTVFluids.FLUID_CONFUSION_SERUM,
                    BTVFluids.FLUID_REGENERATION_SERUM,
                    BTVFluids.FLUID_DAMAGE_RESISTANCE_SERUM,
                    BTVFluids.FLUID_FIRE_RESISTANCE_SERUM,
                    BTVFluids.FLUID_WATER_BREATHING_SERUM,
                    BTVFluids.FLUID_INVISIBILITY_SERUM,
                    BTVFluids.FLUID_BLINDNESS_SERUM,
                    BTVFluids.FLUID_NIGHT_VISION_SERUM,
                    BTVFluids.FLUID_HUNGER_SERUM,
                    BTVFluids.FLUID_WEAKNESS_SERUM,
                    BTVFluids.FLUID_POISON_SERUM,
                    BTVFluids.FLUID_WITHER_SERUM).map(f -> (Fluid) f.getA().get()).toList();
            entries.add(new FluidItemEntry(wartSerums, "wart_serums", JournalCategory.INGREDIENTS));
        }
        // TODO 24-01-2026 unlock fluids when taken with syringe or bucket
        // TODO I guess use playerdatalib.discoveredfluid?
        for (Item knownIngredient : knownIngredients) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(knownIngredient);
            if (key != null) {
                entries.add(new StacksItemEntry(knownIngredient, key.getPath(), JournalCategory.INGREDIENTS));
            }
        }
//List<ItemEntry> entries = knownIngredients.stream().map((Item item) -> new ItemEntry(new ItemStack(item))).toList();
        return entries;
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
        if (mainTitle instanceof MutableComponent mc) {
            pose.pushPose();
            pose.translate(-210, -205, 0);
            pose.scale(3.05F, 3.05F, 1);
            pGuiGraphics.drawCenteredString(Minecraft.getInstance().font, mc.withStyle(style), 0, 0, 0xFFF0B929);
            pose.popPose();
        }
        buttonHolder.render(pose, pGuiGraphics, 0xFFFFFFFF, (int) scaledMouseX(pMouseX), (int) scaledMouseY(pMouseY), pPartialTick);

        if(successfulnessBox.visible) {
            pose.pushPose();
            pGuiGraphics.fill(REPORT_BASE_LEFT_X, SUCCESSFUL_BOX_Y-4, REPORT_BASE_LEFT_X+ENTRY_LIST_BASE_WIDTH - 2, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, 0x21111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X-4, SUCCESSFUL_BOX_Y-4, REPORT_BASE_LEFT_X + 0, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, 0x51111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X+ENTRY_LIST_BASE_WIDTH - 2, SUCCESSFUL_BOX_Y-4, REPORT_BASE_LEFT_X+ENTRY_LIST_BASE_WIDTH + 2, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, 0x51111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X-4, SUCCESSFUL_BOX_Y-8, REPORT_BASE_LEFT_X+ENTRY_LIST_BASE_WIDTH + 2, SUCCESSFUL_BOX_Y -4 , 0x51111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X-4, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, REPORT_BASE_LEFT_X+ENTRY_LIST_BASE_WIDTH + 2, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 7, 0x51111111);
            pose.translate(SUCCESSFUL_BOX_X, SUCCESSFUL_BOX_Y, 0);
            //pGuiGraphics.fill(0, -2, successfulnessBox.getWidth(), 0, 0xFF352E1C);
            pose.pushPose();
            pose.scale(1.01F, 1.02F, 1);
            pGuiGraphics.fill(-2, -2, successfulnessBox.getWidth() + 1, successfulnessBox.getHeight() + 1, 0xFF352E1C);
            pose.popPose();
            //pGuiGraphics.fill(1, 0, 2, successfulnessBox.getHeight(), 0xFF352E1C);
            successfulnessBox.render(pose, pGuiGraphics, 0xFFFFFFFF, successfulnessMouseX(pMouseX), successfulnessMouseY(pMouseY), pPartialTick);
            pose.pushPose();
            pose.translate(successfulnessBox.getWidth() - 20, 7, 0);
            pose.scale(0.9F, 0.9F, 1);
            RenderSystem.enableBlend();
            pGuiGraphics.blit(getIcon(successfulnessBox.getChosen().successfulness()), 0, 0, 12, 12, 0, 0, 20, 20, 20, 20);
            pose.popPose();
            pose.popPose();
            if(patientBox.visible) {
                pose.pushPose();
                pose.translate(REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH - 2 - patientBox.getWidth(), SUCCESSFUL_BOX_Y, 0);
                pGuiGraphics.fill(-2, -2, patientBox.getWidth() + 1, patientBox.getHeight() + 1, 0xFF352E1C);
                patientBox.render(pose, pGuiGraphics, 0xFFFFFFFF, patientTypeMouseX(pMouseX), successfulnessMouseY(pMouseY), pPartialTick);
                pose.popPose();
            }
        }
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
        if (reportLineList != null && selectedCategory == JournalCategory.JOURNAL) {
            relativeMouseX = reportMouseX(pMouseX);
            relativeMouseY = reportMouseY(pMouseY);
            int REPORT_LIST_BASE_HEIGHT = 275;
            pose.pushPose();
            pose.translate(REPORT_BASE_LEFT_X, REPORT_BASE_TOP_Y, 0);
            pGuiGraphics.fill(-4, -3, ENTRY_LIST_BASE_WIDTH + 2, REPORT_LIST_BASE_HEIGHT + 5, 0x11111111);
            pGuiGraphics.fill(-0, -3, ENTRY_LIST_BASE_WIDTH - 2, 0, 0x44111111);
            pGuiGraphics.fill(-0, REPORT_LIST_BASE_HEIGHT + 1, ENTRY_LIST_BASE_WIDTH - 2, REPORT_LIST_BASE_HEIGHT + 5, 0x44111111);
            pGuiGraphics.fill(-4, -3, 0, REPORT_LIST_BASE_HEIGHT + 5, 0x44111111);
            pGuiGraphics.fill(ENTRY_LIST_BASE_WIDTH - 2, -3, ENTRY_LIST_BASE_WIDTH + 2, REPORT_LIST_BASE_HEIGHT + 5, 0x44111111);
            reportLineList.render(pose, pGuiGraphics, 0xFFFFFFFF, relativeMouseX, relativeMouseY, pPartialTick);
            pose.popPose();
        }

        renderPage(pose, pGuiGraphics, pageMouseX(pMouseX), pageMouseY(pMouseY), pPartialTick);

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
        //pGuiGraphics.drawString(minecraft.font, String.format("X: %d, Y: %d", pageMouseX(pMouseX), pageMouseY(pMouseY)), 0, 15, 0xFFFFFFFF);
    }

    private void renderPage(PoseStack pose, GuiGraphics graphics, double scaledMouseX, double scaledMouseY, float pPartialTick) {
        if (currentPage() != null) {
            pose.pushPose();
            pose.translate(PAGE_X, PAGE_Y, 0);
            currentPage().render(pose, graphics, 0xFFFFFFFF, (int) (scaledMouseX), (int) (scaledMouseY), pPartialTick);
            pose.popPose();
        }
    }

    private double scaledMouseX(double mouseX) {
        return (mouseX - width / 2D) / scaleFactor;
    }

    private double scaledMouseY(double mouseY) {
        return (mouseY - height / 2D) / scaleFactor;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseClicked(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        if (currentPage() != null && currentPage().mouseClicked(pageMouseX(pMouseX), pageMouseY(pMouseY), pButton)) {
            return true;
        }
        if (successfulnessBox.visible && successfulnessBox.mouseClicked(successfulnessMouseX(pMouseX), successfulnessMouseY(pMouseY), pButton)) {
            return true;
        }
        if (patientBox.visible && patientBox.mouseClicked(patientTypeMouseX(pMouseX), successfulnessMouseY(pMouseY), pButton)) {
            return true;
        }
        if (reportLineList != null && reportLineList.mouseClicked(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton)) {
            return true;
        }
        for (int i = 0; i < bookmarks.size(); i++) {
            JournalBookmark bookmark = bookmarks.get(i);
            if (bookmark.mouseClicked(bookmarkMouseX(pMouseX), bookmarkMouseY(pMouseY, i), pButton)) {
                return true;
            }
        }
        if (buttonHolder.mouseClicked(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton)) {
            updateWidgetVisibility();
            return true;
        }

        return super.mouseClicked(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (currentList() != null && currentList().mouseDragged(listMouseX(pMouseX), listMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        if (reportLineList != null && reportLineList.mouseDragged(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        return super.mouseDragged(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseReleased(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        if (reportLineList != null && reportLineList.mouseReleased(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseReleased(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (currentList() != null && currentList().mouseScrolled(listMouseX(pMouseX), listMouseY(pMouseY), pDelta)) {
            return true;
        }
        if (reportLineList != null && reportLineList.mouseScrolled(reportMouseX(pMouseX), reportMouseY(pMouseY), pDelta)) {
            return true;
        }
        return super.mouseScrolled(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pDelta);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (currentList() != null && currentList().keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        }
        if (reportLineList != null && reportLineList.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (currentList() != null && currentList().charTyped(pCodePoint, pModifiers)) {
            return true;
        }
        if (reportLineList != null && reportLineList.charTyped(pCodePoint, pModifiers)) {
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

    private int pageMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - PAGE_X * scaleFactor) / scaleFactor);
    }

    private int pageMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - PAGE_Y * scaleFactor) / scaleFactor);
    }

    private int reportMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - REPORT_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int reportMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - REPORT_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    private int successfulnessMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - SUCCESSFUL_BOX_X * scaleFactor) / scaleFactor);
    }
    private int patientTypeMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - (REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH - 2 - patientBox.getWidth()) * scaleFactor) / scaleFactor);
    }

    private int successfulnessMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - SUCCESSFUL_BOX_Y * scaleFactor) / scaleFactor);
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

    @Nullable
    private JournalGui.Page currentPage() {
        return switch (selectedCategory) {
            case OVERVIEW -> overviewPage;
            case TOOLS -> toolPage;
            case INGREDIENTS -> ingredientPage;
            case JOURNAL -> null;
            case ABOMINATIONS -> null;
        };
    }

    public void selectEntry(String entry) {
        if (entry.startsWith("tools")) {
            setCategory(JournalCategory.TOOLS);
            String name = entry.substring("tools.".length());
            for (ItemEntry row : tools.rows()) {
                if (Objects.equals(row.name, name)) {
                    row.selectEntry();
                    break;
                }
            }
        } else {
            setCategory(JournalCategory.INGREDIENTS);
            String name = entry.substring("ingredients.".length());
            for (ItemEntry row : ingredients.rows()) {
                if (Objects.equals(row.name, name)) {
                    row.selectEntry();
                    break;
                }
            }
        }
        updateWidgetVisibility();
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
    public void onClose() {
        super.onClose();
        Messages.sendToServer(GenericToServerPacket.setCurrentReport(reportFromList(), editingReport, chosenReport == null ? null : chosenReport.getName()));
        DataUtil.setCurrentReport(Minecraft.getInstance().player, reportFromList(), editingReport, chosenReport == null ? null : chosenReport.getName());
        DataUtilClient.setInt(PlayerDataLib.open_journal_page.name(), selectedCategory.ordinal(), true);
    }

    private Report reportFromList() {
        if (reportLineList == null) {
            return null;
        }
        Report r = new Report(reportName.getValue());
        reportLineList.rows().forEach(row -> r.addStep(row.makeStep()));
        r.setSuccessful(successfulnessBox.getChosen().successfulness());
        r.setPatientType(patientBox.getChosen().patientType());
        return r;
    }

    private void selectReport(Report report, boolean editable) {
        List<JournalReportLine> lines = new ArrayList<>();
        if (report != null) {
            report.getSteps().forEach(step -> {
                JournalReportLine line = JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, editable);
                line.loadFromStep(step);
                lines.add(line);
            });
            reportName.setValue(report.getName());
            reportName.moveCursorToStart();
        } else {
            JournalReportLine line = JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, editable);
            lines.add(line);
        }
        chosenReport = report;
        this.reportLineList = new EditableList<>(ENTRY_LIST_BASE_WIDTH, 275, lines, DROPDOWN_BASE_HEIGHT + 4, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH, () -> JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, editable));
        this.reportLineList.setVariableSize(true);
        successfulnessBox.selectChosen(new Successfulness(report != null && report.getSuccessful() != null ? report.getSuccessful() : Report.Successfulness.IN_PROGRESS));
        patientBox.selectChosen(new PatientType(report != null && report.getPatientType() != null ? report.getPatientType() : ReportPatientType.HUMAN));
        //updateWidgetVisibility();
    }

    private void openItemPage(Page page) {
        switch (selectedCategory) {
            case TOOLS -> toolPage = page;
            case INGREDIENTS -> ingredientPage = page;
        }
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

    @Override
    public void onAddAdvancementRoot(Advancement pAdvancement) {
    }

    @Override
    public void onRemoveAdvancementRoot(Advancement pAdvancement) {
    }

    @Override
    public void onAddAdvancementTask(Advancement pAdvancement) {
    }

    @Override
    public void onRemoveAdvancementTask(Advancement pAdvancement) {
    }

    @Override
    public void onAdvancementsCleared() {
    }

    @Override
    public void onSelectedTabChanged(@Nullable Advancement pAdvancement) {
    }

    private final Set<Item> unblockAbominations = Set.of(Registration.EMPTY_BLADDER.get());

    @Override
    public void onUpdateAdvancementProgress(Advancement pAdvancement, AdvancementProgress pProgress) {
        String s = pAdvancement.getId().toString();
        if (s.startsWith(References.MODID + ":ingredients/")) {
            ForgeRegistries.ITEMS.getHolder(new ResourceLocation(References.MODID, s.substring(s.indexOf('/') + 1))).ifPresent(h -> {
                knownIngredients.add(h.get());
                if (unblockAbominations.contains(h.get())) {
                    abominationsUnlocked = true;
                }
            });
        }
    }

    @NotNull
    private static ResourceLocation getIcon(Report.Successfulness success) {
        return switch (success) {
            case SUCCESS -> TICK;
            case PARTIAL_SUCCESS -> TILDE;
            case FAILED -> CROSS;
            case IN_PROGRESS -> QUESTION_MARK;
            default -> TILDE;
        };
    }

    public static int getSuccessfulnessColor(Report.Successfulness success) {
        return switch (success) {
            case SUCCESS -> 0xFF36D500;
            case PARTIAL_SUCCESS -> 0xFFFFD800;
            case FAILED -> 0xFFEF0000;
            case IN_PROGRESS -> 0xFFFFFFFF;
            default -> 0xFFFFD800;
        };
    }

    private abstract class ItemEntry extends Element {


        private final String name;
        private final String titleKey;
        private final String textKey;

        protected ItemEntry(String name, JournalCategory category) {
            super(ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            this.name = name;
            textKey = "gui.journal." + (category == JournalCategory.TOOLS ? "tools." : "ingredients.") + name + ".text";
            titleKey = "gui.journal." + (category == JournalCategory.TOOLS ? "tools." : "ingredients.") + name + ".title";
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
            renderIcon(graphics);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(56 + 140, getHeight() / 5F - 4, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            //poseStack.scale(scaleFactor, scaleFactor, 1);
            renderTitle(graphics);
            poseStack.popPose();
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                selectEntry();
                // TODO selectEntry(stack.getItem(), recipe);
                // TODO selectedEntry = this;
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1));
                return true;
            }
            return false;
        }

        protected abstract void renderIcon(GuiGraphics graphics);

        protected void renderTitle(GuiGraphics graphics) {
            graphics.drawCenteredString(minecraft.font, Component.translatable(titleKey), 0, 0, 0xFFFFFFFF);
        }

        private void selectEntry() {
            String translateKey = textKey;
            TextBlock textBlock = new TextBlock(I18n.get(translateKey), 300, 285, Minecraft.getInstance().font);
            Page page = new Page(Component.literal("§l" + Component.translatable(titleKey).getString()), textBlock, getGridElements());
            openItemPage(page);
        }

        protected abstract List<Element> getGridElements();

    }

    private class StacksItemEntry extends ItemEntry {
        private final ItemStack shown;
        private final List<Tuple<Item, Recipe<?>>> recipes = new ArrayList<>();

        protected StacksItemEntry(Item item, String name, JournalCategory category) {
            this(List.of(item), name, category);
        }


        protected StacksItemEntry(List<Item> items, String name, JournalCategory category) {
            super(name, category);
            this.shown = new ItemStack(items.get(0));
            for (Item item : items) {
                ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
                if (key != null) {
                    Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(key);
                    recipe.ifPresent(r -> recipes.add(new Tuple<>(item, r)));
                }
            }
        }

        @Override
        protected void renderIcon(GuiGraphics graphics) {
            graphics.renderItem(shown, -8, -8);
        }

        @Override
        protected List<Element> getGridElements() {
            return recipes.stream().map(r -> (Element) new CraftingRegistryGui.CraftingGrid(ENTRY_BASE_WIDTH, 100, r.getB(), r.getA(), 2.25F)).toList();
        }
    }

    private class FluidItemEntry extends ItemEntry {
        private final float red;
        private final float green;
        private final float blue;
        private final List<AlembicsRecipeRegistry.AlembicRecipe> recipes = new ArrayList<>();

        protected FluidItemEntry(Fluid fluid, String name, JournalCategory category) {
            this(List.of(fluid), name, category);
        }


        protected FluidItemEntry(List<Fluid> fluids, String name, JournalCategory category) {
            super(name, category);
            int color = IClientFluidTypeExtensions.of(fluids.get(0)).getTintColor();
            red = ((color >> 16) & 255) / 255F;
            green = ((color >> 8) & 255) / 255F;
            blue = (color & 255) / 255F;
            for (Fluid fluid : fluids) {
                List<AlembicsRecipeRegistry.AlembicRecipe> alembicRecipes = AlembicsRecipeRegistry.knownRecipes(fluid, knownIngredients);
                recipes.addAll(alembicRecipes);
            }
        }

        @Override
        protected void renderIcon(GuiGraphics graphics) {
            RenderSystem.enableBlend();
            graphics.blit(FLUID_ICON, -8, -8, 16, 16, 0, 0, 60, 60, 60, 60);
            RenderSystem.setShaderColor(red, green, blue, 1);
            graphics.blit(FLUID_FILLING_ICON, -8, -8, 16, 16, 0, 0, 60, 60, 60, 60);
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }

        @Override
        protected List<Element> getGridElements() {
            return recipes.stream().map(r -> (Element) new AlembicRecipeDisplay(ENTRY_BASE_WIDTH, 100, r)).toList();
        }
    }

    private class AlembicRecipeDisplay extends Element {
        private final TextureAtlasSprite input1;
        private final Component input1Name;
        private final ItemStack stack;
        private final Component stackName;
        private final TextureAtlasSprite input2;
        private final Component input2Name;
        private final TextureAtlasSprite output;
        private final Component outputName;
        private static final ItemStack ALEMBICS = new ItemStack(Registration.ALEMBICS.get());
        private static final ItemStack FLASK = new ItemStack(Registration.FLASK_LARGE.get());

        protected AlembicRecipeDisplay(int width, int height, AlembicsRecipeRegistry.AlembicRecipe recipe) {
            super(width, height);
            input1 = minecraft.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(IClientFluidTypeExtensions.of(recipe.input1()).getStillTexture());
            input2 = minecraft.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(IClientFluidTypeExtensions.of(recipe.input2()).getStillTexture());
            stack = recipe.stack().copy();
            output = minecraft.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(IClientFluidTypeExtensions.of(recipe.output()).getStillTexture());
            Component s = recipe.input1().getFluidType().getDescription();
            input1Name = recipe.input1().getFluidType().getDescription();
            stackName = recipe.stack().getItem().getDescription();
            input2Name = recipe.input2().getFluidType().getDescription();
            outputName = recipe.output().getFluidType().getDescription();
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            final int Y_OFFSET = 50;
            final int CORRECTION = 6;
            final int X_OFFSET_1 = 4 - CORRECTION;
            final int X_OFFSET_STACK = 56 - CORRECTION;
            final int X_OFFSET_2 = 108 - CORRECTION;
            final int X_OFFSET_OUTPUT = 153 - CORRECTION;
            final int SIDE = 16;
            final float FACTOR = 0.335F;
            poseStack.pushPose();
            poseStack.translate(-CORRECTION, 1, 0);
            poseStack.scale(FACTOR, FACTOR, 1);
            graphics.blit(ALEMBICS_PROFILE, -13, 27, 534, 181, 0, 0, 534, 181, 534, 181);
            poseStack.popPose();
            //poseStack.pushPose();
            //poseStack.translate(-CORRECTION, 5.5, 100);
            //poseStack.mulPose(Axis.YP.rotation((float) Math.PI / 2));
            //final float SCALE = 4;
            //poseStack.scale(SCALE, SCALE, SCALE);
            //poseStack.translate(0, 0, -128);
            //graphics.renderItem(ALEMBICS, 0, 0);
            //poseStack.translate(0, 0, 18);
            //graphics.renderItem(FLASK, 0, 0);
            //poseStack.popPose();
            //poseStack.pushPose();
            //poseStack.translate(0,0, 100);
            if (input1 != null) {
                graphics.blit(X_OFFSET_1, Y_OFFSET, 0, 16, 16, input1);
            }
            if (stack != null) {
                graphics.renderItem(stack, X_OFFSET_STACK, Y_OFFSET - 7);
            }
            if (input2 != null) {
                graphics.blit(X_OFFSET_2, Y_OFFSET, 0, 16, 16, input2);
            }
            if (output != null) {
                graphics.blit(X_OFFSET_OUTPUT, Y_OFFSET, 0, 16, 16, output);
            }
            if (relativeMouseY > Y_OFFSET && relativeMouseY <= Y_OFFSET + SIDE) {
                if (relativeMouseX > X_OFFSET_1 && relativeMouseX <= X_OFFSET_1 + SIDE) {
                    graphics.renderTooltip(minecraft.font, input1Name, relativeMouseX, relativeMouseY);
                }
                if (relativeMouseX > X_OFFSET_2 && relativeMouseX <= X_OFFSET_2 + SIDE) {
                    graphics.renderTooltip(minecraft.font, input2Name, relativeMouseX, relativeMouseY);
                }
                if (relativeMouseX > X_OFFSET_OUTPUT && relativeMouseX <= X_OFFSET_OUTPUT + SIDE) {
                    graphics.renderTooltip(minecraft.font, outputName, relativeMouseX, relativeMouseY);
                }
            }
            if (relativeMouseX > X_OFFSET_STACK && relativeMouseX <= X_OFFSET_STACK + SIDE && relativeMouseY > Y_OFFSET - 7 && relativeMouseY <= Y_OFFSET - 7 + SIDE) {
                graphics.renderTooltip(minecraft.font, stackName, relativeMouseX, relativeMouseY);
            }
            //poseStack.popPose();
        }
    }


    private class ReportEntry extends Element {


        private final String name;
        private final String nameFull;
        private final Component successfulText;
        private final Report.Successfulness success;
        private final Report report;
        private final float successfulTextX;
        private final ResourceLocation icon;

        protected ReportEntry(Report report) {
            super(ENTRY_BASE_WIDTH, REPORT_BASE_HEIGHT);
            this.nameFull = report.getName();
            this.name = GuiHelper.cutString(nameFull, getWidth() * 4 / 5).toString();
            this.success = report.getSuccessful() != null ? report.getSuccessful() : Report.Successfulness.IN_PROGRESS;
            successfulText = switch (success) {
                case SUCCESS -> Component.translatable("gui.journal.journal.type.successful");
                case PARTIAL_SUCCESS -> Component.translatable("gui.journal.journal.type.partially_successful");
                case FAILED -> Component.translatable("gui.journal.journal.type.failed");
                case IN_PROGRESS -> Component.translatable("gui.journal.journal.type.in_progress");
                default -> Component.translatable("gui.journal.journal.type.partially_successful");
            };
            successfulTextX = getWidth() - 24 - Minecraft.getInstance().font.width(successfulText) * 0.7F;
            icon = getIcon(success);
            this.report = report;
        }



        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(REPORT_ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }
            if (Objects.equals(this.report, chosenReport)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x33A88C00);
            }
            int successfulnessColor = getSuccessfulnessColor(success);
            poseStack.pushPose();
            poseStack.translate(successfulTextX, 22, 0);
            poseStack.scale(0.7F, 0.7F, 1);
            graphics.drawString(minecraft.font, successfulText, 1110, 0, successfulnessColor);
            poseStack.popPose();
            RenderSystem.enableBlend();
            graphics.blit(icon, getWidth() - 22, 18, 12, 12, 0, 0, 20, 20, 20, 20);
            poseStack.pushPose();
            poseStack.scale(1.5F, 1.5F, 1);
            graphics.drawString(minecraft.font, name, 5, 8, 0xFFFFDD87);
            poseStack.popPose();
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                if (!editingReport) {
                    selectReport(report, false);
                    updateWidgetVisibility();
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
        mainTitle = Component.literal("§l" + Component.translatable("gui.journal.bookmark." + selectedCategory.name().toLowerCase()).getString());
        updateWidgetVisibility();
    }

    private void updateWidgetVisibility() {
        reportName.active = reportName.visible = (selectedCategory == JournalCategory.JOURNAL && reportLineList != null);
        newButton.visible = editButton.active = (selectedCategory == JournalCategory.JOURNAL);
        editButton.visible = editButton.active = (selectedCategory == JournalCategory.JOURNAL && !editingReport && chosenReport != null);
        saveButton.visible = saveButton.active = (selectedCategory == JournalCategory.JOURNAL && editingReport);
        deleteButton.visible = deleteButton.active = (selectedCategory == JournalCategory.JOURNAL && !editingReport && chosenReport != null);
        cancelButton.visible = cancelButton.active = (selectedCategory == JournalCategory.JOURNAL && editingReport);
        printButton.visible = (selectedCategory == JournalCategory.JOURNAL && chosenReport != null && !editingReport);
        printButton.active = printButton.visible && Minecraft.getInstance().player.getInventory().contains(new ItemStack(Items.PAPER));
        successfulnessBox.visible = selectedCategory == JournalCategory.JOURNAL && (chosenReport != null || editingReport);
        successfulnessBox.editable = successfulnessBox.visible && editingReport;
        patientBox.visible = selectedCategory == JournalCategory.JOURNAL && (chosenReport != null || editingReport) && ResearchUtil.isResearchComplete(Minecraft.getInstance().player, "WEEPERS");
        patientBox.editable = patientBox.visible && editingReport;
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
                TextBlock textBlock = new TextBlock(I18n.get("gui.journal.overview." + id + ".text"), 300, 285, Minecraft.getInstance().font);
                overviewPage = new Page(Component.literal("§l" + Component.translatable("gui.journal.overview." + id).getString()), textBlock, new ArrayList<>());
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1));
                return true;
            }
            return false;
        }
    }

    private final class Page extends Element {
        private final Component title;
        private final TextBlock page;
        private final List<Element> grids;
        private int selectedGrid = 0;
        private static final int ARROW_Y = 320;
        private static final int LEFT_ARROW_X = 12;
        private static final int RIGHT_ARROW_X = 310;

        private Page(Component title, TextBlock page, List<Element> grids) {
            super(330, 460);
            this.title = title;
            this.page = page;
            this.grids = grids;
        }

        @Override
        public void render(PoseStack pose, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            pose.pushPose();
            pose.translate(150, 0, 0);
            pose.scale(2.55F, 2.55F, 1);
            int titleColor = 0xFFD0A030;
            //graphics.fill(-40, 0, 40, 15, 0xFF000000);
            if (title instanceof MutableComponent mc) {
                graphics.drawCenteredString(Minecraft.getInstance().font, mc.withStyle(style), 0, 0, titleColor);
            }
            //graphics.drawCenteredString(Minecraft.getInstance().font, FormattedCharSequence.forward(overviewPage.title.getString(), style), 0, 0, titleColor);
            int titleWidth = minecraft.font.width(title);

            //graphics.fill(-titleWidth / 2, 12, titleWidth / 2, 13, titleColor);
            pose.popPose();
            pose.pushPose();
            pose.translate(0, 40, 0);
            page.render(pose, graphics, 0xFFFFFFFF, (int) (relativeMouseX), (int) (relativeMouseY - 40), pPartialTick);
            pose.popPose();
            if (grids.size() > 1) {
                if (counter > 0) {
                    renderArrow(pose, graphics, relativeMouseX, relativeMouseY, true);
                }
                if (counter < grids.size() - 1) {
                    renderArrow(pose, graphics, relativeMouseX, relativeMouseY, false);
                }
            }
            if (grids.size() > 0) {
                pose.pushPose();
                final int gridX = 40;
                final int gridY = 260;
                pose.translate(gridX, gridY, 0);
                float factor = 1.5F;
                pose.scale(factor, factor, 1);
                grids.get(counter).render(pose, graphics, color, (int) ((relativeMouseX - gridX) / factor), (int) ((relativeMouseY - gridY) / factor), pPartialTick);
                pose.popPose();
            }

        }

        private void renderArrow(PoseStack pose, GuiGraphics graphics, int relativeMouseX, int relativeMouseY, boolean left) {
            pose.pushPose();
            pose.translate(left ? LEFT_ARROW_X : RIGHT_ARROW_X, ARROW_Y, 0);
            pose.scale(1.5F, 1.5F, 1);
            if ((hoveringLeftArrow(relativeMouseX, relativeMouseY) && left) || ((hoveringRightArrow(relativeMouseX, relativeMouseY) && !left))) {
                pose.scale((float) 1.5F, 1.5F, 1);
            }
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            graphics.blit(left ? LEFT_ARROW : RIGHT_ARROW, -ARROW_WIDTH / 2, -ARROW_HEIGHT / 2, ARROW_WIDTH, ARROW_HEIGHT, 0, 0, 54, 53, 54, 53);
            pose.popPose();
        }

        private boolean hoveringLeftArrow(double relativeMouseX, double relativeMouseY) {
            return relativeMouseX > LEFT_ARROW_X - 1.5 * ARROW_WIDTH / 2D && relativeMouseX < LEFT_ARROW_X + 1.5 * ARROW_WIDTH / 2D && relativeMouseY > ARROW_Y - 1.5 * ARROW_HEIGHT / 2D && relativeMouseY < ARROW_Y + 1.5 * ARROW_HEIGHT / 2D;
        }

        private boolean hoveringRightArrow(double relativeMouseX, double relativeMouseY) {
            return relativeMouseX > RIGHT_ARROW_X - 1.5 * ARROW_WIDTH / 2D && relativeMouseX < RIGHT_ARROW_X + 1.5 * ARROW_WIDTH / 2D && relativeMouseY > ARROW_Y - 1.5 * ARROW_HEIGHT / 2D && relativeMouseY < ARROW_Y + 1.5 * ARROW_HEIGHT / 2D;
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (hoveringLeftArrow(relativeMouseX, relativeMouseY) && counter > 0) {
                counter--;
                return true;
            } else if (hoveringRightArrow(relativeMouseX, relativeMouseY) && counter < grids.size() - 1) {
                counter++;
                return true;
            } else if (page.mouseClicked(relativeMouseX, relativeMouseY - 40, mouseButton)) {
                return true;
            }
            return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }
    }

    private record Successfulness(@NotNull Report.Successfulness successfulness) implements EditableDropdownBox.Option {

        @Override
        public Component getText() {
            return Component.translatable("gui.journal.journal.type." + successfulness.name().toLowerCase());
        }

        @Override
        public String getId() {
            return successfulness.name();
        }
    }

    private record PatientType(@NotNull ReportPatientType patientType) implements EditableDropdownBox.Option {

        @Override
        public Component getText() {
            return Component.translatable("gui.journal.journal.type." + patientType.name().toLowerCase());
        }

        @Override
        public String getId() {
            return patientType.name();
        }
    }

}
