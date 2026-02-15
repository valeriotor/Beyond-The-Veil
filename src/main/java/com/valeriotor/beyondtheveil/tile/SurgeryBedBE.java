package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.block.SurgeryBedBlock;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.util.GuiType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class SurgeryBedBE extends SurgicalBE {
    public SurgeryBedBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.SURGERY_BED_BE.get(), pWorldPosition, pBlockState, SurgicalLocation.BACK);
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
    public Set<SurgicalLocation> allowedLocations() {
        return Set.of(SurgicalLocation.CHEST, SurgicalLocation.BACK);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, 0, -1), getBlockPos().offset(2,2,2));
    }

    @Override
    protected boolean tryPlacePlayer(ServerPlayer player) {
        if (player.isPassenger()) {
            player.stopRiding();
        }

        Messages.sendToPlayer(GenericToClientPacket.openGui(GuiType.SURGERY_BED), player);

        player.setPose(Pose.SLEEPING);
        BlockPos pos = getBlockPos();
        Direction direction = getBlockState().getValue(SurgeryBedBlock.FACING).getCounterClockWise();
        player.setPos((double)pos.getX() + 0.5D + direction.getStepX(), (double)pos.getY() + 1, (double)pos.getZ() + 0.5D + direction.getStepZ());
        player.setSleepingPos(pos);
        player.setDeltaMovement(Vec3.ZERO);
        player.hasImpulse = true;
        player.attackAnim = 0;
        return true;
    }
}
