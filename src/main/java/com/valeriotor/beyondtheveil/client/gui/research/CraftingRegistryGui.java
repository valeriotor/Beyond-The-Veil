package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.ClientMethods;
import com.valeriotor.beyondtheveil.client.gui.elements.*;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CraftingRegistryGui extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_background.png");
    private static final ResourceLocation PAGE = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_page.png");
    private static final ResourceLocation ENTRY = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_entry.png");
    private static final ResourceLocation SELECTION1 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_selection_1.png");
    private static final ResourceLocation SELECTION2 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_selection_2.png");
    private static final ResourceLocation GRID3 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_grid_3.png");
    private static final ResourceLocation GRID4 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_grid_4.png");
    private static final ResourceLocation GRID_ARROW = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_grid_arrow.png");


    private int pageLeftX;
    private int pageTopY;
    private int pageRightX;
    private int pageBottomY;
    private int pageHeight;
    private int pageWidth;
    private int entryListWidth;
    private int entryListHeight;
    private int entryListStart;
    private int scrollbarWidth;
    private ScrollableList<CraftingEntry> itemList;
    private ScrollableList<CraftingEntry> fullList;
    private int listLeftX, listLeftY;
    private int titleX, titleY;
    private int textBlockX, textBlockY;
    private int gridX, gridY, gridWidth;
    private float gridFactor;
    private int CTCategoryX, CTCategoryY, GBCategoryX, GBCategoryY;
    private boolean CTCategory = true, GBCategory;
    private final ItemStack craftingTable = new ItemStack(Blocks.CRAFTING_TABLE);
    private final ItemStack gearBench = new ItemStack(Registration.GEAR_BENCH.get());
    private final List<List<CraftingRecipe>> craftingTableRecipes;
    private final List<List<GearBenchRecipe>> gearBenchRecipes;
    private final Map<String, Research> knownRecipesByName;
    private Component selectedTitle;
    private TextBlock selectedText;
    private CraftingGrid grid;
    private CraftingEntry selectedEntry;
    private List<? extends Recipe<?>> currentSelectionRecipes = new ArrayList<>();
    private int arrowXOffset;
    private int arrowYOffset;
    private final int ARROW_WIDTH = 16;
    private final int ARROW_HEIGHT = 16;
    private int gridIndex;
    private Item currentSelectionItem;
    private int craftingGridWidth;
    private static final float ARROW_DISCOLOR = 0.35F;


    public CraftingRegistryGui(ResearchStatus status) {
        super(Component.translatable(status.res.getName()));
        knownRecipesByName = ResearchUtil.getKnownRecipes(Minecraft.getInstance().player);
        craftingTableRecipes = new ArrayList<>();
        gearBenchRecipes = new ArrayList<>();
        for (String s : knownRecipesByName.keySet()) {
            String key = s.split(";")[0];
            List<? extends Recipe<?>> optionals = ClientMethods.recipesForModItem(key).stream().filter(Optional::isPresent).map(Optional::get).toList();
            if (!optionals.isEmpty()) {
                boolean flag1 = false, flag2 = false;
                for (Recipe<?> recipe : optionals) {
                    if (recipe instanceof CraftingRecipe cr && !flag1) {
                        craftingTableRecipes.add(new ArrayList<>());
                        flag1 = true;
                    } else if (recipe instanceof GearBenchRecipe gbr && !flag2) {
                        gearBenchRecipes.add(new ArrayList<>());
                        flag2 = true;
                    }
                }
                for (Recipe<?> recipe : optionals) {
                    if (recipe instanceof CraftingRecipe cr) {
                        craftingTableRecipes.get(craftingTableRecipes.size() - 1).add(cr);
                    } else if (recipe instanceof GearBenchRecipe gbr) {
                        gearBenchRecipes.get(gearBenchRecipes.size() - 1).add(gbr);
                    }
                }
            }
        }
        GBCategory = !gearBenchRecipes.isEmpty();
    }

    @Override
    protected void init() {
        int blackPageMargin = Math.min(100 * width / 1400, 200);
        pageTopY = height * blackPageMargin / 1440;
        pageBottomY = (height * (1440 - blackPageMargin)) / 1440;
        this.pageHeight = pageBottomY - pageTopY;
        this.pageWidth = pageHeight * 1577 / 1261;
        pageLeftX = width / 2 - pageWidth / 2;
        pageRightX = width / 2 + pageWidth / 2;
        if (pageLeftX < 20) {
            pageLeftX = 20;
            pageRightX = width - 20;
            pageWidth = width - 40;
            pageHeight = pageWidth * 1261 / 1577;
            pageTopY = height / 2 - pageHeight / 2;
            pageBottomY = height / 2 + pageHeight / 2;
        }

        entryListWidth = 489 * pageWidth / 1577;
        entryListHeight = 839 * pageHeight / 1261;
        entryListStart = 44 * pageWidth / 1577;
        scrollbarWidth = 20 * pageWidth / 1577;

        listLeftX = pageLeftX + 44 * pageWidth / 1577;
        listLeftY = pageTopY + 381 * pageHeight / 1261;

        itemList = initList();
        fullList = initList(true);

        if (gearBenchRecipes.isEmpty()) {
            CTCategoryX = pageLeftX + 290 * pageWidth / 1577;
            CTCategoryY = pageTopY + 180 * pageHeight / 1261;
        } else {
            CTCategoryX = pageLeftX + 190 * pageWidth / 1577;
            CTCategoryY = pageTopY + 180 * pageHeight / 1261;
            GBCategoryX = pageLeftX + 390 * pageWidth / 1577;
            GBCategoryY = pageTopY + 180 * pageHeight / 1261;
        }

        titleX = pageLeftX + (555 + 489) * pageWidth / 1577;
        titleY = pageTopY + 100 * pageHeight / 1261;
        textBlockX = pageLeftX + 670 * pageWidth / 1577;
        textBlockY = pageTopY + 220 * pageHeight / 1261;
        gridX = pageLeftX + (556 + 70) * pageWidth / 1577;
        gridY = pageTopY + 651 * pageHeight / 1261;
        gridWidth = 949 * pageWidth / 1577;
        gridFactor = gridWidth / 200F;

        if (selectedEntry != null) {
            selectEntry(selectedEntry.stack.getItem(), selectedEntry.recipes);
        }

        arrowXOffset = (gridX + gridWidth / 2);
        arrowYOffset = 1150 * pageWidth / 1577;
        craftingGridWidth = 800 * pageWidth / 1577;
    }

    private ScrollableList<CraftingEntry> initList() {
        return initList(false);
    }

    private ScrollableList<CraftingEntry> initList(boolean full) {
        List<CraftingEntry> entries = new ArrayList<>();
        Map<Item, List<Recipe<?>>> map = new HashMap<>();
        if (CTCategory) {
            for (List<CraftingRecipe> craftingTableRecipes1 : this.craftingTableRecipes) {
                if (!craftingTableRecipes1.isEmpty()) {
                    Item result = craftingTableRecipes1.get(0).getResultItem(minecraft.level.registryAccess()).getItem();
                    map.computeIfAbsent(result, i -> new ArrayList<>()).addAll(craftingTableRecipes1);
                }
            }
        }
        if (GBCategory) {
            for (List<GearBenchRecipe> gearBenchRecipes1 : this.gearBenchRecipes) {
                if (!gearBenchRecipes1.isEmpty()) {
                    Item result = gearBenchRecipes1.get(0).getResultItem(minecraft.level.registryAccess()).getItem();
                    map.computeIfAbsent(result, i -> new ArrayList<>()).addAll(gearBenchRecipes1);
                }
            }
        }
        for (Map.Entry<Item, List<Recipe<?>>> itemListEntry : map.entrySet()) {
            entries.add(new CraftingEntry(itemListEntry.getKey(), itemListEntry.getValue()));
        }
        entries.sort(Comparator.comparing(e -> e.stack.getItem().getDescription().getString()));
        return new ScrollableList<>(entryListWidth, entryListHeight, entries, entryListHeight / 6, scrollbarWidth);
    }

    @Override
    public void tick() {
        if (grid != null) {
            grid.tick();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

        PoseStack pose = guiGraphics.pose();
        //guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, width, height, 2560, 1440);
        guiGraphics.blit(BACKGROUND, 0, 0, width, height, 0, 0, 2560, 1440, 2560, 1440);
        guiGraphics.blit(PAGE, pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, 1577, 1261, 1577, 1261);

        renderSelections(guiGraphics, pMouseX, pMouseY, pose, pPartialTick);

        pose.pushPose();
        pose.translate(listLeftX, listLeftY, 0);
        itemList.render(pose, guiGraphics, 0xFFFFFFFF, pMouseX - listLeftX, pMouseY - listLeftY, pPartialTick);
        pose.popPose();

        if (selectedTitle != null) {
            pose.pushPose();
            pose.translate(titleX, titleY, 0);
            pose.scale(1.25F, 1.25F, 1);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, selectedTitle, 0, 0, 0xFFAAFFAA);
            pose.popPose();
        }
        if (selectedText != null) {
            pose.pushPose();
            pose.translate(textBlockX, textBlockY, 0);
            selectedText.render(pose, guiGraphics, 0xFFFFFFFF, pMouseX - textBlockX, pMouseY - textBlockY, pPartialTick);
            pose.popPose();
        }
        //guiGraphics.drawString(font, String.format("X: %d, Y: %d", pMouseX, pMouseY), 10, 10, 0xFFFFFFFF);
        if (currentSelectionRecipes.size() > 1) {
            if (currentSelectionRecipes.size() == gridIndex + 1) {
                RenderSystem.setShaderColor(ARROW_DISCOLOR,ARROW_DISCOLOR,ARROW_DISCOLOR,1);
            }
            renderArrow(pose, guiGraphics, pMouseX, pMouseY, false);
            RenderSystem.setShaderColor(1,1,1,1);
            if (gridIndex == 0) {
                RenderSystem.setShaderColor(ARROW_DISCOLOR,ARROW_DISCOLOR,ARROW_DISCOLOR,1);
            }
            renderArrow(pose, guiGraphics, pMouseX, pMouseY, true);
            RenderSystem.setShaderColor(1,1,1,1);
        }

    }

    private void renderSelections(GuiGraphics guiGraphics, int pMouseX, int pMouseY, PoseStack pose, float pPartialTick) {
        pose.pushPose();
        pose.translate(CTCategoryX, CTCategoryY, 0);
        pose.scale(1.5F, 1.5F, 1);
        guiGraphics.blit(CTCategory ? SELECTION2 : SELECTION1, -16, -16, 0, 0, 32, 32, 32, 32);
        if (hoveringCTSelection(pMouseX, pMouseY)) {
            pose.scale(1.5F, 1.5F, 1);
        }
        guiGraphics.renderItem(craftingTable, -8, -8);
        pose.popPose();

        if (!gearBenchRecipes.isEmpty()) {
            pose.pushPose();
            pose.translate(GBCategoryX, GBCategoryY, 0);
            pose.scale(1.5F, 1.5F, 1);
            guiGraphics.blit(GBCategory ? SELECTION2 : SELECTION1, -16, -16, 0, 0, 32, 32, 32, 32);
            if (hoveringGBSelection(pMouseX, pMouseY)) {
                pose.scale(1.5F, 1.5F, 1);
            }
            guiGraphics.renderItem(gearBench, -8, -8);
            pose.popPose();
        }

        if (grid != null) {
            pose.pushPose();
            pose.translate(gridX, gridY, 0);
            pose.scale(gridFactor, gridFactor, 1);
            grid.render(pose, guiGraphics, 0xFFFFFFFF, (int) ((pMouseX - gridX) / gridFactor), (int) ((pMouseY - gridY) / gridFactor), pPartialTick);
            pose.popPose();
        }
    }

    private void renderArrow(PoseStack pose, GuiGraphics guiGraphics, int mouseX, int mouseY, boolean left) {
        pose.pushPose();
        pose.translate(arrowXOffset + (left ? -1 : 1) * ARROW_WIDTH * (left ? 3 : 2), arrowYOffset, 0);
        if ((hoveringLeftArrow(mouseX, mouseY) && left) || ((hoveringRightArrow(mouseX, mouseY) && !left))) {
            pose.scale(1.5F, 1.5F, 1);
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(left ? ResearchPageGui.LEFT_ARROW : ResearchPageGui.RIGHT_ARROW, -ARROW_WIDTH / 2, -ARROW_HEIGHT / 2, ARROW_WIDTH, ARROW_HEIGHT, 0, 0, 54, 53, 54, 53);
        pose.popPose();
    }

    private boolean hoveringLeftArrow(double mouseX, double mouseY) {
        int leftX = arrowXOffset - 7 * ARROW_WIDTH / 2;
        int topY = arrowYOffset - ARROW_HEIGHT / 2;
        return mouseX >= leftX && mouseX <= leftX + ARROW_WIDTH && mouseY >= topY && mouseY < topY + ARROW_HEIGHT;
    }

    private boolean hoveringRightArrow(double mouseX, double mouseY) {
        int leftX = arrowXOffset + 3 * ARROW_WIDTH / 2;
        int topY = arrowYOffset - ARROW_HEIGHT / 2;
        return mouseX >= leftX && mouseX <= leftX + ARROW_WIDTH && mouseY >= topY && mouseY <= topY + ARROW_HEIGHT;
    }

    private boolean hoveringCTSelection(int mouseX, int mouseY) {
        return mouseX >= CTCategoryX - 16 && mouseX <= CTCategoryX + 16 && mouseY >= CTCategoryY - 16 && mouseY <= CTCategoryY + 16;
    }

    private boolean hoveringGBSelection(int mouseX, int mouseY) {
        return !gearBenchRecipes.isEmpty() && mouseX >= GBCategoryX - 16 && mouseX <= GBCategoryX + 16 && mouseY >= GBCategoryY - 16 && mouseY <= GBCategoryY + 16;
    }

    public void selectEntry(String name) {
        for (CraftingEntry row : fullList.rows()) {
            if (Objects.equals(ForgeRegistries.ITEMS.getKey(row.stack.getItem()).getPath(), name)) {
                selectEntry(row.stack.getItem(), row.recipes);
                selectedEntry = row;
                if (row.recipes instanceof GearBenchRecipe) {
                    GBCategory = true;
                    itemList = initList();
                } else if (row.recipes instanceof CraftingRecipe) {
                    CTCategory = true;
                    itemList = initList();
                }
                break;
            }
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        InputConstants.Key mouseKey = InputConstants.getKey(pKeyCode, pScanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            minecraft.setScreen(new NecronomiconGui());
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (itemList.mouseClicked(pMouseX - listLeftX, pMouseY - listLeftY, pButton)) {
            return true;
        } else if (hoveringCTSelection((int) pMouseX, (int) pMouseY)) {
            CTCategory = !CTCategory;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
            itemList = initList();
            return true;
        } else if (hoveringGBSelection((int) pMouseX, (int) pMouseY)) {
            GBCategory = !GBCategory;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
            itemList = initList();
            return true;
        } else if (hoveringLeftArrow(pMouseX, pMouseY)) {
            gridIndex = Math.max(gridIndex - 1, 0);
            if (gridIndex < currentSelectionRecipes.size()) {
                grid = new CraftingGrid(craftingGridWidth, 100, currentSelectionRecipes.get(gridIndex), currentSelectionItem, gridFactor);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
            }
        } else if (hoveringRightArrow(pMouseX, pMouseY)) {
            gridIndex = Math.min(gridIndex + 1, currentSelectionRecipes.size() - 1);
            if (gridIndex < currentSelectionRecipes.size()) {
                grid = new CraftingGrid(craftingGridWidth, 100, currentSelectionRecipes.get(gridIndex), currentSelectionItem, gridFactor);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
            }
        } else if (selectedText.mouseClicked(pMouseX - textBlockX, pMouseY - textBlockY, pButton)) {
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        itemList.mouseDragged(pMouseX - listLeftX, pMouseY - listLeftY, pButton, pDragX, pDragY);
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        itemList.mouseReleased(pMouseX - listLeftX, pMouseY - listLeftY, pButton);
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        itemList.mouseScrolled(pMouseX - listLeftX, pMouseY - listLeftY, pDelta);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private void selectEntry(Item item, List<? extends Recipe<?>> recipes) {
        if (recipes.isEmpty()) {
            return;
        }
        currentSelectionRecipes = recipes;
        currentSelectionItem = item;
        selectedTitle = item.getDescription();
        String pTranslateKey = item.getDescriptionId() + ".crafting";
        if (item == Registration.DREAM_BOTTLE.get() && DataUtil.getBoolean(Minecraft.getInstance().player, PlayerDataLib.filled_bottle.name())) {
            pTranslateKey += "2";
        } else if (item == Registration.MEMORY_PHIAL.get() && DataUtil.getBoolean(Minecraft.getInstance().player, PlayerDataLib.REMINISCED.apply(Memory.METAL.getDataName()))) {
            pTranslateKey += "2";
        } else if (item == Registration.BLACK_MIRROR.get() && DataUtil.getBoolean(Minecraft.getInstance().player, PlayerDataLib.met_mirror.name())) {
            pTranslateKey += "3";
        } else if (item == Registration.BLACK_MIRROR.get() && ResearchUtil.getResearchStage(Minecraft.getInstance().player, "SNIPPET_OF_TRUTH") == 1) {
            pTranslateKey += "2";
        }
        String localizedText = I18n.get(pTranslateKey);
        localizedText += "\\n";
        Research research = knownRecipesByName.get(ForgeRegistries.ITEMS.getKey(item).toString());
        localizedText += I18n.get("research.CRAFTING.introduced", Component.translatable(research.getName()).getString(), research.getKey());
        int width = 800 * pageWidth / 1577;
        List<Element> elements = new TextUtil().parseText(localizedText, width, Minecraft.getInstance().font);
        //elements.add(new CraftingGrid(width, 100, recipe));
        selectedText = TextBlock.fillBlockWithElements(elements, 0, width, 400 * pageHeight / 1261, Minecraft.getInstance().font).getA();
        grid = new CraftingGrid(craftingGridWidth, 100, recipes.get(0), item, gridFactor);
        gridIndex = 0;
    }

    private class CraftingEntry extends Element {

        private final ItemStack stack;
        private final List<? extends Recipe<?>> recipes;
        private final float scaleFactor;

        protected CraftingEntry(Item result, List<? extends Recipe<?>> recipes) {
            super(entryListWidth - scrollbarWidth, entryListHeight / 6);
            stack = new ItemStack(result);
            this.recipes = recipes;
            scaleFactor = Math.min(1, getHeight() / 40F);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            graphics.blit(ENTRY, 0, 0, getWidth(), getHeight(), 0, 0, 490, 150, 490, 150);
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                graphics.fill(0, 0, getWidth(), getHeight(), 0x44604533);
            }
            poseStack.pushPose();
            poseStack.translate(getWidth() / 7, getHeight() / 2, 0);
            poseStack.scale(2.5F, 2.5F, 1);
            poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.renderItem(stack, -8, -8);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(getWidth() / 3, getHeight() / 3, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.drawString(minecraft.font, Component.translatable(stack.getDescriptionId()), 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
        }

        @Override
        public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
            if (insideBounds(relativeMouseX, relativeMouseY)) {
                selectEntry(stack.getItem(), recipes);
                selectedEntry = this;
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                return true;
            }
            return false;
        }
    }

    static class CraftingGrid extends Element {

        private final Recipe<?> recipe;
        private final List<ItemStack[]> stacks;
        private final ItemStack output;
        private final float scaleFactor;

        protected CraftingGrid(int width, int height, Recipe<?> recipe, Item result, float scaleFactor) {
            super(width, height);
            this.recipe = recipe;
            stacks = recipe.getIngredients().stream().map(Ingredient::getItems).collect(Collectors.toList());
            this.output = Minecraft.getInstance().level != null ? recipe.getResultItem(Minecraft.getInstance().level.registryAccess()) : new ItemStack(result);
            this.scaleFactor = scaleFactor;
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY, float pPartialTick) {
            int side = 0, pX = 0, pY = 0;
            int recipeWidth = 0, recipeHeight = 0;
            if (recipe instanceof CraftingRecipe) {
                side = 3;
                pX = 0;//getWidth() / 8 - 31;
                if (pX < 5) {
                    pX = 5;
                }
                pY = 10;
                graphics.blit(GRID3, pX, pY, 63, 63, 0, 0, 63, 63, 63, 63);
                if (recipe instanceof ShapedRecipe sr) {
                    recipeWidth = sr.getRecipeWidth();
                    recipeHeight = sr.getRecipeHeight();
                } else {
                    recipeWidth = recipeHeight = 0;
                }
            } else if (recipe instanceof GearBenchRecipe gbr) {
                side = 4;
                pX = 0;//getWidth() / 8 - 41;
                if (pX < 5) {
                    pX = 5;
                }
                graphics.blit(GRID4, pX, pY, 83, 83, 0, 0, 83, 83, 83, 83);
                recipeWidth = gbr.getRecipeWidth();
                recipeHeight = gbr.getRecipeHeight();
            }
            if (recipeWidth > 0) {
                int i = 0;
                for (int yIndex = 0; yIndex < recipeHeight; yIndex++) {
                    for (int xIndex = 0; xIndex < recipeWidth; xIndex++) {
                        ItemStack[] itemStacks = stacks.get(i);
                        int x = pX + xIndex * 21 + 10;
                        int y = pY + yIndex * 21 + 10;
                        i++;
                        if (itemStacks.length > 0) {
                            ItemStack itemStack = itemStacks[(counter / 20) % itemStacks.length];
                            graphics.renderItem(itemStack, x - 8, y - 8);
                            if (relativeMouseX >= x - 10 && relativeMouseX <= x + 10 && relativeMouseY >= y - 10 && relativeMouseY <= y + 10) {
                                graphics.renderTooltip(Minecraft.getInstance().font, itemStack.getTooltipLines(Minecraft.getInstance().player, TooltipFlag.NORMAL), itemStack.getTooltipImage(), relativeMouseX, relativeMouseY);
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < stacks.size(); i++) {
                    int x = pX + (i % side) * 21 + 10;
                    int y = pY + (i / side) * 21 + 10;
                    ItemStack[] itemStacks = stacks.get(i);
                    if (itemStacks.length > 0) {
                        ItemStack itemStack = itemStacks[(counter / 20) % itemStacks.length];
                        graphics.renderItem(itemStack, x - 8, y - 8);
                        if (relativeMouseX >= x - 10 && relativeMouseX <= x + 10 && relativeMouseY >= y - 10 && relativeMouseY <= y + 10) {
                            graphics.renderTooltip(Minecraft.getInstance().font, itemStack.getTooltipLines(Minecraft.getInstance().player, TooltipFlag.NORMAL), itemStack.getTooltipImage(), relativeMouseX, relativeMouseY);
                        }
                    }
                }
            }
            if (output != null) {
                poseStack.pushPose();
                int outputX = (int) (getWidth() / scaleFactor);
                float outputY = pY + side * 21 / 2F;
                poseStack.translate(outputX, outputY, 0);
                graphics.renderItem(output, -8, -8);
                graphics.renderItemDecorations(Minecraft.getInstance().font, output, -8, -8);
                poseStack.popPose();
                if (relativeMouseX >= outputX - 8 && relativeMouseX <= outputX + 8 && relativeMouseY >= outputY - 8 && relativeMouseY <= outputY + 8) {
                    graphics.renderTooltip(Minecraft.getInstance().font, output.getTooltipLines(Minecraft.getInstance().player, TooltipFlag.NORMAL), output.getTooltipImage(), relativeMouseX, relativeMouseY);
                }
                poseStack.pushPose();
                int arrowX = (outputX + side * 21) / 2 - 2;
                float arrowY = pY + side * 21 / 2F;
                poseStack.translate(arrowX, arrowY, 0);
                graphics.blit(GRID_ARROW, -11, -7, 22, 15, 0, 0, 22, 15, 22, 15);
                poseStack.popPose();
            }
        }

        @Override
        public boolean insertOutOfBounds() {
            return true;
        }
    }


}
