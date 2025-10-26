package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.notes.InsertionStep;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

import java.util.Comparator;
import java.util.List;

public class SurgeonInsertionStep extends SurgeonStep.SurgeonReportStep {

    private final InsertionStep reportStep;
    private ItemStack toInsert = null;
    private int currentDuration = 0;
    private boolean done = false;

    public SurgeonInsertionStep(SurgeonEntity surgeon, BellData bellData, InsertionStep reportStep) {
        super(surgeon, bellData, reportStep);
        this.reportStep = reportStep;
    }

    @Override
    public boolean performAction() {
        if (done) {
            return true;
        }
        if (toInsert == null) {
            if (surgeon.tickCount % 20 <= 1) {
                List<BlockPos> inputContainers = bellData.inputContainers();
                inputContainers.sort(Comparator.comparingDouble(pos -> surgeon.distanceToSqr(pos.getCenter())));
                for (BlockPos container : inputContainers) {
                    BlockEntity be = surgeon.level().getBlockEntity(container);
                    if (be != null && be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                        IItemHandler iItemHandler = be.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get();
                        for (int i = 0; i < iItemHandler.getSlots(); i++) {
                            if (iItemHandler.getStackInSlot(i).getItem() == reportStep.getIngredient()) {
                                if (surgeon.distanceToSqr(container.getCenter()) < 6) {
                                    toInsert = iItemHandler.extractItem(i, 1, false);
                                } else {
                                    surgeon.getNavigation().moveTo(container.getX(), container.getY(), container.getZ(), 1);
                                }
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            SurgicalBE be = moveToBE();
            if (be != null) {
                PatientStatus status = be.getPatientStatus();
                if (status != null) {
                    status.insert(null, toInsert, currentDuration++, () -> {
                        done = true;
                        toInsert = null;
                    }, be);
                }
            }
        }
        return false;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        if (toInsert != null) {
            tag.put("toInsert", toInsert.save(new CompoundTag()));
        }
        tag.putInt("currentDuration", currentDuration);
        tag.putBoolean("done", done);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {
        if (tag.contains("toInsert")) {
            toInsert = ItemStack.of(tag.getCompound("toInsert"));
        }
        currentDuration = tag.getInt("currentDuration");
        done = tag.getBoolean("done");
    }
}
