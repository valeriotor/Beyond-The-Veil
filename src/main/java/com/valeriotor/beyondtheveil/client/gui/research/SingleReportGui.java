package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableDropdownBox;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableList;
import com.valeriotor.beyondtheveil.client.gui.elements.ElementHolder;
import com.valeriotor.beyondtheveil.client.gui.elements.TexturedButton;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalCategory;
import com.valeriotor.beyondtheveil.client.gui.research.journal.JournalReportLine;
import com.valeriotor.beyondtheveil.lib.BTVFluids;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.networking.GenericToServerPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
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
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SingleReportGui extends Screen implements ClientAdvancements.Listener{

    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/journal/single_report_background.png");
    private static final ResourceLocation TICK = new ResourceLocation(References.MODID, "textures/gui/journal/tick.png");
    private static final ResourceLocation TILDE = new ResourceLocation(References.MODID, "textures/gui/journal/tilde.png");
    private static final ResourceLocation CROSS = new ResourceLocation(References.MODID, "textures/gui/journal/cross.png");
    private static final ResourceLocation QUESTION_MARK = new ResourceLocation(References.MODID, "textures/gui/journal/question_mark.png");
    private static final int BACKGROUND_BASE_WIDTH = 382;
    private static final int BACKGROUND_BASE_HEIGHT = 498;
    private static final int ENTRY_LIST_BASE_WIDTH = 345;
    private static final int ENTRY_BASE_WIDTH = 340;
    private static final int DROPDOWN_BASE_HEIGHT = 21;
    private static final int REPORT_BASE_LEFT_X = -170;
    private static final int REPORT_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 100;
    private static final int ENTRY_LIST_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2 + 43;
    private static final int ENTRY_LIST_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2 + 100;
    public static final int SUCCESSFUL_BOX_X = -167;
    public static final int SUCCESSFUL_BOX_Y = ENTRY_LIST_BASE_TOP_Y - 33;
    private EditableList<JournalReportLine> reportLineList;
    private Report chosenReport;
    private EditBox reportName;
    private TexturedButton saveButton;
    private EditableDropdownBox<JournalGui.Successfulness> successfulnessBox;
    private EditableDropdownBox<JournalGui.PatientType> patientBox;
    private static final ResourceLocation BUTTON = new ResourceLocation(References.MODID, "textures/gui/journal/button.png");
    private List<Fluid> knownFluids = new ArrayList<>();
    private final List<Item> knownIngredients = new ArrayList<>();
    private ElementHolder buttonHolder;
    private int savedCounter = 0;

    public SingleReportGui() {
        super(Component.translatable("gui.single_report.title"));
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection != null) {
            connection.getAdvancements().setListener(this);
        }

    }

    @Override
    protected void init() {
        updateKnownIngredients();
        scaleFactor = 1;
        imageWidth = BACKGROUND_BASE_WIDTH;
        imageHeight = BACKGROUND_BASE_HEIGHT;
        //entryListWidth = 343;
        //entryListHeight = 357;

        if (imageHeight > height * 95 / 100) {
            scaleFactor = height * 95F / 100 / imageHeight;
        }
        if (imageWidth > width * 95 / 100) {
            scaleFactor = Math.min(width * 95F / 100 / imageWidth, scaleFactor);
        }
        LocalPlayer p = Minecraft.getInstance().player;
        if (p != null) {
            ItemStack stack = p.getItemInHand(InteractionHand.MAIN_HAND);
            if (stack.is(Registration.SURGERY_REPORT.get())) {
                if (stack.hasTag()) {
                    CompoundTag tag = stack.getTag().getCompound("report");
                    Report report = Report.loadFromNBT(tag);
                    chosenReport = report;
                    List<JournalReportLine> lines = new ArrayList<>();
                    report.getSteps().forEach(step -> {
                        JournalReportLine line = JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, false);
                        line.loadFromStep(step);
                        lines.add(line);
                    });

                    if (reportName != null) {
                        removeWidget(reportName);
                    }
                    reportName = addRenderableWidget(new EditBox(minecraft.font, -60, 150, 120, 20, Component.literal("")));
                    reportName.setValue(report.getName());
                    reportName.moveCursorToStart();
                    reportLineList = new EditableList<>(ENTRY_LIST_BASE_WIDTH, 275, lines, DROPDOWN_BASE_HEIGHT + 4, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH, () -> JournalReportLine.makeReportLine(ENTRY_BASE_WIDTH, DROPDOWN_BASE_HEIGHT + 4, knownIngredients, knownFluids, false));
                    reportLineList.setVariableSize(true);
                    this.reportLineList.setEditable(false);
                    successfulnessBox = EditableDropdownBox.makeBox(Arrays.stream(Report.Successfulness.values()).map(JournalGui.Successfulness::new).toArray(JournalGui.Successfulness[]::new), 23, 8, 0xFF7D633F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, false, 150, 40);
                    patientBox = EditableDropdownBox.makeBox(Arrays.stream(ReportPatientType.values()).map(JournalGui.PatientType::new).toArray(JournalGui.PatientType[]::new), 23, 8, 0xFF7D633F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, false, 150, 40);
                    successfulnessBox.selectChosen(new JournalGui.Successfulness(report.getSuccessful() != null ? report.getSuccessful() : Report.Successfulness.IN_PROGRESS));
                    patientBox.selectChosen(new JournalGui.PatientType(report.getPatientType() != null ? report.getPatientType() : ReportPatientType.HUMAN));
                    buttonHolder = ElementHolder.makeHolder(width, height);
                    saveButton = buttonHolder.addElement(-165, 150, new TexturedButton(50, 20, BUTTON, 0x07FFFFFF, Component.translatable("gui.journal.journal.save"), pButton -> {
                        if (savedCounter <= 0) {
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
                            if (chosenReport != null) {
                                savedCounter = 100;
                                GenericToServerPacket packet = GenericToServerPacket.addJournalReport(chosenReport.saveToNBT(), false);
                                Messages.sendToServer(packet);
                            }
                            DataUtil.addReport(Minecraft.getInstance().player, chosenReport);
                            reportName.setFocused(false);
                        }
                    }));
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        LocalPlayer p = Minecraft.getInstance().player;
        if (p != null) {
            ItemStack stack = p.getItemInHand(InteractionHand.MAIN_HAND);
            if (!stack.is(Registration.SURGERY_REPORT.get())) {
                onClose();
            }
        }
        if (savedCounter > 0) {
            savedCounter--;
        }
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
        //TODO buttonHolder.render(pose, pGuiGraphics, 0xFFFFFFFF, (int) scaledMouseX(pMouseX), (int) scaledMouseY(pMouseY), pPartialTick);
        super.render(pGuiGraphics, (int) scaledMouseX(pMouseX), (int) scaledMouseY(pMouseY), pPartialTick);
        buttonHolder.render(pose, pGuiGraphics, 0xFFFFFFFF, (int) scaledMouseX(pMouseX), (int) scaledMouseY(pMouseY), pPartialTick);
        if (successfulnessBox.visible) {
            pose.pushPose();
            pGuiGraphics.fill(REPORT_BASE_LEFT_X, SUCCESSFUL_BOX_Y - 4, REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH - 2, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, 0x21111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X - 4, SUCCESSFUL_BOX_Y - 4, REPORT_BASE_LEFT_X + 0, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, 0x51111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH - 2, SUCCESSFUL_BOX_Y - 4, REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH + 2, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, 0x51111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X - 4, SUCCESSFUL_BOX_Y - 8, REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH + 2, SUCCESSFUL_BOX_Y - 4, 0x51111111);
            pGuiGraphics.fill(REPORT_BASE_LEFT_X - 4, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 3, REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH + 2, SUCCESSFUL_BOX_Y + successfulnessBox.getHeight() + 7, 0x51111111);
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
            if (patientBox.visible) {
                pose.pushPose();
                pose.translate(REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH - 2 - patientBox.getWidth(), SUCCESSFUL_BOX_Y, 0);
                pGuiGraphics.fill(-2, -2, patientBox.getWidth() + 1, patientBox.getHeight() + 1, 0xFF352E1C);
                patientBox.render(pose, pGuiGraphics, 0xFFFFFFFF, patientTypeMouseX(pMouseX), successfulnessMouseY(pMouseY), pPartialTick);
                pose.popPose();
            }
        }

        if (reportLineList != null) {
            int relativeMouseX = reportMouseX(pMouseX);
            int relativeMouseY = reportMouseY(pMouseY);
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

        if (savedCounter > 0) {
            int alpha = Mth.clamp(savedCounter * 255 / 40, 32, 255);
            pGuiGraphics.drawCenteredString(minecraft.font, Component.translatable("gui.single_report.saved", reportName.getValue()), 0, 190, (alpha << 24) | 0xFFFFFF);
        }

        pose.popPose();
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


    private double scaledMouseX(double mouseX) {
        return (mouseX - width / 2D) / scaleFactor;
    }

    private double scaledMouseY(double mouseY) {
        return (mouseY - height / 2D) / scaleFactor;
    }

    private int patientTypeMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - (REPORT_BASE_LEFT_X + ENTRY_LIST_BASE_WIDTH - 2 - patientBox.getWidth()) * scaleFactor) / scaleFactor);
    }

    private int successfulnessMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - SUCCESSFUL_BOX_X * scaleFactor) / scaleFactor);
    }

    private int successfulnessMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - SUCCESSFUL_BOX_Y * scaleFactor) / scaleFactor);
    }

    private int reportMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - REPORT_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int reportMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - REPORT_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    private void updateKnownIngredients() {
        LocalPlayer p = Minecraft.getInstance().player;
        if (p == null) {
            return;
        }
        Set<String> allBooleans = p.getCapability(PlayerDataProvider.PLAYER_DATA).resolve().get().getAllBooleans();
        List<Fluid> toSort = new ArrayList<>();
        for (String b : allBooleans) {
            if (b.startsWith("fluid_")) {
                String pPath = b.substring(6) + "_source";
                Optional<Holder<Fluid>> holder = ForgeRegistries.FLUIDS.getHolder(new ResourceLocation(References.MODID, pPath));
                holder.ifPresent(fluidHolder -> toSort.add(fluidHolder.get()));
            }
        }
        toSort.sort(Comparator.comparing(f -> new FluidStack(f, 1000).getDisplayName().getString()));
        List<Fluid> knownFluids = new ArrayList<>(toSort);
        if (!knownFluids.contains(BTVFluids.SOURCE_FLUID_SEDATIVE.get())) {
            knownFluids.add(BTVFluids.SOURCE_FLUID_SEDATIVE.get());
        }
        if (!knownFluids.contains(BTVFluids.SOURCE_FLUID_COAGULANT.get())) {
            knownFluids.add(BTVFluids.SOURCE_FLUID_COAGULANT.get());
        }
        if (!knownFluids.contains(BTVFluids.SOURCE_FLUID_SOFTENER.get())) {
            knownFluids.add(BTVFluids.SOURCE_FLUID_SOFTENER.get());
        }
        if (knownFluids.size() != this.knownFluids.size()) {
            this.knownFluids = knownFluids;
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
    public void onUpdateAdvancementProgress(Advancement pAdvancement, AdvancementProgress pProgress) {
        String s = pAdvancement.getId().toString();
        if (s.startsWith(References.MODID + ":ingredients/")) {
            ForgeRegistries.ITEMS.getHolder(new ResourceLocation(References.MODID, s.substring(s.indexOf('/') + 1))).ifPresent(h -> {
                knownIngredients.add(h.get());
            });
        }
    }

    @Override
    public void onSelectedTabChanged(@Nullable Advancement pAdvancement) {

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

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (buttonHolder.mouseClicked(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton)) {
            return true;
        }
        if (reportLineList != null && reportLineList.mouseClicked(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton)) {
            return true;
        }

        boolean b = super.mouseClicked(scaledMouseX(pMouseX), scaledMouseY(pMouseY), pButton);
        if (!b) {
            reportName.setFocused(false);
        }
        return b;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (super.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        }
        InputConstants.Key key = InputConstants.getKey(pKeyCode, pScanCode);
        if (!reportName.isFocused() && Minecraft.getInstance().options.keyInventory.isActiveAndMatches(key)) {
            this.onClose();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (reportLineList != null && reportLineList.mouseScrolled(reportMouseX(pMouseX), reportMouseY(pMouseY), pDelta)) {
            return true;
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (reportLineList != null && reportLineList.mouseDragged(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (reportLineList != null && reportLineList.mouseReleased(reportMouseX(pMouseX), reportMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }
}
