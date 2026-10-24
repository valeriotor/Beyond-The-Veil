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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;
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


    private float entryListWidth;
    private float entryListHeight;
    private float entryListStart;
    private float scrollbarWidth;
    private ScrollableList<CraftingEntry> itemList;
    private ScrollableList<CraftingEntry> fullList;
    private float listLeftX, listLeftY;
    private float titleX, titleY;
    private float textBlockX, textBlockY;
    private float gridX, gridY, gridWidth;
    private float gridFactor;
    private float CTCategoryX, CTCategoryY, GBCategoryX, GBCategoryY;
    private boolean CTCategory = true, GBCategory;
    private final ItemStack craftingTable = new ItemStack(Blocks.CRAFTING_TABLE);
    private final ItemStack gearBench = new ItemStack(Registration.GEAR_BENCH.get());
    private final List<List<CraftingRecipe>> craftingTableRecipes;
    private final List<List<GearBenchRecipe>> gearBenchRecipes;
    private final Map<String, Research> knownRecipesByName;
    private Component selectedTitle;
    private CraftingGrid grid;
    private CraftingEntry selectedEntry;
    private List<? extends Recipe<?>> currentSelectionRecipes = new ArrayList<>();
    private float arrowXOffset;
    private float arrowYOffset;
    private float textArrowXOffset;
    private float textArrowYOffset;
    private final int ARROW_WIDTH = 24;
    private final int ARROW_HEIGHT = 24;
    private int gridIndex;
    private Item currentSelectionItem;
    private float craftingGridWidth;
    private static final float ARROW_DISCOLOR = 0.35F;
    private float scaleFactor = 1;
    private static final int BACKGROUND_BASE_WIDTH = 1577;
    private static final int BACKGROUND_BASE_HEIGHT = 1261;
    private float imageWidth;
    private float imageHeight;
    private final float BASE_IMAGE_TO_GUI_MULTIPLIER = 0.45F;
    private int selectedBlockIndex;
    private List<TextBlock> selectedTextBlocks = new ArrayList<>();


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
        scaleFactor = 1;
        imageWidth = (int) (BACKGROUND_BASE_WIDTH * 1.5F);
        imageHeight = (int) (BACKGROUND_BASE_HEIGHT * 1.5F);
        float bitgm = BASE_IMAGE_TO_GUI_MULTIPLIER;
        imageWidth = BACKGROUND_BASE_WIDTH * bitgm;
        imageHeight = BACKGROUND_BASE_HEIGHT * bitgm;
        //entryListWidth = 343;
        //entryListHeight = 357;

        if (imageHeight > height * 95 / 100F) {
            scaleFactor = height * 95F / 100 / imageHeight;
        }
        if (imageWidth > width * 95 / 100F) {
            scaleFactor = Math.min(width * 95F / 100 / imageWidth, scaleFactor);
        }

        entryListWidth = 489 * bitgm;// * pageWidth / 1577;
        entryListHeight = 839 * bitgm;// * pageHeight / 1261;
        entryListStart = 44 * bitgm;// * pageWidth / 1577;
        scrollbarWidth = 20 * bitgm;// * pageWidth / 1577;

        listLeftX = -imageWidth / 2 + 44 * bitgm;//pageLeftX + 44 * pageWidth / 1577;
        listLeftY = -imageHeight / 2 + 381 * bitgm;//pageTopY + 381 * pageHeight / 1261;

        itemList = initList();
        fullList = initList(true);

        if (gearBenchRecipes.isEmpty()) {
            CTCategoryX = -imageWidth / 2 + 290 * bitgm;//pageLeftX + 290 * pageWidth / 1577;
            CTCategoryY = -imageHeight / 2 + 180 * bitgm;//pageTopY + 180 * pageHeight / 1261;
        } else {
            CTCategoryX = -imageWidth / 2 + 190 * bitgm;//pageLeftX + 190 * pageWidth / 1577;
            CTCategoryY = -imageHeight / 2 + 180 * bitgm;//pageTopY + 180 * pageHeight / 1261;
            GBCategoryX = -imageWidth / 2 + 390 * bitgm;//pageLeftX + 390 * pageWidth / 1577;
            GBCategoryY = -imageHeight / 2 + 180 * bitgm;//pageTopY + 180 * pageHeight / 1261;
        }

        titleX = -imageWidth / 2 + (555 + 489) * bitgm;//pageLeftX + (555 + 489) * pageWidth / 1577;
        titleY = -imageHeight / 2 + 100 * bitgm;//pageTopY + 100 * pageHeight / 1261;
        textBlockX = -imageWidth / 2 + 670 * bitgm;//pageLeftX + 670 * pageWidth / 1577;
        textBlockY = -imageHeight / 2 + 210 * bitgm;//pageTopY + 220 * pageHeight / 1261;
        gridX = -imageWidth / 2 + (556 + 70) * bitgm;//pageLeftX + (556 + 70) * pageWidth / 1577;
        gridY = -imageHeight / 2 + 711 * bitgm;//pageTopY + 651 * pageHeight / 1261;
        gridWidth = 949 * bitgm;// * pageWidth / 1577;
        gridFactor = gridWidth / 200F;

        if (selectedEntry != null) {
            selectEntry(selectedEntry.stack.getItem(), selectedEntry.recipes);
        }

        arrowXOffset = (gridX + gridWidth / 2) - 20;
        arrowYOffset = 520 * bitgm;// * pageWidth / 1577;
        textArrowXOffset = titleX + 20;
        textArrowYOffset = 10;
        craftingGridWidth = 800 * bitgm;// * pageWidth / 1577;
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
        return new ScrollableList<>((int) entryListWidth, (int) entryListHeight, entries, (int) (entryListHeight / 6), (int) scrollbarWidth);
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
        pose.pushPose();
        pose.translate(width / 2F, height / 2F, 0);
        pose.scale(scaleFactor, scaleFactor, 1);
        pMouseX = (int) ((pMouseX - width / 2) / scaleFactor);
        pMouseY = (int) ((pMouseY - height / 2) / scaleFactor);

        pose.pushPose();
        pose.translate(-imageWidth / 2, -imageHeight / 2, 0);
        guiGraphics.blit(PAGE, 0, 0, (int) imageWidth, (int) imageHeight, 0, 0, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT, BACKGROUND_BASE_WIDTH, BACKGROUND_BASE_HEIGHT);
        pose.popPose();
        //guiGraphics.blit(PAGE, pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, 1577, 1261, 1577, 1261);

        renderSelections(guiGraphics, pMouseX, pMouseY, pose, pPartialTick);

        pose.pushPose();
        pose.translate(listLeftX, listLeftY, 0);
        itemList.render(pose, guiGraphics, 0xFFFFFFFF, (int) (pMouseX - listLeftX), (int) (pMouseY - listLeftY), pPartialTick);
        pose.popPose();

        if (selectedTitle != null) {
            pose.pushPose();
            pose.translate(titleX, titleY, 0);
            pose.scale(1.25F, 1.25F, 1);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, selectedTitle, 0, 0, 0xFFAAFFAA);
            pose.popPose();
        }
        if (!selectedTextBlocks.isEmpty()) {
            pose.pushPose();
            pose.translate(textBlockX, textBlockY, 0);
            selectedTextBlocks.get(selectedBlockIndex).render(pose, guiGraphics, 0xFFFFFFFF, (int) (pMouseX - textBlockX), (int) (pMouseY - textBlockY), pPartialTick);
            pose.popPose();
        }
        //guiGraphics.drawString(font, String.format("X: %d, Y: %d", pMouseX, pMouseY), 10, 10, 0xFFFFFFFF);
        if (currentSelectionRecipes.size() > 1) {
            if (currentSelectionRecipes.size() == gridIndex + 1) {
                RenderSystem.setShaderColor(ARROW_DISCOLOR, ARROW_DISCOLOR, ARROW_DISCOLOR, 1);
            }
            renderArrow(pose, guiGraphics, pMouseX, pMouseY, false);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            if (gridIndex == 0) {
                RenderSystem.setShaderColor(ARROW_DISCOLOR, ARROW_DISCOLOR, ARROW_DISCOLOR, 1);
            }
            renderArrow(pose, guiGraphics, pMouseX, pMouseY, true);
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
        if (selectedTextBlocks.size() > 1) {
            if (selectedTextBlocks.size() == selectedBlockIndex + 1) {
                RenderSystem.setShaderColor(ARROW_DISCOLOR, ARROW_DISCOLOR, ARROW_DISCOLOR, 1);
            }
            renderTextArrow(pose, guiGraphics, pMouseX, pMouseY, false);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            if (selectedBlockIndex == 0) {
                RenderSystem.setShaderColor(ARROW_DISCOLOR, ARROW_DISCOLOR, ARROW_DISCOLOR, 1);
            }
            renderTextArrow(pose, guiGraphics, pMouseX, pMouseY, true);
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
        pose.popPose();
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

    private void renderTextArrow(PoseStack pose, GuiGraphics guiGraphics, int mouseX, int mouseY, boolean left) {
        pose.pushPose();
        pose.translate(textArrowXOffset + (left ? -1 : 1) * ARROW_WIDTH * (left ? 7 + 1.5 : 6 + 1.5), textArrowYOffset, 0);
        if ((hoveringLeftTextArrow(mouseX, mouseY) && left) || ((hoveringRightTextArrow(mouseX, mouseY) && !left))) {
            pose.scale(1.5F, 1.5F, 1);
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(left ? ResearchPageGui.LEFT_ARROW : ResearchPageGui.RIGHT_ARROW, -ARROW_WIDTH / 2, -ARROW_HEIGHT / 2, ARROW_WIDTH * 2 / 3, ARROW_HEIGHT, 0, 0, 54, 53, 54, 53);
        pose.popPose();
    }

    private boolean hoveringLeftArrow(double mouseX, double mouseY) {
        float leftX = arrowXOffset - 7 * ARROW_WIDTH / 2F;
        float topY = arrowYOffset - ARROW_HEIGHT / 2F;
        return mouseX >= leftX && mouseX <= leftX + ARROW_WIDTH && mouseY >= topY && mouseY < topY + ARROW_HEIGHT;
    }

    private boolean hoveringRightArrow(double mouseX, double mouseY) {
        float leftX = arrowXOffset + 3 * ARROW_WIDTH / 2F;
        float topY = arrowYOffset - ARROW_HEIGHT / 2F;
        return mouseX >= leftX && mouseX <= leftX + ARROW_WIDTH && mouseY >= topY && mouseY <= topY + ARROW_HEIGHT;
    }

    private boolean hoveringLeftTextArrow(double mouseX, double mouseY) {
        float leftX = textArrowXOffset - 18 * ARROW_WIDTH / 2F;
        float topY = textArrowYOffset - ARROW_HEIGHT / 2F;
        return mouseX >= leftX - 3 && mouseX <= leftX + ARROW_WIDTH + 3 && mouseY >= topY - 3 && mouseY < topY + ARROW_HEIGHT + 3;
    }

    private boolean hoveringRightTextArrow(double mouseX, double mouseY) {
        float leftX = textArrowXOffset + 13.5F * ARROW_WIDTH / 2F;
        float topY = textArrowYOffset - ARROW_HEIGHT / 2F;
        return mouseX >= leftX - 3 && mouseX <= leftX + ARROW_WIDTH + 3 && mouseY >= topY - 3 && mouseY <= topY + ARROW_HEIGHT + 3;
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
        if (Minecraft.getInstance().options.keyInventory.isActiveAndMatches(mouseKey)) {
            Minecraft.getInstance().setScreen(new NecronomiconGui());
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        pMouseX = (int) ((pMouseX - width / 2) / scaleFactor);
        pMouseY = (int) ((pMouseY - height / 2) / scaleFactor);
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
                grid = new CraftingGrid((int) craftingGridWidth, 100, currentSelectionRecipes.get(gridIndex), currentSelectionItem, gridFactor);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
            }
        } else if (hoveringRightArrow(pMouseX, pMouseY)) {
            gridIndex = Math.min(gridIndex + 1, currentSelectionRecipes.size() - 1);
            if (gridIndex < currentSelectionRecipes.size()) {
                grid = new CraftingGrid((int) craftingGridWidth, 100, currentSelectionRecipes.get(gridIndex), currentSelectionItem, gridFactor);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
            }
        } else if (hoveringLeftTextArrow(pMouseX, pMouseY)) {
            selectedBlockIndex = Math.max(0, selectedBlockIndex - 1);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
        } else if (hoveringRightTextArrow(pMouseX, pMouseY)) {
            selectedBlockIndex = Math.max(0, Math.min(selectedBlockIndex + 1, selectedTextBlocks.size() - 1));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
        } else {
            TextBlock textBlock = currentBlock();
            if (textBlock != null && textBlock.mouseClicked(pMouseX - textBlockX, pMouseY - textBlockY, pButton)) {
                return true;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }


    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        pMouseX = (int) ((pMouseX - width / 2) / scaleFactor);
        pMouseY = (int) ((pMouseY - height / 2) / scaleFactor);
        itemList.mouseDragged(pMouseX - listLeftX, pMouseY - listLeftY, pButton, pDragX, pDragY);
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        pMouseX = (int) ((pMouseX - width / 2) / scaleFactor);
        pMouseY = (int) ((pMouseY - height / 2) / scaleFactor);
        itemList.mouseReleased(pMouseX - listLeftX, pMouseY - listLeftY, pButton);
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        pMouseX = (int) ((pMouseX - width / 2) / scaleFactor);
        pMouseY = (int) ((pMouseY - height / 2) / scaleFactor);
        itemList.mouseScrolled(pMouseX - listLeftX, pMouseY - listLeftY, pDelta);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private void selectEntry(Item item, List<? extends Recipe<?>> recipes) {
        if (recipes.isEmpty()) {
            return;
        }
        selectedBlockIndex = 0;
        currentSelectionRecipes = recipes;
        currentSelectionItem = item;
        selectedTitle = item.getDescription();
        if (item == Registration.DEMAND_PILLAR_ITEM.get()) {
            selectedTitle = Component.translatable("pillars.crafting");
        } else if (item == Registration.FLASK_LARGE_ITEM.get()) {
            selectedTitle = Component.translatable("flasks.crafting");
        }
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
        List<Element> elements = new TextUtil().parseText(localizedText, width, Minecraft.getInstance().font);
        //elements.add(new CraftingGrid(width, 100, recipe));
        selectedTextBlocks = makePages(localizedText);
        //selectedText = TextBlock.fillBlockWithElements(elements, 0, width, (int) (520 * BASE_IMAGE_TO_GUI_MULTIPLIER), Minecraft.getInstance().font).getA();
        grid = new CraftingGrid((int) craftingGridWidth, 100, recipes.get(0), item, gridFactor);
        gridIndex = 0;
    }


    private List<TextBlock> makePages(String localized) {
        List<TextBlock> blocks = new ArrayList<>();
        int blockWidth = (int) (800 * BASE_IMAGE_TO_GUI_MULTIPLIER);
        List<Element> lines = new TextUtil().parseText(localized, blockWidth, Minecraft.getInstance().font);
        int i = 0;
        while (i < lines.size()) {
            Tuple<TextBlock, Integer> tuple = TextBlock.fillBlockWithElements(lines, i, blockWidth, (int) (420 * BASE_IMAGE_TO_GUI_MULTIPLIER), Minecraft.getInstance().font);
            blocks.add(tuple.getA());
            i = tuple.getB();
        }
        return blocks;
    }

    private TextBlock currentBlock() {
        if (selectedTextBlocks.size() <= selectedBlockIndex) {
            return null;
        }
        return selectedTextBlocks.get(selectedBlockIndex);
    }

    private class CraftingEntry extends Element {

        private final ItemStack stack;
        private final List<? extends Recipe<?>> recipes;
        private final float scaleFactor;
        private final MutableComponent descriptionId;

        protected CraftingEntry(Item result, List<? extends Recipe<?>> recipes) {
            super((int) (entryListWidth - scrollbarWidth), (int) (entryListHeight / 6));
            stack = new ItemStack(result);
            this.recipes = recipes;
            scaleFactor = Math.min(1, getHeight() / 40F);
            String translationKey = stack.getDescriptionId();
            if (result == Registration.DEMAND_PILLAR_ITEM.get()) {
                translationKey = "pillars.crafting";
            } else if (result == Registration.FLASK_LARGE_ITEM.get()) {
                translationKey = "flasks.crafting";
            }
            descriptionId = Component.translatable(translationKey);
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
            poseStack.scale(scaleFactor * scaleFactor, scaleFactor * scaleFactor, 1);
            graphics.renderItem(stack, -8, -8);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(getWidth() / 3, getHeight() / 3, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            poseStack.scale(scaleFactor, scaleFactor, 1);
            graphics.drawString(minecraft.font, descriptionId, 0, 0, 0xFFFFFFFF);
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
