package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.Fonts;
import com.valeriotor.beyondtheveil.client.gui.elements.*;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.rituals.RitualRegistry;
import com.valeriotor.beyondtheveil.rituals.RitualTemplate;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BloodThesisGui extends Screen {

    private static final int PAGE_WIDTH = 600 * 3 / 5;
    private static final int PAGE_HEIGHT = 800 * 3 / 5;
    private static final int BACKGROUND_BASE_WIDTH = 1210 * 3 / 5;
    private static final int BACKGROUND_BASE_HEIGHT = 800 * 3 / 5;
    private static final int TEXT_BLOCK_WIDTH = 550 * 3 / 5;
    private static final int TEXT_BLOCK_HEIGHT = 680 * 3 / 5;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/blood_thesis.png");
    private static final ResourceLocation LOGO = new ResourceLocation(References.MODID, "textures/gui/miskatonic_logo.png");

    private int imageWidth;
    private int imageHeight;
    private float scaleFactor = 1;
    private List<Element> pages;
    private int index;
    private float zoom = 1;


    public BloodThesisGui() {
        super(Component.translatable("gui.blood_thesis.title"));
    }

    @Override
    protected void init() {
        scaleFactor = 1;
        imageWidth = BACKGROUND_BASE_WIDTH;
        imageHeight = BACKGROUND_BASE_HEIGHT;
        //entryListWidth = 343;
        //entryListHeight = 357;

        float heightRatio = 95F;
        if (imageHeight > height * heightRatio / 100) {
            scaleFactor = height * heightRatio / 100 / imageHeight;
        }
        float widthRatio = 95F;
        if (imageWidth > width * widthRatio / 100) {
            scaleFactor = Math.min(width * widthRatio / 100 / imageWidth, scaleFactor);
        }

        pages = pages();
    }

    private List<Element> pages() {
        // Easier to just hardcode it
        List<Element> blocks = new ArrayList<>();
        blocks.add(new TitlePage());
        blocks.add(empty());
        blocks.add(new AbstractPage());
        blocks.add(empty());
        for (TextChapter value : TextChapter.values()) {
            blocks.addAll(value.makeChapter());
            if (blocks.size() % 2 == 1) {
                blocks.add(empty());
            }
        }

        return blocks;
    }

    private TextBlock centered(String key, int index) {
        Font f = minecraft.font;
        return new TextBlock(new TextUtil().alignCenter().parseText(I18n.get("research.thesis." + key + "." + index), TEXT_BLOCK_WIDTH, f), TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT, f);
    }

    private TextBlock m(String key, int index) {
        return new TextBlock(I18n.get("research.thesis." + key + "." + index), TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT, minecraft.font);
    }

    private TextBlock empty() {
        return new TextBlock("", TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT, minecraft.font);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.fill(0, 0, width, height, 0xCC111111);
        Element leftElement = (index == 0) ? null : pages.get(index * 2 - 1);
        Element rightElement = (index * 2 >= pages.size()) ? null : pages.get(index * 2);
        PoseStack pose = pGuiGraphics.pose();

        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        pose.scale(zoom, zoom, 1);
        if (zoom > 1) {
            pose.translate(width / 2D - pMouseX, height / 2D - pMouseY, 0);
        }
        if (leftElement != null) {
            pose.pushPose();
            pose.translate(-BACKGROUND_BASE_WIDTH / 2F, -BACKGROUND_BASE_HEIGHT / 2F, 0);
            pGuiGraphics.blit(BACKGROUND, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, PAGE_WIDTH, PAGE_HEIGHT);
            pose.translate((PAGE_WIDTH - TEXT_BLOCK_WIDTH) / 2F, 40, 0);
            IntIntPair leftPageMouse = leftPageMouse(pMouseX, pMouseY);
            leftElement.render(pose, pGuiGraphics, 0xFF000000, leftPageMouse.firstInt(), leftPageMouse.secondInt(), pPartialTick);
            pose.popPose();
            pGuiGraphics.blit(BACKGROUND, -BACKGROUND_BASE_WIDTH / 2, -BACKGROUND_BASE_HEIGHT / 2, PAGE_WIDTH, PAGE_HEIGHT, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, PAGE_WIDTH, PAGE_HEIGHT);
        }
        if (rightElement != null) {
            pGuiGraphics.blit(BACKGROUND, BACKGROUND_BASE_WIDTH / 2 - PAGE_WIDTH, -BACKGROUND_BASE_HEIGHT / 2, PAGE_WIDTH, PAGE_HEIGHT, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, PAGE_WIDTH, PAGE_HEIGHT);
            pose.pushPose();
            pose.translate(BACKGROUND_BASE_WIDTH / 2F - PAGE_WIDTH, -BACKGROUND_BASE_HEIGHT / 2F, 0);
            //pGuiGraphics.blit(BACKGROUND, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, PAGE_WIDTH, PAGE_HEIGHT);
            pose.translate((PAGE_WIDTH - TEXT_BLOCK_WIDTH) / 2F, 40, 0);
            IntIntPair rightPageMouse = rightPageMouse(pMouseX, pMouseY);
            RenderSystem.enableBlend();
            rightElement.render(pose, pGuiGraphics, 0xFF000000, rightPageMouse.firstInt(), rightPageMouse.secondInt(), pPartialTick);
            pose.popPose();
            pGuiGraphics.blit(BACKGROUND, BACKGROUND_BASE_WIDTH / 2 - PAGE_WIDTH, -BACKGROUND_BASE_HEIGHT / 2, PAGE_WIDTH, PAGE_HEIGHT, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, PAGE_WIDTH, PAGE_HEIGHT);
        }
        pose.popPose();
    }

    private IntIntPair leftPageMouse(int mouseX, int mouseY) {
        return IntIntPair.of((int) (((mouseX - width / 2D) + (BACKGROUND_BASE_WIDTH / 2 - (PAGE_WIDTH - TEXT_BLOCK_WIDTH) / 2F) * scaleFactor) / scaleFactor), (int) (((mouseY - height / 2D) + BACKGROUND_BASE_HEIGHT / 2 * scaleFactor) / scaleFactor));
    }

    private IntIntPair rightPageMouse(int mouseX, int mouseY) {
        return IntIntPair.of((int) (((mouseX - width / 2D) - (BACKGROUND_BASE_WIDTH / 2 - TEXT_BLOCK_WIDTH) * scaleFactor) / scaleFactor), (int) (((mouseY - height / 2D) + BACKGROUND_BASE_HEIGHT / 2 * scaleFactor) / scaleFactor));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (this.minecraft.options.keyLeft.matches(pKeyCode, pScanCode)) {
            leftArrowClick();
        } else if (this.minecraft.options.keyRight.matches(pKeyCode, pScanCode)) {
            rightArrowClick();
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    private void leftArrowClick() {
        if (index > 0) {
            index--;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
        }
    }

    private void rightArrowClick() {
        if (index < pages.size() / 2) {
            index++;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (pDelta > 0) {
            zoom = Math.min(2.5F, zoom + 0.25F);
        } else if (pDelta < 0) {
            zoom = Math.max(1, zoom - 0.25F);
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private static class TitlePage extends Element {

        private final Component institute1;
        private final Component institute2;
        private final Component title1;
        private final Component title2;
        private final Component thesis;
        private final Component author;
        private final Component supervisor;

        protected TitlePage() {
            super(TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT);
            institute1 = Component.translatable("research.thesis.title.institute1").withStyle(Fonts.ACADEMIC_STYLE);
            institute2 = Component.translatable("research.thesis.title.institute2").withStyle(Fonts.ACADEMIC_STYLE);
            title1 = Component.translatable("research.thesis.title.title1").withStyle(Fonts.ACADEMIC_STYLE);
            title2 = Component.translatable("research.thesis.title.title2").withStyle(Fonts.ACADEMIC_STYLE);
            thesis = Component.translatable("research.thesis.title.thesis").withStyle(Fonts.ACADEMIC_STYLE);
            author = Component.translatable("research.thesis.title.author").withStyle(Fonts.ACADEMIC_STYLE);
            supervisor = Component.translatable("research.thesis.title.supervisor").withStyle(Fonts.ACADEMIC_STYLE);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            poseStack.pushPose();
            poseStack.translate(0, -40, 0);
            poseStack.pushPose();
            poseStack.translate(0, 7, 100);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            graphics.blit(LOGO, -5, 0, 70, 70, 0, 0, 200, 200, 200, 200);
            poseStack.popPose();

            Font f = Minecraft.getInstance().font;
            poseStack.pushPose();
            poseStack.translate(TEXT_BLOCK_WIDTH - 109, 31, 0);
            graphics.drawString(f, institute1, 0, 0, color);
            graphics.drawString(f, institute2, 0, 15, color);
            poseStack.popPose();

            int y = 127;
            poseStack.pushPose();
            poseStack.translate(TEXT_BLOCK_WIDTH / 2F, y, 0);
            poseStack.scale(2, 2, 1);
            graphics.drawCenteredString(f, title1, 0, 0, color);
            poseStack.popPose();

            y += 30;
            poseStack.pushPose();
            poseStack.translate(TEXT_BLOCK_WIDTH / 2F, y, 0);
            poseStack.scale(1.75F, 1.75F, 1);
            graphics.drawCenteredString(f, title2, 0, 0, color);
            poseStack.popPose();


            y += 70;
            poseStack.pushPose();
            poseStack.translate(TEXT_BLOCK_WIDTH / 2F, y, 0);
            poseStack.scale(1.35F, 1.35F, 1);
            graphics.drawCenteredString(f, thesis, 0, 0, color);
            graphics.drawCenteredString(f, author, 0, 50, color);
            poseStack.popPose();


            y += 90;
            poseStack.pushPose();
            poseStack.translate(TEXT_BLOCK_WIDTH / 2F, y, 0);
            poseStack.scale(1F, 1F, 1);
            graphics.drawCenteredString(f, supervisor, 0, 0, color);
            poseStack.popPose();
            poseStack.popPose();


            //y += 90;
            //poseStack.pushPose();
            //poseStack.translate(TEXT_BLOCK_WIDTH / 2F, y, 100);
            //RenderSystem.enableBlend();
            //graphics.blit(LOGO, -35, 0, 70, 70, 0, 0, 200, 200, 200, 200);
            //poseStack.popPose();
        }
    }

    private static class AbstractPage extends Element {

        private final MutableComponent title;
        private final List<Component> lines = new ArrayList<>();
        private final TextBlock text;

        protected AbstractPage() {
            super(TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT);
            title = Component.translatable("research.thesis.abstract.title").withStyle(Fonts.ACADEMIC_STYLE);
            String text = I18n.get("research.thesis.abstract");
            List<Element> elements = new TextUtil().setStyle(Fonts.ACADEMIC_STYLE).alignCenter().parseText(text, TEXT_BLOCK_WIDTH, Minecraft.getInstance().font);
            this.text = new TextBlock(elements, TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT, Minecraft.getInstance().font);
            //Minecraft.getInstance().font.getSplitter().splitLines(text, TEXT_BLOCK_WIDTH, Fonts.ACADEMIC_STYLE, false, (pStyle, pCurrentPos, pContentWidth) -> {
            //    lines.add(Component.literal(text.substring(pCurrentPos, pContentWidth)).withStyle(pStyle));
            //});

        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            final int startY = 60;
            poseStack.pushPose();
            poseStack.translate(TEXT_BLOCK_WIDTH / 2F, startY, 0);
            poseStack.scale(1.45F, 1.45F, 1);
            graphics.drawCenteredString(Minecraft.getInstance().font, title, 0, 0, color);
            poseStack.popPose();
            poseStack.pushPose();
            int newY = startY + 30;
            poseStack.translate(0, newY, 0);
            text.render(poseStack, graphics, color, relativeMouseX, relativeMouseY - newY, pPartialTick);
            //for (int i = 0; i < lines.size(); i++) {
            //    graphics.drawCenteredString(Minecraft.getInstance().font, lines.get(i), TEXT_BLOCK_WIDTH / 2, 15 * i, color);
            //}
            poseStack.popPose();
        }
    }

    private static class ChapterTitle extends Element {

        private final int index;
        private final Component chapter;
        private final Component localized;

        protected ChapterTitle(int index, Component localized) {
            super(TEXT_BLOCK_WIDTH, 80);
            this.index = index;
            this.chapter = Component.translatable("research.thesis.chapter", index + 1).withStyle(Fonts.ACADEMIC_STYLE);
            this.localized = localized;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            poseStack.pushPose();
            //poseStack.translate(TEXT_BLOCK_WIDTH / 2D, 0, 0);
            poseStack.scale(1.25F, 1.25F, 1);
            graphics.drawString(Minecraft.getInstance().font, chapter, 0, 0, color);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0, 25, 0);
            poseStack.scale(1.75F, 1.75F, 1);
            graphics.drawString(Minecraft.getInstance().font, localized, 0, 0, color);
            poseStack.popPose();
        }
    }

    private static class SectionTitle extends Element {

        private final Component localized;

        protected SectionTitle(Component localized) {
            super(TEXT_BLOCK_WIDTH, 25);
            this.localized = localized;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            poseStack.pushPose();
            //poseStack.translate(TEXT_BLOCK_WIDTH / 2D, 0, 0);
            poseStack.scale(1.25F, 1.25F, 1);
            graphics.drawString(Minecraft.getInstance().font, localized, 0, 0, color);
            poseStack.popPose();
        }
    }

    private static class RitualPage extends Element {

        private final String ritual;
        private final Component title;
        private final TextBlock text;

        protected RitualPage(int chapter, int ritualIndex, String ritual) {
            super(TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT);
            this.ritual = ritual;
            String title = String.format("%d.%d   ", chapter + 1, ritualIndex + 1) + I18n.get("research.thesis.ritual." + ritual + ".title");
            this.title = Component.literal(title).withStyle(Fonts.ACADEMIC_STYLE);
            TextUtil util = new TextUtil();
            List<Element> elements = util.setStyle(Fonts.ACADEMIC_STYLE).parseText(I18n.get("research.thesis.ritual." + ritual + ".text"), TEXT_BLOCK_WIDTH, Minecraft.getInstance().font);
            this.text = new TextBlock(elements, TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT, Minecraft.getInstance().font);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            poseStack.pushPose();
            poseStack.translate(0, 0, 0);
            poseStack.scale(1.55F, 1.55F, 1);
            graphics.drawString(Minecraft.getInstance().font, title, 0, 10, color);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0, 55, 0);
            text.render(poseStack, graphics, color, relativeMouseX, relativeMouseY - 55, pPartialTick);
            poseStack.popPose();
        }
    }


    private static class RitualPicture extends Element {

        private final ResourceLocation image;

        protected RitualPicture(RitualTemplate ritual) {
            super(TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT);
            image = new ResourceLocation(References.MODID, "textures/gui/rituals/" + ritual.getName() + ".png");

        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            if (image != null) {
                poseStack.pushPose();
                poseStack.translate(0, 0, 100);
                graphics.blit(image, 0, 60, 300, 300, 0, 0, 500, 500, 500, 500);
                poseStack.popPose();
            }
        }
    }



    private enum TextChapter {
        BACKGROUND(2),
        RITUAL(3, List.of(), true),
        RISKS(2, List.of(), true),
        MODIFIER(2),
        CREATION(1, List.of(RitualRegistry.BLOOD_BRICKS, RitualRegistry.CORAL_STAFF, RitualRegistry.BLEEDING_BELT, RitualRegistry.LIVING_IRON, RitualRegistry.VESSEL_STONE, RitualRegistry.BLOOD_ORB, RitualRegistry.BLOOD_GEM, RitualRegistry.VEIN_MINER)),
        BINDING(1, List.of(RitualRegistry.BIND_ITEM_DAMAGE, RitualRegistry.BIND_ITEM_WEAKNESS, RitualRegistry.BIND_PILLAR, RitualRegistry.POOL_FLEBO)),
        MANIFESTATION(1, List.of(RitualRegistry.SUMMON_WITHER, RitualRegistry.SUMMON_LIVING_PORTAL));

        private final int length;
        private final List<RitualTemplate> rituals;
        private final boolean preamble;

        TextChapter(int length) {
            this(length, List.of());
        }

        TextChapter(int length, List<RitualTemplate> rituals) {
            this(length, rituals, false);
        }
        TextChapter(int length, List<RitualTemplate> rituals, boolean preamble) {
            this.length = length;
            this.rituals = rituals;
            this.preamble = preamble;
        }

        private List<Element> makeChapter() {
            List<Element> blocks = new ArrayList<>();
            List<Element> elements = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                elements.add(Separators.smallSeparator(TEXT_BLOCK_WIDTH));
            }
            elements.add(new ChapterTitle(ordinal(), Component.translatable("research.thesis." + name().toLowerCase() + ".title").withStyle(Fonts.ACADEMIC_STYLE)));
            Font f = Minecraft.getInstance().font;
            if (preamble) {
                TextUtil util = new TextUtil();
                elements.addAll(util.setStyle(Fonts.ACADEMIC_STYLE).parseText(I18n.get("research.thesis." + name().toLowerCase() + ".preamble"), TEXT_BLOCK_WIDTH, f));
                for (int j = 0; j < 3; j++) {
                    elements.add(Separators.smallSeparator(TEXT_BLOCK_WIDTH));
                }
            }
            for (int i = 0; i < length; i++) {
                if(rituals.isEmpty()) {
                    elements.add(new SectionTitle(Component.translatable("research.thesis." + name().toLowerCase() + ".section." + (i + 1)).withStyle(Fonts.ACADEMIC_STYLE)));
                }
                TextUtil util = new TextUtil();
                elements.addAll(util.setStyle(Fonts.ACADEMIC_STYLE).parseText(I18n.get("research.thesis." + name().toLowerCase() + ".text." + (i + 1)), TEXT_BLOCK_WIDTH, f));
                if (i < length - 1) {
                    for (int j = 0; j < 3; j++) {
                        elements.add(Separators.smallSeparator(TEXT_BLOCK_WIDTH));
                    }

                }
            }
            int index = 0;
            while (index < elements.size()) {
                Tuple<TextBlock, Integer> tuple = TextBlock.fillBlockWithElements(elements, index, TEXT_BLOCK_WIDTH, TEXT_BLOCK_HEIGHT, f);
                blocks.add(tuple.getA());
                index = tuple.getB();
                //if (index < elements.size()) {
                //    List<Element> newElements = new ArrayList<>();
                //    for (int i = 0; i < 5; i++) {
                //        newElements.add(Separators.smallSeparator(TEXT_BLOCK_WIDTH));
                //    }
                //    newElements.addAll(elements);
                //    elements = newElements;
                //}
            }
            int i = 0;
            for (RitualTemplate ritual : rituals) {
                blocks.add(new RitualPage(ordinal(), i, ritual.getName()));
                blocks.add(new RitualPicture(ritual));
                i++;
            }
            return blocks;
        }

    }
}
