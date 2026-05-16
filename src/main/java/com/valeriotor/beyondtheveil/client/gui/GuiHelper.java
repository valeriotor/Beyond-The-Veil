package com.valeriotor.beyondtheveil.client.gui;

import com.valeriotor.beyondtheveil.client.gui.pool.BloodPoolGui;
import com.valeriotor.beyondtheveil.client.gui.research.BloodThesisGui;
import com.valeriotor.beyondtheveil.client.gui.research.JournalGui;
import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import com.valeriotor.beyondtheveil.util.GuiType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GuiHelper {

    public static void openClientSideGui(CompoundTag tag) {
        int id = tag.getInt("id");
        if (id >= 0 && id < GuiType.values().length) {
            openClientSideGui(GuiType.values()[id]);
        }
    }

    public static void openClientSideGui(GuiType type) {
        Screen screen = switch (type) {
            case NECRONOMICON -> new NecronomiconGui();
            case JOURNAL -> new JournalGui();
            case BLOOD_POOL -> new BloodPoolGui();
            case KILLED_BY_CULTIST -> new KilledByCultistGui();
            case BLOOD_THESIS -> new BloodThesisGui();
            case SURGERY_BED -> new SurgeryBedGui();
            case DAGON -> new DagonCommunionGui();
        };
        Minecraft.getInstance().setScreen(screen);
//        Minecraft.getInstance().pushGuiLayer(type.supplier.get());
    }

    public static List<FormattedCharSequence> splitStringsByWidth(String string, int width, Font f) {
        List<FormattedCharSequence> strings = f.split(FormattedText.of(string), width);
        //for(int i = 0; i < strings.size() - 1; i++) {
        //    strings.set(i, strings.get(i).concat(" "));
        //}
        return strings;
    }

    @NotNull
    public static StringBuilder cutString(String object1, int width) {
        StringBuilder objectBuilder = new StringBuilder();
        for (char c : object1.toCharArray()) {
            objectBuilder.append(c);
            if (Minecraft.getInstance().font.width(objectBuilder.toString()) > width) {
                objectBuilder.append("...");
                break;
            }
        }
        return objectBuilder;
    }


}
