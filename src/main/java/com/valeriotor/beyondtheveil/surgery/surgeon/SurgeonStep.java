package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public abstract class SurgeonStep {

    @NotNull protected final SurgeonEntity surgeon;
    @NotNull protected final BellData bellData;

    protected SurgeonStep(@NotNull SurgeonEntity surgeon, @NotNull BellData bellData) {
        this.surgeon = surgeon;
        this.bellData = bellData;
    }

    public abstract boolean performAction();

    public abstract CompoundTag saveToNBT();

    public abstract void loadFromNBT(CompoundTag tag);

    protected SurgicalBE moveToBE() {
        BlockPos bePos = bellData.getSurgicalBE();
        if (bePos != null) {
            if (surgeon.distanceToSqr(bePos.getCenter()) > 5) {
                if (surgeon.tickCount % 20 <= 1) {
                    surgeon.getNavigation().moveTo(bePos.getX(), bePos.getY(), bePos.getZ(), 1);
                }
            } else {
                if (surgeon.distanceToSqr(bePos.getCenter()) < 2) {
                    surgeon.getNavigation().stop();
                }
                if (surgeon.level().getBlockEntity(bePos) instanceof SurgicalBE be) {
                    return be;
                }
            }
        }
        return null;
    }


    public static abstract class SurgeonReportStep extends SurgeonStep {

        protected final ReportStep reportStep;

        public SurgeonReportStep(SurgeonEntity surgeon, BellData bellData, ReportStep reportStep) {
            super(surgeon, bellData);
            this.reportStep = reportStep;
        }
    }
}
