package com.valeriotor.beyondtheveil.surgery.notes;

import com.valeriotor.beyondtheveil.client.gui.elements.EditableDropdownBox;
import net.minecraft.network.chat.Component;

public enum ReportStepType implements EditableDropdownBox.Option {
    NONE, POSITION, EXTRACTION, INCISION, INJECTION, INSERTION, STITCHING, PAIN, DEATH;

    @Override
    public Component getText() {
        return Component.translatable("gui.journal.journal.type." + name().toLowerCase());
    }

    @Override
    public String getId() {
        return name();
    }
}
