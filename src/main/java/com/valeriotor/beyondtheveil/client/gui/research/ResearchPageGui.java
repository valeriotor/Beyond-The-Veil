package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.DoubleTextPages;
import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchPageGui extends Screen {
    private final int ARROW_WIDTH = 16;
    private final int ARROW_HEIGHT = 16;
    private int arrowXOffset;
    private int arrowYOffset;

    private final ResearchStatus status;
    private final String title;
    private int titleWidth;
    private Font titleFont;
    private Font textFont;
    private List<List<FormattedCharSequence>> pages = new ArrayList<>();
    private DoubleTextPages pages2;
    private List<String> reqText;
    private Button progress;
    //private List<ResearchRecipe> recipes = new ArrayList<>();
    //private ResearchRecipe shownRecipe;
    private int page = 0;
    private int lineWidth;
    private int lineStart;
    private int pageHeight;
    private int pageLeftX;
    private int pageTopY;
    private int pageRightX;
    private int pageBottomY;

    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research_background.png");
    private static final ResourceLocation FRAME = new ResourceLocation(References.MODID, "textures/gui/research_frame.png");
    private static final ResourceLocation UNDERLINING_LEFT = new ResourceLocation(References.MODID, "textures/gui/research/underlining_left.png");
    private static final ResourceLocation UNDERLINING_RIGHT = new ResourceLocation(References.MODID, "textures/gui/research/underlining_right.png");
    public static final ResourceLocation LEFT_ARROW = new ResourceLocation(References.MODID, "textures/gui/research/left_arrow.png");
    public static final ResourceLocation RIGHT_ARROW = new ResourceLocation(References.MODID, "textures/gui/research/right_arrow.png");
    public static final ResourceLocation CIRCLE = new ResourceLocation(References.MODID, "textures/gui/recipe_circle.png");
    private int middleSpace;
    private int blackPageHeight;
    private int blackPageWidth;


    public ResearchPageGui(ResearchStatus status) {
        super(Component.translatable(status.res.getName()));
        this.status = status;
        title = Component.translatable(status.res.getName()).getString();
    }

    @Override
    public void init() {

        int blackPageMargin = Math.min(200 * width / 1400 + 75, 300);
        pageTopY = height * blackPageMargin / 1440;
        pageBottomY = (height * (1440 - blackPageMargin)) / 1440;
        this.blackPageHeight = pageBottomY - pageTopY;
        this.blackPageWidth = blackPageHeight * 1511 / 1082;
        pageLeftX = width / 2 - blackPageWidth / 2;
        pageRightX = width / 2 + blackPageWidth / 2;
        if (pageLeftX < 20) {
            pageLeftX = 20;
            pageRightX = width - 20;
            blackPageWidth = width - 40;
            blackPageHeight = blackPageWidth * 1082 / 1511;
            pageTopY = height / 2 - blackPageHeight / 2;
            pageBottomY = height / 2 + blackPageHeight / 2;
        }

        lineWidth = (blackPageWidth * 5 / 7) / 2;
        middleSpace = (blackPageWidth / 10) / 2;
        pageHeight = blackPageHeight * 6 / 10;

        titleFont = minecraft.font;
        textFont = minecraft.font;

        titleWidth = Math.min(titleFont.width(title) * 2, (pageRightX - pageLeftX) * 9 / 10);

        arrowXOffset = (blackPageWidth * 52 / 70) / 2;
        arrowYOffset = blackPageHeight * 17 / 50;

        this.page = 0;
        pages.clear();
        //recipes.clear();
        //shownRecipe = null;
        String localized = I18n.get(this.status.res.getStages()[this.status.getStage()].getTextKey());
        //localized = "It feels {natural to dismiss dreams}[caption:test] as a trick of the mind, when such §rdismissal could itself be the trick.\\nThe sensation of travelling to and from the Nether felt familiar, and yet I have no recollection of any similar journey in my life. It left me with the intense feeling of plunging down, gods know how far into stone and bedrock, only to then rise right back up. A feeling I have often experienced in dreams.\\nCould I be underestimating their import? Are they born from my daily sensations, or could they tell me something §omore§r, about the world and about myself? \\nThe desire to understand swells within me. Dreams are such simple, everyday events whose true nature I never bothered to investigate, like a shallow-looking puddle whose bottom I never chose to touch. \\nBut what if it hid an ocean?";
        //TextUtil util = new TextUtil();
        //util.parseText2(localized2, lineWidth, Minecraft.getInstance().font);

        pages2 = DoubleTextPages.makePages(localized, lineWidth * 2 + middleSpace * 2, pageHeight, lineWidth, Minecraft.getInstance().font);
        // TODO ADDENDA
        //int bHeight = this.height / 2 + (mc.gameSettings.guiScale == 3 || minecraft.gameSettings.guiScale == 0 ? 90 : 130) - 5;
        if (progress != null) {
            removeWidget(progress);
        }

        Button b = Button.builder(Component.translatable("gui.research_page.complete"), button -> {
            ResearchUtilClient.progressResearchClientAndSync(status.res.getKey());
            init();
        }).bounds(width / 2 - 60, height / 2 + blackPageHeight * 35 / 100, 120, 20).build();
        progress = addRenderableWidget(b);
        if (!status.canProgressStage(minecraft.player)) {
            progress.visible = false;
            String[] reqs = status.res.getStages()[this.status.getStage()].getRequirements();
            if (reqs != null)
                this.reqText = Arrays.stream(reqs)
                        .map(s -> "research.".concat(s).concat(".text"))
                        .map(I18n::get)
                        .collect(Collectors.toList());
        }

    }

    //private void makeRecipes(String[] recipes) {
    //    for(String s : recipes) {
    //        ResearchRecipe r = ResearchRecipe.getRecipe(s);
    //        if(r != null) {
    //            this.recipes.add(r);
    //        }
    //    }
    //}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack pPoseStack = guiGraphics.pose();
        guiGraphics.fill(0, 0, width, height, 0xFF000000);
        //RenderSystem.setShaderColor(1, 1, 1, 1);
        if (width < 2560 && height < 1440) {
            guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, width, height, 2560, 1440);
        } else {
            guiGraphics.blit(BACKGROUND, 0, 0, width, height, 0, 0, 2560, 1440, 2560, 1440);
        }

        guiGraphics.fill(pageLeftX, pageTopY, pageRightX, pageBottomY, 0x99000000);

        int frameLeftX = pageLeftX - (pageRightX - pageLeftX) * 45 / 1421;
        int frameTopY = pageTopY - (pageBottomY - pageTopY) * 45 / 992;
        int frameWidth = (pageRightX - pageLeftX) * 1511 / 1421;
        int frameHeight = (pageBottomY - pageTopY) * 1082 / 992;
        guiGraphics.blit(FRAME, frameLeftX, frameTopY, frameWidth, frameHeight, 0, 0, 1511, 1082, 1511, 1082);

        int underlineTopY = height / 2 - blackPageHeight * 38 / 100 ;
        guiGraphics.fill(width / 2 - titleWidth / 2, underlineTopY, width / 2 + titleWidth / 2, underlineTopY + 3, 0x7F344234);
        guiGraphics.fill(width / 2 - titleWidth / 2, underlineTopY + 1, width / 2 + titleWidth / 2, underlineTopY + 2, 0xFF344234);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(UNDERLINING_LEFT, width / 2 - titleWidth / 2 - 20, underlineTopY, 20, 16, 0, 0, 20, 16, 20, 16);
        guiGraphics.blit(UNDERLINING_RIGHT, width / 2 + titleWidth / 2, underlineTopY, 20, 16, 0, 0, 20, 16, 20, 16);

        guiGraphics.drawString(font, String.format("X: %d, Y: %d", mouseX, mouseY), 10, 10, 0xFFFFFFFF);

        pPoseStack.pushPose();
        int pX = width / 2 - lineWidth - middleSpace;
        int pY = height / 2 - blackPageHeight * 287 / 1000;
        pPoseStack.translate(pX, pY, 0);
        pages2.render(pPoseStack, guiGraphics, 0xFFFFFFFF, mouseX - pX, mouseY - pY);
        pPoseStack.popPose();


        if (!pages2.isFirstScreen()) {
            pPoseStack.pushPose();
            pPoseStack.translate(width / 2 - arrowXOffset - ARROW_WIDTH / 2, height / 2 + arrowYOffset + ARROW_HEIGHT / 2, 0);
            if (hoveringLeftArrow(mouseX, mouseY)) {
                pPoseStack.scale(1.5F, 1.5F, 1);
            }
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            guiGraphics.blit(LEFT_ARROW, -ARROW_WIDTH / 2, -ARROW_HEIGHT / 2, ARROW_WIDTH, ARROW_HEIGHT, 0, 0, 54, 53, 54, 53);
            pPoseStack.popPose();
        }
        if (!pages2.isLastScreen()) {
            pPoseStack.pushPose();
            pPoseStack.translate(width / 2 + arrowXOffset + ARROW_WIDTH / 2, height / 2 + arrowYOffset + ARROW_HEIGHT / 2, 0);
            if (hoveringRightArrow(mouseX, mouseY)) {
                pPoseStack.scale(1.5F, 1.5F, 1);
            }
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            guiGraphics.blit(RIGHT_ARROW, -ARROW_WIDTH / 2, -ARROW_HEIGHT / 2, ARROW_WIDTH, ARROW_HEIGHT, 0, 0, 54, 53, 54, 53);
            pPoseStack.popPose();
        }

        pPoseStack.pushPose();
        pPoseStack.translate(this.width / 2, this.height / 2 - blackPageHeight * 38 / 100, 0);
        pPoseStack.scale(1.5F, 1.5F, 1);
        guiGraphics.drawCenteredString(minecraft.font, title, 0, -10, 0xFFAAFFAA); // 10/54 to make it in proportion to the image size, 2/3 due to the scaling
        pPoseStack.popPose();

        if (!progress.visible) {
            if (this.reqText != null) {
                int i = 0;
                for (String s : this.reqText) {
                    guiGraphics.drawCenteredString(font, s, width / 2, height / 2 + blackPageHeight * 35 / 100 + (i++) * 15, 0xFFFE9600);
                }
            }
        }

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    //@Override
    //protected void actionPerformed(Button button) throws IOException {
    //    if(button.id == 0) {
    //        ResearchUtil.progressResearchClient(mc.player, status.res.getKey());
    //        this.initGui();
    //    }
    //}


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (super.mouseClicked(mouseX, mouseY, mouseButton))
            return true;
        //int a = this.hoveringRecipeKey(mouseX, mouseY);
        if (hoveringLeftArrow(mouseX, mouseY) && !pages2.isFirstScreen()) {
            pages2.previousScreen();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
            return true;
        } else if (hoveringRightArrow(mouseX, mouseY) && !pages2.isLastScreen()) {
            pages2.nextScreen();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
            return true;
        }
        return false;
        //} else if(a != -1){
        //    this.shownRecipe = recipes.get(a);
        //} else if(this.shownRecipe == null || !this.shownRecipe.mouseClicked(this, mouseX, mouseY, mouseButton))
        //    this.shownRecipe = null;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) { // TODO
        //if(keyCode == 18)
        //if(this.shownRecipe == null)
        InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            minecraft.setScreen(new NecronomiconGui());
        } else if (this.minecraft.options.keyLeft.matches(pKeyCode, pScanCode) && !pages2.isFirstScreen()) {
            pages2.previousScreen();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
        } else if (this.minecraft.options.keyRight.matches(pKeyCode, pScanCode) && !pages2.isLastScreen()) {
            pages2.nextScreen();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
        }
        //else
