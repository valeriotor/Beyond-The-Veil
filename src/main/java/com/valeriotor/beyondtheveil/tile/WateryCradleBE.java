package com.valeriotor.beyondtheveil.tile;

import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Set;

public class WateryCradleBE extends SurgicalBE {


    private Mob backupEntity;
    private int weeperCreatedTicks;

    public WateryCradleBE(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BTVBlockEntities.WATERY_CRADLE_BE.get(), pWorldPosition, pBlockState, SurgicalLocation.SKULL);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().offset(-1, 0, -1), getBlockPos().offset(2,1,2));
    }

    @Override
    protected void weeperCreated() {
        this.backupEntity = getEntity();
        weeperCreatedTicks = 180;
    }

    @Override
    public void tickClient() {
        super.tickClient();
        if (backupEntity != null) {
            backupEntity.tick();
        }
        if (weeperCreatedTicks > 0) {
            weeperCreatedTicks--;
            if (weeperCreatedTicks == 70) {
                backupEntity = null;
            }
        }
    }

    @Override
    public Set<SurgicalLocation> allowedLocations() {
        return Set.of(SurgicalLocation.SKULL);
    }

    public Mob getBackupEntity() {
        return backupEntity;
    }

    public int getWeeperCreatedTicks() {
        return weeperCreatedTicks;
    }


}
