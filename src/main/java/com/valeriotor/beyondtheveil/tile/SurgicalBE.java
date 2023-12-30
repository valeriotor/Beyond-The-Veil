package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SurgicalBE extends BlockEntity {

    private PatientStatus patientStatus;

    public SurgicalBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void use(Player player, InteractionHand hand) {
        if (level == null) {
            return;
        }

    }

}
