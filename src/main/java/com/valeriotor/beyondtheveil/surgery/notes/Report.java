package com.valeriotor.beyondtheveil.surgery.notes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Report {

    public static Report loadFromNBT(CompoundTag tag) {
        String name = tag.getString("name");
        Report r = new Report(name);
        CompoundTag steps1 = tag.getCompound("steps");
        steps1.getAllKeys().stream().sorted(Comparator.comparingInt(Integer::valueOf)).forEach(s -> {
            ReportStep reportStep = ReportStep.stepFromNBT(steps1.getCompound(s));
            r.steps.add(reportStep);
        });
        try {
            r.patientType = ReportPatientType.valueOf(tag.getString("patientType"));
        } catch (IllegalArgumentException ignored) {

        }
        return r;
    }

    private final String name;
    private final List<ReportStep> steps = new ArrayList<>();
    private int successful;
    private ReportPatientType patientType = ReportPatientType.HUMAN;


    public Report(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addStep(ReportStep step) {
        steps.add(step);
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public int getSuccessful() {
        return successful;
    }

    public List<ReportStep> getSteps() {
        return new ArrayList<>(steps);
    }

    public ReportPatientType getPatientType() {
        return patientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        return name.equals(report.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        CompoundTag steps = new CompoundTag();
        for (int i = 0; i < this.steps.size(); i++) {
            steps.put(String.valueOf(i), this.steps.get(i).saveToNBT());
        }
        tag.put("steps", steps);
        tag.putString("patientType", patientType.name());
        return tag;
    }
}
