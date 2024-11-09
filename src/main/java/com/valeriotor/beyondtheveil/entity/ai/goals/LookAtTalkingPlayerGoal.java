package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.ShoremanEntity;
import com.valeriotor.beyondtheveil.entity.Talkable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

public class LookAtTalkingPlayerGoal<T extends PathfinderMob & Talkable> extends LookAtPlayerGoal {
    private final T villager;
    private boolean earlyStop;

    public LookAtTalkingPlayerGoal(T pVillager) {
        super(pVillager, Player.class, 8.0F);
        this.villager = pVillager;
    }

    public boolean canUse() {
        if (this.villager.isTalking()) {
            this.lookAt = this.villager.getTalkingPlayer();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (earlyStop) {
            earlyStop = false;
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
        if (villager instanceof ShoremanEntity shoreman) {
            if (shoreman.getProfession() == ShoremanEntity.ShoremanProfession.LIGHTHOUSE_KEEPER && !this.villager.isTalking()) {
                earlyStop = true;
            }
        }
    }
}
