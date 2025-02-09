package com.valeriotor.beyondtheveil.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.capability.util.LetterDataProvider;
import com.valeriotor.beyondtheveil.client.gui.elements.*;
import com.valeriotor.beyondtheveil.container.LetterBoxContainer;
import com.valeriotor.beyondtheveil.letters.Correspondence;
import com.valeriotor.beyondtheveil.letters.Exchange;
import com.valeriotor.beyondtheveil.letters.ExchangeTemplate;
import com.valeriotor.beyondtheveil.letters.Letter;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class LetterBoxGui extends AbstractContainerScreen<LetterBoxContainer> {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/letters/letter_box.png");
    private static final ResourceLocation BUTTON = new ResourceLocation(References.MODID, "textures/gui/letters/letter_box_button.png");
    private static final ResourceLocation PAGE = new ResourceLocation(References.MODID, "textures/gui/letters/letter_page.png");
    private static final ResourceLocation LETTER_ENTRY = new ResourceLocation(References.MODID, "textures/gui/letters/letter_entry.png");
    private static final ResourceLocation LETTER_OPEN = new ResourceLocation(References.MODID, "textures/gui/letters/letter_open.png");
    private static final ResourceLocation LETTER_CLOSED = new ResourceLocation(References.MODID, "textures/gui/letters/letter_closed.png");
    private static final ResourceLocation RED_LETTER_OPEN = new ResourceLocation(References.MODID, "textures/gui/letters/red_letter_open.png");
    private static final ResourceLocation RED_LETTER_CLOSED = new ResourceLocation(References.MODID, "textures/gui/letters/red_letter_closed.png");
    private static final int ENTRY_LIST_BASE_WIDTH = 282 * 5 / 8;
    private static final int ENTRY_LIST_BASE_HEIGHT = 742 * 5 / 8 - 64;
    private static final int BACKGROUND_BASE_WIDTH = 500;
    private static final int BACKGROUND_BASE_HEIGHT = 500;
    private static final int SCROLLBAR_WIDTH = 5;
    private static final int ENTRY_BASE_WIDTH = 282 * 5 / 8 - SCROLLBAR_WIDTH;
    private static final int ENTRY_BASE_HEIGHT = 67 * 5 / 8;
    private static final float ENTRY_LIST_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2F + 4.375F;
    private static final float ENTRY_LIST_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2F + 8.125F + 32;
    private static final int LETTER_WIDTH = 486 * 5 / 8;
    private static final int LETTER_HEIGHT = 756 * 5 / 8;
    private static final float LETTER_BASE_LEFT_X = -BACKGROUND_BASE_WIDTH / 2F + 302 * 5F / 8;
    private static final float LETTER_BASE_TOP_Y = -BACKGROUND_BASE_HEIGHT / 2F + 8.125F;
    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private ScrollableList<LetterEntry> receivedLetters;
    private ScrollableList<LetterEntry> sentLetters;
    private LetterPage shownLetter;
    private ScrollableList<ReceiverEntry> receivers;
    private boolean showingReceived = true;
    private ElementHolder buttonHolder;
    private TexturedButton receivedButton;
    private TexturedButton sentButton;
    private TexturedButton newLetterButton;
    //private TexturedButton replyButton;
    //private TexturedButton sendButton;


    public LetterBoxGui(LetterBoxContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
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

        Minecraft.getInstance().player.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> {
            receivedLetters = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, c.getReceivedInOrder().stream().map(LetterEntry::new).toList(), ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
            receivedLetters.setAlwaysRenderScrollbar(true);
            sentLetters = new ScrollableList<>(ENTRY_LIST_BASE_WIDTH, ENTRY_LIST_BASE_HEIGHT, c.getSentInOrder().stream().map(LetterEntry::new).toList(), ENTRY_BASE_HEIGHT, ENTRY_LIST_BASE_WIDTH - ENTRY_BASE_WIDTH);
            sentLetters.setAlwaysRenderScrollbar(true);
        });

        buttonHolder = ElementHolder.makeHolder(imageWidth, imageHeight);
        receivedButton = buttonHolder.addElement((int) (7 * 5F / 8), 755 * 5 / 8, new TexturedButton(141 * 5 / 8, 32 * 5 / 8, BUTTON, 0x07FFFFFF, Component.translatable("gui.letter_box.received"), t -> {
            showingReceived = true;
        }));
        sentButton = buttonHolder.addElement((int) (148 * 5F / 8), 755 * 5 / 8, new TexturedButton(141 * 5 / 8, 32 * 5 / 8, BUTTON, 0x07FFFFFF, Component.translatable("gui.letter_box.sent"), t -> {
            showingReceived = false;
        }));
        newLetterButton = buttonHolder.addElement((int) (7 * 5F / 8), 13 * 5 / 8, new TexturedButton(282 * 5 / 8, 32 * 5 / 8, BUTTON, 0x07FFFFFF, Component.translatable("gui.letter_box.new"), t -> {
            Minecraft.getInstance().player.getCapability(LetterDataProvider.LETTER_DATA).ifPresent(c -> {
                List<ReceiverEntry> receiverEntries = new ArrayList<>();
                for (Exchange exchange : c.getExchanges()) {
                    if (exchange.canSendLetter() && exchange.noLettersSent()) {
                        receiverEntries.add(new ReceiverEntry(exchange));
                    }
                }
                receivers = new ScrollableList<>(LETTER_WIDTH, LETTER_HEIGHT, receiverEntries, ENTRY_BASE_HEIGHT, SCROLLBAR_WIDTH);
                shownLetter = null;
            });
        }));
        updateWidgetVisibility();
    }

    private void updateWidgetVisibility() {

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.fill(0, 0, width, height, 0xCC111111);
        PoseStack pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        RenderSystem.enableBlend();
        pGuiGraphics.blit(BACKGROUND, -imageWidth / 2, -imageHeight / 2, imageWidth, imageHeight, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT);
        ScrollableList<LetterEntry> toRender = currentList();
        if (toRender != null) {
            pose.pushPose();
            pose.translate(ENTRY_LIST_BASE_LEFT_X, ENTRY_LIST_BASE_TOP_Y, 0);
            toRender.render(pose, pGuiGraphics, 0xFFFFFFFF, listMouseX(pMouseX), listMouseY(pMouseY), pPartialTick, 0, true);
            pose.popPose();
        }
        Element rightElement = rightElement();
        if (rightElement != null) {
            pose.pushPose();
            pose.translate(LETTER_BASE_LEFT_X, LETTER_BASE_TOP_Y, 0);
            rightElement.render(pose, pGuiGraphics, 0xFFFFFFFF, letterMouseX(pMouseX), letterMouseY(pMouseY), pPartialTick);
            pose.popPose();
        }

        pose.pushPose();
        pose.translate(-imageWidth / 2F, -imageHeight / 2F, 0);
        buttonHolder.render(pose, pGuiGraphics, 0xFFFFFFFF, (int) holderMouseX(pMouseX), (int) holderMouseY(pMouseY), pPartialTick);
        pose.popPose();

        pose.popPose();
    }

    private Element rightElement() {
        if (receivers != null) {
            return receivers;
        }
        return shownLetter;
    }

    @Nullable
    private ScrollableList<LetterEntry> currentList() {
        return showingReceived ? receivedLetters : sentLetters;
    }

    private double scaledMouseX(double mouseX) {
        return (mouseX - width / 2D) * scaleFactor;
    }

    private double scaledMouseY(double mouseY) {
        return (mouseY - height / 2D) * scaleFactor;
    }

    private double holderMouseX(double mouseX) {
        return (mouseX - width / 2D + imageWidth / 2D * scaleFactor) / scaleFactor;
    }

    private double holderMouseY(double mouseY) {
        return (mouseY - height / 2D + imageHeight / 2D * scaleFactor) / scaleFactor;
    }
    private int listMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - ENTRY_LIST_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int listMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - ENTRY_LIST_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    private int letterMouseX(double pMouseX) {
        return (int) ((pMouseX - width / 2 - LETTER_BASE_LEFT_X * scaleFactor) / scaleFactor);
    }

    private int letterMouseY(double pMouseY) {
        return (int) ((pMouseY - height / 2 - LETTER_BASE_TOP_Y * scaleFactor) / scaleFactor);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseClicked(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        if (rightElement() != null && rightElement().mouseClicked(letterMouseX(pMouseX), letterMouseY(pMouseY), pButton)) {
            return true;
        }
        if (buttonHolder.mouseClicked(holderMouseX(pMouseX), holderMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (currentList() != null && currentList().mouseReleased(listMouseX(pMouseX), listMouseY(pMouseY), pButton)) {
            return true;
        }
        if (rightElement() != null && rightElement().mouseReleased(letterMouseX(pMouseX), letterMouseY(pMouseY), pButton)) {
            return true;
        }
        if (buttonHolder.mouseReleased(holderMouseX(pMouseX), holderMouseY(pMouseY), pButton)) {
            return true;
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (currentList() != null && currentList().mouseDragged(listMouseX(pMouseX), listMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        if (rightElement() != null && rightElement().mouseDragged(letterMouseX(pMouseX), letterMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        if (buttonHolder.mouseDragged(holderMouseX(pMouseX), holderMouseY(pMouseY), pButton, pDragX, pDragY)) {
            return true;
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (currentList() != null && currentList().mouseScrolled(listMouseX(pMouseX), listMouseY(pMouseY), pDelta)) {
            return true;
        }
        if (rightElement() != null && rightElement().mouseScrolled(letterMouseX(pMouseX), letterMouseY(pMouseY), pDelta)) {
            return true;
        }
        if (buttonHolder.mouseScrolled(holderMouseX(pMouseX), holderMouseY(pMouseY), pDelta)) {
            return true;
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private void selectLetter(Letter letter) {
        receivers = null;
        shownLetter = new LetterPage(letter);
    }

    private void newLetter(ExchangeTemplate.LetterTemplate template) {
        selectLetter(Letter.toWrite(template));
    }

    private class LetterEntry extends Element {
        private final Letter letter;

        protected LetterEntry(Letter letter) {
            super(ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            this.letter = letter;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(LETTER_ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }

            poseStack.pushPose();
            poseStack.translate(28, 28, 0);
            poseStack.scale(2.2F, 2.2F, 1);
            //poseStack.scale(scaleFactor, scaleFactor, 1);
            ResourceLocation icon = letter.getTemplate().getParent().getCorrespondence() == Correspondence.BLOOD_CULT ? (letter.isOpened() ? RED_LETTER_OPEN : RED_LETTER_CLOSED) : (letter.isOpened() ? LETTER_OPEN : LETTER_CLOSED);
            graphics.blit(icon, -11, -11, 16, 16, 0, 0, 60, 60, 60, 60);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(56 + 140, getHeight() / 5F - 4, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            //poseStack.scale(scaleFactor, scaleFactor, 1);
            //renderTitle(graphics);
            poseStack.popPose();
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                selectLetter(letter);
                // TODO open letter
                return true;
            }
            return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }
    }

    private class ReceiverEntry extends Element {
        private final Exchange exchange;
        private final String correspondence;
        private final String object;

        protected ReceiverEntry(Exchange exchange) {
            super(LETTER_WIDTH, ENTRY_BASE_HEIGHT);
            this.exchange = exchange;
            correspondence = Component.translatable("correspondence.to").getString() + Component.translatable("correspondence." + exchange.getTemplate().getCorrespondence().name().toLowerCase()).getString();
            object = Component.translatable("exchange." + exchange.getName() + ".object").getString();
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(LETTER_ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT, ENTRY_BASE_WIDTH, ENTRY_BASE_HEIGHT);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }
            graphics.drawString(Minecraft.getInstance().font, correspondence, 7, 3, 0xFFFFFFFF);
            graphics.drawString(Minecraft.getInstance().font, object, 7, 18, 0xFFFFFFFF);
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                newLetter(exchange.nextLetterTemplate());
                return true;
            }
            return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }
    }


    private static class LetterPage extends Element {

        private final Letter letter;
        private TextBlock textBlock;
        private LetterOptions options;

        protected LetterPage(Letter letter) {
            super(LETTER_WIDTH, LETTER_HEIGHT);
            this.letter = letter;
            makeTextAndOptions();
        }

        private void makeTextAndOptions() {
            StringBuilder localized = new StringBuilder();
            List<String> chosenOptions = letter.getChosenOptions();
            String exchange = letter.getTemplate().getParent().getName().toLowerCase();
            int index = letter.getTemplate().getIndex();
            for (int i = 0; i < chosenOptions.size(); i++) {
                String chosenOption = chosenOptions.get(i);
                if ("name".equals(chosenOption)) {
                    localized.append(Minecraft.getInstance().player.getName().getString());
                } else {
                    localized.append(I18n.get(String.format("exchange.%s.%d.%d.%s", exchange, index, i, chosenOption)));
                }
                //if (i < chosenOptions.size() - 1) {
                //    localized.append("\\n");
                //}
            }
            textBlock = new TextBlock(localized.toString(), LETTER_WIDTH * 9 / 10, LETTER_HEIGHT * 8 / 10, Minecraft.getInstance().font);
            if (letter.getChosenOptions().size() < letter.getTemplate().getOptionsPerLine().size()) {
                String localizationKeyPrefix = String.format("exchange.%s.%d.%d.", exchange, index, letter.getChosenOptions().size());
                BiConsumer<LetterOptions, Integer> listener = (letterOptions, integer) -> {
                    letter.chooseOption(letter.getTemplate().getOptionsPerLine().get(letter.getChosenOptions().size()).get(integer));
                    makeTextAndOptions();
                };
                List<String> localizedOptions = letter.getTemplate().getOptionsPerLine().get(letter.getChosenOptions().size()).stream().map(o -> "name".equals(o) ? Minecraft.getInstance().player.getName().getString() : I18n.get(localizationKeyPrefix + o)).toList();
                options = LetterOptions.makeOptions(localizedOptions, LETTER_WIDTH * 8 / 10, Minecraft.getInstance().font, LETTER_WIDTH * 9 / 10, 60, 10, listener);
            } else {
                options = null;
            }
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(PAGE, 0, 0, getWidth(), getHeight(), 0, 0, LETTER_WIDTH, LETTER_HEIGHT, LETTER_WIDTH, LETTER_HEIGHT);
            poseStack.pushPose();
            poseStack.translate(LETTER_WIDTH * 5F / 100, LETTER_HEIGHT * 5F / 100, 0);
            textBlock.render(poseStack, graphics, color, relativeMouseX - LETTER_WIDTH * 5 / 100, relativeMouseY - LETTER_HEIGHT * 5 / 100, pPartialTick);
            poseStack.popPose();

            if (options != null) {
                poseStack.pushPose();
                poseStack.translate(LETTER_WIDTH * 5F / 100, LETTER_HEIGHT * 85F / 100, 0);
                options.render(poseStack, graphics, color, relativeMouseX - LETTER_WIDTH * 5 / 100, relativeMouseY - LETTER_HEIGHT * 85 / 100, pPartialTick);
                poseStack.popPose();
            }
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (options != null && options.mouseClicked(relativeMouseX - LETTER_WIDTH * 5D / 100, relativeMouseY - LETTER_HEIGHT * 85D / 100, mouseButton)) {
                return true;
            }
            return super.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        }
    }


}
