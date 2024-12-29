package com.valeriotor.beyondtheveil.client.gui.research.journal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableDropdownBox;
import com.valeriotor.beyondtheveil.client.gui.elements.EditableList;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JournalReportLine extends Element implements EditableList.EditableListElement, ScrollableList.NumberedListElement {

    private final List<ItemOption> knownSolids;
    private final List<FluidOption> knownFluids;
    private Type type = Type.NONE;
    private final int typeSelectorWidth;
    private final EditableDropdownBox<Type> typeSelector;
    private EditBox quantityBox;
    private final List<Box> boxes = new ArrayList<>();
    private static final int SELECTOR_LIST_LEFT_X = 54;
    private static final int SELECTOR_LIST_TOP_Y = 25;


    public static JournalReportLine makeReportLine(int width, int height, List<Item> knownSolids, List<Fluid> knownFluids) {
        return new JournalReportLine(width, height, knownSolids, knownFluids);
    }

    protected JournalReportLine(int width, int height, List<Item> knownSolids, List<Fluid> knownFluids) {
        super(width, height);
        this.knownSolids = Stream.concat(Stream.of(Items.AIR), knownSolids.stream()).map(ItemOption::new).toList();
        this.knownFluids = Stream.concat(Stream.of(Fluids.EMPTY), knownFluids.stream()).map(FluidOption::new).toList();
        typeSelector = EditableDropdownBox.makeBox(Type.values(), 23, 8, 0xFF7D633F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C);
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
        graphics.drawString(Minecraft.getInstance().font, String.format("§l%d.", index + 1), 40, 8, 0xFFE0D5B3);
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
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
        for (Box box : boxes) {
            if (box.box.mouseScrolled(relativeMouseX - box.x, relativeMouseY - 1, pDelta)) {
                return true;
            }
        }
        return super.mouseScrolled(relativeMouseX, relativeMouseY, pDelta);
    }

    @Override
    public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int pButton, double pDragX, double pDragY) {
        for (Box box : boxes) {
            if (box.box.mouseDragged(relativeMouseX - box.x, relativeMouseY - 1, pButton, pDragX, pDragY)) {
                return true;
            }
        }
        return super.mouseDragged(relativeMouseX, relativeMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int pButton) {
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
        switch (type) {
            case POSITION -> {
            } // TODO locations, but skull may or may not be known
            case EXTRACTION, INCISION ->
                    boxes.add(new Box(first.x + first.box.getWidth() + 1, EditableDropdownBox.makeBox(Completeness.values(), 23, 8, 0xFF8D734F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C)));
            case INJECTION -> {
                int quantityBoxWidth = 50;
                quantityBox = new EditBox(Minecraft.getInstance().font, first.x + first.box.getWidth() + 10, 8, quantityBoxWidth, 23, Component.literal("0"));
                quantityBox.setBordered(false);
                quantityBox.setValue("0");
                Box second = new Box(quantityBox.getX() + quantityBoxWidth + 1, EditableDropdownBox.makeBox(knownFluids.toArray(new FluidOption[]{}), 23, 8, 0xFF8D734F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C));
                boxes.add(second);
            }
            case INSERTION -> {
                Box second = new Box(first.x + first.box.getWidth() + 1, EditableDropdownBox.makeBox(knownSolids.toArray(new ItemOption[]{}), 23, 8, 0xFF8D734F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C));
                boxes.add(second);
                boxes.add(new Box(second.x + second.box.getWidth() + 1, EditableDropdownBox.makeBox(Completeness.values(), 23, 8, 0xFF8D734F, 0xFFAAAAAA, 0xFF352E1C, 0xFF554E3C)));
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

    private enum Type implements EditableDropdownBox.Option {
        NONE, POSITION, EXTRACTION, INCISION, INJECTION, INSERTION, STITCHING, PAIN, DEATH;

        public Component getText() {
            return Component.translatable("gui.journal.journal.type." + name().toLowerCase());
        }

    }

    private enum Completeness implements EditableDropdownBox.Option {

        COMPLETE, INCOMPLETE;

        @Override
        public Component getText() {
            return Component.translatable("gui.journal.journal.completeness." + name().toLowerCase());
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
    }


    private record Box(int x, EditableDropdownBox<?> box) {
    }

}
