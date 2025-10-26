package com.valeriotor.beyondtheveil.surgery.surgeon;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.notes.InjectionStep;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SurgeonInjectionStep extends SurgeonStep.SurgeonReportStep {

    private final InjectionStep reportStep;
    private boolean startInjection = false;
    private FluidTank tank = new FluidTank(10000);

    public SurgeonInjectionStep(SurgeonEntity surgeon, BellData bellData, InjectionStep reportStep) {
        super(surgeon, bellData, reportStep);
        this.reportStep = reportStep;
    }

    @Override
    public boolean performAction() {
        if (!startInjection) {
            if (tank.getFluidAmount() < reportStep.getAmount()) {
                List<BlockPos> inputContainers = bellData.inputContainers();
                inputContainers.sort(Comparator.comparingDouble(pos -> surgeon.distanceToSqr(pos.getCenter())));
                for (BlockPos container : inputContainers) {
                    BlockEntity be = surgeon.level().getBlockEntity(container);
                    BlockState state = surgeon.level().getBlockState(container);
                    if (be != null && be.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
                        FluidStack drain = be.getCapability(ForgeCapabilities.FLUID_HANDLER).resolve().get().drain(new FluidStack(reportStep.getFluid(), 1), IFluidHandler.FluidAction.SIMULATE);
                        if (!drain.isEmpty()) {
                            if (surgeon.distanceToSqr(container.getCenter()) < 6) {
                                FluidStack drained = be.getCapability(ForgeCapabilities.FLUID_HANDLER).resolve().get().drain(new FluidStack(reportStep.getFluid(), Math.min(5, reportStep.getAmount() - tank.getFluidAmount())), IFluidHandler.FluidAction.EXECUTE);
                                tank.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                be.setChanged();
                                surgeon.level().sendBlockUpdated(container, state, state, 2);
                            } else {
                                if (surgeon.tickCount % 20 <= 1) {
                                    surgeon.getNavigation().moveTo(container.getX(), container.getY(), container.getZ(), 1);
                                }
                            }
                        }
                    }

                }
            } else {
                startInjection = true;
            }
        } else {
            SurgicalBE be = moveToBE();
            if (be != null) {
                PatientStatus patientStatus = be.getPatientStatus();
                if (patientStatus != null) {
                    patientStatus.inject(null, tank.drain(1, IFluidHandler.FluidAction.SIMULATE), be, tank);
                    if (tank.getFluidAmount() == 0) {
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
        tag.put("tank", tank.writeToNBT(new CompoundTag()));
        tag.putBoolean("startInjection", startInjection);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {
        if (tag.contains("tank")) {
            tank.readFromNBT(tag.getCompound("tank"));
        }
        startInjection = tag.getBoolean("startInjection");
    }
}
