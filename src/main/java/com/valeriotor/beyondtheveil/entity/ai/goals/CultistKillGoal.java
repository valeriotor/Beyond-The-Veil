package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class CultistKillGoal extends Goal {

    private final BloodCultistEntity cultist;

    public CultistKillGoal(BloodCultistEntity cultist) {
        this.cultist = cultist;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!cultist.isAlive()) {
            return false;
        } else {
            LivingEntity entity = cultist.getKillingEntity();
            if (entity == null) {
                return false;
            } else return !(cultist.distanceToSqr(entity) > 25);
        }
    }

    @Override
    public void start() {
        this.cultist.getNavigation().stop();
    }

    @Override
    public void stop() {
        cultist.setKillingEntity(null);
    }
}
