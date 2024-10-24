package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.gui.elements.DoubleTextPages;
import com.valeriotor.beyondtheveil.client.research.ResearchUtilClient;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.util.DataUtil;
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
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
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
    //private List<? extends Recipe<? extends Container>> selectedRecipeGroup;
    private RecipeType selectedRecipeType;
    private int recipeGroupX, recipeGroup1Y, recipeGroup2Y;
    private int[] recipeGroupYs = new int[0];
    private int gridX, gridY;//, recipePageWidth, recipePageHeight;

    private final ItemStack craftingTable = new ItemStack(Blocks.CRAFTING_TABLE);
    private final ItemStack gearBench = new ItemStack(Registration.GEAR_BENCH.get());
    private final ItemStack memorySieve = new ItemStack(Registration.MEMORY_SIEVE.get());

    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research_background.png");
    private static final ResourceLocation FRAME = new ResourceLocation(References.MODID, "textures/gui/research_frame.png");
    private static final ResourceLocation UNDERLINING_LEFT = new ResourceLocation(References.MODID, "textures/gui/research/underlining_left.png");
    private static final ResourceLocation UNDERLINING_RIGHT = new ResourceLocation(References.MODID, "textures/gui/research/underlining_right.png");
    public static final ResourceLocation LEFT_ARROW = new ResourceLocation(References.MODID, "textures/gui/research/left_arrow.png");
    public static final ResourceLocation RIGHT_ARROW = new ResourceLocation(References.MODID, "textures/gui/research/right_arrow.png");
    private static final ResourceLocation SELECTION1 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_selection_1.png");
    private static final ResourceLocation SELECTION2 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_selection_2.png");
    //private static final ResourceLocation RECIPE_BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research/research_page_recipe_background.png");
    //public static final ResourceLocation CIRCLE = new ResourceLocation(References.MODID, "textures/gui/recipe_circle.png");
    private int middleSpace;
    private int blackPageHeight;
    private int blackPageWidth;
    private final List<GearBenchRecipe> gearBenchRecipes = new ArrayList<>();
    private final List<CraftingRecipe> craftingRecipes = new ArrayList<>();
    private final List<Memory> memories = new ArrayList<>();
    private final List<RecipeType> recipeTypes = new ArrayList<>();
    private CraftingRegistryGui.CraftingGrid currentGrid;
    private int currentRecipeIndex;
    private ItemStack memoryIngredient;


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

        lineWidth = (blackPageWidth * 48 / 70) / 2;
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
        Object[] format = getFormatting();
        String localized = I18n.get(this.status.res.getStages()[this.status.getStage()].getTextKey(), getFormatting());
        //if (status.res.getKey().equals("FIRSTDREAMS") && status.getStage() == 2) {
        //    localized = localized.formatted(DataUtil.getBoolean(getMinecraft().player, PlayerDataLib.HELD_MEMORY_DREAM) ? "§cX§r" : " ", DataUtil.getBoolean(getMinecraft().player, PlayerDataLib.DRANK_MEMORY_DREAM) ? "§cX§r" : " ", DataUtil.getBoolean(getMinecraft().player, PlayerDataLib.REMINISCED.apply(Memory.METAL)) ? "§aV§r" : " ");
        //}
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
            resetRecipe(); // TODO test
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

        gearBenchRecipes.clear();
        craftingRecipes.clear();
        memories.clear();


        String[] recipes = status.res.getStages()[status.getStage()].getRecipes();
        for (String s : recipes) {
            String key = s.split(";")[0];
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(key));
            if (item != null) {
                Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(key));
                if (recipe.isPresent()) {
                    if (recipe.get() instanceof CraftingRecipe cr) {
                        craftingRecipes.add(cr);
                    } else if (recipe.get() instanceof GearBenchRecipe gbr) {
                        gearBenchRecipes.add(gbr);
                    }
                }
            }
        }
        memories.addAll(status.res.getStages()[status.getStage()].getMemories());

        craftingRecipes.sort(Comparator.comparing(r -> r.getResultItem(minecraft.level.registryAccess()).getItem().getDescription().getString()));
        gearBenchRecipes.sort(Comparator.comparing(r -> r.getResultItem(minecraft.level.registryAccess()).getItem().getDescription().getString()));
        memories.sort(Comparator.comparing(m -> m.getTranslationComponent().getString()));

        recipeTypes.clear();
        if (!craftingRecipes.isEmpty()) {
            recipeTypes.add(RecipeType.CRAFTING_TABLE);
        }
        if (!gearBenchRecipes.isEmpty()) {
            recipeTypes.add(RecipeType.GEAR_BENCH);
        }
        if (!memories.isEmpty()) {
            recipeTypes.add(RecipeType.MEMORY);
        }

        recipeGroupX = pageLeftX + 1420 * blackPageWidth / 1511;

        recipeGroupYs = new int[recipeTypes.size()];

        for (int i = 0; i < recipeGroupYs.length; i++) {
            recipeGroupYs[i] = pageTopY + (270 + 95 * i) * blackPageHeight / 1082;
        }

        recipeGroup1Y = pageTopY + 270 * blackPageHeight / 1082;
        recipeGroup2Y = pageTopY + 365 * blackPageHeight / 1082;

        //recipePageX = pageLeftX + 130 * blackPageWidth / 1511;
        //recipePageY = pageTopY + 50 * blackPageHeight / 1082;
        //recipePageWidth = 1200 * blackPageWidth / 1511;
        //recipePageHeight = 900 * blackPageHeight / 1082;

        gridX = pageLeftX + 200 * blackPageWidth / 1511;
        gridY = pageTopY + 230 * blackPageHeight / 1082;

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
    public void tick() {
        if (currentGrid != null) {
            currentGrid.tick();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack pose = guiGraphics.pose();
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

        renderMainPage(pose, guiGraphics, mouseX, mouseY);

        int craftingTableIndex = getCraftingTableIndex();
        int gearBenchIndex = getGearBenchIndex();

        for (int i = 0; i < recipeTypes.size(); i++) {
            RecipeType recipeType = recipeTypes.get(i);
            renderSelection(pose, guiGraphics, mouseX, mouseY, i, recipeType);
        }
        //renderSelection(pose, guiGraphics, mouseX, mouseY, gearBenchIndex, 2);

        if (currentGrid != null) {
            pose.pushPose();
            pose.translate(gridX, gridY, 0);
            int width1 = 1500 * blackPageWidth / 1511;
            float scaleFactor = width1 / 200F;
            pose.scale(scaleFactor, scaleFactor, 1);
            currentGrid.render(pose, guiGraphics, 0xFFFFFFFF, (int) ((mouseX - gridX) / scaleFactor), (int) ((mouseY - gridY) / scaleFactor));
            pose.popPose();
        } else if (memoryIngredient != null) {
            int width1 = 1500 * blackPageWidth / 1511;
            float scaleFactor = width1 / 200F;
            pose.pushPose();
            float fX = frameLeftX + frameWidth / 2F;
            pose.translate(fX, frameTopY + frameHeight * 0.55F, 0);
            pose.scale(scaleFactor * 2.75F, scaleFactor * 2.75F, 1);
            guiGraphics.renderItem(memorySieve, -8, -8);
            pose.popPose();

            pose.pushPose();
            pose.translate(fX, frameTopY + frameHeight * 0.37, 0);
            pose.scale(scaleFactor, scaleFactor, 1);
            guiGraphics.renderItem(memoryIngredient, -8, -8);

            double relativeMouseX = (mouseX - fX) / scaleFactor;
            double relativeMouseY = (mouseY - (frameTopY + frameHeight * 0.37)) / scaleFactor;
            if (relativeMouseX >= -8 && relativeMouseX <= 8 && relativeMouseY >= -8 && relativeMouseY <= 8) {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, memoryIngredient.getTooltipLines(Minecraft.getInstance().player, TooltipFlag.NORMAL), memoryIngredient.getTooltipImage(), (int) relativeMouseX, (int) relativeMouseY);
            }
            pose.popPose();
        }

        //if (selectedRecipeGroup != null) {
        //    RenderSystem.disableBlend();
        //    pose.pushPose();
        //    pose.translate(0, 0, 10);
        //    guiGraphics.blit(RECIPE_BACKGROUND, recipePageX, recipePageY, recipePageWidth, recipePageHeight, 0, 0, 1200, 900, 1200, 900);
        //    pose.popPose();
        //}


        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void renderMainPage(PoseStack pose, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (selectedRecipeType == null) {
            renderTitleUnderline(guiGraphics);
            renderTitle(pose, guiGraphics);
            renderPageText(pose, guiGraphics, mouseX, mouseY);
        }

        //guiGraphics.drawString(font, String.format("X: %d, Y: %d", mouseX, mouseY), 10, 10, 0xFFFFFFFF);

        if ((!pages2.isFirstScreen() && selectedRecipeType == null) || (selectedRecipeType != null && currentRecipeIndex > 0)) {
            renderArrow(pose, guiGraphics, mouseX, mouseY, true);
        }
        if ((!pages2.isLastScreen() && selectedRecipeType == null) || (selectedRecipeType != null && currentRecipeIndex < getRecipeListSize() - 1)) {
            renderArrow(pose, guiGraphics, mouseX, mouseY, false);
        }


        if (!progress.visible) {
            if (this.reqText != null) {
                int i = 0;
                for (String s : this.reqText) {
                    guiGraphics.drawCenteredString(font, s, width / 2, height / 2 + blackPageHeight * 35 / 100 + (i++) * 15, 0xFFFE9600);
                }
            }
        }
    }

    private void renderArrow(PoseStack pose, GuiGraphics guiGraphics, int mouseX, int mouseY, boolean left) {
        pose.pushPose();
        pose.translate(width / 2 + (- arrowXOffset - ARROW_WIDTH / 2) * (left ? 1 : -1), height / 2 + arrowYOffset + ARROW_HEIGHT / 2, 0);
        if ((hoveringLeftArrow(mouseX, mouseY) && left) || ((hoveringRightArrow(mouseX, mouseY) && !left))) {
            pose.scale(1.5F, 1.5F, 1);
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(left ? LEFT_ARROW : RIGHT_ARROW, -ARROW_WIDTH / 2, -ARROW_HEIGHT / 2, ARROW_WIDTH, ARROW_HEIGHT, 0, 0, 54, 53, 54, 53);
        pose.popPose();
    }

    private void renderTitle(PoseStack pose, GuiGraphics guiGraphics) {
        pose.pushPose();
        pose.translate(this.width / 2, this.height / 2 - blackPageHeight * 38 / 100, 0);
        pose.scale(1.5F, 1.5F, 1);
        guiGraphics.drawCenteredString(minecraft.font, title, 0, -10, 0xFFAAFFAA); // 10/54 to make it in proportion to the image size, 2/3 due to the scaling
        pose.popPose();
    }

    private void renderTitleUnderline(GuiGraphics guiGraphics) {
        int underlineTopY = height / 2 - blackPageHeight * 38 / 100;
        guiGraphics.fill(width / 2 - titleWidth / 2, underlineTopY, width / 2 + titleWidth / 2, underlineTopY + 3, 0x7F344234);
        guiGraphics.fill(width / 2 - titleWidth / 2, underlineTopY + 1, width / 2 + titleWidth / 2, underlineTopY + 2, 0xFF344234);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(UNDERLINING_LEFT, width / 2 - titleWidth / 2 - 20, underlineTopY, 20, 16, 0, 0, 20, 16, 20, 16);
        guiGraphics.blit(UNDERLINING_RIGHT, width / 2 + titleWidth / 2, underlineTopY, 20, 16, 0, 0, 20, 16, 20, 16);
    }

    private void renderPageText(PoseStack pose, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        pose.pushPose();
        int pX = width / 2 - lineWidth - middleSpace;
        int pY = height / 2 - blackPageHeight * 287 / 1000;
        pose.translate(pX, pY, 0);
        pages2.render(pose, guiGraphics, 0xFFFFFFFF, mouseX - pX, mouseY - pY);
        pose.popPose();
    }

    private int getGearBenchIndex() {
        return gearBenchRecipes.isEmpty() ? -1 : (craftingRecipes.isEmpty() ? 1 : 2);
    }

    private List<? extends Recipe<? extends Container>> getCraftingList() {
        return switch (selectedRecipeType) {
            case CRAFTING_TABLE -> craftingRecipes;
            case GEAR_BENCH -> gearBenchRecipes;
            default -> null;
        };
    }

    private int getRecipeListSize() {
        return switch (selectedRecipeType) {
            case CRAFTING_TABLE -> craftingRecipes.size();
            case GEAR_BENCH -> gearBenchRecipes.size();
            case MEMORY -> memories.size();
        };
    }

    private ItemStack getRecipeIcon(RecipeType recipeType) {
        return switch (recipeType) {
            case CRAFTING_TABLE -> craftingTable;
            case GEAR_BENCH -> gearBench;
            case MEMORY -> memorySieve;
        };
    }

    private int getCraftingTableIndex() {
        return craftingRecipes.isEmpty() ? -1 : 1;
    }

    private void renderSelection(PoseStack pose, GuiGraphics guiGraphics, int mouseX, int mouseY, int i, RecipeType recipeType) {
        // bad code today cause im tired
        if (i == -1) {
            return;
        }
        pose.pushPose();
        pose.translate(recipeGroupX, recipeGroupYs[i], 0);
        float factor = 2.2F * blackPageWidth / 1511;
        pose.scale(factor, factor, 1);
        guiGraphics.blit(selectedRecipeType == recipeType ? SELECTION2 : SELECTION1, -16, -16, 0, 0, 32, 32, 32, 32);
        if (hoveredSelection(mouseX, mouseY) == recipeType) {
            pose.scale(1.75F, 1.75F, 1);
        } else {
            pose.scale(1.25F, 1.25F, 1);
        }
        guiGraphics.renderItem(getRecipeIcon(recipeType), -8, -8);
        pose.popPose();
    }

    private RecipeType hoveredSelection(double mouseX, double mouseY) {
        if (mouseX < recipeGroupX - 16 || mouseX > recipeGroupX + 16) {
            return null;
        }
        for (int i = 0; i < recipeTypes.size(); i++) {
            if (mouseY >= recipeGroupYs[i] - 16 && mouseY <= recipeGroupYs[i] + 16) {
                return recipeTypes.get(i);
            }
        }
        return null;
    }

    //@Override
    //protected void actionPerformed(Button button) throws IOException {
    //    if(button.id == 0) {
    //        ResearchUtil.progressResearchClient(mc.player, status.res.getKey());
    //        this.initGui();
    //    }
    //}

    private void makeGrid() {
        Recipe<? extends Container> recipe = getCraftingList().get(currentRecipeIndex);
        int width1 = 1500 * blackPageWidth / 1511;
        float scaleFactor = width1 / 125F;
        currentGrid = new CraftingRegistryGui.CraftingGrid(width1, 300, recipe, recipe.getResultItem(minecraft.level.registryAccess()).getItem(), scaleFactor);
    }

    private void makeMemory() {
        memoryIngredient = memories.get(currentRecipeIndex).getItem();
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (super.mouseClicked(mouseX, mouseY, mouseButton))
            return true;
        //int a = this.hoveringRecipeKey(mouseX, mouseY);
        if (hoveringLeftArrow(mouseX, mouseY)) {
            leftArrowClick();
            return true;
        } else if (hoveringRightArrow(mouseX, mouseY)) {
            rightArrowClick();
            return true;
        } else {
            RecipeType recipeType = hoveredSelection(mouseX, mouseY);
            if (recipeType != null) {
                if (recipeType != selectedRecipeType) {
                    selectedRecipeType = recipeType;
                    currentRecipeIndex = 0;
                    if (recipeType.grid) {
                        makeGrid();
                        memoryIngredient = null;
                    } else {
                        currentGrid = null;
                        makeMemory();
                    }
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                } else {
                    resetRecipe();
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                }
            }
        }


        /*else if (hoveringSelection(mouseX, mouseY, 1)) {
            selectedRecipeGroup = getCraftingTableIndex() == 1 ? craftingRecipes : gearBenchRecipes;
            currentRecipeIndex = 0;
            makeGrid();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
        } else if (hoveringSelection(mouseX, mouseY, 2)) {
            selectedRecipeGroup = gearBenchRecipes;
            currentRecipeIndex = 0;
            makeGrid();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
        } else if (hoveringRecipeX(mouseX, mouseY)) {
            selectedRecipeGroup = null;
            currentGrid = null;
        }*/
        return false;
        //} else if(a != -1){
        //    this.shownRecipe = recipes.get(a);
        //} else if(this.shownRecipe == null || !this.shownRecipe.mouseClicked(this, mouseX, mouseY, mouseButton))
        //    this.shownRecipe = null;
    }

    private void resetRecipe() {
        selectedRecipeType = null;
        currentGrid = null;
        memoryIngredient = null;
    }

    private void leftArrowClick() {
        if (selectedRecipeType == null) {
            if (!pages2.isFirstScreen()) {
                pages2.previousScreen();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
            }
        } else {
            if (currentRecipeIndex > 0) {
                currentRecipeIndex = currentRecipeIndex - 1;
                if (selectedRecipeType.grid) {
                    makeGrid();
                } else {
                    makeMemory();
                }
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
            }
        }
    }

    private void rightArrowClick() {
        if (selectedRecipeType == null) {
            if (!pages2.isLastScreen()) {
                pages2.nextScreen();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
            }
        } else {
            if (currentRecipeIndex < getRecipeListSize() - 1) {
                currentRecipeIndex = currentRecipeIndex + 1;
                if (selectedRecipeType.grid) {
                    makeGrid();
                } else {
                    makeMemory();
                }
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 2));
            }
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) { // TODO
        //if(keyCode == 18)
        //if(this.shownRecipe == null)
        InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            if (selectedRecipeType != null) {
                resetRecipe();
            } else {
                minecraft.setScreen(new NecronomiconGui());
            }
        } else if (this.minecraft.options.keyLeft.matches(pKeyCode, pScanCode)) {
            leftArrowClick();
        } else if (this.minecraft.options.keyRight.matches(pKeyCode, pScanCode)) {
            rightArrowClick();
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

    private Object[] getFormatting() {
        if(status.res.getKey().equals("FIRSTDREAMS") && status.getStage() == 2)
            return new Object[]{DataUtil.getBoolean(getMinecraft().player, PlayerDataLib.HELD_MEMORY_DREAM) ? "§cX§r" : " ", DataUtil.getBoolean(getMinecraft().player, PlayerDataLib.DRANK_MEMORY_DREAM) ? "§cX§r" : " ", DataUtil.getBoolean(getMinecraft().player, PlayerDataLib.REMINISCED.apply(Memory.METAL)) ? "§aV§r" : " "};
        return new Object[0];
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

    private enum RecipeType {
        CRAFTING_TABLE(true), GEAR_BENCH(true), MEMORY(false);

        private final boolean grid;

        RecipeType(boolean grid) {
            this.grid = grid;
        }
    }

}