//                this.shownRecipe = null;
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    private boolean hoveringLeftArrow(double mouseX, double mouseY) {
        int rightX = this.width / 2 - arrowXOffset;
        int topY = this.height / 2 + arrowYOffset;
        return mouseX >= rightX - ARROW_WIDTH && mouseX <= rightX && mouseY >= topY && mouseY < topY + ARROW_HEIGHT;
    }

    private boolean hoveringRightArrow(double mouseX, double mouseY) {
        int leftX = this.width / 2 + arrowXOffset;
        int topY = this.height / 2 + arrowYOffset;
        return mouseX >= leftX && mouseX <= leftX + ARROW_WIDTH && mouseY >= topY && mouseY <= topY + ARROW_HEIGHT;
    }


    //private int hoveringRecipeKey(int mouseX, int mouseY) {
    //    mouseX -= this.width / 2;
    //    mouseY -= this.height / 2;
    //    if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0) {
    //        mouseX = mouseX * 4 / 3;
    //        mouseY = mouseY * 4 / 3;
    //    }
    //    if(mouseY > 125 && mouseY < 141 && mouseX >= -160) {
    //        int a = (mouseX + 160) / 25;
    //        if(a < 6 && a < recipes.size() && a >= 0)
    //            return a;
    //    }
    //    return -1;
    //}

    //@Override
    //public RenderItem getItemRender() {
    //    return this.itemRender;
    //}
//
    //@Override
    //public void renderTooltip(ItemStack stack, int x, int y) {
    //    this.renderToolTip(stack, x, y);
    //}
//
    //@Override
    //public void updateScreen() {
    //    if(this.shownRecipe != null)
    //        this.shownRecipe.update();
    //}

}
