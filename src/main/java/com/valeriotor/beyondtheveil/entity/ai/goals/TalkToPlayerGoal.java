package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.BloodCultistEntity;
import com.valeriotor.beyondtheveil.entity.Talkable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class TalkToPlayerGoal<T extends PathfinderMob & Talkable> extends Goal {
    private final T mob;

    public TalkToPlayerGoal(T pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (!this.mob.isAlive()) {
            return false;
        } else if (!this.mob.onGround() && !(this.mob.getVehicle() instanceof BloodCultistEntity)) {
            return false;
        } else if (this.mob.hurtMarked) {
            return false;
        } else {
            Player player = this.mob.getTalkingPlayer();
            if (player == null) {
                return false;
            } else if (this.mob.distanceToSqr(player) > 16.0D) {
                return false;
            } else {
                return player.containerMenu != null;
            }
        }
    }

    public void start() {
        this.mob.getNavigation().stop();
    }

    public void stop() {
        this.mob.setTalkingPlayer((Player)null);
    }
}
