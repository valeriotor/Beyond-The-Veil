package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.client.gui.elements.TextBlock;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.recipes.GearBenchRecipe;
import com.valeriotor.beyondtheveil.research.Research;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
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
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class CraftingRegistryGui extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_background.png");
    private static final ResourceLocation PAGE = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_page.png");
    private static final ResourceLocation ENTRY = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_entry.png");
    private static final ResourceLocation SELECTION1 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_selection_1.png");
    private static final ResourceLocation SELECTION2 = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_selection_2.png");


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
    private Element itemList;
    private int listLeftX, listLeftY;
    private int titleX, titleY;
    private int textBlockX, textBlockY;
    private int CTCategoryX, CTCategoryY, GBCategoryX, GBCategoryY;
    private boolean CTCategory = true, GBCategory;
    private final ItemStack craftingTable = new ItemStack(Blocks.CRAFTING_TABLE);
    private final ItemStack gearBench = new ItemStack(Registration.GEAR_BENCH.get());
    private final List<CraftingRecipe> craftingTableRecipes;
    private final List<GearBenchRecipe> gearBenchRecipes;
    private final Map<String, Research> knownRecipesByName;
    private Component selectedTitle;
    private TextBlock selectedText;


    public CraftingRegistryGui(ResearchStatus status) {
        super(Component.translatable(status.res.getName()));
        knownRecipesByName = ResearchUtil.getKnownRecipes(Minecraft.getInstance().player);
        craftingTableRecipes = new ArrayList<>();
        gearBenchRecipes = new ArrayList<>();
        for (String s : knownRecipesByName.keySet()) {
            String key = s.split(";")[0];
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(key));
            if (item != null && !craftingTableRecipes.contains(item)) {
                Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(key));
                if (recipe.isPresent()) {
                    if (recipe.get() instanceof CraftingRecipe cr) {
                        craftingTableRecipes.add(cr);
                    } else if (recipe.get() instanceof GearBenchRecipe gbr) {
                        gearBenchRecipes.add(gbr);
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

        initList();

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

    }

    private void initList() {
        List<CraftingEntry> entries = new ArrayList<>();
        if (CTCategory) {
            for (CraftingRecipe craftingTableRecipe : craftingTableRecipes) {
                entries.add(new CraftingEntry(craftingTableRecipe.getResultItem(minecraft.level.registryAccess()).getItem()));
            }
        }
        if (GBCategory) {
            for (GearBenchRecipe gearBenchRecipe : gearBenchRecipes) {
                entries.add(new CraftingEntry(gearBenchRecipe.getResultItem(minecraft.level.registryAccess()).getItem()));
            }
        }
        entries.sort(Comparator.comparing(e -> e.stack.getItem().getDescription().getString()));
        itemList = new ScrollableList(entryListWidth, entryListHeight, entries, entryListHeight / 6, scrollbarWidth);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

        PoseStack pose = guiGraphics.pose();
        //guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, width, height, 2560, 1440);
        guiGraphics.blit(BACKGROUND, 0, 0, width, height, 0, 0, 2560, 1440, 2560, 1440);
        guiGraphics.blit(PAGE, pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, 1577, 1261, 1577, 1261);

        renderSelections(guiGraphics, pMouseX, pMouseY, pose);

        pose.pushPose();
        pose.translate(listLeftX, listLeftY, 0);
        itemList.render(pose, guiGraphics, 0xFFFFFFFF, pMouseX - listLeftX, pMouseY - listLeftY);
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
            selectedText.render(pose, guiGraphics, 0xFFFFFFFF, pMouseX - textBlockX, pMouseY - textBlockY);
            pose.popPose();
        }
        //guiGraphics.drawString(font, String.format("X: %d, Y: %d", pMouseX, pMouseY), 10, 10, 0xFFFFFFFF);

    }

    private void renderSelections(GuiGraphics guiGraphics, int pMouseX, int pMouseY, PoseStack pose) {
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
    }

    private boolean hoveringCTSelection(int mouseX, int mouseY) {
        return mouseX >= CTCategoryX - 16 && mouseX <= CTCategoryX + 16 && mouseY >= CTCategoryY - 16 && mouseY <= CTCategoryY + 16;
    }

    private boolean hoveringGBSelection(int mouseX, int mouseY) {
        return !gearBenchRecipes.isEmpty() && mouseX >= GBCategoryX - 16 && mouseX <= GBCategoryX + 16 && mouseY >= GBCategoryY - 16 && mouseY <= GBCategoryY + 16;
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
            initList();
        } else if (hoveringGBSelection((int) pMouseX, (int) pMouseY)) {
            GBCategory = !GBCategory;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
            initList();
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

    private void selectEntry(Item item) {
        selectedTitle = item.getDescription();
        String localizedText = I18n.get(item.getDescriptionId() + ".crafting");
        localizedText += "\\n";
        Research research = knownRecipesByName.get(ForgeRegistries.ITEMS.getKey(item).toString());
        localizedText += I18n.get("research.CRAFTING.introduced", Component.translatable(research.getName()).getString(), research.getKey());
        selectedText = new TextBlock(localizedText, 800 * pageWidth / 1577, 400 * pageHeight / 1261, Minecraft.getInstance().font);
    }

    private class CraftingEntry extends Element {

        private final ItemStack stack;
        private final float scaleFactor;

        public CraftingEntry(Item result) {
            super(entryListWidth - scrollbarWidth, entryListHeight / 6);
            stack = new ItemStack(result);
            scaleFactor = Math.min(1, getHeight() / 40F);
        }

        @Override
        public void render(PoseStack poseStack, GuiGraphics graphics, int color, int relativeMouseX, int relativeMouseY) {
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
                selectEntry(stack.getItem());
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
                return true;
            }
            return false;
        }
    }


}
