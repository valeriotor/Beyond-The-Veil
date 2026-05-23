package com.valeriotor.beyondtheveil.surgery.notes;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class ComplicationStep extends ReportStep {


    public static ComplicationStep fromNBT(CompoundTag tag) {
        ReportComplication complication = ReportComplication.valueOf(tag.getString("complication"));
        return new ComplicationStep(complication);
    }
    private final ReportComplication complication;

    public ComplicationStep(@NotNull ReportComplication complication) {
        super(ReportStepType.COMPLICATION);
        this.complication = complication;
    }

    public ReportComplication getComplication() {
        return complication;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = super.saveToNBT();
        tag.putString("complication", complication.name());
        return tag;
    }
}
