package com.valeriotor.beyondtheveil.surgery.notes;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public abstract class ReportStep {

    public static ReportStep stepFromNBT(CompoundTag tag) {
        try {
            ReportStepType type = ReportStepType.valueOf(tag.getString("type"));
            return stepFromType(type, tag);
        } catch (Exception e) {
            return new SimpleStep(ReportStepType.NONE);
        }
    }

    private static ReportStep stepFromType(ReportStepType type, CompoundTag tag) {
        return switch (type) {
            case NONE, DEATH, PAIN, STITCHING -> new SimpleStep(type);
            case POSITION -> PositionStep.fromNBT(tag);
            case EXTRACTION, INCISION -> new CompletableStep(type, tag);
            case INJECTION -> new InjectionStep(tag);
            case INSERTION -> new InsertionStep(tag);
        };

    }

    @NotNull private final ReportStepType type;

    public ReportStep(@NotNull ReportStepType type) {
        this.type = type;
    }

    public final ReportStepType getType() {
        return type;
    }

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.name());
        return tag;
    }

    public static class SimpleStep extends ReportStep {

        public SimpleStep(ReportStepType type) {
            super(type);
        }
    }

    public static class CompletableStep extends ReportStep {
        private final boolean complete;

        public CompletableStep(ReportStepType type, CompoundTag tag) {
            super(type);
            this.complete = tag.getBoolean("complete");
        }

        public CompletableStep(ReportStepType type, boolean complete) {
            super(type);
            this.complete = complete;
        }

        public boolean isComplete() {
            return complete;
        }

        @Override
        public CompoundTag saveToNBT() {
            CompoundTag tag = super.saveToNBT();
            tag.putBoolean("complete", complete);
            return tag;
        }
    }


}
