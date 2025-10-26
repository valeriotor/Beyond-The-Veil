package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.nbt.CompoundTag;

public class SurgeonStitchingStep extends SurgeonStep.SurgeonReportStep {

    public SurgeonStitchingStep(SurgeonEntity surgeon, BellData bellData, ReportStep reportStep) {
        super(surgeon, bellData, reportStep);
    }

    @Override
    public boolean performAction() {
        SurgicalBE be = moveToBE();
        if (be != null) {
            be.getPatientStatus().sewIncision();
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag saveToNBT() {
        return new CompoundTag();
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {

    }
}
