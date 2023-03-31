package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.valeriotor.beyondtheveil.capability.PlayerData;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.MathHelperBTV;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchPageGui extends Screen {

    private final ResearchStatus status;
    private final String title;
    private List<List<FormattedCharSequence>> pages = new ArrayList<>();
    private List<String> reqText;
    private Button progress;
    //private List<ResearchRecipe> recipes = new ArrayList<>();
    //private ResearchRecipe shownRecipe;
    private int page = 0;
    private int backgroundWidth;
    private int backgroundHeight;
    private int lineWidth;
    private int lineStart;
    private int pageHeight;

    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/res_page_small.png");
    public static final ResourceLocation ARROW = new ResourceLocation(References.MODID, "textures/gui/right_arrow.png");
    public static final ResourceLocation CIRCLE = new ResourceLocation(References.MODID, "textures/gui/recipe_circle.png");

    public ResearchPageGui(ResearchStatus status) {
        super(new TranslatableComponent(status.res.getName()));
        this.status = status;
        title = new TranslatableComponent(status.res.getName()).getString();
    }

    @Override
    public void init() {

        if (width * 54 >= height * 67) {
            backgroundHeight = height;
            backgroundWidth = height * 67 / 54;
        } else {
            backgroundWidth = width;
            backgroundHeight = width * 54 / 67;
        }
        lineWidth = 19 * backgroundWidth / 67;
        lineStart = lineWidth + backgroundWidth * 3 / 134;
        pageHeight = 21 * backgroundWidth / 67;

        this.page = 0;
        pages.clear();
        //recipes.clear();
        //shownRecipe = null;
        String[] pages = I18n.get(this.status.res.getStages()[this.status.getStage()].getTextKey()).split("<PAGE>");
        this.formatText(pages);
        //this.makeRecipes(this.status.res.getStages()[this.status.getStage()].getRecipes());
        PlayerData data = minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA, null).orElse(PlayerData.DUMMY);
        if (this.status.isComplete()) {
            for (Research.SubResearch sr : this.status.res.getAddenda()) {
                if (sr.meetsRequirements(data)) {
                    pages = I18n.get(sr.getTextKey()).split("<PAGE>");
                    this.formatText(pages);
                    //this.makeRecipes(sr.getRecipes());
                }
            }
        }
        //int bHeight = this.height / 2 + (mc.gameSettings.guiScale == 3 || minecraft.gameSettings.guiScale == 0 ? 90 : 130) - 5;
        progress = addRenderableWidget(new Button(width / 2 - 60, height / 4, 120, 20, new TranslatableComponent("gui.research_page.complete"), button -> {
            ResearchUtilClient.progressResearchClientAndSync(status.res.getKey());
            init();
        }));
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

    private void formatText(String[] pages) {
        List<FormattedCharSequence> ls;
        int i = 0;
        this.pages.add(new ArrayList<>());
        for (int k = 0; k < pages.length; k++) {
            String s0 = pages[k];
            i = 0;
            String[] paragraphs = s0.split("<BR>");
            for (String s : paragraphs) {
                ls = GuiHelper.splitStringsByWidth(s, lineWidth, minecraft.font);
                for (FormattedCharSequence ss : ls) {
                    if (i > pageHeight) {
                        i = 0;
                        this.pages.add(new ArrayList<>());
                    }
                    this.pages.get(this.pages.size() - 1).add(ss);
                    i += 15;
                }
                i += 15;
                this.pages.get(this.pages.size() - 1).add(FormattedCharSequence.EMPTY);
            }
            if (k < pages.length - 1)
                this.pages.add(new ArrayList<>());
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
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float partialTicks) {
        fill(pPoseStack, 0, 0, width, height, 0xFF000000);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        RenderSystem.setShaderTexture(0, BACKGROUND);

        pPoseStack.pushPose();
        pPoseStack.translate(this.width / 2, this.height / 2, 0);
        //if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0) { TODO check gui scales
        //    GlStateManager.scale(0.75, 0.75, 0.75);
        //} else {
//
        //}
        blit(pPoseStack, -backgroundWidth / 2, -backgroundHeight / 2, 0, 0, backgroundWidth, backgroundHeight, backgroundWidth, backgroundHeight);
        drawString(pPoseStack, font, String.format("X: %d, Y: %d", width, height), -width/2+10, -height/2+10, 0xFFFFFFFF);

        if (true /*this.shownRecipe == null*/) {
            pPoseStack.pushPose();
            pPoseStack.scale(1.5F, 1.5F, 1);
            drawCenteredString(pPoseStack, minecraft.font, title, 0, -backgroundHeight * 10 / 54, 0xFFAAFFAA); // 10/54 to make it in proportion to the image size, 2/3 due to the scaling
            pPoseStack.popPose();
            if (this.pages.size() > this.page * 2) {
                int i = 0;
                for (FormattedCharSequence s : this.pages.get(this.page * 2)) {
                    drawString(pPoseStack, minecraft.font, s, -lineStart, -backgroundHeight * 11 / 54 + (i++) * 15, 0xFFFFFFFF);
                }
            }
            if (this.pages.size() > this.page * 2 + 1) {
                int i = 0;
                for (FormattedCharSequence s : this.pages.get(this.page * 2 + 1)) {
                    drawString(pPoseStack, minecraft.font, s, backgroundWidth / 134, -backgroundHeight * 11 / 54 + (i++) * 15, 0xFFFFFFFF);
                }
            }
            if (!progress.visible) {
                if (this.reqText != null) {
                    int i = 0;
                    for (String s : this.reqText) {
                        drawCenteredString(pPoseStack, font, s, 0, backgroundHeight * 13 / 54 + (i++) * 15, 0xFFFE9600);
                    }
                }
            }
            if (this.pages.size() > 2) {
                RenderSystem.setShaderTexture(0, ARROW);
                pPoseStack.pushPose();
                pPoseStack.translate(178 * backgroundWidth / 670, 131 * backgroundWidth / 670, 0);
                if (this.page < (this.pages.size() + 1) / 2 - 1) {
                    if (hoveringRightArrow(mouseX, mouseY))
                        pPoseStack.scale(1.5F, 1.5F, 1);
                    blit(pPoseStack, -16, -16, 0, 0, 32, 32, 32, 32);
                }
                pPoseStack.popPose();
                pPoseStack.pushPose();
                RenderSystem.setShaderColor(1, 1, 1, 1);
                pPoseStack.translate(-178*backgroundWidth/670, 131*backgroundWidth/670, 0);
                pPoseStack.mulPose(Vector3f.ZP.rotation((float) Math.PI));
                if (this.page > 0) {
                    if (hoveringLeftArrow(mouseX, mouseY))
                        pPoseStack.scale(1.5F, 1.5F, 1);
                    blit(pPoseStack, -16, -16, 0, 0, 32, 32, 32, 32);
                }
                pPoseStack.popPose();
            }
        } else {
            //shownRecipe.render(this, mouseX, mouseY);
        }
        //int hoveredKey = this.hoveringRecipeKey(mouseX, mouseY);
        /*for(int i = 0; i < 6 && i < recipes.size(); i++) {

            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.pushMatrix();
            GlStateManager.translate(-160 + i * 25, 125, 0);
            if(hoveredKey == i) {
                GlStateManager.scale(2, 2, 2);
            }
//            itemRenderer.renderGuiItem(icons[counter % 20 % icons.length], resX - topX, resY - topY);

            recipes.get(i).renderKey(this);
            GlStateManager.popMatrix();
            mc.renderEngine.bindTexture(CIRCLE);
            drawModalRectWithCustomSizedTexture(-164 + i *25, 121, 0, 0, 25, 25, 25, 25);
        }*/

        pPoseStack.popPose();
        //if(hoveredKey != -1) {
        //    recipes.get(hoveredKey).renderTooltip(this, mouseX + 20, mouseY + 10);
        //}
        super.render(pPoseStack, mouseX, mouseY, partialTicks);
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
        if(hoveringLeftArrow(mouseX, mouseY)) {
            this.page = MathHelperBTV.clamp(0, (this.pages.size() + 1) / 2 - 1, this.page - 1);
            return true;
        } else if(hoveringRightArrow(mouseX, mouseY)) {
            this.page = MathHelperBTV.clamp(0, (this.pages.size() + 1) / 2 - 1, this.page + 1);
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
        minecraft.setScreen(new NecronomiconGui());
        //else
//                this.shownRecipe = null;
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    /*@Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 18)
            if(this.shownRecipe == null)
                this.mc.displayGuiScreen(new GuiNecronomicon());
            else
                this.shownRecipe = null;
        super.keyTyped(typedChar, keyCode);
    }*/

    private boolean hoveringLeftArrow(double mouseX, double mouseY) {
        //if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0)
        //    return mouseX > this.width / 2 - 182 * 3/4 && mouseX < this.width / 2 - 150 * 3/4 && mouseY > this.height / 2 + 115 * 3/4 && mouseY < this.height / 2 + (115 + 32) * 3/4;
        return mouseX > this.width / 2 - adjustToGuiScale(194) && mouseX < this.width / 2 - adjustToGuiScale(162) && mouseY > this.height / 2 + adjustToGuiScale(115) && mouseY < this.height / 2 + adjustToGuiScale(115 + 32);
    }

    private boolean hoveringRightArrow(double mouseX, double mouseY) {
        //if(this.mc.gameSettings.guiScale == 3 || this.mc.gameSettings.guiScale == 0)
        //    return mouseX > this.width / 2 + 150 * 3/4 && mouseX < this.width / 2 + 182 * 3/4 && mouseY > this.height / 2 + 115 * 3/4 && mouseY < this.height / 2 + (115 + 32) * 3/4;
        return mouseX > this.width / 2 + adjustToGuiScale(162) && mouseX < this.width / 2 + adjustToGuiScale(194) && mouseY > this.height / 2 + adjustToGuiScale(115) && mouseY < this.height / 2 + adjustToGuiScale(115 + 32);
    }

    private int adjustToGuiScale(int original) {
        return original*backgroundWidth/670;
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
