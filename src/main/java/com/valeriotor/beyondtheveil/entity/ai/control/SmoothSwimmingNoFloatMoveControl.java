package com.valeriotor.beyondtheveil.entity.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;

public class SmoothSwimmingNoFloatMoveControl extends SmoothSwimmingMoveControl {

    private final boolean applyGravity;

    public SmoothSwimmingNoFloatMoveControl(Mob pMob, int pMaxTurnX, int pMaxTurnY, float pInWaterSpeedModifier, float pOutsideWaterSpeedModifier, boolean pApplyGravity) {
        super(pMob, pMaxTurnX, pMaxTurnY, pInWaterSpeedModifier, pOutsideWaterSpeedModifier, pApplyGravity);
        this.applyGravity = pApplyGravity;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.applyGravity && this.mob.isInWater()) {
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
        }
    }
}
