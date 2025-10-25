package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStepType;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class SurgeonCompletableStep extends SurgeonStep.SurgeonReportStep {

    private final ReportStep.CompletableStep reportStep;
    private int currentDuration = 0;
    private boolean finishedExtraction = false; // hacky...

    public SurgeonCompletableStep(SurgeonEntity surgeon, BellData bellData, ReportStep.CompletableStep reportStep) {
        super(surgeon, bellData, reportStep);
        this.reportStep = reportStep;
    }

    @Override
    public boolean performAction() {
        if (finishedExtraction) {
            return true;
        }
        SurgicalBE be = moveToBE();
        if (be != null) {
            PatientStatus patientStatus = be.getPatientStatus();
            if (patientStatus != null) {
                if (reportStep.getType() == ReportStepType.EXTRACTION) {
                    patientStatus.extract(null, be, currentDuration++, stack -> {
                        surgeon.giveItem(stack);
                        finishedExtraction = true;
                    });
                } else if (reportStep.getType() == ReportStepType.INCISION) {
                    patientStatus.performIncision(null, be, currentDuration++);
                    if (patientStatus.isIncised()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("currentDuration", currentDuration);
        tag.putBoolean("finishedExtraction", finishedExtraction);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {
        currentDuration = tag.getInt("currentDuration");
        finishedExtraction = tag.getBoolean("finishedExtraction");
    }
}
