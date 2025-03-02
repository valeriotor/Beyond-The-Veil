package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.Weeping;
import com.valeriotor.beyondtheveil.tile.LacrymatoryBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class WeepGoal<T extends Mob & Weeping> extends Goal {

    private final T entity;
    private final double speed;
    private static final double MAX_DISTANCE = 10;
    private int counter = 0;

    public WeepGoal(T entity, double speed) {
        this.entity = entity;
        this.speed = speed;
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (entity.getLacrymatoryPos() != null && entity.distanceToSqr(entity.getLacrymatoryPos().getCenter()) < MAX_DISTANCE * MAX_DISTANCE) {
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        counter++;
        if ((counter & 3) == 0) {
            BlockPos lPos = entity.getLacrymatoryPos();
            if (entity.distanceToSqr(lPos.getCenter()) > 1.5 * 1.5) {
                entity.getNavigation().moveTo(lPos.getX(), lPos.getY(), lPos.getZ(), speed);
            }
            if ((counter & 127) == 0) {
                if (entity.distanceToSqr(lPos.getCenter()) < 2.5 * 2.5) {
                    if (entity.level().getBlockEntity(lPos) instanceof LacrymatoryBE be) {
                        be.addTears(entity.mbWept());
                    }
                }
            }
        }
    }
}
