package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.tile.PatientPodBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class SurgeonDisposePatientStep extends SurgeonStep {

    boolean done = false;
    protected SurgeonDisposePatientStep(@NotNull SurgeonEntity surgeon, @NotNull BellData bellData) {
        super(surgeon, bellData);
    }

    @Override
    public boolean performAction() {
        if (done) {
            return true;
        }
        if (surgeon.getHeldPatientType() != null) {
            tryDisposeInPod();
            if (surgeon.getHeldPatientType() == null) {
                done = true;
                return true;
            }
        } else {
            SurgicalBE be = moveToBE();
            if (be != null) {
                be.collectPatientSurgeon(surgeon);
            }
        }
        return false;
    }

    private boolean tryDisposeInPod() {
        List<BlockPos> outputPods = bellData.outputPods();
        outputPods.sort(Comparator.comparingDouble(pos -> surgeon.distanceToSqr(pos.getCenter())));
        for (BlockPos outputPod : outputPods) {
            if (surgeon.level().getBlockEntity(outputPod) instanceof PatientPodBE pod) {
                LifeEconomyData.PodData podData = LifeEconomyData.getInstance((ServerLevel) surgeon.level()).getPodData(outputPod);
                if (podData != null) {
                    PatientType containedPatient = podData.getPatient();
                    if (containedPatient == null) {
                        if (surgeon.distanceToSqr(outputPod.getCenter()) < 6) {
                            podData.setPatientAndSync(surgeon.getHeldPatientType(), surgeon.getHeldPatientData(), (ServerLevel) surgeon.level());
                            surgeon.setHeldPatient(null, new CompoundTag());
                        } else {
                            if (surgeon.tickCount % 20 <= 1) {
                                surgeon.getNavigation().moveTo(outputPod.getX(), outputPod.getY(), outputPod.getZ(), 1);
                            }
                        }
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
        tag.putBoolean("done", done);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {
        done = tag.getBoolean("done");
    }
}
