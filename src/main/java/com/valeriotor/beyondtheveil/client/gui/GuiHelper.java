package com.valeriotor.beyondtheveil.client.gui;

import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;

import java.util.function.Supplier;

public class GuiHelper {

    public static void openClientSideGui(GuiType type) {
        Minecraft.getInstance().setScreen(type.supplier.get());
//        Minecraft.getInstance().pushGuiLayer(type.supplier.get());
    }

    public enum GuiType {
        NECRONOMICON(() -> new NecronomiconGui());

        private Supplier<Screen> supplier;

        GuiType(Supplier<Screen> supplier) {
            this.supplier = supplier;
        }
    }


}
