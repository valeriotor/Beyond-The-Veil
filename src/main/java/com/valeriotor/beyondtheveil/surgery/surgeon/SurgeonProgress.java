package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.notes.*;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;

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
            progress.valid = tag.getBoolean("valid");
            return progress;
        }
        return null;
    }

    private final SurgeonEntity surgeon;
    private final Report report;
    private final List<SurgeonStep> steps = new ArrayList<>();
    private int currentStep = 0;
    private boolean valid = true;

    public SurgeonProgress(SurgeonEntity surgeon, Report report) {
        this.surgeon = surgeon;
        this.report = report;
        steps.add(new SurgeonCollectPatientStep(surgeon, surgeon.bellData, report.getPatientType()));
        for (ReportStep step : report.getSteps()) {
            SurgeonStep newStep = switch (step.getType()) {
                case POSITION -> new SurgeonPositionStep(surgeon, surgeon.bellData, (PositionStep) step);
                case EXTRACTION, INCISION -> new SurgeonCompletableStep(surgeon, surgeon.bellData, (ReportStep.CompletableStep) step);
                case INJECTION -> new SurgeonInjectionStep(surgeon, surgeon.bellData, (InjectionStep) step);
                case INSERTION -> new SurgeonInsertionStep(surgeon, surgeon.bellData, (InsertionStep) step);
                case STITCHING -> new SurgeonStitchingStep(surgeon, surgeon.bellData, step);
                default -> null;
            };
            if (newStep != null) {
                steps.add(newStep);
            }
            if (step instanceof PositionStep ps) {
                BlockPos surgicalBE = surgeon.bellData.getSurgicalBE();
                if (surgicalBE != null && surgeon.level().getBlockEntity(surgicalBE) instanceof SurgicalBE be) {
                    if (!be.allowedLocations().contains(ps.getLocation().getLocation())) {
                        valid = false;
                    }
                }
            }
        }
        steps.add(new SurgeonDisposePatientStep(surgeon, surgeon.bellData));
    }

    /**
     * @return false if progress must be canceled (due to removal of patient or be from other causes)
     */
    public boolean tick() {
        if (isFinished()) {
            return true;
        }
        if (!valid) {
            return false;
        }
        BellData bellData = surgeon.bellData;
        BlockPos surgicalBE = bellData.getSurgicalBE();
        if (surgicalBE == null) {
            return false;
        }
        BlockEntity be = surgeon.level().getBlockEntity(surgicalBE);
        if (!(be instanceof SurgicalBE be1) || ((currentStep > 0 && currentStep < steps.size() - 1 && be1.getPatientStatus() == null))) {
            return false;
        }
        SurgeonStep step = steps.get(currentStep);
        if (step.performAction()) {
            currentStep++;
        }
        return true;
    }

    public boolean isFinished() {
        return currentStep == steps.size();
    }

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag stepList = new ListTag();
        tag.put("steps", stepList);
        for (SurgeonStep step : steps) {
            stepList.add(step.saveToNBT());
        }
        tag.putInt("currentStep", currentStep);
        tag.putBoolean("valid", valid);
        tag.put("report", report.saveToNBT());
        return tag;
    }
}
