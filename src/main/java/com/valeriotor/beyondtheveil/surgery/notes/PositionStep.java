package com.valeriotor.beyondtheveil.surgery.notes;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class PositionStep extends ReportStep {

    public static PositionStep fromNBT(CompoundTag tag) {
        ReportLocationType location = ReportLocationType.valueOf(tag.getString("location"));
        return new PositionStep(location);
    }

    @NotNull private final ReportLocationType location;

    public PositionStep(@NotNull ReportLocationType type) {
        super(ReportStepType.POSITION);
        this.location = type;
    }

    public ReportLocationType getLocation() {
        return location;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = super.saveToNBT();
        tag.putString("location", location.name());
        return tag;
    }
}
