package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.capability.surgery.ConvalescentDataProvider;
import com.valeriotor.beyondtheveil.client.model.entity.SurgeryPatient;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgeryUtil;
import com.valeriotor.beyondtheveil.surgery.notes.ReportPatientType;
import com.valeriotor.beyondtheveil.tile.PatientPodBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SurgeonCollectPatientStep extends SurgeonStep {

    private final ReportPatientType patientType;

    protected SurgeonCollectPatientStep(@NotNull SurgeonEntity surgeon, @NotNull BellData bellData, ReportPatientType patientType) {
        super(surgeon, bellData);
        this.patientType = patientType;
    }

    @Override
    public boolean performAction() {
        BlockPos bePos = bellData.getSurgicalBE();
        if (bePos != null && surgeon.level().getBlockEntity(bePos) instanceof SurgicalBE be) {
            if (be.getPatientStatus() != null && patientType.getTypes().contains(be.getPatientStatus().getPatientType())) {
                return true;
            } else if (be.getPatientStatus() == null) {
                if (surgeon.getHeldPatientType() == null) {
                    boolean foundInPod = tryCollectFromPod();
                    if (!foundInPod) {
                        tryCollectFromSpot();
                    }
                } else {
                    if (surgeon.distanceToSqr(bePos.getCenter()) < 6) {
                        be.placePatientSurgeon(surgeon.getHeldPatientType(), surgeon.getHeldPatientData());
                        surgeon.setHeldPatient(null, new CompoundTag());
                    } else {
                        if (surgeon.tickCount % 20 == 0) {
                            surgeon.getNavigation().moveTo(bePos.getX(), bePos.getY(), bePos.getZ(), 1);
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean tryCollectFromPod() {
        List<BlockPos> inputPods = bellData.inputPods();
        inputPods.sort(Comparator.comparingDouble(pos -> surgeon.distanceToSqr(pos.getCenter())));
        for (BlockPos inputPod : inputPods) {
            if (surgeon.level().getBlockEntity(inputPod) instanceof PatientPodBE pod) {
                LifeEconomyData.PodData podData = LifeEconomyData.getInstance((ServerLevel) surgeon.level()).getPodData(inputPod);
                if (podData != null) {
                    PatientType takenPatient = podData.getPatient();
                    if (patientType.getTypes().contains(takenPatient)) {
                        if (surgeon.distanceToSqr(inputPod.getCenter()) < 6) {
                            surgeon.setHeldPatient(takenPatient, podData.getEntity());
                            podData.setPatientAndSync(null, null, (ServerLevel) surgeon.level());
                        } else {
                            if (surgeon.tickCount % 20 == 0) {
                                surgeon.getNavigation().moveTo(inputPod.getX(), inputPod.getY(), inputPod.getZ(), 1);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void tryCollectFromSpot() {
        List<BlockPos> inputSpots = bellData.inputSpots();
        inputSpots.sort(Comparator.comparingDouble(pos -> surgeon.distanceToSqr(pos.getCenter())));
        for (BlockPos inputPod : inputSpots) {
            List<Entity> entities = surgeon.level().getEntities((Entity) null, AABB.ofSize(inputPod.getCenter(), 15, 5, 15), e -> e instanceof SurgeryPatient sp && patientType.getTypes().contains(sp.getPatientType()));
            Optional<Entity> closest = entities.stream().min(Comparator.comparingDouble(surgeon::distanceToSqr));
            if (closest.isPresent() && closest.get() instanceof SurgeryPatient sp && closest.get() instanceof Mob mob) {
                Entity e = closest.get();
                if (surgeon.distanceToSqr(e) < 6) {
                    CompoundTag data = SurgeryUtil.heldPatientData(mob);
                    surgeon.setHeldPatient(sp.getPatientType(), data);
                    mob.discard();
                } else {
                    if (surgeon.tickCount % 20 == 0) {
                        surgeon.getNavigation().moveTo(mob.getX(), mob.getY(), mob.getZ(), 1);
                    }
                }
                break;
            }
        }
    }

    @Override
    public CompoundTag saveToNBT() { // relevant data is stored in the surgeon, not here
        return new CompoundTag();
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {

    }
}
