package com.valeriotor.beyondtheveil.client.gui.elements.property;

import com.valeriotor.beyondtheveil.client.gui.research.CraftingRegistryGui;
import com.valeriotor.beyondtheveil.client.gui.research.JournalGui;
import com.valeriotor.beyondtheveil.client.gui.research.ResearchPageGui;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class LinkProperty extends Property {

    private final String link;
    private final Component text;

    public LinkProperty(int xStart, int xEnd, String link) {
        super(xStart, xEnd);
        this.link = link;
        this.text = text(link);
    }

    private Component text(String link) {
        if (link.startsWith("crafting")) {
            return Component.translatable("caption.crafting");
        } else if (link.startsWith("journal")) {
            return Component.translatable("caption.journal");
        } else if (link.startsWith("research")) {
            return Component.translatable("caption.research");
        }
        return Component.translatable("link." + link);
    }

    @Override
    public void render(GuiGraphics graphics, int relativeMouseX, int relativeMouseY) {
        graphics.renderTooltip(Minecraft.getInstance().font, text, relativeMouseX, relativeMouseY);
    }

    @Override
    public boolean mouseClicked(double relativeMouseX, double relativeMouseY, int mouseButton) {
        if (link.startsWith("journal")) {
            if (!(Minecraft.getInstance().screen instanceof JournalGui)) {
                JournalGui pGuiScreen = new JournalGui();
                Minecraft.getInstance().setScreen(pGuiScreen);
            }
            if (Minecraft.getInstance().screen instanceof JournalGui jg) {
                jg.selectEntry(link.substring("journal.".length()));
            }
        } else if (link.startsWith("crafting")) {
            if (!(Minecraft.getInstance().screen instanceof CraftingRegistryGui)) {
                CraftingRegistryGui pGuiScreen = new CraftingRegistryGui(ResearchUtil.getResearch(Minecraft.getInstance().player, "CRAFTING"));
                Minecraft.getInstance().setScreen(pGuiScreen);
            }
            if (Minecraft.getInstance().screen instanceof CraftingRegistryGui cr) {
                cr.selectEntry(link.substring("crafting.".length()));
            }
        } else if (link.startsWith("research")) {
            String researchName = link.substring("research.".length());
            ResearchStatus status = ResearchUtil.getResearch(Minecraft.getInstance().player, researchName);
            if (status.getStage() == -2) {
                return false;
            }
            ResearchPageGui pGuiScreen = new ResearchPageGui(status);
            Minecraft.getInstance().setScreen(pGuiScreen);
        }
        return true;
    }
}
