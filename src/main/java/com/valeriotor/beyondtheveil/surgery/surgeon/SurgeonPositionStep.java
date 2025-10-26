package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.notes.PositionStep;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.nbt.CompoundTag;

public class SurgeonPositionStep extends SurgeonStep.SurgeonReportStep {

    private PositionStep reportStep;

    public SurgeonPositionStep(SurgeonEntity surgeon, BellData bellData, PositionStep reportStep) {
        super(surgeon, bellData, reportStep);
        this.reportStep = reportStep;
    }

    @Override
    public boolean performAction() {
        SurgicalBE be = moveToBE();
        if (be != null && be.getPatientStatus() != null && be.allowedLocations().contains(reportStep.getLocation().getLocation())) {
            be.getPatientStatus().setExposedLocation(reportStep.getLocation().getLocation());
            be.setChanged();
            surgeon.level().sendBlockUpdated(be.getBlockPos(), be.getBlockState(), be.getBlockState(), 2);
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("step", reportStep.saveToNBT());
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {
        reportStep = PositionStep.fromNBT(tag.getCompound("step"));
    }
}
