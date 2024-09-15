package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SurgeryBedBE extends SurgicalBE {
    public SurgeryBedBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.SURGERY_BED_BE.get(), pWorldPosition, pBlockState, SurgicalLocation.BACK);
    }

    @Override
    public boolean interact(Player p, ItemStack in, InteractionHand hand) {
        if (level == null) {
            return false;
        }
        if (level.isClientSide)
            return true;
        if (getEntityData() != null) {
            if (p.isShiftKeyDown() && p.getItemInHand(hand).isEmpty()) {
                PatientStatus patientStatus = getPatientStatus();
                SurgicalLocation exposedLocation = patientStatus.getExposedLocation();
                if (exposedLocation == SurgicalLocation.BACK) {
                    patientStatus.setExposedLocation(SurgicalLocation.CHEST);
                } else {
                    patientStatus.setExposedLocation(SurgicalLocation.BACK);
                }
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
                return true;
            }
        }
        return super.interact(p, in, hand);
    }

    @Override
    public PatientStatus getPatientStatus() {
        return super.getPatientStatus();
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, 0, -1), getBlockPos().offset(2,2,2));
    }
}
