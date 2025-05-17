package com.valeriotor.beyondtheveil.entity.ai.goals;

import com.valeriotor.beyondtheveil.entity.DeepOneEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DeepOneContact1Goal extends Goal {

    private final DeepOneEntity deepOne;
    private int counter = 0;

    public DeepOneContact1Goal(DeepOneEntity deepOne) {
        this.deepOne = deepOne;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return deepOne.getContactType() != null && deepOne.getContactType().isMove();
    }

    @Override
    public void start() {
        counter = deepOne.getContactType().getStartOffset() + deepOne.getExtraCounterOffset();
    }

    @Override
    public void tick() {
        float arg = counter * Mth.PI / 50;
        float sin = 0;
        float cos = 0;
        DeepOneEntity.ContactType contactType = deepOne.getContactType();
        if (contactType == DeepOneEntity.ContactType.MOVE2) {
            counter--;
            sin = -Mth.sin(arg);
            cos = -Mth.cos(arg);
        } else {
            counter++;
            sin = Mth.sin(arg);
            cos = Mth.cos(arg);
        }
        float factor = contactType.getFactor();
        deepOne.setDeltaMovement(cos / factor, 0, sin / factor);
        double atan2 = Mth.atan2(sin, cos);
        deepOne.setYRot((float) atan2 * 180 / Mth.PI - 90);
        // maybe set y to initial value


    }
}
