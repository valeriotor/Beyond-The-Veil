package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class StrollThroughHamletGoal extends WaterAvoidingRandomStrollGoal {

    private final ShoremanEntity shoreman;
    private final double speed;

    public StrollThroughHamletGoal(ShoremanEntity shoreman, double pSpeedModifier) {
        super(shoreman, pSpeedModifier);
        this.shoreman = shoreman;
        speed = pSpeedModifier;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        BlockPos villageCenter = shoreman.getVillageCenter();
        BlockPos spawnPoint = shoreman.getSpawnPoint();
        if (villageCenter != null && spawnPoint != null) {
            if (shoreman.distanceToSqr(villageCenter.getCenter()) > 2500 && shoreman.distanceToSqr(spawnPoint.getCenter()) > 1600) {
                return LandRandomPos.getPosTowards(shoreman, 10, 7, spawnPoint.getCenter());
            }
        }
        return super.getPosition();
    }
}
