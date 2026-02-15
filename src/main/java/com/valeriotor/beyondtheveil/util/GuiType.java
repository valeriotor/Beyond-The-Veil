package com.valeriotor.beyondtheveil.util;

import com.valeriotor.beyondtheveil.client.gui.DagonCommunionGui;
import com.valeriotor.beyondtheveil.client.gui.KilledByCultistGui;
import com.valeriotor.beyondtheveil.client.gui.SurgeryBedGui;
import com.valeriotor.beyondtheveil.client.gui.pool.BloodPoolGui;
import com.valeriotor.beyondtheveil.client.gui.research.BloodThesisGui;
import com.valeriotor.beyondtheveil.client.gui.research.JournalGui;
import com.valeriotor.beyondtheveil.client.gui.research.NecronomiconGui;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Supplier;

public enum GuiType {
    NECRONOMICON,
    JOURNAL,
    BLOOD_POOL,
    KILLED_BY_CULTIST,
    BLOOD_THESIS,
    SURGERY_BED,
    DAGON;

}
