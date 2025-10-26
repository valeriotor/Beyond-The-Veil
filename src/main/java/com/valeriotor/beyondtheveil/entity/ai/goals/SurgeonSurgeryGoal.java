package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.SurgeonEntity;
import com.valeriotor.beyondtheveil.surgery.surgeon.SurgeonProgress;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SurgeonSurgeryGoal extends Goal {

    private final SurgeonEntity surgeon;
    private int ticksTillNextProgress = 20;

    public SurgeonSurgeryGoal(SurgeonEntity surgeon) {
        this.surgeon = surgeon;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return surgeon.getReport() != null;
    }

    @Override
    public void tick() {
        if(canUse()) { // probably unnecessary if? Just to make sure...
            SurgeonProgress progress = surgeon.getProgress();
            if (progress != null) {
                boolean shouldContinue = progress.tick();
                if (!shouldContinue || progress.isFinished()) {
                    surgeon.removeProgress();
                }
            } else {
                ticksTillNextProgress--;
                if (ticksTillNextProgress <= 0) {
                    ticksTillNextProgress = 20;
                    surgeon.newProgress();
                }
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
