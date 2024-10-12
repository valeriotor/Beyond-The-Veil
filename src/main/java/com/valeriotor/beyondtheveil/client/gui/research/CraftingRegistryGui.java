package com.valeriotor.beyondtheveil.client.gui.research;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.valeriotor.beyondtheveil.client.gui.elements.Element;
import com.valeriotor.beyondtheveil.client.gui.elements.ScrollableList;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class CraftingRegistryGui extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_background.png");
    private static final ResourceLocation PAGE = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_page.png");
    private static final ResourceLocation ENTRY = new ResourceLocation(References.MODID, "textures/gui/research/crafting_registry_entry.png");


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
    private int listLeftX;
    private int listLeftY;
    private final List<CraftingRecipe> craftingTableRecipes;

    public CraftingRegistryGui(ResearchStatus status) {
        super(Component.translatable(status.res.getName()));
        List<String> knownRecipesByName = ResearchUtil.getKnownRecipes(Minecraft.getInstance().player).stream().sorted(Comparator.comparing(s -> Component.translatable(s).toString())).toList();
        craftingTableRecipes = new ArrayList<>();
        for (String s : knownRecipesByName) {
            String key = s.split(";")[0];
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(key));
            if (item != null && !craftingTableRecipes.contains(item)) {
                Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(key));
                if (recipe.isPresent()) {
                    if (recipe.get() instanceof CraftingRecipe cr) {
                        craftingTableRecipes.add(cr);
                    }
                }
            }
        }

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
        scrollbarWidth = 15;

        listLeftX = pageLeftX + 44 * pageWidth / 1577;
        listLeftY = pageTopY + 381 * pageHeight / 1261;

        List<CraftingEntry> entries = new ArrayList<>();
        for (CraftingRecipe craftingTableRecipe : craftingTableRecipes) {
            entries.add(new CraftingEntry(craftingTableRecipe.getResultItem(minecraft.level.registryAccess()).getItem()));
        }
        itemList = new ScrollableList(entryListWidth, entryListHeight, entries, entryListHeight / 6, scrollbarWidth);

    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

        PoseStack pose = guiGraphics.pose();
        //guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, width, height, 2560, 1440);
        guiGraphics.blit(BACKGROUND, 0, 0, width, height, 0, 0, 2560, 1440, 2560, 1440);
        guiGraphics.blit(PAGE, pageLeftX, pageTopY, pageWidth, pageHeight, 0, 0, 1577, 1261, 1577, 1261);

        pose.pushPose();
        pose.translate(listLeftX, listLeftY, 0);
        itemList.render(pose, guiGraphics, 0xFFFFFFFF, pMouseX - listLeftX, pMouseY - listLeftY);
        pose.popPose();
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

    private class CraftingEntry extends Element {

        private final ItemStack stack;

        public CraftingEntry(Item result) {
            super(entryListWidth - scrollbarWidth, entryListHeight / 6);
            stack = new ItemStack(result);
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
            graphics.renderItem(stack, -8, -8);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(getWidth() / 3, getHeight() / 3, 0);
            //poseStack.scale(2.5F, 2.5F, 1);
            graphics.drawString(minecraft.font, Component.translatable(stack.getDescriptionId()), 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
        }
    }


}
