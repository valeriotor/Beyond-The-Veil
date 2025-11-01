package com.valeriotor.beyondtheveil.client.gui.research.journal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableDropdownBox;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableList;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.item.SurgeryIngredient;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.surgery.notes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JournalReportLine extends Element implements EditableList.EditableListElement, ScrollableList.NumberedListElement {

    private final List<ItemOption> knownSolids;
    private final List<FluidOption> knownFluids;
    private final boolean editable;
    private final int typeSelectorWidth;
    private final EditableDropdownBox<Type> typeSelector;
    private EditBox quantityBox;
    private final List<Box> boxes = new ArrayList<>();
    private static final int SELECTOR_LIST_LEFT_X = 54;
    private static final int SELECTOR_LIST_TOP_Y = 25;


    public static JournalReportLine makeReportLine(int width, int height, List<Item> knownSolids, List<Fluid> knownFluids, boolean editable) {
        return new JournalReportLine(width, height, knownSolids, knownFluids, editable);
    }

    protected JournalReportLine(int width, int height, List<Item> knownSolids, List<Fluid> knownFluids, boolean editable) {
        super(width, height);
        this.knownSolids = Stream.concat(Stream.of(Items.AIR), knownSolids.stream()).map(ItemOption::new).toList();
        this.knownFluids = Stream.concat(Stream.of(Fluids.EMPTY), knownFluids.stream()).map(FluidOption::new).toList();
        this.editable = editable;
        typeSelector = EditableDropdownBox.makeBox(Arrays.stream(ReportStepType.values()).map(Type::new).toArray(Type[]::new), 23, 8, 0xFF7D633F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, editable);
        typeSelector.setOnSelect(this::updateDropdowns);
        typeSelectorWidth = typeSelector.getWidth();
        boxes.add(new Box(SELECTOR_LIST_LEFT_X, typeSelector));
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
        //graphics.drawString(Minecraft.getInstance().font, "§oInjection: 75 mB Sedative", 50, 6, 0xFFE0D5B3);
        if (insideBounds(relativeMouseX, relativeMouseY) && boxes.size() > 0) {
            Box last = boxes.get(boxes.size() - 1);
            graphics.fill(52, 0, last.x + last.box.getWidth(), getHeight(), 0x4499875A);
        }
        graphics.fill(SELECTOR_LIST_LEFT_X - 3, 1, SELECTOR_LIST_LEFT_X + typeSelectorWidth, getHeight() - 1, 0xFF7D633F);
        //graphics.fill(SELECTOR_LIST_LEFT_X + typeSelectorWidth, 1, SELECTOR_LIST_LEFT_X + typeSelectorWidth + 1, getHeight() - 1, 0xFF352E1C);
        //graphics.fill(52, 2, 101, getHeight() - 2, 0x88000000);
        Box lastBox = boxes.get(boxes.size() - 1);
        int lastX = lastBox.x + lastBox.box.getWidth() + 1;
        graphics.fill(52, 0, lastX, 1, 0xFF352E1C);
        graphics.fill(52, getHeight() - 1, lastX, getHeight(), 0xFF352E1C);
        graphics.fill(52, 0, 53, getHeight(), 0xFF352E1C);
        //graphics.fill(getWidth() - 53 - 1, 0, lastX, getHeight(), 0xFF352E1C);
        for (Box box : boxes) {
            poseStack.pushPose();
            poseStack.translate(box.x, 1, 0);
            box.box.render(poseStack, graphics, color, relativeMouseX - box.x, relativeMouseY - 1, pPartialTick);
            graphics.fill(box.box.getWidth(), 0, box.box.getWidth() + 1, getHeight() - 1, 0xFF352E1C);
            poseStack.popPose();
        }
        if (quantityBox != null) {
            Box firstBox = boxes.get(0);
            graphics.fill(firstBox.x + firstBox.box.getWidth() + 1, 1, quantityBox.getX() + quantityBox.getWidth(), getHeight() - 1, 0xFF8D734F);
            if (relativeMouseX > quantityBox.getX() && relativeMouseX < quantityBox.getX() + quantityBox.getWidth() && relativeMouseY > 0 && relativeMouseY < getHeight()) {
                graphics.fill(firstBox.x + firstBox.box.getWidth() + 1, 1, quantityBox.getX() + quantityBox.getWidth(), getHeight(), 0x0AFFFFFF);
            }
            quantityBox.render(graphics, relativeMouseX, relativeMouseY, pPartialTick);
            graphics.drawString(Minecraft.getInstance().font, "mB", quantityBox.getX() + 30, 8, color);
            graphics.fill(quantityBox.getX() + quantityBox.getWidth(), 0, quantityBox.getX() + quantityBox.getWidth() + 1, getHeight() - 1, 0xFF352E1C);
        }
    }

    @Override
    public void renderIndexed(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick, int index) {
        ScrollableList.NumberedListElement.super.renderIndexed(poseStack, graphics, color, relativeMouseX, relativeMouseY, pPartialTick, index);
        graphics.drawString(Minecraft.getInstance().font, String.format("§l%d.", index + 1), 33, 8, 0xFFE0D5B3);
    }

    @Override
    public void renderAdd(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        if (editable) {
            EditableList.EditableListElement.super.renderAdd(poseStack, graphics, color, relativeMouseX, relativeMouseY);
        }
    }

    @Override
    public void renderDelete(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
        if (editable) {
            EditableList.EditableListElement.super.renderDelete(poseStack, graphics, color, relativeMouseX, relativeMouseY);
        }
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        if (!editable) {
            return false;
        }
        if (quantityBox != null) {
            if (relativeMouseX > quantityBox.getX() && relativeMouseX < quantityBox.getX() + quantityBox.getWidth() && relativeMouseY > 0 && relativeMouseY < getHeight()) {
                quantityBox.active = true;
                quantityBox.setFocused(true);
                return true;
            }
        }
        for (Box box : boxes) {
            if (box.box.mouseClicked(relativeMouseX - box.x, relativeMouseY - 1, mouseButton)) {
                return true;
            }
        }
        //typeSelector.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
    }

    @Override
    public void defocus(double relativeMouseX, double relativeMouseY, int mouseButton) {
        if (quantityBox != null) {
            if (!(relativeMouseX > quantityBox.getX()) || !(relativeMouseX < quantityBox.getX() + quantityBox.getWidth()) || !(relativeMouseY > 0) || !(relativeMouseY < getHeight())) {
                quantityBox.active = false;
                quantityBox.setFocused(false);
                if (quantityBox.getValue().isEmpty()) {
                    quantityBox.setValue("0");
                }
            }
        }
        for (Box box : boxes) {
            box.box.defocus(relativeMouseX - box.x, relativeMouseY - 1, mouseButton);
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (!editable) {
            return false;
        }
        if (quantityBox != null && quantityBox.isActive()) {
            if (pKeyCode == 257) {
                quantityBox.active = false;
                quantityBox.setFocused(false);
                if (quantityBox.getValue().isEmpty()) {
                    quantityBox.setValue("0");
                }
            } else if (quantityBox.keyPressed(pKeyCode, pScanCode, pModifiers)) {
                removeNonDigits();
                return true;
            }
        }
        for (Box box : boxes) {
            if (box.box.keyPressed(pKeyCode, pScanCode, pModifiers)) {
                return true;
            }
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (!editable) {
            return false;
        }
        if (quantityBox != null && quantityBox.isActive()) {
            if (quantityBox.charTyped(pCodePoint, pModifiers)) {
                removeNonDigits();
                return true;
            }
        }
        for (Box box : boxes) {
            if (box.box.charTyped(pCodePoint, pModifiers)) {
                return true;
            }
        }
        return super.charTyped(pCodePoint, pModifiers);
    }

    private void removeNonDigits() {
        String newValue = quantityBox.getValue().replaceAll("\\D", "");
        if (newValue.length() > 4) {
            newValue = newValue.substring(0, 4);
        }
        quantityBox.setValue(newValue);
    }

    @Override
    public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double pDelta) {
        if (!editable) {
            return false;
        }
        for (Box box : boxes) {
            if (box.box.mouseScrolled(relativeMouseX - box.x, relativeMouseY - 1, pDelta)) {
                return true;
            }
        }
        return super.mouseScrolled(relativeMouseX, relativeMouseY, pDelta);
    }

    @Override
    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        if (!editable) {
            return false;
        }
        for (Box box : boxes) {
            if (box.box.mouseDragged(relativeMouseX - box.x, relativeMouseY - 1, pButton, pDragX, pDragY)) {
                return true;
            }
        }
        return super.mouseDragged(relativeMouseX, relativeMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int pButton) {
        if (!editable) {
            return false;
        }
        for (Box box : boxes) {
            if (box.box.mouseReleased(relativeMouseX - box.x, relativeMouseY - 1, pButton)) {
                return true;
            }
        }
        return super.mouseReleased(relativeMouseX, relativeMouseY, pButton);
    }

    private void updateDropdowns(Type type) {
        quantityBox = null;
        Box first = boxes.get(0);
        boxes.clear();
        boxes.add(first);
        switch (type.type) {
            case POSITION -> boxes.add(new Box(first.x + first.box.getWidth() + 1, locationBox()));
            case EXTRACTION, INCISION -> boxes.add(new Box(first.x + first.box.getWidth() + 1, completenessBox()));
            case INJECTION -> {
                int quantityBoxWidth = 50;
                quantityBox = new EditBox(Minecraft.getInstance().font, first.x + first.box.getWidth() + 10, 8, quantityBoxWidth, 23, Component.literal("0"));
                quantityBox.setBordered(false);
                quantityBox.setValue("0");
                Box second = new Box(quantityBox.getX() + quantityBoxWidth + 1, EditableDropdownBox.makeBox(knownFluids.toArray(new FluidOption[]{}), 23, 8, 0xFF8D734F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, editable));
                boxes.add(second);
            }
            case INSERTION -> {
                Box second = new Box(first.x + first.box.getWidth() + 1, EditableDropdownBox.makeBox(knownSolids.toArray(new ItemOption[]{}), 23, 8, 0xFF8D734F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, editable));
                boxes.add(second);
                boxes.add(new Box(second.x + second.box.getWidth() + 1, completenessBox()));
            }
        }
    }

    @Override
    protected boolean insideBounds(double relativeMouseX, double relativeMouseY) {
        for (Box box : boxes) {
            if (box.box.insideBounds(relativeMouseX - box.x, relativeMouseY - 1)) {
                return true;
            }
        }
        return super.insideBounds(relativeMouseX, relativeMouseY);
    }

    @Override
    public void tick() {
        super.tick();
        for (Box box : boxes) {
            box.box.tick();
        }
        if (quantityBox != null) {
            quantityBox.tick();
        }
    }

    public CompoundTag saveToNBT() {
        if (true) {
            return makeStep().saveToNBT();
        }
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < boxes.size(); i++) {
            tag.putString(String.valueOf(i), boxes.get(i).box.getChosen().getId());
        }
        if (quantityBox != null) {
            tag.putString("quantity", quantityBox.getValue());
        }
        return tag;
    }

    public ReportStep makeStep() {
        try {
            ReportStepType type = ReportStepType.valueOf(boxes.get(0).box.getChosen().getId());
            return switch (type) {
                case NONE, STITCHING, PAIN, DEATH -> new ReportStep.SimpleStep(type);
                case POSITION -> new PositionStep(ReportLocationType.valueOf(boxes.get(1).box.getChosen().getId()));
                case EXTRACTION, INCISION ->
                        new ReportStep.CompletableStep(type, Objects.equals(boxes.get(1).box.getChosen().getId(), "complete"));
                case INJECTION -> {
                    int amount = Integer.parseInt(quantityBox.getValue());
                    String id = boxes.get(1).box.getChosen().getId();
                    FluidOption fluidOption = fluidOptionFromId(id);
                    Fluid value = fluidOption.fluid;
                    yield new InjectionStep(amount, value);
                }
                case INSERTION -> {
                    String id = boxes.get(1).box.getChosen().getId();
                    Item value = itemOptionFromId(id).item.getItem();
                    boolean complete = Objects.equals(boxes.get(2).box.getChosen().getId(), "complete");
                    if (value instanceof SurgeryIngredient s) {
                        yield new InsertionStep(complete, s);
                    } else {
                        yield new InsertionStep(complete, null);
                    }
                }
            };
        } catch (Exception e) {
            return new ReportStep.SimpleStep(ReportStepType.NONE);
        }
    }

    public void loadFromStep(ReportStep step) {
        try {
            ReportStepType type = step.getType();
            typeSelector.selectChosen(new Type(type));
            quantityBox = null;
            Box first = boxes.get(0);
            boxes.clear();
            boxes.add(first);
            switch (type) {
                case POSITION -> {
                    EditableDropdownBox<Location> box = locationBox();
                    box.selectChosen(new Location(((PositionStep) step).getLocation()));
                    boxes.add(new Box(first.x + first.box.getWidth() + 1, box));
                }
                case EXTRACTION, INCISION -> {
                    EditableDropdownBox<Completeness> box = completenessBox();
                    box.selectChosen(new Completeness(((ReportStep.CompletableStep) step).isComplete()));
                    boxes.add(new Box(first.x + first.box.getWidth() + 1, box));
                }
                case INJECTION -> {
                    int quantityBoxWidth = 50;
                    quantityBox = new EditBox(Minecraft.getInstance().font, first.x + first.box.getWidth() + 10, 8, quantityBoxWidth, 23, Component.literal("0"));
                    quantityBox.setBordered(false);
                    quantityBox.setValue(String.valueOf(((InjectionStep)step).getAmount()));
                    EditableDropdownBox<FluidOption> box = standardBox(knownFluids.toArray(new FluidOption[]{}), editable);
                    Fluid fluid = ((InjectionStep) step).getFluid();
                    for (FluidOption knownFluid : knownFluids) {
                        if (Objects.equals(knownFluid.fluid, fluid)) {
                            box.selectChosen(knownFluid);
                            break;
                        }
                    }
                    Box second = new Box(quantityBox.getX() + quantityBoxWidth + 1, box);
                    boxes.add(second);
                }
                case INSERTION -> {
                    EditableDropdownBox<ItemOption> firstDropdown = standardBox(knownSolids.toArray(new ItemOption[]{}), editable);
                    SurgeryIngredient ingredient = ((InsertionStep) step).getIngredient();
                    if (ingredient != null) { // otherwise just air
                        for (ItemOption knownSolid : knownSolids) {
                            if (Objects.equals(knownSolid.item.getItem(), ingredient)) {
                                firstDropdown.selectChosen(knownSolid);
                                break;
                            }
                        }
                    }
                    Box firstBox = new Box(first.x + first.box.getWidth() + 1, firstDropdown);
                    boxes.add(firstBox);
                    EditableDropdownBox<Completeness> secondDropdown = completenessBox();
                    secondDropdown.selectChosen(new Completeness(((ReportStep.CompletableStep) step).isComplete()));
                    boxes.add(new Box(firstBox.x + firstBox.box.getWidth() + 1, secondDropdown));
                }
            }
        } catch (Exception e) {
            typeSelector.selectChosen(new Type(ReportStepType.NONE));
        }
    }

    private ItemOption itemOptionFromId(String id) {
        for (ItemOption knownSolid : knownSolids) {
            if (Objects.equals(knownSolid.getId(), id)) {
                return knownSolid;
            }
        }
        return new ItemOption(Items.AIR);
    }

    private FluidOption fluidOptionFromId(String id) {
        for (FluidOption knownFluid : knownFluids) {
            if (Objects.equals(knownFluid.getId(), id)) {
                return knownFluid;
            }
        }
        return new FluidOption(Fluids.EMPTY);
    }

    @NotNull
    private EditableDropdownBox<Completeness> completenessBox() {
        return standardBox(new Completeness[]{new Completeness(true), new Completeness(false)}, editable);
    }

    @NotNull
    private EditableDropdownBox<Location> locationBox() {
        return standardBox(Arrays.stream(ReportLocationType.values()).map(Location::new).toArray(Location[]::new), editable);
    }

    private static <T extends EditableDropdownBox.Option> EditableDropdownBox<T> standardBox(T[] values, boolean editable) {
        return EditableDropdownBox.makeBox(values, 23, 8, 0xFF8D734F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C, editable);
    }

    private record Type(@NotNull ReportStepType type) implements EditableDropdownBox.Option {

        @Override
        public Component getText() {
            return Component.translatable("gui.journal.journal.type." + type.name().toLowerCase());
        }

        @Override
        public String getId() {
            return type.name();
        }
    }

    private record Location(@NotNull ReportLocationType location) implements EditableDropdownBox.Option {

        @Override
        public Component getText() {
            return Component.translatable("gui.journal.journal.location." + location.name().toLowerCase());
        }

        @Override
        public String getId() {
            return location.name();
        }
    }

    private record Completeness(boolean complete) implements EditableDropdownBox.Option {
        @Override
        public Component getText() {
            return Component.translatable("gui.journal.journal.completeness." + (complete ? "complete" : "incomplete"));
        }

        @Override
        public String getId() {
            return complete ? "complete" : "incomplete";
        }
    }

    private static class ItemOption implements EditableDropdownBox.Option {

        private final ItemStack item;

        public ItemOption(Item item) {
            this.item = new ItemStack(item);
        }

        @Override
        public Component getText() {
            if (item.getItem() == Items.AIR) {
                return Component.translatable("gui.journal.journal.ingredient.none");
            }
            return item.getHoverName();
        }

        @Override
        public String getId() {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(item.getItem());
            if (key != null) {
                return key.toString();
            }
            return ForgeRegistries.ITEMS.getKey(Items.AIR).toString();
        }
    }

    private static class FluidOption implements EditableDropdownBox.Option {

        private final ItemStack item;
        private final Fluid fluid;
        private final String text;

        public FluidOption(Fluid fluid) {
            this.fluid = fluid;
            FluidStack fluidStack = new FluidStack(fluid, 1000);
            this.item = FluidUtil.getFilledBucket(fluidStack);
            text = fluidStack.getDisplayName().getString();
        }

        @Override
        public Component getText() {
            if (fluid == Fluids.EMPTY) {
                return Component.translatable("gui.journal.journal.fluid.none");
            }
            return Component.literal(text);
        }

        @Override
        public String getId() {
            ResourceLocation key = ForgeRegistries.FLUIDS.getKey(fluid);
            if (key != null) {
                return key.getPath();
            }
            return null;
        }
    }


    private record Box(int x, EditableDropdownBox<?> box) {
    }

}
