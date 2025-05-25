package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.util.ProcessionDataProvider;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.util.multiblocks.MultiblockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BloodWellBE extends BlockEntity {

    private int counter;

    public BloodWellBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.BLOOD_WELL_BE.get(), pWorldPosition, pBlockState);
    }

    public void tickServer() {
        counter++;
        if ((counter & 15) == 0) {
            if (!MultiblockRegistry.BLOOD_WELL_COMPLETE.checksOutBottomCenter(level, worldPosition.below())) {
                level.setBlock(worldPosition, Registration.BLOOD_BRICK.get().defaultBlockState(), 3);
                return;
            }
            List<Mob> undeads = this.level.getEntities(EntityTypeTest.forClass(Mob.class), AABB.ofSize(new Vec3(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()), 64, 64, 64), e -> e.getMobType() == MobType.UNDEAD);

            for (Mob undead : undeads) {
                undead.getCapability(ProcessionDataProvider.PROCESSION_DATA).ifPresent(c -> {
                    c.setDestination(worldPosition, worldPosition, true);
                });
            }
        }
    }

}
