package com.valeriotor.beyondtheveil.client.gui;

import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;
import java.util.function.Supplier;

public class GuiHelper {

    public static void openClientSideGui(GuiType type) {
        Minecraft.getInstance().setScreen(type.supplier.get());
//        Minecraft.getInstance().pushGuiLayer(type.supplier.get());
    }

    public static List<FormattedCharSequence> splitStringsByWidth(String string, int width, Font f) {
        List<FormattedCharSequence> strings = f.split(FormattedText.of(string), width);
        //for(int i = 0; i < strings.size() - 1; i++) {
        //    strings.set(i, strings.get(i).concat(" "));
        //}
        return strings;
    }

    public enum GuiType {
        NECRONOMICON(() -> new NecronomiconGui());

        private Supplier<Screen> supplier;

        GuiType(Supplier<Screen> supplier) {
            this.supplier = supplier;
        }
    }


}
