package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.entity.WeeperEntity;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.surgery.SurgeryUtil;
import com.valeriotor.beyondtheveil.tile.PatientPodBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.world.saved.LifeEconomyData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class SurgeonDisposePatientStep extends SurgeonStep {

    boolean disposedPatient = false;
    boolean disposedItems = false;
    protected SurgeonDisposePatientStep(@NotNull SurgeonEntity surgeon, @NotNull BellData bellData) {
        super(surgeon, bellData);
    }

    @Override
    public boolean performAction() {
        if (disposedPatient && disposedItems) {
            return true;
        }
        if (!disposedPatient) {
            if (surgeon.getHeldPatientType() != null) {
                if (!tryDisposeInPod()) {
                    tryDisposeInSpot();
                }
                if (surgeon.getHeldPatientType() == null) {
                    disposedPatient = true;
                    return false;
                }
            } else {
                SurgicalBE be = moveToBE();
                if (be != null) {
                    if (be.getPatientStatus() != null && be.getPatientStatus().getPatientType() != PatientType.PLAYER) {
                        be.collectPatientSurgeon(surgeon);
                    } else {
                        disposedPatient = true;
                    }
                }
            }
        } else {
            if (!surgeon.getItems().isEmpty()) {
                if (surgeon.getItems().stream().allMatch(ItemStack::isEmpty)) { // sanity check
                    surgeon.getItems().clear();
                    disposedItems = true;
                } else {
                    tryPutItemsInContainer();
                }
            } else {
                disposedItems = true;
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

    private void tryDisposeInSpot() {
        List<BlockPos> outputSpots = bellData.outputSpots();
        outputSpots.sort(Comparator.comparingDouble(pos -> surgeon.distanceToSqr(pos.getCenter())));
        for (BlockPos outputSpot : outputSpots) {
            if (surgeon.distanceToSqr(outputSpot.getCenter()) < 6) {
                Mob mob = surgeon.getHeldPatientEntity();
                if (mob != null) {
                    mob = SurgeryUtil.transformHeldPatient(mob, (ServerLevel) surgeon.level(), surgeon.getMasterID());
                    mob.setPos(outputSpot.getCenter().add(0,1,0));
                    surgeon.level().addFreshEntity(mob);
                    if (mob instanceof WeeperEntity weeper) {
                        weeper.standUp();
                    }
                    if (mob instanceof PlayerMinion minion) {
                        minion.setMasterID(surgeon.getMasterID());
                    }
                }
                surgeon.setHeldPatient(null, new CompoundTag());
            } else {
                if (surgeon.tickCount % 20 <= 1) {
                    surgeon.getNavigation().moveTo(outputSpot.getX(), outputSpot.getY(), outputSpot.getZ(), 1);
                }
            }
        }
    }

    private void tryPutItemsInContainer() {
        List<BlockPos> outputContainers = bellData.outputContainers();
        outputContainers.sort(Comparator.comparingDouble(pos -> surgeon.distanceToSqr(pos.getCenter())));
        for (BlockPos container : outputContainers) {
            BlockEntity be = surgeon.level().getBlockEntity(container);
            if (be != null && be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                IItemHandler iItemHandler = be.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get();
                for (int i = 0; i < iItemHandler.getSlots(); i++) {
                    if (iItemHandler.insertItem(i, surgeon.getItems().get(0), true).getCount() < surgeon.getItems().get(0).getCount()) {
                        if (surgeon.distanceToSqr(container.getCenter()) < 6) {
                            ItemStack remainder = iItemHandler.insertItem(i, surgeon.getItems().get(0), false);
                            if (remainder.isEmpty()) {
                                surgeon.getItems().remove(0);
                                if (surgeon.getItems().isEmpty()) {
                                    return;
                                }
                            } else {
                                surgeon.getItems().set(0, remainder);
                            }
                        } else {
                            surgeon.getNavigation().moveTo(container.getX(), container.getY(), container.getZ(), 1);
                        }
                    }
                }
            }
        }

    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("disposedPatient", disposedPatient);
        tag.putBoolean("disposedItems", disposedItems);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {
        disposedPatient = tag.getBoolean("disposedPatient");
        disposedItems = tag.getBoolean("disposedItems");
    }
}
