package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.notes.InjectionStep;
import com.valeriotor.beyondtheveil.surgery.notes.InsertionStep;
import com.valeriotor.beyondtheveil.surgery.notes.Report;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class SurgeonProgress {

    public static SurgeonProgress fromNBT(SurgeonEntity surgeon, CompoundTag tag) {
        if (tag.contains("report")) {
            Report r = Report.loadFromNBT(tag.getCompound("report"));
            SurgeonProgress progress = new SurgeonProgress(surgeon, r);
            if (tag.contains("steps")) {
                ListTag steps = tag.getList("steps", Tag.TAG_COMPOUND);
                for (int i = 0; i < progress.steps.size(); i++) {
                    if (steps.size() > i) {
                        progress.steps.get(i).loadFromNBT(steps.getCompound(i));
                    }
                }
            }
            progress.currentStep = tag.getInt("currentStep");
            return progress;
        }
        return null;
    }

    private final Report report;
    private final List<SurgeonStep> steps = new ArrayList<>();
    private int currentStep = 0;

    public SurgeonProgress(SurgeonEntity surgeon, Report report) {
        this.report = report;
        steps.add(new SurgeonCollectPatientStep(surgeon, surgeon.getBellData(), report.getPatientType()));
        for (ReportStep step : report.getSteps()) {
            SurgeonStep newStep = switch (step.getType()) {
                case POSITION -> null; // TODO
                case EXTRACTION, INCISION -> new SurgeonCompletableStep(surgeon, surgeon.getBellData(), (ReportStep.CompletableStep) step);
                case INJECTION -> new SurgeonInjectionStep(surgeon, surgeon.getBellData(), (InjectionStep) step);
                case INSERTION -> new SurgeonInsertionStep(surgeon, surgeon.getBellData(), (InsertionStep) step);
                case STITCHING -> null; // TODO
                default -> null;
            };
            if (newStep != null) {
                steps.add(newStep);
            }
        }
    }

    public boolean tick() { // will return false if progress must be canceled (due to removal of patient or be from other causes)
        return true;
    }

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag stepList = new ListTag();
        tag.put("steps", stepList);
        for (SurgeonStep step : steps) {
            stepList.add(step.saveToNBT());
        }
        tag.putInt("currentStep", currentStep);
        return tag;
    }
}
